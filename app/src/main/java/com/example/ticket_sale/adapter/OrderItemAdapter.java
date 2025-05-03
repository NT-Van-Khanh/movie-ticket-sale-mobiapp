package com.example.ticket_sale.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ticket_sale.R;
import com.example.ticket_sale.model.OrderDisplayItem;
import com.example.ticket_sale.util.debug.MoneyUtil;

import java.text.DecimalFormat;
import java.util.List;

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.OrderItemViewHolder> {
    private final List<OrderDisplayItem> orderItems;

    public OrderItemAdapter(List<OrderDisplayItem>  orderItems) {
        this.orderItems = orderItems;
    }

    public  interface  OnItemClickListener{
        void onClick();
    }
    @NonNull
    @Override
    public OrderItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_item, parent, false);
        return new OrderItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderItemViewHolder holder, int position) {
        OrderDisplayItem orderItem = orderItems.get(position);
        holder.bind(orderItem);
    }

    @Override
    public int getItemCount() {
        return orderItems.size();
    }

    public class OrderItemViewHolder extends RecyclerView.ViewHolder{
        private TextView txtItemTitle;
        private TextView txtItemQuantity;
        private TextView txtItemPrice;

        public OrderItemViewHolder(@NonNull View itemView) {
            super(itemView);
            txtItemTitle = itemView.findViewById(R.id.txtItemTitle);
            txtItemQuantity = itemView.findViewById(R.id.txtItemQuantity);
            txtItemPrice = itemView.findViewById(R.id.txtItemPrice);
        }

        public void bind(OrderDisplayItem orderItem) {
            if(orderItem == null ) return;
            if (orderItem.getTitle() != null) {
                txtItemTitle.setText(orderItem.getTitle());
            } else {
                txtItemTitle.setText("No Title");
            }

            if (orderItem.getQuantity() != null) {
                txtItemQuantity.setText(String.format(MoneyUtil.localeVN,"x%d",orderItem.getQuantity()));
            } else {
                txtItemQuantity.setText("0");
            }

            DecimalFormat formatter = new DecimalFormat("#,###");
            if (orderItem.getUnitPrice() != null && orderItem.getQuantity() != null) {
                txtItemPrice.setText(MoneyUtil.formatLocalCurrency(orderItem.getTotalPrice()));
            }else{
                txtItemPrice.setText("0đ");
            }
        }
    }
}
