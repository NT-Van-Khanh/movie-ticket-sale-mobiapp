package com.example.ticket_sale.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.ticket_sale.activity.LoginActivity;

public class AuthGuard {
    public static boolean checkLogin(Context context){
        TokenManager.init(context.getApplicationContext());
        if (!TokenManager.isLoggedIn()) {
            Log.e("TokenManager", "Haven't logined");
            Intent intent = new Intent(context, LoginActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(intent);
//            if (context instanceof Activity) {
//                ((Activity) context).finish(); // Đảm bảo không quay lại
//            }
            return false;
        }
        Log.e("AuthGuard", "Đã đăng nhập");
        return true;
    }
}
