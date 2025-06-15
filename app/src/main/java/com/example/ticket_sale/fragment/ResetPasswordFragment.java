package com.example.ticket_sale.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ticket_sale.R;
import com.example.ticket_sale.viewmodel.EmailOTPViewModel;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ResetPasswordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResetPasswordFragment extends Fragment {
    private TextView txtGoBack;
    private EditText edtPhoneOrMail;
    private Button btnNext;
    private ProgressBar pbLoadSendOTP;
    private View viewOverlay;

    private EmailOTPViewModel emailOTPViewModel;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ResetPasswordFragment() {
        // Required empty public constructor
    }

    public static ResetPasswordFragment newInstance(String param1, String param2) {
        ResetPasswordFragment fragment = new ResetPasswordFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
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
        View root = inflater.inflate(R.layout.fragment_reset_password, container, false);
        initViews(root);
        emailOTPViewModel = new EmailOTPViewModel();
        return root;
    }

    private void initViews(View root) {
        txtGoBack =  root.findViewById(R.id.txtGoBack);
        edtPhoneOrMail =  root.findViewById(R.id.edtPhoneOrEmail);
        btnNext =  root.findViewById(R.id.btnNext);
        pbLoadSendOTP = root.findViewById(R.id.pbLoadSendOtp);
        viewOverlay = root.findViewById(R.id.viewOverlay);

        txtGoBack.setOnClickListener(v -> ResetPasswordFragment.this.requireActivity().finish());

        btnNext.setOnClickListener(v -> sendOTP());
    }

    private void sendOTP() {
        showLoadingUI();
        String phoneOrMail = edtPhoneOrMail.getText().toString();
        if(phoneOrMail.trim().isEmpty()) {
            Toast.makeText(getContext(), "Vui lòng nhập số điện thoại hoặc email.", Toast.LENGTH_SHORT).show();
            hideLoadingUI();
            return;
        }

        if(isPhone(phoneOrMail)){
            navigateToPhoneOTPAuth(phoneOrMail);
        } else if(isEmail(phoneOrMail)){
            emailOTPViewModel.sendOtpToEmail(phoneOrMail).observe( getViewLifecycleOwner(), response ->{
                if(response == null){
                    Toast.makeText(getContext(), "Lỗi kết nối. Vui lòng thử lại!", Toast.LENGTH_SHORT).show();
                }else if(response.getStatusCode() != 200){
                    String message = String.format(Locale.getDefault(),"%s",response.getMessage());
                    Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                }else{
                    navigateToEmailOTPAuth(phoneOrMail);
                }
                hideLoadingUI();
            });
        }else{
            hideLoadingUI();
            Toast.makeText(getContext(), "Email hoặc số điện thoại không hợp lệ.", Toast.LENGTH_SHORT).show();
        }

    }

    private void navigateToEmailOTPAuth(String email) {
        Bundle bundle = new Bundle();
        bundle.putString("email",email);
        OtpNewPasswordFragment otpNewPasswordFragment= new OtpNewPasswordFragment();
        otpNewPasswordFragment.setArguments(bundle);
        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, otpNewPasswordFragment)
                .addToBackStack(null)
                .commit();
    }

    private void navigateToPhoneOTPAuth(String phone) {
        Bundle bundle = new Bundle();
        bundle.putString("phone",phone);
        PhoneOTPAuthFragment phoneOTPAuthFragment= new PhoneOTPAuthFragment();
        phoneOTPAuthFragment.setArguments(bundle);
        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, phoneOTPAuthFragment)
                .addToBackStack(null)
                .commit();
    }

    private boolean isPhone(String input) {
        return input.matches("\\d{10}");
    }

    private boolean isEmail(String input) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(input).matches();
    }

    public static ResetPasswordFragment newInstance() {
        return new ResetPasswordFragment();
    }

    public void setButtonListener(View.OnClickListener listener) {
//        this.btnNextListener = listener;
    }

    private void showLoadingUI(){
        pbLoadSendOTP.setVisibility(View.VISIBLE);
        viewOverlay.setVisibility(View.VISIBLE);
    }

    private void hideLoadingUI(){
        pbLoadSendOTP.setVisibility(View.GONE);
        viewOverlay.setVisibility(View.GONE);
    }
}