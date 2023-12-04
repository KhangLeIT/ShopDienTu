package com.example.cuahangdientu.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.cuahangdientu.DbQuery;
import com.example.cuahangdientu.R;
import com.example.cuahangdientu.model.GioHang;
import com.example.cuahangdientu.model.SanPhamMoi;
import com.example.cuahangdientu.utils.Utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nex3z.notificationbadge.NotificationBadge;

import java.text.DecimalFormat;

import okhttp3.internal.Util;

public class ChiTietSanPhamActivity extends AppCompatActivity {

    private ImageView imgSanPham;
    private TextView tenSP, giaSP, thongTinSp, motaSP, txtCheckSP;
    private Spinner slSP;
    private Button addCart, btnYoutube;
    private Toolbar toolbar;
    private SanPhamMoi ctsp;
    private NotificationBadge cartSL;
    private FloatingActionButton giohangClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);

        AnhXaView();
        AcctionToobar();
        initData();
        initControl();
        checkSanPham();

        if(ctsp.getLinkvideo() == null)
        {
            btnYoutube.setVisibility(View.GONE);
        }
        DbQuery.link_video = ctsp.getLinkvideo();

    }

    private void initControl() {
        addCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                themVaoGioHang();
            }
        });

        btnYoutube.setOnClickListener(view->{
            Intent intent = new Intent(getApplicationContext(), YoutubeActivity.class);
            startActivity(intent);
        });
    }
    private void checkSanPham()
    {
        for(int i = 0; i<DbQuery.mangGioHang.size();i++)
        {
            if (DbQuery.mangGioHang.get(i).getIdsp() == ctsp.getIdsp())
            {
                txtCheckSP.setVisibility(View.VISIBLE);
                return;
            }
        }
    }

    private void themVaoGioHang() {
        if(DbQuery.mangGioHang.size() >0)
        {
            boolean flag = false;
            int soluong = Integer.parseInt(slSP.getSelectedItem().toString());
            for(int i = 0; i<DbQuery.mangGioHang.size();i++)
            {
                if (DbQuery.mangGioHang.get(i).getIdsp() == ctsp.getIdsp())
                {
                    int tongSL = soluong + DbQuery.mangGioHang.get(i).getSoluong();
                    if(tongSL >= 10)
                    {
                        DbQuery.mangGioHang.get(i).setSoluong(10);
                    }
                    else
                    {
                        DbQuery.mangGioHang.get(i).setSoluong(tongSL);
                    }

                    float gia = ctsp.getGiabansp() * DbQuery.mangGioHang.get(i).getSoluong();
                    DbQuery.mangGioHang.get(i).setGiabansp(gia);
                    flag = true;
                }
            }
            if(flag == false)
            {
                float gia = ctsp.getGiabansp() * soluong;
                GioHang gioHang = new GioHang();
                gioHang.setGiabansp(gia);
                gioHang.setSoluong(soluong);
                gioHang.setGiahientai(ctsp.getGiabansp());
                gioHang.setIdsp(ctsp.getIdsp());
                gioHang.setHinhanhsp(ctsp.getHinhanhsp());
                gioHang.setTensp(ctsp.getTensp());
                DbQuery.mangGioHang.add(gioHang);

                txtCheckSP.setVisibility(View.VISIBLE);
            }

        }else
        {
            int soluong = Integer.parseInt(slSP.getSelectedItem().toString());
            float gia = ctsp.getGiabansp() * soluong;

            GioHang gioHang = new GioHang();
            gioHang.setGiabansp(gia);
            gioHang.setSoluong(soluong);
            gioHang.setIdsp(ctsp.getIdsp());
            gioHang.setHinhanhsp(ctsp.getHinhanhsp());
            gioHang.setGiahientai(ctsp.getGiabansp());
            gioHang.setTensp(ctsp.getTensp());
            DbQuery.mangGioHang.add(gioHang);

            txtCheckSP.setVisibility(View.VISIBLE);
        }
        cartSL.setText(String.valueOf(DbQuery.mangGioHang.size()));
    }

    private void initData() {
        ctsp = DbQuery.select_sanpham_chitiet;

        Glide.with(this).load(Utils.BASE_URL + "../Hinhanh/"+ctsp.getHinhanhsp()).into(imgSanPham);
        tenSP.setText(ctsp.getTensp());
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        giaSP.setText(formatter.format(ctsp.getGiabansp()) +" VNĐ");
        thongTinSp.setText(ctsp.getThongtinsp());
        motaSP.setText(ctsp.getMotasp());

        Integer [] so = new Integer[] {
                1,2,3,4,5,6,7,8,9,10
        };

        ArrayAdapter<Integer> adapterSo = new ArrayAdapter<>(this, R.layout.item_soluong_chitietsp, so);
        slSP.setAdapter(adapterSo);
        slSP.setGravity(Gravity.CENTER);

    }

    private void AnhXaView() {
        imgSanPham = findViewById(R.id.itemsp_img);
        tenSP = findViewById(R.id.itemsp_tensp);
        giaSP = findViewById(R.id.itemsp_giasp);
        thongTinSp = findViewById(R.id.itemsp_thongtin);
        motaSP = findViewById(R.id.itemsp_mota);
        addCart = findViewById(R.id.itemsp_addCart);
        slSP = findViewById(R.id.itemsp_sl);
        toolbar = findViewById(R.id.toolbar_ct);
        cartSL = findViewById(R.id.cart_soluong);
        txtCheckSP = findViewById(R.id.itemsp_ktraSp);
        btnYoutube = findViewById(R.id.chitietsp_xemvideo);

        giohangClick = findViewById(R.id.shoppingCart_chitiet);
        giohangClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GioHangActivity.class);
                startActivity(intent);
            }
        });

        if(DbQuery.mangGioHang != null)
        {
            cartSL.setText(String.valueOf(DbQuery.mangGioHang.size()));
        }

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


}