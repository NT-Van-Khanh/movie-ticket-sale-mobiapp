package com.example.ticket_sale.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class MovieType implements Parcelable {
    private String id;
    private String name;

    public MovieType() {
    }

    public MovieType(String id, String name) {
        this.id = id;
        this.name = name;
    }

    protected MovieType(Parcel in) {
        id = in.readString();
        name = in.readString();
    }

    public static final Creator<MovieType> CREATOR = new Creator<MovieType>() {
        @Override
        public MovieType createFromParcel(Parcel in) {
            return new MovieType(in);
        }

        @Override
        public MovieType[] newArray(int size) {
            return new MovieType[size];
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
    }
}
