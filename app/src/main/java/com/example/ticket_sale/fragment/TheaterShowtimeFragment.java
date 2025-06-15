package com.example.ticket_sale.fragment;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ticket_sale.R;
import com.example.ticket_sale.activity.MainActivity;
import com.example.ticket_sale.adapter.DateAdapter;
import com.example.ticket_sale.adapter.MovieByTheaterAdapter;
import com.example.ticket_sale.mapper.ScreenMapper;
import com.example.ticket_sale.mapper.TicketMapper;
import com.example.ticket_sale.model.Movie;
import com.example.ticket_sale.model.MovieFormat;
import com.example.ticket_sale.model.Screen;
import com.example.ticket_sale.model.ShowtimeKey;
import com.example.ticket_sale.model.Theater;
import com.example.ticket_sale.model.Order;
import com.example.ticket_sale.model.Showtime;
import com.example.ticket_sale.model.Ticket;
import com.example.ticket_sale.util.AuthGuard;
import com.example.ticket_sale.mapper.MovieMapper;
import com.example.ticket_sale.mapper.ShowtimeMapper;
import com.example.ticket_sale.viewmodel.TheaterShowtimeViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;


public class TheaterShowtimeFragment extends Fragment {
    private TextView txtTheaterName;
    private TextView txtTheaterAddress;
    private TextView txtGoBack;
    private ProgressBar pbLoadShowtimes;
    private View viewOverlay;

    private RecyclerView rcViewDates;
    private RecyclerView rcViewMoviesByTheater;

    private DateAdapter dateAdapter;
    private MovieByTheaterAdapter movieByTheaterAdapter;

    private List<Calendar> availableDates;
    private List<Movie> movies;
    private List<Movie> visibleMovies;
    private Theater theater;
    private List<Ticket> tickets;

    private TheaterShowtimeViewModel showtimeViewModel;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public TheaterShowtimeFragment() {
        // Required empty public constructor
    }

    public static TheaterShowtimeFragment newInstance(String param1, String param2) {
        TheaterShowtimeFragment fragment = new TheaterShowtimeFragment();
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
        View root = inflater.inflate(R.layout.fragment_theater_showtime, container, false);
        initView(root);
        initData();
        setDataForViews();
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) requireActivity()).hideBottomNav();
    }

    private void initView(View root){
        txtTheaterName = root.findViewById(R.id.txtMovieTheaterName);
        txtTheaterAddress = root.findViewById(R.id.txtMovieTheaterAddress);
        txtGoBack = root.findViewById(R.id.txtGoBack);
        txtGoBack.setOnClickListener(v -> {
            ((MainActivity) requireActivity()).showBottomNav();
            getParentFragmentManager().popBackStack();
        });
        pbLoadShowtimes = root.findViewById(R.id.pbLoadShowtimes);
        viewOverlay = root.findViewById(R.id.viewOverlay);
        rcViewDates = root.findViewById(R.id.rcViewDates);
        rcViewMoviesByTheater = root.findViewById(R.id.rcViewMoviesByTheater);

        rcViewDates.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
        rcViewMoviesByTheater.setLayoutManager(new GridLayoutManager(getContext(),1));

    }

    private void setDataForViews() {
        if(theater ==null) return;
        txtTheaterName.setText(theater.getName());
        txtTheaterAddress.setText(theater.getAddress());

        dateAdapter = new DateAdapter(availableDates, this::onDateClick);
        rcViewDates.setAdapter(dateAdapter);

        movieByTheaterAdapter = new MovieByTheaterAdapter(new ArrayList<>(), getContext(), this::onItemClick);
        rcViewMoviesByTheater.setAdapter(movieByTheaterAdapter);

        Calendar fixedDate = Calendar.getInstance();
        fixedDate.set(2025, Calendar.MARCH, 21);
        onDateClick((Calendar) fixedDate.clone());
    }

    private void initData() {
        showLoadingUI();
        if(getArguments()== null) return;
        theater = getArguments().getParcelable("theater");
        availableDates = getAvailableDates(10);

        showtimeViewModel = new TheaterShowtimeViewModel();
        getTicketsFromAPI();
//        getMoviesFromAPI("2025-03-21");
//            movieTheater = new TheaterDTO();
//            movieTheater.setId( getArguments().getString("theaterId"));
//            Log.d("theater infor:", movieTheater.getId() +" "+movieTheater.getName() + " " +movieTheater.getAddress());

    }

    private void getMoviesFromAPI(String date){
        showtimeViewModel.getCurrentMovies().observe(getViewLifecycleOwner(), response -> {
            if (response != null && response.getStatusCode() == 200) {
                movies = response.getData().stream() .map(MovieMapper::toMovie)
                        .collect(Collectors.toList());
                getShowtimeFromAPI(movies, date);
            }else{
                Toast.makeText(getContext(), "Không thể lấy dữ liệu các rạp.", Toast.LENGTH_SHORT).show();
                movies = getMovies();
                visibleMovies = movies;
                movieByTheaterAdapter.setMovies(visibleMovies);
                hideLoadingUI();
            }
        });
    }

    private void getShowtimeFromAPI(List<Movie> activeMovies, String date) {
        visibleMovies = new ArrayList<>();
        int[] totalRequests = {0};
        int[] completedRequests = {0};

        for(Movie mv : activeMovies){
            if(mv.getMovieFormats() != null) {
                for (MovieFormat mFormat : mv.getMovieFormats()) {
                    totalRequests[0]++;
                    ShowtimeKey showtimeKey = new ShowtimeKey(theater.getId(), mv.getId(), mFormat.getId(), date);
                    showtimeViewModel.getShowtimes(showtimeKey).observe(getViewLifecycleOwner(), response -> {
                        completedRequests[0]++;
                        if (response != null &&response.getStatusCode() == 200 && !response.getData().isEmpty()) {
                            List<Showtime> showtimes = response.getData().stream()
                                    .map(ShowtimeMapper::toShowtime)
                                    .collect(Collectors.toList());
                            mFormat.setShowtimes(showtimes);
                            visibleMovies.add(mv);
                            movieByTheaterAdapter.setMovies(visibleMovies);
                        }
//                        else{
//                            Toast.makeText(getContext(),
//                                    String.format("Không thể lấy suất chiếu của phim %s",mv.getTitle()),
//                                    Toast.LENGTH_SHORT).show();
//                        }
                        hideLoadingUI();
                    });
                }

            }
        }
    }

    private void getTicketsFromAPI(){
        showtimeViewModel.getTickets().observe(getViewLifecycleOwner(), response ->{
            if (response != null && response.getStatusCode() == 200) {
                tickets = response.getData().stream()
                        .map(TicketMapper::toTicket)
                        .collect(Collectors.toList());
            }else{
                tickets = getExampleTicket();
                Toast.makeText(getContext(), "Không thể lấy các loại vé", Toast.LENGTH_SHORT).show();
            }

        });
    }

