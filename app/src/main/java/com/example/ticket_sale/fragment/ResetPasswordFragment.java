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

import com.example.ticket_sale.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ResetPasswordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResetPasswordFragment extends Fragment {
    private TextView txtGoBack;
    private EditText edtPhoneOrMail;
    private Button btnNext;
    private View.OnClickListener btnNextListener;
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

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ResetPasswordFragment.
     */
    // TODO: Rename and change types and number of parameters
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
        if(context instanceof View.OnClickListener){
            btnNextListener = (View.OnClickListener) context;
        }else{
            throw new RuntimeException(context.toString() + "must implement FragmentNavigationListener");
        }
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
        View v = inflater.inflate(R.layout.fragment_reset_password, container, false);
        initViews(v);
        return v;
    }

    private void initViews(View v) {
        txtGoBack = v.findViewById(R.id.txtGoBack);
        edtPhoneOrMail = v.findViewById(R.id.edtPhoneOrEmail);
        btnNext = v.findViewById(R.id.btnNext);


        txtGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResetPasswordFragment.this.requireActivity().finish();
            }
        });

        if (btnNextListener != null) {
            btnNext.setOnClickListener(btnNextListener);
        }
    }

    public static ResetPasswordFragment newInstance() {
        return new ResetPasswordFragment();
    }

    public void setButtonListener(View.OnClickListener listener) {
        this.btnNextListener = listener;
    }

}