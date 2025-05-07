package com.example.ticket_sale.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.ticket_sale.R;
import com.example.ticket_sale.adapter.MovieAdapter;
import com.example.ticket_sale.adapter.PosterAdapter;
import com.example.ticket_sale.data.dto.Slider;
import com.example.ticket_sale.model.Movie;
import com.example.ticket_sale.util.mapper.MovieMapper;
import com.example.ticket_sale.viewmodel.HomeViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    private ViewPager2 bannerSlider;
    private Button btnCurrentMovies;
    private Button btnUpcomingMovies;
    private Button btnMoreMovies;
    private RecyclerView rcViewMovies;
    private PosterAdapter posterAdapter;
    private MovieAdapter movieAdapter;
    private HomeViewModel homeViewModel;

    private List<Slider> currentPosters = new ArrayList<>();
    private List<Movie> currentMovies= new ArrayList<>();
    private List<Movie> upcomingMovies= new ArrayList<>();

    ProgressBar pgbLoadMovies;
    ProgressBar pgbLoadSliders;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        initView(root);
        initData();
        return root;
    }

    private void initView(View root){
        bannerSlider = root.findViewById(R.id.slidePoster);
        btnCurrentMovies = root.findViewById(R.id.btnCurrentMovies);
        btnUpcomingMovies = root.findViewById(R.id.btnUpcomingMovies);
        btnMoreMovies = root.findViewById(R.id.btnMoreMovies);
        rcViewMovies = root.findViewById(R.id.rcViewCurrentMovies);
        pgbLoadSliders = root.findViewById(R.id.pbLoadPosters);
        pgbLoadMovies = root.findViewById(R.id.pbLoadMovies);

        posterAdapter = new PosterAdapter(currentPosters);
        bannerSlider.setAdapter(posterAdapter);
        setAnimationForSlider();

        movieAdapter = new MovieAdapter(currentMovies,this::openMovieDetail);
        rcViewMovies.setLayoutManager(new GridLayoutManager(getContext(),2));
        rcViewMovies.setAdapter(movieAdapter);

        setButtonListener();


    }

    private void initData() {
        homeViewModel = new HomeViewModel();
        getCurrentPosterFromAPI();
        getCurrentMovieFromAPI();
//        getUpcomingMovieFromAPI();
    }

    private void getCurrentMovieFromAPI(){
        movieAdapter.setMovies(new ArrayList<>());
        pgbLoadMovies.setVisibility(View.VISIBLE);
        homeViewModel.getCurrentMovies().observe(getViewLifecycleOwner(), dataResult ->{
            if(dataResult == null || dataResult.getStatusCode()!=200){
                Log.e("MovieAPI", "Failed to get movies: " + dataResult);
                Toast.makeText(getContext(), "Không thể lấy dữ liệu phim hiện tại", Toast.LENGTH_SHORT).show();
                currentMovies = getExampleMovies();
            }else{
                currentMovies = dataResult.getData().stream().map(MovieMapper::toMovie).collect(Collectors.toList());
            }
            movieAdapter.setMovies(currentMovies);
            pgbLoadMovies.setVisibility(View.GONE);
        });
    }

    private void getUpcomingMovieFromAPI(){
        movieAdapter.setMovies(new ArrayList<>());
        pgbLoadMovies.setVisibility(View.VISIBLE);
        homeViewModel.getUpcomingMovies().observe(getViewLifecycleOwner(), dataResult ->{
            if(dataResult == null || dataResult.getStatusCode()!=200){
                Log.e("MovieAPI", "Failed to get movies: " + dataResult);
                Toast.makeText(getContext(), "Không thể lấy dữ liệu phim hiện tại", Toast.LENGTH_SHORT).show();
                upcomingMovies = getExampleUpcomingMovies();

            }else{
                upcomingMovies = dataResult.getData().stream().map(MovieMapper::toMovie).collect(Collectors.toList());
            }
            movieAdapter.setMovies(upcomingMovies);
            pgbLoadMovies.setVisibility(View.GONE);
        });
    }

    private void getCurrentPosterFromAPI(){
        pgbLoadSliders.setVisibility(View.VISIBLE);
        homeViewModel.getCurrentPosters().observe(getViewLifecycleOwner(), resultData ->{
            if(resultData == null || resultData.getStatusCode()!=200){
                Log.e("MovieAPI", "Failed to get movies: " +resultData);
                Toast.makeText(getContext(), "Không thể lấy dữ liệu slider hiện tại", Toast.LENGTH_SHORT).show();
                currentPosters = getExamplePosters();
            }else{
                currentPosters = resultData.getData();
            }
            posterAdapter.setPosters(currentPosters);
            pgbLoadSliders.setVisibility(View.GONE);
        });
    }

    private List<Slider> getExamplePosters() {
        List<Slider> sliders = new ArrayList<>();
        sliders.add( new Slider("SD1","New Movie","",R.drawable.pt_captainamerica,""));
        sliders.add( new Slider("SD2","Thanh toán với ShopeePay","",R.drawable.pt_shopeepay,""));
        sliders.add( new Slider("SD3","Thanh toán với ZaloPay","",R.drawable.pt_zalopay,""));
        return sliders;
    }

    private List<Movie> getExampleUpcomingMovies() {
        Movie mv7 = new Movie("MV7",R.drawable.mv_nhoc_quay,"Nhóc quậy",115,6, 8.9F,"https://youtu.be/JvsRMNTV9is?si=Uc0WwR1RWtrjrsyu");
        Movie mv8 = new Movie("MV8",R.drawable.mv_emma,"Emma và vương quốc tí hon",105,6, 8.9F, "https://youtu.be/JvsRMNTV9is?si=Uc0WwR1RWtrjrsyu");
        Movie mv9 = new Movie("MV9",R.drawable.mv_mission_impossible,"Nhiệm vụ bất khả thi: Nghiệp báo cuối cùng",105,6, 8.9F,"https://youtu.be/JvsRMNTV9is?si=Uc0WwR1RWtrjrsyu");
        Movie mv10 = new Movie("MV10",R.drawable.mv_lang_xi_trum,"Phim xì trum",125,3, 8.9F,"https://youtu.be/JvsRMNTV9is?si=Uc0WwR1RWtrjrsyu");
        return Arrays.asList(mv7,mv8,mv9,mv10);
    }

    private List<Movie> getExampleMovies() {
        Movie mv1 = new Movie("MV1",R.drawable.mv_bi_kip_luyen_rong,"Bí kíp luyện rồng",123,13, 8.6F, "https://youtu.be/JvsRMNTV9is?si=Uc0WwR1RWtrjrsyu");
        Movie mv2 = new Movie("MV2",R.drawable.mv_elio,"Elio Cậu bé đến từ trái đất",112,13, 8.5F, "https://youtu.be/JvsRMNTV9is?si=Uc0WwR1RWtrjrsyu");
        Movie mv3 = new Movie("MV3",R.drawable.mv_superman,"Superman",113,13, 9.3F, "https://youtu.be/JvsRMNTV9is?si=Uc0WwR1RWtrjrsyu");
        Movie mv4 = new Movie("MV4",R.drawable.mv_gau_thu_chu_du,"Gấu thủ chu du",98,3, 9.1F, "https://youtu.be/JvsRMNTV9is?si=Uc0WwR1RWtrjrsyu");
        Movie mv5 = new Movie("MV5",R.drawable.mv_nu_tu_bong_toi,"Nữ tu bóng tối",114,16, 9.2F, "https://youtu.be/JvsRMNTV9is?si=Uc0WwR1RWtrjrsyu");
        Movie mv6 = new Movie("MV6",R.drawable.mv_the_bad_guys_2,"Băng đảng quái kiệt 2",131,16, 9.0F, "https://youtu.be/JvsRMNTV9is?si=Uc0WwR1RWtrjrsyu");

        return Arrays.asList(mv1,mv2,mv3,mv4,mv5,mv6);
    }

    private final Handler sliderHandler = new Handler();

    private final Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            if (bannerSlider != null && bannerSlider.getAdapter() != null) {
                int currentItem = bannerSlider.getCurrentItem();
                int itemCount = bannerSlider.getAdapter().getItemCount();
                if(itemCount != 0) {
                    // Chuyển sang slide tiếp theo hoặc quay về đầu nếu hết
                    bannerSlider.setCurrentItem((currentItem + 1) % itemCount, true);
                }
            }
            sliderHandler.postDelayed(this, 5000); // Lặp lại sau 5 giây
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        sliderHandler.postDelayed(sliderRunnable, 5000); // Bắt đầu tự động chạy
    }

    @Override
    public void onPause() {
        super.onPause();
        sliderHandler.removeCallbacks(sliderRunnable); // Dừng lại khi thoát Activity
    }

    private void setAnimationForSlider(){
        bannerSlider.setPageTransformer((page, position) -> {
            page.setAlpha(0.7f + (1 - Math.abs(position)) * 0.3f);
            page.setScaleY(0.8f + (1 - Math.abs(position)) * 0.2f);
        });

        bannerSlider.setClipToPadding(false);
        bannerSlider.setClipChildren(false);
        bannerSlider.setOffscreenPageLimit(3); // Load trước 3 slide để cuộn mượt hơn

        RecyclerView recyclerView = (RecyclerView) bannerSlider.getChildAt(0);
        recyclerView.setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        if (recyclerView.getOnFlingListener() != null) {
            recyclerView.setOnFlingListener(null);
        }

        // Giúp slide dừng chính xác ở giữa
        LinearSnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
    }


    private void setButtonListener(){
        btnCurrentMovies.setOnClickListener(v -> {
            if(currentMovies == null || currentMovies.isEmpty()){
                getCurrentMovieFromAPI();
            }else{
                movieAdapter.setMovies(currentMovies);
            }
        });

        btnUpcomingMovies.setOnClickListener(v -> {
            if(upcomingMovies == null || upcomingMovies.isEmpty()){
                getUpcomingMovieFromAPI();
            }else{
                movieAdapter.setMovies(upcomingMovies);
            }
        });

        btnMoreMovies.setOnClickListener(v -> {
            getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new MovieFragment())
                .addToBackStack(null).commit();
        });
    }

    private void openMovieDetail(Movie movie) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("movie", movie);

        MovieDetailFragment detailFragment = new MovieDetailFragment();
        detailFragment.setArguments(bundle);

        // Chuyển đến MovieDetailFragment
        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, detailFragment)
                .addToBackStack(null)
                .commit();
    }
}


//    private void setDataForCurrentMovies(List<MovieDTO> currentMovies){
//        currentMovieAdapter = new MovieAdapter(currentMovies);
//        rcViewMovies.setLayoutManager(new GridLayoutManager(getContext(),2));
//        rcViewMovies.setAdapter(currentMovieAdapter);
//    }
//
//    private void setDataForUpcomingMovies(List<MovieDTO> upcomingMovies){
//        upcomingMovieAdapter = new MovieAdapter(upcomingMovies);
//        rcViewMovies.setLayoutManager(new GridLayoutManager(getContext(),2));
//        rcViewMovies.setAdapter(upcomingMovieAdapter);
//    }