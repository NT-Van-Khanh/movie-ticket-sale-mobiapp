package com.example.ticket_sale.data.dto;

import com.google.gson.annotations.SerializedName;

public class TheaterDTO {
    private String id;
    @SerializedName("nameBranch")
    private String name;
    private String address;
    private String status;

    public TheaterDTO() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
