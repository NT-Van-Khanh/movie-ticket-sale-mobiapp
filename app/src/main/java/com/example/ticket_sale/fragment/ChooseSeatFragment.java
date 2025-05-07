package com.example.ticket_sale.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ticket_sale.R;
import com.example.ticket_sale.model.Movie;
import com.example.ticket_sale.model.MovieTheater;
import com.example.ticket_sale.model.Order;
import com.example.ticket_sale.model.Screen;
import com.example.ticket_sale.model.Seat;
import com.example.ticket_sale.model.SeatType;
import com.example.ticket_sale.model.Showtime;
import com.example.ticket_sale.util.AuthGuard;
import com.example.ticket_sale.util.mapper.ScreenMapper;
import com.example.ticket_sale.viewmodel.ChooseSeatViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ChooseSeatFragment extends Fragment {
    private TextView txtTheaterName;
    private TextView txtScreen;
    private TextView txtTimeFrame;
    private TextView txtMovieTitle;
    private TextView txtSeatsSelected;
    private TextView txtTotalCost;
    private Button btnNext;
    private LinearLayout lnlSeatRowName;
    private GridLayout gdlSeats;
    private TextView txtGoBack;
    private ProgressBar pbLoadSeats;
//    private List<List<Seat>> seats;
    private List<Seat> selectedSeats;
    private Order order;
    private Screen screen;
    private ChooseSeatViewModel chooseSeatViewModel;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChooseSeatFragment() {
        // Required empty public constructor
    }

    public static ChooseSeatFragment newInstance(String param1, String param2) {
        ChooseSeatFragment fragment = new ChooseSeatFragment();
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
        View root = inflater.inflate(R.layout.fragment_choose_seat, container, false);
        initViews(root);
        initData();
        setDataForViews();
        return root;
    }

    private void setDataForViews() {
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
        if(order.getMovieFormat() == null) return;
        showSelectedSeats();
//        initSeatRowName(screen.getSeats().length);
//        initSeatViews(screen.getSeats());
    }

    private void initViews(View root){
        txtTheaterName = root.findViewById(R.id.txtTheaterName);
        txtScreen = root.findViewById(R.id.txtScreen);
        txtTimeFrame = root.findViewById(R.id.txtTimeFrame);
        txtMovieTitle = root.findViewById(R.id.txtMovieName);
        txtSeatsSelected = root.findViewById(R.id.txtSeatSelected);
        txtTotalCost = root.findViewById(R.id.txtTotalCost);
        lnlSeatRowName = root.findViewById(R.id.lnlSeatRowName);
        gdlSeats = root.findViewById(R.id.gdlSeats);
        pbLoadSeats = root.findViewById(R.id.pbLoadSeats);
        selectedSeats = new ArrayList<>();
        btnNext = root.findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedSeats == null || selectedSeats.size()<1){
                    Toast.makeText(getContext(),"Vui lòng Chọn vị trí ghế ngồi!",Toast.LENGTH_LONG).show();
                    return;
                }
                if(AuthGuard.checkLogin(requireActivity())) {
                ChooseFoodFragment chooseFoodFragment = new ChooseFoodFragment();
                Bundle b = new Bundle();
                order.setSeats(selectedSeats);
                b.putParcelable("order", order);
                chooseFoodFragment.setArguments(b);
                getParentFragmentManager().beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE) //hieu ung chuyen fragment
                        .add(R.id.fragment_container, chooseFoodFragment)
                        .hide(Objects.requireNonNull(getParentFragmentManager().findFragmentById(R.id.fragment_container)))
                        .addToBackStack(null)
                        .commit();
                }
            }
        });

        txtGoBack = root.findViewById(R.id.txtGoBack);
        txtGoBack.setOnClickListener(v1 -> getParentFragmentManager().popBackStack());
        lnlSeatRowName.removeAllViews();
        gdlSeats.removeAllViews();
    }

    private void showSelectedSeats() {
        if(selectedSeats == null) {
            txtSeatsSelected.setText("");
            return;
        }
        String stringSeats = selectedSeats.stream()
                .map(seat -> " "+ seat.getTitle()).collect(Collectors.joining());
        txtSeatsSelected.setText(stringSeats);

    }

    private void initData(){
        if(getArguments() == null) return;
        order = getArguments().getParcelable("order");
        if(order == null) return;
        if(chooseSeatViewModel == null) chooseSeatViewModel  = new ChooseSeatViewModel();
        getScreenFromAPI();
//        seats = getSeats(10,14);
    }

    private void getScreenFromAPI(){
        chooseSeatViewModel.fetchScreen(order.getScreen().getId());
        chooseSeatViewModel.getScreen().observe(getViewLifecycleOwner(), response ->{
            if(response!=null && response.getStatusCode() == 200){
                screen = ScreenMapper.toScreen(response.getData());
            }else{
                screen = getExampleScreen();

            }
            initSeatRowName(screen.getSeats().length);
            initSeatViews(screen.getSeats());
            pbLoadSeats.setVisibility(View.GONE);
        });
    }

    private Screen getExampleScreen() {
        Screen s = order.getScreen();
        Seat[][] seats = getSeats(10, 14);
        s.setSeats(seats);
        return s;
    }


    private Seat[][] getSeats(int row, int column){
        Seat[][] seats = new Seat[row][column];
        for (int seatRow = 0; seatRow < seats.length; seatRow++) {
            for (int seatColumn = 0; seatColumn < seats[seatRow].length; seatColumn++) {
                seats[seatRow][seatColumn] = new Seat("C1",
                        String.format("%s%d",(char)('A'+ seatRow), seatColumn), "STANDARD",
                        "", 1, SeatType.STANDARD, 60000L);
            }
        }
        return seats;
    }
    private void initSeatRowName(int numberOfRows){
        lnlSeatRowName.removeAllViews();
        for( char rowCharacter = 'A'; rowCharacter < 'A' + numberOfRows; ++rowCharacter ){
            TextView txtRow = new TextView(getContext());
            txtRow.setText(String.valueOf(rowCharacter));
            txtRow.setLayoutParams( new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, dpToPx(24)
            ));
            txtRow.setTextAlignment( View.TEXT_ALIGNMENT_CENTER);
            lnlSeatRowName.addView(txtRow);
        }

    }

    private void initSeatViews(Seat[][] seats){
        for(int row = 0; row < seats.length; ++ row) {
            for(int column = 0; column < seats[row].length; ++ column) {
                Seat seat = seats[row][column];
                if(seat == null) continue;

                ImageView seatView = new ImageView(getContext());
                seatView.setImageResource(R.drawable.ic_chair);
                seatView.setTag(seat);

                GridLayout.LayoutParams param = new GridLayout.LayoutParams();
                param.rowSpec = GridLayout.spec(row);     // row là số nguyên
                param.columnSpec = GridLayout.spec(column); // column cũng là số nguyên
                param.width = dpToPx(24);
                param.height = dpToPx(24);

                seatView.setLayoutParams(param);
                seatView.setOnClickListener(v -> {
                    Seat clickedSeat = (Seat) v.getTag();
                    if (selectedSeats.contains(clickedSeat)) {
                        selectedSeats.remove(clickedSeat);
                        seatView.setColorFilter(seat.getSeatType().getTypeOfSeat(getContext()));
                    } else {
                        selectedSeats.add(clickedSeat);
                        seatView.setColorFilter(SeatType.SELECTED.getTypeOfSeat(getContext()));
                    }
                    showSelectedSeats();
                    showTotalCost();
                });

                gdlSeats.addView(seatView);
            }
        }
    }


    private int dpToPx(int dp) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    private void showTotalCost(){
        if(order == null) return;
        Long totalCost = 0L;
        for(Seat s : selectedSeats){
            totalCost += s.getPrice();
        }
        txtTotalCost.setText(String.valueOf(totalCost));
    }

}

