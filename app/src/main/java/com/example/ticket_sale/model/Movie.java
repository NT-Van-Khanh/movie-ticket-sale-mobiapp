package com.example.ticket_sale.model;

public class Movie {
    private String id;
    private String title;
    private String content;
    private Integer time;
    private Integer age;
    private String actor;
    private String director;
    private Integer imageResId;
    private Float rating;
    private String language;

    public Movie() {
    }

    public Movie(String id, Integer imageResId, String title, Integer time, Integer age,Float rating) {
        this.id = id;
        this.imageResId = imageResId;
        this.title = title;
        this.time = time;
        this.age = age;
        this.rating = rating;
    }

    public Movie(String id, String title, String content,Integer time, Integer age, Integer imageResId, Float rating, String language) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.age = age;
        this.actor = actor;
        this.director = director;
        this.time = time;
        this.imageResId = imageResId;
        this.rating = rating;
        this.language = language;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Integer getImageResId() {
        return imageResId;
    }

    public void setImageResId(Integer imageResId) {
        this.imageResId = imageResId;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
