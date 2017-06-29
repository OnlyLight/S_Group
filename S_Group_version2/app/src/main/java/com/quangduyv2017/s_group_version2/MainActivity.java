package com.quangduyv2017.s_group_version2;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private String urlLichTruc = "https://docs.google.com/spreadsheets/d/1vVLQO3Rd64ocaGB7oy9WlmnULOl2GLaY3FDZ-x0bNbg/edit#gid=745163599";
    private ListView lvDanhSach;
    private ArrayList<String> arrDanhSach;
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addShow();
        arrDanhSach = new ArrayList<>();
        addArrayString();
        adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, arrDanhSach);
        lvDanhSach.setAdapter(adapter);
        setClick();
    }

    private void addShow() {
        lvDanhSach = (ListView) findViewById(R.id.lvDanhSach);
    }

    private void addArrayString() {
        arrDanhSach.add("Lịch trực");
        arrDanhSach.add("Lịch lên văn phòng");
        arrDanhSach.add("Lịch tham gia dự án");
    }

    private void setClick() {
        lvDanhSach.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (arrDanhSach.get(i).equals("Lịch trực")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlLichTruc));
                    startActivity(intent);
                } else if (arrDanhSach.get(i).equals("Lịch lên văn phòng")) {
                    startActivity(new Intent(MainActivity.this, Main2Activity.class));
                } else if (arrDanhSach.get(i).equals("Lịch tham gia dự án")) {
                    startActivity(new Intent(MainActivity.this, DuAnActivity.class));
                }
            }
        });
    }
}
