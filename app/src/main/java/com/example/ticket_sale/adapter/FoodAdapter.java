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
import com.example.ticket_sale.model.Food;

import java.util.List;
import java.util.Locale;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder>{
    private final List<Food> foods;
    private OnItemClickListener onItemClickListener;
    private final int maxQuantity;

    public FoodAdapter(List<Food> foods, int maxQuantity, OnItemClickListener listener) {
        this.foods = foods;
        this.onItemClickListener = listener;
        this.maxQuantity = maxQuantity;
    }

    public interface OnItemClickListener{
        void onQuantityChanged(Food food,int quantity);
    }

    @NonNull
    @Override
    public FoodAdapter.FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_food, parent, false);
        return new FoodViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodAdapter.FoodViewHolder holder, int position) {
        Food food = foods.get(position);
        holder.bind(food);
    }

    @Override
    public int getItemCount() {
        return foods.size();
    }

    public class FoodViewHolder extends RecyclerView.ViewHolder {
        private final TextView txtFoodTitle;
        private final TextView txtFoodPrice;
        private final EditText edtQuantity;
        private final ImageButton btnSubtractQuantity;
        private final ImageButton btnAddQuantity;
        private final ImageView imgFoodImage;

        private Food currentFood;
        private boolean isUpdating = false;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFoodImage = itemView.findViewById(R.id.imgFoodImage);
            txtFoodTitle = itemView.findViewById(R.id.txtFoodTitle);
            txtFoodPrice = itemView.findViewById(R.id.txtFoodPrice);
            edtQuantity = itemView.findViewById(R.id.edtQuantity);
            btnAddQuantity = itemView.findViewById(R.id.btnAdd);
            btnSubtractQuantity = itemView.findViewById(R.id.btnRemove);

            btnAddQuantity.setOnClickListener(v->addQuantity());
            btnSubtractQuantity.setOnClickListener(v -> subtractQuantity());
            edtQuantity.addTextChangedListener(quantityWatcher);
        }

        void bind(Food food) {
            currentFood = food;
            imgFoodImage.setImageResource(food.getImageResId());
            txtFoodTitle.setText(food.getTitle());
            txtFoodPrice.setText(String.format("%sđ", food.getPrice()));
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
                if (onItemClickListener != null) onItemClickListener.onQuantityChanged(currentFood, quantity);
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
                    onItemClickListener.onQuantityChanged(currentFood, quantity);
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
                    onItemClickListener.onQuantityChanged(currentFood, quantity);
                }
            }
        }

        private void updateButtonState(int quantity) {
            btnAddQuantity.setEnabled(quantity < maxQuantity);
            btnSubtractQuantity.setEnabled(quantity > 0);
        }
    }
}
