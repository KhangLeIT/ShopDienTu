package com.example.cuahangdientu.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cuahangdientu.DbQuery;
import com.example.cuahangdientu.R;
import com.example.cuahangdientu.activity.ChiTietSanPhamActivity;
import com.example.cuahangdientu.activity.ChiTietTinTucActivity;
import com.example.cuahangdientu.activity.TinTucTheoDanhMucActivity;
import com.example.cuahangdientu.model.TinTuc;
import com.example.cuahangdientu.utils.Utils;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TinTucTheoDanhMucAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    Context context;
    List<TinTuc> list;

    public TinTucTheoDanhMucAdapter(Context context, List<TinTuc> list) {
        this.context = context;
        this.list = list;
    }

    private static final int VIEW_TYPE_DATA = 0;
    private static final int VIEW_TYPE_LOADING = 1;


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_DATA){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tintuc,parent,false);
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
        if(holder instanceof TinTucTheoDanhMucAdapter.ViewHolder)
        {
            TinTucTheoDanhMucAdapter.ViewHolder viewHolder = (TinTucTheoDanhMucAdapter.ViewHolder) holder;
            viewHolder.setData(position);
        }
        else
        {
            TinTucTheoDanhMucAdapter.LoadingViewHolder loadingViewHolder =(TinTucTheoDanhMucAdapter.LoadingViewHolder) holder;
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

        private ImageView imgTinTuc;
        private TextView tieude, luotxem, ngaydang;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tieude = itemView.findViewById(R.id.itemtintucmoi_tieude);
            imgTinTuc = itemView.findViewById(R.id.itemtintucmoi_img);
            luotxem = itemView.findViewById(R.id.itemtintucmoi_luotxem);
            ngaydang = itemView.findViewById(R.id.itemtintucmoi_ngaydang);
        }
        void setData(int position)
        {
            TinTuc tinTuc = list.get(position);
            String str = tinTuc.getTieude();
            if(str.length()>60)
            {
                str = str.substring(0,60) + "...";
            }
            tieude.setText(str);


            Glide.with(context).load(Utils.BASE_URL + "../Hinhanh/" + tinTuc.getHinhanh()).into(imgTinTuc);
            luotxem.setText(String.valueOf(tinTuc.getLuotxem()));
            ngaydang.setText(formatDate(String.valueOf(tinTuc.getThoigianthem())));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DbQuery.chiTietTinTuc = tinTuc;
                    Intent intent = new Intent(context, ChiTietTinTucActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });

        }

    }

    private static String formatDate(String str){
        String strDt = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        SimpleDateFormat newFormat = new SimpleDateFormat("EEE, dd-MM-yyyy");
        try {
            Date datetime = format.parse(str);
            strDt = newFormat.format(datetime);
            return strDt;
        } catch (Exception e) {
            return "null";
        }
    }
}
