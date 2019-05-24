package com.example.catmanager.model;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.catmanager.R;
import com.example.catmanager.fragment.CartFragment;
import com.example.catmanager.fragment.CatinfoFragment;
import com.example.catmanager.fragment.ProductFragment;

public class MainActivity extends AppCompatActivity {
    // FrameLayout에 각 메뉴의 Fragment를 바꿔 줌
    private FragmentManager fragmentManager = getSupportFragmentManager();
    // 4개의 메뉴에 들어갈 Fragment들
    private CatinfoFragment catinfoFragment = new CatinfoFragment();
    private ProductFragment productFragment = new ProductFragment();
    private CartFragment cartFragment = new CartFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.mainActivity_bottom_navigation_view);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.mainActivity_frame_layout, catinfoFragment).commitAllowingStateLoss();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                switch (menuItem.getItemId()) {
                    case R.id.navigation_catinfo: {
                        transaction.replace(R.id.mainActivity_frame_layout, catinfoFragment).commitAllowingStateLoss();
                        break;
                    }
                    case R.id.navigation_product: {
                        transaction.replace(R.id.mainActivity_frame_layout, productFragment).commitAllowingStateLoss();
                        break;
                    }
                    case R.id.navigation_cart: {
                        transaction.replace(R.id.mainActivity_frame_layout, cartFragment).commitAllowingStateLoss();
                        break;
                    }

                }
                return true;
            }
        });

    }

}
