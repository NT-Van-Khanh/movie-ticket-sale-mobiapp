package com.example.ticket_sale.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.ticket_sale.activity.LoginActivity;
import com.example.ticket_sale.R;
import com.example.ticket_sale.model.User;
import com.example.ticket_sale.viewmodel.RegisterViewModel;

public class RegisterUserFragment extends Fragment {
    private TextView txtGoBack;
    private Button btnNext;
    private Button btnRedirectLogin;
    private Toolbar toolbar;
    private NestedScrollView scrollView;
    private EditText edtFullName;
    private EditText edtPhone;
    private EditText edtEmail;
    private EditText edtUsername;
    private EditText edtPassword;
    private EditText edtConfirmPassword;
    private CheckBox cbAgreeTerms;
    private ProgressBar pbLoadData;
    private View viewOverlay;
    private User user;

    private RegisterViewModel registerViewModel;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RegisterUserFragment() {
        // Required empty public constructor
    }

    public static RegisterUserFragment newInstance(String param1, String param2) {
        RegisterUserFragment fragment = new RegisterUserFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


//    @Override
//    public void onAttach(@NonNull Context context) {
//        super.onAttach(context);
//        if(context instanceof View.OnClickListener){
//            btnNextListener = (View.OnClickListener) context;
//        }else{
//            throw new RuntimeException(context.toString() + "must implement FragmentNavigationListener");
//        }
//    }

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
        View root = inflater.inflate(R.layout.fragment_register_user, container, false);
        initView(root);
        registerViewModel = new RegisterViewModel();
        return root;
    }

    private void initView(View root){
        scrollView = root.findViewById(R.id.nesScrollContainer); // cập nhật ID nếu có
        toolbar = root.findViewById(R.id.toolbar);

        txtGoBack = root.findViewById(R.id.txtGoBack);
        edtFullName = root.findViewById(R.id.edtFullName);
        edtEmail = root.findViewById(R.id.edtEmail);
        edtPhone = root.findViewById(R.id.edtPhoneNumber);
        edtUsername = root.findViewById(R.id.edtUsername);
        edtPassword = root.findViewById(R.id.edtPassword);
        edtConfirmPassword = root.findViewById(R.id.edtConfirmPassword);
        cbAgreeTerms = root.findViewById(R.id.cbAgreeTerms);
        btnNext= root.findViewById(R.id.btnRegister);
        btnRedirectLogin = root.findViewById(R.id.btnRedirectLogin);
        viewOverlay = root.findViewById(R.id.viewOverlay);
        pbLoadData = root.findViewById(R.id.pbLoadRegisterAccount);

        btnNext.setOnClickListener(v -> registerAccount());

        txtGoBack.setOnClickListener(v -> {
            if (RegisterUserFragment.this.getActivity() != null) {
                RegisterUserFragment.this.getActivity().finish();
            }
        });



        btnRedirectLogin.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        });

        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY > oldScrollY) {
                    // Kéo lên -> Ẩn Toolbar
                    toolbar.animate().translationY(-toolbar.getHeight()).setDuration(200);
                } else if (scrollY < oldScrollY) {
                    // Kéo xuống -> Hiện Toolbar
                    toolbar.animate().translationY(0).setDuration(200);
                }
            }
        });

    }

    private void registerAccount() {
        user = getUserFromView();
        fetchAddAccount(user);
    }

    private void fetchAddAccount(User user){
        if(user == null)  return;
        showLoadingUI();
        registerViewModel.addAccount(user,"123123123").observe(getViewLifecycleOwner(), response ->{
            if(response == null) {
                Toast.makeText(getContext(), "Không thể thêm tải khoản.", Toast.LENGTH_LONG).show();
                hideLoadingUI();
                return;
            }
            if(response.getStatusCode() == 201){
                fetchSendOtpToEmail();
            }else if(response.getMessage() != null){
                Toast.makeText(getContext(), response.getMessage(), Toast.LENGTH_LONG).show();
                hideLoadingUI();
            }
        });
    }

    private void fetchSendOtpToEmail() {
        if(user == null)  return;
        registerViewModel.sendOtToEmail(user.getEmail()).observe(getViewLifecycleOwner(), response ->{
            if(response != null && response.getStatusCode()==200){
                navigateToAuthEmailFragment();
            }else{
                Toast.makeText(getContext(), "Không thể xác thực email này.", Toast.LENGTH_LONG).show();
            }
            hideLoadingUI();
        });
    }

    private void navigateToAuthEmailFragment() {
        String password =edtPassword.getText().toString().trim();
        Bundle b = new Bundle();
//        b.putParcelable("user",user);
        b.putString("email",user.getEmail());
        b.putString("password", password);
        EmailOTPAuthFragment emailOTPAuthFragment = new EmailOTPAuthFragment();
        emailOTPAuthFragment.setArguments(b);
        getParentFragmentManager().beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .replace(R.id.fragment_container, emailOTPAuthFragment)
                .addToBackStack(null)
                .commit();
    }


    public static RegisterUserFragment newInstance() {
        return new RegisterUserFragment();
    }


    private User getUserFromView() {
        String fullName = edtFullName.getText().toString().trim();
        if(fullName.isEmpty()) {
            edtFullName.requestFocus(); // Đặt focus vào trường này
            Toast.makeText(getActivity(), "Vui lòng nhập tên đầy đủ", Toast.LENGTH_SHORT).show();
            return null;
        }
        String phone = edtPhone.getText().toString().trim();
        if (phone.isEmpty()) {
            edtPhone.requestFocus();
            Toast.makeText(getActivity(), "Vui lòng nhập số điện thoại", Toast.LENGTH_SHORT).show();
            return null;
        }

        String email = edtEmail.getText().toString().trim();
        if (email.isEmpty()) {
            edtEmail.requestFocus();
            Toast.makeText(getActivity(), "Vui lòng nhập email", Toast.LENGTH_SHORT).show();
            return null;
        }

        String username = edtUsername.getText().toString().trim();
        if (username.isEmpty()) {
            edtUsername.requestFocus();
            Toast.makeText(getActivity(), "Vui lòng nhập tên tài khoản", Toast.LENGTH_SHORT).show();
            return null;
        }

        String password = edtPassword.getText().toString().trim();
        if (password.isEmpty()) {
            edtPassword.requestFocus();
            Toast.makeText(getActivity(), "Vui lòng nhập mật khẩu", Toast.LENGTH_SHORT).show();
            return null;
        }

        String confirmPassword = edtConfirmPassword.getText().toString().trim();
        if (confirmPassword.isEmpty()) {
            edtConfirmPassword.requestFocus();
            Toast.makeText(getActivity(), "Vui lòng nhập xác nhận mật khẩu", Toast.LENGTH_SHORT).show();
            return null;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(getActivity(), "Mật khẩu và xác nhận mật khẩu không khớp", Toast.LENGTH_SHORT).show();
            edtConfirmPassword.requestFocus();
            return null;
        }

        if(!cbAgreeTerms.isChecked()){
            Toast.makeText(getActivity(), "Vui lòng chấp nhận chính sách điều khoản", Toast.LENGTH_SHORT).show();
            cbAgreeTerms.requestFocus();
            return null;
        }
        Log.e("test", fullName + " "+ phone +" "+email +" "+username);
        return new User(fullName, phone, email, username);
    }

    private void showLoadingUI(){
        pbLoadData.setVisibility(View.VISIBLE);
        viewOverlay.setVisibility(View.VISIBLE);
    }

    private void hideLoadingUI(){
        pbLoadData.setVisibility(View.GONE);
        viewOverlay.setVisibility(View.GONE);
    }
}