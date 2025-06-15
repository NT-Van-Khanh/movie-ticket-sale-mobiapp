package com.example.ticket_sale.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ticket_sale.R;
import com.example.ticket_sale.model.Order;
import com.example.ticket_sale.model.Screen;
import com.example.ticket_sale.model.Showtime;

import java.util.List;

public class BookingHistoryAdapter extends RecyclerView.Adapter<BookingHistoryAdapter.ViewHolder> {
    private List<Order> orders;
    private final OnItemClickListener onItemClickListener;

    public BookingHistoryAdapter(List<Order> orders, OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        this.orders = orders;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_booking_history,parent, false);
        return new ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order order = orders.get(position);
        holder.bind(order);
    }

    @Override
    public int getItemCount() {
        return orders == null ? 0 : orders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView txtMovieTitle;
        private TextView txtTheaterAddress;
        private TextView txtMovieShowtime;
        private TextView txtTheaterScreen;
        private TextView txtTheaterSeats;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMovieTitle = itemView.findViewById(R.id.txtMovieTitle);
            txtMovieShowtime =itemView.findViewById(R.id.txtMovieShowtime);
            txtTheaterAddress = itemView.findViewById(R.id.txtTheaterAddress);
            txtTheaterScreen = itemView.findViewById(R.id.txtTheaterScreen);
            txtTheaterSeats = itemView.findViewById(R.id.txtTheaterSeats);
            itemView.setOnClickListener(this);
        }

        public void bind(Order order) {
            Showtime showtime = order.getShowtime();
            Screen screen = order.getScreen();

            txtMovieTitle.setText(order.getMovie().getTitle());
            txtMovieShowtime.setText(String.format("%s - %s, %s",showtime.getTimeStart(), showtime.getTimeEnd(), showtime.getDate()));
            txtTheaterAddress.setText(screen.getTheater().getName());
            txtTheaterScreen.setText(screen.getName());
            txtTheaterSeats.setText(order.getSelectedSeatsString());
        }

        @Override
        public void onClick(View v) {
            if(onItemClickListener != null){
                onItemClickListener.onItemClick(getAdapterPosition());
            }
        }
    }
    public void setBookingHistories(List<Order> orders){
        this.orders = orders;
        notifyDataSetChanged();
    }
}
