package com.example.cuahangdientu.retrifit;



import com.example.cuahangdientu.model.DonHangModel;
import com.example.cuahangdientu.model.HinhAnhList;
import com.example.cuahangdientu.model.HinhAnhModel;
import com.example.cuahangdientu.model.LoaiSpModel;
import com.example.cuahangdientu.model.SanPhamMoiModel;
import com.example.cuahangdientu.model.ThongBaoModel;
import com.example.cuahangdientu.model.TinTuc;
import com.example.cuahangdientu.model.TinTucModel;
import com.example.cuahangdientu.model.UserStoreModel;
import com.example.cuahangdientu.model.UuDaiHotModel;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiStore {
    @GET("getloaisp.php")
    Observable<LoaiSpModel> getLoaiSp();

    @GET("getsanphammoi.php")
    Observable<SanPhamMoiModel> getSanPhamMoi();

    @POST("dangkyuser.php")
    @FormUrlEncoded
    Observable<UserStoreModel> dangKyUser(
      @Field("email") String email,
      @Field("pass") String pass,
      @Field("sdt") String sdt,
      @Field("tenuser") String tenuser,
      @Field("gioitinh") int gioitinh
    );

    @POST("dangnhapuser.php")
    @FormUrlEncoded
    Observable<UserStoreModel> dangnhapUser(
            @Field("email") String email,
            @Field("pass") String pass
    );

    @POST("getdmucsp.php")
    @FormUrlEncoded
    Observable<SanPhamMoiModel> sanphamdanhmuc(
            @Field("page") int page,
            @Field("iddm") int iddm
    );

    @POST("donhang.php")
    @FormUrlEncoded
    Observable<UserStoreModel> taoDonHang(
            @Field("iduser") int iduser,
            @Field("diachi") String diachi,
            @Field("sodienthoai") String sdt,
            @Field("soluong") int sl,
            @Field("tongtien") float tongtien,
            @Field("chitiet") String chitiet,
            @Field("ngaytaodon") String ngaytaodon
    );

    @POST("donhanguser.php")
    @FormUrlEncoded
    Observable<DonHangModel> donhanguser(
            @Field("iduser") int iduser,
            @Field("trangthai") int trangthai
    );


    @POST("timkiemsanpham.php")
    @FormUrlEncoded
    Observable<SanPhamMoiModel> timkiemsanpham(
            @Field("timkiem") String timkiem,
            @Field("sapxep")  String sapxep
    );

    @POST("timkiemtheogia.php")
    @FormUrlEncoded
    Observable<SanPhamMoiModel> timkiemtheogia(
            @Field("gia1") float gia1,
            @Field("gia2") float gia2
    );

    @POST("updateaccount.php")
    @FormUrlEncoded
    Observable<UserStoreModel> updateuser(
            @Field("iduser") int iduser,
            @Field("sdt") String sdt,
            @Field("tenuser") String tenuser
    );

    @POST("actiondonhang.php")
    @FormUrlEncoded
    Observable<DonHangModel> actionuserdonhang(
            @Field("action") int action,
            @Field("iduser") int iduser,
            @Field("iddonhang") int iddonhang,
            @Field("date") String date
    );

    @POST("uudaihot.php")
    @FormUrlEncoded
    Observable<UuDaiHotModel> getuudaihot(
            @Field("action") int action
    );

    @POST("uudaihot.php")
    @FormUrlEncoded
    Observable<SanPhamMoiModel> getsanphamuudai(
            @Field("action") int action,
            @Field("idsp") int idsp
    );

    @POST("tintuc.php")
    @FormUrlEncoded
    Observable<TinTucModel> gettintucmoi(
            @Field("action") int action
    );

    @POST("tintuc.php")
    @FormUrlEncoded
    Observable<TinTucModel> updateTinTuc(
            @Field("action") int action,
            @Field("idtintuc") int idtintuc
    );

    @POST("tintuc.php")
    @FormUrlEncoded
    Observable<HinhAnhModel> gethinhanhtintuc(
            @Field("action") int action,
            @Field("idtintuc") int idtintuc
    );

    @POST("tintuc.php")
    @FormUrlEncoded
    Observable<SanPhamMoiModel> getsanphamtintuc(
            @Field("action") int action,
            @Field("idsp") int idsp
    );

    @POST("tintuc.php")
    @FormUrlEncoded
    Observable<TinTucModel> getTinTucTheoDM(
            @Field("action") int action,
            @Field("page") int page,
            @Field("iddm") int iddm
    );

    @POST("timkiemboloc.php")
    @FormUrlEncoded
    Observable<SanPhamMoiModel> timkiemtheoboloc(
            @Field("gia1") int gia1,
            @Field("gia2") int gia2,
            @Field("iddm") String iddm,
            @Field("sapxep") String sapxep
    );

    @POST("thongbaoDonHang.php")
    @FormUrlEncoded
    Observable<ThongBaoModel> getThongBaoUser(
            @Field("iduser") int iduser

    );

}
