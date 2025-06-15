package com.example.ticket_sale.data.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class BillResponseDTO {
    private String id;
    private Double totalPrice;//
    private String paymentMethodId;//
    private String paymentMethod;
    private String transactionCode;
    private String active;
    private List<ChairDTO> chairs;//id
    private List<FoodResponseDTO> dishes;//id
    private String timestamp;//"2025-04-20T11:50:53.599Z"
    private String status;
    private Integer filmShowTimeId; //id
    private String timeStart;// "2025-04-20T11:50:53.599Z",
    private String timeEnd;// "2025-04-20T11:50:53.599Z"
    private String timeStampSee;//   "timeStampSee": "2025-04-20"
    private String roomId;//id
    private String nameRoom;
    private String nameBranch;
    private String address;
    private String filmId;
    private String nameFilm;
    private String userName;
    private String email;
    private String numberPhone;
    private String qrCode;
    private String customerId;


//    private String id;
//    private String paymentMethodId;
//    private String transactionCode; // nếu cần với thanh toán online
//    private List<ChairDTO> chairs;
//    private List<FoodResponseDTO> dishes;
//    private LocalDateTime timestamp; // thời điểm đặt
//    private String status;
//    private Long filmShowTimeId;
//    private String roomId;
//    private String filmId;
//    private String userName;
//    private String email;
//    private String numberPhone;
    public BillResponseDTO() {
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getActive() {
        return active;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<ChairDTO> getChairs() {
        return chairs;
    }

    public void setChairs(List<ChairDTO> chairs) {
        this.chairs = chairs;
    }

    public List<FoodResponseDTO> getDishes() {
        return dishes;
    }

    public void setDishes(List<FoodResponseDTO> dishes) {
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

    public Integer getFilmShowTimeId() {
        return filmShowTimeId;
    }

    public void setFilmShowTimeId(Integer filmShowTimeId) {
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


    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTimeStampSee() {
        return timeStampSee;
    }

    public void setTimeStampSee(String timeStampSee) {
        this.timeStampSee = timeStampSee;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
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
