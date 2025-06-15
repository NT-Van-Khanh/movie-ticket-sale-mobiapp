package com.example.ticket_sale.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.ticket_sale.data.RetrofitInstance;

import org.json.JSONObject;

public class TokenManager {
    private static final String PREFS_NAME = "sale_ticket_pref";
    private static final String TOKEN_KEY = "accessToken";
    private static SharedPreferences sharedPreferences;

    public static void init(Context context) {
        if(sharedPreferences == null)
            sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public static String getToken() {
        if (sharedPreferences == null)   return null;
        return sharedPreferences.getString(TOKEN_KEY, null);  // Lấy token từ SharedPreferences
    }

    public static void saveToken(String token) {
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(TOKEN_KEY, token);
            editor.apply();

            RetrofitInstance.updateToken(token);
            Log.d("TokenManager_saveToken", "Save new token success!");
        }
    }

    public static void clearToken() {
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove(TOKEN_KEY);
            editor.apply();

            RetrofitInstance.updateToken(null);
        }
    }
    public static String getUserIdFromToken(){
        String token = TokenManager.getToken();
        JSONObject payload = JwtUtil.decodeJWT(token);
        if(payload == null) return null;
        return payload.optString("id");

    }
    public static boolean isLoggedIn() {
        return getToken() != null && !getToken().isEmpty();
    }
}
