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
import android.widget.TextView;
import android.widget.Toast;

import com.example.ticket_sale.R;
import com.example.ticket_sale.activity.LoginActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ResetPasswordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResetPasswordFragment extends Fragment {
    private TextView txtGoBack;
    private EditText edtPhoneOrMail;
    private Button btnNext;
//    private View.OnClickListener btnNextListener;
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
        return root;
    }

    private void initViews(View root) {
        txtGoBack =  root.findViewById(R.id.txtGoBack);
        edtPhoneOrMail =  root.findViewById(R.id.edtPhoneOrEmail);
        btnNext =  root.findViewById(R.id.btnNext);

        txtGoBack.setOnClickListener(v -> ResetPasswordFragment.this.requireActivity().finish());

        btnNext.setOnClickListener(v -> {
            String phoneOrMail = edtPhoneOrMail.getText().toString();
            if(phoneOrMail.trim().isEmpty()) {
                Toast.makeText(getContext(), "Vui lòng nhập số điện thoại hoặc email.", Toast.LENGTH_SHORT).show();
                return;
            }

            Bundle bundle = new Bundle();
            if(isPhone(phoneOrMail)){
                bundle.putString("phone",phoneOrMail);
                PhoneOTPAuthFragment phoneOTPAuthFragment= new PhoneOTPAuthFragment();
                phoneOTPAuthFragment.setArguments(bundle);
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, phoneOTPAuthFragment)
                        .addToBackStack(null)
                        .commit();
                return;
            }
            if(isEmail(phoneOrMail)){
                bundle.putString("email",phoneOrMail);
                EmailOTPAuthFragment emailOTPAuthFragment= new EmailOTPAuthFragment();
                emailOTPAuthFragment.setArguments(bundle);
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, emailOTPAuthFragment)
                        .addToBackStack(null)
                        .commit();
                return;
            }
            Toast.makeText(getContext(), "Email hoặc số điện thoại không hợp lệ.", Toast.LENGTH_SHORT).show();
        });
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

}