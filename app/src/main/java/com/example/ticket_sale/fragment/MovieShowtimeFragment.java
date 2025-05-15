package com.example.ticket_sale.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ticket_sale.R;
import com.example.ticket_sale.adapter.DateAdapter;
import com.example.ticket_sale.adapter.TheaterShowtimeAdapter;
import com.example.ticket_sale.model.Movie;
import com.example.ticket_sale.model.MovieFormat;
import com.example.ticket_sale.model.Showtime;
import com.example.ticket_sale.model.Theater;
import com.example.ticket_sale.util.ViLocaleUtil;
import com.example.ticket_sale.mapper.TheaterMapper;
import com.example.ticket_sale.viewmodel.MovieShowtimeViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;


public class MovieShowtimeFragment extends Fragment {
    private TextView txtMovieTitle;
    private TextView txtMovieDuration;
    private TextView txtMovieRating;
    private TextView txtMovieAge;
    private TextView txtGoBack;
    private ImageView imgMovieImage;
    private RecyclerView rcViewDates;
    private RecyclerView rcViewTheaterShowtimes;
    private ProgressBar pbLoadShowtimes;

    private DateAdapter dateAdapter;
    private TheaterShowtimeAdapter theaterShowtimeAdapter;

    private Movie movie;
    private List<Theater> theaters;
    private List<Calendar> availableDates;

    private MovieShowtimeViewModel movieShowtimeViewModel;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MovieShowtimeFragment() {
        // Required empty public constructor
    }

