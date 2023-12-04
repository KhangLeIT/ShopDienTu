package com.example.admincuahangdientu.model;

import java.util.List;

public class UserStoreModel {
    private boolean success;
    private String message;
    private List<UserStore> result;

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

    public List<UserStore> getResult() {
        return result;
    }

    public void setResult(List<UserStore> result) {
        this.result = result;
    }
}
