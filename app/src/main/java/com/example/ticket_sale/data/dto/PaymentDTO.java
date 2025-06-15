package com.example.ticket_sale.data.dto;

import com.google.gson.annotations.SerializedName;

public class PaymentDTO {
    @SerializedName("id")
    private String stripePaymentMethodId;
    private String billId;
    private Long amount;
    private String currency;

    public PaymentDTO( String billId, Long amount, String currency, String stripePaymentMethodId) {
        this.amount = amount;
        this.billId = billId;
        this.currency = currency;
        this.stripePaymentMethodId = stripePaymentMethodId;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getStripePaymentMethodId() {
        return stripePaymentMethodId;
    }

    public void setStripePaymentMethodId(String stripePaymentMethodId) {
        this.stripePaymentMethodId = stripePaymentMethodId;
    }
}
