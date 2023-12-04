package com.example.cuahangdientu.model;

public class SanPhamMoi {
    private int idsp;
    private String tensp;
    private String hinhanhsp;
    private String motasp;
    private String thongtinsp;
    private float giabansp;
    private int iddm;
    private int slco;
    private String linkvideo;

    public SanPhamMoi(int idsp, String tensp, String hinhanhsp, String motasp, String thongtinsp, float giabansp, int iddm, int slco) {
        this.idsp = idsp;
        this.tensp = tensp;
        this.hinhanhsp = hinhanhsp;
        this.motasp = motasp;
        this.thongtinsp = thongtinsp;
        this.giabansp = giabansp;
        this.iddm = iddm;
        this.slco = slco;
    }


    public String getLinkvideo() {
        return linkvideo;
    }

    public void setLinkvideo(String linkvideo) {
        this.linkvideo = linkvideo;
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

    public String getMotasp() {
        return motasp;
    }

    public void setMotasp(String motasp) {
        this.motasp = motasp;
    }

    public String getThongtinsp() {
        return thongtinsp;
    }

    public void setThongtinsp(String thongtinsp) {
        this.thongtinsp = thongtinsp;
    }

    public float getGiabansp() {
        return giabansp;
    }

    public void setGiabansp(float giabansp) {
        this.giabansp = giabansp;
    }

    public int getIddm() {
        return iddm;
    }

    public void setIddm(int iddm) {
        this.iddm = iddm;
    }

    public int getSlco() {
        return slco;
    }

    public void setSlco(int slco) {
        this.slco = slco;
    }
}
