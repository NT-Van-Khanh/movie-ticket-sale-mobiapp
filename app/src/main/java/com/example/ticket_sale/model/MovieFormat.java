package com.example.ticket_sale.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.List;

public class MovieFormat implements Parcelable {
    String id;
    String name;
    List<Showtime> showtimes;

    public MovieFormat() {
    }

    public MovieFormat(String id, String name, List<Showtime> showtimes) {
        this.id = id;
        this.name = name;
        this.showtimes = showtimes;
    }

    protected MovieFormat(Parcel in) {
        id = in.readString();
        name = in.readString();
        showtimes = in.createTypedArrayList(Showtime.CREATOR);
    }

    public static final Creator<MovieFormat> CREATOR = new Creator<MovieFormat>() {
        @Override
        public MovieFormat createFromParcel(Parcel in) {
            return new MovieFormat(in);
        }

        @Override
        public MovieFormat[] newArray(int size) {
            return new MovieFormat[size];
        }
    };

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

    public List<Showtime> getShowtimes() {
        return showtimes;
    }

    public void setShowtimes(List<Showtime> showtimes) {
        this.showtimes = showtimes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeTypedList(showtimes);
    }
}
