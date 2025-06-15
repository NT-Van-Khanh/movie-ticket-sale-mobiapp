package com.example.ticket_sale.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ticket_sale.R;
import com.example.ticket_sale.fragment.ChooseSeatFragment;
import com.example.ticket_sale.model.MovieFormat;
import com.example.ticket_sale.model.Showtime;

import java.util.ArrayList;
import java.util.List;

public class MovieFormatAdapter extends RecyclerView.Adapter<MovieFormatAdapter.MovieFormatViewHolder> {
    private List<MovieFormat> movieFormats;
    private final ShowtimeAdapter.OnShowtimeClickListener onShowtimeClickListener;
    private int movieIndex;
//    private final Drawable arrowRight;
//    private final Drawable arrowDown;
    public MovieFormatAdapter(List<MovieFormat> movieFormats,int movieIndex, Context context,
                              ShowtimeAdapter.OnShowtimeClickListener onShowtimeClickListener) {
        this.movieFormats = movieFormats;
        this.onShowtimeClickListener = onShowtimeClickListener;
        this.movieIndex = movieIndex;
//        this.arrowDown = ContextCompat.getDrawable(context, R.drawable.ic_arrow_down);
//        this.arrowRight = ContextCompat.getDrawable(context, R.drawable.ic_arrow_right);
    }

    @NonNull
    @Override
    public MovieFormatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_format,
                parent, false);
        return new MovieFormatViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieFormatViewHolder holder, int position) {
        MovieFormat movieFormat = movieFormats.get(position);
        if (movieFormat == null) return;
        holder.bind(movieFormat);

    }

    @Override
    public int getItemCount() {
        return movieFormats == null ? 0 : movieFormats.size();
    }

    public class MovieFormatViewHolder extends RecyclerView.ViewHolder {
        private final TextView txtMovieFormatName;
        private final RecyclerView rcViewMovieShowtimes;

        public MovieFormatViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMovieFormatName = itemView.findViewById(R.id.txtMovieFormatName);
            rcViewMovieShowtimes = itemView.findViewById(R.id.rcViewMovieShowtimes);
            int spanCount = calculateSpanCount(itemView.getContext(), 65);
            rcViewMovieShowtimes.setLayoutManager(new GridLayoutManager(itemView.getContext(),5));
//            txtMovieFormatName.setOnClickListener(v ->{
//                if(rcViewMovieShowtimes.getVisibility() == View.VISIBLE){
//                    setMovieFormatsVisibility(false);
//                }else{
//                    setMovieFormatsVisibility(true);
//                }
//            });
//            txtMovieFormatName.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, arrowRight, null);
        }

        void bind(MovieFormat movieFormat ){
            txtMovieFormatName.setText(movieFormat.getName());
            ShowtimeAdapter showtimeAdapter = (ShowtimeAdapter) rcViewMovieShowtimes.getAdapter();
            if(showtimeAdapter == null){
                showtimeAdapter = new ShowtimeAdapter(movieFormat.getShowtimes(), movieIndex, getAdapterPosition(), onShowtimeClickListener);
                rcViewMovieShowtimes.setAdapter(showtimeAdapter);
            }else{
                showtimeAdapter.updateData(movieFormat.getShowtimes(), movieIndex, getAdapterPosition());
            }
//            if(movieIndex == 0){
//                setMovieFormatsVisibility(true);
//            }else{
//                setMovieFormatsVisibility(false);
//            }
        }

        public void setMovieFormatsVisibility(boolean status){
            ViewGroup parent = (ViewGroup) rcViewMovieShowtimes.getParent();
            Transition transition = new AutoTransition();
            transition.setDuration(200);
            TransitionManager.beginDelayedTransition(parent, transition);

            if(status){
//                txtMovieFormatName.setCompoundDrawablesWithIntrinsicBounds(null, null,arrowDown, null );
                rcViewMovieShowtimes.setVisibility(View.VISIBLE);
            }else{
//                txtMovieFormatName.setCompoundDrawablesWithIntrinsicBounds(null, null,arrowRight, null );
                rcViewMovieShowtimes.setVisibility(View.GONE);
            }
        }
    }

    public void updateData(List<MovieFormat> newMovieFormats, int movieIndex){
        this.movieFormats = new ArrayList<>(newMovieFormats);
        this.movieIndex = movieIndex;
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
