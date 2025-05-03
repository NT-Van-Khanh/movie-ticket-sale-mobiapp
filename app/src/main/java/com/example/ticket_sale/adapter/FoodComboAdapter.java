package com.example.ticket_sale.adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ticket_sale.R;
import com.example.ticket_sale.model.FoodCombo;

import java.util.List;

public class FoodComboAdapter extends RecyclerView.Adapter<FoodComboAdapter.FComboViewHolder> {
    private final List<FoodCombo> foodCombos;
    private final OnItemClickListener onItemClickListener;
    private final int maxQuantity;

    public FoodComboAdapter(List<FoodCombo> foodCombos,int maxQuantity, OnItemClickListener onItemClickListener) {
        this.foodCombos = foodCombos;
        this.maxQuantity = maxQuantity;
        this.onItemClickListener = onItemClickListener;
    }

    public interface  OnItemClickListener{
        void onQuantityChanged(FoodCombo food, int quantity);
    }

    @NonNull
    @Override
    public FoodComboAdapter.FComboViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_food_combo, parent, false);
        return new FComboViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodComboAdapter.FComboViewHolder holder, int position) {
        FoodCombo foodCombo= foodCombos.get(position);
        holder.bind(foodCombo);
    }

    @Override
    public int getItemCount() {
        return foodCombos.size();
    }

    public class FComboViewHolder extends RecyclerView.ViewHolder {
        private final TextView txtComboTitle;
        private final TextView txtComboPrice;
        private final TextView txtComboDescription;
        private final EditText edtQuantity;
        private final ImageView imgComboImage;
        private final ImageButton btnAddQuantity;
        private final ImageButton btnSubtractQuantity;

        private FoodCombo currentFoodCombo;
        private boolean isUpdating = false;

        public FComboViewHolder(@NonNull View itemView) {
            super(itemView);
            txtComboDescription = itemView.findViewById(R.id.txtComboDescription);
            txtComboTitle = itemView.findViewById(R.id.txtComboTitle);
            txtComboPrice = itemView.findViewById(R.id.txtComboPrice);
            edtQuantity = itemView.findViewById(R.id.edtQuantity);
            imgComboImage = itemView.findViewById(R.id.imgComboImage);
            btnAddQuantity = itemView.findViewById(R.id.btnAdd);
            btnSubtractQuantity = itemView.findViewById(R.id.btnRemove);

            btnAddQuantity.setOnClickListener(v->addQuantity());
            btnSubtractQuantity.setOnClickListener(v -> subtractQuantity());
            edtQuantity.addTextChangedListener(quantityWatcher);
        }

        void bind(FoodCombo foodCombo) {
            currentFoodCombo = foodCombo;
            txtComboDescription.setText(foodCombo.getDescription());
            txtComboTitle.setText(foodCombo.getTitle());
            txtComboPrice.setText(foodCombo.getPrice().toString());
            imgComboImage.setImageResource(foodCombo.getImageResId());
            isUpdating = true;
            edtQuantity.setText("0");
            isUpdating = false;
            updateButtonState(0);
        }

        private final TextWatcher quantityWatcher = new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (isUpdating) return;
                int quantity = safeParseQuantity();
                if (quantity > maxQuantity) {
                    quantity = maxQuantity;
                    isUpdating = true;
                    edtQuantity.setText(String.valueOf(quantity));
                    edtQuantity.setSelection(String.valueOf(quantity).length());
                    isUpdating = false;
                }
                if (onItemClickListener != null) onItemClickListener.onQuantityChanged(currentFoodCombo, quantity);
                updateButtonState(quantity);
            }
        };

        private int safeParseQuantity() {
            try {
                return Integer.parseInt(edtQuantity.getText().toString());
            } catch (NumberFormatException e) {
                return 0;
            }
        }

        private void addQuantity(){
            Integer quantity =safeParseQuantity();
            if(quantity < maxQuantity){
                quantity +=1;
                isUpdating = true;
                edtQuantity.setText(String.valueOf(quantity));
                isUpdating = false;
                updateButtonState(quantity);
                if (onItemClickListener != null) {
                    onItemClickListener.onQuantityChanged(currentFoodCombo, quantity);
                }
            }
        }

        private void subtractQuantity(){
            Integer quantity = safeParseQuantity();
            if(quantity > 0){
                quantity -= 1;
                isUpdating = true;
                edtQuantity.setText(String.valueOf(quantity));
                isUpdating = false;
                updateButtonState(quantity);
                if (onItemClickListener != null) {
                    onItemClickListener.onQuantityChanged(currentFoodCombo, quantity);
                }
            }
        }

        private void updateButtonState(int quantity) {
            btnAddQuantity.setEnabled(quantity < maxQuantity);
            btnSubtractQuantity.setEnabled(quantity > 0);
        }
    }
}
