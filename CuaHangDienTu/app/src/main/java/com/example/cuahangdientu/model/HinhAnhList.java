package com.example.cuahangdientu.model;

public class HinhAnhList {
    int idTT;
    String hinhanh;

    public HinhAnhList(int idTT, String hinhanh) {
        this.idTT = idTT;
        this.hinhanh = hinhanh;
    }

    public int getIdTT() {
        return idTT;
    }

    public void setIdTT(int idTT) {
        this.idTT = idTT;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }
}
