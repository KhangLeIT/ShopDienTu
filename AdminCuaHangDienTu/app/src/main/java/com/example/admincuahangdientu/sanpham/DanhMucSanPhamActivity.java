package com.example.admincuahangdientu.sanpham;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
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
import com.example.admincuahangdientu.model.SanPham;
import com.example.admincuahangdientu.query.DbQuery;
import com.example.admincuahangdientu.retrifit.APIadminStore;
import com.example.admincuahangdientu.retrifit.RetrofitClient;
import com.example.admincuahangdientu.utils.Utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DanhMucSanPhamActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView rec_spTheoDm;
    private ProgressBar progressBar;

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private APIadminStore apiStore;
    private int iddm = DbQuery.select_id_dmsp;
    private int page = 1;

    private SanPhamTheoDMucAdapter sanPhamTheoDMucAdapter;
    private List<SanPham> sanPhamDM;

    private LinearLayoutManager linearLayoutManager ;
    private Handler handler = new Handler();
    private boolean isLoading = false;

    private LinearLayout backDelete;
    private int vtspback = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_muc_san_pham);

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
        List<SanPham> listSpback = DbQuery.sanphamBack;
        if(listSpback.size() == 0)
        {
            ThongBao("Không có sản phẩm để back");
        }
        else{


            SanPham spback = listSpback.get(listSpback.size()-1);
            if(spback.getLinkvideo() == null) spback.setLinkvideo("NULL");

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Bạn Muốn Back Sản Phẩm Này?");
            builder.setMessage("\nTên: "+spback.getTensp() +"\nGiá: " + String.valueOf(spback.getGiabansp()));
            builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    backSanPham(spback.getIddm(),spback.getIdsp(), spback.getTensp(), spback.getHinhanhsp(), spback.getGiabansp(),
                            spback.getSlco(), spback.getMotasp(), spback.getLinkvideo(), spback.getThongtinsp());
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

    private void backSanPham(int iddm, int idsp,String tensp, String hinhanhsp, float giabansp, int slco,
                             String motasp, String linkvideo, String thongtinsp) {
        compositeDisposable.add(apiStore.backSanPhamDelete("backdelete",idsp,tensp,hinhanhsp,motasp,thongtinsp,giabansp,iddm,slco,linkvideo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        messageModel -> {
                           if(messageModel.isSuccess()){
                               DbQuery.sanphamBack.remove(DbQuery.sanphamBack.size()-1);
                               Intent intent = new Intent(this, DanhMucSanPhamActivity.class);
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

    private void ThongBao(String str){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(str);
        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();
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

                if(linearLayoutManager.findLastCompletelyVisibleItemPosition() == sanPhamDM.size()-1){
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
                        sanPhamModel -> {
                            if (sanPhamModel.isSuccess())
                            {
                                if(sanPhamTheoDMucAdapter  == null)
                                {
                                    sanPhamDM = sanPhamModel.getResult();
                                    sanPhamTheoDMucAdapter = new SanPhamTheoDMucAdapter(this, sanPhamDM);
                                    rec_spTheoDm.setAdapter(sanPhamTheoDMucAdapter);

                                }
                                else
                                {
                                    int vt = sanPhamDM.size() -1;
                                    int sl = sanPhamModel.getResult().size();
                                    for(int i = 0; i<sl; i++)
                                    {
                                        sanPhamDM.add(sanPhamModel.getResult().get(i));
                                    }
                                    sanPhamTheoDMucAdapter.notifyItemRangeInserted(vt, sl);
                                }

                            }
                            else{
                                //Toast.makeText(this, "hết san pham", Toast.LENGTH_SHORT).show();
                                isLoading = true;
                            }
                            progressBar.setVisibility(View.GONE);
                        },throwable -> Log.e(TAG, "no connect with server " + throwable.getMessage())
                )
        );
    }


    private void AnhXaView() {
        toolbar = findViewById(R.id.toolbar);
        rec_spTheoDm = findViewById(R.id.rec_dmuc_sanpham);

        progressBar = findViewById(R.id.progressBar);
        //RecyclerView.LayoutManager layoutSanPham = new GridLayoutManager(this, 2);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rec_spTheoDm.setLayoutManager(linearLayoutManager);
        sanPhamDM = new ArrayList<>();

        backDelete = findViewById(R.id.backDelete);

    }


    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onResume() {

        if(DbQuery.sanpham_edit_change_YesOrNo){
            int idTT = DbQuery.select_id_dmsp;
            int idKtra = DbQuery.select_edit_sanpham.getIddm();
            if(idTT == idKtra){
                sanPhamDM.set(DbQuery.position_edit_sanpham, DbQuery.select_edit_sanpham);
                sanPhamTheoDMucAdapter.notifyDataSetChanged();
                DbQuery.sanpham_edit_change_YesOrNo = false;
            }
            else{
                sanPhamDM.remove(DbQuery.position_edit_sanpham);
                sanPhamTheoDMucAdapter.notifyDataSetChanged();
                DbQuery.sanpham_edit_change_YesOrNo = false;            }
        }
        super.onResume();
    }
}