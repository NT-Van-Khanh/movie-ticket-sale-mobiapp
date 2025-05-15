package com.example.ticket_sale.model;

import androidx.annotation.Nullable;

import java.util.Objects;

public class ShowtimeKey {
    private String movieId;
    private String theaterId;
    private String date;
    private String formatId;


    public ShowtimeKey(String theaterId, String movieId, String formatId, String date) {
        this.date = date;
        this.formatId = formatId;
        this.movieId = movieId;
        this.theaterId = theaterId;
    }


    @Override
    public int hashCode() {
        return Objects.hash(theaterId, movieId, formatId, date);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof ShowtimeKey)) return false;
        ShowtimeKey that = (ShowtimeKey) obj;
        return theaterId.equals(that.theaterId) &&
                movieId.equals(that.movieId) &&
                formatId.equals(that.formatId) &&
                date.equals(that.date);
    }

    public boolean isValid(){
        return isNonEmpty(theaterId) &&
                isNonEmpty(movieId) &&
                isNonEmpty(formatId) &&
                isNonEmpty(date);
    }

    private boolean isNonEmpty(String value) {
        return value != null && !value.isEmpty();
    }

    public String getDate() {
        return date;
    }

    public String getFormatId() {
        return formatId;
    }

    public String getMovieId() {
        return movieId;
    }

    public String getTheaterId() {
        return theaterId;
    }
}
