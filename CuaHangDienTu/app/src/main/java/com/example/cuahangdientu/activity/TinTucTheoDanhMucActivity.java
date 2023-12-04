package com.example.cuahangdientu.activity;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
import com.example.cuahangdientu.adapter.SanPhamTheoDMucAdapter;
import com.example.cuahangdientu.adapter.TinTucTheoDanhMucAdapter;
import com.example.cuahangdientu.model.TinTuc;
import com.example.cuahangdientu.retrifit.ApiStore;
import com.example.cuahangdientu.retrifit.RetrofitClient;
import com.example.cuahangdientu.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class TinTucTheoDanhMucActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView rec_ttTheoDm;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private ApiStore apiStore;
    private LinearLayoutManager linearLayoutManager ;
    private Handler handler = new Handler();
    private ProgressBar progressBar;
    private boolean isLoading = false;
    private int iddm = DbQuery.select_id_dmtt;
    private int page = 1;

    private List<TinTuc> tintucDM = new ArrayList<>();
    private TinTucTheoDanhMucAdapter tinTucTheoDanhMucAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tin_tuc_theo_danh_muc);

        AnhXaView();
        progressBar.setVisibility(View.VISIBLE);
        AcctionToobar();
        getData(page);
        addEventLoad();

    }

    private void addEventLoad() {

        rec_ttTheoDm.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if(linearLayoutManager.findLastCompletelyVisibleItemPosition()==tintucDM.size()-1){
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
                tinTucTheoDanhMucAdapter.notifyDataSetChanged();
                isLoading = false;
            }
        },2000);

    }

    private void getData(int page) {
        progressBar.setVisibility(View.VISIBLE);
        compositeDisposable.add(apiStore.getTinTucTheoDM(6,page, iddm)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        tinTucModel -> {
                            if (tinTucModel.isSuccess())
                            {
                                if(tinTucTheoDanhMucAdapter  == null)
                                {
                                    tintucDM = tinTucModel.getResult();
                                    tinTucTheoDanhMucAdapter = new TinTucTheoDanhMucAdapter(this, tintucDM);
                                    rec_ttTheoDm.setAdapter(tinTucTheoDanhMucAdapter);

                                    progressBar.setVisibility(View.GONE);
                                }
                                else
                                {
                                    int vt = tintucDM.size() -1;
                                    int sl = tinTucModel.getResult().size();
                                    for(int i = 0; i<sl; i++)
                                    {
                                        tintucDM.add(tinTucModel.getResult().get(i));
                                    }
                                    tinTucTheoDanhMucAdapter.notifyItemRangeInserted(vt, sl);

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
        apiStore = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiStore.class);
        toolbar = findViewById(R.id.toolbar);
        rec_ttTheoDm = findViewById(R.id.rec_dmuc_tintuc);
        progressBar = findViewById(R.id.progressBar);

        //RecyclerView.LayoutManager layoutSanPham = new GridLayoutManager(this, 2);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rec_ttTheoDm.setLayoutManager(linearLayoutManager);
        tintucDM = new ArrayList<>();

    }
}