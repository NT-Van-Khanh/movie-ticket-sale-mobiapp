package com.example.ticket_sale.model;

import java.util.List;

public class MovieByTheater {
    private String id;
    private String title;
    private Integer time;
    private Integer age;
    private Float rating;
    private Integer imageResId;
    private List<MovieFormat> movieFormats;

    public MovieByTheater() {
    }

    public MovieByTheater(String id, String title, Integer time, Integer age, Float rating, Integer imageResId, List<MovieFormat> movieFormats) {
        this.id = id;
        this.title = title;
        this.time = time;
        this.age = age;
        this.rating = rating;
        this.imageResId = imageResId;
        this.movieFormats = movieFormats;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public Integer getImageResId() {
        return imageResId;
    }

    public void setImageResId(Integer imageResId) {
        this.imageResId = imageResId;
    }

    public List<MovieFormat> getMovieFormats() {
        return movieFormats;
    }

    public void setMovieFormats(List<MovieFormat> movieFormats) {
        this.movieFormats = movieFormats;
    }
}
