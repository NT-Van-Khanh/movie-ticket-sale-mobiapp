package com.example.ticket_sale.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ticket_sale.R;
import com.example.ticket_sale.util.DateUtil;

import java.util.Calendar;
import java.util.List;

public class DateAdapter extends RecyclerView.Adapter<DateAdapter.DateViewHolder> {
    private List<Calendar> dates;
    private final OnDateClickListener listener;
    private int selectedPosition = RecyclerView.NO_POSITION;

    public interface OnDateClickListener {
        void onDateClick(Calendar date);
    }

    public DateAdapter(List<Calendar> dates, OnDateClickListener listener) {
        this.dates = dates;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_date,parent,false);
        return new DateViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull DateViewHolder holder, int position) {
        Calendar date = dates.get(position);

        holder.txtDayMonth.setText(DateUtil.getDayMonthFromString(date));
        holder.txtDayOfWeek.setText(DateUtil.getDayOfWeek(date));

        holder.itemView.setSelected(selectedPosition == position);
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDateClick(date);
                notifyItemChanged(selectedPosition);
                selectedPosition = position;
                notifyItemChanged(selectedPosition);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dates.size();
    }

    public static class DateViewHolder extends RecyclerView.ViewHolder{
        TextView txtDayOfWeek;
        TextView txtDayMonth;
        public DateViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDayOfWeek = itemView.findViewById(R.id.txtDayOfWeek);
            txtDayMonth = itemView.findViewById(R.id.txtDayMonth);
        }
    }

    public void setDates(List<Calendar> newDates){
        this.dates = newDates;
        notifyDataSetChanged();
    }
}
