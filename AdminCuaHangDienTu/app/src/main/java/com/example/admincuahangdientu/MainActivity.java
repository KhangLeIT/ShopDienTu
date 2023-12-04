package com.example.admincuahangdientu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private FrameLayout main_frame;
    private BottomNavigationView bottomNavigationView;
    private Toolbar toolbar;


    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @SuppressLint("NonConstantResourceId")
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId())
                    {
                        case R.id.nav_QLsanpham:
                            //bottomNavigationView.setSelectedItemId(R.id.nav_home);
                            getSupportActionBar().setTitle("Quản Lý Sản Phẩm");
                            setFragement(new QuanLySanPhamFragment());
                            item.setChecked(true);
                            return true;

                        case R.id.nav_Qltintuc:
                            //bottomNavigationView.setSelectedItemId(R.id.nav_account);
                            getSupportActionBar().setTitle("Quản Lý Tin Tức");
                            setFragement(new QuanLyTinTucFragment());
                            item.setChecked(true);
                            return true;

                        case  R.id.nav_QLdonhang:
                            getSupportActionBar().setTitle("Quản Lý Đơn Hàng");
                            //bottomNavigationView.setSelectedItemId(R.id.nav_news);
                            setFragement(new QuanLyDonHangFragment());
                            item.setChecked(true);
                            return true;
                    }
                    //item.setChecked(false);
                    return false;

                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        main_frame = findViewById(R.id.main_frame);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Quản Lý Sản Phẩm");

        bottomNavigationView = findViewById(R.id.bottom_nav_bar);
        main_frame = findViewById(R.id.main_frame);

        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

        setFragement(new QuanLySanPhamFragment());

    }

    private void setFragement(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(main_frame.getId(),fragment);
        transaction.commit();

    }

}