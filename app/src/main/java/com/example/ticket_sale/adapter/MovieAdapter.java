package com.example.ticket_sale.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ticket_sale.R;
import com.example.ticket_sale.model.Movie;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private List<Movie> movies;
    private OnItemClickListener onItemClickListener;

    public MovieAdapter(List<Movie> movies, OnItemClickListener listener) {
        this.movies = movies;
        this.onItemClickListener = listener;
    }
    public interface OnItemClickListener {
        void onItemClick(Movie movie);
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
        holder.imgMovieImage.setImageResource(movie.getImageResId());
        holder.txtMovieTitle.setText(movie.getTitle());
        holder.txtMovieAge.setText(movie.getAge().toString());
        holder.txtMovieDuration.setText(movie.getDuration().toString() + " phút");
        holder.txtMovieRating.setText(movie.getRating().toString());
        holder.bind(movie, onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView imgMovieImage;
        TextView txtMovieTitle;
        TextView txtMovieDuration;
        TextView txtMovieAge;
        TextView txtMovieRating;
        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imgMovieImage = itemView.findViewById(R.id.imgMovieImage);
            txtMovieTitle =itemView.findViewById(R.id.txtMovieTitle);
            txtMovieDuration = itemView.findViewById(R.id.txtMovieDuration);
            txtMovieAge = itemView.findViewById(R.id.txtMovieAge);
            txtMovieRating = itemView.findViewById(R.id.txtMovieRating);
        }

        public void bind(final Movie movie, final OnItemClickListener listener) {
            Glide.with(itemView.getContext()).load(movie.getImageResId()).into(imgMovieImage);
            if (listener == null) {
                Log.e("MovieAdapter", "Listener is null!");
            }
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemClick(movie);
                }
            });
        }
    }
    public void setMovies(List<Movie> newMovies) {
        this.movies = newMovies;
        notifyDataSetChanged(); // hoặc dùng DiffUtil để tối ưu
    }
}
