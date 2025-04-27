package com.example.ticket_sale.data.network;

import java.util.List;

public class PageResponse<T> {
    private List<T> content;
    private int pageCurrent;
    private int totalPages;

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

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

   }
