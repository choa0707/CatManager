package com.example.catmanager.fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.catmanager.R;
import com.example.catmanager.model.CustomGridAdapter;
import com.example.catmanager.model.GridData;
import com.example.catmanager.model.MyDBHelper;

import java.util.ArrayList;
import java.util.List;

public class ProductFragment extends Fragment {

    View fragmentView;
    GridView gridView;
    private ArrayList<GridData> arraylist;
    private List<GridData> list;
    int i;
    MyDBHelper myDBHelper;
    SQLiteDatabase sqlDB;
    int amount;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_product, container,false);
        gridView = (GridView)fragmentView.findViewById(R.id.gridViewImages);
        myDBHelper = new MyDBHelper(getContext());
        sqlDB = myDBHelper.getWritableDatabase();

        list = new ArrayList<GridData>();
        arraylist = new ArrayList<GridData>();
        settingList();

        arraylist.addAll(list);

        gridView.setAdapter(  new CustomGridAdapter( getContext(), list ) );

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                //Toast.makeText(getContext(), ((TextView) v.findViewById( R.id.grid_name ))
                  //      .getText()+"를 카트에 추가하였습니다.", Toast.LENGTH_SHORT).show();
                //cart에 해당 product있는지 확인
                int pos = position+1;
                String cartcheckQuery = "SELECT * FROM Cart WHERE fp_id = "+pos+";";
                Cursor cursor2 = (Cursor) sqlDB.rawQuery(cartcheckQuery, null);
                cursor2.moveToFirst();
                list.get(position).num++;
                if (cursor2.getCount() == 0)
                {
                    //카트에 추가
                    String cartInsert = "INSERT INTO Cart (cat_id, fp_id, amount) VALUES (1, "+pos+", 1);";
                    sqlDB.execSQL(cartInsert);
                }
                else
                {
                    //해당 상품의 개수를 가져온다.

                    amount = cursor2.getInt(3);
                    Log.d("amount", Integer.toString(amount));
                    amount++;
                    String cartUpdate = "UPDATE Cart SET amount = "+amount+" WHERE fp_id = "+pos+";";
                    sqlDB.execSQL(cartUpdate);
                }
                Toast.makeText(getContext(), "추가 되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });



        return fragmentView;
    }

    private void settingList() {
        myDBHelper = new MyDBHelper(getContext());
        sqlDB = myDBHelper.getReadableDatabase();
        String sqlSelect = "SELECT * FROM Product";
        Cursor cursor = (Cursor) sqlDB.rawQuery(sqlSelect, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            GridData gridData = new GridData();
            gridData.name = cursor.getString(1);
            gridData.image = cursor.getInt(0);
            gridData.month = cursor.getInt(3);
            gridData.price = cursor.getInt(2);
            gridData.num = 0;
            list.add(gridData);
            cursor.moveToNext();
        }
        cursor.close();

    }

    @Override
    public void onDestroy() {
        sqlDB.close();
        super.onDestroy();
    }
}
