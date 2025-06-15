package com.example.ticket_sale.fragment;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ticket_sale.R;
import com.example.ticket_sale.adapter.FoodAdapter;
import com.example.ticket_sale.adapter.FoodComboAdapter;
import com.example.ticket_sale.model.Food;
import com.example.ticket_sale.model.FoodType;
import com.example.ticket_sale.model.Item;
import com.example.ticket_sale.model.Movie;
import com.example.ticket_sale.model.SeatPosition;
import com.example.ticket_sale.model.SharedBookingViewModel;
import com.example.ticket_sale.model.Theater;
import com.example.ticket_sale.model.Order;
import com.example.ticket_sale.model.Screen;
import com.example.ticket_sale.model.Seat;
import com.example.ticket_sale.model.Showtime;
import com.example.ticket_sale.util.AuthGuard;
import com.example.ticket_sale.mapper.FoodMapper;
import com.example.ticket_sale.util.ViLocaleUtil;
import com.example.ticket_sale.viewmodel.ChooseFoodViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class ChooseFoodFragment extends Fragment {
    private final int COUNTDOWN_MINUTES = 7;
    private TextView txtGoBack;
    private TextView txtBookingCountdown;
    private TextView txtTheaterName;
    private TextView txtScreen;
    private TextView txtTimeFrame;
    private TextView txtMovieTitle;
    private TextView txtSeatsSelected;
    private TextView txtTotalCost;
    private Button btnNext;
    private TextView txtFoodsSelected;
    private ProgressBar pbLoadFoods;
    private ProgressBar pbLoadCombos;
    private View viewOverlay;

    private RecyclerView rcViewFoods;
    private RecyclerView rcViewCombos;
    private RecyclerView rcViewDrinks;

    private List<Food> foods;
    private List<Food> combos;
    private List<FoodType> foodTypes;
    private Map<Food,Integer> selectedFoods = new HashMap<>();
    private Order order;

    private FoodAdapter foodAdapter;
    private FoodComboAdapter foodComboAdapter;

    private int countdownTime;
    private final Handler handler = new Handler(Looper.getMainLooper());

    private final int MAX_QUANTITY = 20;
    private int availableQuantity = 10;

    private ChooseFoodViewModel chooseFoodViewModel;
    private SharedBookingViewModel sharedBookingViewModel;

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

    private final Runnable countdownRunnable = new Runnable(){
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
                    getParentFragmentManager().popBackStack();
                })
                .create();

        dialog.setOnCancelListener(d -> {
            getParentFragmentManager().popBackStack();
        });

        dialog.show();
    }

    public ChooseFoodFragment() {
        // Required empty public constructor
    }

    public static ChooseFoodFragment newInstance(String param1, String param2) {
        ChooseFoodFragment fragment = new ChooseFoodFragment();
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
        View v = inflater.inflate(R.layout.fragment_choose_food, container, false);
        initViews(v);
        initData();
        setDataForViews();
        return v;
    }
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            handler.removeCallbacks(countdownRunnable);
        } else {
            handler.post(countdownRunnable);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("onPause","onPause");
        handler.removeCallbacks(countdownRunnable);
    }

    private void setDataForViews() {
        Screen screen = order.getScreen();
        if(screen == null) return;
        txtScreen.setText(screen.getName());

        Theater theater = screen.getTheater();
        if(theater == null) return;
        txtTheaterName.setText(theater.getName());

        Showtime showtime = order.getShowtime();
        if(showtime == null) return;
        txtTimeFrame.setText( String.format("Khung giờ: %s - %s",showtime.getTimeStart(), showtime.getTimeEnd()));

        Movie movie = order.getMovie();
        if(movie == null) return;
        txtMovieTitle.setText(movie.getTitle());
        txtSeatsSelected.setText(order.getSelectedSeatsString());
        showTotalCost();
    }

    private void showTotalCost(){
        if(order == null) return;
        Long totalCost =order.getTotalCostOfSeats();
        StringBuilder stringSelectedFoods = new StringBuilder();

        for (Map.Entry<Food, Integer> foodEntry : selectedFoods.entrySet()) {

            totalCost += foodEntry.getKey().getPrice() * foodEntry.getValue();

            if (stringSelectedFoods.length() > 0)  stringSelectedFoods.append(", ");
            stringSelectedFoods.append(foodEntry.getKey().getTitle());
        }
        txtFoodsSelected.setText(stringSelectedFoods.toString());
        txtTotalCost.setText(ViLocaleUtil.formatLocalCurrency(totalCost));
    }

    private void initViews(View root){
        txtBookingCountdown = root.findViewById(R.id.txtBookingCountdown);
        txtFoodsSelected = root.findViewById(R.id.txtFoodSelected);
        txtSeatsSelected = root.findViewById(R.id.txtSeatSelected);
        txtScreen = root.findViewById(R.id.txtScreen);
        txtTheaterName = root.findViewById(R.id.txtTheaterName);
        txtMovieTitle = root.findViewById(R.id.txtMovieName);
        txtTimeFrame = root.findViewById(R.id.txtTimeFrame);
        txtTotalCost = root.findViewById(R.id.txtTotalCost);
        pbLoadFoods = root.findViewById(R.id.pbLoadFoods);
        pbLoadCombos = root.findViewById(R.id.pbLoadCombos);
        viewOverlay = root.findViewById(R.id.viewOverlay);

        btnNext = root.findViewById(R.id.btnNext);
        btnNext.setOnClickListener(v -> {
            if(AuthGuard.checkLogin(requireActivity())) {
                navigateToPayment();
            }
        });

        txtGoBack = root.findViewById(R.id.txtGoBack);
        txtGoBack.setOnClickListener(v -> {
            getParentFragmentManager().popBackStack();
        });

        foods = new ArrayList<>();
        foodAdapter = new FoodAdapter(foods, new FoodAdapter.OnQuantityChangeListener() {
            @Override
            public int getAvailableQuantity() {
                return availableQuantity;
            }

            @Override
            public void onQuantityChanged(Food food, int quantity) {
                handleQuantityChanged(food, quantity);
                availableQuantity = availableFoodsQuantity(MAX_QUANTITY);
            }
        });

        rcViewFoods = root.findViewById(R.id.rcViewPopcorn);
        rcViewFoods.setLayoutManager(new GridLayoutManager(getContext(),2));
        rcViewFoods.setAdapter(foodAdapter);

        combos = new ArrayList<>();
        foodComboAdapter = new FoodComboAdapter(combos, new FoodComboAdapter.OnQuantityChangeListener() {
            @Override
            public int getAvailableQuantity() {
                return availableQuantity;
            }

            @Override
            public void onQuantityChanged(Food food, int quantity) {
                handleQuantityChanged(food, quantity);
                availableQuantity = availableFoodsQuantity(MAX_QUANTITY);
            }
        });
        rcViewCombos = root.findViewById(R.id.rcViewCombo);
        rcViewCombos.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
        rcViewCombos.setAdapter(foodComboAdapter);

    }

    private void initData(){
        if(getArguments() == null) getParentFragmentManager().popBackStack();
        viewOverlay.setVisibility(View.VISIBLE);
        order = getArguments().getParcelable("order");
        if(order == null) getParentFragmentManager().popBackStack();

        chooseFoodViewModel = new ChooseFoodViewModel();
        getFoodsFromAPI();

        sharedBookingViewModel = new ViewModelProvider(requireActivity()).get(SharedBookingViewModel.class);
        sharedBookingViewModel.setCountdownTime(COUNTDOWN_MINUTES * 60);
//        foods = getExampleFoods();
//        combos = getExampleFoodCombos();
//        theater = new TheaterDTO();
//        theater.setId(getArguments().getString("theaterId"));
//        movieFormat = getArguments().getParcelable("movieFormat");
//        movie = getArguments().getParcelable("movieByTheater");
//        showtime = getArguments().getParcelable("movieShowtime");
    }

    private void navigateToPayment() {
        PaymentFragment paymentFragment = new PaymentFragment();
        Bundle b = new Bundle();
        order.setFoods(selectedFoods);
        b.putParcelable("order", order);
        paymentFragment.setArguments(b);

        getParentFragmentManager().beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .add(R.id.fragment_container, paymentFragment)
                .hide(this)
                .addToBackStack(null)
                .commit();
    }

    private void getFoodsFromAPI(){

        chooseFoodViewModel.getFoods().observe(getViewLifecycleOwner(), response ->{
            if(response != null && response.getStatusCode() == 200){
                foodTypes = response.getData()
                        .stream()
                        .map(FoodMapper::toFoodType)
                        .collect(Collectors.toList());
            }else{
                foodTypes =  getExampleFoodTypes();
            }
            for(FoodType ft : foodTypes){
                if("COMBO".equals(ft.getName())){
                    combos.addAll(ft.getFoods());
                    foodComboAdapter.setFoodCombos(combos);
                }else{
                    foods.addAll(ft.getFoods());
                    foodAdapter.setFoods(foods);
                }
            }

            if(combos == null ||combos.isEmpty()){
                rcViewCombos.setVisibility(View.GONE);
            }

            viewOverlay.setVisibility(View.GONE);
            pbLoadFoods.setVisibility(View.GONE);
            pbLoadCombos.setVisibility(View.GONE);
            startCountdown();
        });
    }

    private List<FoodType> getExampleFoodTypes(){
        List<FoodType> fts = new ArrayList<>();
        List<Food> foodItems = new ArrayList<>(getExampleFoods());
        fts.add(new FoodType("FT1", "FOOD", foodItems));
        List<Food> foodComboItems = getExampleFoodCombos();
        fts.add(new FoodType("FT2", "COMBO", foodComboItems));
        return fts;
    }

    private List<Food> getExampleFoods(){
       List<Food> foods = new ArrayList<>();
//        foods.add(new Food("FD1","Combo 1","", R.drawable.fd_cb_popcorn1,52000L ));
//        foods.add(new Food("FD2","Combo 2","", R.drawable.fd_cb2,82000L ));
//        foods.add(new Food("FD3","Lay's","", R.drawable.fd_lays1,32000L));
//        foods.add(new Food("FD4","Lay's truyền thống","", R.drawable.fd_lays2,32000L));
//        foods.add(new Food("FD5","Nước suối","", R.drawable.fd_water,18000L ));
        foods.add(new Food("FD1","Combo 1",null, 52000L, new FoodType("POPCORN", "BẮP NƯỚC",null)));
        foods.add(new Food("FD2","Combo 2",null, 82000L,new FoodType("POPCORN", "BẮP NƯỚC",null)));
        foods.add(new Food("FD3","Lay's",null, 32000L, new FoodType("POPCORN", "BẮP NƯỚC",null)));
        foods.add(new Food("FD4","Lay's truyền thống",null, 32000L,  new FoodType("POPCORN", "BẮP NƯỚC",null)));
        foods.add(new Food("FD5","Nước suối",null, 18000L,  new FoodType("POPCORN", "BẮP NƯỚC",null)));
        return foods;
    }

    private List<Food> getExampleFoodCombos(){
        List<Food> combos =  new ArrayList<>();
        combos.add(new Food("FC1","Combo 1",null, 92000L,"- 1 Bắp\n- 1 Nước tự chọn", new FoodType("COMBO", "COMBO",null)));
        combos.add(new Food("FC1","Combo 2",null, 132000L,"- 2 Bắp\n- 2 Nước tự chọn", new FoodType("COMBO", "COMBO",null)));
        combos.add(new Food("FC1","Combo 3",null, 92000L,"- 1 Bắp\n- 2 Nước tự chọn", new FoodType("COMBO", "COMBO",null)));
        combos.add(new Food("FC1","Combo 4",null, 132000L,"- 1Bắp\n- 2 Nước tự chọn\n - 1 Mực chiên giòn", new FoodType("COMBO", "COMBO",null)));
        return combos;
    }


    private void handleQuantityChanged(Food food, int quantity) {
        if (quantity > 0) {
            selectedFoods.put(food, quantity);
        } else {
            selectedFoods.remove(food);
        }
        showTotalCost();
        availableQuantity = availableFoodsQuantity(MAX_QUANTITY);
        selectedFoods.forEach((fd, integer)
                -> Log.e("selectedFoods", fd.getTitle() + "-" + integer +"\n"));
    }

    private int availableFoodsQuantity(int maxQuantity){
        int totalQuantity = 0;
        for(int quantity : selectedFoods.values()){
            totalQuantity += quantity;
        }
        return maxQuantity - totalQuantity;
    }
}