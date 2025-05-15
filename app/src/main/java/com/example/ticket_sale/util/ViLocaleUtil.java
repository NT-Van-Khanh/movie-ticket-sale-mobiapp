package com.example.ticket_sale.util;

import android.util.Log;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ViLocaleUtil {
    private static final DecimalFormat decimalFormat = new DecimalFormat("#,###");
    public static final Locale localeVN = new Locale("vi", "VN");
    private static final NumberFormat numberFormat = NumberFormat.getInstance(localeVN);

    public static String formatCurrency(long amount){
        return String.format("%sđ", decimalFormat.format(amount));
    }

    public static String formatLocalCurrency(long amount){
        return String.format("%sđ", numberFormat.format(amount));
    }

    public static String formatCompactCurrency(long amount){
        if( amount >= 1_000_000){
            return String.format(localeVN,"%.3fTr", amount/ 1_000_000.0);
        }else if(amount >=1_000){
            return String.format(localeVN,"%.3fK", amount/1_000.0);
        }else{
            return amount + "đ";
        }
    }

    public static String getDayMonthFromString(Calendar calendar) {
        if (calendar == null) return "";

        SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM", Locale.getDefault());
        return outputFormat.format(calendar.getTime()); // Lấy 5 ký tự đầu tiên (dd/MM)
    }


    public static String getDayOfWeek(Calendar date){
        try{
            SimpleDateFormat outputFormat = new SimpleDateFormat("EEEE", new Locale("vi","VN"));
//            String dayOfWeek = outputFormat.format(date.getTime());
            String[] daysOfWeek = {"C.Nhật", "T.Hai", "T.Ba", "T.Tư", "T.Năm", "T.Sáu", "T.Bảy"};
            int dayIndex = date.get(Calendar.DAY_OF_WEEK);
            return daysOfWeek[dayIndex-1];
        }catch(Exception ex){
            Log.e("getDayOfWeek","Lỗi lấy thứ từ ngày tháng năm: "+date.getTime(),ex);
            return null;
        }
    }

    public static Date stringToDate(String dateString){
        try{
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            return outputFormat.parse(dateString);
        }catch (ParseException ex){
            Log.e("stringToDate","Lỗi chuyển chuỗi ký tự thành ngày: " + dateString,ex);
            return null;
        }
    }
    public static String formatToHHmm(String timeString){
        if (timeString == null || !timeString.matches("^\\d{1,2}:\\d{2}$"))
            return null;

        String[] parts = timeString.split(":");
        int hour = Integer.parseInt(parts[0]);
        int minute = Integer.parseInt(parts[1]);
        if (hour < 0 || hour > 23 || minute < 0 || minute > 59) {
            return null;
        }

        return String.format("%02d:%02d", hour, minute);
    }
}
