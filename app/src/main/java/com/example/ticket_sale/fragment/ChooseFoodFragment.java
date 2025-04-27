package com.example.ticket_sale.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ticket_sale.R;
import com.example.ticket_sale.model.MovieByTheater;
import com.example.ticket_sale.model.MovieFormat;
import com.example.ticket_sale.model.MovieTheater;
import com.example.ticket_sale.model.Seat;
import com.example.ticket_sale.model.Showtime;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChooseFoodFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChooseFoodFragment extends Fragment {
    private TextView txtTheaterName;
    private TextView txtScreen;
    private TextView txtTimeFrame;
    private TextView txtMovieName;
    private TextView txtSeatsSelected;
    private TextView txtTotalCost;
    private Button btnNext;
    private TextView txtFoodsSelected;
    private TextView txtGoBack;

    private List<Seat> selectedSeats;
    private MovieTheater theater;
    private MovieByTheater movieByTheater;
    private MovieFormat movieFormat;
    private Showtime showtime;

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

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChooseFoodFragment.
     */
    // TODO: Rename and change types and number of parameters
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
        return v;
    }

    private void initViews(View v){
        txtFoodsSelected = v.findViewById(R.id.txtFoodSelected);
        txtSeatsSelected = v.findViewById(R.id.txtSeatSelected);
        txtScreen = v.findViewById(R.id.txtScreen);
        txtTheaterName = v.findViewById(R.id.txtTheaterName);
        txtMovieName = v.findViewById(R.id.txtMovieName);
        txtTimeFrame = v.findViewById(R.id.txtTimeFrame);
        txtTotalCost = v.findViewById(R.id.txtTotalCost);
        btnNext = v.findViewById(R.id.btnNext);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PaymentFragment paymentFragment = new PaymentFragment();
                Bundle b = new Bundle();
                paymentFragment.setArguments(b);
                getParentFragmentManager().beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .replace(R.id.fragment_container, paymentFragment)
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
    }

    private void initData(){
        if(getArguments() == null) return;
        theater = new MovieTheater();
        theater.setId(getArguments().getString("theaterId"));
        theater.setName(getArguments().getString("theaterName"));
        theater.setAddress(getArguments().getString("theaterAddress"));

        movieFormat = getArguments().getParcelable("movieFormat");
        movieByTheater = getArguments().getParcelable("movieByTheater");
        showtime = getArguments().getParcelable("movieShowtime");
    }
}