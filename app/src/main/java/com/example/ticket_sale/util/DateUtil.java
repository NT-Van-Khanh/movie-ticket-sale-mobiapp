package com.example.ticket_sale.util;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

//    public static String getDayMonthFromString(String dateString) {
//        if (dateString == null || !dateString.matches("\\d{2}/\\d{2}/\\d{4}")) {
//            return "";
//        }
//        return dateString.substring(0, 5); // Lấy 5 ký tự đầu tiên (dd/MM)
//    }

    public static String getDayMonthFromString(Calendar calendar) {
        if (calendar == null) {
            return "";
        }
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM", Locale.getDefault());
        return outputFormat.format(calendar.getTime()); // Lấy 5 ký tự đầu tiên (dd/MM)
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

    public static String getDayOfWeek(Calendar date){
        try{

//            Date date = stringToDate(dayString);
            SimpleDateFormat outputFormat = new SimpleDateFormat("EEEE", new Locale("vi","VN"));
            return outputFormat.format(date.getTime());

        }catch(Exception ex){

            Log.e("getDayOfWeek","Lỗi lấy thứ từ ngày tháng năm: "+date.getTime(),ex);
            return null;
        }
    }
//    public static String getDayOfWeek(String dayString){
//        try{
//
//            Date date = stringToDate(dayString);
//            SimpleDateFormat outputFormat = new SimpleDateFormat("EEEE", new Locale("vi","VN"));
//            return outputFormat.format(Calendar.getInstance().getTime());
//
//        }catch(Exception ex){
//
//            Log.e("getDayOfWeek","Lỗi lấy thứ từ ngày tháng năm: "+dayString,ex);
//            return null;
//        }
//    }

}
