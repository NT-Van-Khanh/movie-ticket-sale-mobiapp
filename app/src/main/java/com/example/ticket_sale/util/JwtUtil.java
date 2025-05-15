package com.example.ticket_sale.util;

import android.util.Base64;

import org.json.JSONObject;

public class JwtUtil {
    public static JSONObject decodeJWT(String jwt) {
        try {
            String[] parts = jwt.split("\\."); // JWT có dạng header.payload.signature
            if (parts.length != 3) {
                throw new IllegalArgumentException("Invalid JWT token.");
            }
            byte[] decodedBytes = Base64.decode(parts[1], Base64.URL_SAFE);
            String payload = new String(decodedBytes);

            return new JSONObject(payload); // Trả về dạng JSONObject để dễ truy cập
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
