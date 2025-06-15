package com.example.ticket_sale.data.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TotalRateDTO {
    @SerializedName("rate")
    private Float totalRate;
    private List<RateDTO> comments;

    public TotalRateDTO(List<RateDTO> comments, Float totalRate) {
        this.comments = comments;
        this.totalRate = totalRate;
    }

    public List<RateDTO> getComments() {
        return comments;
    }

    public void setComments(List<RateDTO> comments) {
        this.comments = comments;
    }

    public Float getTotalRate() {
        return totalRate;
    }

    public void setTotalRate(Float totalRate) {
        this.totalRate = totalRate;
    }
}
