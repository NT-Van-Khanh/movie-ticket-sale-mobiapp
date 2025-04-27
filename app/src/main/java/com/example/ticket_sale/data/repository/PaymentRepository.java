package com.example.ticket_sale.data.repository;

import com.example.ticket_sale.data.network.api.PaymentAPI;

public class PaymentRepository {
    private final PaymentAPI paymentAPI;

    public PaymentRepository(PaymentAPI paymentAPI) {
        this.paymentAPI = paymentAPI;
    }
}
