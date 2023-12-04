package com.example.cuahangdientu.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cuahangdientu.DbQuery;
import com.example.cuahangdientu.R;
import com.example.cuahangdientu.retrifit.ApiStore;
import com.example.cuahangdientu.retrifit.RetrofitClient;
import com.example.cuahangdientu.utils.Utils;
import com.google.gson.Gson;

import java.text.DecimalFormat;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ThanhToanActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView tongTien, sdt, username;
    private EditText diachi;
    private Button datHangNgay;
    private ProgressBar progressBar;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiStore apiStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan);

        AnhXaView();
        initControl();
    }

    private void initControl() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Thanh Toán");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        float tongsotien = DbQuery.giatri_tongtien_donhang;
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        tongTien.setText(formatter.format( tongsotien)+ " VNĐ");
        sdt.setText(Utils.user_current.getSdt());
        username.setText(Utils.user_current.getTenuser());

        datHangNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strDiaChi = diachi.getText().toString().trim();
                if(TextUtils.isEmpty(strDiaChi))
                {
                    Toast.makeText(ThanhToanActivity.this, "Bạn Chưa Nhập Địa Chỉ", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                    builder.setTitle("Bạn Muốn Đặt Đơn Hàng Này?");
                    builder.setMessage("\nĐịa Chỉ: "+ diachi.getText().toString().trim()+
                                    "\nSĐT nhận: " +Utils.user_current.getSdt()
                            +"\nTên Người Nhận: "+ Utils.user_current.getTenuser());
                    builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActionDatHangNgay();
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
        });
    }

    private void ActionDatHangNgay() {
        progressBar.setVisibility(View.VISIBLE);
        String str_email = Utils.user_current.getEmail();
        String str_sdt = Utils.user_current.getSdt();
        String str_diachi = diachi.getText().toString().trim();
        int iduser = Utils.user_current.getIduser();
        int sl = DbQuery.mangGioHang.size();
        float tongtien = DbQuery.giatri_tongtien_donhang;
        String date = String.valueOf(DbQuery.getNgayGio());

        compositeDisposable.add(apiStore.taoDonHang(iduser,str_diachi,str_sdt,sl,tongtien,new Gson().toJson(DbQuery.mangGioHang),date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        userStoreModel -> {
                            DbQuery.mangGioHang.clear();
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(), "Thành Công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                        },
                        throwable -> {
                            Toast.makeText(ThanhToanActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                )
        );
    }


    private void AnhXaView() {
        apiStore = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiStore.class);
        toolbar = findViewById(R.id.toolbar);
        tongTien = findViewById(R.id.thanhtoan_tongtien);
        sdt = findViewById(R.id.thanhtoan_SDT);
        username = findViewById(R.id.thanhtoan_TenUser);
        diachi = findViewById(R.id.thanhtoan_diachi);
        datHangNgay = findViewById(R.id.thanhtoan_DatHang);

        progressBar = findViewById(R.id.progressBar);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}