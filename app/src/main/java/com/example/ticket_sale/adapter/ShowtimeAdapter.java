package com.example.ticket_sale.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ticket_sale.R;
import com.example.ticket_sale.model.Showtime;

import java.util.List;

public class ShowtimeAdapter extends RecyclerView.Adapter<ShowtimeAdapter.ViewHolder> {
    private List<Showtime> showtimes;

    public ShowtimeAdapter(List<Showtime> showtimes) {
        this.showtimes = showtimes;
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
        return showtimes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        Button btnShowtime;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btnShowtime = itemView.findViewById(R.id.btnMovieShowtime);
        }
    }

    public void updateData(List<Showtime> newShowtimes) {
        this.showtimes.clear();
        this.showtimes.addAll(newShowtimes);
        notifyDataSetChanged(); // Cập nhật RecyclerView mà không cần tạo lại Adapter
    }
}
