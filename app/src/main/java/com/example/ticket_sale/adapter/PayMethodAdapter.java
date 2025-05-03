package com.example.ticket_sale.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ticket_sale.R;
import com.example.ticket_sale.model.PaymentMethod;

import java.util.List;

public class PayMethodAdapter extends RecyclerView.Adapter<PayMethodAdapter.PayMethodViewHolder> {
    private final List<PaymentMethod> payMethods;

    public PayMethodAdapter(List<PaymentMethod> payMethods) {
        this.payMethods = payMethods;
    }

    @NonNull
    @Override
    public PayMethodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pay_method,parent,false );
        return new PayMethodViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PayMethodViewHolder holder, int position) {
        PaymentMethod p = payMethods.get(position);
        holder.bind(p);
    }

    @Override
    public int getItemCount() {
        return payMethods.size();
    }

    public class PayMethodViewHolder extends RecyclerView.ViewHolder{
        private TextView txtPayMethodTitle;
        private ImageView imgPayMethodImage;
        private RadioButton radPayMethodChoose;
        public PayMethodViewHolder(@NonNull View itemView) {
            super(itemView);

            txtPayMethodTitle = itemView.findViewById(R.id.txtPayMethodTitle);
            imgPayMethodImage = itemView.findViewById(R.id.imgPayMethodImage);
            radPayMethodChoose = itemView.findViewById(R.id.radPayMethodChoose);
        }

        public void bind(PaymentMethod p) {
            txtPayMethodTitle.setText(p.getInfo());
            imgPayMethodImage.setImageResource(p.getImageResId());
        }
    }
}
