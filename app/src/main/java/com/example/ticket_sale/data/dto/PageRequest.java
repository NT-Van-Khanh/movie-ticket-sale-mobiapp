package com.example.ticket_sale.data.dto;


public class PageRequest {
    private String page;
    private  String limit;
    private  String searchKey;
    private  String sort;
    private  String status;
    private  String orderBy;

    public PageRequest() {
    }

    public PageRequest(String page, String limit, String searchKey, String sort, String status, String orderBy) {
        this.limit = limit;
        this.orderBy = orderBy;
        this.page = page;
        this.searchKey = searchKey;
        this.sort = sort;
        this.status = status;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
