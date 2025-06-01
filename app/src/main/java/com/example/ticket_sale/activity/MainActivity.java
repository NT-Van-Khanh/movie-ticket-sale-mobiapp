package com.example.ticket_sale.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.ticket_sale.R;
import com.example.ticket_sale.fragment.HomeFragment;
import com.example.ticket_sale.fragment.MovieFragment;
import com.example.ticket_sale.fragment.TheaterFragment;
import com.example.ticket_sale.fragment.ProfileFragment;
import com.example.ticket_sale.util.TokenManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    private FrameLayout fragmentContainer;
    private BottomNavigationView bottomNavigationView;
    private Fragment selectedFragment;
//    private ProgressBar pbLoadData;
//    private View viewOverlay;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TokenManager.init(this);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        intiViews();

//        ApiTester apiTester = new ApiTester(this,this);
//        apiTester.testAllApis();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });
    }

    private void intiViews() {
        fragmentContainer = findViewById(R.id.fragment_container);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener( this::onNavigationItemSelected);
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
    }

    private boolean onNavigationItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();

        if (itemId == R.id.nav_home) {
            selectedFragment = new HomeFragment();
        } else if (itemId == R.id.nav_movie_theaters) {
            selectedFragment = new TheaterFragment();
        } else if (itemId == R.id.nav_movies) {
            selectedFragment = new MovieFragment();
        } else if (itemId == R.id.nav_profile) {
            selectedFragment = new ProfileFragment();
        }
        if (selectedFragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, selectedFragment)
                    .commit();
        }
        return true;
    }

    public void hideBottomNav() {
        bottomNavigationView.animate()
                .translationY(bottomNavigationView.getHeight())
                .setDuration(200);
        bottomNavigationView.setVisibility(View.GONE);
    }

    public void showBottomNav() {
        bottomNavigationView.animate()
                .translationY(0)
                .setDuration(200);
        bottomNavigationView.setVisibility(View.VISIBLE);
    }


//    public void  showLoadingUI(){
//        pbLoadData.setVisibility(View.VISIBLE);
//        viewOverlay.setVisibility(View.VISIBLE);
//    }
//
//    public void hideLoadingUI(){
//        pbLoadData.setVisibility(View.GONE);
//        viewOverlay.setVisibility(View.GONE);
//    }
}

//        try{
//            authRepository.auth("caocuong", "123123123")
//                    .observe(this, tokenMap -> {
//                        if (tokenMap != null&& tokenMap.containsKey("accessToken")) {
//                            String token = tokenMap.get("accessToken");
//                            userRepository.getUserById("cd664219-042d-4ffc-b851-5bbba4005d8b", token)
//                                    .observe(this, user -> {
//                                        if (user != null && user.getData() != null) {
//                                            Log.e("Dữ liệu user", user.getData().toString());
//                                        } else {
//                                            Log.e("Dữ liệu user", "Không tìm thấy người dùng!");
//                                        }
//                                    });
//                        } else {
//                            Log.e("authRepository.auth", "Không lấy được token!");
//                        }
//                    });
//        }catch (Exception e){
//            Log.e("userRepository.getUserById","Error when get UserDTO Info");
//        }