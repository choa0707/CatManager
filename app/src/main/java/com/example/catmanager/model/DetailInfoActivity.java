package com.example.catmanager.model;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.catmanager.R;

public class DetailInfoActivity extends AppCompatActivity {

    MyDBHelper myDBHelper;
    SQLiteDatabase sqlDB;
    TextView view_text, view_title;
    Button cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_info);

        view_text = (TextView)findViewById(R.id.detail_text);
        view_title  = (TextView)findViewById(R.id.detail_title);
        cancel = (Button)findViewById(R.id.detail_cancel);

        myDBHelper = new MyDBHelper(this);
        sqlDB = myDBHelper.getWritableDatabase();

        Intent intent = getIntent();
        int infonum = intent.getExtras().getInt("infonum");
        infonum+=1;
        String infoQuery = "SELECT * FROM information WHERE info_id="+infonum+";";
        Cursor cursor = (Cursor) sqlDB.rawQuery(infoQuery, null);
        cursor.moveToFirst();

        String text = cursor.getString(3);
        String title = cursor.getString(4);
        view_text.setText(text);
        view_title.setText(title);

        sqlDB.close();
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}