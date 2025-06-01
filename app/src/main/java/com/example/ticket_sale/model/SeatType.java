package com.example.ticket_sale.model;

import android.content.Context;
import android.content.res.ColorStateList;
import androidx.core.content.ContextCompat;

import com.example.ticket_sale.R;

public enum SeatType {
    NONE(R.color.white),
    STANDARD(R.color.seat_standard),
    VIP(R.color.seat_vip),
    COUPLE(R.color.seat_couple),
    SELECTED(R.color.seat_selected),
    SOLD(R.color.seat_sold);

    private final int colorResId;
    private Integer cachedColor;

    SeatType (int colorResId){
        this.colorResId = colorResId;
    }

    public static SeatType getSeatTypeFromId(String seatTypeId) {
        switch (seatTypeId) {
            case "1":
                return SeatType.STANDARD;
            case "3":
                return SeatType.VIP;
            case "2":
                return SeatType.COUPLE;
            case "-1":
                return SeatType.SOLD;
            case "5":
                return SeatType.SELECTED;
            default:
                return SeatType.NONE;
        }
    }


    public int getColorResId(){
        return this.colorResId;
    }

    public int getTypeOfSeat(Context context){
        if (cachedColor != null) {
            cachedColor = ContextCompat.getColor(context, colorResId);
        }
        return ContextCompat.getColor(context, colorResId);
    }


//    public ColorStateList getTypeOfSeat(Context context){
//        if (cachedColor != null) {
//            cachedColor = ColorStateList.valueOf(ContextCompat.getColor(context, colorResId));
//        }
//        return cachedColor;
//    }
    //      case "STANDARD":
//              return ColorStateList.valueOf(ContextCompat.getColor(getContext(),R.color.seat_standard));
//            case "VIP":
//                    return ColorStateList.valueOf(ContextCompat.getColor(getContext(),R.color.seat_vip));
//            case "COUPLE":
//                    return ColorStateList.valueOf(ContextCompat.getColor(getContext(),R.color.seat_couple));
//            case "SELECTED":
//                    return ColorStateList.valueOf(ContextCompat.getColor(getContext(),R.color.seat_selected));
//            case "SOLD":
//                    return ColorStateList.valueOf(ContextCompat.getColor(getContext(),R.color.seat_sold));
//
}
