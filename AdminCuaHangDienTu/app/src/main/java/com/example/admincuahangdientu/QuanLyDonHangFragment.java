package com.example.admincuahangdientu;

import static com.example.admincuahangdientu.R.drawable.background_none;
import static com.example.admincuahangdientu.R.drawable.background_select;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admincuahangdientu.adapter.DonHangAdapter;
import com.example.admincuahangdientu.model.DonHang;
import com.example.admincuahangdientu.model.ItemDonHang;
import com.example.admincuahangdientu.query.DbQuery;
import com.example.admincuahangdientu.retrifit.APIadminStore;
import com.example.admincuahangdientu.retrifit.RetrofitClient;
import com.example.admincuahangdientu.utils.Utils;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class QuanLyDonHangFragment extends Fragment {
    private NavigationView drawerHomNay, drawerDonHang;
    TextView donHangHomNayClick, donHangAllClick, emptyHomNay;
    RadioButton homnay_hoanthanh, homnay_choduyet, homnay_danggiao, homnay_daduyet, sapxep_moinhat, sapxep_cunhat;
    ImageView btnTimKiem;
    EditText editIdTKiem;
    TextView huyTimKiem, tongDonHang;

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private APIadminStore apiStore;

    List<DonHang> donHangHomNay = new ArrayList<>();
    RecyclerView rec_donHangHomNay;
    DonHangAdapter donHangHomNayAdapter;

    List<DonHang> allDonHang = new ArrayList<>();
    RecyclerView rec_AllDonHang;
    DonHangAdapter donHangAllAdapter;

    List<DonHang> timkiemDH = new ArrayList<>();
    RecyclerView rec_timkiem;
    DonHangAdapter donhangTimKiemAdapter;

    ImageView thongke_click;

    private ProgressBar progressBar;
    private LinearLayout layoutTimKiem;

    int select_trangthai = 0;
    String select_sapxep = "DESC"; // ASC
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quan_ly_don_hang, container, false);

        AnhXaView(view);
        drawerHomNay.setVisibility(View.VISIBLE);
        drawerDonHang.setVisibility(View.GONE);
        ChangeDrawerLick();
        ActionButton();

        getDonHangHomNay();
        getAllDonHang();


        return view;
    }

    private void actionTimKiemDonHang(int iddonhang) {
        progressBar.setVisibility(View.VISIBLE);
        compositeDisposable.add(apiStore.timkiemDonHang("timkiem", iddonhang)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        donHangModel -> {
                            if(donHangModel.isSuccess()){
                                timkiemDH = donHangModel.getResult();

                                donhangTimKiemAdapter = new DonHangAdapter(getContext(), timkiemDH);
                                rec_timkiem.setAdapter(donhangTimKiemAdapter);

                                progressBar.setVisibility(View.GONE);
                                emptyHomNay.setVisibility(View.GONE);
                                layoutTimKiem.setVisibility(View.VISIBLE);
                            }
                            else {
                                progressBar.setVisibility(View.GONE);
                                if(timkiemDH!= null){
                                    timkiemDH.clear();
                                    donHangAllAdapter.notifyDataSetChanged();
                                    layoutTimKiem.setVisibility(View.GONE);
                                    Toast.makeText(getContext(), "không có đơn hàng này", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                        ,
                        throwable -> {
                            Log.d("error", throwable.getMessage());
                        }
                ));
    }

    private void getAllDonHang() {
        progressBar.setVisibility(View.VISIBLE);
        compositeDisposable.add(apiStore.getDonHang("getDonHang",">-1",select_trangthai,select_sapxep)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        donHangModel -> {
                            if(donHangModel.isSuccess()){
                                allDonHang = donHangModel.getResult();

                                donHangAllAdapter = new DonHangAdapter(getContext(), allDonHang);
                                rec_AllDonHang.setAdapter(donHangAllAdapter);
                                tongDonHang.setText(String.valueOf(allDonHang.size()));
                                progressBar.setVisibility(View.GONE);
                                emptyHomNay.setVisibility(View.GONE);
                            }
                            else {
                                progressBar.setVisibility(View.GONE);
                                if(allDonHang!= null){
                                    allDonHang.clear();
                                    donHangAllAdapter.notifyDataSetChanged();
                                    tongDonHang.setText(String.valueOf(0));
                                    emptyHomNay.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                        ,
                        throwable -> {
                            Log.d("error", throwable.getMessage());
                        }
                ));
    }

    private void getDonHangHomNay() {
        progressBar.setVisibility(View.VISIBLE);
        compositeDisposable.add(apiStore.getDonHang("getDonHang","=0",select_trangthai,select_sapxep)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        donHangModel -> {
                            if(donHangModel.isSuccess()){
                                donHangHomNay = donHangModel.getResult();

                                donHangHomNayAdapter = new DonHangAdapter(getContext(), donHangHomNay);
                                rec_donHangHomNay.setAdapter(donHangHomNayAdapter);
                                tongDonHang.setText(String.valueOf(donHangHomNay.size()));
                                progressBar.setVisibility(View.GONE);
                                emptyHomNay.setVisibility(View.GONE);
                            }
                            else {
                                progressBar.setVisibility(View.GONE);
                                if(donHangHomNay!= null){
                                    donHangHomNay.clear();
                                    donHangHomNayAdapter.notifyDataSetChanged();
                                    tongDonHang.setText(String.valueOf(0));
                                    emptyHomNay.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                        ,
                        throwable -> {
                            Log.d("error", throwable.getMessage());
                        }
                ));
    }

    private void ActionButton() {
        thongke_click.setOnClickListener(view -> {
            Intent it = new Intent(getContext(),ThongKeActivity.class);
            startActivity(it);
        });

        homnay_hoanthanh.setOnClickListener(view -> {
            select_trangthai = 3;
            getDonHangHomNay();
            getAllDonHang();
        });
        homnay_danggiao.setOnClickListener(view ->{
            select_trangthai = 2;
            getDonHangHomNay();
            getAllDonHang();
        });
        homnay_daduyet.setOnClickListener(view -> {
            select_trangthai = 1;
            getDonHangHomNay();
            getAllDonHang();
        });
        homnay_choduyet.setOnClickListener(view -> {
            select_trangthai = 0;
            getDonHangHomNay();
            getAllDonHang();
        });

        sapxep_moinhat.setOnClickListener(view ->{
            select_sapxep = "DESC";
            getDonHangHomNay();
            getAllDonHang();
        });
        sapxep_cunhat.setOnClickListener(view -> {
            select_sapxep = "ASC";
            getDonHangHomNay();
            getAllDonHang();
        });

        btnTimKiem.setOnClickListener(view -> {
            String id = editIdTKiem.getText().toString().trim();
            if(TextUtils.isEmpty(id)){
                Toast.makeText(getContext(), "id trống", Toast.LENGTH_SHORT).show();
            }
            else{
                int idTimkiem = Integer.parseInt(id);
                actionTimKiemDonHang(idTimkiem);
            }

        });

        huyTimKiem.setOnClickListener(view -> {
            if(timkiemDH != null){
                if(timkiemDH.size() >0){
                    timkiemDH.clear();
                    donhangTimKiemAdapter.notifyDataSetChanged();
                    layoutTimKiem.setVisibility(View.GONE);
                }


            }
        });
    }

    @SuppressLint("ResourceAsColor")
    private void ChangeDrawerLick() {
        donHangHomNayClick.setOnClickListener(view -> {
            if(drawerHomNay.getVisibility() == View.GONE){
                drawerHomNay.setVisibility(View.VISIBLE);
                drawerDonHang.setVisibility(View.GONE);
                donHangHomNayClick.setBackgroundResource(background_select);
                donHangAllClick.setBackgroundResource(background_none);
                tongDonHang.setText(String.valueOf(donHangHomNay.size()));

            }
        } );

        donHangAllClick.setOnClickListener(view ->{
            if(drawerDonHang.getVisibility() == View.GONE){
                drawerHomNay.setVisibility(View.GONE);
                drawerDonHang.setVisibility(View.VISIBLE);
                donHangHomNayClick.setBackgroundResource(background_none);
                donHangAllClick.setBackgroundResource(background_select);
                tongDonHang.setText(String.valueOf(allDonHang.size()));
            }
        });
    }

    private void AnhXaView(View view) {
        apiStore = RetrofitClient.getInstance(Utils.BASE_URL).create(APIadminStore.class);
        thongke_click = view.findViewById(R.id.thongke_donhang);

        drawerHomNay = view.findViewById(R.id.navViewHomNay);
        drawerDonHang = view.findViewById(R.id.navViewDonHang);
        donHangAllClick = view.findViewById(R.id.allDH_click);
        donHangHomNayClick = view.findViewById(R.id.dHhonNay_click);

        homnay_danggiao = view.findViewById(R.id.homnay_danggiao);
        homnay_choduyet = view.findViewById(R.id.homnay_choduyet);
        homnay_hoanthanh = view.findViewById(R.id.homnay_hoanthanh);
        homnay_daduyet = view.findViewById(R.id.homnay_daduyet);
        emptyHomNay = view.findViewById(R.id.donhangHomNayEmpty);


        btnTimKiem = view.findViewById(R.id.btnTimKiem);
        editIdTKiem = view.findViewById(R.id.editTimKiem);
        huyTimKiem = view.findViewById(R.id.huyTkiem);

        sapxep_moinhat = view.findViewById(R.id.sapxep_moinhat);
        sapxep_cunhat = view.findViewById(R.id.sapxep_cunhat);

        rec_donHangHomNay = view.findViewById(R.id.rec_homnay);
        rec_AllDonHang = view.findViewById(R.id.rec_donhang);
        rec_timkiem = view.findViewById(R.id.rec_donhangTimkiem);

        layoutTimKiem = view.findViewById(R.id.layouttk);
        tongDonHang = view.findViewById(R.id.tongDonHang);


        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getContext());
        layoutManager1.setOrientation(LinearLayoutManager.VERTICAL);

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext());
        layoutManager2.setOrientation(LinearLayoutManager.VERTICAL);

        rec_donHangHomNay.setLayoutManager(layoutManager);
        rec_AllDonHang.setLayoutManager(layoutManager1);
        rec_timkiem.setLayoutManager(layoutManager2);

        progressBar = view.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);
        layoutTimKiem.setVisibility(View.GONE);
        emptyHomNay.setVisibility(View.GONE);

    }

    @Override
    public void onResume() {
        if (DbQuery.actionCapNhatDonHang) {

        }
        super.onResume();
    }
}