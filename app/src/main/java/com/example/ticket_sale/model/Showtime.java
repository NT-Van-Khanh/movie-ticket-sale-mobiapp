package com.example.ticket_sale.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Showtime implements Parcelable {
    String id;
    String timeStart;
    String timeEnd;
    String screenId;
    String movieId;
    String date;

    public Showtime() {
    }

    public Showtime(String id, String timeStart, String timeEnd, String roomId, String movieId, String date) {
        this.id = id;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.screenId = roomId;
        this.movieId = movieId;
        this.date = date;
    }

    protected Showtime(Parcel in) {
        id = in.readString();
        timeStart = in.readString();
        timeEnd = in.readString();
        screenId = in.readString();
        movieId = in.readString();
        date = in.readString();
    }

    public static final Creator<Showtime> CREATOR = new Creator<Showtime>() {
        @Override
        public Showtime createFromParcel(Parcel in) {
            return new Showtime(in);
        }

        @Override
        public Showtime[] newArray(int size) {
            return new Showtime[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getRoomId() {
        return screenId;
    }

    public void setRoomId(String roomId) {
        this.screenId = roomId;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(timeStart);
        dest.writeString(timeEnd);
        dest.writeString(screenId);
        dest.writeString(movieId);
        dest.writeString(date);
    }
}
