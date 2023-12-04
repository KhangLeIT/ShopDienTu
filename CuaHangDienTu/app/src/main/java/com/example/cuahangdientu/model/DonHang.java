package com.example.cuahangdientu.model;

import java.util.List;

public class DonHang {
    private int id;
    private int iduser;
    private String diachi;
    private String sodienthoai;
    private int soluong;
    private float tongtien;
    private int trangthai;
    private String ngaytaodon;
    private List<ItemDonHang> item;
    private String ngayhoanthanh;
    public String getNgayhoanthanh() {
        return ngayhoanthanh;
    }

    public void setNgayhoanthanh(String ngayhoanthanh) {
        this.ngayhoanthanh = ngayhoanthanh;
    }

    public String getNgaytaodon() {
        return ngaytaodon;
    }

    public void setNgaytaodon(String ngaytaodon) {
        this.ngaytaodon = ngaytaodon;
    }

    public String getSodienthoai() {
        return sodienthoai;
    }

    public void setSodienthoai(String sodienthoai) {
        this.sodienthoai = sodienthoai;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public float getTongtien() {
        return tongtien;
    }

    public void setTongtien(float tongtien) {
        this.tongtien = tongtien;
    }

    public int getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(int trangthai) {
        this.trangthai = trangthai;
    }

    public List<ItemDonHang> getItem() {
        return item;
    }

    public void setItem(List<ItemDonHang> item) {
        this.item = item;
    }
}
