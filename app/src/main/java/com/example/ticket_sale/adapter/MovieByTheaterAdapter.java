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
import com.example.ticket_sale.model.MovieByTheater;

import java.util.List;

public class MovieByTheaterAdapter extends Adapter<MovieByTheaterAdapter.MovieByTheaterViewHolder> {
    private List<MovieByTheater> moviesByTheater;
    private ShowtimeAdapter.OnShowtimeClickListener onShowtimeClickListener;

    public MovieByTheaterAdapter(List<MovieByTheater> moviesByTheater, ShowtimeAdapter.OnShowtimeClickListener onShowtimeClickListener) {
        this.moviesByTheater = moviesByTheater;
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
        MovieByTheater movieByTheater  = moviesByTheater.get(position);
        Log.d("MovieAdapter", "Num of movie: " + moviesByTheater.size());
        holder.imgMovieImage.setImageResource(movieByTheater.getImageResId());
        holder.txtMovieTitle.setText(movieByTheater.getTitle());
        holder.txtMovieAge.setText(movieByTheater.getAge().toString());
        holder.txtMovieRating.setText(movieByTheater.getRating().toString());
        holder.txtMovieDuration.setText(movieByTheater.getTime().toString() + " phút");

        MovieFormatAdapter movieFormatAdapter = (MovieFormatAdapter) holder.rcViewMovieFormats.getAdapter();
        if(movieFormatAdapter == null){
                Log.e("movieFormatAdapter","adapter is null");
                movieFormatAdapter = new MovieFormatAdapter(movieByTheater.getMovieFormats(),holder.getAdapterPosition(), onShowtimeClickListener);
                holder.rcViewMovieFormats.setAdapter(movieFormatAdapter);
        }else{
            movieFormatAdapter.updateData(movieByTheater.getMovieFormats(),holder.getAdapterPosition());
        }
    }

    @Override
    public int getItemCount() {
        return moviesByTheater.size();
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
    }
}
