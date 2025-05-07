package com.example.ticket_sale.data.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Bill {
    private String id;
    private Double totalPrice;
    private String paymentMethodId;
    private String paymentMethod;
    private String transactionCode;
//    private String active = "ACTIVE";
    private List<Chair> chairs;
    private List<FoodDTO> dishes;
    private LocalDateTime timestamp;//"2025-04-20T11:50:53.599Z"
    private String status;
    private Long filmShowTimeId; //1073741824
    private LocalDateTime timeStart;// "2025-04-20T11:50:53.599Z",
    private LocalDateTime timeEnd;// "2025-04-20T11:50:53.599Z"
    private LocalDate timeStampSee;//   "timeStampSee": "2025-04-20"
    private String roomId;
    private String nameRoom;
    private String nameBranch;
    private String address;
    private String filmId;
    private String nameFilm;
    private String userName;
    private String email;
    private String numberPhone;

    public Bill() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Chair> getChairs() {
        return chairs;
    }

    public void setChairs(List<Chair> chairs) {
        this.chairs = chairs;
    }

    public List<FoodDTO> getDishes() {
        return dishes;
    }

    public void setDishes(List<FoodDTO> dishes) {
        this.dishes = dishes;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFilmId() {
        return filmId;
    }

    public void setFilmId(String filmId) {
        this.filmId = filmId;
    }

    public Long getFilmShowTimeId() {
        return filmShowTimeId;
    }

    public void setFilmShowTimeId(Long filmShowTimeId) {
        this.filmShowTimeId = filmShowTimeId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNameBranch() {
        return nameBranch;
    }

    public void setNameBranch(String nameBranch) {
        this.nameBranch = nameBranch;
    }

    public String getNameFilm() {
        return nameFilm;
    }

    public void setNameFilm(String nameFilm) {
        this.nameFilm = nameFilm;
    }

    public String getNameRoom() {
        return nameRoom;
    }

    public void setNameRoom(String nameRoom) {
        this.nameRoom = nameRoom;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(String paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(LocalDateTime timeEnd) {
        this.timeEnd = timeEnd;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public LocalDate getTimeStampSee() {
        return timeStampSee;
    }

    public void setTimeStampSee(LocalDate timeStampSee) {
        this.timeStampSee = timeStampSee;
    }

    public LocalDateTime getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(LocalDateTime timeStart) {
        this.timeStart = timeStart;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getTransactionCode() {
        return transactionCode;
    }

    public void setTransactionCode(String transactionCode) {
        this.transactionCode = transactionCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}

//{
//        "id": "string",
//        "totalPrice": 0.1,
//        "transactionCode": "string",
//        "paymentMethodId": "string",
//        "paymentMethod": "string",
//        "active": "ACTIVE",
//        "chairs": [
//        {
//        "id": "string",
//        "chairCode": "string",
//        "price": 0.1,
//        "ticket": {
//        "id": "string",
//        "active": "ACTIVE",
//        "conditionUse": "string",
//        "name": "string",
//        "price": 1073741824,
//        "typeTicket": "string",
//        "slot": 10
//        },
//        "active": "ACTIVE"
//        }
//        ],
//        "dishes": [
//        {
//        "id": "string",
//        "active": "ACTIVE",
//        "price": 0.1,
//        "amount": 1073741824,
//        "dishDto": {
//        "id": "string",
//        "price": 0.1,
//        "active": "string",
//        "name": "string",
//        "image": "string",
//        "typeDish": {
//        "id": "string",
//        "active": "string",
//        "name": "string",
//        "dishes": [
//        "string"
//        ]
//        }
//        }
//        }
//        ],
//        "timestamp": "2025-04-20T11:50:53.599Z",
//        "status": "string",
//        "filmShowTimeId": 1073741824,
//        "timeEnd": "2025-04-20T11:50:53.599Z",
//        "timeStart": "2025-04-20T11:50:53.599Z",
//        "timeStampSee": "2025-04-20",
//        "roomId": "string",
//        "nameRoom": "string",
//        "nameBranch": "string",
//        "address": "string",
//        "filmId": "string",
//        "nameFilm": "string",
//        "userName": "string",
//        "email": "string",
//        "numberPhone": "string"
//        }
