package com.example.cuahangdientu.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cuahangdientu.DbQuery;
import com.example.cuahangdientu.R;
import com.example.cuahangdientu.adapter.GioHangAdapter;
import com.example.cuahangdientu.model.EventBus.TinhTongEvent;
import com.example.cuahangdientu.model.GioHang;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.List;

public class GioHangActivity extends AppCompatActivity {
    private RecyclerView rec_gioHang;
    private TextView tongTien, gioHangTrong;
    private Button datHang;
    private Toolbar toolbar;
    private GioHangAdapter adapter;
    //private List<GioHang> gioHangList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);

        AnhXaView();
        initControl();

    }

    private void initControl() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Giỏ Hàng");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        rec_gioHang.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rec_gioHang.setLayoutManager(layoutManager);

        if(DbQuery.mangGioHang.size() == 0 )
        {
            gioHangTrong.setVisibility(View.VISIBLE);
        }
        else
        {
            adapter = new GioHangAdapter(getApplicationContext(), DbQuery.mangGioHang);
            rec_gioHang.setAdapter(adapter);
        }

        TinhTongTien();
    }

    private void AnhXaView() {
        rec_gioHang = findViewById(R.id.rec_gionghang);
        tongTien = findViewById(R.id.txtTongTien);
        gioHangTrong = findViewById(R.id.txtGioHangTrong);
        datHang = findViewById(R.id.btnDatHang);
        toolbar = findViewById(R.id.toolbar_gh);

        datHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(DbQuery.mangGioHang.size() >0)
                {
                    Intent intent = new Intent(getApplicationContext(), ThanhToanActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(GioHangActivity.this, "chưa có sản phẩm trong giỏ hàng", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void eventTinhTien(TinhTongEvent event)
    {
        if(event != null)
        {
            TinhTongTien();
        }
        if(DbQuery.mangGioHang.size() == 0)
        {
            gioHangTrong.setVisibility(View.VISIBLE);
        }
    }

    private void TinhTongTien()
    {
        float total = 0;
        for(int i = 0; i< DbQuery.mangGioHang.size(); i++)
        {
            total = total+ DbQuery.mangGioHang.get(i).getGiabansp();
        }
        DbQuery.giatri_tongtien_donhang = total;
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        tongTien.setText(formatter.format(total)+ "VNĐ");
    }

}