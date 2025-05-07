package com.example.ticket_sale.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;

import com.example.ticket_sale.R;
import com.example.ticket_sale.base.BaseAuthActivity;
import com.example.ticket_sale.viewmodel.AuthViewModel;

import java.util.Map;

public class LoginActivity extends BaseAuthActivity {
    private TextView txtGoBack;
    private EditText edtUsername;
    private EditText edtPassword;
    private TextView txtForgotPassword;
    private Button btnLogin;
    private Button btnRedirectRegister;

    private final AuthViewModel authViewModel = new AuthViewModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        initViews();

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

        txtGoBack.setOnClickListener(v -> finish());

        txtForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ResetPasswordActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(v -> {
            String username = edtUsername.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Tên đăng nhập và mật khẩu không được để trống",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            authViewModel.auth(username, password).observe(LoginActivity.this, result -> {
                if (result == null || !result.containsKey("accessToken")) {
                    Log.e("authRepository.auth", "Can't not get token!");
                    Toast.makeText(LoginActivity.this, "Tên đăng nhập hoặc mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            });
//
//
//
//            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//            startActivity(intent);
        });

        btnRedirectRegister.setOnClickListener(v -> {
            Intent intent  = new Intent(LoginActivity.this,RegisterActivity.class);
            startActivity(intent);
        });
    }
}