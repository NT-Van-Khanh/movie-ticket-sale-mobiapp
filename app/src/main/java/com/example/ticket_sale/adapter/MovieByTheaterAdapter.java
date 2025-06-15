package com.example.ticket_sale.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.bumptech.glide.Glide;
import com.example.ticket_sale.R;
import com.example.ticket_sale.model.Movie;
import com.example.ticket_sale.util.ViLocaleUtil;

import java.util.List;

public class MovieByTheaterAdapter extends Adapter<MovieByTheaterAdapter.MovieByTheaterViewHolder> {
    private List<Movie> movies;
    private ShowtimeAdapter.OnShowtimeClickListener onShowtimeClickListener;
    private Context context;
    public MovieByTheaterAdapter(List<Movie> movies, Context context,
                                 ShowtimeAdapter.OnShowtimeClickListener onShowtimeClickListener) {
        this.movies = movies;
        this.context = context;
        this.onShowtimeClickListener = onShowtimeClickListener;
    }

    @NonNull
    @Override
    public MovieByTheaterAdapter.MovieByTheaterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_by_theater,parent,false);
        return new MovieByTheaterViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieByTheaterAdapter.MovieByTheaterViewHolder holder, int position) {
        Movie movie  = movies.get(position);
        holder.bind(movie);
//        Log.d("MovieAdapter", "Num of movie: " + movies.size());

    }

    @Override
    public int getItemCount() {
        return movies == null ? 0 : movies.size();
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    public class MovieByTheaterViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imgMovieImage;
        private final TextView txtMovieTitle;
        private final TextView txtMovieAge;
        private final TextView txtMovieDuration;
        private final TextView txtMovieRating;
        private final RecyclerView rcViewMovieFormats;

        public MovieByTheaterViewHolder(@NonNull View itemView) {
            super(itemView);
            imgMovieImage = itemView.findViewById(R.id.imgMovieImage);
            txtMovieTitle = itemView.findViewById(R.id.txtMovieTitle);
            txtMovieAge = itemView.findViewById(R.id.txtMovieAge);
            txtMovieDuration = itemView.findViewById(R.id.txtMovieDuration);
            txtMovieRating = itemView.findViewById(R.id.txtMovieRating);
            rcViewMovieFormats = itemView.findViewById(R.id.rcViewMovieFormats);
            rcViewMovieFormats.setLayoutManager(new LinearLayoutManager(itemView.getContext(),
                                                    LinearLayoutManager.VERTICAL,false));
        }

        public void bind(Movie movie) {
            Glide.with(itemView.getContext())
                    .load(movie.getImageLink())
                    .placeholder(R.drawable.mv_dai_chien_nguoi_khong_lo)
                    .error(movie.getImageResId())
                    .into(imgMovieImage);
            txtMovieTitle.setText(movie.getTitle());
            txtMovieAge.setText(String.valueOf(movie.getAge()));
            txtMovieRating.setText(String.valueOf(movie.getRating()));
            txtMovieDuration.setText(String.format(ViLocaleUtil.localeVN,"%d phút", movie.getDuration()));

            MovieFormatAdapter movieFormatAdapter = (MovieFormatAdapter) rcViewMovieFormats.getAdapter();
            if(movieFormatAdapter == null){
                movieFormatAdapter = new MovieFormatAdapter(movie.getMovieFormats(),
                                            getAdapterPosition(),context, onShowtimeClickListener);
                rcViewMovieFormats.setAdapter(movieFormatAdapter);
            }else{
                movieFormatAdapter.updateData(movie.getMovieFormats(), getAdapterPosition());
            }
        }

    }
}
