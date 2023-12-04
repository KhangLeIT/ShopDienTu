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
import com.example.cuahangdientu.activity.ChiTietTinTucActivity;
import com.example.cuahangdientu.model.TinTuc;
import com.example.cuahangdientu.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TinTucMoiAdapter extends RecyclerView.Adapter<TinTucMoiAdapter.ViewHodler> {

    Context context;
    List<TinTuc> tinTucList;

    public TinTucMoiAdapter(Context context, List<TinTuc> tinTucList) {
        this.context = context;
        this.tinTucList = tinTucList;
    }

    @NonNull
    @Override
    public ViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tintuc,parent,false);

        return new ViewHodler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodler holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return tinTucList.size();
    }

    public class ViewHodler extends RecyclerView.ViewHolder {
        private ImageView imgTinTuc;
        private TextView tieude, luotxem, ngaydang;

        public ViewHodler(@NonNull View itemView) {
            super(itemView);

            tieude = itemView.findViewById(R.id.itemtintucmoi_tieude);
            imgTinTuc = itemView.findViewById(R.id.itemtintucmoi_img);
            luotxem = itemView.findViewById(R.id.itemtintucmoi_luotxem);
            ngaydang = itemView.findViewById(R.id.itemtintucmoi_ngaydang);
        }

        public void setData(int position) {
            TinTuc tinTuc = tinTucList.get(position);
            String str = tinTuc.getTieude();
            if(str.length() > 65)
            {
                str = str.substring(0,65) + "...";
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
