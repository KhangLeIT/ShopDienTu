package com.example.cuahangdientu.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cuahangdientu.DbQuery;
import com.example.cuahangdientu.R;
import com.example.cuahangdientu.activity.ChiTietDonHangActivity;
import com.example.cuahangdientu.model.DonHang;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.util.List;

public class DonHangUserAdapter extends RecyclerView.Adapter<DonHangUserAdapter.ViewHolder> {
    Context context;
    List<DonHang> donHangList;

    public DonHangUserAdapter(Context context, List<DonHang> donHangList) {
        this.context = context;
        this.donHangList = donHangList;
    }

    @NonNull
    @Override
    public DonHangUserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_donhang,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DonHangUserAdapter.ViewHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return donHangList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView iddonhang, tongtien, trangthai, ngaymua, slsanpham;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iddonhang = itemView.findViewById(R.id.donhang_id);
            tongtien = itemView.findViewById(R.id.donhang_tongtien);
            trangthai = itemView.findViewById(R.id.donhang_trangthai);
            ngaymua = itemView.findViewById(R.id.donhang_date);
            slsanpham = itemView.findViewById(R.id.donhang_soluongsp);
        }

        public void setData(int position) {
            DonHang donHang = donHangList.get(position);
            iddonhang.setText(String.valueOf(donHang.getId()));
            slsanpham.setText(String.valueOf(donHang.getSoluong()));

            String date = DbQuery.formatDate(donHang.getNgaytaodon());
            ngaymua.setText(date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DbQuery.chitiet_donhang_ngayhoanthanh = donHang.getNgayhoanthanh();
                    DbQuery.chitiet_trangthai_donhang = donHang.getTrangthai();

                    DbQuery.itemDonHangList = donHang.getItem();
                    DbQuery.chitet_donhang_tongtien = donHang.getTongtien();
                    Intent intent = new Intent(context, ChiTietDonHangActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });

            DecimalFormat formatter = new DecimalFormat("###,###,###");
            float ttien = donHang.getTongtien();
            tongtien.setText(formatter.format(ttien) + " VNĐ");
            if(donHang.getTrangthai() == 0)
            {
                trangthai.setText("đang chờ duyệt");
            }
            else if(donHang.getTrangthai() == 1)
            {
                trangthai.setText("Đã Duyệt");
            }
            else if(donHang.getTrangthai() == 2)
            {
                trangthai.setText("Đang Giao Hàng");
            }
            else if(donHang.getTrangthai() == 3)
            {
                trangthai.setText("Hoàn Thành");
            }
            else if(donHang.getTrangthai() == -1){
                trangthai.setText("Đơn Hàng Bị Hủy");
            }
        }
    }
}
