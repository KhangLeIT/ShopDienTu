package com.example.admincuahangdientu.model;

import java.util.List;

public class AdminModel {
    boolean success;
    String message;
    List<Admin> result;

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

    public List<Admin> getResult() {
        return result;
    }

    public void setResult(List<Admin> result) {
        this.result = result;
    }
}
