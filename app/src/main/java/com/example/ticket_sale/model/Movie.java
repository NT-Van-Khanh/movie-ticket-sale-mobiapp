package com.example.ticket_sale.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.time.LocalDate;
import java.util.List;

public class Movie implements Parcelable {
    private String id;
    private String title;
    private Integer age;
    private Integer duration;//String
    private LocalDate openingDate;    //release date
    private Float rating;

    private String actor;
    private String director;
    private String nation;
    private String description;

    private String trailerLink;
    private String imageLink;
    private Integer imageResId;

    private List<MovieFormat> movieFormats;//format movie
    private List<MovieType> movieTypes;

    public Movie() {
    }

    public Movie( String id, String title, Integer age, Integer duration,
                  Integer imageResId, String nation, LocalDate openingDate, Float rating,
                  String description, String actor, String director,
                  List<MovieFormat> movieFormats, List<MovieType> movieTypes) {
        this.actor = actor;
        this.age = age;
        this.description = description;
        this.director = director;
        this.duration = duration;
        this.imageResId = imageResId;
        this.movieFormats = movieFormats;
        this.movieTypes = movieTypes;
        this.nation = nation;
        this.openingDate = openingDate;
        this.rating = rating;
        this.title = title;
        this.id = id;
    }

    public Movie( String id, String title, Integer duration, Integer age, Float rating,
                  Integer imageResId, List<MovieFormat> movieFormats, List<MovieType> movieTypes) {
        this.title = title;
        this.id = id;
        this.rating = rating;
        this.imageResId = imageResId;
        this.duration = duration;
        this.age = age;
        this.movieFormats = movieFormats;
    }

    public Movie(String id, Integer imageResId,String title, Integer duration, Integer age,  Float rating, String trailerLink) {
        this.id = id;
        this.imageResId = imageResId;
        this.age = age;
        this.title = title;
        this.rating = rating;
        this.duration = duration;
        this.trailerLink = trailerLink;
    }


    protected Movie(Parcel in) {
        id = in.readString();
        title = in.readString();
        if (in.readByte() == 0) {
            age = null;
        } else {
            age = in.readInt();
        }
        if (in.readByte() == 0) {
            duration = null;
        } else {
            duration = in.readInt();
        }
        if (in.readByte() == 0) {
            rating = null;
        } else {
            rating = in.readFloat();
        }
        actor = in.readString();
        director = in.readString();
        nation = in.readString();
        description = in.readString();
        trailerLink = in.readString();
        imageLink = in.readString();
        if (in.readByte() == 0) {
            imageResId = null;
        } else {
            imageResId = in.readInt();
        }
        movieFormats = in.createTypedArrayList(MovieFormat.CREATOR);
        movieTypes = in.createTypedArrayList(MovieType.CREATOR);
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
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

    public List<MovieType> getMovieTypes() {
        return movieTypes;
    }

    public void setMovieTypes(List<MovieType> movieTypes) {
        this.movieTypes = movieTypes;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public LocalDate getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(LocalDate openingDate) {
        this.openingDate = openingDate;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        if (age == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(age);
        }
        if (duration == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(duration);
        }
        if (rating == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeFloat(rating);
        }
        dest.writeString(actor);
        dest.writeString(director);
        dest.writeString(nation);
        dest.writeString(description);
        dest.writeString(trailerLink);
        dest.writeString(imageLink);
        if (imageResId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(imageResId);
        }
        dest.writeTypedList(movieFormats);
        dest.writeTypedList(movieTypes);
    }
}
