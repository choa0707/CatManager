package com.example.catmanager.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.catmanager.R;

public class ProductFragment extends Fragment {

    View fragmentView;
    private int[] imageIDs = new int[] {
            R.drawable.baseline_home_black_18,
            R.drawable.baseline_home_black_18,
            R.drawable.baseline_home_black_18,
            R.drawable.baseline_home_black_18,
            R.drawable.baseline_home_black_18,
            R.drawable.baseline_home_black_18,
            R.drawable.baseline_home_black_18,
            R.drawable.baseline_home_black_18,
            R.drawable.baseline_home_black_18,
            R.drawable.baseline_home_black_18,
    };

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_product, container,false);

        GridView gridViewImages = (GridView)fragmentView.findViewById(R.id.gridViewImages);
        ImageGridAdapter imageGridAdapter = new ImageGridAdapter(getContext(), imageIDs);
        gridViewImages.setAdapter(imageGridAdapter);

        return fragmentView;
    }
}
