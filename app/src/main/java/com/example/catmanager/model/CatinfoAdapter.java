package com.example.catmanager.model;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.catmanager.R;

import java.util.ArrayList;
import java.util.Dictionary;

import static android.support.v4.content.ContextCompat.startActivity;

public class CatinfoAdapter extends RecyclerView.Adapter<CatinfoAdapter.CustomViewHolder> {
    private ArrayList<CatInfoData> mList;

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView title, text;


        public CustomViewHolder(View view) {
            super(view);
            this.title = (TextView)view.findViewById(R.id.catinfo_title);
            this.text = (TextView)view.findViewById(R.id.catinfo_text);
        }
    }


    public CatinfoAdapter(ArrayList<CatInfoData> list) {
        this.mList = list;
    }


    // RecyclerView에 새로운 데이터를 보여주기 위해 필요한 ViewHolder를 생성해야 할 때 호출.
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list, null);
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_catinfo, viewGroup, false);

        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }



    // Adapter의 특정 위치(position)에 있는 데이터를 보여줌.
    @Override
    public void onBindViewHolder(@NonNull final CustomViewHolder viewholder, final int position) {
        viewholder.title.setText(mList.get(position).title);
        viewholder.text.setText(mList.get(position).text);
        viewholder.title.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(v.getContext(), DetailInfoActivity.class);
               intent.putExtra("infonum",mList.get(position).id);
                v.getContext().startActivity(intent);
            }
        });
        viewholder.text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetailInfoActivity.class);
                intent.putExtra("infonum",mList.get(position).id);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }
}