package com.example.ticket_sale.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.ticket_sale.util.ViLocaleUtil;

public class Showtime implements Parcelable {
    private int id;
    private String timeStart;
    private String timeEnd;
    private String screenId;
    private String movieId;
    private String date;

    public Showtime() {
    }

    public Showtime( int id, String timeStart, String timeEnd,String screenId, String movieId, String date) {
        this.date = ViLocaleUtil.formatToddMMyyyy(date);
        this.id = id;
        this.movieId = movieId;
        this.screenId = screenId;
        this.timeEnd = timeEnd;
        this.timeStart = timeStart;
    }

    protected Showtime(Parcel in) {
        id = in.readInt();
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = ViLocaleUtil.formatToddMMyyyy(date);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getScreenId() {
        return screenId;
    }

    public void setScreenId(String screenId) {
        this.screenId = screenId;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(timeStart);
        dest.writeString(timeEnd);
        dest.writeString(screenId);
        dest.writeString(movieId);
        dest.writeString(date);
    }
}
