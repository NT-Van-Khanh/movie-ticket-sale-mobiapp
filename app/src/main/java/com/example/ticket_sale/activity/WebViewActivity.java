package com.example.ticket_sale.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ticket_sale.R;

public class WebViewActivity extends AppCompatActivity {
    private TextView txtGoBack;
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_web_view);
        initViews();

        String url = getIntent().getStringExtra("URL");
        if (url != null) {
            webView.loadUrl(url);
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private void initViews(){
        txtGoBack  = findViewById(R.id.txtGoBack);
        webView = findViewById(R.id.webContainer);
        txtGoBack.setOnClickListener(v -> finish());
    }

    public void loadUrl(String url){
        webView.loadUrl(url);
    }
}