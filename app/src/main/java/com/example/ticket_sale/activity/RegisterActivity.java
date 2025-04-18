package com.example.ticket_sale.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.ticket_sale.R;
import com.example.ticket_sale.fragment.PhoneOTPAuthFragment;
import com.example.ticket_sale.fragment.RegisterUserFragment;

public class RegisterActivity extends AppCompatActivity {
    private FrameLayout fragmentContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        fragmentContainer = findViewById(R.id.fragment_container);

        if(savedInstanceState ==null){
            RegisterUserFragment registerUserFragment = RegisterUserFragment.newInstance();
            registerUserFragment.setBtnNextListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, new PhoneOTPAuthFragment())
                                .addToBackStack(null)
                                .commit();
                }
            });

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, registerUserFragment)
                    .commit();
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)  // Cho phép quay lại bước trước
                .commit();
    }

}