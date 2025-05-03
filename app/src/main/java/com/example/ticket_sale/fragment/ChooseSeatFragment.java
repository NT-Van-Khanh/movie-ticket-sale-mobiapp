package com.example.ticket_sale.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ticket_sale.R;
import com.example.ticket_sale.model.MovieFormat;
import com.example.ticket_sale.model.MovieTheater;
import com.example.ticket_sale.model.Order;
import com.example.ticket_sale.model.Seat;
import com.example.ticket_sale.model.SeatType;
import com.example.ticket_sale.model.Showtime;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ChooseSeatFragment extends Fragment {
    private TextView txtTheaterName;
    private TextView txtScreen;
    private TextView txtTimeFrame;
    private TextView txtMovieName;
    private TextView txtSeatsSelected;
    private TextView txtTotalCost;
    private Button btnNext;
    private LinearLayout lnlSeatRowName;
    private GridLayout gdlSeats;
    private TextView txtGoBack;

//    private List<List<Seat>> seats;
    private List<Seat> selectedSeats;
    private Seat[][] seats;
    private Order order;

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
        View v = inflater.inflate(R.layout.fragment_choose_seat, container, false);
        initData();
        initViews(v);
        return v;
    }

    private void initViews(View v){
        txtTheaterName = v.findViewById(R.id.txtTheaterName);
        txtScreen = v.findViewById(R.id.txtScreen);
        txtTimeFrame = v.findViewById(R.id.txtTimeFrame);
        txtMovieName = v.findViewById(R.id.txtMovieName);
        txtSeatsSelected = v.findViewById(R.id.txtSeatSelected);
        txtTotalCost = v.findViewById(R.id.txtTotalCost);

        lnlSeatRowName = v.findViewById(R.id.lnlSeatRowName);
        gdlSeats = v.findViewById(R.id.gdlSeats);
        btnNext = v.findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedSeats == null || selectedSeats.size()<1){
                    Toast.makeText(getContext(),"Vui lòng Chọn vị trí ghế ngồi!",Toast.LENGTH_LONG).show();
                    return;
                }

                ChooseFoodFragment chooseFoodFragment = new ChooseFoodFragment();
                Bundle b = new Bundle();
//                b.putString("theaterId",theater.getId());
//                b.putString("theaterName",theater.getName());
//                b.putString("theaterAddress",theater.getAddress());
//
//                b.putParcelable("movieByTheater", movieByTheater);
//                b.putParcelable("movieFormat", movieFormat);
//                b.putParcelable("movieShowtime", showtime);
                order.setSeats(selectedSeats);
                b.putParcelable("order", order);
                chooseFoodFragment.setArguments(b);
                getParentFragmentManager().beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE) //hieu ung chuyen fragment
                        .add(R.id.fragment_container, chooseFoodFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        txtGoBack = v.findViewById(R.id.txtGoBack);
        txtGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().popBackStack();
            }
        });
//        if( theater != null){
//            txtTheaterName.setText(theater.getName());
//        }
//        if(movieByTheater != null){
//            txtMovieName.setText(movieByTheater.getTitle());
//        }
//        if(movieFormat != null){
//        }
//
//        if(showtime!=null){
//            txtTimeFrame.setText( String.format("Khung giờ: %s - %s",showtime.getTimeStart(), showtime.getTimeEnd()));
//        }
        if( order.getScreen().getTheater() != null){
            txtTheaterName.setText(order.getScreen().getTheater().getName());
        }
        if(order.getMovie()!= null){
            txtMovieName.setText(order.getMovie().getTitle());
        }
        if(order.getMovieFormat() != null){
        }

        if(order.getShowtime()!=null){
            txtTimeFrame.setText( String.format("Khung giờ: %s - %s",order.getShowtime().getTimeStart(), order.getShowtime().getTimeEnd()));
        }
        setTxtSelectedSeats();

        initSeatRowName(seats.length);
        initSeats(seats);

    }

    private void setTxtSelectedSeats() {
        if(selectedSeats == null) {
            txtSeatsSelected.setText("");
            return;
        }
        String stringSeats = selectedSeats.stream()
                .map(seat -> " "+ seat.getTitle()).collect(Collectors.joining());
        txtSeatsSelected.setText(stringSeats);

    }

    private void initData(){
        selectedSeats = new ArrayList<>();
        seats = new Seat[10][14];
        for (int seatRow = 0; seatRow < seats.length; seatRow++) {
            for (int seatColumn = 0; seatColumn < seats[seatRow].length; seatColumn++) {
                seats[seatRow][seatColumn] = new Seat("C1",
                        String.format("%s%d",(char)('A'+ seatRow), seatColumn), "STANDARD",
                        "", 1, SeatType.STANDARD, 60000L);
            }
        }
        if(getArguments() == null) return;
        order = getArguments().getParcelable("order");
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

    private void initSeats(Seat[][] seats){
        for(int row = 0; row < seats.length; ++ row) {
            for(int column = 0; column < seats[row].length; ++ column) {
                Seat seat = seats[row][column];
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
                    setTxtSelectedSeats();
                });

                gdlSeats.addView(seatView);
            }
        }
    }

//    private ColorStateList getTypeOfSeat(Seat seat){
//        if(seat == null) return null;
//        SeatType seatType = seat.getSeatType();
//        if(seatType == null) return null;
//        return seatType.getTypeOfSeat(getContext());
//    }

    private int dpToPx(int dp) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

}

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