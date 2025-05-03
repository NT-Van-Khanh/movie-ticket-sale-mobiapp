package com.example.ticket_sale.fragment;

import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.example.ticket_sale.R;
import com.example.ticket_sale.model.Movie;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MovieDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieDetailFragment extends Fragment {
    TextView txtGoBack;
    ImageView imgMoviePoster;
    TextView txtMovieRating;
    TextView txtMovieTitle;
    TextView txtMovieAge;
    TextView txtMovieDuration;
    TextView txtMovieOpeningDate;

    TextView txtMovieGenre;
    TextView txtMovieDirector;
    TextView txtMovieActor;
    TextView txtMovieFormat; //movie format

    TextView txtMovieContent;
    VideoView vidMovieTrailer;
    private Movie movie;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MovieDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MovieDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MovieDetailFragment newInstance(String param1, String param2) {
        MovieDetailFragment fragment = new MovieDetailFragment();
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
        View root = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        initView(root);
        setDataForView();

        return root;
    }

    private void setDataForView(){
        if(getArguments() == null) return;
        movie = getArguments().getParcelable("movie");
        if (movie == null) return;

        imgMoviePoster.setImageResource(movie.getImageResId());
        txtMovieTitle.setText(movie.getTitle());
        txtMovieAge.setText(String.format("T%d",movie.getAge()));
        txtMovieDuration.setText(String.format("%d phút",movie.getDuration()));
        txtMovieOpeningDate.setText(movie.getOpeningDate() == null ? "Đang chiếu" : movie.getOpeningDate().toString());
//        txtMovieGenre.setText(movie.getGenre());
        txtMovieActor.setText(movie.getActor());
        txtMovieDirector.setText(movie.getDirector());
//        txtMovieFormat.setText(movie.getMovieFormats());
        txtMovieContent.setText(movie.getDescription());
    }

    private void initView(View root){
        txtGoBack = root.findViewById(R.id.txtGoBack);
        txtGoBack.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());

        imgMoviePoster = root.findViewById(R.id.imgMoviePoster);
        txtMovieTitle = root.findViewById(R.id.txtMovieTitle);
        txtMovieAge = root.findViewById(R.id.txtMovieAge);
        txtMovieDuration = root.findViewById(R.id.txtMovieDuration);
        txtMovieOpeningDate = root.findViewById(R.id.txtMovieOpeningDate);
        txtMovieGenre = root.findViewById(R.id.txtMovieGenre);
        txtMovieActor = root.findViewById(R.id.txtMovieActor);
        txtMovieDirector = root.findViewById(R.id.txtMovieDirector);
        txtMovieFormat = root.findViewById(R.id.txtMovieFormat);
        txtMovieContent = root.findViewById(R.id.txtMovieContent);
        vidMovieTrailer = root.findViewById(R.id.vidMovieTrailer);
    }

}