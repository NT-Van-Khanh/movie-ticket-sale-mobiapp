package com.example.ticket_sale.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class MovieTheater implements Parcelable {
    private String id;
    private String name;
    private String address;
    private Integer imageResId;

    public MovieTheater() {
    }

    public MovieTheater(String id, String name, String address, Integer imageResId) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.imageResId = imageResId;
    }

    protected MovieTheater(Parcel in) {
        id = in.readString();
        name = in.readString();
        address = in.readString();
        if (in.readByte() == 0) {
            imageResId = null;
        } else {
            imageResId = in.readInt();
        }
    }

    public static final Creator<MovieTheater> CREATOR = new Creator<MovieTheater>() {
        @Override
        public MovieTheater createFromParcel(Parcel in) {
            return new MovieTheater(in);
        }

        @Override
        public MovieTheater[] newArray(int size) {
            return new MovieTheater[size];
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getImageResId() {
        return imageResId;
    }

    public void setImageResId(Integer imageResId) {
        this.imageResId = imageResId;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(address);
        if (imageResId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(imageResId);
        }
    }
}
