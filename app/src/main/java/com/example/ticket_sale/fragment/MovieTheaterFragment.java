package com.example.ticket_sale.fragment;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.ticket_sale.R;
import com.example.ticket_sale.adapter.MovieTheaterAdapter;
import com.example.ticket_sale.model.MovieTheater;

import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MovieTheaterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieTheaterFragment extends Fragment {
    private Button btnChooseProvince;
    private RecyclerView rcViewMovieTheaters;

    private MovieTheaterAdapter movieTheaterAdapter;

    private List<MovieTheater> movieTheaters;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MovieTheaterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MovieTheaterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MovieTheaterFragment newInstance(String param1, String param2) {
        MovieTheaterFragment fragment = new MovieTheaterFragment();
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
        View root = inflater.inflate(R.layout.fragment_movie_theater,container,false);
        movieTheaters = getExampleMovieTheaters();
        init(root);

        movieTheaterAdapter = new MovieTheaterAdapter(movieTheaters, this::onItemClick);

        rcViewMovieTheaters.setAdapter(movieTheaterAdapter);

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    void init(View view){
        rcViewMovieTheaters = view.findViewById(R.id.rcViewMovieTheaters);
        btnChooseProvince = view.findViewById(R.id.btnChooseProvince);

        rcViewMovieTheaters.setLayoutManager(new GridLayoutManager(getContext(), 1));
        initBtnChooseProvince();
    }

    private List<MovieTheater> getExampleMovieTheaters(){

        MovieTheater mt1 = new MovieTheater("MT1","Rạp Linh Trung, Thủ Đức","Rạp Linh Trung, Hồ Chí Minh",R.drawable.cinema1,0);
        MovieTheater mt2 = new MovieTheater("MT2","Rạp Thảo Điền, Thủ Đức","Xuân Thủy, Thảo Điền, Thủ Đức, Hồ Chí Minh",R.drawable.cinema2,0);
        MovieTheater mt3 = new MovieTheater("MT3","Rạp Bến Tre","Nguyễn Huệ, Phường 4, Bến Tre",R.drawable.cinema3,1);
        MovieTheater mt4 = new MovieTheater("MT4","Rạp Mỹ Tho","Lý Thường Kiệt, Phường 5, Mỹ Tho, Tiền Giang",R.drawable.cinema4,1);
        MovieTheater mt5 = new MovieTheater("MT5","Rạp Bến Nghé","Đinh Tiên Hoàng, Bến Nghé, Quận 1, Hồ Chí Minh",R.drawable.cinema5,1);
        MovieTheater mt6 = new MovieTheater("MT6","Rạp Trương Định","Trương Định, Phường Võ Thị Sáu, Quận 3, Hồ Chí Minh",R.drawable.cinema6,1);
        return Arrays.asList(mt1,mt2,mt3,mt4,mt5,mt6);
    }
    private void initBtnChooseProvince(){
        String[] provinces = getResources().getStringArray(R.array.provinces);
        btnChooseProvince.setOnClickListener(v->{
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle("Chọn Tỉnh thành")
                    .setItems(provinces,(dialog, which) -> {
                        btnChooseProvince.setText(provinces[which]);
                    }).show();
        });
    }

    private void onItemClick(int position) {
//        Toast.makeText(getContext(), "Bạn chọn: " + movieTheaters.get(position).getName(), Toast.LENGTH_SHORT).show();
        MovieTheater movieTheater = movieTheaters.get(position);
        MovieShowtimeFragment movieShowtimeFragment = new MovieShowtimeFragment();

        Bundle bundle  = new Bundle();
        bundle.putParcelable("theater",movieTheater);
//        bundle.putString("theaterId",movieTheater.getId());
//        bundle.putString("theaterName",movieTheater.getName());
//        bundle.putString("theaterAddress",movieTheater.getAddress());
        movieShowtimeFragment.setArguments(bundle);
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container,movieShowtimeFragment)
                .addToBackStack(null)
                .commit();
    }
}