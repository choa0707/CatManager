package com.example.catmanager.fragment;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.catmanager.R;
import com.example.catmanager.model.CartGridAdapter;
import com.example.catmanager.model.CustomGridAdapter;
import com.example.catmanager.model.GridData;
import com.example.catmanager.model.MyDBHelper;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {
    View fragmentView;
    GridView gridView;
    private ArrayList<GridData> arraylist;
    private List<GridData> list;
    int i;
    String clickname;
    String clickname_long;
    MyDBHelper myDBHelper;
    SQLiteDatabase sqlDB;
    CartGridAdapter adapter;
    int pos;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_cart, container,false);
        gridView = (GridView)fragmentView.findViewById(R.id.cartViewImages);
        list = new ArrayList<GridData>();
        arraylist = new ArrayList<GridData>();
        myDBHelper = new MyDBHelper(getContext());
        sqlDB = myDBHelper.getReadableDatabase();
        settingList();

        arraylist.addAll(list);
        adapter = new CartGridAdapter(getContext(), list);
        gridView.setAdapter( adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                clickname = list.get(position).name;
                show(position);
            }
        });
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                clickname_long = list.get(position).name;
                show_long(position);
                return true;
            }
        });
        return fragmentView;
    }

    private void settingList() {

        String sqlSelect = "SELECT * FROM Product";
        Cursor cursor = (Cursor) sqlDB.rawQuery(sqlSelect, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            GridData gridData = new GridData();
            gridData.name = cursor.getString(1);
            gridData.image = cursor.getInt(0);
            String getnum = "SELECT amount FROM Cart WHERE fp_id = "+gridData.image+";";
            Cursor cursor2 = (Cursor) sqlDB.rawQuery(getnum, null);

            if (cursor2.getCount() == 0)
            {
                gridData.num = 0;
            }
            else
            {
                cursor2.moveToFirst();
                if (cursor2.getInt(0) != 0)
                {
                    cursor2.moveToFirst();
                    int num = cursor2.getInt(0);
                    gridData.num = num;
                    gridData.price = cursor.getInt(2);
                    list.add(gridData);
                }
            }
            cursor2.close();
            cursor.moveToNext();
        }
        cursor.close();
    }
    void show_long(final int pos2)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        String getProduct = "SELECT fp_id FROM Product WHERE name = '"+clickname+"';";
        Cursor cursor3 = (Cursor) sqlDB.rawQuery(getProduct, null);
        cursor3.moveToFirst();

        pos = cursor3.getInt(0);

        builder.setTitle("삭제");
        builder.setMessage("해당 품목을 삭제하시겠습니까?");

        builder.setPositiveButton("예",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String cartUpdate = "DELETE FROM Cart WHERE fp_id = "+pos+";";
                        sqlDB.execSQL(cartUpdate);
                        Toast.makeText(getContext(), "삭제가 완료 되었습니다." ,Toast.LENGTH_SHORT).show();
                        fragmentView.destroyDrawingCache();
                        fragmentView.setVisibility(ListView.INVISIBLE);
                        fragmentView.setVisibility(ListView.VISIBLE);

                        list.get(pos2).num = 0;
                        gridView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                });
        builder.setNegativeButton("아니요",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.show();
    }
    void show(final int pos2)
    {
        final EditText edittext = new EditText(getContext());
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        String getProduct = "SELECT fp_id FROM Product WHERE name = '"+clickname+"';";
        Cursor cursor3 = (Cursor) sqlDB.rawQuery(getProduct, null);
        cursor3.moveToFirst();

        pos = cursor3.getInt(0);

        builder.setTitle("수량변경");
        builder.setMessage("원하시는 수량을 입력해주세요.");
        builder.setView(edittext);

        builder.setPositiveButton("입력",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        int numChange;
                        numChange = Integer.parseInt(edittext.getText().toString());
                        Log.d("final debug", Integer.toString(pos));
                        String cartUpdate = "UPDATE Cart SET amount = "+numChange+" WHERE fp_id = "+ pos +";";
                        sqlDB.execSQL(cartUpdate);
                        Toast.makeText(getContext(), "변경이 완료 되었습니다." ,Toast.LENGTH_SHORT).show();
                        fragmentView.destroyDrawingCache();
                        fragmentView.setVisibility(ListView.INVISIBLE);
                        fragmentView.setVisibility(ListView.VISIBLE);

                        list.get(pos2).num = numChange;
                        gridView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                });
        builder.setNegativeButton("취소",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.show();
    }

    @Override
    public void onDestroy() {
        sqlDB.close();
        super.onDestroy();
    }

    @Override
    public void onResume() {

        super.onResume();
    }
}
