package com.example.catmanager.model;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.catmanager.R;

public class LoginCheckActivity extends AppCompatActivity {
    MyDBHelper myDBHelper;
    SQLiteDatabase sqlDB;
    Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_check);
        myDBHelper =new MyDBHelper(this);
        sqlDB = myDBHelper.getReadableDatabase();

        String loginCheck = "SELECT * FROM Cat";
        cursor =(Cursor) sqlDB.rawQuery(loginCheck, null);
        Logincheck();

        sqlDB.close();
    }
    private void Logincheck()
    {
        if (cursor.getCount() == 0)
        {
            Intent intent = new Intent(getApplicationContext(), StartActivity.class);
            startActivity(intent);
            finish();
        }
        else
        {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
