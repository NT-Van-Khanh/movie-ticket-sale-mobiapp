package com.example.ticket_sale.util.debug;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class MoneyUtil {
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
}
