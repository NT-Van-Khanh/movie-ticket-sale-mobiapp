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
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(data, null, 200);
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(null, message, 400);
    }

    public String getMessage() {
        return message;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
