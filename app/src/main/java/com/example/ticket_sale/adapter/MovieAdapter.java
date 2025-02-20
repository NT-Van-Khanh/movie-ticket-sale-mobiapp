package com.example.ticket_sale.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ticket_sale.R;
import com.example.ticket_sale.model.Movie;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private final List<Movie> movies;

    public MovieAdapter(List<Movie> movies) {
        this.movies = movies;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item,parent,false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.movieImage.setImageResource(movie.getImageResId());
        holder.movieTitle.setText(movie.getTitle());
        holder.movieAge.setText(movie.getAge().toString());
        holder.movieDuration.setText(movie.getTime().toString() + " phút");
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView movieImage;
        TextView movieTitle;
        TextView movieDuration;
        TextView movieAge;
        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            movieImage = itemView.findViewById(R.id.imgMovieImage);
            movieTitle =itemView.findViewById(R.id.txtMovieTitle);
            movieDuration = itemView.findViewById(R.id.txtMovieDuration);
            movieAge = itemView.findViewById(R.id.txtMovieAge);
        }
    }
}
