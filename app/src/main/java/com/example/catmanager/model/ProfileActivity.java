package com.example.catmanager.model;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.catmanager.R;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    EditText catName, catWeight, catMonth;
    Button profileregister;
    Spinner kindSpinner;
    CheckBox male, female;
    MyDBHelper myDBHelper;
    SQLiteDatabase sqlDB;

    String name, kind;
    int month, gender;
    double weight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_acitivty);
        myDBHelper =new MyDBHelper(this);
        profileregister = (Button)findViewById(R.id.profileActivity_register);
        catName = (EditText)findViewById(R.id.profileActivity_name);
        catMonth = (EditText)findViewById(R.id.profileActivity_month);
        catWeight = (EditText)findViewById(R.id.profileActivity_weight);
        male = (CheckBox)findViewById(R.id.checkbox_male);
        female = (CheckBox)findViewById(R.id.checkbox_female);

        male.setChecked(true);

        kindSpinner = (Spinner) findViewById(R.id.kind_spinner);
        ArrayAdapter kindAdapter = ArrayAdapter.createFromResource(this, R.array.kind, android.R.layout.simple_spinner_item);
        kindSpinner.setAdapter(kindAdapter);

        profileregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    kind = kindSpinner.getSelectedItem().toString();

                    name = catName.getText().toString();
                    month = Integer.parseInt(catMonth.getText().toString());
                    weight = Double.parseDouble(catWeight.getText().toString());
                    Log.d("item", kind);
                    Log.d("item", name);
                    Log.d("item", Integer.toString(month));
                    Log.d("item", Double.toString(weight));
                    if (male.isChecked())
                    {
                        gender = 0;
                    }else
                    {
                        gender = 1;
                    }
                    sqlDB = myDBHelper.getWritableDatabase();
                    String insertQuery = "INSERT INTO Cat (name, kind, month, gender, weight) VALUES ('"+name+"','"+kind+"', "+month+", "+gender+", "+weight+");";
                    sqlDB.execSQL(insertQuery);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(), "모든 항목을 입력해주세요.", Toast.LENGTH_SHORT).show();
                }

            }
        });
        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox)female).isChecked())
                {
                    male.setChecked(true);
                    female.setChecked(false);
                }
                else
                {
                    male.setChecked(true);
                }
            }
        });
        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox)male).isChecked())
                {
                    female.setChecked(true);
                    male.setChecked(false);
                }
                else
                {
                    female.setChecked(true);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        sqlDB.close();
        super.onDestroy();
    }
}
