package com.example.ticket_sale.data.dto;

public class MovieFormatDTO {
    private String id;
    private String name;

    public MovieFormatDTO() {
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

    public boolean isValid(){
        return true;
    }
}
