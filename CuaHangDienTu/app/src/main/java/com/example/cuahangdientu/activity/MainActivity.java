package com.example.cuahangdientu.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cuahangdientu.DbQuery;
import com.example.cuahangdientu.R;
import com.example.cuahangdientu.databinding.ActivityMainBinding;
import com.example.cuahangdientu.activity.fragment.AccountFragment;
import com.example.cuahangdientu.activity.fragment.HomeFragment;
import com.example.cuahangdientu.activity.fragment.NewsFragment;
import com.example.cuahangdientu.utils.Utils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private FrameLayout main_frame;
    private BottomNavigationView bottomNavigationView;
    private TextView userName, userEmail;
    private ImageView img_user;




    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @SuppressLint("NonConstantResourceId")
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId())
                    {
                        case R.id.nav_home:
                            //bottomNavigationView.setSelectedItemId(R.id.nav_home);
                            getSupportActionBar().setTitle("Home");
                            setFragement(new HomeFragment());
                            item.setChecked(true);
                            return true;

                        case R.id.nav_account:
                            //bottomNavigationView.setSelectedItemId(R.id.nav_account);
                            setFragement(new AccountFragment());
                            item.setChecked(true);
                            return true;

                        case  R.id.nav_news:
                            //bottomNavigationView.setSelectedItemId(R.id.nav_news);
                            setFragement(new NewsFragment());
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

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Home");

        bottomNavigationView = findViewById(R.id.bottom_nav_bar);
        main_frame = findViewById(R.id.main_frame);

        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_account, R.id.nav_news)
                .setOpenableLayout(drawer)
                .build();



//        NavController navController = Navigation.findNavController(this, R.id.nav_home);
//        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
//        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.setNavigationItemSelectedListener(this);

        AnhXaView();
        ActionBar();
        setFragement(new HomeFragment());
        setThongTinUser();

    }

    private void AnhXaView() {

        userEmail = binding.navView.getHeaderView(0).findViewById(R.id.user_email);
        userName = binding.navView.getHeaderView(0).findViewById(R.id.user_name);
        img_user = binding.navView.getHeaderView(0).findViewById(R.id.user_anhdaidien);
    }

    private void setThongTinUser() {
        if(Utils.user_current != null)
        {
            if(Utils.user_current.getGioitinh() == 0)
            {
                img_user.setImageResource(R.drawable.user_icon_nu);
            }
            else if(Utils.user_current.getGioitinh() == 1)
            {
                img_user.setImageResource(R.drawable.user_icon_nam);
            }
            userEmail.setText(Utils.user_current.getEmail());
            userName.setText(Utils.user_current.getTenuser());
        }

    }

    private void setFragement(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(main_frame.getId(),fragment);
        transaction.commit();

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

    @Override
    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_home);
//        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
//                || super.onSupportNavigateUp();

        return super.onSupportNavigateUp();
    }


    private void ActionBar() {
        setSupportActionBar(binding.appBarMain.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.appBarMain.toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        binding.appBarMain.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.nav_home){
            getSupportActionBar().setTitle("Home");
            setFragement(new HomeFragment());

        }else if (id == R.id.nav_account){
            getSupportActionBar().setTitle("My Account");
            setFragement(new AccountFragment());

        }else if (id == R.id.nav_news){
            getSupportActionBar().setTitle("Tin Tuc");
            setFragement(new NewsFragment());
        }
        else if (id == R.id.nav_donhang) {
            DbQuery.trangthai_hoanthanh = 0;
            Intent intent = new Intent(getApplicationContext(), DonHangUserActivity.class);
            startActivity(intent);
        }
        else if(id == R.id.nav_thongbao){
            Intent intent = new Intent(getApplicationContext(), ThongBaoDonHangActivity.class);
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private boolean isConnected (Context context)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if(wifi != null && wifi.isConnected() || mobile != null && mobile.isConnected())
        {
            return true;
        }
        else{
            return false;
        }

    }

    @Override
    protected void onResume() {
        if(Utils.user_current == null){
            Intent i  = new Intent(getApplicationContext(), BeginActivity.class);
            startActivity(i);
            finish();
        }
        setThongTinUser();
        super.onResume();
    }
}