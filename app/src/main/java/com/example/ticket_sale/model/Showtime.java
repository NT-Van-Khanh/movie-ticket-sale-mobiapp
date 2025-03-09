package com.example.ticket_sale.model;

public class Showtime {
    String id;
    String timeStart;
    String timeEnd;
    String roomId;
    String movieId;
    String date;

    public Showtime() {
    }

    public Showtime(String id, String timeStart, String timeEnd, String roomId, String movieId, String date) {
        this.id = id;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.roomId = roomId;
        this.movieId = movieId;
        this.date = date;
    }

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
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
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
}
