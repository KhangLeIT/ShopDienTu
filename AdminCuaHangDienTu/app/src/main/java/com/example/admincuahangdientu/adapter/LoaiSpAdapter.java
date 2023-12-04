package com.example.admincuahangdientu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admincuahangdientu.R;
import com.example.admincuahangdientu.model.LoaiSp;

import java.util.List;

public class LoaiSpAdapter extends RecyclerView.Adapter<LoaiSpAdapter.MyViewHolder> {

    private Context context;
    private List<LoaiSp> list;

    public LoaiSpAdapter(Context context, List<LoaiSp> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_danh_muc_demo,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tendmuc;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tendmuc = itemView.findViewById(R.id.item_dm_tendmuc);
        }

        public void setData(int position) {
            LoaiSp loaiSp = list.get(position);
            tendmuc.setText(loaiSp.getTensanpham());
        }
    }
}
