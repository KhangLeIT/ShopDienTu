package com.example.cuahangdientu.model;

import java.util.List;

public class TinTucModel {
    boolean success;
    String message;
    List<TinTuc> result;

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

    public List<TinTuc> getResult() {
        return result;
    }

    public void setResult(List<TinTuc> result) {
        this.result = result;
    }
}
