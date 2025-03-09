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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie,parent,false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.txtMovieImage.setImageResource(movie.getImageResId());
        holder.txtMovieTitle.setText(movie.getTitle());
        holder.txtMovieAge.setText(movie.getAge().toString());
        holder.txtMovieDuration.setText(movie.getTime().toString() + " phút");
        holder.txtMovieRating.setText(movie.getRating().toString());
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView txtMovieImage;
        TextView txtMovieTitle;
        TextView txtMovieDuration;
        TextView txtMovieAge;
        TextView txtMovieRating;
        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMovieImage = itemView.findViewById(R.id.imgMovieImage);
            txtMovieTitle =itemView.findViewById(R.id.txtMovieTitle);
            txtMovieDuration = itemView.findViewById(R.id.txtMovieDuration);
            txtMovieAge = itemView.findViewById(R.id.txtMovieAge);
            txtMovieRating = itemView.findViewById(R.id.txtMovieRating);
        }
    }
}
