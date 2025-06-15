
package com.example.ticket_sale.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ticket_sale.R;
import com.stripe.android.ApiResultCallback;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.Stripe;
import com.stripe.android.model.PaymentMethod;
import com.stripe.android.model.PaymentMethodCreateParams;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;
import com.stripe.android.view.CardFormView;
import com.stripe.android.view.CardInputWidget;
import com.stripe.android.view.CardMultilineWidget;

public class PaymentMethodFragment extends DialogFragment {
    private final String PUBLIC_KEY = "pk_test_51QuPM5BcNft3NmLQZVIJ785cTDy9t8KaqQ7jwCb5GbPOe263Fq6mRrsWrN6TMYt0VKSoYsayw5ODzJSKCIjVfuGX00MHglpjYu";
    private Stripe stripe;

    private TextView txtGoBack;
    private CardMultilineWidget cardMultilineWidget;
    private Button btnAddPayMethod;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PaymentMethodFragment() {
        // Required empty public constructor
    }

    public static PaymentMethodFragment newInstance(String param1, String param2) {
        PaymentMethodFragment fragment = new PaymentMethodFragment();
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
        View root =inflater.inflate(R.layout.fragment_payment_method, container, false);
        initViews(root);

        PaymentConfiguration.init(requireContext(), PUBLIC_KEY);
        stripe = new Stripe(requireContext(), PaymentConfiguration.getInstance(requireContext()).getPublishableKey());
        return root;
    }
    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
        }
    }
    private void initViews(View root) {
        txtGoBack = root.findViewById(R.id.txtGoBack);
        cardMultilineWidget = root.findViewById(R.id.cardInputWidget);
        cardMultilineWidget.setShouldShowPostalCode(false);
        btnAddPayMethod = root.findViewById(R.id.btnAddPayMethod);
        btnAddPayMethod.setOnClickListener(v -> createPaymentMethod());
        txtGoBack.setOnClickListener(v -> dismiss());
    }

    private void createPaymentMethod() {
        PaymentMethodCreateParams params = cardMultilineWidget.getPaymentMethodCreateParams();
        if (params == null) {
            Toast.makeText(getContext(), "Thông tin thẻ kông hợp lệ", Toast.LENGTH_SHORT).show();
            return;
        }
        stripe.createPaymentMethod( params, new ApiResultCallback<PaymentMethod>() {
                @Override
                public void onSuccess(@NonNull PaymentMethod paymentMethod) {
                    String paymentMethodId = paymentMethod.id;
                    Bundle bundle = new Bundle();
                    bundle.putString("paymentMethodId", paymentMethod.id);
                    getParentFragmentManager().setFragmentResult("paymentMethodResult", bundle);
                    dismiss();
                }

                @Override
                public void onError(@NonNull Exception e) {
                    Log.e("Stripe", "Lỗi tạo PaymentMethod: " + e.getMessage());
                    Toast.makeText(getContext(), "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        );
    }

    private void onPaymentSheetResult(final PaymentSheetResult paymentSheetResult) {
        if (paymentSheetResult instanceof PaymentSheetResult.Completed) {
            Toast.makeText(requireContext(), "Thanh toán thành công", Toast.LENGTH_SHORT).show();
        } else if (paymentSheetResult instanceof PaymentSheetResult.Canceled) {
            Toast.makeText(requireContext(), "Đã huỷ", Toast.LENGTH_SHORT).show();
        } else if (paymentSheetResult instanceof PaymentSheetResult.Failed) {
            Toast.makeText(requireContext(), "Lỗi thanh toán", Toast.LENGTH_SHORT).show();
        }
    }

//    Mô tả	            Số thẻ	                Expiry	    CVC
//    Thành công	4242 4242 4242 4242	        12/34	    123
//    Bị từ chối	4000 0000 0000 0002	        12/34	    123
//    Sai CVC	    4000 0000 0000 0101	        12/34	    123
}