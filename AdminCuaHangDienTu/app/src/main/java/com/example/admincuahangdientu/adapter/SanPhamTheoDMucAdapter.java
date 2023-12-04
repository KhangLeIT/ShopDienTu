package com.example.admincuahangdientu.adapter;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.admincuahangdientu.R;
import com.example.admincuahangdientu.model.SanPham;
import com.example.admincuahangdientu.query.DbQuery;
import com.example.admincuahangdientu.retrifit.APIadminStore;
import com.example.admincuahangdientu.retrifit.RetrofitClient;
import com.example.admincuahangdientu.sanpham.SuaSanPhamActivity;
import com.example.admincuahangdientu.utils.Utils;
import java.text.DecimalFormat;
import java.util.List;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SanPhamTheoDMucAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<SanPham> list;
    private static final int VIEW_TYPE_DATA = 0;
    private static final int VIEW_TYPE_LOADING = 1;

    private final CompositeDisposable  compositeDisposable = new CompositeDisposable();
    private final APIadminStore apiStore = RetrofitClient.getInstance(Utils.BASE_URL).create(APIadminStore.class);

    public SanPhamTheoDMucAdapter(Context context, List<SanPham> list) {
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
        private final ImageView imgSpMoi, edit, delete;
        private final TextView txtTenSp;
        private final TextView txtGiaSp, txtIdSp;
        private final LinearLayout layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgSpMoi = itemView.findViewById(R.id.itemspm_img);
            txtGiaSp = itemView.findViewById(R.id.itemspm_giasp);
            txtTenSp = itemView.findViewById(R.id.itemspm_tensp);
            layout = itemView.findViewById(R.id.layoutWH);
            edit = itemView.findViewById(R.id.edit_DM);
            delete = itemView.findViewById(R.id.delete_DM);
            txtIdSp = itemView.findViewById(R.id.itemspm_id);

        }

        @SuppressLint("SetTextI18n")
        public void setData(int position)
        {
            DecimalFormat formatter = new DecimalFormat("###,###,###");

            SanPham spmoi = list.get(position);

            txtIdSp.setText("ID: " + spmoi.getIdsp());
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
            if(spmoi.getLinkvideo()==null) {
                spmoi.setLinkvideo("NULL");
            }
            String finalStr = str;
            delete.setOnClickListener(view -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                builder.setTitle("Bạn Muốn Xóa Sản Phẩm Này?");
                builder.setMessage("\nTên: " + finalStr +"\nGiá: " + String.valueOf(formatter.format(spmoi.getGiabansp())) +"VNĐ");
                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DbQuery.sanphamBack.add(spmoi);
                        deleteSanPham(position,spmoi.getIdsp());
                    }
                });
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
            });

            edit.setOnClickListener(view->{
                DbQuery.select_edit_sanpham = spmoi;
                DbQuery.position_edit_sanpham = position;
                Intent i = new Intent(context, SuaSanPhamActivity.class);
                context.startActivity(i);
            });

        }
    }

    private void deleteSanPham(int position, int idsp) {
        compositeDisposable.add(apiStore.deletesanpham("delete",idsp)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                       messageModel -> {
                           if(messageModel.isSuccess()){
                               list.remove(position);
                               notifyDataSetChanged();
                               AlertDialog.Builder builder = new AlertDialog.Builder(context);
                               builder.setTitle("Đã Xóa");
                               builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                                   @Override
                                   public void onClick(DialogInterface dialogInterface, int i) {

                                   }
                               });
                               builder.show();
                           }
                           else Toast.makeText(context, "error", Toast.LENGTH_SHORT).show();
                       },
                        throwable -> Log.e(TAG, "no connect with server " + throwable.getMessage())
                )
        );
    }

}
