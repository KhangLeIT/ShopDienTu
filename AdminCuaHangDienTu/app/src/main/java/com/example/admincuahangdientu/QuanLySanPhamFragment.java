package com.example.admincuahangdientu;

import static android.content.ContentValues.TAG;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.admincuahangdientu.adapter.DanhMucAdapter;
import com.example.admincuahangdientu.model.LoaiSp;
import com.example.admincuahangdientu.model.SanPham;
import com.example.admincuahangdientu.query.DbQuery;
import com.example.admincuahangdientu.retrifit.APIadminStore;
import com.example.admincuahangdientu.retrifit.RetrofitClient;
import com.example.admincuahangdientu.sanpham.ThemDanhMucActivity;
import com.example.admincuahangdientu.sanpham.ThemSanPhamActivity;
import com.example.admincuahangdientu.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class QuanLySanPhamFragment extends Fragment {
    private AppCompatButton add_sp, btnthemDM, backDM;
    private RecyclerView recDM;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private APIadminStore apiStore;

    private DanhMucAdapter loaiSpHomeAdapter;
    private List<LoaiSp> mangLoaiSp;
    private LinearLayout progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_quan_ly_san_pham, container, false);
        AnhXaView(view);
        progressBar.setVisibility(View.VISIBLE);
        ActionButton();
        loadDanhMuc();
        return view;
    }

    private void loadDanhMuc() {
        progressBar.setVisibility(View.VISIBLE);
        compositeDisposable.add(apiStore.getLoaiSp("getloaisp")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        loaiSpModel -> {
                            if (loaiSpModel.isSuccess())
                            {
                                mangLoaiSp = loaiSpModel.getResult();
                                loaiSpHomeAdapter = new DanhMucAdapter(getContext(), mangLoaiSp);
                                recDM.setAdapter(loaiSpHomeAdapter);
                                progressBar.setVisibility(View.GONE);
                            }
                        },throwable -> Log.e(TAG, "no connect with server " + throwable.getMessage())
                )
        );
    }

    private void ActionButton() {
        add_sp.setOnClickListener(view -> {
            Intent i = new Intent(getContext(), ThemSanPhamActivity.class);
            startActivity(i);
        });

        btnthemDM.setOnClickListener(view -> {
            Intent i = new Intent(getContext(), ThemDanhMucActivity.class);
            startActivity(i);
        });

        backDM.setOnClickListener(view -> {
            List<LoaiSp> listBack = DbQuery.danhMucBack;
            if(listBack.size() == 0)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Không có danh mục để back");
                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
            }
            else{

                LoaiSp dMback = listBack.get(listBack.size()-1);
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Bạn Muốn Back Sản Phẩm Này?");
                builder.setMessage("\nTên: "+dMback.getTensanpham());
                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mangLoaiSp.add(dMback);
                        loaiSpHomeAdapter.notifyDataSetChanged();
                        backDanhMuc(dMback.getId(), dMback.getTensanpham(), dMback.getHinhanh());
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

    }

    private void backDanhMuc(int iddm, String tenDM,String hinhanh) {
        progressBar.setVisibility(View.VISIBLE);
        compositeDisposable.add(apiStore.backDanhMuc("backDelete",iddm,tenDM, hinhanh)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        messageModel -> {
                            if (messageModel.isSuccess())
                            {
                                DbQuery.danhMucBack.remove(DbQuery.danhMucBack.size()-1);
                                Toast.makeText(getContext(), "ok", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        },throwable -> Log.e(TAG, "no connect with server " + throwable.getMessage())
                )
        );
    }

    private void AnhXaView(View view) {
        apiStore = RetrofitClient.getInstance(Utils.BASE_URL).create(APIadminStore.class);
        add_sp = view.findViewById(R.id.QLSP_themsp);
        recDM = view.findViewById(R.id.rec_danhmuc);
        progressBar = view.findViewById(R.id.progressBar);
        btnthemDM = view.findViewById(R.id.QLSP_themDM);

        LinearLayoutManager layoutDMSp = new LinearLayoutManager(getContext());
        layoutDMSp.setOrientation(LinearLayoutManager.VERTICAL);
        recDM.setLayoutManager(layoutDMSp);

        backDM = view.findViewById(R.id.backDM);
    }
}