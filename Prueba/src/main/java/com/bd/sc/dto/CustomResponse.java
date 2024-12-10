package com.bd.sc.dto;

public class CustomResponse <T extends Object> {
    private boolean ok;
    private String message;
    private T data;

    public CustomResponse() {
    }

    public CustomResponse(boolean ok, String message, T data) {
        this.ok = ok;
        this.message = message;
        this.data = data;
    }

    public CustomResponse(T data) {
        this.ok = true;
        this.message = "";
        this.data = data;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
