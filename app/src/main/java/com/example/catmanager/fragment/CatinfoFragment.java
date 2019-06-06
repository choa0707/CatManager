package com.example.catmanager.fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.catmanager.R;
import com.example.catmanager.model.CatInfoData;
import com.example.catmanager.model.CatinfoAdapter;
import com.example.catmanager.model.MyDBHelper;

import java.util.ArrayList;
import java.util.Dictionary;

public class CatinfoFragment extends Fragment {

    View fragmentView;
    MyDBHelper myDBHelper;
    SQLiteDatabase sqlDB;
    String title, text;
    private ArrayList<CatInfoData> mArrayList;
    private CatinfoAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private int count = -1;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        fragmentView = inflater.inflate(R.layout.fragment_catinfo, container,false);
        mRecyclerView = (RecyclerView) fragmentView.findViewById(R.id.catinfo_recyclerView);
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mArrayList = new ArrayList<>();
        settingData();
        // MainActivity에서 RecyclerView의 데이터에 접근시 사용.


        // RecyclerView를 위해 CustomAdapter를 사용합니다.
        mAdapter = new CatinfoAdapter( mArrayList);
        mRecyclerView.setAdapter(mAdapter);



         //RecyclerView의 줄(row) 사이에 수평선을 넣기위해 사용.
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                mLinearLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);



        return fragmentView;
    }

    private void settingData() {
        myDBHelper = new MyDBHelper(getContext());
        sqlDB = myDBHelper.getWritableDatabase();

        String catMonthQuery = "SELECT month FROM Cat;";
        Cursor cursor = (Cursor) sqlDB.rawQuery(catMonthQuery, null);
        cursor.moveToFirst();
        int catMonth = cursor.getInt(0);

        String infoQuery = "SELECT * FROM Information;";
        cursor = (Cursor) sqlDB.rawQuery(infoQuery, null);
        cursor.moveToFirst();
        int month;
        int stop = 0;
        while (!cursor.isAfterLast()) {
            CatInfoData catData = new CatInfoData();
            month = cursor.getInt(2);
            if (month == 0)
            {
                catData.title = cursor.getString(4);
                catData.text = catData.title+" 정보 자세히.";
                catData.id = cursor.getInt(0)-1;
                mArrayList.add(catData);

            }
            else if (catMonth < month && stop == 0)
            {
                Log.d("month", Integer.toString(catMonth));
                Log.d("month", Integer.toString(month));
                catData.title = cursor.getString(4);
                catData.text = catData.title+" 정보 자세히.";
                catData.id = cursor.getInt(0)-1;
                mArrayList.add(catData);
                stop = 1;
            }
            cursor.moveToNext();
        }
        sqlDB.close();
    }
}