//    private void getScreenFromAPI(Showtime showtime){
//        showLoadingUI();
//        showtimeViewModel.getScreen(showtime.getScreenId()).observe(getViewLifecycleOwner(), response ->{
//            if(response!=null && response.getStatusCode() == 200){
//               Screen screen = ScreenMapper.toScreen(response.getData());
//            }else{
//                Toast.makeText(getContext(),"Không thể lấy thông tin rạp.",Toast.LENGTH_SHORT).show();
//                Screen screen = getExampleScreen();
//            }
//            hideLoadingUI();
//        });
//    }
//    private Screen getExampleScreen() {
//        Screen s = new Screen("SC2","Phòng 2", new Theater("TH1","Rạp", "address", R.drawable.cinema2));
////        Integer[][] seats = getSeats(10, 14);
////        s.setSeatPositions(seats);
//        return s;
//    }

    private void onDateClick(Calendar calendar) {
        showLoadingUI();
        movieByTheaterAdapter.setMovies(new ArrayList<>());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String dateString = dateFormat.format(calendar.getTime());
        getMoviesFromAPI(dateString);
    }


    private void onItemClick(int movieIndex, int formatIndex, int showtimeIndex) {
        showLoadingUI();
        if (theater == null) {
            Toast.makeText(requireContext(), "Rạp không khả dụng.", Toast.LENGTH_SHORT).show();
            hideLoadingUI();
            return;
        }
        Movie selectedMovie = visibleMovies.get(movieIndex);
        MovieFormat selectedMovieFormat = selectedMovie.getMovieFormats().get(formatIndex);
        Showtime selectedShowtime = selectedMovieFormat.getShowtimes().get(showtimeIndex);

        if(tickets == null) {
            Toast.makeText(requireContext(), "Không có loại vé nào khả dụng.", Toast.LENGTH_SHORT).show();
            hideLoadingUI();
            return;
        }
        String[] ticketNames = new String[tickets.size()];
        for (int i = 0; i < tickets.size(); i++) {
            Ticket t = tickets.get(i);
            ticketNames[i] = t.getName() + " - " + t.getPrice() + "đ";
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Chọn loại vé");
        builder.setItems(ticketNames, (dialog, which) -> {
            Ticket selectedTicket = tickets.get(which);
            navigateToChooseSeat(selectedMovie, selectedMovieFormat, selectedShowtime, selectedTicket);
        });
        builder.setOnDismissListener(dialog -> hideLoadingUI());
        builder.show();
    }

    private void navigateToChooseSeat(Movie selectedMovie,MovieFormat selectedMovieFormat,
                                      Showtime selectedShowtime,Ticket selectedTicket) {
        if(selectedMovie == null) {
            Toast.makeText(requireContext(), "Phim không khả dụng.", Toast.LENGTH_SHORT).show();
            return;
        }

        if(selectedMovieFormat == null) {
            Toast.makeText(requireContext(), "Định dạng của phim không khả dụng.", Toast.LENGTH_SHORT).show();
            return;
        }

        if(selectedShowtime == null) {
            Toast.makeText(requireContext(), "Suất chiếu không khả dụng.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (tickets == null || tickets.isEmpty()) {
            Toast.makeText(requireContext(), "Không có loại vé nào khả dụng.", Toast.LENGTH_SHORT).show();
            return;
        }

        showLoadingUI();
        ChooseSeatFragment chooseSeatFragment = new ChooseSeatFragment();
        Bundle bundle = new Bundle();
        Order order = new Order();

        order.setSelectedTicket(selectedTicket);
        order.setMovie(selectedMovie);
        order.setMovieFormat(selectedMovieFormat);
        order.setShowtime(selectedShowtime);
        bundle.putParcelable("order", order);

        chooseSeatFragment.setArguments(bundle);
        if(AuthGuard.checkLogin(requireActivity())){
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container,chooseSeatFragment)
                    .addToBackStack(null)
                    .commit();
        }
        hideLoadingUI();
    }

    private List<Ticket> getExampleTicket() {
        List<Ticket> tickets = new ArrayList<>();
        tickets.add( new Ticket("1", "Tiêu chuẩn",65000L,(short) 1,"",""));
        tickets.add( new Ticket("1", "U22",60000L,(short) 1,"",""));
        return tickets;
    }

    private List<Calendar> getAvailableDates(int numOfDays){
        List<Calendar> availableDates = new ArrayList<>();

        Calendar fixedDate = Calendar.getInstance();
        fixedDate.set(2025, Calendar.MARCH, 21);
        availableDates.add((Calendar) fixedDate.clone());

        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < numOfDays; i++) {
            availableDates.add((Calendar) calendar.clone());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        return availableDates;
    }



    private void showLoadingUI(){
        pbLoadShowtimes.setVisibility(View.VISIBLE);
        viewOverlay.setVisibility(View.VISIBLE);
    }

    private void hideLoadingUI(){
        pbLoadShowtimes.setVisibility(View.GONE);
        viewOverlay.setVisibility(View.GONE);
    }

    private List<Movie> getMovies() {

        Showtime st1  = new Showtime(1,"20:10","21:32","RV1","MV1","07/03/2025");
        Showtime st2  = new Showtime(2,"18:10","21:32","RV1","MV1","07/03/2025");
        Showtime st3  = new Showtime(3,"16:10","21:32","RV1","MV1","07/03/2025");
        Showtime st4  = new Showtime(4,"19:20","21:32","RV1","MV1","07/03/2025");
        List<Showtime> showtimes1 = new ArrayList<>(List.of(st1, st2, st1, st2, st1, st2));
        List<Showtime> showtimes2 = new ArrayList<>(List.of(st3, st4, st1, st2, st1, st2));

        MovieFormat mf1 = new MovieFormat("VSUB","Phụ đề",showtimes1);
        MovieFormat mf2 = new MovieFormat("VSUB","Thuyết minh",showtimes1);
        MovieFormat mf3 = new MovieFormat("VSUB","Phụ đề",showtimes2);

        List<MovieFormat> movieFormats1= new ArrayList<>(List.of(mf1, mf2));
        List<MovieFormat> movieFormats2 = new ArrayList<>(List.of(mf2, mf3));

        Movie mbt1 = new Movie("MV1","Bí kíp luyện rồng",123,13, 8.6F,R.drawable.mv_bi_kip_luyen_rong,movieFormats1,null);
        Movie mbt2 = new Movie("MV2","The bad guys 2",113,13, 8.6F,R.drawable.mv_the_bad_guys_2,movieFormats2,null);
        List<Movie> movies= Arrays.asList(mbt2,mbt1);
//        Log.e("moviesByTheater.get(0).getMovieFormats().size", String.valueOf(moviesByTheater.get(0).getMovieFormats().get(0).getShowtimes().size()));
//        Log.e("moviesByTheater.get(1).getMovieFormats().size", String.valueOf(moviesByTheater.get(1).getMovieFormats().get(0).getShowtimes().size()));
        return movies;
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
////        List<MovieDTO> movies = moviesByDate.get(date);
////        movieAdapter = new MovieAdapter(movies);
////        rcViewMovieShowtime.setAdapter(movieAdapter);
//}