package com.example.cuahangdientu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cuahangdientu.R;
import com.example.cuahangdientu.model.HinhAnhList;
import com.example.cuahangdientu.utils.Utils;

import java.util.List;

public class HinhAnhListAdapter extends RecyclerView.Adapter<HinhAnhListAdapter.ViewHolder> {

    Context context;
    List<HinhAnhList> list;

    public HinhAnhListAdapter(Context context, List<HinhAnhList> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_hinhanh,parent,false);

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
        private ImageView img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.item_list_img);
        }

        public void setData(int position) {
            HinhAnhList hinhAnhList = list.get(position);
            Glide.with(context).load(Utils.BASE_URL +"../Hinhanh/" + hinhAnhList.getHinhanh()).into(img);
        }
    }
}
