package com.example.ticket_sale.util;

public class UserUtil {

    public static String maskEmail(String email) {
        if (email == null || !email.contains("@")) return email;

        String[] parts = email.split("@");
        String username = parts[0];
        String domain = parts[1];

        int visibleChars = Math.min(3, username.length());
        String visiblePart = username.substring(0, visibleChars);
        String maskedPart = "*".repeat(Math.max(0, username.length() - visibleChars));

        return visiblePart + maskedPart + "@" + domain;
    }

    public static String maskPhone(String phone) {
        if (phone == null || phone.length() < 4) return phone;

        String visibleStart = phone.substring(0, 2);
        String visibleEnd = phone.substring(phone.length() - 2);
        String maskedMiddle = "*".repeat(phone.length() - 4);

        return visibleStart + maskedMiddle + visibleEnd;
    }
}
