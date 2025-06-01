package com.example.ticket_sale.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.transition.AutoTransition;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.example.ticket_sale.R;
import com.example.ticket_sale.model.Movie;
import com.example.ticket_sale.model.MovieFormat;
import com.example.ticket_sale.model.Theater;

import java.util.List;

public class TheaterShowtimeAdapter extends RecyclerView.Adapter<TheaterShowtimeAdapter.TheaterShowtimeViewHolder> {
    private List<Theater> theaters;
    private Movie movie;
    private final Context context;
    private final Drawable arrowDown;
    private final Drawable arrowRight;
    private final ShowtimeAdapter.OnShowtimeClickListener onShowtimeClickListener;

    public TheaterShowtimeAdapter(List<Theater> theaters, Movie movie, Context context,
                                  ShowtimeAdapter.OnShowtimeClickListener onShowtimeClickListener) {
        this.onShowtimeClickListener = onShowtimeClickListener;
        this.movie = movie;
        this.theaters = theaters;
        this.context = context;
        this.arrowDown = ContextCompat.getDrawable(context, R.drawable.ic_arrow_down);
        this.arrowRight = ContextCompat.getDrawable(context, R.drawable.ic_arrow_right);
    }

    @NonNull
    @Override
    public TheaterShowtimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_theater_showtime, parent, false);
        return new TheaterShowtimeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TheaterShowtimeViewHolder holder, int position) {
        Theater theater = theaters.get(position);
        if(movie == null || movie.getMovieFormats() == null) return;
        holder.bind(theater, movie.getMovieFormats());
        holder.setMovieFormatsVisibility(position == 0);
    }

    @Override
    public int getItemCount() {
        return theaters == null ? 0 : theaters.size();
    }

    public void setTheaters(List<Theater> theaters) {
        this.theaters = theaters;
        notifyDataSetChanged();
    }

    public class TheaterShowtimeViewHolder extends ViewHolder{
        private final TextView txtTheaterName;
        private RecyclerView rcViewMovieFormats;
        private MovieFormatAdapter movieFormatAdapter;

        public TheaterShowtimeViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTheaterName  = itemView.findViewById(R.id.txtTheaterName);
            txtTheaterName.setOnClickListener(v -> {
                if (rcViewMovieFormats.getVisibility() == View.VISIBLE) {
                    setMovieFormatsVisibility(false);
                } else {
                    setMovieFormatsVisibility(true);
                }
            });
            rcViewMovieFormats = itemView.findViewById(R.id.rcViewMovieFormats);
            rcViewMovieFormats.setLayoutManager(new LinearLayoutManager(itemView.getContext(),
                                                    LinearLayoutManager.VERTICAL,false));
        }

        void bind(Theater theater, List<MovieFormat> formats) {
            if(theater == null) return;
            txtTheaterName.setText(theater.getName());
            if (movieFormatAdapter == null) {
                movieFormatAdapter = new MovieFormatAdapter(formats,  getAdapterPosition(), context,
                        onShowtimeClickListener);
                rcViewMovieFormats.setAdapter(movieFormatAdapter);
            } else {
                movieFormatAdapter.updateData(formats, getAdapterPosition());
            }
        }

        public void setMovieFormatsVisibility(boolean status){
            ViewGroup parent = (ViewGroup) rcViewMovieFormats.getParent();
            Transition transition = new AutoTransition();
            transition.setDuration(200);
            TransitionManager.beginDelayedTransition(parent, transition);

            if(status){
                txtTheaterName.setCompoundDrawablesWithIntrinsicBounds(null, null,arrowDown, null );
                rcViewMovieFormats.setVisibility(View.VISIBLE);
            }else{
                txtTheaterName.setCompoundDrawablesWithIntrinsicBounds(null, null,arrowRight, null );
                rcViewMovieFormats.setVisibility(View.GONE);
            }
        }
    }
}
