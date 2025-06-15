package com.example.ticket_sale.fragment;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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
import com.example.ticket_sale.model.SharedBookingViewModel;
import com.example.ticket_sale.model.Showtime;
import com.example.ticket_sale.util.ViLocaleUtil;
import com.example.ticket_sale.viewmodel.PaymentViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PaymentFragment extends Fragment {
//    private final int COUNTDOWN_MINUTES = 7;
//    private int countdownTime;
    private final Handler handler = new Handler(Looper.getMainLooper());

    private List<PaymentMethod> payMethods;
    private List<OrderDisplayItem> orderItems;
    private Order order;

    private PayMethodAdapter payMethodAdapter;
    private OrderItemAdapter orderItemAdapter;

    private TextView txtBookingCountdown;
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

    private ProgressBar pbLoadAddBill;
    private View viewOverlay;

    private PaymentViewModel paymentViewModel;
    private SharedBookingViewModel sharedBookingViewModel;
    public PaymentFragment(List<PaymentMethod> payMethods, Order order) {
        this.payMethods = payMethods;
        this.order = order;
    }

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

//    private void startCountdown(){
//        countdownTime = COUNTDOWN_MINUTES*60;
//        handler.post(countdownRunnable);
//    }
    private void startCountdown(){
        sharedBookingViewModel.getCountdownTime().observe(getViewLifecycleOwner(), time -> {
            if (time != null) {
                txtBookingCountdown.setText(String.format(Locale.getDefault(), "%02d:%02d", time / 60, time % 60));
            }
        });
        handler.post(countdownRunnable);
    }

    private final Runnable countdownRunnable = new Runnable() {
        @Override
        public void run() {
            Integer countdownTime = sharedBookingViewModel.getCountdownTime().getValue();
            if(countdownTime != null && countdownTime >= 0){
//                txtBookingCountdown.setText(String.format(Locale.getDefault(), "%02d : %02d", countdownTime/60, countdownTime%60));
                sharedBookingViewModel.decreaseCountdown();
                handler.postDelayed(this, 1000);
            }else{
                handleCountdownTimeout();
            }
        }
    };

    private void handleCountdownTimeout() {
        AlertDialog dialog = new AlertDialog.Builder(requireContext())
                .setTitle("Hết thời gian")
                .setMessage("Thời gian đặt vé đã hết. Bạn sẽ được quay lại màn hình trước.")
                .setCancelable(true)
                .setPositiveButton("OK", (d, which) -> {
                    FragmentManager fm = getParentFragmentManager();
                    fm.popBackStack();
                    fm.popBackStack();
                })
                .create();

        dialog.setOnCancelListener(d -> {
            getParentFragmentManager().popBackStack();
        });

        dialog.show();
    }

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
        initViews(root);
        initData();
        setDataForViews();
        return root;
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(countdownRunnable);
    }

    private void setDataForViews() {
        if(order == null) return;
        Movie movie = order.getMovie();
        if(movie == null) return;
        txtMovieTitle.setText(movie.getTitle());
        Glide.with(this)
                .load(movie.getImageLink())
                .placeholder(R.drawable.mv_elio)
                .error(R.drawable.mv_elio)
                .into(imgMovieImage);
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

        startCountdown();
    }

    private void initData() {
        if(getArguments() == null) return;
        order = getArguments().getParcelable("order");
        orderItems = OrderMapper.mapToDisplayItems(order);
        payMethods = getExamplePayMethods();
        paymentViewModel = new PaymentViewModel();
        sharedBookingViewModel = new ViewModelProvider(requireActivity()).get(SharedBookingViewModel.class);
    }


    private void initViews(View root){
        txtBookingCountdown = root.findViewById(R.id.txtBookingCountdown);
        txtGoBack = root.findViewById(R.id.txtGoBack);
        txtMovieTitle = root.findViewById(R.id.txtMovieTitle);
        txtTheaterAddressDetail = root.findViewById(R.id.txtTheaterAddressDetail);
        imgMovieImage = root.findViewById(R.id.imgMovieImage);
        txtMovieShowtime = root.findViewById(R.id.txtMovieShowtime);
        txtTotalCost = root.findViewById(R.id.txtTotalCost);
        txtFinalCost = root.findViewById(R.id.txtFinalCost);
        rcViewPayMethods = root.findViewById(R.id.rcViewPayMethods);
        rcViewOrderItems = root.findViewById(R.id.rcViewOrderItems);
        btnNext = root.findViewById(R.id.btnNext);
        pbLoadAddBill = root.findViewById(R.id.pbLoadAddBill);
        viewOverlay = root.findViewById(R.id.viewOverlay);

        rcViewOrderItems.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rcViewPayMethods.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        txtGoBack.setOnClickListener(v -> getParentFragmentManager().popBackStack());
        btnNext.setOnClickListener(this::onClick);
    }

    private void onClick(View v) {
        if (payMethodAdapter.getSelectedPosition() == -1) {
            Toast.makeText(getContext(), "Vui lòng chọn phương thức thanh toán.", Toast.LENGTH_SHORT).show();
            return;
        }
        order.setPaymentMethodId(payMethodAdapter.getSelectedPaymentMethod().getId());
        addBillByApi();
    }

    private void addBillByApi() {
        showLoadingUI();
        paymentViewModel.getAddBill(order).observe(getViewLifecycleOwner(), response ->{
            if(response == null){
                Toast.makeText(getContext(), "Lỗi kết nối. Vui lòng thử lại.", Toast.LENGTH_SHORT).show();
            }else if( response.getStatusCode() != 201){
                String message = String.format(Locale.getDefault(),"Không thể tiếp tục đặt vé, (%s)",response.getMessage());
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getContext(), "Đặt vé thành công.", Toast.LENGTH_SHORT).show();
                order.setId(response.getData().getId());
                openAddCreditCardNumber();
            }
            hideLoadingUI();
        });
    }

    private void openAddCreditCardNumber() {
        getParentFragmentManager().setFragmentResultListener("paymentMethodResult", this, (requestKey, result) -> {
            String paymentMethodId = result.getString("paymentMethodId");
//            Toast.makeText(requireContext(), "Tạo thành công: " + paymentMethodId, Toast.LENGTH_LONG).show();
            paymentBillByApi(paymentMethodId);
        });
        PaymentMethodFragment paymentMethodFragment = new PaymentMethodFragment();
        paymentMethodFragment.show(getParentFragmentManager(), "paymentMethodFragment");
    }


    private void paymentBillByApi(String paymentMethodId) {
        showLoadingUI();
        paymentViewModel.paymentBill(order.getId(), order.getTotalCost(), paymentMethodId).observe( getViewLifecycleOwner(), response->{
                if(response == null){
                    Toast.makeText(getContext(), "Lỗi kết nối. Vui lòng thử lại.", Toast.LENGTH_SHORT).show();
                }else if( response.getStatusCode() != 200){
                    String message = String.format(Locale.getDefault(),"Không thể tiếp tục thanh toán, (%s)",response.getMessage());
                    Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(), "Thanh toán thành công.", Toast.LENGTH_SHORT).show();
                    navigateToHome();
                }
                hideLoadingUI();
            });
    }

    private void navigateToHome() {
        requireActivity().getSupportFragmentManager()
                .popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new HomeFragment())
                .commit();
        ((MainActivity) requireActivity()).showBottomNav();
    }


    private List<PaymentMethod> getExamplePayMethods() {
        List<PaymentMethod> payMethods = new ArrayList<>();
        payMethods.add(new PaymentMethod("1", "Thanh toán bằng thẻ", PaymentMethod.Method.VNPAY, "Phương thức thanh toán bằng thẻ",R.drawable.pm_vn_pay));
        payMethods.add(new PaymentMethod("2", "VNPay", PaymentMethod.Method.VNPAY, "Phương thức thanh toán VNPay",R.drawable.pm_vn_pay));
        payMethods.add(new PaymentMethod("3", "ZaloPay", PaymentMethod.Method.ZALOPAY, "Ví điện tử ZaloPay",R.drawable.pm_vn_pay));
        payMethods.add(new PaymentMethod("4", "Tài khoản ngân hàng", PaymentMethod.Method.BANKING,"Thanh toán bằng thẻ ngân hàng",R.drawable.pm_vn_pay));
        payMethods.add(new PaymentMethod("5", "Master Card", PaymentMethod.Method.MASTER_CARD,"Thanh toán bằng thẻ quốc tế",R.drawable.pm_vn_pay));
        return payMethods;
    }

    private void showLoadingUI(){
        pbLoadAddBill.setVisibility(View.VISIBLE);
        viewOverlay.setVisibility(View.VISIBLE);
    }

    private void hideLoadingUI(){
        pbLoadAddBill.setVisibility(View.GONE);
        viewOverlay.setVisibility(View.GONE);

    }
}