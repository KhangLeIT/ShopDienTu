package com.example.admincuahangdientu.t_donhang;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import com.example.admincuahangdientu.R;
import com.example.admincuahangdientu.adapter.ChiTietDonHangAdapter;
import com.example.admincuahangdientu.model.ItemDonHang;
import com.example.admincuahangdientu.model.UserStore;
import com.example.admincuahangdientu.query.DbQuery;
import com.example.admincuahangdientu.retrifit.APIadminStore;
import com.example.admincuahangdientu.retrifit.RetrofitClient;
import com.example.admincuahangdientu.utils.Utils;
import com.google.android.material.navigation.NavigationView;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Objects;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ChiTietDonHangActivity extends AppCompatActivity {

    private RecyclerView recChiTietDH;
    private TextView txt_tongtien, chitietTrangThai, txtTrangthai;
    private Toolbar toolbar;
    ChiTietDonHangAdapter adapter;

    AppCompatButton capnhatBtn, thongTinUerBtn, chiTietCapNhatBtn;
    NavigationView navView, naViewUser;
    RadioButton radioHuy, radioHoanThanh, radioGiaoHang, radioDuyet, radioChoDuyet;
    int trangthai = 0;

    ImageView gioitinh;
    TextView tenUser, emailUser, sdtUser, diaChi, iduser;

    int idOfUser, idDonHang;
    String noidungThongBao = "";

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private APIadminStore apiStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_don_hang);

        AnhXaView();
        DbQuery.actionCapNhatDonHang = false;
        ActionToolbar();
        loadAdapterDonHang();
        trangthai = DbQuery.chitiet_trangthai_donhang;
        getUserDonHang();
        ActionButton();

        if (trangthai == 0) {
            radioChoDuyet.setChecked(true);
        }
        if(trangthai ==1){
            radioDuyet.setChecked(true);
        }
        if(trangthai == -1){
            radioHuy.setChecked(true);
        }if(trangthai ==2){
            radioGiaoHang.setChecked(true);
        }
        if(trangthai ==3) {
            radioHoanThanh.setChecked(true);
        }
    }

    @SuppressLint("SetTextI18n")
    private void getUserDonHang() {
        compositeDisposable.add(apiStore.getUser("getUser", DbQuery.select_id_user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        userStoreModel -> {
                            if (userStoreModel.isSuccess())
                            {
                                UserStore userStore = userStoreModel.getResult().get(0);
                                idOfUser = userStore.getIduser();
                                tenUser.setText(userStore.getTenuser());
                                if(userStore.getGioitinh() ==0){
                                    gioitinh.setImageResource(R.drawable.user_icon_nu);
                                }else{
                                    gioitinh.setImageResource(R.drawable.user_icon_nam);
                                }
                                sdtUser.setText(DbQuery.SDT_user);
                                emailUser.setText(userStore.getEmail());
                                diaChi.setText(DbQuery.diaChi_user);
                                iduser.setText("ID: " + userStore.getIduser());

                            }
                        },throwable -> Log.e(TAG, "no connect with server " + throwable.getMessage())
                )
        );
    }

    @SuppressLint("SetTextI18n")
    private void ActionButton() {
        capnhatBtn.setOnClickListener(view -> {
            if(naViewUser.getVisibility() == View.GONE) {
                if (navView.getVisibility() == View.GONE) {
                    capnhatBtn.setText("Đóng Cập Nhật");
                    capnhatBtn.setBackgroundColor(Color.RED);
                    navView.setVisibility(View.VISIBLE);
                } else {
                    capnhatBtn.setText("Cập Nhật Trạng Thái");
                    capnhatBtn.setBackgroundResource(R.drawable.backgroudbtn);
                    navView.setVisibility(View.GONE);
                }
            }
            else {
                Toast.makeText(this, "bạn chưa đóng thông tin User", Toast.LENGTH_SHORT).show();
            }
        });
        thongTinUerBtn.setOnClickListener(view -> {
            if (navView.getVisibility() == View.GONE) {
                if (naViewUser.getVisibility() == View.GONE) {
                    thongTinUerBtn.setText("Đóng thông tin");
                    thongTinUerBtn.setBackgroundColor(Color.RED);
                    naViewUser.setVisibility(View.VISIBLE);
                } else {
                    thongTinUerBtn.setText("Thông tin user");
                    thongTinUerBtn.setBackgroundResource(R.drawable.backgroudbtn);
                    naViewUser.setVisibility(View.GONE);
                }
            }
            else {
                Toast.makeText(this, "bạn chưa đóng Cập Nhật", Toast.LENGTH_SHORT).show();
            }
        });

        chiTietCapNhatBtn.setOnClickListener(view -> {
            capnhatBtn.setText("Cập Nhật Trạng Thái");
            capnhatBtn.setBackgroundResource(R.drawable.backgroudbtn);
            navView.setVisibility(View.GONE);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Bạn cập nhật trạng thái này Này?");

            builder.setPositiveButton("Đồng ý", (dialogInterface, i) -> {
                capnhatTrangThaiDonHang();
                thongBaoDonHangChoUser();
            });
            builder.setNegativeButton("Hủy", (dialogInterface, i) -> {
            });
            builder.show();

        });

        radioHuy.setOnClickListener(view ->trangthai = -1);
        radioChoDuyet.setOnClickListener(view -> trangthai = 0);
        radioDuyet.setOnClickListener(view -> trangthai = 1);
        radioGiaoHang.setOnClickListener(view -> trangthai = 2);
        radioHoanThanh.setOnClickListener(view -> trangthai=3);

    }

    private void thongBaoDonHangChoUser() {
        String ngaygio = DbQuery.getNgayGioDonHang();
        ngaygio = DbQuery.formatDateThongBao(ngaygio);
        if(trangthai == -1) noidungThongBao = "Đon Hàng" +idDonHang+ " Của Bạn Bị Hủy Vào Lúc " +  ngaygio;

        if(trangthai == 0) noidungThongBao = "Đơn Hàng "+idDonHang+" Của Bạn Đang Ở Trạng Thá Chờ Duyệt Vào lúc " + ngaygio;

        if(trangthai == 1) noidungThongBao = "Đơn Hàng " + idDonHang+" Của Bạn Đã Được Duyệt và Đang Lên Đơn Vào Lúc " + ngaygio;

        if(trangthai == 2) noidungThongBao = "Đơn Hàng " + idDonHang + " Của Bạn Đang Được Giao Cho Shipper Vào Lúc " + ngaygio;

        if(trangthai ==3) noidungThongBao = "Đơn Hàng " + idDonHang + " Của Bạn Được Nhân Viên Xác Nhận Hòa Thành Vào Lúc "+ ngaygio;

        compositeDisposable.add(apiStore.thongBaoChoUser("thongbaoUser",idOfUser,idDonHang,noidungThongBao)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        messageModel -> {
                            if (messageModel.isSuccess())
                            {
                                Toast.makeText(this, "gửi thông báo thành công", Toast.LENGTH_SHORT).show();
                            }
                        },throwable -> Log.e(TAG, "no connect with server " + throwable.getMessage())
                )
        );
    }

    @SuppressLint("SetTextI18n")
    private void capnhatTrangThaiDonHang() {
        String ngayhoanthanh = "'" +  DbQuery.getNgayGioDonHang() + "'";
        String finalNgayhoanthanh = DbQuery.getNgayGioDonHang();
        if(trangthai<3){
            ngayhoanthanh = "NULL";
        }

        compositeDisposable.add(apiStore.updateTrangThai("updateTrangThai",DbQuery.select_id_donhang,trangthai, ngayhoanthanh)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        messageModel -> {
                            if (messageModel.isSuccess())
                            {

                                DbQuery.actionCapNhatDonHang = true;
                                if(trangthai ==-1)
                                {
                                    txtTrangthai.setText("Đã Hủy");
                                    txtTrangthai.setTextColor(Color.RED);
                                    chitietTrangThai.setText(finalNgayhoanthanh);
                                    chitietTrangThai.setTextColor(Color.RED);
                                }
                                if(trangthai == 3)
                                {
                                    txtTrangthai.setText("Đã Nhận");
                                    txtTrangthai.setTextColor(Color.BLUE);
                                    chitietTrangThai.setText(finalNgayhoanthanh);
                                    chitietTrangThai.setTextColor(Color.BLUE);
                                }

                                if(trangthai == 0)
                                {
                                    chitietTrangThai.setText("Đang Chờ Duyệt");
                                    chitietTrangThai.setTextColor(Color.YELLOW);
                                }
                                if(trangthai ==1)
                                {
                                    chitietTrangThai.setText("Đã duyệt");
                                    chitietTrangThai.setTextColor(Color.GREEN);
                                }
                                if(trangthai == 2)
                                {
                                    chitietTrangThai.setText("Đang Giao Hàng");
                                    chitietTrangThai.setTextColor(Color.YELLOW);
                                }
                            }
                        },throwable -> Log.e(TAG, "no connect with server " + throwable.getMessage())
                )
        );
    }


    @SuppressLint("SetTextI18n")
    private void loadAdapterDonHang() {
        if(DbQuery.select_item_donhang.size() > 0)
        {
            idDonHang = DbQuery.select_item_donhang.get(DbQuery.select_item_donhang.size()-1).getIddonhang();
            Objects.requireNonNull(getSupportActionBar()).setTitle("Đơn Hàng " + idDonHang);

            List<ItemDonHang> itemDonHangList = DbQuery.select_item_donhang;

            adapter = new ChiTietDonHangAdapter(this, itemDonHangList);
            recChiTietDH.setAdapter(adapter);

            float ttien = DbQuery.tongTienDonHang;
            DecimalFormat formatter = new DecimalFormat("###,###,###");
            txt_tongtien.setText(formatter.format(ttien) + " VNĐ");

        }
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Đơn Hàng");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(view -> finish());
    }

    @SuppressLint("SetTextI18n")
    private void AnhXaView() {
        apiStore = RetrofitClient.getInstance(Utils.BASE_URL).create(APIadminStore.class);

        toolbar = findViewById(R.id.toolbar);
        txt_tongtien = findViewById(R.id.chitiet_TongTien);
        recChiTietDH = findViewById(R.id.rec_chiTietDH);

        chitietTrangThai = findViewById(R.id.chitiet_trangthai);
        txtTrangthai = findViewById(R.id.chitiet_txtTrangThai);

        capnhatBtn = findViewById(R.id.capnhatTrangThai);
        navView = findViewById(R.id.navView);
        navView.setVisibility(View.GONE);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recChiTietDH.setLayoutManager(layoutManager);

        radioHuy = findViewById(R.id.chiTiet_HuyDon);
        radioGiaoHang = findViewById(R.id.chiTiet_GiaoHang);
        radioDuyet = findViewById(R.id.chiTiet_Duyet);
        radioHoanThanh = findViewById(R.id.chiTiet_ThanhCong);
        radioChoDuyet = findViewById(R.id.chiTiet_choDuyet);
        chiTietCapNhatBtn = findViewById(R.id.chiTiet_btnCapNhat);

        tenUser = findViewById(R.id.chiTiet_tenUser);
        gioitinh = findViewById(R.id.chiTiet_gioitinh);
        sdtUser = findViewById(R.id.chiTiet_SDT);
        emailUser = findViewById(R.id.chiTiet_Email);
        diaChi = findViewById(R.id.chiTiet_DiaChi);
        iduser = findViewById(R.id.chiTiet_iduser);

        thongTinUerBtn = findViewById(R.id.chitiet_thongtinUser);
        naViewUser = findViewById(R.id.navViewUser);
        naViewUser.setVisibility(View.GONE);


        if(DbQuery.chitiet_trangthai_donhang ==-1 && DbQuery.chitiet_donhang_ngayhoanthanh != null  )
        {
            txtTrangthai.setText("Đã Hủy");
            txtTrangthai.setTextColor(Color.RED);
            String str = DbQuery.formatDate(DbQuery.chitiet_donhang_ngayhoanthanh);
            chitietTrangThai.setText(str);
            chitietTrangThai.setTextColor(Color.RED);
        }
        else if(DbQuery.chitiet_trangthai_donhang == 3 && DbQuery.chitiet_donhang_ngayhoanthanh != null)
        {
            txtTrangthai.setText("Đã Nhận");
            txtTrangthai.setTextColor(Color.BLUE);
            String str = DbQuery.formatDate(DbQuery.chitiet_donhang_ngayhoanthanh);
            chitietTrangThai.setText(str);
            chitietTrangThai.setTextColor(Color.BLUE);
        }
        else{
            if(DbQuery.chitiet_trangthai_donhang == 0)
            {
                chitietTrangThai.setText("Đang Chờ Duyệt");
                chitietTrangThai.setTextColor(Color.YELLOW);
            }
            else if( DbQuery.chitiet_trangthai_donhang ==1)
            {
                chitietTrangThai.setText("Đã duyệt");
                chitietTrangThai.setTextColor(Color.GREEN);
            }
            else if(DbQuery.chitiet_trangthai_donhang == 2)
            {
                chitietTrangThai.setText("Đang Giao Hàng");
                chitietTrangThai.setTextColor(Color.YELLOW);
            }
        }



    }
}