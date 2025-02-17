package com.example.ticket_sale.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ticket_sale.R;
import com.example.ticket_sale.adapter.PosterAdapter;

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
    private RecyclerView recyclerView;
    private PosterAdapter posterAdapter;
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
        recyclerView = root.findViewById(R.id.recyclerView);
        // Truyền ữ liệu poster
        List<Integer> posters = Arrays.asList(R.drawable.ic_home,R.drawable.ic_calendar,R.drawable.ic_history);
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

        return root;
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
}