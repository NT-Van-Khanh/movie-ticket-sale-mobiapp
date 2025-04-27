package com.example.ticket_sale.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Movie implements Parcelable {
    private String id;
    private Integer imageResId;
    private Integer age;
    private String title;
    private Integer duration;//String
    private String openingDate;    //release date
    private Float rating;

    private String genre;
    private String actor;
    private String director;
    private String language;//format movie

    private String content;//description
    private String trailerLink;

    public Movie() {
    }

    public Movie(String id, Integer imageResId, String title, Integer duration, Integer age, Float rating) {
        this.id = id;
        this.imageResId = imageResId;
        this.title = title;
        this.duration = duration;
        this.age = age;
        this.rating = rating;
    }

    public Movie(String id, Integer imageResId, Integer age, String title, Integer duration, String openingDate, Float rating,
                 String genre, String actor, String director, String language, String content, String trailerLink) {
        this.id = id;
        this.imageResId = imageResId;
        this.age = age;
        this.title = title;
        this.duration = duration;
        this.openingDate = openingDate;
        this.rating = rating;
        this.genre = genre;
        this.actor = actor;
        this.director = director;
        this.language = language;
        this.content = content;
        this.trailerLink = trailerLink;
    }


    protected Movie(Parcel in) {
        id = in.readString();
        if (in.readByte() == 0) {
            imageResId = null;
        } else {
            imageResId = in.readInt();
        }
        if (in.readByte() == 0) {
            age = null;
        } else {
            age = in.readInt();
        }
        title = in.readString();
        if (in.readByte() == 0) {
            duration = null;
        } else {
            duration = in.readInt();
        }
        openingDate = in.readString();
        if (in.readByte() == 0) {
            rating = null;
        } else {
            rating = in.readFloat();
        }
        genre = in.readString();
        actor = in.readString();
        director = in.readString();
        language = in.readString();
        content = in.readString();
        trailerLink = in.readString();
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

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
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

    public String getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(String openingDate) {
        this.openingDate = openingDate;
    }

    public String getTrailerLink() {
        return trailerLink;
    }

    public void setTrailerLink(String trailerLink) {
        this.trailerLink = trailerLink;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(id);
        if (imageResId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(imageResId);
        }
        if (age == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(age);
        }
        dest.writeString(title);
        if (duration == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(duration);
        }
        dest.writeString(openingDate);
        if (rating == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeFloat(rating);
        }
        dest.writeString(genre);
        dest.writeString(actor);
        dest.writeString(director);
        dest.writeString(language);
        dest.writeString(content);
        dest.writeString(trailerLink);
    }
}
