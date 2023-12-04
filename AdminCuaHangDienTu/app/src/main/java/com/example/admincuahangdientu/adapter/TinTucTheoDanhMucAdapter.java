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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.admincuahangdientu.R;
import com.example.admincuahangdientu.model.TinTuc;
import com.example.admincuahangdientu.query.DbQuery;
import com.example.admincuahangdientu.retrifit.APIadminStore;
import com.example.admincuahangdientu.retrifit.RetrofitClient;
import com.example.admincuahangdientu.tintuc.SuaTinTucActivity;
import com.example.admincuahangdientu.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.internal.Util;

public class TinTucTheoDanhMucAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    Context context;
    List<TinTuc> list;

    public TinTucTheoDanhMucAdapter(Context context, List<TinTuc> list) {
        this.context = context;
        this.list = list;
    }

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final APIadminStore apiStore = RetrofitClient.getInstance(Utils.BASE_URL).create(APIadminStore.class);

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

        private ImageView imgTinTuc, deleteTT, editTT;
        private TextView tieude, luotxem, ngaydang, idTT;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tieude = itemView.findViewById(R.id.itemtintucmoi_tieude);
            imgTinTuc = itemView.findViewById(R.id.itemtintucmoi_img);
            luotxem = itemView.findViewById(R.id.itemtintucmoi_luotxem);
            ngaydang = itemView.findViewById(R.id.itemtintucmoi_ngaydang);
            idTT = itemView.findViewById(R.id.itemtintucmoi_idTT);
            deleteTT = itemView.findViewById(R.id.delete_TT);
            editTT = itemView.findViewById(R.id.edit_TT);
        }
        void setData(int position)
        {
            TinTuc tinTuc = list.get(position);
            idTT.setText(String.valueOf(tinTuc.getId()));
            String str = tinTuc.getTieude();
            if(str.length()>60)
            {
                str = str.substring(0,60) + "...";
            }
            tieude.setText(str);

            Glide.with(context).load(Utils.BASE_URL + "../Hinhanh/" + tinTuc.getHinhanh()).into(imgTinTuc);
            luotxem.setText(String.valueOf(tinTuc.getLuotxem()));
            ngaydang.setText(formatDate(String.valueOf(tinTuc.getThoigianthem())));
            editTT.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DbQuery.select_edit_tintuc = tinTuc;
                    DbQuery.position_edit_tintuc = position;
                    Intent intent = new Intent(context, SuaTinTucActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });

            deleteTT.setOnClickListener(view -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                builder.setTitle("Bạn Muốn Xóa Sản Phẩm Này?");
                builder.setMessage("\nTiêu đề: " + tinTuc.getTieude() );
                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DbQuery.tinTucBack.add(tinTuc);
                        deleteTinTuc(position,tinTuc.getId());
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

    private void deleteTinTuc(int position, int idsp) {

        compositeDisposable.add(apiStore.deleteTinTuc("delete",idsp)
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
