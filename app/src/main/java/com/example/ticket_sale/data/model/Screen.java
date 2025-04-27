package com.example.ticket_sale.data.model;

import com.google.gson.annotations.SerializedName;

public class Screen {
    private String id;
    private String name;
    private Integer [][] positionChair;
//    private List<List<Integer>> positionChair;
    private String status;

    @SerializedName("branch")
    private Theater theater;

    public Screen() {
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

    public Integer[][] getPositionChair() {
        return positionChair;
    }

    public void setPositionChair(Integer[][] positionChair) {
        this.positionChair = positionChair;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Theater getTheater() {
        return theater;
    }

    public void setTheater(Theater theater) {
        this.theater = theater;
    }
}
