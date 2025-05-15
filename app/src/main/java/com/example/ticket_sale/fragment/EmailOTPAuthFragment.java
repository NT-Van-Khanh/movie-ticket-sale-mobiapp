package com.example.ticket_sale.fragment;

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
import android.widget.TextView;

import com.example.ticket_sale.R;
import com.example.ticket_sale.model.User;

import java.util.ArrayList;
import java.util.List;

public class EmailOTPAuthFragment extends Fragment {
    private List<EditText> edtNumbers;
    private TextView txtTimeLeft;
    private TextView txtGoBack;
    private Button btnConfirm;
    private int timeLeft;
    private Handler handler = new Handler(Looper.getMainLooper());
    private User user;
    private String password;
    private String email;

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
        timeLeft = 120;
        btnConfirm.setEnabled(true);
        handler.post(timeLeftRunnable);
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
    }

    private Runnable timeLeftRunnable = new Runnable(){
        @Override
        public void run() {
            if (timeLeft > 0) {
                txtTimeLeft.setText("(" + timeLeft + "s)");
                timeLeft--;
                handler.postDelayed(this, 1000);
            } else {

            }
        }
    };

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
        txtGoBack.setOnClickListener(v->{
            getParentFragmentManager().popBackStack();
        });
    }
}