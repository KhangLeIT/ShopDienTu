package com.example.cuahangdientu.activity.fragment;

import static android.content.ContentValues.TAG;

import android.graphics.ColorSpace;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cuahangdientu.R;
import com.example.cuahangdientu.activity.MainActivity;
import com.example.cuahangdientu.adapter.DanhMucTinTucAdapter;
import com.example.cuahangdientu.adapter.LoaiSpHomeAdapter;
import com.example.cuahangdientu.adapter.SanPhamMoiAdapter;
import com.example.cuahangdientu.adapter.TinTucMoiAdapter;
import com.example.cuahangdientu.databinding.FragmentNewsBinding;
import com.example.cuahangdientu.model.LoaiSp;
import com.example.cuahangdientu.model.SanPhamMoi;
import com.example.cuahangdientu.model.TinTuc;
import com.example.cuahangdientu.retrifit.ApiStore;
import com.example.cuahangdientu.retrifit.RetrofitClient;
import com.example.cuahangdientu.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class NewsFragment extends Fragment {
    private RecyclerView recTTmoi, recTThot, recTTdm;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private ApiStore apiStore;
    private ProgressBar progressBar;

    private List<TinTuc> tinTucMoi;
    private TinTucMoiAdapter tinTucMoiAdapter;

    private List<TinTuc> tinTucHot;
    private TinTucMoiAdapter tinTucHotAdapter;
    private LinearLayoutManager layoutManager1;

    private DanhMucTinTucAdapter loaiTTHomeAdapter;
    private List<LoaiSp> mangLoaiTT;


    private FragmentNewsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentNewsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        ((MainActivity)getActivity()).getSupportActionBar().setTitle("Tin Tức");

        AnhXaView(root);
        progressBar.setVisibility(View.GONE);

        LoadTinTucMoi();
        LoadTinTucHot();
        getDMucTT();
        return root;
    }

    private void LoadTinTucMoi() {
        progressBar.setVisibility(View.VISIBLE);
        compositeDisposable.add(apiStore.gettintucmoi(0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        tinTucModel -> {
                            if (tinTucModel.isSuccess())
                            {
                                tinTucMoi = tinTucModel.getResult();
                                tinTucMoiAdapter = new TinTucMoiAdapter(getContext(), tinTucMoi);
                                recTTmoi.setAdapter(tinTucMoiAdapter);
                                progressBar.setVisibility(View.GONE);

                            }
                        },throwable -> Log.e(TAG, "no connect with server " + throwable.getMessage())
                )
        );
    }
    private void LoadTinTucHot()
    {
        progressBar.setVisibility(View.VISIBLE);
        compositeDisposable.add(apiStore.gettintucmoi(5)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        tinTucModel -> {
                            if (tinTucModel.isSuccess())
                            {
                                tinTucHot = tinTucModel.getResult();
                                tinTucHotAdapter = new TinTucMoiAdapter(getContext(), tinTucHot);
                                recTThot.setAdapter(tinTucHotAdapter);

                                progressBar.setVisibility(View.GONE);

                                LinearSnapHelper snapHelper = new LinearSnapHelper();
                                snapHelper.attachToRecyclerView(recTThot);
                                Timer timer = new Timer();
                                timer.schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                        if(layoutManager1.findLastCompletelyVisibleItemPosition() < (tinTucHotAdapter.getItemCount() -1)){
                                            layoutManager1.smoothScrollToPosition(recTThot, new RecyclerView.State(), layoutManager1.findLastCompletelyVisibleItemPosition() + 1);
                                        }
                                        else {
                                            layoutManager1.smoothScrollToPosition(recTThot, new RecyclerView.State(), 0);
                                        }

                                    }
                                }, 5000,5000);

                            }
                        },throwable -> Log.e(TAG, "no connect with server " + throwable.getMessage())
                )
        );
    }

    private void getDMucTT() {
        progressBar.setVisibility(View.VISIBLE);
        compositeDisposable.add(apiStore.getLoaiSp()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        loaiSpModel -> {
                            if (loaiSpModel.isSuccess())
                            {
                                mangLoaiTT = loaiSpModel.getResult();
                                loaiTTHomeAdapter = new DanhMucTinTucAdapter(getContext(), mangLoaiTT);
                                recTTdm.setAdapter(loaiTTHomeAdapter);

                                progressBar.setVisibility(View.GONE);
                            }
                        },throwable -> Log.e(TAG, "no connect with server " + throwable.getMessage())
                )
        );

    }

    private void AnhXaView(View root) {

        apiStore = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiStore.class);

        recTTmoi = root.findViewById(R.id.tintuc_recMoi);
        recTTdm = root.findViewById(R.id.tintuc_recDM);
        recTThot = root.findViewById(R.id.tintuc_recHot);
        progressBar = root.findViewById(R.id.progressBar);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recTTmoi.setHasFixedSize(true);
        recTTmoi.setLayoutManager(layoutManager);
        tinTucMoi = new ArrayList<>();

        layoutManager1 = new LinearLayoutManager(getContext());
        layoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        recTThot.setHasFixedSize(true);
        recTThot.setLayoutManager(layoutManager1);
        tinTucHot = new ArrayList<>();

        LinearLayoutManager layoutDMSp = new LinearLayoutManager(getContext());
        layoutDMSp.setOrientation(LinearLayoutManager.HORIZONTAL);
        recTTdm.setLayoutManager(layoutDMSp);

        mangLoaiTT = new ArrayList<>();

    }

    @Override
    public void onResume() {
        if(tinTucMoi.size()>0)
        {
            tinTucMoiAdapter = new TinTucMoiAdapter(getContext(), tinTucMoi);
            recTTmoi.setAdapter(tinTucMoiAdapter);
        }
        if(tinTucHot.size()>0)
        {
            LoadTinTucHot();
        }
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        compositeDisposable.clear();
        super.onDestroyView();
        binding = null;
    }
}