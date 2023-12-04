package com.example.cuahangdientu.activity;

import static android.content.ContentValues.TAG;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.cuahangdientu.DbQuery;
import com.example.cuahangdientu.R;
import com.example.cuahangdientu.adapter.HinhAnhListAdapter;
import com.example.cuahangdientu.model.HinhAnhList;
import com.example.cuahangdientu.model.SanPhamMoi;
import com.example.cuahangdientu.model.TinTuc;
import com.example.cuahangdientu.retrifit.ApiStore;
import com.example.cuahangdientu.retrifit.RetrofitClient;
import com.example.cuahangdientu.utils.Utils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class ChiTietTinTucActivity extends AppCompatActivity {
    private Toolbar toolbar;

    private TextView ngaythem, noidung, tieude, luotxem, rec_left, rec_right, tensp, giasp;
    private ImageView sp_img;
    private AppCompatButton btnxemchitiet;
    private LinearLayout layoutSpTT;

    private RecyclerView rec_hinhanh;
    private TinTuc tinTuc = DbQuery.chiTietTinTuc;

    private ApiStore apiStore;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    CountDownTimer countDownTimer = null;

    private List<HinhAnhList> hinhAnhLists = new ArrayList<>();
    private HinhAnhListAdapter hinhAnhListAdapter;

    LinearLayoutManager linearLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_tin_tuc);

        AnhXaView();
        ActionToolbar();
        setData();



        countDownTimer = new CountDownTimer(10000,1000) {
            @Override
            public void onTick(long l) {
            }

            @Override
            public void onFinish() {
                updateLuotXem();
                countDownTimer.cancel();
            }
        };
        countDownTimer.start();


    }

    private void ActionRecyclerView() {
        rec_left.setText("<<");
        rec_right.setText(">>");

        LinearSnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(rec_hinhanh);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(linearLayoutManager.findLastCompletelyVisibleItemPosition() < (hinhAnhListAdapter.getItemCount() -1)){
                    linearLayoutManager.smoothScrollToPosition(rec_hinhanh, new RecyclerView.State(), linearLayoutManager.findLastCompletelyVisibleItemPosition() + 1);
                }
                else {
                    linearLayoutManager.smoothScrollToPosition(rec_hinhanh, new RecyclerView.State(), 0);
                }

            }
        }, 3000,6000);

        rec_left.setOnClickListener(view -> {
            if(linearLayoutManager.findLastCompletelyVisibleItemPosition() > 0){
                linearLayoutManager.smoothScrollToPosition(rec_hinhanh, new RecyclerView.State(), linearLayoutManager.findLastCompletelyVisibleItemPosition() - 1);
            }
            else
            {
                linearLayoutManager.smoothScrollToPosition(rec_hinhanh, new RecyclerView.State(),hinhAnhListAdapter.getItemCount() -1 );
            }
        });

        rec_right.setOnClickListener(view -> {
            if(linearLayoutManager.findLastCompletelyVisibleItemPosition() < (hinhAnhListAdapter.getItemCount() -1)){
                linearLayoutManager.smoothScrollToPosition(rec_hinhanh, new RecyclerView.State(), linearLayoutManager.findLastCompletelyVisibleItemPosition() + 1);
            }
            else {
                linearLayoutManager.smoothScrollToPosition(rec_hinhanh, new RecyclerView.State(), 0);
            }
        });
    }

    private void updateLuotXem()
    {
        if(DbQuery.chiTietTinTuc != null)
        {
            compositeDisposable.add(apiStore.updateTinTuc(2,DbQuery.chiTietTinTuc.getId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            tinTucModel -> {
                                if (tinTucModel.isSuccess())
                                {
                                    DbQuery.chiTietTinTuc.setLuotxem(DbQuery.chiTietTinTuc.getLuotxem()+1);
                                    luotxem.setText(String.valueOf(DbQuery.chiTietTinTuc.getLuotxem()));
                                }
                            }
                            ,throwable -> {
                                Log.e(TAG,  throwable.getMessage());
                            }
                    )
            );
        }
    }
    private void getHinhAnhTinTuc()
    {
        if(DbQuery.chiTietTinTuc != null)
        {
            compositeDisposable.add(apiStore.gethinhanhtintuc(3, DbQuery.chiTietTinTuc.getId())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                     hinhAnhModel -> {
                        if (hinhAnhModel.isSuccess())
                        {
                            hinhAnhLists = hinhAnhModel.getResult();
                            hinhAnhLists.add(0,new HinhAnhList(DbQuery.chiTietTinTuc.getId(), DbQuery.chiTietTinTuc.getHinhanh()));
                            hinhAnhListAdapter = new HinhAnhListAdapter(getApplicationContext(), hinhAnhLists);
                            rec_hinhanh.setAdapter(hinhAnhListAdapter);

                            ActionRecyclerView();
                        }
                        else{
                            if(DbQuery.chiTietTinTuc != null)
                            {
                                hinhAnhLists.add(0,new HinhAnhList(DbQuery.chiTietTinTuc.getId(), DbQuery.chiTietTinTuc.getHinhanh()));
                                hinhAnhListAdapter = new HinhAnhListAdapter(getApplicationContext(), hinhAnhLists);
                                rec_hinhanh.setAdapter(hinhAnhListAdapter);
                            }
                        }

                    }, throwable -> Log.e(TAG, throwable.getMessage())
            ));
        }
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Tin tức");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setData() {
        String date = formatDate(tinTuc.getThoigianthem());
        ngaythem.setText(date);
        noidung.setText(tinTuc.getNoidung());
        luotxem.setText(String.valueOf(tinTuc.getLuotxem()));
        tieude.setText(tinTuc.getTieude());

        getHinhAnhTinTuc();
        getSanPham();

    }

    private void getSanPham() {
        if(DbQuery.chiTietTinTuc.getIdsp() > 0 && DbQuery.chiTietTinTuc != null)
        {
            int idsp = DbQuery.chiTietTinTuc.getIdsp();
            compositeDisposable.add(apiStore.getsanphamtintuc(4,idsp)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            sanPhamMoiModel -> {
                                if(sanPhamMoiModel.isSuccess())
                                {
                                    List<SanPhamMoi> sanPhamMoiList  = sanPhamMoiModel.getResult();
                                    SanPhamMoi q = sanPhamMoiList.get(0);
                                    DbQuery.select_sanpham_chitiet = q;

                                    Glide.with(getApplicationContext()).load(Utils.BASE_URL +"../Hinhanh/" + q.getHinhanhsp()).into(sp_img);
                                    String str = q.getTensp();
                                    if(str.trim().length()>=50)
                                    {
                                        str = str.substring(0,50) + "...";
                                    }
                                    tensp.setText(str);
                                    DecimalFormat formatter = new DecimalFormat("###,###,###");
                                    String gia = String.valueOf(formatter.format(q.getGiabansp()) +" VNĐ");
                                    giasp.setText(gia);

                                }
                            }

                            ,throwable -> Log.e(TAG,  throwable.getMessage())
                    )
            );
        }
        else
        {
            layoutSpTT.setVisibility(View.GONE);
        }
    }

    private void AnhXaView() {
        apiStore = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiStore.class);

        toolbar = findViewById(R.id.toolbar);
        ngaythem = findViewById(R.id.chitiettintuc_ngaythem);
        noidung = findViewById(R.id.chitiettintuc_noidung);
        tieude = findViewById(R.id.chitiettintuc_tieude);
        luotxem = findViewById(R.id.chitiettintuc_luotxem);
        rec_hinhanh = findViewById(R.id.chitiettintuc_recHinhanhlienquan);

        rec_left = findViewById(R.id.rec_left);
        rec_right = findViewById(R.id.rec_right);

        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rec_hinhanh.setLayoutManager(linearLayoutManager);

        sp_img = findViewById(R.id.itemsptt_img);
        giasp = findViewById(R.id.itemsptt_giasp);
        tensp = findViewById(R.id.itemsptt_tensp);
        btnxemchitiet = findViewById(R.id.itemsptt_xemchitiet);

        layoutSpTT = findViewById(R.id.linearSpTinTuc);

        btnxemchitiet.setOnClickListener(view ->{
            Intent intent = new Intent(getApplicationContext(), ChiTietSanPhamActivity.class);
            startActivity(intent);
        });

    }

    private static String formatDate(String str){
        String strDt = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat newFormat = new SimpleDateFormat("EEE, dd-MM-yyyy");
        try {
            Date datetime = format.parse(str);
            strDt = newFormat.format(datetime);
            return strDt;
        } catch (Exception e) {
            return "null";
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        countDownTimer.cancel();
        super.onDestroy();
    }
}