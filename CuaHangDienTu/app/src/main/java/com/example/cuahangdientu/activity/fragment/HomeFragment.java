package com.example.cuahangdientu.activity.fragment;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cuahangdientu.DbQuery;
import com.example.cuahangdientu.R;
import com.example.cuahangdientu.activity.GioHangActivity;
import com.example.cuahangdientu.activity.TimKiemActivity;
import com.example.cuahangdientu.adapter.LoaiSpHomeAdapter;
import com.example.cuahangdientu.adapter.SanPhamMoiAdapter;
import com.example.cuahangdientu.adapter.UuDaiHotAdapter;
import com.example.cuahangdientu.databinding.FragmentHomeBinding;
import com.example.cuahangdientu.model.LoaiSp;
import com.example.cuahangdientu.model.SanPhamMoi;
import com.example.cuahangdientu.model.UuDaiHot;
import com.example.cuahangdientu.retrifit.ApiStore;
import com.example.cuahangdientu.retrifit.RetrofitClient;
import com.example.cuahangdientu.utils.Utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private FloatingActionButton shoppingClick, searchClick;
    private NotificationBadge cartSL;

    private RecyclerView recUuDaiHot;
    private RecyclerView recyclerHome;
    private RecyclerView recDMucSP;

    private TextView rec_left, rec_right;
    // private LoaiSpAdapter loaiSpAdapter;
    private LoaiSpHomeAdapter loaiSpHomeAdapter;
    private List<LoaiSp> mangLoaiSp;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private ApiStore apiStore;

    private List<SanPhamMoi> mangSpMoi;
    private SanPhamMoiAdapter sanPhamMoiAdapter;

    private List<UuDaiHot> uuDaiHotList;
    private UuDaiHotAdapter uuDaiHotAdapter;
    private LinearLayoutManager layoutManagerUD;

    private ProgressBar progressBar;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        apiStore = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiStore.class);

        AnhXaView(root);
        progressBar.setVisibility(View.VISIBLE);
        if (isConnected(getContext()))
        {
            ActionViewFlipper();
            getLoaiSanPhamHome();
            getSanPhamMoiHome();

        }
        else
        {
            Toast.makeText(getContext(), "No internet, please connect to the internet", Toast.LENGTH_SHORT).show();

        }

        return root;
    }


    private void getLoaiSanPhamHome() {
        progressBar.setVisibility(View.VISIBLE);
        compositeDisposable.add(apiStore.getLoaiSp()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        loaiSpModel -> {
                            if (loaiSpModel.isSuccess())
                            {
                                mangLoaiSp = loaiSpModel.getResult();
                                loaiSpHomeAdapter = new LoaiSpHomeAdapter(getContext(), mangLoaiSp);
                                recDMucSP.setAdapter(loaiSpHomeAdapter);

                                progressBar.setVisibility(View.GONE);
                            }
                        },throwable -> Log.e(TAG, "no connect with server " + throwable.getMessage())
                )
        );

    }

    private void getSanPhamMoiHome()
    {
        progressBar.setVisibility(View.VISIBLE);
        compositeDisposable.add(apiStore.getSanPhamMoi()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        sanPhamMoiModel -> {
                            if (sanPhamMoiModel.isSuccess())
                            {
                                mangSpMoi = sanPhamMoiModel.getResult();
                                sanPhamMoiAdapter = new SanPhamMoiAdapter(getContext(), mangSpMoi);
                                recyclerHome.setAdapter(sanPhamMoiAdapter);
                                progressBar.setVisibility(View.GONE);
                            }
                        },throwable -> Log.e(TAG, "no connect with server " + throwable.getMessage())
                )
        );
    }


    private boolean isConnected (Context context)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if(wifi != null && wifi.isConnected() || mobile != null && mobile.isConnected())
        {
            return true;
        }
        else{
            return false;
        }

    }
    private void ActionViewFlipper() {
        progressBar.setVisibility(View.VISIBLE);
        compositeDisposable.add(apiStore.getuudaihot(0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        uuDaiHotModel -> {
                            if(uuDaiHotModel.isSuccess())
                            {
                                uuDaiHotList = uuDaiHotModel.getResult();
                                uuDaiHotAdapter = new UuDaiHotAdapter(getContext(), uuDaiHotList);
                                recUuDaiHot.setAdapter(uuDaiHotAdapter);
                                progressBar.setVisibility(View.GONE);
                                LinearSnapHelper snapHelper = new LinearSnapHelper();
                                snapHelper.attachToRecyclerView(recUuDaiHot);
                                Timer timer = new Timer();
                                timer.schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                        if(layoutManagerUD.findLastCompletelyVisibleItemPosition() < (uuDaiHotAdapter.getItemCount() -1)){
                                            layoutManagerUD.smoothScrollToPosition(recUuDaiHot, new RecyclerView.State(), layoutManagerUD.findLastCompletelyVisibleItemPosition() + 1);
                                        }
                                        else {
                                            layoutManagerUD.smoothScrollToPosition(recUuDaiHot, new RecyclerView.State(), 0);
                                        }

                                    }
                                }, 6000,6000);


                            }
                        },
                        throwable -> {
                            Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
                        }

                )
        );
    }


    private void AnhXaView(View view) {

        recUuDaiHot = view.findViewById(R.id.home_recUuDai);
        recyclerHome = view.findViewById(R.id.recSpMoiHome);
        shoppingClick = view.findViewById(R.id.shoppingCart);
        cartSL = view.findViewById(R.id.cart_soluong);
        searchClick= view.findViewById(R.id.timkiemsp);

        progressBar = view.findViewById(R.id.progressBar);

        //drawerLayout = view.findViewById(R.id.drawerLayout);
        recDMucSP = view.findViewById(R.id.recDMsp);
        // khoi tao list
        mangLoaiSp = new ArrayList<>();
        mangSpMoi = new ArrayList<>();
        uuDaiHotList = new ArrayList<>();
        if(DbQuery.mangGioHang == null)
        {
            DbQuery.mangGioHang = new ArrayList<>();
        }

        // danh muc san pham home
        LinearLayoutManager layoutDMSp = new LinearLayoutManager(getContext());
        layoutDMSp.setOrientation(LinearLayoutManager.HORIZONTAL);
        recDMucSP.setHasFixedSize(true);
        recDMucSP.setLayoutManager(layoutDMSp);

        //san pham moi nhat home
        RecyclerView.LayoutManager layoutSanPhamMoi = new GridLayoutManager(getContext(), 2);
        recyclerHome.setLayoutManager(layoutSanPhamMoi);
        recyclerHome.setHasFixedSize(true);


        // uu dai hot
        layoutManagerUD = new LinearLayoutManager(getContext());
        layoutManagerUD.setOrientation(LinearLayoutManager.HORIZONTAL);
        recUuDaiHot.setLayoutManager(layoutManagerUD);



        if(DbQuery.mangGioHang != null)
        {
            cartSL.setText(String.valueOf(DbQuery.mangGioHang.size()));
        }

        shoppingClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), GioHangActivity.class);
                startActivity(intent);
            }
        });

        searchClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), TimKiemActivity.class);
                startActivity(intent);
            }
        });



    }

    @Override
    public void onResume() {
        super.onResume();
        if(DbQuery.mangGioHang != null)
        {
            cartSL.setText(String.valueOf(DbQuery.mangGioHang.size()));
        }else
            cartSL.setText(String.valueOf(0));
    }

    @Override
    public void onDestroyView() {
        compositeDisposable.clear();

        super.onDestroyView();
        binding = null;
    }
}