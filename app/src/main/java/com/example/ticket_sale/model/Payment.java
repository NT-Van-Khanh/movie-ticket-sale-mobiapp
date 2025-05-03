package com.example.ticket_sale.model;

import java.time.LocalDateTime;

public class Payment {
    private String id;
    private Order order;
    private PaymentMethod method;
    private Long discount = 0L;
    private Long finalCost;
    private PaymentStatus status;
    private LocalDateTime createdAt;
    public enum PaymentStatus {
        PENDING,
        PAID,
        FAILED
    }
    public void calculateFinalCost() {
        Long originalCost = order.calculateTotalCost();
        this.finalCost = originalCost - (discount != null ? discount : 0L);
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getDiscount() {
        return discount;
    }

    public void setDiscount(Long discount) {
        this.discount = discount;
    }

    public Long getFinalCost() {
        return finalCost;
    }

    public void setFinalCost(Long finalCost) {
        this.finalCost = finalCost;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PaymentMethod getMethod() {
        return method;
    }

    public void setMethod(PaymentMethod method) {
        this.method = method;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }
}
