package com.example.ticket_sale.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ticket_sale.R;
import com.example.ticket_sale.activity.MainActivity;
import com.example.ticket_sale.adapter.OrderItemAdapter;
import com.example.ticket_sale.adapter.PayMethodAdapter;
import com.example.ticket_sale.mapper.OrderMapper;
import com.example.ticket_sale.model.Movie;
import com.example.ticket_sale.model.Order;
import com.example.ticket_sale.model.OrderDisplayItem;
import com.example.ticket_sale.model.PaymentMethod;
import com.example.ticket_sale.model.Screen;
import com.example.ticket_sale.model.Showtime;
import com.example.ticket_sale.util.ViLocaleUtil;

import java.util.ArrayList;
import java.util.List;

public class PaymentFragment extends Fragment {
    private List<PaymentMethod> payMethods;
    private List<OrderDisplayItem> orderItems;
    private Order order;

    private PayMethodAdapter payMethodAdapter;
    private OrderItemAdapter orderItemAdapter;

    private TextView txtMovieTitle;
    private ImageView imgMovieImage;
    private TextView txtTheaterAddressDetail;
    private TextView txtMovieShowtime;
    private RecyclerView rcViewOrderItems;
    private RecyclerView rcViewPayMethods;
    private TextView txtTotalCost;
    private TextView txtFinalCost;
    private Button btnNext;
    private TextView txtGoBack;

    public PaymentFragment(List<PaymentMethod> payMethods, Order order) {
        this.payMethods = payMethods;
        this.order = order;
    }

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PaymentFragment() {
        // Required empty public constructor
    }

    public static PaymentFragment newInstance(String param1, String param2) {
        PaymentFragment fragment = new PaymentFragment();
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
        View root = inflater.inflate(R.layout.fragment_payment, container, false);
        initData();
        initViews(root);
        setDataForViews();
        return root;
    }

    private void setDataForViews() {
        if(order == null) return;
        Movie movie = order.getMovie();
        if(movie == null) return;
        txtMovieTitle.setText(movie.getTitle());
        imgMovieImage.setImageResource(movie.getImageResId());

        Showtime showtime  = order.getShowtime();
        if(showtime == null) return;
        txtMovieShowtime.setText(String.format("%s - %s",showtime.getTimeStart(), showtime.getDate()));
        Screen screen = order.getScreen();
        if(screen == null) return;
        txtTheaterAddressDetail.setText(String.format("%s - %s", screen.getTheater().getName(), screen.getName()));

        Long orderTotalCost = 0L;
        for(OrderDisplayItem orderItem : orderItems){
            orderTotalCost += orderItem.getTotalPrice();
        }
        txtTotalCost.setText(ViLocaleUtil.formatLocalCurrency(orderTotalCost));
        txtFinalCost.setText(ViLocaleUtil.formatLocalCurrency(orderTotalCost));
        payMethodAdapter = new PayMethodAdapter(payMethods);
        rcViewPayMethods.setAdapter(payMethodAdapter);

        orderItemAdapter = new OrderItemAdapter(orderItems);
        rcViewOrderItems.setAdapter(orderItemAdapter);

    }

    private void initData() {
        payMethods = new ArrayList<>();
        payMethods.add(new PaymentMethod("PM1", "VNPay", PaymentMethod.Method.VNPAY, "Phương thức thanh toán VNPay",R.drawable.pm_vn_pay));
        payMethods.add(new PaymentMethod("PM2", "ZaloPay", PaymentMethod.Method.ZALOPAY, "Ví điện tử ZaloPay",R.drawable.pm_vn_pay));
        payMethods.add(new PaymentMethod("PM3", "Tài khoản ngân hàng", PaymentMethod.Method.BANKING,"Thanh toán bằng thẻ ngân hàng",R.drawable.pm_vn_pay));
        payMethods.add(new PaymentMethod("PM4", "Master Card", PaymentMethod.Method.MASTER_CARD,"Thanh toán bằng thẻ quốc tế",R.drawable.pm_vn_pay));
        if(getArguments() == null) return;
        order = getArguments().getParcelable("order");
        orderItems = OrderMapper.mapToDisplayItems(order);

    }

    private void initViews(View root){
        txtMovieTitle = root.findViewById(R.id.txtMovieTitle);
        txtTheaterAddressDetail = root.findViewById(R.id.txtTheaterAddressDetail);
        imgMovieImage = root.findViewById(R.id.imgMovieImage);
        txtMovieShowtime = root.findViewById(R.id.txtMovieShowtime);
        txtTotalCost = root.findViewById(R.id.txtTotalCost);
        txtFinalCost = root.findViewById(R.id.txtFinalCost);

        rcViewOrderItems = root.findViewById(R.id.rcViewOrderItems);
        rcViewOrderItems.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rcViewPayMethods = root.findViewById(R.id.rcViewPayMethods);
        rcViewPayMethods.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        txtGoBack = root.findViewById(R.id.txtGoBack);
        txtGoBack.setOnClickListener(v -> getParentFragmentManager().popBackStack());

        btnNext = root.findViewById(R.id.btnNext);
        btnNext.setOnClickListener(this::onClick);
    }

    private void onClick(View v) {
        if (payMethodAdapter.getSelectedPosition() == -1) {
            Toast.makeText(getContext(), "Vui lòng chọn phương thức thanh toán.", Toast.LENGTH_SHORT).show();
            return;
        }
        requireActivity().getSupportFragmentManager()
                .popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new HomeFragment())
                .commit();
        ((MainActivity) requireActivity()).showBottomNav();
    }
}