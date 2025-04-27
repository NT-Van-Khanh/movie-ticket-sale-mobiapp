package com.example.ticket_sale.data.network;

public class ApiResponse <T>{
    private int statusCode;
    private String message;
    private T data;
    public enum Status { SUCCESS, ERROR, LOADING }
    public ApiResponse() {
    }

    public ApiResponse(T data, String message, int statusCode) {
        this.data = data;
        this.message = message;
        this.statusCode = statusCode;
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