    public static MovieShowtimeFragment newInstance(String param1, String param2) {
        MovieShowtimeFragment fragment = new MovieShowtimeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private static void onItemClick(int movieByTheaterPosition, int formatPosition, int showtimePosition) {
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
        View root =  inflater.inflate(R.layout.fragment_movie_showtime, container, false);
        initViews(root);
        initData();
        setDataForViews();
        return root;
    }

    private void initViews(View root) {
        txtMovieTitle = root.findViewById(R.id.txtMovieTitle);
        txtMovieDuration = root.findViewById(R.id.txtMovieDuration);
        txtMovieRating = root.findViewById(R.id.txtMovieRating);
        txtMovieAge = root.findViewById(R.id.txtMovieAge);
        txtGoBack = root.findViewById(R.id.txtGoBack);
        imgMovieImage = root.findViewById(R.id.imgMovieImage);
        rcViewDates = root.findViewById(R.id.rcViewDates);
        rcViewTheaterShowtimes = root.findViewById(R.id.rcViewTheaterShowtimes);
        pbLoadShowtimes = root.findViewById(R.id.pbLoadShowtimes);

        txtGoBack.setOnClickListener(v -> {
            getParentFragmentManager().popBackStack();
        });

        dateAdapter = new DateAdapter(new ArrayList<>(), null);
        rcViewDates.setLayoutManager( new LinearLayoutManager(getContext(),
                                        LinearLayoutManager.HORIZONTAL, false));
        rcViewDates.setAdapter(dateAdapter);

        theaters = new ArrayList<>();
        theaterShowtimeAdapter = new TheaterShowtimeAdapter(new ArrayList<>(), movie, getContext(),
                                                    MovieShowtimeFragment::onItemClick);
        rcViewTheaterShowtimes.setLayoutManager(new LinearLayoutManager(getContext(),
                            LinearLayoutManager.VERTICAL, false));
        rcViewTheaterShowtimes.setAdapter(theaterShowtimeAdapter);
    }

    private void initData(){
        if(getArguments() == null ) return;
        movie = getArguments().getParcelable("movie");
        if(movie == null) return;

        availableDates = getAvailableDates();
        getTheatersFromAPI();
    }

    private void setDataForViews(){
        if(movie == null) return;
        txtMovieTitle.setText(movie.getTitle());
        txtMovieDuration.setText(String.format(ViLocaleUtil.localeVN, "%d phút", movie.getDuration()));
        txtMovieRating.setText(String.valueOf(movie.getRating()));
        txtMovieAge.setText(String.format(ViLocaleUtil.localeVN, "T%d",movie.getAge()));
        imgMovieImage.setImageResource(movie.getImageResId());
        if(availableDates == null) return;
        dateAdapter.setDates(availableDates);

    }

    private void getTheatersFromAPI() {
        pbLoadShowtimes.setVisibility(View.VISIBLE);
        if(movieShowtimeViewModel == null) movieShowtimeViewModel = new MovieShowtimeViewModel();
        movieShowtimeViewModel.getTheaters().observe(getViewLifecycleOwner(), response ->{
            if(response != null && response.getStatusCode() == 200){
                theaters = response.getData().stream()
                                .map(TheaterMapper::toTheater)
                                .collect(Collectors.toList());
            }else{
                movie.setMovieFormats(getExampleMovieFormats());
                theaters = getExampleTheaters();
                Toast.makeText(getContext(), "Không thể lấy dữ liệu rạp hiện tại.", Toast.LENGTH_SHORT).show();
            }
            theaterShowtimeAdapter.setTheaters(theaters, movie);
            pbLoadShowtimes.setVisibility(View.GONE);
        });
    }

    private List<Calendar> getAvailableDates(){
        List<Calendar> availableDates = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < 15; i++) {
            availableDates.add((Calendar) calendar.clone());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        return availableDates;
    }

    private List<Theater> getExampleTheaters(){
        Theater mt1 = new Theater("MT1","Rạp Linh Trung, Thủ Đức","Rạp Linh Trung, Hồ Chí Minh",R.drawable.cinema1);
        Theater mt2 = new Theater("MT2","Rạp Thảo Điền, Thủ Đức","Xuân Thủy, Thảo Điền, Thủ Đức, Hồ Chí Minh",R.drawable.cinema2);
        Theater mt3 = new Theater("MT3","Rạp Bến Tre","Nguyễn Huệ, Phường 4, Bến Tre",R.drawable.cinema3);
        Theater mt4 = new Theater("MT4","Rạp Mỹ Tho","Lý Thường Kiệt, Phường 5, Mỹ Tho, Tiền Giang",R.drawable.cinema4);
        Theater mt5 = new Theater("MT5","Rạp Bến Nghé","Đinh Tiên Hoàng, Bến Nghé, Quận 1, Hồ Chí Minh",R.drawable.cinema5);
        Theater mt6 = new Theater("MT6","Rạp Trương Định","Trương Định, Phường Võ Thị Sáu, Quận 3, Hồ Chí Minh",R.drawable.cinema6);
        return Arrays.asList(mt1,mt2,mt3,mt4,mt5,mt6);
    }

    private List<MovieFormat> getExampleMovieFormats(){
        Showtime st1  = new Showtime("ST1","20:10","21:32","RV1","MV1","07/03/2025");
        Showtime st2  = new Showtime("ST2","18:10","21:32","RV1","MV1","07/03/2025");
        Showtime st3  = new Showtime("ST3","16:10","21:32","RV1","MV1","07/03/2025");
        Showtime st4  = new Showtime("ST4","19:20","21:32","RV1","MV1","07/03/2025");
        List<Showtime> showtimes1 = Arrays.asList(st1, st2, st1, st2, st1, st2);
        List<Showtime> showtimes2 = Arrays.asList(st3, st4, st1, st2, st1, st2);

        MovieFormat mf1 = new MovieFormat("VSUB","Phụ đề",showtimes1);
        MovieFormat mf2 = new MovieFormat("VSUB","Thuyết minh",showtimes1);
        MovieFormat mf3 = new MovieFormat("VSUB","Phụ đề",showtimes2);
        List<MovieFormat> movieFormats1 = List.of(mf1, mf2);
        List<MovieFormat> movieFormats2 = Arrays.asList(mf2, mf3);
        return movieFormats1;
    }
}
