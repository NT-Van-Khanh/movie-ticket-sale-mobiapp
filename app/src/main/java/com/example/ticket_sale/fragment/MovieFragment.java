package com.example.ticket_sale.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.ticket_sale.R;
import com.example.ticket_sale.adapter.MovieAdapter;
import com.example.ticket_sale.model.Movie;
import com.example.ticket_sale.mapper.MovieMapper;
import com.example.ticket_sale.viewmodel.HomeViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MovieFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieFragment extends Fragment {
    private RecyclerView rcViewMovies;
    private Button btnCurrentMovies;
    private Button btnUpcomingMovies;
    private ProgressBar  pbLoadMovies;

    private MovieAdapter movieAdapter;
    private List<Movie> currentMovies;
    private List<Movie> upcomingMovies;
    private HomeViewModel homeViewModel;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MovieFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static MovieFragment newInstance(String param1, String param2) {
        MovieFragment fragment = new MovieFragment();
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
        View v = inflater.inflate(R.layout.fragment_movie, container, false);
        initView(v);
        initData();
        setDataForViews();
        return v;
    }

    private void setDataForViews() {
        movieAdapter.setMovies(currentMovies);
    }

    private void initData() {
        getCurrentMovieFromAPI();
    }

    private void initView(View root) {
        rcViewMovies  = root.findViewById(R.id.rcViewMovies);
        btnCurrentMovies = root.findViewById(R.id.btnCurrentMovies);
        btnUpcomingMovies = root.findViewById(R.id.btnUpcomingMovies);
        pbLoadMovies = root.findViewById(R.id.pbLoadMovies);
        currentMovies = new ArrayList<>();
        movieAdapter = new MovieAdapter(currentMovies, this::openMovieDetail);
        rcViewMovies.setLayoutManager(new GridLayoutManager(getContext(),2));
        rcViewMovies.setAdapter(movieAdapter);

        initButtonListener();
    }


    private void initButtonListener(){
        btnCurrentMovies.setOnClickListener(v ->{
            if(currentMovies == null || currentMovies.isEmpty()){
                getCurrentMovieFromAPI();
            }else{
                movieAdapter.setMovies(currentMovies);
            }
        });

        btnUpcomingMovies.setOnClickListener(v->{
            if(upcomingMovies == null || upcomingMovies.isEmpty()){
                getUpcomingMovieFromAPI();
            }else{
                movieAdapter.setMovies(upcomingMovies);
            }
        });
    }


    private void openMovieDetail(Movie movie){
        Bundle bundle = new Bundle();
        bundle.putParcelable("movie", movie);

        MovieDetailFragment detailFragment = new MovieDetailFragment();
        detailFragment.setArguments(bundle);

        getParentFragmentManager().beginTransaction().
                replace(R.id.fragment_container, detailFragment)
                .addToBackStack(null)
                .commit();
    }
    private void getCurrentMovieFromAPI(){
        movieAdapter.setMovies(new ArrayList<>());
        pbLoadMovies.setVisibility(View.VISIBLE);
        if(homeViewModel == null)   homeViewModel = new HomeViewModel();
        homeViewModel.getCurrentMovies().observe(getViewLifecycleOwner(), dataResult ->{
            if(dataResult == null || dataResult.getStatusCode()!=200){
                Log.e("MovieAPI", "Failed to get movies: " + dataResult);
                Toast.makeText(getContext(), "Không thể lấy dữ liệu phim hiện tại", Toast.LENGTH_SHORT).show();
                currentMovies = getExampleCurrentMovies();
            }else{
                currentMovies = dataResult.getData().stream().map(MovieMapper::toMovie).collect(Collectors.toList());
            }
            movieAdapter.setMovies(currentMovies);
            pbLoadMovies.setVisibility(View.GONE);
        });
    }

    private void getUpcomingMovieFromAPI(){
        movieAdapter.setMovies(new ArrayList<>());
        pbLoadMovies.setVisibility(View.VISIBLE);
        homeViewModel.getUpcomingMovies().observe(getViewLifecycleOwner(), dataResult ->{
            if(dataResult == null || dataResult.getStatusCode()!=200){
                Log.e("MovieAPI", "Failed to get movies: " + dataResult);
                Toast.makeText(getContext(), "Không thể lấy dữ liệu phim hiện tại", Toast.LENGTH_SHORT).show();
                upcomingMovies = getExampleUpcomingMovies();
            }else{
                upcomingMovies = dataResult.getData().stream().map(MovieMapper::toMovie).collect(Collectors.toList());
            }
            movieAdapter.setMovies(upcomingMovies);
            pbLoadMovies.setVisibility(View.GONE);
        });
    }

    private List<Movie> getExampleUpcomingMovies(){
        Movie mv7 = new Movie("MV7",R.drawable.mv_nhoc_quay,"Nhóc quậy",115,6, 8.9F, "https://youtu.be/JvsRMNTV9is?si=Uc0WwR1RWtrjrsyu");
        Movie mv8 = new Movie("MV8",R.drawable.mv_emma,"Emma và vương quốc tí hon",105,6, 8.9F, "https://youtu.be/JvsRMNTV9is?si=Uc0WwR1RWtrjrsyu");
        Movie mv9 = new Movie("MV9",R.drawable.mv_mission_impossible,"Nhiệm vụ bất khả thi: Nghiệp báo cuối cùng",105,6, 8.9F, "https://youtu.be/JvsRMNTV9is?si=Uc0WwR1RWtrjrsyu");
        Movie mv10 = new Movie("MV10",R.drawable.mv_lang_xi_trum,"Phim xì trum",125,3, 8.9F, "https://youtu.be/JvsRMNTV9is?si=Uc0WwR1RWtrjrsyu");
        return Arrays.asList(mv7,mv8,mv9,mv10);
    }

    private List<Movie> getExampleCurrentMovies(){
        Movie mv1 = new Movie("MV1",R.drawable.mv_bi_kip_luyen_rong,"Bí kíp luyện rồng",123,13, 8.6F, "https://youtu.be/JvsRMNTV9is?si=Uc0WwR1RWtrjrsyu");
        Movie mv2 = new Movie("MV2",R.drawable.mv_elio,"Elio Cậu bé đến từ trái đất",112,13, 8.5F, "https://youtu.be/JvsRMNTV9is?si=Uc0WwR1RWtrjrsyu");
        Movie mv3 = new Movie("MV3",R.drawable.mv_superman,"Superman",113,13, 9.3F, "https://youtu.be/JvsRMNTV9is?si=Uc0WwR1RWtrjrsyu");
        Movie mv4 = new Movie("MV4",R.drawable.mv_gau_thu_chu_du,"Gấu thủ chu du",98,3, 9.1F, "https://youtu.be/JvsRMNTV9is?si=Uc0WwR1RWtrjrsyu");
        Movie mv5 = new Movie("MV5",R.drawable.mv_nu_tu_bong_toi,"Nữ tu bóng tối",114,16, 9.2F, "https://youtu.be/JvsRMNTV9is?si=Uc0WwR1RWtrjrsyu");
        Movie mv6 = new Movie("MV6",R.drawable.mv_the_bad_guys_2,"Băng đảng quái kiệt 2",131,16, 9.0F, "https://youtu.be/JvsRMNTV9is?si=Uc0WwR1RWtrjrsyu");

        return Arrays.asList(mv1,mv2,mv3,mv4,mv5,mv6);
    }
}