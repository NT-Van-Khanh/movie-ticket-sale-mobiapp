package com.example.ticket_sale.model;

import java.time.LocalDateTime;

public class PaymentMethod {
    private String id;
    private String title;
    private String info;
    private LocalDateTime createdAt;
    private Integer imageResId;
    private Method method;

    public enum PaymentGroup {
        CREDIT_DEBIT_CARD,
        BANK_TRANSFER,
        DIGITAL_WALLET,
        PAYMENT_GATEWAY,
        QR_CODE
    }

    public enum Method {
        MOMO(PaymentGroup.DIGITAL_WALLET, "MoMo"),
        VNPAY(PaymentGroup.QR_CODE, "VNPAY QR"),
        ZALOPAY(PaymentGroup.DIGITAL_WALLET, "ZaloPay"),
        BANKING(PaymentGroup.BANK_TRANSFER, "Tài khoản ngân hàng"),
        MASTER_CARD(PaymentGroup.CREDIT_DEBIT_CARD, "Master Card");

        private final PaymentGroup group;
        private final String displayName;

        Method(PaymentGroup group, String displayName) {
            this.group = group;
            this.displayName = displayName;
        }

        public PaymentGroup getGroup() {
            return group;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    public PaymentMethod() {
    }

//    public PaymentMethod(String id, String title, String info, Integer imageResId) {
//        this.id = id;
//        this.imageResId = imageResId;
//        this.info = info;
//        this.title = title;
//    }

    public PaymentMethod(String id, String title, Method method, String info, Integer imageResId) {
        this.id = id;
        this.imageResId = imageResId;
        this.info = info;
        this.method = method;
        this.title = title;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Integer getImageResId() {
        return imageResId;
    }

    public void setImageResId(Integer imageResId) {
        this.imageResId = imageResId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
