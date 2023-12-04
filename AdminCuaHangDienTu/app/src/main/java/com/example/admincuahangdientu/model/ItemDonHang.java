package com.example.admincuahangdientu.model;

public class ItemDonHang {
    private int iddonhang;
    private int idsp;
    private int soluongmua; // so luong da mua
    private float gia; // gia cua san pham
    private float giabansp; // gia theo so luong
    private String tensp;
    private String hinhanhsp;
    private String motasp;
    private String thongtinsp;
    private int iddm;
    private int slco;

    public int getIddonhang() {
        return iddonhang;
    }

    public void setIddonhang(int iddonhang) {
        this.iddonhang = iddonhang;
    }

    public int getIdsp() {
        return idsp;
    }

    public void setIdsp(int idsp) {
        this.idsp = idsp;
    }

    public int getSoluongmua() {
        return soluongmua;
    }

    public void setSoluongmua(int soluongmua) {
        this.soluongmua = soluongmua;
    }


    public float getGia() {
        return gia;
    }

    public void setGia(float gia) {
        this.gia = gia;
    }

    public float getGiabansp() {
        return giabansp;
    }

    public void setGiabansp(float giabansp) {
        this.giabansp = giabansp;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public String getHinhanhsp() {
        return hinhanhsp;
    }

    public void setHinhanhsp(String hinhanhsp) {
        this.hinhanhsp = hinhanhsp;
    }
}
