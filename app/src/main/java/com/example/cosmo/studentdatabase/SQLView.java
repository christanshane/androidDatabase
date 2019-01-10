package com.example.cosmo.studentdatabase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SQLView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlview);
        TextView tv = (TextView) findViewById(R.id.tvSQLinfo);
        StudentInfo info = new StudentInfo(this);
        info.open();
        String data = info.getData();
        info.close();
        tv.append(data);
//        tv.setText(data);
    }
}
