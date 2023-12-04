package com.example.cuahangdientu.activity;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cuahangdientu.DbQuery;
import com.example.cuahangdientu.R;
import com.example.cuahangdientu.adapter.ThongBaoDonHangAdapter;
import com.example.cuahangdientu.model.ThongBao;
import com.example.cuahangdientu.retrifit.ApiStore;
import com.example.cuahangdientu.retrifit.RetrofitClient;
import com.example.cuahangdientu.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ThongBaoDonHangActivity extends AppCompatActivity {

    RecyclerView rec_ThongBao;
    ApiStore apiStore;
    Toolbar toolbar;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    List<ThongBao> thongBaoList = new ArrayList<>();
    ThongBaoDonHangAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_bao_don_hang);

        AnhXaView();
        ActionToolbar();
        getThongBaoDonHang();

    }

    private void getThongBaoDonHang() {
        compositeDisposable.add(apiStore.getThongBaoUser(Utils.user_current.getIduser())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        thongBaoModel -> {
                            if(thongBaoModel.isSuccess()){
                                thongBaoList = thongBaoModel.getResult();
                                DbQuery.listThongBao = thongBaoList;
                                adapter = new ThongBaoDonHangAdapter(this, thongBaoList);
                                rec_ThongBao.setAdapter(adapter);
                            }
                            else{

                            }
                        }
                       ,throwable -> Log.e(TAG, "no connect with server " + throwable.getMessage())
                )
        );
    }
    private void ActionToolbar() {
        setSupportActionBar(toolbar);
        //toolbar.setTitle("Đơn Hàng");
        getSupportActionBar().setTitle("Đơn Hàng");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void AnhXaView() {
        apiStore = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiStore.class);
        rec_ThongBao = findViewById(R.id.rec_ThongBaoDonHang);
        toolbar = findViewById(R.id.toolbar);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rec_ThongBao.setLayoutManager(layoutManager);

    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}