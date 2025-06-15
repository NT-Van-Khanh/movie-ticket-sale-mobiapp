package com.example.ticket_sale.data.network;

public class ApiResponse <T>{
    private int statusCode;
    private String message;
    private T data;
    public enum Status { SUCCESS, ERROR, LOADING }
    public ApiResponse() {
    }

    public ApiResponse(int statusCode, String message,T data) {
        this.data = data;
        this.message = message;
        this.statusCode = statusCode;
    }

    public T getData() {
        return data;
    }
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(200, null, data);
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(400, message, null);
    }

    public static <T> ApiResponse<T> errorToken(String message) {
        return new ApiResponse<>(401, message,null); // 401: Unauthorized (token invalid)
    }

    public String getMessage() {
        return message;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
