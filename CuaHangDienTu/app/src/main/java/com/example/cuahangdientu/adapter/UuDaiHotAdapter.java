package com.example.cuahangdientu.adapter;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cuahangdientu.DbQuery;
import com.example.cuahangdientu.R;
import com.example.cuahangdientu.activity.ChiTietSanPhamActivity;
import com.example.cuahangdientu.model.SanPhamMoi;
import com.example.cuahangdientu.model.UuDaiHot;
import com.example.cuahangdientu.retrifit.ApiStore;
import com.example.cuahangdientu.retrifit.RetrofitClient;
import com.example.cuahangdientu.utils.Utils;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class UuDaiHotAdapter extends RecyclerView.Adapter<UuDaiHotAdapter.ViewHolder> {

    Context context;
    List<UuDaiHot> list;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private ApiStore apiStore;

    public UuDaiHotAdapter(Context context, List<UuDaiHot> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_uudaihot,parent,false);
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

        private ImageView img_uudai;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_uudai = itemView.findViewById(R.id.itemUD_img);
            apiStore = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiStore.class);

        }

        public void setData(int position) {
            UuDaiHot uuDaiHot = list.get(position);
            Glide.with(context).load(Utils.BASE_URL+"../Hinhanh/" + uuDaiHot.getHinhanh()).into(img_uudai);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   if(uuDaiHot.getIdsp() != -1)
                   {
                       loadSanphamUuDai(uuDaiHot.getIdsp());
                   }
                }
            });
        }
    }

    private void loadSanphamUuDai(int idsp) {
        compositeDisposable.add(apiStore.getsanphamuudai(1, idsp)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        sanPhamMoiModel -> {
                            if (sanPhamMoiModel.isSuccess())
                            {
                                DbQuery.select_ten_danhmuc = "Sản Phẩm Ưu Đãi";
                                List<SanPhamMoi> sanPhamMoi = sanPhamMoiModel.getResult();
                                DbQuery.select_sanpham_chitiet =  sanPhamMoi.get(0);
                                Intent intent = new Intent(context, ChiTietSanPhamActivity.class);
                                context.startActivity(intent);
                            }
                        },throwable -> Log.e(TAG, "no connect with server " + throwable.getMessage())
                )
        );
    }

}
