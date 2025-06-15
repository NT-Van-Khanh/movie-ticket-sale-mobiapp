package com.example.ticket_sale.adapter;

import android.content.IntentFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ticket_sale.R;

import java.util.List;

public class SearchHistoryAdapter extends RecyclerView.Adapter<SearchHistoryAdapter.SearchHistoryViewHolder> {
    private List<String> searchHistories;
    private final OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public SearchHistoryAdapter(List<String> searchHistories, OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        this.searchHistories = searchHistories;
    }

    @NonNull
    @Override
    public SearchHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_history, parent, false);
        return new SearchHistoryViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchHistoryViewHolder holder, int position) {
        String keyword = searchHistories.get(position);
        holder.bind(keyword);
    }

    @Override
    public int getItemCount() {
        return searchHistories == null ? 0 : searchHistories.size();
    }

    public class SearchHistoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView txtSearchKeyword;

        public SearchHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            txtSearchKeyword = itemView.findViewById(R.id.txtSearchKeyword);
            itemView.setOnClickListener(this);
        }
        void bind( String searchKeyword){
            txtSearchKeyword.setText(searchKeyword);
        }

        @Override
        public void onClick(View v) {
            if(onItemClickListener != null){
                onItemClickListener.onItemClick(getAdapterPosition());
            }
        }
    }

    public void setSearchHistories(List<String> searchHistories){
        this.searchHistories = searchHistories;
        notifyDataSetChanged();
    }
}
