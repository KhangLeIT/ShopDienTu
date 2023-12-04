package com.example.admincuahangdientu.adapter;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.admincuahangdientu.R;
import com.example.admincuahangdientu.model.LoaiSp;
import com.example.admincuahangdientu.query.DbQuery;
import com.example.admincuahangdientu.retrifit.APIadminStore;
import com.example.admincuahangdientu.retrifit.RetrofitClient;
import com.example.admincuahangdientu.sanpham.DanhMucSanPhamActivity;
import com.example.admincuahangdientu.sanpham.SuaDanhMucActivity;
import com.example.admincuahangdientu.utils.Utils;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DanhMucAdapter extends RecyclerView.Adapter<DanhMucAdapter.ViewHolder> {

    private Context context;
    private List<LoaiSp> list;

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final APIadminStore apiStore = RetrofitClient.getInstance(Utils.BASE_URL).create(APIadminStore.class);


    public DanhMucAdapter(Context context, List<LoaiSp> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_danhmuc,parent,false);
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
            img_delete = itemView.findViewById(R.id.delete_DM);
            img_edit = itemView.findViewById(R.id.edit_DM);

        }


        public void setData(int position) {
            LoaiSp loaiSp = list.get(position);
            txtTenDM.setText(loaiSp.getTensanpham());
            Glide.with(context).load(Utils.BASE_URL+"../Hinhanh/"+loaiSp.getHinhanh()).into(imgDMSP);

            itemClick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DbQuery.select_id_dmsp = loaiSp.getId();
                    Intent  i = new Intent(context, DanhMucSanPhamActivity.class);
                    context.startActivity(i);
                }
            });
            img_edit.setOnClickListener(view -> {
                DbQuery.select_edit_danhmuc = loaiSp;
                Intent  i = new Intent(context, SuaDanhMucActivity.class);
                context.startActivity(i);
            });

            img_delete.setOnClickListener(view -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                builder.setTitle("Bạn Muốn Xóa Sản Phẩm Này?");
                builder.setMessage("\nTên: " + loaiSp.getTensanpham());
                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DbQuery.danhMucBack.add(loaiSp);
                        deleteDanhMuc(loaiSp.getId(), position);
                    }
                });
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
            });
        }

    }

    private void deleteDanhMuc(int iddm, int position) {
        compositeDisposable.add(apiStore.deleteDanhMuc("delete",iddm)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        messageModel -> {
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
                        },
                        throwable -> Log.e(TAG, "no connect with server " + throwable.getMessage())
                )
        );
    }
}
