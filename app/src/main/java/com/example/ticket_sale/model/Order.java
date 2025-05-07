package com.example.ticket_sale.model;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class Order implements Parcelable {
    private String id;
    private Map<Item, Integer> foods;
    private List<Seat> seats;
    private User user;
    private Movie movie;
    private MovieFormat movieFormat;
    private Showtime showtime;
    private Screen screen;
    private OrderStatus orderStatus;
    private LocalDateTime createdAt;

    protected Order(Parcel in) {
        id = in.readString();
        movie = in.readParcelable(Movie.class.getClassLoader());
        movieFormat = in.readParcelable(MovieFormat.class.getClassLoader());
        showtime = in.readParcelable(Showtime.class.getClassLoader());
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeParcelable(movie, flags);
        dest.writeParcelable(movieFormat, flags);
        dest.writeParcelable(showtime, flags);
    }


    public enum OrderStatus{
        SUCCESS, FAILED, CANCELLED, WAITING_PAYMENT
    }

    public Order() {
    }

    public Order(LocalDateTime createdAt, Map<Item, Integer> foods, String id, Movie movie, OrderStatus orderStatus,
                 Screen screen, List<Seat> seats, Showtime showtime, User user) {
        this.createdAt = createdAt;
        this.foods = foods;
        this.id = id;
        this.movie = movie;
        this.orderStatus = orderStatus;
        this.screen = screen;
        this.seats = seats;
        this.showtime = showtime;
        this.user = user;
    }

    public Long getTotalCostOfSeats(){
        if(seats == null) return 0L;
        Long totalcost = 0L;
        for(Seat s: seats){
            totalcost += s.getPrice();
        }
        return totalcost;
    }

    public Long getTotalCostOfFoods(){
        if(foods == null) return 0L;
        Long totalCost = 0L;
        for (Map.Entry<Item, Integer> foodEntry : foods.entrySet()) {
            totalCost += foodEntry.getKey().getPrice() * foodEntry.getValue();
        }
        return totalCost;
    }

    public Long getTotalCost(){
        return getTotalCostOfFoods() + getTotalCostOfSeats();
    }
    public MovieFormat getMovieFormat() {
        return movieFormat;
    }

    public void setMovieFormat(MovieFormat movieFormat) {
        this.movieFormat = movieFormat;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Map<Item, Integer> getFoods() {
        return foods;
    }

    public void setFoods(Map<Item, Integer> foods) {
        this.foods = foods;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Screen getScreen() {
        return screen;
    }

    public void setScreen(Screen screen) {
        this.screen = screen;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    public Showtime getShowtime() {
        return showtime;
    }

    public void setShowtime(Showtime showtime) {
        this.showtime = showtime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long calculateTotalCost() {
        Long totalFoodCosts = foods.entrySet().stream()
                .map(entry -> entry.getKey().getPrice() * entry.getValue())
                .reduce(0L, Long::sum);
        Long totalTicketCosts = seats.stream()
                .map(seat -> seat.getPrice())  // Lấy giá của ghế
                .reduce(0L, Long::sum);
        return totalFoodCosts + totalTicketCosts;
    }
}
