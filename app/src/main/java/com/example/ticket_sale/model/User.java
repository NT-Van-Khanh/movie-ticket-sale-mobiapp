package com.example.ticket_sale.model;

import java.util.List;

public class User {
    private String id;
    private String name;
    private String username;
    private String phone;
    private String email;
    private List<PaymentMethod> paymentMethods;
    public User() {
    }

    public User(String email, String id, String name, String phone, String username) {
        this.email = email;
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
