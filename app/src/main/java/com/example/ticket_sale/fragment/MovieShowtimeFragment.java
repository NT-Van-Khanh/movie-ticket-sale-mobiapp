package com.example.ticket_sale.fragment;

import android.app.AlertDialog;
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

import com.bumptech.glide.Glide;
import com.example.ticket_sale.R;
import com.example.ticket_sale.activity.MainActivity;
import com.example.ticket_sale.adapter.DateAdapter;
import com.example.ticket_sale.adapter.TheaterShowtimeAdapter;
import com.example.ticket_sale.mapper.ShowtimeMapper;
import com.example.ticket_sale.mapper.TicketMapper;
import com.example.ticket_sale.model.Movie;
import com.example.ticket_sale.model.MovieFormat;
import com.example.ticket_sale.model.Order;
import com.example.ticket_sale.model.Showtime;
import com.example.ticket_sale.model.ShowtimeKey;
import com.example.ticket_sale.model.Theater;
import com.example.ticket_sale.model.Ticket;
import com.example.ticket_sale.util.AuthGuard;
import com.example.ticket_sale.util.ViLocaleUtil;
import com.example.ticket_sale.mapper.TheaterMapper;
import com.example.ticket_sale.viewmodel.MovieShowtimeViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
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
    private View viewOverlay;
    private DateAdapter dateAdapter;
    private TheaterShowtimeAdapter theaterShowtimeAdapter;

    private Movie movie;
    private List<Theater> theaters;
    private List<Calendar> availableDates;
    private List<Theater> availableTheaters;

    private List<Ticket> tickets;

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
        viewOverlay = root.findViewById(R.id.viewOverlay);
        txtGoBack.setOnClickListener(v ->  {
            ((MainActivity) requireActivity()).showBottomNav();
            getParentFragmentManager().popBackStack();
        });

        rcViewDates.setLayoutManager( new LinearLayoutManager(getContext(),
                                        LinearLayoutManager.HORIZONTAL, false));
        rcViewTheaterShowtimes.setLayoutManager(new LinearLayoutManager(getContext(),
                                        LinearLayoutManager.VERTICAL, false));

    }

    private void setDataForViews(){
        if(movie == null) return;
        txtMovieTitle.setText(movie.getTitle());
        txtMovieDuration.setText(String.format(ViLocaleUtil.localeVN, "%d phút", movie.getDuration()));
        txtMovieRating.setText(String.valueOf(movie.getRating()));
        txtMovieAge.setText(String.format(ViLocaleUtil.localeVN, "T%d",movie.getAge()));
        Glide.with(this)
                .load(movie.getImageLink())
                .placeholder(R.drawable.mv_elio)
                .error(R.drawable.mv_elio)
                .into(imgMovieImage);

        if(availableDates == null) return;
        dateAdapter = new DateAdapter(availableDates, this::onDateClick);
        rcViewDates.setAdapter(dateAdapter);

        theaterShowtimeAdapter = new TheaterShowtimeAdapter(new ArrayList<>(), movie, getContext(), this::onShowtimeClick);
        rcViewTheaterShowtimes.setAdapter(theaterShowtimeAdapter);

    }

    private void initData(){
        showLoadingUI();
        if(getArguments() == null ) return;
        movie = getArguments().getParcelable("movie");
        if(movie == null) return;
        movieShowtimeViewModel = new MovieShowtimeViewModel();

        availableDates = getAvailableDates(10);

        getTicketsFromAPI();
        getTheatersFromAPI("2025-03-21");
    }

    private void getTicketsFromAPI() {
        movieShowtimeViewModel.getTickets().observe(getViewLifecycleOwner(), response ->{
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


    private void getShowtimeFromAPI(Movie movie, List<Theater> theaters, String date){
        availableTheaters = new ArrayList<>();
        int[] totalRequests = {0};
        int[] completedRequests = {0};

        for (Theater theater : theaters){
            if(movie.getMovieFormats() == null) return;
            for(MovieFormat movieFormat : movie.getMovieFormats()){
                totalRequests[0]++;
                ShowtimeKey showtimeKey = new ShowtimeKey(theater.getId(), movie.getId(), movieFormat.getId(), date);
                movieShowtimeViewModel.getShowtimes(theater.getId(),date,movie.getId(), movieFormat.getId())
                        .observe(getViewLifecycleOwner(), response ->{
                            completedRequests[0]++;
                            if (response != null &&response.getStatusCode() == 200 && !response.getData().isEmpty()) {
                                List<Showtime> showtimes = response.getData().stream()
                                        .map(ShowtimeMapper::toShowtime)
                                        .collect(Collectors.toList());
                                movieFormat.setShowtimes(showtimes);
                                availableTheaters.add(theater);
                                theaterShowtimeAdapter.setTheaters(availableTheaters);
                            }
                            hideLoadingUI();

                        });
            }

        }

    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) requireActivity()).hideBottomNav();
    }

    private void getTheatersFromAPI(String date) {
        movieShowtimeViewModel.getTheaters().observe(getViewLifecycleOwner(), response ->{
            if(response != null && response.getStatusCode() == 200){
                theaters = response.getData().stream()
                                .map(TheaterMapper::toTheater)
                                .collect(Collectors.toList());

                getShowtimeFromAPI(movie, theaters, date);

            }else{
                movie.setMovieFormats(getExampleMovieFormats());
                theaters = getExampleTheaters();
                theaterShowtimeAdapter.setTheaters(theaters);
                Toast.makeText(getContext(), "Không thể lấy dữ liệu rạp hiện tại.", Toast.LENGTH_SHORT).show();
            }
            hideLoadingUI();
        });
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

    private void onShowtimeClick(int theaterPosition, int formatPosition, int showtimePosition) {
        showLoadingUI();
        if (movie == null) {
            Toast.makeText(requireContext(), "Phim không khả dụng.", Toast.LENGTH_SHORT).show();
            hideLoadingUI();
            return;
        }

        Theater theater = theaters.get(theaterPosition);
        MovieFormat selectedMovieFormat = movie.getMovieFormats().get(formatPosition);
        Showtime selectedShowtime = selectedMovieFormat.getShowtimes().get(showtimePosition);

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
            navigateToChooseSeat(movie, selectedMovieFormat, selectedShowtime, selectedTicket);
        });
        builder.setOnDismissListener(dialog -> hideLoadingUI());
        builder.show();

    }

    private void navigateToChooseSeat(Movie selectedMovie, MovieFormat selectedMovieFormat,
                                      Showtime selectedShowtime, Ticket selectedTicket) {
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

    private void onDateClick(Calendar calendar) {
        showLoadingUI();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String dateString = dateFormat.format(calendar.getTime());
        getTheatersFromAPI(dateString);
    }


    private void showLoadingUI(){
        pbLoadShowtimes.setVisibility(View.VISIBLE);
        viewOverlay.setVisibility(View.VISIBLE);
    }

    private void hideLoadingUI(){
        pbLoadShowtimes.setVisibility(View.GONE);
        viewOverlay.setVisibility(View.GONE);
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
        Showtime st1  = new Showtime(1,"20:10","21:32","RV1","MV1","21/03/2025");
        Showtime st2  = new Showtime(2,"18:10","21:32","RV1","MV1","21/03/2025");
        Showtime st3  = new Showtime(3,"16:10","21:32","RV1","MV1","21/03/2025");
        Showtime st4  = new Showtime(4,"19:20","21:32","RV1","MV1","21/03/2025");
        List<Showtime> showtimes1 = Arrays.asList(st1, st2, st1, st2, st1, st2);
        List<Showtime> showtimes2 = Arrays.asList(st3, st4, st1, st2, st1, st2);

        MovieFormat mf1 = new MovieFormat("VSUB","Phụ đề",showtimes1);
        MovieFormat mf2 = new MovieFormat("VSUB","Thuyết minh",showtimes1);
        MovieFormat mf3 = new MovieFormat("VSUB","Phụ đề",showtimes2);
        List<MovieFormat> movieFormats1 = List.of(mf1, mf2);
        List<MovieFormat> movieFormats2 = Arrays.asList(mf2, mf3);
        return movieFormats1;
    }

    private List<Ticket> getExampleTicket() {
        List<Ticket> tickets = new ArrayList<>();
        tickets.add( new Ticket("1", "Tiêu chuẩn",65000L,(short) 1,"",""));
        tickets.add( new Ticket("1", "U22",60000L,(short) 1,"",""));
        return tickets;
    }
}
