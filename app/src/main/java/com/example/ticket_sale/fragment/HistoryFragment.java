package com.example.ticket_sale.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ticket_sale.R;
import com.example.ticket_sale.adapter.BookingHistoryAdapter;
import com.example.ticket_sale.mapper.OrderMapper;
import com.example.ticket_sale.model.Movie;
import com.example.ticket_sale.model.Order;
import com.example.ticket_sale.model.Screen;
import com.example.ticket_sale.model.SeatPosition;
import com.example.ticket_sale.model.Showtime;
import com.example.ticket_sale.model.Theater;
import com.example.ticket_sale.model.User;
import com.example.ticket_sale.viewmodel.HistoryViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;


public class HistoryFragment extends Fragment {
    private TextView txtGoBack;

    private RecyclerView rcViewAvailableBooking;
    private RecyclerView rcViewBookingHistory;

    private LinearLayout lnlAvailableBooking;
    private LinearLayout lnlBookingHistory;

    private BookingHistoryAdapter bookingHistoryAdapter;
    private BookingHistoryAdapter availableBookingAdapter;
    private List<Order> orders;
    private List<Order> availableBooking;
    private List<Order> bookingHistory;


    private ProgressBar pbLoadBookingHistory;
    private View viewOverlay;

    private HistoryViewModel historyViewModel;
    
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HistoryFragment() {
        // Required empty public constructor
    }

    public static HistoryFragment newInstance(String param1, String param2) {
        HistoryFragment fragment = new HistoryFragment();
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
        View root = inflater.inflate(R.layout.fragment_history, container, false);
        initView(root);
        initData();
        setDataForViews();
        return root;
    }

    private void initData() {
        historyViewModel = new HistoryViewModel();
        availableBooking  = new ArrayList<>();
        bookingHistory  = new ArrayList<>();
        getBillsFromAPI();
    }

    private void getBillsFromAPI() {
        showLoadingUI();
         historyViewModel.getBillsByCustomer().observe(getViewLifecycleOwner(), response -> {
             if(response == null){
                 Toast.makeText(getContext(), "Lỗi kết nối. Vui lòng thử lại.", Toast.LENGTH_SHORT).show();
                 orders = getExampleBookingHistory();
             }else if( response.getStatusCode() != 200){
                 String message = String.format(Locale.getDefault(),"Không thể lấy danh sách vé, (%s)",response.getMessage());
                 orders = getExampleBookingHistory();
                 Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
             }else{
                orders = response.getData().stream()
                        .filter(order -> order.getTransactionCode() != null && !order.getTransactionCode().isEmpty())
                        .map(OrderMapper::toOrder)
                        .collect(Collectors.toList());
//                 Map<Boolean, List<Order>> partitionedOrders = response.getData().stream()
//                         .filter(order -> order.getTransactionCode() != null && !order.getTransactionCode().isEmpty())
//                         .map(OrderMapper::toOrder)
//                         .collect(Collectors.partitioningBy(order -> {
//                             Showtime showtime = order.getShowtime();
//                             try {
//                                 Date endDate = simpleDateFormat.parse(showtime.getDate() + " " + showtime.getTimeEnd());
//                                 return endDate != null && endDate.after(new Date());
//                             } catch (ParseException e) {
//                                 return false;
//                             }
//                         }));
             }
             Map<Boolean, List<Order>> partitionedOrders = partitionOrders(orders);
             availableBooking = partitionedOrders.get(true);
             bookingHistory = partitionedOrders.get(false);
             showAvailableBooking(!availableBooking.isEmpty());
             showBookingHistory(!bookingHistory.isEmpty());

             bookingHistoryAdapter.setBookingHistories(bookingHistory);
             availableBookingAdapter.setBookingHistories(availableBooking);
             hideLoadingUI();

        });
    }

    private void setDataForViews() {
        bookingHistoryAdapter = new BookingHistoryAdapter(bookingHistory, this::navigationToDetailBooking);
        availableBookingAdapter = new BookingHistoryAdapter(availableBooking, this::navigationToDetailBooking);
        rcViewAvailableBooking.setAdapter(availableBookingAdapter);
        rcViewBookingHistory.setAdapter(bookingHistoryAdapter);
    }

    private List<Order> getExampleBookingHistory() {
        List<Order> orders = new ArrayList<>();
        Movie movie =new Movie("ID",R.drawable.mv_elio,"Elio", 120, 13, 8F,"");
        Showtime showtime = new Showtime(2,"12:00","13:00","2","ID","2025-06-12");
        Theater theater = new Theater("id","Rạp Quang Trung","Quang Trung, Thủ Đức",R.drawable.cinema2);
        Screen screen = new Screen("ID","Phòng 2",theater);
        List<SeatPosition> seats = new ArrayList<>();
        seats.add(new SeatPosition(1, 2));
        User user = new User("Nguyen Van A","0333666999","nvana@gmail.com","nguyenvana");
        orders.add(new Order("OD001",movie, showtime, screen, seats, user));
        return orders;
    }

    private void initView(View root) {
        txtGoBack = root.findViewById(R.id.txtGoBack);
        pbLoadBookingHistory = root.findViewById(R.id.pbLoadBookingHistory);
        viewOverlay = root.findViewById(R.id.viewOverlay);
        rcViewAvailableBooking = root.findViewById(R.id.rcViewAvailableBooking);
        rcViewBookingHistory = root.findViewById(R.id.rcViewBookingHistory);

        lnlAvailableBooking = root.findViewById(R.id.lnlAvailableBooking);
        lnlBookingHistory = root.findViewById(R.id.lnlBookingHistory);

        rcViewBookingHistory.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false));
        rcViewAvailableBooking.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false));

        txtGoBack.setOnClickListener( v ->
                requireActivity().getSupportFragmentManager().popBackStack());
    }
    private void navigationToDetailBooking(int position) {
        Order order = orders.get(position);
        Bundle bundle = new Bundle();
        bundle.putParcelable("order",order);

        HistoryDetailFragment   historyDetailFragment = new HistoryDetailFragment();
        historyDetailFragment.setArguments(bundle);
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container,historyDetailFragment)
                .hide(this)
                .addToBackStack(null)
                .commit();
    }

    private void showAvailableBooking(boolean isShowed){
        if(isShowed){
            lnlAvailableBooking.setVisibility(View.VISIBLE);
        }else{
            lnlAvailableBooking.setVisibility(View.GONE);
        }

    }
    private void showBookingHistory(boolean isShowed){
        if(isShowed){
            lnlBookingHistory.setVisibility(View.VISIBLE);
        }else{
            lnlBookingHistory.setVisibility(View.GONE);
        }

    }

    private void showLoadingUI(){
        pbLoadBookingHistory.setVisibility(View.VISIBLE);
        viewOverlay.setVisibility(View.VISIBLE);
    }

    private void hideLoadingUI(){
        pbLoadBookingHistory.setVisibility(View.GONE);
        viewOverlay.setVisibility(View.GONE);
    }

    private Map<Boolean, List<Order>> partitionOrders(List<Order> orders) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault());
        return orders.stream()
                .collect(Collectors.partitioningBy(order -> {
                    Showtime showtime = order.getShowtime();
                    try {
                        Date endDate = simpleDateFormat.parse(showtime.getDate() + " " + showtime.getTimeEnd());
                        return endDate != null && endDate.after(new Date());
                    } catch (ParseException e) {
                        Log.e("endDate",String.valueOf(e));
                        return false;
                    }
                }));
    }
}