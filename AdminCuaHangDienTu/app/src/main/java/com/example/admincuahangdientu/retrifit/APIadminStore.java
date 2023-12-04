package com.example.admincuahangdientu.retrifit;

import com.example.admincuahangdientu.model.AdminModel;
import com.example.admincuahangdientu.model.DonHangModel;
import com.example.admincuahangdientu.model.LoaiSpModel;
import com.example.admincuahangdientu.model.MessageModel;
import com.example.admincuahangdientu.model.SanPhamModel;
import com.example.admincuahangdientu.model.ThongKe;
import com.example.admincuahangdientu.model.ThongKeModel;
import com.example.admincuahangdientu.model.TinTucModel;
import com.example.admincuahangdientu.model.UserStoreModel;

import java.util.Date;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface APIadminStore {
    @POST("adminDanhMuc.php")
    @FormUrlEncoded
    Observable<LoaiSpModel> getLoaiSp(
            @Field("action") String  action
    );


    @Multipart
    @POST("uploadHinhAnh.php")
    Call<MessageModel> uploadFile(
            @Part MultipartBody.Part file
           // @Part("file") RequestBody name
    );


    @POST("getdmucsp.php")
    @FormUrlEncoded
    Observable<SanPhamModel> sanphamdanhmuc(
            @Field("page") int page,
            @Field("iddm") int iddm
    );

    @POST("adminSanPham.php")
    @FormUrlEncoded
    Observable<MessageModel> themsanpham(
            @Field("action") String action,
            @Field("tensp") String tensp,
            @Field("hinhanh") String hinhanh,
            @Field("mota") String mota,
            @Field("thongtin") String thongtin,
            @Field("giaban") Float giaban,
            @Field("iddm") int iddm,
            @Field("slco") int slco,
            @Field("linkvideo") String linkvideo
    );

    @POST("adminSanPham.php")
    @FormUrlEncoded
    Observable<MessageModel> backSanPhamDelete(
            @Field("action") String action,
            @Field("idsp") int idsp,
            @Field("tensp") String tensp,
            @Field("hinhanh") String hinhanh,
            @Field("mota") String mota,
            @Field("thongtin") String thongtin,
            @Field("giaban") Float giaban,
            @Field("iddm") int iddm,
            @Field("slco") int slco,
            @Field("linkvideo") String linkvideo
    );

    @POST("adminSanPham.php")
    @FormUrlEncoded
    Observable<MessageModel> deletesanpham(
           @Field("action") String action,
           @Field("idsp") int idsp
    );

    @POST("adminSanPham.php")
    @FormUrlEncoded
    Observable<MessageModel> updatesanpham(
            @Field("action") String action,
            @Field("idsp") int idsp,
            @Field("noidungupdate") String noidungupdate
    );

    @Multipart
    @POST("updateHinhAnh.php")
    Call<MessageModel> updatefile(
            @Part MultipartBody.Part file
            // @Part("file") RequestBody name
    );

    @POST("adminSanPham.php")
    @FormUrlEncoded
    Observable<MessageModel> deleteAnhStore(
            @Field("action") String action,
            @Field("hinhanh") String hinhanh
    );

    @POST("adminDanhMuc.php")
    @FormUrlEncoded
    Observable<MessageModel> themdanhmuc(
            @Field("action") String action,
            @Field("tenDM") String tenDM,
            @Field("hinhanh") String hinhanh
    );

    @POST("adminDanhMuc.php")
    @FormUrlEncoded
    Observable<MessageModel> deleteDanhMuc(
            @Field("action") String action,
            @Field("iddm") int iddm
    );

    @POST("adminDanhMuc.php")
    @FormUrlEncoded
    Observable<MessageModel> backDanhMuc(
            @Field("action") String action,
            @Field("iddm") int iddm,
            @Field("tenDM") String tenDM,
            @Field("hinhanh") String hinhanh
    );

    @POST("adminDanhMuc.php")
    @FormUrlEncoded
    Observable<MessageModel> updateDanhMuc(
            @Field("action") String action,
            @Field("iddm") int iddm,
            @Field("querySet") String querySet
    );

    @POST("adminDanhMuc.php")
    @FormUrlEncoded
    Observable<MessageModel> deleteAnhDanhMuc(
            @Field("action") String action,
            @Field("hinhanh") String hinhanh
    );

    @POST("adminTinTuc.php")
    @FormUrlEncoded
    Observable<MessageModel> themTinTucMoi(
            @Field("action") String action,
            @Field("tieude") String tieude,
            @Field("hinhanh") String hinhanh,
            @Field("noidung") String noidung,
            @Field("iddm") int iddm,
            @Field("idsp") int idsp,
            @Field("thoigian") String thoigian
    );

    @POST("adminTinTuc.php")
    @FormUrlEncoded
    Observable<TinTucModel> getTinTucTheoDM(
            @Field("action") String action,
            @Field("page") int page,
            @Field("iddm") int iddm
    );

    @POST("adminTinTuc.php")
    @FormUrlEncoded
    Observable<MessageModel> deleteTinTuc(
            @Field("action") String action,
            @Field("idTT") int idTT
    );

    @POST("adminTinTuc.php")
    @FormUrlEncoded
    Observable<MessageModel> backTinTuc(
            @Field("action") String action,
            @Field("id") int id,
            @Field("tieude") String tieude,
            @Field("hinhanh") String hinhanh,
            @Field("noidung") String noidung,
            @Field("iddm") int iddm,
            @Field("luotxem") int luotxem,
            @Field("idsp") int idsp,
            @Field("thoigian") String thoigian
    );

    @POST("adminTinTuc.php")
    @FormUrlEncoded
    Observable<MessageModel> updateTinTuc(
            @Field("action") String action,
            @Field("idTT") int iddm,
            @Field("noidungupdate") String noidungupdate
    );
    @POST("adminDonHang.php")
    @FormUrlEncoded
    Observable<DonHangModel> getDonHang(
            @Field("action") String action,
            @Field("thoigian") String thoigian,
            @Field("trangthai") int trangthai,
            @Field("sapxep") String sapxep
    );

    @POST("adminDonHang.php")
    @FormUrlEncoded
    Observable<DonHangModel> timkiemDonHang(
            @Field("action") String action,
            @Field("id") int id
    );

    @POST("adminDonHang.php")
    @FormUrlEncoded
    Observable<UserStoreModel> getUser(
            @Field("action") String action,
            @Field("iduser") int iduser
    );

    @POST("adminDonHang.php")
    @FormUrlEncoded
    Observable<MessageModel> updateTrangThai(
            @Field("action") String action,
            @Field("id") int id,
            @Field("trangthai") int trangthai,
            @Field("ngayhoanthanh") String ngayhoanthanh
    );

    @POST("adminDonHang.php")
    @FormUrlEncoded
    Observable<MessageModel> thongBaoChoUser(
            @Field("action") String action,
            @Field("iduser") int iduser,
            @Field("iddonhang") int iddonhang,
            @Field("noidung") String noidung
    );


    @POST("adminSignIn.php")
    @FormUrlEncoded
    Observable<AdminModel> sigInAdmin(
            @Field("taikhoan") String taikhoan,
            @Field("matkhau") String matkhau

    );

    @GET("adminThongKe.php")
    Observable<ThongKeModel> getThongKe();

    @GET("thongketheothang.php")
    Observable<ThongKeModel> getThongKeTheoThang();

}
