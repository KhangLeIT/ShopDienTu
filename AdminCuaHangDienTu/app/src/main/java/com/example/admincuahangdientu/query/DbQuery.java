package com.example.admincuahangdientu.query;

import com.example.admincuahangdientu.model.ItemDonHang;
import com.example.admincuahangdientu.model.LoaiSp;
import com.example.admincuahangdientu.model.SanPham;
import com.example.admincuahangdientu.model.TinTuc;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DbQuery {

    public static String linkVideo_check = null;
    public static int select_id_dmsp = 0;
    public static List<SanPham> sanphamBack = new ArrayList<>();
    public static List<LoaiSp> danhMucBack = new ArrayList<>();
    public static SanPham select_edit_sanpham = null;
    public static LoaiSp select_edit_danhmuc = null;

    public static int select_id_TT = 0;
    public static List<TinTuc> tinTucBack = new ArrayList<>();
    public static TinTuc select_edit_tintuc = null;
    public static int position_edit_tintuc = -1;
    public static int position_edit_sanpham = -1;

    public static boolean tintuc_edit_change_YesOrNo = false;
    public static boolean sanpham_edit_change_YesOrNo = false;

    public static List<ItemDonHang> select_item_donhang = new ArrayList<>();
    public static float tongTienDonHang = 0;
    public static int chitiet_trangthai_donhang = 0;
    public static String chitiet_donhang_ngayhoanthanh;
    public static int select_id_user = 1;
    public static String SDT_user;
    public static String diaChi_user;
    public static int select_id_donhang;
    public static boolean actionCapNhatDonHang = false;


    public static String getNgayGio()
    {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String fmd = df.format(Calendar.getInstance().getTime());
        return  fmd;
    }

    public static String getNgayGioDonHang()
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

    public static String formatDateThongBao(String str){
        String strDt = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        SimpleDateFormat newFormat = new SimpleDateFormat(" HH:mm:ss - EEE, dd-MM-yyyy");
        try {
            Date datetime = format.parse(str);
            strDt = newFormat.format(datetime);
            return strDt;
        } catch (Exception e) {
            return "null";
        }
    }

}
