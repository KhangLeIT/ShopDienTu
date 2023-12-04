package com.example.cuahangdientu.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cuahangdientu.DbQuery;
import com.example.cuahangdientu.R;
import com.example.cuahangdientu.activity.SanPhamTheoDanhMucActivity;
import com.example.cuahangdientu.model.LoaiSp;
import com.example.cuahangdientu.utils.Utils;


import java.util.List;

public class LoaiSpHomeAdapter extends RecyclerView.Adapter<LoaiSpHomeAdapter.ViewHolder> {

    private List<LoaiSp> list;
    private Context context;

    public LoaiSpHomeAdapter(Context context, List<LoaiSp> list) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dmsp,parent,false);

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
        private TextView txtTenDM;
        private ImageView imgDMSP;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTenDM = itemView.findViewById(R.id.item_tensp);
            imgDMSP = itemView.findViewById(R.id.item_image);
        }

        private void setData(int position){
            LoaiSp loaiSp = list.get(position);
            txtTenDM.setText(loaiSp.getTensanpham());
            Glide.with(context).load(Utils.BASE_URL +"../Hinhanh/"+loaiSp.getHinhanh()).into(imgDMSP);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DbQuery.select_id_dmsp = loaiSp.getId();
                    DbQuery.select_ten_danhmuc = loaiSp.getTensanpham();
                    //Toast.makeText(context, String.valueOf(loaiSp.getId()), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, SanPhamTheoDanhMucActivity.class);
                    context.startActivity(intent);
                }
            });

        }



    }
}
