package com.example.ticket_sale.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ticket_sale.R;
import com.example.ticket_sale.adapter.DateAdapter;
import com.example.ticket_sale.model.Theater;

import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MovieShowtimeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieShowtimeFragment extends Fragment {
    private TextView txtMovieTitle;
    private TextView txtMovieDuration;
    private TextView txtMovieRating;
    private TextView txtMovieAge;
    private TextView txtGoBack;
    private RecyclerView rcViewDates;
    private RecyclerView rcViewTheatersByMovie;
    private ProgressBar pbLoadShowtimes;

    private DateAdapter dateAdapter;
    private List<Theater> theaters;
    private List<Calendar> availableDates;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MovieShowtimeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MovieShowtimeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MovieShowtimeFragment newInstance(String param1, String param2) {
        MovieShowtimeFragment fragment = new MovieShowtimeFragment();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_showtime, container, false);
    }
}