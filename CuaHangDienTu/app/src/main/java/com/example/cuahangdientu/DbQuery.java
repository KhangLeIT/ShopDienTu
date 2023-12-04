package com.example.cuahangdientu;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.example.cuahangdientu.adapter.ChiTietDonHangAdapter;
import com.example.cuahangdientu.adapter.TinTucMoiAdapter;
import com.example.cuahangdientu.model.GioHang;
import com.example.cuahangdientu.model.ItemDonHang;
import com.example.cuahangdientu.model.SanPhamMoi;
import com.example.cuahangdientu.model.ThongBao;
import com.example.cuahangdientu.model.TinTuc;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DbQuery {
    public static int select_id_dmsp = 0;
    //public static int select_id_spmoi = 0;
    public static String select_ten_danhmuc = "";
    public static SanPhamMoi select_sanpham_chitiet = null;

    public static List<GioHang> mangGioHang = new ArrayList<>();

    public static float giatri_tongtien_donhang = 0;

    public static List<ItemDonHang> itemDonHangList = null;
    public static float chitet_donhang_tongtien = 0;

    public static int trangthai_hoanthanh = 0;
    public static int chitiet_trangthai_donhang = 0;

    public static String chitiet_donhang_ngayhoanthanh ="";

    public static TinTuc chiTietTinTuc = null;

    public static int select_id_dmtt = 0;

    public static String link_video = "";

    public static boolean checkText = false;
    public static List<ThongBao> listThongBao = new ArrayList<>();

    public static String getNgayGio()
    {

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fmd = df.format(Calendar.getInstance().getTime());
        return  fmd;
    }

    public static String formatDate(String str){
        String strDt = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        SimpleDateFormat newFormat = new SimpleDateFormat("EEE, dd-MM-yyyy,HH:mm:ss");
        try {
            Date datetime = format.parse(str);
            strDt = newFormat.format(datetime);
            return strDt;
        } catch (Exception e) {
            return "null";
        }
    }

    public static void checkTextSpace(TextView ed)
    {
        ed.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (ed.getText().toString().contains(" ")) {
                    ed.setError("No Spaces Allowed");
                    checkText=false;
                }
                else {
                    checkText = true;
                }
            }
        });
    }
}
