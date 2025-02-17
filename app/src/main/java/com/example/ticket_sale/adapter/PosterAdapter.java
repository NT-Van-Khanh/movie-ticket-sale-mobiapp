package com.example.ticket_sale.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ticket_sale.R;

import java.util.List;

public class PosterAdapter extends RecyclerView.Adapter<PosterAdapter.PosterViewHolder> {
    private final List<Integer> posterLinks;
    private Context context;
    public PosterAdapter(List<Integer> posterLinks){
        this.posterLinks = posterLinks;
    }

    @NonNull
    @Override
    public PosterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_movie, parent, false);
        return new PosterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PosterViewHolder holder, int position) {
        Integer posterId = posterLinks.get(position);
        holder.imageView.setImageResource(posterId);
        // Xử lý sự kiện khi click vào slide
//        holder.imageView.setOnClickListener(v -> {
//            Intent intent = new Intent(context, DetailActivity.class);
//            intent.putExtra("posterId", posterId);
//            context.startActivity(intent);
//        });
    }

    @Override
    public int getItemCount() {
        return posterLinks.size();
    }

    public static class PosterViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public PosterViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgMovie);
        }
    }
}
