package com.quangduyv2017.s_group_version2;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DuAnActivity extends AppCompatActivity {
    private EditText edtDateDuAn;
    private Button btnDangKyDuAn, btnXemLichDuAn;
    private Database mData;
    private SimpleDateFormat format;
    private Calendar calendar;
    private int date;
    private int mounth;
    private int year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_du_an);

        addShow();

        format = new SimpleDateFormat("dd/MM/yyyy");

        calendar = Calendar.getInstance();

        date = calendar.get(Calendar.DATE);

        mounth = calendar.get(Calendar.MONTH);

        year = calendar.get(Calendar.YEAR);

        mData = new Database(DuAnActivity.this, "danhsach.sqlite", null, 1);

        mData.queryData("CREATE TABLE IF NOT EXISTS DuAn (Id INTEGER PRIMARY KEY AUTOINCREMENT, ThoiGian VARCHAR(150))");

        addEvent();
    }

    private void addShow() {
        edtDateDuAn = (EditText) findViewById(R.id.edtDateDuAn);
        btnDangKyDuAn = (Button) findViewById(R.id.btnDangKyDuAn);
        btnXemLichDuAn = (Button) findViewById(R.id.btnXemLichDuAn);
    }

    private void addEvent() {
        edtDateDuAn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chonNgay();
            }
        });

        btnDangKyDuAn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dangKy();
            }
        });

        btnXemLichDuAn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seenData();
            }
        });
    }

    private void seenData() {
        Cursor cursor = mData.getData("SELECT * FROM DuAn");

        StringBuffer buffer = new StringBuffer();
        while (cursor.moveToNext()) {
            buffer.append("ID: " + cursor.getInt(0));
            buffer.append("\nThời gian: " + cursor.getString(1) + "\n\n");
        }

        showMessage("Thông tin đã đăng ký", buffer.toString());
    }

    private void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(DuAnActivity.this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    private void dangKy() {
        if (edtDateDuAn.getText().toString().length() > 0) {
            mData.queryData("INSERT INTO DuAn VALUES(null, '" + format.format(calendar.getTime()) + "')");
            Toast.makeText(this, "Đăng ký thành công !!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Vui lòng chọn ngày !!!", Toast.LENGTH_SHORT).show();
        }
    }

    private void chonNgay() {
        DatePickerDialog pickerDialog = new DatePickerDialog(DuAnActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(i, i1, i2);
                edtDateDuAn.setText(format.format(calendar.getTime()));
            }
        }, year, mounth, date);
        pickerDialog.show();
    }
}
