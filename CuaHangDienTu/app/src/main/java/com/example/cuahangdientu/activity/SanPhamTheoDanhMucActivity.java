package com.example.cuahangdientu.activity;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.cuahangdientu.DbQuery;
import com.example.cuahangdientu.R;
import com.example.cuahangdientu.adapter.LoaiSpHomeAdapter;
import com.example.cuahangdientu.adapter.SanPhamTheoDMucAdapter;
import com.example.cuahangdientu.model.SanPhamMoi;
import com.example.cuahangdientu.retrifit.ApiStore;
import com.example.cuahangdientu.retrifit.RetrofitClient;
import com.example.cuahangdientu.utils.Utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SanPhamTheoDanhMucActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView rec_spTheoDm;
    private NotificationBadge cartSL;
    private FloatingActionButton shopCartClick;
    private ProgressBar progressBar;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private ApiStore apiStore;
    private int iddm = DbQuery.select_id_dmsp;
    private int page = 1;

    private SanPhamTheoDMucAdapter sanPhamTheoDMucAdapter;
    private List<SanPhamMoi> sanPhamDM;

    private LinearLayoutManager linearLayoutManager ;
    private Handler handler = new Handler();
    private boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_muc_san_pham);

        apiStore = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiStore.class);


        AnhXaView();
        AcctionToobar();
        getData(page);
        addEventLoad();

    }

    private void addEventLoad() {

        rec_spTheoDm.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if(linearLayoutManager.findLastCompletelyVisibleItemPosition()==sanPhamDM.size()-1){
                    isLoading = true;
                    loadMore();
                }
            }
        });
    }

    private void loadMore() {
        handler.post(new Runnable() {
            @Override
            public void run() {


            }
        });

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //remove null;
                page = page+1;
                getData(page);
                sanPhamTheoDMucAdapter.notifyDataSetChanged();
                isLoading = false;
            }
        },2000);

    }


    private void getData(int page) {
        progressBar.setVisibility(View.VISIBLE);
        compositeDisposable.add(apiStore.sanphamdanhmuc(page, iddm)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                         sanPhamMoiModel -> {
                            if (sanPhamMoiModel.isSuccess())
                            {
                                if(sanPhamTheoDMucAdapter  == null)
                                {
                                    sanPhamDM = sanPhamMoiModel.getResult();
                                    sanPhamTheoDMucAdapter = new SanPhamTheoDMucAdapter(this, sanPhamDM);
                                    rec_spTheoDm.setAdapter(sanPhamTheoDMucAdapter);
                                    progressBar.setVisibility(View.GONE);
                                }
                                else
                                {
                                    int vt = sanPhamDM.size() -1;
                                    int sl = sanPhamMoiModel.getResult().size();
                                    for(int i = 0; i<sl; i++)
                                    {
                                        sanPhamDM.add(sanPhamMoiModel.getResult().get(i));
                                    }
                                    sanPhamTheoDMucAdapter.notifyItemRangeInserted(vt, sl);
                                    progressBar.setVisibility(View.GONE);
                                }
                            }
                            else{
                                //Toast.makeText(this, "hết san pham", Toast.LENGTH_SHORT).show();
                                isLoading = true;
                                progressBar.setVisibility(View.GONE);
                            }
                        },throwable -> Log.e(TAG, "no connect with server " + throwable.getMessage())
                )
        );
    }

    private void AcctionToobar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(DbQuery.select_ten_danhmuc);
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
        rec_spTheoDm = findViewById(R.id.rec_dmuc_sanpham);
        cartSL = findViewById(R.id.cart_soluong);
        shopCartClick = findViewById(R.id.shoppingCart_dmsp);
        progressBar = findViewById(R.id.progressBar);
        //RecyclerView.LayoutManager layoutSanPham = new GridLayoutManager(this, 2);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rec_spTheoDm.setLayoutManager(linearLayoutManager);
        sanPhamDM = new ArrayList<>();

        if(DbQuery.mangGioHang != null)
        {
            cartSL.setText(String.valueOf(DbQuery.mangGioHang.size()));
        }

        shopCartClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GioHangActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if(DbQuery.mangGioHang != null)
        {
            cartSL.setText(String.valueOf(DbQuery.mangGioHang.size()));
        }
        else
            cartSL.setText(String.valueOf(0));
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}