//    private ColorStateList getTypeOfSeat(Seat seat){
//        if(seat == null) return null;
//        SeatType seatType = seat.getSeatType();
//        if(seatType == null) return null;
//        return seatType.getTypeOfSeat(getContext());
//    }


//    private void initSeats(List<List<Seat>> seats){
//        for(int row = 0; row < seats.size(); ++row) {
//            List<Seat> seatRow = seats.get(row);
//            for(int column = 0; column < seatRow.size(); ++column) {
//                Seat seat = seatRow.get(column);
//                ImageView seatView = new ImageView(getContext());
//                seatView.setImageResource(R.drawable.ic_chair);
//                seatView.setTag(seat);
//
//                GridLayout.LayoutParams param = new GridLayout.LayoutParams();
//                param.rowSpec = GridLayout.spec(row);     // row là số nguyên
//                param.columnSpec = GridLayout.spec(column); // column cũng là số nguyên
//                param.width = dpToPx(24);
//                param.height = dpToPx(24);
//                seatView.setLayoutParams(param);
//
//
//                seatView.setOnClickListener(v -> {
//                    Seat clickedSeat = (Seat) v.getTag();
//                    if (selectedSeats.contains(clickedSeat)) {
//                        selectedSeats.remove(clickedSeat);
//                        seatView.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(),R.color.seat_standard)));
//                    } else {
//                        selectedSeats.add(clickedSeat);
//                        seatView.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(),R.color.seat_standard))); // Đổi màu/ảnh ghế
//                    }
//                });
//
//                gdlSeats.addView(seatView);
//            }
//        }
//    }