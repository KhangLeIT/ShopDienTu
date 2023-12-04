package com.example.cuahangdientu.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cuahangdientu.DbQuery;
import com.example.cuahangdientu.R;
import com.example.cuahangdientu.activity.ChiTietSanPhamActivity;
import com.example.cuahangdientu.model.SanPhamMoi;
import com.example.cuahangdientu.utils.Utils;


import java.text.DecimalFormat;
import java.util.List;

public class SanPhamMoiAdapter extends RecyclerView.Adapter<SanPhamMoiAdapter.ViewHolder> {

    List<SanPhamMoi> list;
    Context context;

    public SanPhamMoiAdapter(Context context, List<SanPhamMoi> list) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_spmoi_home,parent,false);

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
        private ImageView imgSpMoi;
        private TextView txtTenSp;
        private TextView txtGiaSp;
        private LinearLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgSpMoi = itemView.findViewById(R.id.itemspm_img);
            txtGiaSp = itemView.findViewById(R.id.itemspm_giasp);
            txtTenSp = itemView.findViewById(R.id.itemspm_tensp);
            layout = itemView.findViewById(R.id.layoutWH);
        }
        private void setData(int position)
        {
            DecimalFormat formatter = new DecimalFormat("###,###,###");
            SanPhamMoi spmoi = list.get(position);

            Glide.with(context).load(Utils.BASE_URL+"../Hinhanh/" + spmoi.getHinhanhsp()).into(imgSpMoi);
            String str = spmoi.getTensp();
            if(str.length() > 40)
            {
                str = str.substring(0,40) + "...";

            }
            txtTenSp.setText(str);
            txtGiaSp.setText(String.valueOf(formatter.format(spmoi.getGiabansp())) +" VNĐ");
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DbQuery.select_ten_danhmuc = "Sản Phẩm Mới";
                    DbQuery.select_sanpham_chitiet = spmoi;
                    Intent intent = new Intent(context, ChiTietSanPhamActivity.class);
                    context.startActivity(intent);
                   // Toast.makeText(context, String.valueOf(spmoi.getIddm()) +" / "+ String.valueOf(spmoi.getIdsp()), Toast.LENGTH_SHORT).show();
                }
            });

        }

    }
}
