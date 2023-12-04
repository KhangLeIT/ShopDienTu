package com.example.cuahangdientu.model;

import java.util.List;

public class UuDaiHotModel {
    boolean success;
    String message;
    List<UuDaiHot> result;

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

    public List<UuDaiHot> getResult() {
        return result;
    }

    public void setResult(List<UuDaiHot> result) {
        this.result = result;
    }
}
