package com.example.admincuahangdientu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.admincuahangdientu.R;
import com.example.admincuahangdientu.model.ItemDonHang;
import com.example.admincuahangdientu.utils.Utils;
import java.text.DecimalFormat;
import java.util.List;


public class ChiTietDonHangAdapter extends RecyclerView.Adapter<ChiTietDonHangAdapter.ViewHolder> {

    Context context;
    List<ItemDonHang> itemDonHangList;

    public ChiTietDonHangAdapter(Context context, List<ItemDonHang> itemDonHangList) {
        this.context = context;
        this.itemDonHangList = itemDonHangList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chitiet_donhang,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return itemDonHangList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView ctdh_gia, ctdh_tongtien, ctdh_soluong, ctdh_tensp;
        private ImageView ctdh_img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ctdh_img = itemView.findViewById(R.id.itemchitietdonhang_img);
            ctdh_gia = itemView.findViewById(R.id.itemchitietdonhang__giasp);
            ctdh_tongtien = itemView.findViewById(R.id.itemchitietdonhang_tongtien);
            ctdh_soluong = itemView.findViewById(R.id.itemchitietdonhang_sl);
            ctdh_tensp = itemView.findViewById(R.id.itemchitietdonhang__tensp);
        }

        public void setData(int position) {

            ItemDonHang itemDonHang = itemDonHangList.get(position);
            Glide.with(context).load(Utils.BASE_URL+"../Hinhanh/" + itemDonHang.getHinhanhsp()).into(ctdh_img);

            DecimalFormat formatter = new DecimalFormat("###,###,###");

            ctdh_soluong.setText(itemDonHang.getSoluongmua()+"");
            ctdh_gia.setText("Giá: "+ formatter.format(itemDonHang.getGiabansp())+ " VNĐ");
            ctdh_tongtien.setText("Tổng: "+formatter.format(itemDonHang.getGia()) + " VNĐ");

            String str = itemDonHang.getTensp();
            if(str.length() > 50)
            {
                str = str.substring(0,50) + "...";
            }
            ctdh_tensp.setText(str);

        }
    }
}
