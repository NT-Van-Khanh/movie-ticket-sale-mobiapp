package com.example.ticket_sale.fragment;

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
import com.example.ticket_sale.viewmodel.UpdateUserViewModel;


public class ProfileDetailFragment extends Fragment {
    private TextView txtGoBack;
    private EditText edtName;
    private EditText edtPhone;
    private EditText edtEmail;
    private Button btnConfirm;
    private ProgressBar pbLoadUpdateUser;
    private View viewOverlay;

    private UpdateUserViewModel updateUserInfoViewModel;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileDetailFragment() {

    }
    public static ProfileDetailFragment newInstance(String param1, String param2) {
        ProfileDetailFragment fragment = new ProfileDetailFragment();
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
        View root = inflater.inflate(R.layout.fragment_profile_detail, container, false);
        initViews(root);
        updateUserInfoViewModel = new UpdateUserViewModel();
        return root;
    }

    private void initViews(View root) {
        this.txtGoBack = root.findViewById(R.id.txtGoBack);
        this.edtName = root.findViewById(R.id.edtFullName);
        this.edtPhone = root.findViewById(R.id.edtPhoneNumber);
        this.edtEmail = root.findViewById(R.id.edtEmail);
        this.btnConfirm = root.findViewById(R.id.btnConfirm);
        this.pbLoadUpdateUser = root.findViewById(R.id.pbLoadUpdateUser);
        this.viewOverlay = root.findViewById(R.id.viewOverlay);
        txtGoBack.setOnClickListener(v ->{
            getParentFragmentManager().popBackStack();
        });

        btnConfirm.setOnClickListener(v -> {
            updateUserInformation();
        });
    }

    private void updateUserInformation() {

        String name = edtName.getText().toString().trim();
        if(name.isEmpty()) {
            edtName.requestFocus(); // Đặt focus vào trường này
            Toast.makeText(getActivity(), "Vui lòng nhập tên đầy đủ", Toast.LENGTH_SHORT).show();
            return;
        }
        String phone = edtPhone.getText().toString().trim();
        if (phone.isEmpty()) {
            edtPhone.requestFocus();
            Toast.makeText(getActivity(), "Vui lòng nhập số điện thoại", Toast.LENGTH_SHORT).show();
            return;
        }

        String email = edtEmail.getText().toString().trim();
        if (email.isEmpty()) {
            edtEmail.requestFocus();
            Toast.makeText(getActivity(), "Vui lòng nhập email", Toast.LENGTH_SHORT).show();
            return;
        }

        showLoadingUI();
        updateUserInfoViewModel.updateUser(name, phone, email).observe(getViewLifecycleOwner(), response ->{
            if(response == null){
                Toast.makeText(getContext(), "Lỗi kết nối. Vui lòng thử lại sau.", Toast.LENGTH_LONG).show();
                hideLoadingUI();
                return;
            }else if(response.getStatusCode() != 200){
                Toast.makeText(getContext(), String.format("Có lỗi khi cập nhật thông tin, %s", response.getMessage()), Toast.LENGTH_LONG).show();
                hideLoadingUI();
                return;
            }

            hideLoadingUI();
            Toast.makeText(getContext(), "Cập nhật thông tin thành công.", Toast.LENGTH_LONG).show();
            getParentFragmentManager().popBackStack();
        });
    }

    private void hideLoadingUI() {
        pbLoadUpdateUser.setVisibility(View.GONE);
        viewOverlay.setVisibility(View.GONE);
    }

    private void showLoadingUI() {
        pbLoadUpdateUser.setVisibility(View.VISIBLE);
        viewOverlay.setVisibility(View.VISIBLE);
    }
}