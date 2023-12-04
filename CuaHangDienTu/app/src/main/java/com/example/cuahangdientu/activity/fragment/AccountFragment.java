package com.example.cuahangdientu.activity.fragment;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.cuahangdientu.DbQuery;
import com.example.cuahangdientu.R;
import com.example.cuahangdientu.activity.BeginActivity;
import com.example.cuahangdientu.activity.DonHangUserActivity;
import com.example.cuahangdientu.activity.MainActivity;
import com.example.cuahangdientu.activity.ThongBaoDonHangActivity;
import com.example.cuahangdientu.activity.UserAccountActivity;
import com.example.cuahangdientu.adapter.ThongBaoDonHangAdapter;
import com.example.cuahangdientu.databinding.FragmentAccountBinding;
import com.example.cuahangdientu.model.ThongBao;
import com.example.cuahangdientu.model.UserStore;
import com.example.cuahangdientu.retrifit.ApiStore;
import com.example.cuahangdientu.retrifit.RetrofitClient;
import com.example.cuahangdientu.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class AccountFragment extends Fragment {

    private FragmentAccountBinding binding;
    private TextView userName, userEmail, accHello, sizeThongbao;
    private Button btnDonHang, btnCapNhatAccount, btnLichSu, btnThongBao;
    private AppCompatButton btnDangXuat;
    private ImageView imgUser;

    ApiStore apiStore;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    List<ThongBao> thongBaoList = new ArrayList<>();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAccountBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        ((MainActivity)getActivity()).getSupportActionBar().setTitle("My Account");

        AnhXaView(root);
        ThongTinUser();
        ActionButton(root);
        getThongBaoDonHang();
        return root;
    }

    private void getThongBaoDonHang() {
        compositeDisposable.add(apiStore.getThongBaoUser(Utils.user_current.getIduser())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        thongBaoModel -> {
                            if(thongBaoModel.isSuccess()){
                                thongBaoList = thongBaoModel.getResult();
                                DbQuery.listThongBao = thongBaoList;
                                sizeThongbao.setText(String.valueOf(thongBaoList.size()));
                            }
                            else{

                            }
                        }
                        ,throwable -> Log.e(TAG, "no connect with server " + throwable.getMessage())
                )
        );
    }

    private void ActionButton(View root) {

        btnDonHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbQuery.trangthai_hoanthanh = 0;
                Intent intent = new Intent(getContext(), DonHangUserActivity.class);
                startActivity(intent);
            }
        });
        btnLichSu.setOnClickListener(view -> {
            DbQuery.trangthai_hoanthanh = 3;
            Intent intent = new Intent(getContext(), DonHangUserActivity.class);
            startActivity(intent);
        });

        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dangXuat();

            }
        });
        btnCapNhatAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), UserAccountActivity.class);
                startActivity(intent);
            }
        });

        btnThongBao.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), ThongBaoDonHangActivity.class);
            startActivity(intent);
        });
    }

    private void AnhXaView(View root) {
        apiStore = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiStore.class);
        userName = root.findViewById(R.id.acc_userName);
        userEmail = root.findViewById(R.id.acc_email);
        accHello = root.findViewById(R.id.acc_hello);
        btnDonHang = root.findViewById(R.id.account_DonHang);
        btnCapNhatAccount = root.findViewById(R.id.account_capnhat);
        btnLichSu = root.findViewById(R.id.account_lichsu_muahang);
        btnThongBao = root.findViewById(R.id.account_thongbao);
        btnDangXuat = root.findViewById(R.id.account_DangXuat);
        sizeThongbao = root.findViewById(R.id.account_sizeThongBao);
        imgUser = root.findViewById(R.id.account_img);
    }

    private void ThongTinUser() {
        if(Utils.user_current != null)
        {
            accHello.setText("Xin Chào !");
            userEmail.setText(Utils.user_current.getEmail());
            userName.setText(Utils.user_current.getTenuser());
            if(Utils.user_current.getGioitinh()==0)
            {
                imgUser.setImageResource(R.drawable.user_icon_nu);
            }
            else if(Utils.user_current.getGioitinh() ==1){
                imgUser.setImageResource(R.drawable.user_icon_nam);
            }
        }
    }

    private void dangXuat() {
        Paper.init(getContext());
        Paper.book().delete("email");
        Paper.book().delete("pass");
        Paper.book().delete("NameUser");
        Utils.user_current = new UserStore();
        Intent intent = new Intent(getContext(), BeginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onResume() {
        ThongTinUser();
        super.onResume();
    }
}