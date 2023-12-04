package com.example.admincuahangdientu.tintuc;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.admincuahangdientu.R;
import com.example.admincuahangdientu.adapter.SanPhamTheoDMucAdapter;
import com.example.admincuahangdientu.adapter.TinTucTheoDanhMucAdapter;
import com.example.admincuahangdientu.model.SanPham;
import com.example.admincuahangdientu.model.TinTuc;
import com.example.admincuahangdientu.query.DbQuery;
import com.example.admincuahangdientu.retrifit.APIadminStore;
import com.example.admincuahangdientu.retrifit.RetrofitClient;
import com.example.admincuahangdientu.sanpham.DanhMucSanPhamActivity;
import com.example.admincuahangdientu.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DanhMucTinTucActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView rec_spTheoDm;
    private ProgressBar progressBar;

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private APIadminStore apiStore;
    private int iddm = DbQuery.select_id_TT;
    private int page = 1;


    private LinearLayoutManager linearLayoutManager ;
    private Handler handler = new Handler();
    private boolean isLoading = false;

    private LinearLayout backDelete;
    private int vtspback = 0;

    private List<TinTuc> tintucDM = new ArrayList<>();
    private TinTucTheoDanhMucAdapter tinTucTheoDanhMucAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_muc_tin_tuc);

        apiStore = RetrofitClient.getInstance(Utils.BASE_URL).create(APIadminStore.class);
        AnhXaView();
        AcctionToobar();
        getData(page);
        addEventLoad();

        backDelete.setOnClickListener(view ->{
            ActionBackDelete();
        });
    }

    private void ActionBackDelete() {
        List<TinTuc> listTTBack = DbQuery.tinTucBack;
        if(listTTBack.size() == 0){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Không có Tin Tức để Back");
            builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            builder.show();
        }
        else{
            TinTuc ttback = DbQuery.tinTucBack.get(DbQuery.tinTucBack.size()-1);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Bạn muôn back tin túc này ?");
            builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String thoigianthem = ttback.getThoigianthem();
                    backTinTuc(ttback.getId(), ttback.getTieude(), ttback.getHinhanh(), ttback.getIddm(), ttback.getIdsp(), ttback.getLuotxem(), ttback.getNoidung(), thoigianthem);

                }
            });
            builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });
            builder.show();
        }
    }

    private void backTinTuc(int id, String tieude, String hinhanh, int iddm, int idsp, int luotxem, String noidung, String thoigianthem) {
        compositeDisposable.add(apiStore.backTinTuc("backDelete",id,tieude,hinhanh,noidung,iddm,luotxem,idsp,thoigianthem)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        messageModel -> {
                            if(messageModel.isSuccess()){
                                DbQuery.tinTucBack.remove(DbQuery.tinTucBack.size()-1);
                                Intent intent = new Intent(this, DanhMucTinTucActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        },
                        throwable -> {
                            Log.d("error", throwable.getMessage());
                        }
                )
        );
    }

    private void AcctionToobar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Xóa Sửa Sản Phẩm");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
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

                if(linearLayoutManager.findLastCompletelyVisibleItemPosition() == tintucDM.size()-1){
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
        compositeDisposable.add(apiStore.getTinTucTheoDM("getTinTuc" ,page, iddm)
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
                                    rec_spTheoDm.setAdapter(tinTucTheoDanhMucAdapter);

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

    @Override
    protected void onResume() {
        super.onResume();
        if(DbQuery.tintuc_edit_change_YesOrNo){
            int idTT = DbQuery.select_id_TT;
            int idKtra = DbQuery.select_edit_tintuc.getIddm();
            if(idTT == idKtra){
                tintucDM.set(DbQuery.position_edit_tintuc, DbQuery.select_edit_tintuc);
                tinTucTheoDanhMucAdapter.notifyDataSetChanged();
                DbQuery.tintuc_edit_change_YesOrNo = false;
            }
            else{
                tintucDM.remove(DbQuery.position_edit_tintuc);
                tinTucTheoDanhMucAdapter.notifyDataSetChanged();
                DbQuery.tintuc_edit_change_YesOrNo = false;            }
        }
    }

    private void AnhXaView() {
        toolbar = findViewById(R.id.toolbar);
        rec_spTheoDm = findViewById(R.id.rec_dmuc_Tintuc);

        progressBar = findViewById(R.id.progressBar);
        //RecyclerView.LayoutManager layoutSanPham = new GridLayoutManager(this, 2);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rec_spTheoDm.setLayoutManager(linearLayoutManager);
        tintucDM = new ArrayList<>();

        backDelete = findViewById(R.id.backDelete);

    }
}