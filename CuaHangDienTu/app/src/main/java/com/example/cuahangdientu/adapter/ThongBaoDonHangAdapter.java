package com.example.cuahangdientu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cuahangdientu.R;
import com.example.cuahangdientu.model.ThongBao;

import java.util.List;

public class ThongBaoDonHangAdapter extends RecyclerView.Adapter<ThongBaoDonHangAdapter.ViewHolder> {
    Context context;
    List<ThongBao> list;

    public ThongBaoDonHangAdapter(Context context, List<ThongBao> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_thongbao_donhang,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView iddonhang, noidung;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iddonhang = itemView.findViewById(R.id.itemthongbao_iddonhang);
            noidung = itemView.findViewById(R.id.itemthongbao_noidung);
        }

        public void setData(int position) {
            ThongBao thongBao = list.get(position);
            noidung.setText(thongBao.getNoidungthongbao());
            iddonhang.setText("Thông Báo Đơn Hàng Số "+thongBao.getIddonhang());
        }
    }
}
