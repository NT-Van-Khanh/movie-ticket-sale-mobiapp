package com.example.ticket_sale.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ticket_sale.R;
import com.example.ticket_sale.adapter.DateAdapter;
import com.example.ticket_sale.adapter.MovieAdapter;
import com.example.ticket_sale.adapter.MovieByTheaterAdapter;
import com.example.ticket_sale.adapter.ShowtimeAdapter;
import com.example.ticket_sale.model.Movie;
import com.example.ticket_sale.model.MovieFormat;
import com.example.ticket_sale.model.MovieTheater;
import com.example.ticket_sale.model.Order;
import com.example.ticket_sale.model.Screen;
import com.example.ticket_sale.model.Showtime;
import com.example.ticket_sale.model.User;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MovieShowtimeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieShowtimeFragment extends Fragment {
    private RecyclerView rcViewDates;
    private RecyclerView rcViewMoviesByTheater;

    private DateAdapter dateAdapter;
    private MovieByTheaterAdapter movieByTheaterAdapter;

    private List<Calendar> availableDates;
    private List<Movie> movies;
    private MovieTheater theater;

    private TextView txtTheaterName;
    private TextView txtTheaterAddress;
    private TextView txtGoBack;

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
     * @return A new instance of fragment MovieAndShowTimeFragment.
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
        View root = inflater.inflate(R.layout.fragment_movie_showtime, container, false);
        initData();
        initView(root);
        return root;
    }

    private void initData() {
        availableDates = getAvailableDates();
        dateAdapter = new DateAdapter(availableDates, null);
        movies = getMovies();
        movieByTheaterAdapter = new MovieByTheaterAdapter(movies, this::onItemClick);

        if(getArguments()== null) return;
//            movieTheater = new MovieTheater();
//            movieTheater.setId( getArguments().getString("theaterId"));
//            movieTheater.setName(getArguments().getString("theaterName"));
//            movieTheater.setAddress(getArguments().getString("theaterAddress"));
        theater = getArguments().getParcelable("theater");
//            Log.d("theater infor:", movieTheater.getId() +" "+movieTheater.getName() + " " +movieTheater.getAddress());

    }

    private void initView(View root){
        txtTheaterName = root.findViewById(R.id.txtMovieTheaterName);
        txtTheaterAddress = root.findViewById(R.id.txtMovieTheaterAddress);
        txtGoBack = root.findViewById(R.id.txtGoBack);
        txtGoBack.setOnClickListener(v -> getParentFragmentManager().popBackStack());

        rcViewDates = root.findViewById(R.id.rcViewDates);
        rcViewMoviesByTheater = root.findViewById(R.id.rcViewMoviesByTheater);

        rcViewDates.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
        rcViewDates.setAdapter(dateAdapter);

        rcViewMoviesByTheater.setLayoutManager(new GridLayoutManager(getContext(),1));
        rcViewMoviesByTheater.setAdapter(movieByTheaterAdapter);

        if(theater!=null){
            txtTheaterName.setText(theater.getName());
            txtTheaterAddress.setText(theater.getAddress());
        }
    }

    private List<Movie> getMovies() {

        Showtime st1  = new Showtime("ST1","20:10","21:32","RV1","MV1","07/03/2025");
        Showtime st2  = new Showtime("ST2","18:10","21:32","RV1","MV1","07/03/2025");
        Showtime st3  = new Showtime("ST3","16:10","21:32","RV1","MV1","07/03/2025");
        Showtime st4  = new Showtime("ST4","19:20","21:32","RV1","MV1","07/03/2025");
        List<Showtime> showtimes1 = Arrays.asList(st1, st2, st1, st2, st1, st2);
        List<Showtime> showtimes2 = Arrays.asList(st3, st4, st1, st2, st1, st2);

        MovieFormat mf1 = new MovieFormat("VSUB","Phụ đề",showtimes1);
        MovieFormat mf2 = new MovieFormat("VSUB","Thuyết minh",showtimes1);
        MovieFormat mf3 = new MovieFormat("VSUB","Phụ đề",showtimes2);
        List<MovieFormat> movieFormats1 = Arrays.asList(mf1, mf2);
        List<MovieFormat> movieFormats2 = Arrays.asList(mf2, mf3);

        Movie mbt1 = new Movie("MV1","Bí kíp luyện rồng",123,13, 8.6F,R.drawable.mv_bi_kip_luyen_rong,movieFormats1,null);
        Movie mbt2 = new Movie("MV2","The bad guys 2",113,13, 8.6F,R.drawable.mv_the_bad_guys_2,movieFormats2,null);
        List<Movie> movies= Arrays.asList(mbt2,mbt1);
        Log.e("moviesByTheater", String.valueOf(movies.size()));
//        Log.e("moviesByTheater.get(0).getMovieFormats().size", String.valueOf(moviesByTheater.get(0).getMovieFormats().get(0).getShowtimes().size()));
//        Log.e("moviesByTheater.get(1).getMovieFormats().size", String.valueOf(moviesByTheater.get(1).getMovieFormats().get(0).getShowtimes().size()));
        return movies;
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

    private void onItemClick(int movieIndex, int formatIndex, int showtimeIndex) {
        if (theater == null) return;
        Movie movie = movies.get(movieIndex);
        MovieFormat movieFormat = movie.getMovieFormats().get(formatIndex);
        Showtime showtime = movieFormat.getShowtimes().get(showtimeIndex);
        if(movieFormat == null || showtime == null) return;
        ChooseSeatFragment chooseSeatFragment = new ChooseSeatFragment();
        Bundle bundle = new Bundle();
        Order order = new Order();
        order.setUser(new User());
        order.setScreen(new Screen("SC2","Phòng 2", theater));
        order.setMovie(movie);
        order.setMovieFormat(movieFormat);
        order.setShowtime(showtime);
        bundle.putParcelable("order", order);
////        bundle.putString("theaterId",movieTheater.getId());
////        bundle.putString("theaterName",movieTheater.getName());
////        bundle.putString("theaterAddress",movieTheater.getAddress());
////
////        bundle.putParcelable("movieByTheater", movie);
////        bundle.putParcelable("movieFormat", movieFormat);
////        bundle.putParcelable("movieShowtime",showtime);
//
        chooseSeatFragment.setArguments(bundle);
        requireActivity().getSupportFragmentManager() .beginTransaction()
                .replace(R.id.fragment_container,chooseSeatFragment)
                .addToBackStack(null)
                .commit();

    }

}


////    private List<String> getAvailableDates(){
////
////        List<String> availableDates = new ArrayList<>();
////        Calendar calendar = Calendar.getInstance();
////
////        for(int day = 0; day < 14; ++day){
////            availableDates.add(calendar.getTime().toString());
////            calendar.add(Calendar.DAY_OF_MONTH,1);
////            Log.e("getAvailableDates",calendar.getTime().toString());
////        }
////        return availableDates;
////    }
//
//
//private void updateMovies(Calendar date) {
////        List<Movie> movies = moviesByDate.get(date);
////        movieAdapter = new MovieAdapter(movies);
////        rcViewMovieShowtime.setAdapter(movieAdapter);
//}