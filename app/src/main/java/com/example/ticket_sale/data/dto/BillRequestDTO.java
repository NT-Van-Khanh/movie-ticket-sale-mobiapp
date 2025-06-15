package com.example.ticket_sale.data.dto;

import java.util.List;

public class BillRequestDTO {
    private Long totalPrice;//
    private String paymentMethodId;//
    private String transactionCode;
    private List<ChairRequestDTO> chairs;//id
    private List<FoodOrderDTO> dishes;//id- dc null
    private Integer filmShowTimeId; //id
    private String roomId;//id
    private String filmId;
    private String userName;// ten khach hang - khong phai trong login
    private String email;
    private String numberPhone;
//    private String customerId;
    public BillRequestDTO() {
    }

    public List<ChairRequestDTO> getChairs() {
        return chairs;
    }

    public void setChairs(List<ChairRequestDTO> chairs) {
        this.chairs = chairs;
    }

    public List<FoodOrderDTO> getDishes() {
        return dishes;
    }

    public void setDishes(List<FoodOrderDTO> dishes) {
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

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
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

    public Long getTotalPrice() {
        return totalPrice;
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

//        {
//        "totalPrice": 100000,
//        "transactionCode": "pi_3R9iCqBcNft3NmLQ0hh0eQkI",
//        "paymentMethodId": "1",
//        "chairs": [{
//            "chairCode": "[3,3]",
//            "price": 100000,
//            "ticket": {
//                "id": "1"
//            }
//        }],
//        "dishes": [{
//            "amount": 2,
//            "price": 50000,
//            "dishDto": {
//                "id": "3361ff55-72ea-4d7b-b66d-bcf7352bf9ee"// đang lỗi, ng dùng đã mua món này -> không thể mua nữa
//            }
//        }],
//        "filmShowTimeId": 13,
//        "roomId": "2",
//        "filmId": "9d7226db-6e50-4370-a50b-f0f2b5f28024",
//        "userName": "Cao Cường",
//        "email": "CaoCuong1@gmail.com",
//        "numberPhone": "0335723811",
//        "customerId": "cd664219-042d-4ffc-b851-5bbba4005d8b"
//        }