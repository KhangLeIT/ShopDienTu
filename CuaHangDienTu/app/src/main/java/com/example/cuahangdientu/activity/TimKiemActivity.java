package com.example.cuahangdientu.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cuahangdientu.R;
import com.example.cuahangdientu.adapter.DonHangUserAdapter;
import com.example.cuahangdientu.adapter.SanPhamTheoDMucAdapter;
import com.example.cuahangdientu.model.SanPhamMoi;
import com.example.cuahangdientu.model.SanPhamMoiModel;
import com.example.cuahangdientu.retrifit.ApiStore;
import com.example.cuahangdientu.retrifit.RetrofitClient;
import com.example.cuahangdientu.utils.Utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class TimKiemActivity extends AppCompatActivity {

    private RecyclerView rec_timkiem;
    private Toolbar toolbar;
    private AppCompatEditText edTimKiem;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private ApiStore apiStore;
    private SanPhamTheoDMucAdapter timKiemAdapter;
    private List<SanPhamMoi> sanPhamMoiList = new ArrayList<>();
    private DrawerLayout drawerLayout;
    private ImageView imgtimkiemgia;
    private ProgressBar progressBar;
    private AppCompatButton btnTimKemLoc;
    private TextView txtThongbao;

    private int tk_gia1 = 0;
    private int tk_gia2 = Integer.MAX_VALUE;
    private boolean select_gia = false;

    private int iddm = 0;
    private  boolean select_dmuc = false;

    private String sapxep = "ASC"; // tang // DESC giam


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tim_kiem);

        AnhXaView();
        progressBar.setVisibility(View.GONE);
        txtThongbao.setVisibility(View.GONE);
        ActionToobar();
        ActionTimkiemsanpham();
        ActionBar();

        btnTimKemLoc.setOnClickListener(view -> {
            TimKiemTheoBoLoc();
        });
    }

    private void TimKiemTheoBoLoc() {
        drawerLayout.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        txtThongbao.setVisibility(View.GONE);
        if(sanPhamMoiList != null)
        {
            sanPhamMoiList.clear();
        }
        String idloc;
        if(iddm == 0)
        {
            idloc = ">" +iddm;
        }
        else
        {
            idloc = "=" +iddm;
        }
        compositeDisposable.add(apiStore.timkiemtheoboloc(tk_gia1, tk_gia2,idloc, sapxep)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        sanPhamMoiModel -> {
                            if (sanPhamMoiModel.isSuccess())
                            {
                                sanPhamMoiList = sanPhamMoiModel.getResult();
                                timKiemAdapter = new SanPhamTheoDMucAdapter(getApplicationContext(), sanPhamMoiList);
                                rec_timkiem.setAdapter(timKiemAdapter);
                                progressBar.setVisibility(View.GONE);
                                imgtimkiemgia.setImageResource(R.drawable.ic_search);
                            }
                            else
                            {
                                sanPhamMoiList= new ArrayList<>();
                                timKiemAdapter = new SanPhamTheoDMucAdapter(getApplicationContext(), sanPhamMoiList);
                                rec_timkiem.setAdapter(timKiemAdapter);
                                progressBar.setVisibility(View.GONE);
                                setTxtThongbao("Hiện Tại Chưa Có Sản Phẩm Theo Yêu Cầu");
                            }
                        }
                        ,
                        throwable -> {
                            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
                        }
                ));
    }

    private void setTxtThongbao(String str)
    {
        txtThongbao.setText(str);
        txtThongbao.setVisibility(View.VISIBLE);
    }

    private void ActionTimkiemsanpham() {

        edTimKiem.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(charSequence.toString().trim().length() > 3)
                {
                    getDataTimKiem(charSequence.toString().trim());

                }
                else{
                    sanPhamMoiList = new ArrayList<>();
                    timKiemAdapter = new SanPhamTheoDMucAdapter(getApplicationContext(), sanPhamMoiList);
                    rec_timkiem.setAdapter(timKiemAdapter);
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void getDataTimKiem(String str) {
        progressBar.setVisibility(View.VISIBLE);
        txtThongbao.setVisibility(View.GONE);
        if(sanPhamMoiList != null)
        {
            sanPhamMoiList.clear();
        }
        compositeDisposable.add(apiStore.timkiemsanpham(str, sapxep)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        sanPhamMoiModel -> {
                            if (sanPhamMoiModel.isSuccess())
                            {
                                sanPhamMoiList = sanPhamMoiModel.getResult();
                                timKiemAdapter = new SanPhamTheoDMucAdapter(getApplicationContext(), sanPhamMoiList);
                                rec_timkiem.setAdapter(timKiemAdapter);
                                progressBar.setVisibility(View.GONE);
                            }
                            else
                            {
                                sanPhamMoiList = new ArrayList<>();
                                timKiemAdapter = new SanPhamTheoDMucAdapter(getApplicationContext(), sanPhamMoiList);
                                rec_timkiem.setAdapter(timKiemAdapter);
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                        ,
                        throwable -> {
                            sanPhamMoiList = new ArrayList<>();
                            timKiemAdapter = new SanPhamTheoDMucAdapter(getApplicationContext(), sanPhamMoiList);
                            rec_timkiem.setAdapter(timKiemAdapter);
                            progressBar.setVisibility(View.GONE);
                        }
                ));
    }

    private void ActionToobar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Tìm Kiếm");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void ActionBar() {
        imgtimkiemgia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(drawerLayout.getVisibility() == View.GONE)
                {
                    imgtimkiemgia.setImageResource(R.drawable.ic__search_off_24);
                    drawerLayout.setVisibility(View.VISIBLE);
                }
                else
                {
                    imgtimkiemgia.setImageResource(R.drawable.ic_search);
                    drawerLayout.setVisibility(View.GONE);
                }

            }
        });

    }

    private void AnhXaView() {
        apiStore = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiStore.class);

        toolbar = findViewById(R.id.toolbar);
        rec_timkiem = findViewById(R.id.rec_timkiem);
        edTimKiem = findViewById(R.id.timkiem_text);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rec_timkiem.setLayoutManager(layoutManager);
        progressBar = findViewById(R.id.progressBar);

        drawerLayout = findViewById(R.id.drawerLayout);
        imgtimkiemgia = findViewById(R.id.img_timkiem_gia);

        btnTimKemLoc = findViewById(R.id.btn_TimKiemLoc);

        txtThongbao = findViewById(R.id.txtThongbao);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    @SuppressLint("NonConstantResourceId")
    public void selectGiaRadioButton(View view) {
        boolean checked =((RadioButton) view).isChecked();
        switch (view.getId())
        {
            case R.id.tk_gia_all:
                if(checked){
                    tk_gia1 = 0;
                    tk_gia2 = Integer.MAX_VALUE;
                    select_gia = true;
                }
                break;
            case R.id.tk_gia_duoi5:
                if(checked)
                {
                    tk_gia1 = 0;
                    tk_gia2 = 5000001;
                    select_gia = true;
                }
                break;
            case R.id.tk_gia_5den10:
                if(checked){
                    tk_gia1 = 5000000;
                    tk_gia2 = 10000001;
                    select_gia = true;
                }
                break;
            case R.id.tk_gia_10den20:
                if(checked){
                    tk_gia1 = 10000000;
                    tk_gia2 = 20000001;
                    select_gia = true;
                }
                break;
            case R.id.tk_gia_tren20:
                if(checked){
                    tk_gia1 = 20000000;
                    tk_gia2 = Integer.MAX_VALUE;
                    select_gia = true;
                }
                break;
            default:
                tk_gia1 = 0;
                tk_gia2 = Integer.MAX_VALUE;
                break;
        }

    }

    @SuppressLint("NonConstantResourceId")
    public void selectDanhMucRadioButton(View view) {
        boolean checked =((RadioButton) view).isChecked();
        switch (view.getId()){
            case R.id.tk_dm_all:
                if(checked)
                {
                    iddm = 0;
                }
                break;
            case R.id.tk_dm_dienthoai:
                if(checked)
                {
                    iddm = 1;
                }
                break;
            case R.id.tk_dm_laptop:
                if(checked)
                {
                    iddm = 2;
                }
                break;
            case R.id.tk_dm_dongho:
                if(checked)
                {
                    iddm = 3;
                }
                break;
            case R.id.tk_dm_tainghe:
                if(checked)
                {
                    iddm = 4;
                }
                break;
            default:
                iddm = 0;
                break;
        }

    }

    @SuppressLint("NonConstantResourceId")
    public void selectSapXepRadioButton(View view) {
        boolean checked =((RadioButton) view).isChecked();
        switch (view.getId())
        {
            case R.id.tk_sx_tang:
                if(checked){
                    sapxep = "ASC"; // tang
                }
                break;
            case R.id.tk_sx_giam:
                if(checked){
                    sapxep ="DESC"; // giam
                }
                break;
            default:
                sapxep ="DESC";
                break;
        }

    }
}