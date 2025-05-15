package com.example.ticket_sale.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ticket_sale.R;
import com.example.ticket_sale.util.ViLocaleUtil;

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
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_date, parent,false);
        return new DateViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull DateViewHolder holder, int position) {
        Calendar date = dates.get(position);
        holder.bind(date);
        holder.itemView.setSelected(selectedPosition == position);
        holder.itemView.setOnClickListener(v -> {
            int adapterPosition = holder.getAdapterPosition();
//            if (adapterPosition != RecyclerView.NO_POSITION && listener != null && selectedPosition != adapterPosition) {
//                int previousPosition = selectedPosition;
//                selectedPosition = adapterPosition;
//                notifyItemChanged(previousPosition);
//                notifyItemChanged(selectedPosition);
//                listener.onDateClick(dates.get(selectedPosition));
//            }
            if (selectedPosition == adapterPosition) {
                // Bỏ chọn
                int prevPos = selectedPosition;
                selectedPosition = RecyclerView.NO_POSITION;
                notifyItemChanged(prevPos);
            } else {
                int prevPos = selectedPosition;
                selectedPosition = adapterPosition;
                notifyItemChanged(prevPos);
                notifyItemChanged(selectedPosition);
            }
            if (listener != null) listener.onDateClick(date);
        });

    }

    @Override
    public int getItemCount() {
        return dates.size();
    }

    public static class DateViewHolder extends RecyclerView.ViewHolder{
        private TextView txtDayOfWeek;
        private TextView txtDayMonth;
        public DateViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDayOfWeek = itemView.findViewById(R.id.txtDayOfWeek);
            txtDayMonth = itemView.findViewById(R.id.txtDayMonth);
        }

        void bind(Calendar date){
            txtDayMonth.setText(ViLocaleUtil.getDayMonthFromString(date));
            txtDayOfWeek.setText(ViLocaleUtil.getDayOfWeek(date));
        }
    }

    public void setDates(List<Calendar> newDates){
        this.dates = newDates;
        notifyDataSetChanged();
    }


}
