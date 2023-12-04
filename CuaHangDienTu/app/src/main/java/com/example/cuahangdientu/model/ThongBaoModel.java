package com.example.cuahangdientu.model;

import java.util.List;

public class ThongBaoModel {
    boolean success;
    String message;
    List<ThongBao> result;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ThongBao> getResult() {
        return result;
    }

    public void setResult(List<ThongBao> result) {
        this.result = result;
    }
}
