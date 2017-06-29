package com.quangduyv2017.s_group_version2;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Main2Activity extends AppCompatActivity {
    private EditText edtDate;
    private Button btnDangKy, btnXemLich;
    private CheckBox cbSang, cbChieu, cbToi;
    private Button btnUpdate, btnDelete;
    private Database data;
    private SimpleDateFormat format;
    private final Calendar calendar = Calendar.getInstance();
    private int date;
    private int mounth;
    private int year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        addShow();

        format = new SimpleDateFormat("dd/MM/yyyy");

        date = calendar.get(Calendar.DATE);

        mounth = calendar.get(Calendar.MONTH);

        year = calendar.get(Calendar.YEAR);

        data = new Database(Main2Activity.this, "danhsachLich.sqlite", null, 1);

        data.queryData("CREATE TABLE IF NOT EXISTS Lich(Id INTEGER PRIMARY KEY AUTOINCREMENT, ThoiGian VARCHAR(150), BuoiSang VARCHAR(150), BuoiChieu VARCHAR(150), BuoiToi VARCHAR(150))");

        addEvent();
    }

    private void addShow() {
        edtDate = (EditText) findViewById(R.id.edtDate);
        btnDangKy = (Button) findViewById(R.id.btnDangKy);
        btnXemLich = (Button) findViewById(R.id.btnXemLich);
        cbSang = (CheckBox) findViewById(R.id.cbSang);
        cbChieu = (CheckBox) findViewById(R.id.cbChieu);
        cbToi = (CheckBox) findViewById(R.id.cbToi);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnDelete = (Button) findViewById(R.id.btnDelete);
    }

    private void addEvent() {
        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chonNgay();
            }
        });

        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check();
            }
        });

        btnXemLich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateData();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteData();
            }
        });
    }

    private void updateData() {
        final Dialog dialog = new Dialog(Main2Activity.this);
        dialog.setContentView(R.layout.dong_id);
        final EditText edtIdUpdate = (EditText) dialog.findViewById(R.id.edtIdUpdate);
        Button btnIdUpdate = (Button) dialog.findViewById(R.id.btnIdUpdate);
        final Button btnHuyIdUpdate = (Button) dialog.findViewById(R.id.btnHuyIdUpdate);

        btnHuyIdUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        btnIdUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            int id = 0;
            String thoiGian, buoiSang, buoiChieu, buoiToi;
            final Dialog dialog2 = new Dialog(Main2Activity.this);
            dialog2.setContentView(R.layout.dong_update_info);
            final EditText edtTimeInfoUpdate = dialog2.findViewById(R.id.edtTimeInfoUpdate);
            final CheckBox cbSangInfo = (CheckBox) dialog2.findViewById(R.id.cbSangInfo);
            final CheckBox cbChieuInfo = (CheckBox) dialog2.findViewById(R.id.cbChieuInfo);
            final CheckBox cbToiInfo = (CheckBox) dialog2.findViewById(R.id.cbToiInfo);
            Button btnOKInfoUpdate = dialog2.findViewById(R.id.btnOKInfoUpdate);
            Button btnHuyInfoUpdate = dialog2.findViewById(R.id.btnHuyInfoUpdate);

            btnHuyInfoUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog2.cancel();
                }
            });

            if (edtIdUpdate.getText().toString().trim().length() <= 0) {
                Toast.makeText(Main2Activity.this, "Xin hãy nhập ID !!", Toast.LENGTH_SHORT).show();
            } else {
                Cursor cursor = data.getData("SELECT * FROM Lich");
                while (cursor.moveToNext()) {
                    id = cursor.getInt(0);
                    thoiGian = cursor.getString(1);
                    buoiSang = cursor.getString(2);
                    buoiChieu = cursor.getString(3);
                    buoiToi = cursor.getString(4);

                    if (Integer.parseInt(edtIdUpdate.getText().toString().trim()) == id) {
                        edtTimeInfoUpdate.setText(thoiGian);

                        if (buoiSang.equals("Sáng") && buoiChieu.equals("Chiều") && buoiToi.equals("Tối")) {
                            cbSangInfo.setChecked(true);
                            cbChieuInfo.setChecked(true);
                            cbToiInfo.setChecked(true);
                        } else if (buoiSang.equals("Sáng") && buoiChieu.equals("Chiều")) {
                            cbSangInfo.setChecked(true);
                            cbChieuInfo.setChecked(true);
                        } else if (buoiSang.equals("Sáng") && buoiToi.equals("Tối")) {
                            cbSangInfo.setChecked(true);
                            cbToiInfo.setChecked(true);
                        } else if (buoiChieu.equals("Chiều") && buoiToi.equals("Tối")) {
                            cbChieuInfo.setChecked(true);
                            cbToiInfo.setChecked(true);
                        } else if (buoiSang.equals("Sáng")) {
                            cbSangInfo.setChecked(true);
                        } else if (buoiChieu.equals("Chiều")) {
                            cbChieuInfo.setChecked(true);
                        } else if (buoiToi.equals("Tối")) {
                            cbToiInfo.setChecked(true);
                        }

                        dialog2.show();
                        break;
                    }
                }
                if (Integer.parseInt(edtIdUpdate.getText().toString().trim()) != id) {
                    Toast.makeText(Main2Activity.this, "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            edtTimeInfoUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(Main2Activity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                            calendar.set(i, i1, i2);
                            edtTimeInfoUpdate.setText(format.format(calendar.getTime()));
                        }
                    }, year, mounth, date);
                    datePickerDialog.show();
                }
            });

            final int finalId = id;
            btnOKInfoUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String thoiGian = edtTimeInfoUpdate.getText().toString().trim();

                    if (!cbSangInfo.isChecked() && !cbChieuInfo.isChecked() && !cbToiInfo.isChecked()) {
                        Toast.makeText(Main2Activity.this, "Đăng ký ít nhất 1 buổi", Toast.LENGTH_SHORT).show();
                    }
                    xacNhanUpdate(thoiGian, cbSangInfo.isChecked(), cbChieuInfo.isChecked(), cbToiInfo.isChecked(), finalId);
                    Toast.makeText(Main2Activity.this, "Sửa thành công !!", Toast.LENGTH_SHORT).show();
                    dialog2.dismiss();
                    dialog.dismiss();
                }
            });
            }
        });

        dialog.show();
    }

    private void xacNhanUpdate(String thoiGian, boolean sang, boolean chieu, boolean toi, int id) {
        if (sang && chieu && toi) {
            data.queryData("UPDATE Lich SET ThoiGian = '" + thoiGian + "', BuoiSang = 'Sáng', BuoiChieu = 'Chiều', BuoiToi = 'Tối' WHERE Id = '" + id + "'");
        } else if (sang && chieu) {
            data.queryData("UPDATE Lich SET ThoiGian = '" + thoiGian + "', BuoiSang = 'Sáng', BuoiChieu = 'Chiều', BuoiToi = '' WHERE Id = '" + id + "'");
        } else if (sang && toi) {
            data.queryData("UPDATE Lich SET ThoiGian = '" + thoiGian + "', BuoiSang = 'Sáng', BuoiChieu = '', BuoiToi = 'Tối' WHERE Id = '" + id + "'");
        } else if (chieu && toi) {
            data.queryData("UPDATE Lich SET ThoiGian = '" + thoiGian + "', BuoiSang = '', BuoiChieu = 'Chiều', BuoiToi = 'Tối' WHERE Id = '" + id + "'");
        } else if (sang) {
            data.queryData("UPDATE Lich SET ThoiGian = '" + thoiGian + "', BuoiSang = 'Sáng', BuoiChieu = '', BuoiToi = '' WHERE Id = '" + id + "'");
        } else if (chieu) {
            data.queryData("UPDATE Lich SET ThoiGian = '" + thoiGian + "', BuoiSang = '', BuoiChieu = 'Chiều', BuoiToi = '' WHERE Id = '" + id + "'");
        } else if (toi) {
            data.queryData("UPDATE Lich SET ThoiGian = '" + thoiGian + "', BuoiSang = '', BuoiChieu = '', BuoiToi = 'Tối' WHERE Id = '" + id + "'");
        }
    }

    private void deleteData() {
        final Dialog dialog = new Dialog(Main2Activity.this);
        dialog.setContentView(R.layout.dong_delete);
        final EditText edtID = (EditText) dialog.findViewById(R.id.edtID);
        Button btnXacNhan = (Button) dialog.findViewById(R.id.btnXacNhan);
        Button btnHuy = (Button) dialog.findViewById(R.id.btnHuy);

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtID.getText().toString().trim().length() > 0) {
                    xacNhanDelete(Integer.parseInt(edtID.getText().toString()));
                    Toast.makeText(Main2Activity.this, "Đã xóa", Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                } else {
                    Toast.makeText(Main2Activity.this, "Xin hãy nhập ID", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog.show();
    }

    private void xacNhanDelete(int id) {
        data.queryData("DELETE FROM Lich WHERE Id = '" + id + "'");
    }

    private StringBuffer info(Cursor cursor) {
        StringBuffer buffer = new StringBuffer();
        while (cursor.moveToNext()) {
            buffer.append("ID: " + cursor.getInt(0) + "\n");
            buffer.append("\nThoi gian: "+ cursor.getString(1) + "\n");
            buffer.append(cursor.getString(2) + "\n");
            buffer.append(cursor.getString(3) + "\n");
            buffer.append(cursor.getString(4) + "\n\n");
        }
        return buffer;
    }

    private void getData() {
        Cursor cursor = data.getData("SELECT * FROM Lich");

        if (cursor.getCount() == 0) {
            Toast.makeText(this, "Không có dữ liệu", Toast.LENGTH_SHORT).show();
        }

        showMessage("Đã đăng ký", info(cursor).toString());
    }

    private void showMessage(String titile, String message) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(Main2Activity.this);
        dialog.setCancelable(true);
        dialog.setTitle(titile);
        dialog.setMessage(message);
        dialog.show();
    }

    private void check() {
        if (edtDate.getText().toString().length() > 0) {
            if (cbSang.isChecked() && cbChieu.isChecked() && cbToi.isChecked()) {
                data.queryData("INSERT INTO Lich VALUES(null, '" + format.format(calendar.getTime()) + "', 'Sáng', 'Chiều', 'Tối')");
                Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
            }
            else if (cbChieu.isChecked() && cbToi.isChecked()) {
                data.queryData("INSERT INTO Lich VALUES(null, '" + format.format(calendar.getTime()) + "', '', 'Chiều', 'Tối')");
                Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
            }
            else if (cbSang.isChecked() && cbToi.isChecked()) {
                data.queryData("INSERT INTO Lich VALUES(null, '" + format.format(calendar.getTime()) + "', 'Sáng', '', 'Tối')");
                Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
            }
            else if (cbSang.isChecked() && cbChieu.isChecked()) {
                data.queryData("INSERT INTO Lich VALUES(null, '" + format.format(calendar.getTime()) + "', 'Sáng', 'Chiều', '')");
                Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
            }
            else if (cbToi.isChecked()) {
                data.queryData("INSERT INTO Lich VALUES(null, '" + format.format(calendar.getTime()) + "', '', '', 'Tối')");
                Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
            }
            else if (cbChieu.isChecked()) {
                data.queryData("INSERT INTO Lich VALUES(null, '" + format.format(calendar.getTime()) + "', '', 'Chiều', '')");
                Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
            }
            else if (cbSang.isChecked()) {
                data.queryData("INSERT INTO Lich VALUES(null, '" + format.format(calendar.getTime()) + "', 'Sáng', '', '')");
                Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this, "Chưa chọn buổi !! Xin chọn lại", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Vui lòng chọn ngày !!", Toast.LENGTH_SHORT).show();
        }
    }

    private void chonNgay() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(Main2Activity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(i, i1, i2);
                edtDate.setText(format.format(calendar.getTime()));
            }
        }, year, mounth, date);
        datePickerDialog.show();
    }
}
