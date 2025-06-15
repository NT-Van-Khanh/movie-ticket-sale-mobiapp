package com.example.ticket_sale.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
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
import com.example.ticket_sale.viewmodel.EmailOTPViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OtpNewPasswordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OtpNewPasswordFragment extends Fragment {
    private TextView txtGoBack;
    private List<EditText> edtNumbers;
    private EditText edtNewPassword;
    private  EditText edtConfirmNewPassword;
    private Button btnConfirm;
    private Button btnSendOtpAgain;
    private TextView txtTimeLeft;

    private ProgressBar pbLoadVerifyOTP;
    private View viewOverlay;

    private EmailOTPViewModel emailOTPViewModel;
    
    private String email;


    private Handler handler = new Handler(Looper.getMainLooper());
    private int timeLeft = 180;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public OtpNewPasswordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OtpNewPasswordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OtpNewPasswordFragment newInstance(String param1, String param2) {
        OtpNewPasswordFragment fragment = new OtpNewPasswordFragment();
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
        View root = inflater.inflate(R.layout.fragment_otp_new_password, container, false);
        initView(root);
        initData();
        startCountdown();
        return root; 
    }

    private void initData() {
        if(getArguments() == null) {
            Toast.makeText(getContext(),"Không tìm thấy email.",Toast.LENGTH_SHORT).show();
            getParentFragmentManager().popBackStack();
            return;
        }
        email = getArguments().getString("email");
        emailOTPViewModel = new EmailOTPViewModel();
    }   

    private void initView(View root) {
        this.txtGoBack = root.findViewById(R.id.txtGoBack);
        this.edtNewPassword = root.findViewById(R.id.edtNewPassword);
        this.edtConfirmNewPassword = root.findViewById(R.id.edtConfirmNewPassword);
        this.btnConfirm = root.findViewById(R.id.btnConfirm);
        this.btnSendOtpAgain =  root.findViewById(R.id.btnSendOtpAgain);
        this.pbLoadVerifyOTP = root.findViewById(R.id.pbLoadVerifyOTP);
        this.txtTimeLeft = root.findViewById(R.id.txtTimeLeft);
        this.viewOverlay = root.findViewById(R.id.viewOverlay);

        this.edtNumbers = new ArrayList<>();
        edtNumbers.add(root.findViewById(R.id.edtNum1));
        edtNumbers.add(root.findViewById(R.id.edtNum2));
        edtNumbers.add(root.findViewById(R.id.edtNum3));
        edtNumbers.add(root.findViewById(R.id.edtNum4));
        edtNumbers.add(root.findViewById(R.id.edtNum5));
        edtNumbers.add(root.findViewById(R.id.edtNum6));
        
        txtGoBack.setOnClickListener(v -> getParentFragmentManager().popBackStack());
        btnConfirm.setOnClickListener(v -> otpVerification());
        btnSendOtpAgain.setOnClickListener(v -> sendOtpToEmailAgain());
        
    }

    private void sendOtpToEmailAgain() {
        showLoadingUI();
        btnSendOtpAgain.setEnabled(false);
        emailOTPViewModel.sendOtpToEmail(email).observe(getViewLifecycleOwner(), response ->{
            if(response == null){
                Toast.makeText(getContext(), "Lỗi kết nối. Vui lòng thử lại!", Toast.LENGTH_SHORT).show();
                btnSendOtpAgain.setEnabled(true);
            }else if(response.getStatusCode() != 200){
                String message = String.format(Locale.getDefault(),"%s",response.getMessage());
                btnSendOtpAgain.setEnabled(true);
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getContext(), "Gửi OTP thành công. Vui lòng kiểm tra hộp thư email.", Toast.LENGTH_SHORT).show();
                startCountdown();
            }
            hideLoadingUI();
        });
    }

    private void otpVerification() {

        String newPassword = edtNewPassword.getText().toString().trim();
        if (newPassword.isEmpty()) {
            edtNewPassword.requestFocus();
            Toast.makeText(getActivity(), "Vui lòng nhập mật khẩu", Toast.LENGTH_SHORT).show();
            return;
        }

        String confirmPassword = edtConfirmNewPassword.getText().toString().trim();
        if (confirmPassword.isEmpty()) {
            edtConfirmNewPassword.requestFocus();
            Toast.makeText(getActivity(), "Vui lòng nhập xác nhận mật khẩu", Toast.LENGTH_SHORT).show();
            return;
        }

        StringBuilder otp = new StringBuilder();
        for( EditText number : edtNumbers){
            otp.append(number.getText().toString());
        }
        showLoadingUI();
        emailOTPViewModel.authEmailOTP(this.email, otp.toString(), newPassword).observe(getViewLifecycleOwner(), response ->{
            if(response == null){
                Toast.makeText(getContext(), "Lỗi kết nối. Vui lòng thử lại!", Toast.LENGTH_SHORT).show();
            }else if(response.getStatusCode() != 200){
                String message = String.format(Locale.getDefault(),"%s",response.getMessage());
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getContext(), "Đổi mật khẩu thành công. Vui lòng đăng nhập để tiếp tục.", Toast.LENGTH_SHORT).show();
                navigateToLogin();
            }
            hideLoadingUI();
        });
    }


    private void startCountdown() {
        timeLeft = 180;
        handleStartCountdown();
        handler.post(timeLeftRunnable);
    }

    private final Runnable timeLeftRunnable = new Runnable(){
        @Override
        public void run() {
            if (timeLeft > 0) {
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
    private void navigateToLogin() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }

    private void showLoadingUI(){
        pbLoadVerifyOTP.setVisibility(View.VISIBLE);
        viewOverlay.setVisibility(View.VISIBLE);
    }

    private void hideLoadingUI(){
        pbLoadVerifyOTP.setVisibility(View.GONE);
        viewOverlay.setVisibility(View.GONE);
    }
}