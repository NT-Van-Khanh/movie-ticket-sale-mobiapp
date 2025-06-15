package com.example.ticket_sale.data.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TotalRateDTO {
    @SerializedName("rate")
    private int totalRate;
    private List<Rate> comments;

    public TotalRateDTO(List<Rate> comments, int totalRate) {
        this.comments = comments;
        this.totalRate = totalRate;
    }

    public List<Rate> getComments() {
        return comments;
    }

    public void setComments(List<Rate> comments) {
        this.comments = comments;
    }

    public int getTotalRate() {
        return totalRate;
    }

    public void setTotalRate(int totalRate) {
        this.totalRate = totalRate;
    }
}
