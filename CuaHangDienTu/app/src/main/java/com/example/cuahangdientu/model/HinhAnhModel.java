package com.example.cuahangdientu.model;

import java.util.List;

public class HinhAnhModel {
    boolean success;
    String message;
    List<HinhAnhList> result;

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

    public List<HinhAnhList> getResult() {
        return result;
    }

    public void setResult(List<HinhAnhList> result) {
        this.result = result;
    }
}
