package com.example.cuahangdientu.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cuahangdientu.DbQuery;
import com.example.cuahangdientu.R;
import com.example.cuahangdientu.adapter.DonHangUserAdapter;
import com.example.cuahangdientu.retrifit.ApiStore;
import com.example.cuahangdientu.retrifit.RetrofitClient;
import com.example.cuahangdientu.utils.Utils;
import com.google.gson.Gson;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DonHangUserActivity extends AppCompatActivity {
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private ApiStore apiStore;
    private RecyclerView recDonHang;
    private Toolbar toolbar;
    private TextView donhangEmpty;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_don_hang_user);


        AnhXaView();
        progressBar.setVisibility(View.GONE);
        ActionToolbar();
        LoadDonHang();
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Đơn Hàng Của Bạn");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void AnhXaView() {
        toolbar = findViewById(R.id.toolbar);
        donhangEmpty = findViewById(R.id.donhangEmpty);
        apiStore = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiStore.class);
        recDonHang = findViewById(R.id.rec_donhangUser);
        progressBar = findViewById(R.id.progressBar);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recDonHang.setLayoutManager(layoutManager);


    }

    private void LoadDonHang() {
        progressBar.setVisibility(View.VISIBLE);
        compositeDisposable.add(apiStore.donhanguser(Utils.user_current.getIduser(), DbQuery.trangthai_hoanthanh)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                   donHangModel -> {
                      if(donHangModel.isSuccess())
                      {
                          DonHangUserAdapter adapter = new DonHangUserAdapter(getApplicationContext(), donHangModel.getResult());
                          recDonHang.setAdapter(adapter);
                          progressBar.setVisibility(View.GONE);
                      }
                      else{
                          donhangEmpty.setVisibility(View.VISIBLE);
                          progressBar.setVisibility(View.GONE);
                      }
                   } ,
                   throwable -> {
                       Toast.makeText(this, "no connect", Toast.LENGTH_SHORT).show();
                   }
                ));
    }

    @Override
    protected void onResume() {
        LoadDonHang();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}