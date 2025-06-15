package com.example.ticket_sale.data.network;

import java.util.List;

public class PageResponse<T> {
    private List<T> content;
    private int pageCurrent;
    private int totalPages;
    private T data;
//    private int totalElements;
//    private int size;
//    private int number;
//    private boolean first;
//    private int numberOfElements;
//    private boolean empty;
//    private Pageable pageable;
//    private boolean last;
    public PageResponse() {
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getPageCurrent() {
        return pageCurrent;
    }

    public void setPageCurrent(int pageCurrent) {
        this.pageCurrent = pageCurrent;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

   }
