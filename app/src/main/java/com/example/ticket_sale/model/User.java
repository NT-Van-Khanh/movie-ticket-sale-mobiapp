package com.example.ticket_sale.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.List;

public class User implements Parcelable {
    private String id;
    private String name;
    private String username;
    private String phone;
    private String email;
    private List<PaymentMethod> paymentMethods;
    public User() {
    }

    public User(String name, String phone, String email, String username) {
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.username = username;
    }

    public User(String id, String username, String name, String email, String phone) {
        this.email = email;
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.username = username;
    }

    protected User(Parcel in) {
        id = in.readString();
        name = in.readString();
        username = in.readString();
        phone = in.readString();
        email = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public List<PaymentMethod> getPaymentMethods() {
        return paymentMethods;
    }

    public void setPaymentMethods(List<PaymentMethod> paymentMethods) {
        this.paymentMethods = paymentMethods;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(username);
        dest.writeString(phone);
        dest.writeString(email);
    }
}
