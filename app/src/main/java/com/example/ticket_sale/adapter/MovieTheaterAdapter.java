package com.example.ticket_sale.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ticket_sale.R;
import com.example.ticket_sale.model.MovieTheater;

import java.util.List;

public class MovieTheaterAdapter extends RecyclerView.Adapter<MovieTheaterAdapter.MovieTheaterViewHolder> {
    private final List<MovieTheater> movieTheaters;
    private final OnItemClickListener onItemClickListener;

    public MovieTheaterAdapter(List<MovieTheater> movieTheaters, OnItemClickListener onItemClickListener) {
        this.movieTheaters = movieTheaters;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public MovieTheaterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_theater,parent,false);
        return new MovieTheaterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieTheaterViewHolder holder, int position) {
        MovieTheater movieTheater = movieTheaters.get(position);
        holder.txtMovieTheaterName.setText(movieTheater.getName());
        holder.txtMovieTheaterAddress.setText(movieTheater.getAddress());
        holder.imgMovieTheaterImage.setImageResource(movieTheater.getImageResId());
    }

    @Override
    public int getItemCount() {
        return movieTheaters.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public class MovieTheaterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        ImageView imgMovieTheaterImage;
        TextView txtMovieTheaterName;
        TextView txtMovieTheaterAddress;

        public MovieTheaterViewHolder(@NonNull View itemView) {
            super(itemView);
            imgMovieTheaterImage = itemView.findViewById(R.id.imgMovieTheaterImage);
            txtMovieTheaterAddress = itemView.findViewById(R.id.txtMovieTheaterAddress);
            txtMovieTheaterName = itemView.findViewById(R.id.txtMovieTheaterName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(getAdapterPosition());
            }
        }

    }
}
