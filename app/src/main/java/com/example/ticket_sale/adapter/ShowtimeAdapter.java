package com.example.ticket_sale.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ticket_sale.R;
import com.example.ticket_sale.model.Showtime;

import java.util.List;

public class ShowtimeAdapter extends RecyclerView.Adapter<ShowtimeAdapter.ViewHolder> {
    private List<Showtime> showtimes;
    private final OnShowtimeClickListener onShowtimeClickListener;
    private int movieIndex;
    private int formatIndex;
    public ShowtimeAdapter(List<Showtime> showtimes,int movieIndex, int formatIndex, OnShowtimeClickListener onShowtimeClickListener) {
        this.showtimes = showtimes;
        this.onShowtimeClickListener = onShowtimeClickListener;
        this.movieIndex = movieIndex;
        this.formatIndex = formatIndex;
    }

    @NonNull
    @Override
    public ShowtimeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_showtime,parent,false);
        return new ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowtimeAdapter.ViewHolder holder, int position) {
        Showtime showtime = showtimes.get(position);
        holder.btnShowtime.setText(showtime.getTimeStart());
    }

    @Override
    public int getItemCount() {
        return showtimes == null ? 0 : showtimes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final Button btnShowtime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btnShowtime = itemView.findViewById(R.id.btnMovieShowtime);
            btnShowtime.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onShowtimeClickListener != null) {
                onShowtimeClickListener.onItemClick(movieIndex, formatIndex, getAdapterPosition());
            }
        }
    }

    public interface  OnShowtimeClickListener{
        void onItemClick(int movieByTheaterPosition, int formatPosition, int showtimePosition);
    }

    public void updateData(List<Showtime> newShowtimes, int movieIndex, int formatIndex) {
        this.showtimes.clear();
        this.showtimes.addAll(newShowtimes);
        this.movieIndex = movieIndex;
        this.formatIndex = formatIndex;
        notifyDataSetChanged(); // Cập nhật RecyclerView mà không cần tạo lại Adapter
    }
}
