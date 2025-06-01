package com.example.ticket_sale.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ticket_sale.R;
import com.example.ticket_sale.base.BaseAuthActivity;
import com.example.ticket_sale.viewmodel.AuthViewModel;
import com.google.android.material.button.MaterialButton;

public class LoginActivity extends BaseAuthActivity {
    private TextView txtGoBack;
    private EditText edtUsername;
    private EditText edtPassword;
    private TextView txtForgotPassword;
    private Button btnLogin;
    private Button btnRedirectRegister;
    private View viewOverlay;
    private ProgressBar pbLoadLogin;


    private AuthViewModel authViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        initViews();
        authViewModel = new AuthViewModel();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private void initViews(){
        txtGoBack = findViewById(R.id.txtGoBack);
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        txtForgotPassword = findViewById(R.id.txtForgotPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRedirectRegister = findViewById(R.id.btnRedirectRegister);
        pbLoadLogin = findViewById(R.id.pbLoadLogin);
        viewOverlay = findViewById(R.id.viewOverlay);

        txtGoBack.setOnClickListener(v -> finish());
        txtForgotPassword.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, ResetPasswordActivity.class);
            startActivity(intent);
        });

        btnLogin.setOnClickListener(v -> {
            showLoadingUI();
            String username = edtUsername.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Tên đăng nhập và mật khẩu không được để trống",
                        Toast.LENGTH_SHORT).show();
                hideLoadingUI();
                return;
            }
            login(username, password);
//            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//            startActivity(intent);
        });

        btnRedirectRegister.setOnClickListener(v -> {
            Intent intent  = new Intent(LoginActivity.this,RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void login(String username, String password) {
        authViewModel.auth(username, password).observe(LoginActivity.this, result -> {
            if (result == null || !result.containsKey("accessToken")) {
                Log.e("authRepository.auth", "Can't not get token!");
                Toast.makeText(LoginActivity.this, "Tên đăng nhập hoặc mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
                hideLoadingUI();
                return;
            }
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            hideLoadingUI();
            finish();
        });
    }

    public void  showLoadingUI(){
        pbLoadLogin.setVisibility(View.VISIBLE);
        viewOverlay.setVisibility(View.VISIBLE);
    }

    public void hideLoadingUI(){
        pbLoadLogin.setVisibility(View.GONE);
        viewOverlay.setVisibility(View.GONE);
    }
}