package com.example.cuahangdientu.model;

public class GioHang {
    private int idsp;
    private String tensp;
    private String hinhanhsp;
    private float giahientai;

    public float getGiahientai() {
        return giahientai;
    }

    public void setGiahientai(float giahientai) {
        this.giahientai = giahientai;
    }

    private float giabansp;
    private int soluong;

    public GioHang() {
    }

    public int getIdsp() {
        return idsp;
    }

    public void setIdsp(int idsp) {
        this.idsp = idsp;
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

    public float getGiabansp() {
        return giabansp;
    }

    public void setGiabansp(float giabansp) {
        this.giabansp = giabansp;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }
}
