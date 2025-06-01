package com.example.ticket_sale.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Order implements Parcelable {
    private String id;

    private Ticket selectedTicket;
    private Map<Food, Integer> foods;
    private List<SeatPosition> selectedSeats;

    private User user;
    private Movie movie;
    private MovieFormat movieFormat;
    private Showtime showtime;
    private Screen screen;
//    private OrderStatus orderStatus;

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

//    public enum OrderStatus{
//        SUCCESS, FAILED, CANCELLED, WAITING_PAYMENT
//    }

    public Order() {
    }

//    public Order( Map<Item, Integer> foods, String id, Movie movie, OrderStatus orderStatus,
//                 Screen screen, List<Seat> seats, Showtime showtime, User user) {
//
//        this.foods = foods;
//        this.id = id;
//        this.movie = movie;
//        this.orderStatus = orderStatus;
//        this.screen = screen;
//        this.seats = seats;
//        this.showtime = showtime;
//        this.user = user;
//    }

//    public Long getTotalCostOfSeats(){
//        if(selectedSeats == null) return 0L;
//        Long totalcost = 0L;
//        for(SeatPosition s: selectedSeats){
//            totalcost += s.getPrice();
//        }
//        return totalcost;
//    }

    public Long getTotalCostOfSeats(){
        if(selectedSeats == null || selectedTicket == null) return 0L;
        return selectedTicket.getPrice() * selectedSeats.size();
    }

    public Long getTotalCostOfFoods(){
        if(foods == null) return 0L;
        Long totalCost = 0L;
        for (Map.Entry<Food, Integer> foodEntry : foods.entrySet()) {
            totalCost += foodEntry.getKey().getPrice() * foodEntry.getValue();
        }
        return totalCost;
    }

    public List<SeatPosition> getSelectedSeats() {
        return selectedSeats;
    }

    public void setSelectedSeats(List<SeatPosition> selectedSeats) {
        this.selectedSeats = selectedSeats;
    }

    public Ticket getSelectedTicket() {
        return selectedTicket;
    }

    public void setSelectedTicket(Ticket selectedTicket) {
        this.selectedTicket = selectedTicket;
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


    public Map<Food, Integer> getFoods() {
        return foods;
    }

    public void setFoods(Map<Food, Integer> foods) {
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
//
//    public OrderStatus getOrderStatus() {
//        return orderStatus;
//    }
//
//    public void setOrderStatus(OrderStatus orderStatus) {
//        this.orderStatus = orderStatus;
//    }

    public Screen getScreen() {
        return screen;
    }

    public void setScreen(Screen screen) {
        this.screen = screen;
    }

    public List<SeatPosition> getSeats() {
        return selectedSeats;
    }
    public String getSelectedSeatsString(){
        if(selectedSeats == null|| selectedSeats.isEmpty()) return "";
        String stringSeats = selectedSeats.stream()
                .map(seat -> " "+ seat.getTitle()).collect(Collectors.joining());
        return stringSeats;
    }

    public void setSeats(List<SeatPosition> seats) {
        this.selectedSeats = seats;
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
        Long totalFoodCosts = getTotalCostOfFoods();
        Long totalTicketCosts = getTotalCostOfSeats();
        return totalFoodCosts + totalTicketCosts;
    }

//    public Long calculateTotalCost() {
//        Long totalFoodCosts = foods.entrySet().stream()
//                .map(entry -> entry.getKey().getPrice() * entry.getValue())
//                .reduce(0L, Long::sum);
//        Long totalTicketCosts = selectedSeats.stream()
//                .map(seat -> seat.getPrice())  // Lấy giá của ghế
//                .reduce(0L, Long::sum);
//        return totalFoodCosts + totalTicketCosts;
//    }
}
