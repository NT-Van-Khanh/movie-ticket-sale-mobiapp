package com.example.ticket_sale.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ticket_sale.R;
import com.example.ticket_sale.adapter.FoodAdapter;
import com.example.ticket_sale.adapter.FoodComboAdapter;
import com.example.ticket_sale.model.Food;
import com.example.ticket_sale.model.FoodCombo;
import com.example.ticket_sale.model.Item;
import com.example.ticket_sale.model.Movie;
import com.example.ticket_sale.model.MovieTheater;
import com.example.ticket_sale.model.Order;
import com.example.ticket_sale.model.Screen;
import com.example.ticket_sale.model.Seat;
import com.example.ticket_sale.model.Showtime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ChooseFoodFragment extends Fragment {
    private TextView txtTheaterName;
    private TextView txtScreen;
    private TextView txtTimeFrame;
    private TextView txtMovieTitle;
    private TextView txtSeatsSelected;
    private TextView txtTotalCost;
    private Button btnNext;
    private TextView txtFoodsSelected;
    private TextView txtGoBack;

    private RecyclerView rcViewFoods;
    private RecyclerView rcViewCombos;
    private RecyclerView rcViewDrinks;

    private List<Food> foods;
    private List<FoodCombo> combos;
    private Map<Item,Integer> selectedFoods = new HashMap<>();
    private Order order;

    private FoodAdapter foodAdapter;
    private FoodComboAdapter foodComboAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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
        initData();
        initViews(v);
        setDataForViews(v);
        return v;
    }

    private void setDataForViews(View root) {
        Screen screen = order.getScreen();
        if(screen == null) return;
        txtScreen.setText(screen.getName());

        MovieTheater theater = screen.getTheater();
        if(theater == null) return;
        txtTheaterName.setText(theater.getName());

        Showtime showtime = order.getShowtime();
        if(showtime == null) return;
        txtTimeFrame.setText( String.format("Khung giờ: %s - %s",showtime.getTimeStart(), showtime.getTimeEnd()));

        Movie movie = order.getMovie();
        if(movie == null) return;
        txtMovieTitle.setText(movie.getTitle());
        List<Seat> seats = order.getSeats();
        String stringSeats = seats.stream()
                .map(seat -> ( " " + seat.getTitle())) .collect(Collectors.joining());
        txtSeatsSelected.setText(stringSeats);
    }

    private void initViews(View root){
        txtFoodsSelected = root.findViewById(R.id.txtFoodSelected);
        txtSeatsSelected = root.findViewById(R.id.txtSeatSelected);
        txtScreen = root.findViewById(R.id.txtScreen);
        txtTheaterName = root.findViewById(R.id.txtTheaterName);
        txtMovieTitle = root.findViewById(R.id.txtMovieName);
        txtTimeFrame = root.findViewById(R.id.txtTimeFrame);
        txtTotalCost = root.findViewById(R.id.txtTotalCost);

        btnNext = root.findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PaymentFragment paymentFragment = new PaymentFragment();
                Bundle b = new Bundle();
                order.setFoods(selectedFoods);
                b.putParcelable("order",order);
                paymentFragment.setArguments(b);

                getParentFragmentManager().beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .add(R.id.fragment_container, paymentFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        txtGoBack = root.findViewById(R.id.txtGoBack);
        txtGoBack.setOnClickListener(v -> getParentFragmentManager().popBackStack());
        foodAdapter = new FoodAdapter(foods,50, this::onQuantityChanged);

        rcViewFoods = root.findViewById(R.id.rcViewPopcorn);
        rcViewFoods.setLayoutManager(new GridLayoutManager(getContext(),2));
        rcViewFoods.setAdapter(foodAdapter);

        foodComboAdapter = new FoodComboAdapter(combos, 50,this::onQuantityChanged);
        rcViewCombos = root.findViewById(R.id.rcViewCombo);
        rcViewCombos.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
        rcViewCombos.setAdapter(foodComboAdapter);

    }

    private void initData(){
        foods = new ArrayList<>();
        foods.add(new Food("FD1","Combo 1","", R.drawable.fd_cb_popcorn1,52000L ));
        foods.add(new Food("FD2","Combo 2","", R.drawable.fd_cb2,82000L ));
        foods.add(new Food("FD3","Lay's","", R.drawable.fd_lays1,32000L));
        foods.add(new Food("FD4","Lay's truyền thống","", R.drawable.fd_lays2,32000L));
        foods.add(new Food("FD5","Nước suối","", R.drawable.fd_water,18000L ));

        combos = new ArrayList<>();
        combos.add(new FoodCombo("FC1","Combo 1","- 1 Bắp\n- 1 Nước tự chọn","",R.drawable.fd_cb3,62000L));
        combos.add(new FoodCombo("FC2","Combo 2","- 2 Bắp\n- 2 Nước tự chọn","",R.drawable.fd_cb2,132000L));
        combos.add(new FoodCombo("FC3","Combo 3","- 1 Bắp\n- 2 Nước tự chọn","",R.drawable.fd_cb_popcorn1,92000L));
        combos.add(new FoodCombo("FC4","Combo 4","- 1Bắp\n- 2 Nước tự chọn\n - 1 Mực chiên giòn","",R.drawable.fd_cb_popcorn,132000L));

        if(getArguments() == null) return;
        order = getArguments().getParcelable("order");
//        theater = new MovieTheater();
//        theater.setId(getArguments().getString("theaterId"));
//        theater.setName(getArguments().getString("theaterName"));
//        theater.setAddress(getArguments().getString("theaterAddress"));
//
//        movieFormat = getArguments().getParcelable("movieFormat");
//        movie = getArguments().getParcelable("movieByTheater");
//        showtime = getArguments().getParcelable("movieShowtime");
    }

    private void onQuantityChanged(Item item, int quantity) {
        if (quantity > 0) {
            selectedFoods.put(item, quantity);
        } else {
            selectedFoods.remove(item);
        }
        selectedFoods.forEach((fd, integer)
                -> Log.e("selectedFoods", fd.getTitle() + "-" + integer +"\n"));
    }
}