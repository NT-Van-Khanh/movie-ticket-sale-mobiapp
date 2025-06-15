package com.example.ticket_sale.data.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieDTO {
    private String id;
    private Integer age;
    private String nation;
    private String duration;//Thời lượng phim
    private String description;
    private String content;//description
    private String status;

    @SerializedName("name")
    private String title;

    @SerializedName("image")
    private String imageLink;


    @SerializedName("trailer")
    private String trailerLink;

    @SerializedName("typeFilms")
    private List<MovieTypeDTO> movieTypes;

    @SerializedName("sub")
    private List<MovieFormatDTO> format;

//CHƯA CÓ:
//    private String actor;
//    private String director;
//    private String openingDate;    //release date
//    private Float rating;


    public MovieDTO() {
    }

    public Integer getAge() {
        return age;
    }

    public List<MovieFormatDTO> getFormat() {
        return format;
    }

    public void setFormat(List<MovieFormatDTO> format) {
        this.format = format;
    }

    public List<MovieTypeDTO> getMovieTypes() {
        return movieTypes;
    }

    public void setMovieTypes(List<MovieTypeDTO> movieTypes) {
        this.movieTypes = movieTypes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
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

    public String getTrailerLink() {
        return trailerLink;
    }

    public void setTrailerLink(String trailerLink) {
        this.trailerLink = trailerLink;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }
}
