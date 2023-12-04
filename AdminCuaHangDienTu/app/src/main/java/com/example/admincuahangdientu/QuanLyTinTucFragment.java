package com.example.admincuahangdientu;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.admincuahangdientu.adapter.DanhMucAdapter;
import com.example.admincuahangdientu.adapter.DanhMucTinTucAdapter;
import com.example.admincuahangdientu.model.LoaiSp;
import com.example.admincuahangdientu.retrifit.APIadminStore;
import com.example.admincuahangdientu.retrifit.RetrofitClient;
import com.example.admincuahangdientu.tintuc.ThemTinTucActivity;
import com.example.admincuahangdientu.utils.Utils;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class QuanLyTinTucFragment extends Fragment {

    private AppCompatButton btnthemTT;
    private RecyclerView recDM;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private APIadminStore apiStore;

    private DanhMucTinTucAdapter loaiSpHomeAdapter;
    private List<LoaiSp> mangLoaiSp;
    private LinearLayout progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quan_ly_tin_tuc, container, false);
        // Inflate the layout for this fragment
        AnhXaView(view);
        progressBar.setVisibility(View.VISIBLE);
        loadDanhMuc();
        ActionButton();
        return view;
    }

    private void ActionButton() {
        btnthemTT.setOnClickListener(view -> {
            Intent it = new Intent(getContext(),ThemTinTucActivity.class);
            startActivity(it);
        });


    }

    private void AnhXaView(View view) {
        apiStore = RetrofitClient.getInstance(Utils.BASE_URL).create(APIadminStore.class);
        recDM = view.findViewById(R.id.rec_danhmuc);
        progressBar = view.findViewById(R.id.progressBar);
        btnthemTT = view.findViewById(R.id.QLTT_themTT);

        LinearLayoutManager layoutDMSp = new LinearLayoutManager(getContext());
        layoutDMSp.setOrientation(LinearLayoutManager.VERTICAL);
        recDM.setLayoutManager(layoutDMSp);

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
                                loaiSpHomeAdapter = new DanhMucTinTucAdapter(getContext(), mangLoaiSp);
                                recDM.setAdapter(loaiSpHomeAdapter);
                                progressBar.setVisibility(View.GONE);
                            }
                        },throwable -> Log.e(TAG, "no connect with server " + throwable.getMessage())
                )
        );
    }
}