package com.example.ticket_sale.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.ticket_sale.R;
import com.example.ticket_sale.adapter.MovieAdapter;
import com.example.ticket_sale.adapter.PosterAdapter;
import com.example.ticket_sale.model.Movie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    private ViewPager2 slidePoster;
    private Button btnCurrentMovies;
    private Button btnUpcomingMovies;
    private RecyclerView rcViewMovies;

    private PosterAdapter posterAdapter;
    private MovieAdapter currentMovieAdapter;
    private MovieAdapter upcomingMovieAdapter;

    private List<Integer> currentPosters;
    private List<Movie> currentMovies;
    private List<Movie> upcomingMovies;



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
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        slidePoster = root.findViewById(R.id.slidePoster);
        btnCurrentMovies = root.findViewById(R.id.btnCurrentMovies);
        btnUpcomingMovies = root.findViewById(R.id.btnUpcomingMovies);

        rcViewMovies = root.findViewById(R.id.rcViewCurrentMovies);
//        rcViewUpcomingMovies = root.findViewById(R.id.rcViewUpcomingMovies);
        // Truyền dữ liệu cho poster
        currentPosters = Arrays.asList(R.drawable.pt_captainamerica,R.drawable.pt_shopeepay,R.drawable.pt_zalopay);
        setDataSliderAdapter(currentPosters);

        //Truyền dữ liệu cho các movie đang chiếu
        currentMovies = getExampleMovies();
//        setDataForCurrentMovies(currentMovies);
        //Truyền dữ liệu cho các movie sắp chiếu
        upcomingMovies = getExampleUpcomingMovies();

        currentMovieAdapter = new MovieAdapter(currentMovies);
        upcomingMovieAdapter = new MovieAdapter(upcomingMovies);

        setButtonListener();
//        setDataForUpcomingMovies(upcomingMovies);
        return root;
    }

    private List<Movie> getExampleUpcomingMovies() {
        Movie mv7 = new Movie("MV7",R.drawable.mv_nhoc_quay,"Nhóc quậy",115,6, 8.9F);
        Movie mv8 = new Movie("MV8",R.drawable.mv_emma,"Emma và vương quốc tí hon",105,6, 8.9F);
        Movie mv9 = new Movie("MV9",R.drawable.mv_mission_impossible,"Nhiệm vụ bất khả thi: Nghiệp báo cuối cùng",105,6, 8.9F);
        Movie mv10 = new Movie("MV10",R.drawable.mv_lang_xi_trum,"Phim xì trum",125,3, 8.9F);
        return Arrays.asList(mv7,mv8,mv9,mv10);
    }

    private List<Movie> getExampleMovies() {
        Movie mv1 = new Movie("MV1",R.drawable.mv_bi_kip_luyen_rong,"Bí kíp luyện rồng",123,13, 8.6F);
        Movie mv2 = new Movie("MV2",R.drawable.mv_elio,"Elio Cậu bé đến từ trái đất",112,13, 8.5F);
        Movie mv3 = new Movie("MV3",R.drawable.mv_superman,"Superman",113,13, 9.3F);
        Movie mv4 = new Movie("MV4",R.drawable.mv_gau_thu_chu_du,"Gấu thủ chu du",98,3, 9.1F);
        Movie mv5 = new Movie("MV5",R.drawable.mv_nu_tu_bong_toi,"Nữ tu bóng tối",114,16, 9.2F);
        Movie mv6 = new Movie("MV6",R.drawable.mv_the_bad_guys_2,"Băng đảng quái kiệt 2",131,16, 9.0F);

        return Arrays.asList(mv1,mv2,mv3,mv4,mv5,mv6);
    }

    private final Handler sliderHandler = new Handler();
    private final Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            if (slidePoster != null && slidePoster.getAdapter() != null) {
                int currentItem = slidePoster.getCurrentItem();
                int itemCount = slidePoster.getAdapter().getItemCount();

                // Chuyển sang slide tiếp theo hoặc quay về đầu nếu hết
                slidePoster.setCurrentItem((currentItem + 1) % itemCount, true);
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

    private void setDataSliderAdapter(List<Integer> posters){
        posterAdapter = new PosterAdapter(posters);
        slidePoster.setAdapter(posterAdapter);
        slidePoster.setPageTransformer((page, position) -> {
            page.setAlpha(0.7f + (1 - Math.abs(position)) * 0.3f);
            page.setScaleY(0.8f + (1 - Math.abs(position)) * 0.2f);
        });

        slidePoster.setClipToPadding(false);
        slidePoster.setClipChildren(false);
        slidePoster.setOffscreenPageLimit(3); // Load trước 3 slide để cuộn mượt hơn

        RecyclerView recyclerView = (RecyclerView) slidePoster.getChildAt(0);
        recyclerView.setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        if (recyclerView.getOnFlingListener() != null) {
            recyclerView.setOnFlingListener(null);
        }

        // Giúp slide dừng chính xác ở giữa
        LinearSnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
    }

//    private void setDataForCurrentMovies(List<Movie> currentMovies){
//        currentMovieAdapter = new MovieAdapter(currentMovies);
//        rcViewMovies.setLayoutManager(new GridLayoutManager(getContext(),2));
//        rcViewMovies.setAdapter(currentMovieAdapter);
//    }
//
//    private void setDataForUpcomingMovies(List<Movie> upcomingMovies){
//        upcomingMovieAdapter = new MovieAdapter(upcomingMovies);
//        rcViewMovies.setLayoutManager(new GridLayoutManager(getContext(),2));
//        rcViewMovies.setAdapter(upcomingMovieAdapter);
//    }

    private void setButtonListener(){
        rcViewMovies.setLayoutManager(new GridLayoutManager(getContext(),2));
        rcViewMovies.setAdapter(currentMovieAdapter);
        btnCurrentMovies.setOnClickListener(v -> {
            rcViewMovies.setAdapter(new MovieAdapter(currentMovies));
        });

        btnUpcomingMovies.setOnClickListener(v -> {
            rcViewMovies.setAdapter(new MovieAdapter(upcomingMovies));
        });
    }

}