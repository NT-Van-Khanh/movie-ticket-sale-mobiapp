package com.example.ticket_sale.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.example.ticket_sale.R;
import com.example.ticket_sale.model.Movie;

import java.util.List;

public class MovieByTheaterAdapter extends Adapter<MovieByTheaterAdapter.MovieByTheaterViewHolder> {
    private List<Movie> movies;
    private ShowtimeAdapter.OnShowtimeClickListener onShowtimeClickListener;

    public MovieByTheaterAdapter(List<Movie> movies, ShowtimeAdapter.OnShowtimeClickListener onShowtimeClickListener) {
        this.movies = movies;
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
        Log.d("MovieAdapter", "Num of movie: " + movies.size());

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class MovieByTheaterViewHolder extends RecyclerView.ViewHolder {
        ImageView imgMovieImage;
        TextView txtMovieTitle;
        TextView txtMovieAge;
        TextView txtMovieDuration;
        TextView txtMovieRating;

        RecyclerView rcViewMovieFormats;

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
            imgMovieImage.setImageResource(movie.getImageResId());
            txtMovieTitle.setText(movie.getTitle());
            txtMovieAge.setText(movie.getAge().toString());
            txtMovieRating.setText(movie.getRating().toString());
            txtMovieDuration.setText(movie.getDuration().toString() + " phút");

            MovieFormatAdapter movieFormatAdapter = (MovieFormatAdapter) rcViewMovieFormats.getAdapter();
            if(movieFormatAdapter == null){
                Log.e("movieFormatAdapter","adapter is null");
                movieFormatAdapter = new MovieFormatAdapter(movie.getMovieFormats(), getAdapterPosition(), onShowtimeClickListener);
                rcViewMovieFormats.setAdapter(movieFormatAdapter);
            }else{
                movieFormatAdapter.updateData(movie.getMovieFormats(), getAdapterPosition());
            }
        }
    }
}
