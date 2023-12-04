package com.example.cuahangdientu.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.cuahangdientu.DbQuery;
import com.example.cuahangdientu.R;
import com.example.cuahangdientu.activity.ChiTietSanPhamActivity;
import com.example.cuahangdientu.model.SanPhamMoi;
import com.example.cuahangdientu.utils.Utils;

import java.text.DecimalFormat;
import java.util.List;

public class SanPhamTheoDMucAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<SanPhamMoi> list;
    private static final int VIEW_TYPE_DATA = 0;
    private static final int VIEW_TYPE_LOADING = 1;

    public SanPhamTheoDMucAdapter(Context context, List<SanPhamMoi> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_DATA){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sanphamtheodanhmuc,parent,false);
            return new ViewHolder(view);
        }
        else
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loading_item,parent,false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ViewHolder)
        {
            ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.setData(position);
        }
        else
        {
            LoadingViewHolder loadingViewHolder =(LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }

    }

    @Override
    public int getItemViewType(int position) {

        return list.get(position) == null ? VIEW_TYPE_LOADING:VIEW_TYPE_DATA;
    }

    @Override
    public int getItemCount() {
        if(list == null)
            return  0;
        else
            return list.size();
    }

    public class LoadingViewHolder extends RecyclerView.ViewHolder{
        ProgressBar progressBar;
        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgSpMoi;
        private TextView txtTenSp;
        private TextView txtGiaSp, txtMoTa;
        private LinearLayout layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgSpMoi = itemView.findViewById(R.id.itemspm_img);
            txtGiaSp = itemView.findViewById(R.id.itemspm_giasp);
            txtTenSp = itemView.findViewById(R.id.itemspm_tensp);
            layout = itemView.findViewById(R.id.layoutWH);

        }

        public void setData(int position)
        {
            DecimalFormat formatter = new DecimalFormat("###,###,###");

            SanPhamMoi spmoi = list.get(position);
            //get chieu rong dai cho laptop
            if(spmoi.getIddm() == 2)
            {
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) layout.getLayoutParams();
                params.width = 400;
                params.height = 300;
                params.setMarginStart(10);
                params.setMarginEnd(10);
                layout.setLayoutParams(params);
            }
            String str = spmoi.getTensp();
            if(str.length() > 60)
            {
                str = str.substring(0,60) + "...";

            }
            txtTenSp.setText(str);
            Glide.with(context).load(Utils.BASE_URL+"../Hinhanh/" + spmoi.getHinhanhsp()).into(imgSpMoi);
            txtGiaSp.setText(String.valueOf(formatter.format(spmoi.getGiabansp())) +" VNĐ");
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DbQuery.select_sanpham_chitiet = spmoi;
                    Intent intent = new Intent(context, ChiTietSanPhamActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    //Toast.makeText(context, "iddm "+String.valueOf(spmoi.getIddm()) +" / "+"idsp"+ String.valueOf(spmoi.getIdsp()), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}
