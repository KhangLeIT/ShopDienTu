package com.example.cuahangdientu.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cuahangdientu.DbQuery;
import com.example.cuahangdientu.R;
import com.example.cuahangdientu.activity.SanPhamTheoDanhMucActivity;
import com.example.cuahangdientu.activity.TinTucTheoDanhMucActivity;
import com.example.cuahangdientu.model.LoaiSp;
import com.example.cuahangdientu.utils.Utils;

import java.util.List;

public class DanhMucTinTucAdapter extends RecyclerView.Adapter<DanhMucTinTucAdapter.ViewHolder> {

    Context context;
    List<LoaiSp> list;

    public DanhMucTinTucAdapter(Context context, List<LoaiSp> list) {
        this.context = context;
        this.list = list;
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
                    DbQuery.select_id_dmtt = loaiSp.getId();
                    Intent intent = new Intent(context, TinTucTheoDanhMucActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });

        }
    }
}
