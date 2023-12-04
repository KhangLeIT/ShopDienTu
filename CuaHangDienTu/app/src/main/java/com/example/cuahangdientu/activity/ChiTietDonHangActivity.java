package com.example.cuahangdientu.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cuahangdientu.DbQuery;
import com.example.cuahangdientu.R;
import com.example.cuahangdientu.adapter.ChiTietDonHangAdapter;
import com.example.cuahangdientu.adapter.DonHangUserAdapter;
import com.example.cuahangdientu.model.EventBus.TinhTongEvent;
import com.example.cuahangdientu.retrifit.ApiStore;
import com.example.cuahangdientu.retrifit.RetrofitClient;
import com.example.cuahangdientu.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ChiTietDonHangActivity extends AppCompatActivity {
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private ApiStore apiStore;
    private RecyclerView recChiTietDH;
    private TextView txt_tongtien, chitietTrangThai, txtTrangthai;
    private Button btnXacNhan, btnHuyDon;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_don_hang);

        AnhXaView();
        ActionToolbar();
        loadAdapterDonHang();
        ActionButton();

    }

    private void ActionButton() {
        btnXacNhan.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
            builder.setTitle("Thông Báo");
            builder.setMessage("Xác Nhận Đơn Hàng Đã Đến Tay Bạn?");
            builder.setPositiveButton("đồng ý", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    ActionDonHang("Đã Nhận");
                }
            });
            builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            builder.show();

        });
        btnHuyDon.setOnClickListener(view ->{
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
            builder.setTitle("Thông Báo");
            builder.setMessage("Bạn Muốn Hủy Đơn Hàng Này?");
            builder.setPositiveButton("đồng ý", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String str= "Đã Hủy";
                    ActionDonHang(str);

                }
            });
            builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            builder.show();
        });
    }

    private void ActionDonHang(String str) {
        String date = DbQuery.getNgayGio();
        int iddonhang = DbQuery.itemDonHangList.get(0).getIddonhang();
        int trangthai = DbQuery.chitiet_trangthai_donhang;
        compositeDisposable.add(apiStore.actionuserdonhang(trangthai,Utils.user_current.getIduser(),iddonhang , date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        donHangModel -> {
                            if (donHangModel.isSuccess())
                            {
                                String dateHuy = str + "/ "+ date;
                                chitietTrangThai.setText(dateHuy);
                            }
                        },
                        throwable -> {
                            Toast.makeText(this, "no connect" + DbQuery.chitiet_trangthai_donhang, Toast.LENGTH_SHORT).show();
                        }
                ));
    }


    private void loadAdapterDonHang() {
        if(DbQuery.itemDonHangList.size() > 0)
        {
            getSupportActionBar().setTitle("Đơn Hàng " + DbQuery.itemDonHangList.get(DbQuery.itemDonHangList.size()-1).getIddonhang());
            ChiTietDonHangAdapter adapter = new ChiTietDonHangAdapter(getApplicationContext(), DbQuery.itemDonHangList);
            recChiTietDH.setAdapter(adapter);

            float ttien = DbQuery.chitet_donhang_tongtien;
            DecimalFormat formatter = new DecimalFormat("###,###,###");
            txt_tongtien.setText(formatter.format(ttien) + " VNĐ");

        }
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
        toolbar = findViewById(R.id.toolbar);
        txt_tongtien = findViewById(R.id.chitiet_TongTien);
        apiStore = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiStore.class);
        recChiTietDH = findViewById(R.id.rec_chiTietDH);
        btnHuyDon = findViewById(R.id.chitiet_huydon);
        btnXacNhan = findViewById(R.id.chitiet_xacnhan);
        chitietTrangThai = findViewById(R.id.chitiet_trangthai);
        txtTrangthai = findViewById(R.id.chitiet_txtTrangThai);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recChiTietDH.setLayoutManager(layoutManager);

        btnHuyDon.setVisibility(View.GONE);
        btnXacNhan.setVisibility(View.GONE);

        if(DbQuery.chitiet_trangthai_donhang == 0)
        {
            btnHuyDon.setVisibility(View.VISIBLE);
        }

        if(DbQuery.chitiet_trangthai_donhang == 2)
        {
            btnXacNhan.setVisibility(View.VISIBLE);
        }


        if(DbQuery.chitiet_trangthai_donhang ==-1 && DbQuery.chitiet_donhang_ngayhoanthanh != null  )
        {
            txtTrangthai.setText("Đã Hủy");
            txtTrangthai.setTextColor(Color.RED);
            String str = DbQuery.formatDate(DbQuery.chitiet_donhang_ngayhoanthanh);
            chitietTrangThai.setText(str);
            chitietTrangThai.setTextColor(Color.RED);
        }
        else if(DbQuery.chitiet_trangthai_donhang == 3 && DbQuery.chitiet_donhang_ngayhoanthanh != null)
        {
            txtTrangthai.setText("Đã Nhận");
            txtTrangthai.setTextColor(Color.BLUE);
            String str = DbQuery.formatDate(DbQuery.chitiet_donhang_ngayhoanthanh);
            chitietTrangThai.setText(str);
            chitietTrangThai.setTextColor(Color.BLUE);
        }
        else{
            if(DbQuery.chitiet_trangthai_donhang == 0)
            {
                chitietTrangThai.setText("Đang Chờ Duyệt");
                chitietTrangThai.setTextColor(Color.YELLOW);
            }
            else if( DbQuery.chitiet_trangthai_donhang ==1)
            {
                chitietTrangThai.setText("Đã duyệt");
                chitietTrangThai.setTextColor(Color.GREEN);
            }
            else if(DbQuery.chitiet_trangthai_donhang == 2)
            {
                chitietTrangThai.setText("Đang Giao Hàng");
                chitietTrangThai.setTextColor(Color.YELLOW);
            }
            else
            {
                chitietTrangThai.setText("Chưa hoàn Thành");
            }
        }

    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}