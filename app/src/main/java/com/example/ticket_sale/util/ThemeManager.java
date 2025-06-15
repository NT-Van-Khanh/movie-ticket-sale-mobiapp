package com.example.ticket_sale.util;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatDelegate;

public class ThemeManager {
    private static final String THEME_PREFS_NAME = "sale_ticket_theme_pref";
    private static final String THEME_MODE_KEY = "theme_mode";

    public static void applyTheme(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(THEME_PREFS_NAME, Context.MODE_PRIVATE);
        int mode = prefs.getInt(THEME_MODE_KEY, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        AppCompatDelegate.setDefaultNightMode(mode);
    }

    public static void setDarkMode(Context context, boolean isDarkMode) {
        int mode = isDarkMode ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO;
        SharedPreferences prefs = context.getSharedPreferences(THEME_PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit().putInt(THEME_MODE_KEY, mode).apply();
        AppCompatDelegate.setDefaultNightMode(mode);
    }

    // Optional: để lấy trạng thái hiện tại (ví dụ để set switch)
    public static boolean isDarkMode(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(THEME_PREFS_NAME, Context.MODE_PRIVATE);
        int mode = prefs.getInt(THEME_MODE_KEY, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        return mode == AppCompatDelegate.MODE_NIGHT_YES;
    }
}
