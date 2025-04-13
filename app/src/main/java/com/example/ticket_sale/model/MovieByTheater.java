package com.example.ticket_sale.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.List;

public class MovieByTheater implements Parcelable {
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

    protected MovieByTheater(Parcel in) {
        id = in.readString();
        title = in.readString();
        if (in.readByte() == 0) {
            time = null;
        } else {
            time = in.readInt();
        }
        if (in.readByte() == 0) {
            age = null;
        } else {
            age = in.readInt();
        }
        if (in.readByte() == 0) {
            rating = null;
        } else {
            rating = in.readFloat();
        }
        if (in.readByte() == 0) {
            imageResId = null;
        } else {
            imageResId = in.readInt();
        }
    }

    public static final Creator<MovieByTheater> CREATOR = new Creator<MovieByTheater>() {
        @Override
        public MovieByTheater createFromParcel(Parcel in) {
            return new MovieByTheater(in);
        }

        @Override
        public MovieByTheater[] newArray(int size) {
            return new MovieByTheater[size];
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        if (time == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(time);
        }
        if (age == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(age);
        }
        if (rating == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeFloat(rating);
        }
        if (imageResId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(imageResId);
        }
    }
}
