package com.example.cuahangdientu.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cuahangdientu.DbQuery;
import com.example.cuahangdientu.R;
import com.example.cuahangdientu.model.EventBus.TinhTongEvent;
import com.example.cuahangdientu.model.GioHang;
import com.example.cuahangdientu.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.List;

public class GioHangAdapter extends RecyclerView.Adapter<GioHangAdapter.ViewHolder> {

    Context context;
    List<GioHang> gioHangList;

    public GioHangAdapter(Context context, List<GioHang> gioHangList) {
        this.context = context;
        this.gioHangList = gioHangList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gio_hang,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        if(gioHangList == null) return 0;
        else return gioHangList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView gioHang_tenSP, gioHang_tongTienSP,gioHang_soLuong,gioHang_cong, gioHang_tru, gioHang_giaSP;
        private ImageView gioHang_img, cart_delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            gioHang_tenSP = itemView.findViewById(R.id.item_giohang_tensp);
            gioHang_tongTienSP = itemView.findViewById(R.id.item_giohang_tongtien);
            gioHang_soLuong = itemView.findViewById(R.id.item_giohang_soluong);
            gioHang_cong = itemView.findViewById(R.id.item_giohang_cong);
            gioHang_tru = itemView.findViewById(R.id.item_giohang_tru);
            gioHang_giaSP = itemView.findViewById(R.id.item_giohang_giasp);
            gioHang_img = itemView.findViewById(R.id.item_giohang_img);
            cart_delete = itemView.findViewById(R.id.item_giohang_delete);
        }

        public void setData(int position) {
            GioHang gioHang = gioHangList.get(position);

            // cong so luong
            gioHang_cong.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int sluong = gioHang.getSoluong() + 1;
                    if(sluong <11)
                    {
                        gioHangList.get(position).setSoluong(sluong);
                        gioHang.setSoluong(sluong);
                        gioHang_soLuong.setText(sluong + "");

                        float giaban = gioHang.getGiabansp() + gioHang.getGiahientai();

                        gioHang.setGiabansp(giaban);
                        gioHangList.get(position).setGiabansp(giaban);

                        DecimalFormat formatter = new DecimalFormat("###,###,###");
                        gioHang_tongTienSP.setText("Tổng: " + formatter.format(gioHang.getGiabansp()) + "VNĐ");

                        EventBus.getDefault().postSticky(new TinhTongEvent());

                    }
                }
            });
            // tru so luong
            gioHang_tru.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int sluong = gioHang.getSoluong() - 1;
                    if(sluong > 0)
                    {
                        gioHangList.get(position).setSoluong(sluong);
                        gioHang.setSoluong(sluong);
                        gioHang_soLuong.setText(sluong + "");

                        float giaban = gioHang.getGiabansp() - gioHang.getGiahientai();

                        gioHang.setGiabansp(giaban);
                        gioHangList.get(position).setGiabansp(giaban);

                        DecimalFormat formatter = new DecimalFormat("###,###,###");
                        gioHang_tongTienSP.setText("Tổng: " + formatter.format(gioHang.getGiabansp()) + "VNĐ");

                        EventBus.getDefault().postSticky(new TinhTongEvent());
                    }
                }
            });

            // click xoa item
            cart_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                    builder.setTitle("Thông Báo");
                    builder.setMessage("Bạn có muốn xóa sản phẩm này không?");
                    builder.setPositiveButton("đồng ý", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            DbQuery.mangGioHang.remove(position);
                            notifyDataSetChanged();
                            EventBus.getDefault().postSticky(new TinhTongEvent());
                        }
                    });
                    builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builder.show();

                }
            });

            DecimalFormat formatter = new DecimalFormat("###,###,###");
            gioHang_giaSP.setText("Giá: " + formatter.format(gioHang.getGiahientai())+ "VNĐ");
            gioHang_tongTienSP.setText("Tổng: " + formatter.format(gioHang.getGiabansp()) + "VNĐ");

            String str = gioHang.getTensp();
            if(str.length() > 60)
            {
                str = str.substring(0,60) + "...";
            }
            gioHang_tenSP.setText(str);

            gioHang_soLuong.setText(gioHang.getSoluong() + "");

            Glide.with(context).load(Utils.BASE_URL+"../Hinhanh/" + gioHang.getHinhanhsp()).into(gioHang_img);


        }
    }
}
