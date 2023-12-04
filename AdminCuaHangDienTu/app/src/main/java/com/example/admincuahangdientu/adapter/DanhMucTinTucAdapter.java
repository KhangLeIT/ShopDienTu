package com.example.admincuahangdientu.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.admincuahangdientu.R;
import com.example.admincuahangdientu.model.LoaiSp;
import com.example.admincuahangdientu.query.DbQuery;
import com.example.admincuahangdientu.sanpham.DanhMucSanPhamActivity;
import com.example.admincuahangdientu.tintuc.DanhMucTinTucActivity;
import com.example.admincuahangdientu.utils.Utils;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_danhmuc_tintuc,parent,false);
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
        private ImageView imgDMSP, img_delete, img_edit;
        private LinearLayout itemClick;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTenDM = itemView.findViewById(R.id.item_tensp);
            imgDMSP = itemView.findViewById(R.id.item_image);
            itemClick = itemView.findViewById(R.id.item_click);
        }

        public void setData(int position) {
            LoaiSp loaiSp = list.get(position);
            txtTenDM.setText(loaiSp.getTensanpham());
            Glide.with(context).load(Utils.BASE_URL+"../Hinhanh/"+loaiSp.getHinhanh()).into(imgDMSP);

            itemClick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DbQuery.select_id_TT = loaiSp.getId();
                    Intent i = new Intent(context, DanhMucTinTucActivity.class);
                    context.startActivity(i);
                }
            });
        }
    }
}
