package com.example.admincuahangdientu.model;

public class LoaiSp {
    private int iddm;
    private String tensanpham;
    private String hinhanh;

    public int getId() {
        return iddm;
    }

    public void setId(int id) {
        this.iddm = id;
    }

    public String getTensanpham() {
        return tensanpham;
    }

    public void setTensanpham(String tensanpham) {
        this.tensanpham = tensanpham;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }
}
