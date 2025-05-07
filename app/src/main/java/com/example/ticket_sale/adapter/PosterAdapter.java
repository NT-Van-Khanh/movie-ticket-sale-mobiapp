package com.example.ticket_sale.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ticket_sale.R;
import com.example.ticket_sale.data.dto.Slider;

import java.util.List;

public class PosterAdapter extends RecyclerView.Adapter<PosterAdapter.PosterViewHolder> {
    private List<Slider> posters;
    private Context context;
    public PosterAdapter(List<Slider> posters){
        this.posters = posters;
    }

    @NonNull
    @Override
    public PosterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_movie, parent, false);
        return new PosterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PosterViewHolder holder, int position) {
        Slider slider = posters.get(position);
        Glide.with(holder.itemView.getContext())
                .load(slider.getImageLink())
                .placeholder(slider.getImageResId()) // ảnh tạm thời
                .error(slider.getImageResId())             // ảnh khi lỗi
                .into(holder.imageView);
//        holder.imageView.setImageResource(slider.getImageLink());
        // Xử lý sự kiện khi click vào slide
//        holder.imageView.setOnClickListener(v -> {
//            Intent intent = new Intent(context, DetailActivity.class);
//            intent.putExtra("posterId", posterId);
//            context.startActivity(intent);
//        });
    }

    @Override
    public int getItemCount() {
        return posters.size();
    }

    public static class PosterViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public PosterViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgMovie);
        }
    }

    public void setPosters(List<Slider> posters){
        this.posters = posters;
        notifyDataSetChanged();
    }
}
