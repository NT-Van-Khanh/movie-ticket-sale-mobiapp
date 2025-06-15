package com.example.ticket_sale.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ticket_sale.R;
import com.example.ticket_sale.activity.LoginActivity;
import com.example.ticket_sale.model.User;
import com.example.ticket_sale.viewmodel.EmailOTPViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class EmailOTPAuthFragment extends Fragment {
    private List<EditText> edtNumbers;
    private TextView txtTimeLeft;
    private TextView txtGoBack;
    private Button btnConfirm;
    private ProgressBar pbLoadVerifyOtp;
    private View viewOverlay;
    private Button btnSendOtpAgain;

    private int timeLeft = 180;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private User user;
    private String password;
    private String email;

    private EmailOTPViewModel emailOTPViewModel;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EmailOTPAuthFragment() {
        // Required empty public constructor
    }

    public static EmailOTPAuthFragment newInstance(String param1, String param2) {
        EmailOTPAuthFragment fragment = new EmailOTPAuthFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_email_otp_auth, container, false);
        initViews(root);
        initData();
        startCountdown();
        return root;
    }

    private void startCountdown() {
        timeLeft = 180;
        handler.post(timeLeftRunnable);
        handleStartCountdown();
    }

    private void initData() {
        Bundle bundle = getArguments();
        if (bundle == null) {
            Log.e("EmailOTPAuthFragment", "Bundle is null. Không nhận được dữ liệu từ Fragment trước đó.");
            return;
        }
        user = bundle.getParcelable("user");
        email = bundle.getString("email");
        password = bundle.getString("password");

        emailOTPViewModel = new EmailOTPViewModel();
    }

    private final Runnable timeLeftRunnable = new Runnable(){
        @Override
        public void run() {
            if (timeLeft >= 0) {
                txtTimeLeft.setText(String.format("(%ss)",timeLeft));
                timeLeft--;
                handler.postDelayed(this, 1000);
            } else {
                handleCountdownTimeout();

            }
        }
    };

    private void handleCountdownTimeout() {
        btnSendOtpAgain.setEnabled(true);
        btnConfirm.setEnabled(false);
        for(EditText edtNumber : edtNumbers){
            edtNumber.setEnabled(false);
        }
    }

    private void handleStartCountdown(){
        btnSendOtpAgain.setEnabled(false);
        btnConfirm.setEnabled(true);
        for(EditText edtNumber : edtNumbers){
            edtNumber.setEnabled(true);
        }
    }

    private void verifyEmailOTP(){
        showLoadingUI();
        StringBuilder otp = new StringBuilder();
        for( EditText number : edtNumbers){
            otp.append(number.getText().toString());
        }
        emailOTPViewModel.authEmailOTP(email, otp.toString(), password).observe(getViewLifecycleOwner(),
            response ->{
            if(response == null){
                Toast.makeText(getContext(), "Lỗi kết nối. Vui lòng thử lại!", Toast.LENGTH_SHORT).show();
            }else if(response.getStatusCode() != 200){
                String message = String.format(Locale.getDefault(),"%s",response.getMessage());
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getContext(), "Xác nhận thành công. Vui lòng đăng nhập để tiếp tục.", Toast.LENGTH_SHORT).show();
                navigateToLogin();
            }
            hideLoadingUI();
        });
    }

    private void sendOtpToEmailAgain() {
        showLoadingUI();
        emailOTPViewModel.sendOtpToEmail(email).observe(getViewLifecycleOwner(), response ->{
            if(response == null){
                Toast.makeText(getContext(), "Lỗi kết nối. Vui lòng thử lại!", Toast.LENGTH_SHORT).show();
            }else if(response.getStatusCode() != 200){
                String message = String.format(Locale.getDefault(),"%s",response.getMessage());
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getContext(), "Gửi OTP thành công. Vui lòng kiểm tra hộp thư email.", Toast.LENGTH_SHORT).show();
                btnSendOtpAgain.setEnabled(false);
                startCountdown();
            }
            hideLoadingUI();
        });
    }


    private void navigateToLogin() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        requireActivity().finish();
    }


    private void initViews(View root) {
        txtGoBack = root.findViewById(R.id.txtGoBack);
        edtNumbers = new ArrayList<>();
        edtNumbers.add(root.findViewById(R.id.edtNum1));
        edtNumbers.add(root.findViewById(R.id.edtNum2));
        edtNumbers.add(root.findViewById(R.id.edtNum3));
        edtNumbers.add(root.findViewById(R.id.edtNum4));
        edtNumbers.add(root.findViewById(R.id.edtNum5));
        edtNumbers.add(root.findViewById(R.id.edtNum6));
        txtTimeLeft = root.findViewById(R.id.txtTimeLeft);
        btnConfirm = root.findViewById(R.id.btnConfirm);
        pbLoadVerifyOtp = root.findViewById(R.id.pbLoadVerifyOTP);
        viewOverlay = root.findViewById(R.id.viewOverlay);
        btnSendOtpAgain = root.findViewById(R.id.btnSendOtpAgain);

        txtGoBack.setOnClickListener(v->{
            getParentFragmentManager().popBackStack();
        });

        btnConfirm.setOnClickListener(v->{
            verifyEmailOTP();
        });

        btnSendOtpAgain.setOnClickListener( v ->{
            sendOtpToEmailAgain();
        });
    }

    private void showLoadingUI(){
        pbLoadVerifyOtp.setVisibility(View.VISIBLE);
        viewOverlay.setVisibility(View.VISIBLE);
    }

    private void hideLoadingUI(){
        pbLoadVerifyOtp.setVisibility(View.GONE);
        viewOverlay.setVisibility(View.GONE);
    }
}