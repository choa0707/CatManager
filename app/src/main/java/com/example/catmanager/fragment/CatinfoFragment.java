package com.example.catmanager.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.catmanager.R;

import java.util.ArrayList;

public class CatinfoFragment extends Fragment {

    View fragmentView;


    private ArrayList<ListViewItemData> listViewItems;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        fragmentView = inflater.inflate(R.layout.fragment_catinfo, container,false);
        String[] LIST_MENU = {"털갈이", "이갈이", "예방접종"} ;
        final ArrayList<String> list1 = new ArrayList<>();
        //arraylist에 담아줌
        for (int i = 0; i < 3; i++) {
            list1.add(LIST_MENU[i]);
        }
        RecyclerView recyclerView = (RecyclerView)fragmentView.findViewById(R.id.catinfo_recyclerView) ;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext())) ;
        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
        SimpleTextAdapter adapter = new SimpleTextAdapter(list1) ;
        recyclerView.setAdapter(adapter) ;


        return fragmentView;
    }
}
