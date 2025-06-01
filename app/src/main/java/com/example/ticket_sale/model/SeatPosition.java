package com.example.ticket_sale.model;

import com.example.ticket_sale.util.ViLocaleUtil;

public class SeatPosition {
    private int row;
    private int column;
    private String title;
    private String seatTypeId;
    private SeatType seatType;

    public SeatPosition(int row, int column, String seatTypeId) {
        this.row = row;
        this.column = column;
        this.seatTypeId = seatTypeId;
        this.title = getTitleByRowColumn(row, column);
        this.seatType = SeatType.getSeatTypeFromId(seatTypeId);
    }

    public SeatPosition(int row, int column) {
        this.row = row;
        this.column = column;
    }

    private String getTitleByRowColumn(int row, int column){
        char rowChar = (char) ('A'+row);
        return String.format(ViLocaleUtil.localeVN,"%s%d",rowChar,column);
    }

    public SeatPosition(int column, int row, SeatType seatType, String seatTypeId) {
        this.column = column;
        this.row = row;
        this.seatType = seatType;
        this.seatTypeId = seatTypeId;
    }

    public String getTitle() {
        return title;
    }

    public SeatType getSeatType() {
        return seatType;
    }

    public void setSeatType(SeatType seatType) {
        this.seatType = seatType;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public String getSeatTypeId() {
        return seatTypeId;
    }

    public void setSeatTypeId(String seatTypeId) {
        this.seatTypeId = seatTypeId;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getRow() { return row; }
    public int getColumn() { return column; }

}
