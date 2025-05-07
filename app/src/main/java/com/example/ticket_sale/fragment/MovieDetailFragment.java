package com.example.ticket_sale.fragment;

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
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;


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
//    VideoView vidMovieTrailer;
    YouTubePlayerView vidMovieTrailer;
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
        vidMovieTrailer.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(YouTubePlayer youTubePlayer) {
                String videoId = extractVideoIdFromUrl(movie.getTrailerLink());
                youTubePlayer.loadVideo(videoId, 0);
            }
        });
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
        getLifecycle().addObserver(vidMovieTrailer);
    }
    private String extractVideoIdFromUrl(String url) {
        if (url.contains("/embed/")) {
            return url.substring(url.lastIndexOf("/embed/") + 7, url.indexOf("?"));
        }
        if (url.contains("youtu.be")) {
            return url.substring(url.lastIndexOf("/") + 1, url.indexOf("?"));
        }
        return "";
    }
}