package com.example.ticket_sale.fragment;

import android.app.AlertDialog;
import android.os.Bundle;

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
import com.example.ticket_sale.viewmodel.ChangePasswordViewModel;


public class ChangePasswordFragment extends Fragment {
    private EditText edtCurrentPassword;
    private EditText edtNewPassword;
    private EditText edtConfirmPassword;
    private TextView txtGoBack;
    private Button btnConfirm;
    private View vOverlay;
    private ProgressBar pbLoadChangePassword;

    private ChangePasswordViewModel changePasswordViewModel;

    private static final int SUCCESS = 1;
    private static final int FAIL = 0;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChangePasswordFragment() {
        // Required empty public constructor
    }

    public static ChangePasswordFragment newInstance(String param1, String param2) {
        ChangePasswordFragment fragment = new ChangePasswordFragment();
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
        View root = inflater.inflate(R.layout.fragment_change_password, container, false);
        initViews(root);
        changePasswordViewModel = new ChangePasswordViewModel();
        return root;
    }

    private void initViews(View root) {
        txtGoBack = root.findViewById(R.id.txtGoBack);
        edtCurrentPassword = root.findViewById(R.id.edtCurrentPassword);
        edtNewPassword = root.findViewById(R.id.edtNewPassword);
        edtConfirmPassword = root.findViewById(R.id.edtConfirmPassword);
        btnConfirm = root.findViewById(R.id.btnConfirm);
        pbLoadChangePassword  = root.findViewById(R.id.pbLoadChangePassword);
        vOverlay = root.findViewById(R.id.vOverlay);

        txtGoBack.setOnClickListener( v ->
            requireActivity().getSupportFragmentManager() .popBackStack());

        btnConfirm.setOnClickListener(v ->{
            checkPassword();
        });
    }

    private void checkPassword(){
        showLoading();
        String currentPassword = edtCurrentPassword.getText().toString();
        if (currentPassword.isBlank()){
            showMessage("Vui lòng nhập mật khẩu hiện tại.", FAIL);
            return;
        }
        String newPassword = edtNewPassword.getText().toString();
        if (newPassword.isBlank()){
            showMessage("Vui lòng nhập mật khẩu mới.", FAIL);
            return;
        }
        String confirmPassword =edtConfirmPassword.getText().toString();
        if (confirmPassword.isBlank()){
            showMessage("Vui lòng xác nhận lại mật khẩu mới.", FAIL);
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            showMessage("Mật khẩu mới và mật khẩu xác nhận không khớp.", FAIL);
            return;
        }
        changePasswordViewModel.getAuthResponse(currentPassword)
                .observe(getViewLifecycleOwner(), response ->{
                    if(response != null && response.get("accessToken")!= null){
                        changePassword(newPassword);
                    }else{
                        hideLoading();
                        showMessage("Sai mật khẩu. Vui lòng nhập lại.", FAIL);
                    }
                });
    }

    private void changePassword(String newPassword){
        changePasswordViewModel.getChangePasswordResponse(newPassword)
                .observe(getViewLifecycleOwner(), response ->{
                    if(response != null && response.getStatusCode() == 200){
                        showMessage("Đổi mật khẩu thành công",SUCCESS);
                        getParentFragmentManager().popBackStack();
                    }else{
                        showMessage("Đổi mật khẩu thất bại",FAIL);
                    }
                    hideLoading();
                });
    }

    private void showMessage(String message, int status) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show();
    }

    private void showLoading() {
        pbLoadChangePassword.setVisibility(View.VISIBLE);
        vOverlay.setVisibility(View.VISIBLE);
    }
    private void hideLoading() {
        pbLoadChangePassword.setVisibility(View.GONE);
        vOverlay.setVisibility(View.GONE);
    }
}