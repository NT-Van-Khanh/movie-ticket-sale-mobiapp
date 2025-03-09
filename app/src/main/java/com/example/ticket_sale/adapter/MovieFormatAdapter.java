package com.example.ticket_sale.adapter;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ticket_sale.R;
import com.example.ticket_sale.model.MovieFormat;
import com.example.ticket_sale.model.Showtime;

import java.util.List;

public class MovieFormatAdapter extends RecyclerView.Adapter<MovieFormatAdapter.MovieFormatViewHolder> {
    private List<MovieFormat> movieFormats;

    public MovieFormatAdapter(List<MovieFormat> movieFormats) {
        this.movieFormats = movieFormats;
    }

    @NonNull
    @Override
    public MovieFormatAdapter.MovieFormatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_format,parent,false);
        return new MovieFormatViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieFormatAdapter.MovieFormatViewHolder holder, int position) {
        MovieFormat movieFormat = movieFormats.get(position);
        if (movieFormat == null) return;
        holder.txtMovieFormatName.setText(movieFormat.getName());
        ShowtimeAdapter showtimeAdapter = (ShowtimeAdapter) holder.rcMovieShowtimes.getAdapter();
        if(showtimeAdapter == null){
            showtimeAdapter = new ShowtimeAdapter(movieFormat.getShowtimes());
            holder.rcMovieShowtimes.setAdapter(showtimeAdapter);
        }else{
            showtimeAdapter.updateData(movieFormat.getShowtimes());
        }

    }

    @Override
    public int getItemCount() {
        return movieFormats.size();
    }

    public class MovieFormatViewHolder extends RecyclerView.ViewHolder {
        TextView txtMovieFormatName;
        RecyclerView rcMovieShowtimes;
        public MovieFormatViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMovieFormatName = itemView.findViewById(R.id.txtMovieFormatName);
            rcMovieShowtimes = itemView.findViewById(R.id.rcViewMovieShowtimes);
            int spanCount = calculateSpanCount(itemView.getContext(), 65);
            rcMovieShowtimes.setLayoutManager(new GridLayoutManager(itemView.getContext(),5));

        }
    }

    public void updateData(List<MovieFormat> newMovieFormats){
        this.movieFormats.clear();
        this.movieFormats.addAll(newMovieFormats);
        notifyDataSetChanged();
    }

    public int calculateSpanCount(Context context,  int itemMinWidthDp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        // Chuyển đổi itemMinWidth từ dp sang pixel
        int itemMinWidthPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                itemMinWidthDp,
                displayMetrics);

        int screenWidthPx = displayMetrics.widthPixels;

        return Math.max(1, screenWidthPx / itemMinWidthPx);// Đảm bảo có ít nhất 1 cột
    }
}
