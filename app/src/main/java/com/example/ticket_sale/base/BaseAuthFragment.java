package com.example.ticket_sale.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ticket_sale.activity.LoginActivity;
import com.example.ticket_sale.util.TokenManager;

public abstract class BaseAuthFragment extends Fragment {
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        TokenManager.init(requireContext().getApplicationContext());
        if(!TokenManager.isLoggedIn()){
            Log.e("TokenManager","Haven't logined");
            Intent intent = new Intent(requireActivity(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            requireActivity().finish();
        }
        Log.e("TokenManager","Logined");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = super.onCreateView(inflater, container, savedInstanceState);
        TokenManager.init(requireContext().getApplicationContext());
        if(!TokenManager.isLoggedIn()){
            Log.e("TokenManager","Haven't logined");
            Intent intent = new Intent(requireActivity(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            requireActivity().finish();
        }
        Log.e("TokenManager","Logined");
        return root;
    }
    protected void checkLogin(){
        TokenManager.init(requireContext().getApplicationContext());
        if (!TokenManager.isLoggedIn()) {
            Log.e("TokenManager", "Haven't logined");
            Intent intent = new Intent(requireActivity(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            requireActivity().finish();
        } else {
            Log.e("TokenManager", "Logined");
        }
    }
}
