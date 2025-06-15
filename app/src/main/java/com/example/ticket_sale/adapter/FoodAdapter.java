package com.example.ticket_sale.adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ticket_sale.R;
import com.example.ticket_sale.model.Food;
import com.example.ticket_sale.util.ViLocaleUtil;

import java.util.List;
import java.util.Locale;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder>{
    private List<Food> foods;
    private final OnQuantityChangeListener onQuantityChangeListener;

    public FoodAdapter(List<Food> foods, OnQuantityChangeListener listener) {
        this.foods = foods;
        this.onQuantityChangeListener = listener;
    }

    public interface  OnQuantityChangeListener{
        int  getAvailableQuantity();
        void onQuantityChanged(Food food, int quantity);
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
        return foods != null ? foods.size() : 0;
    }

    public void setFoods(List<Food> foods) {
        this.foods = foods;
        notifyDataSetChanged();
    }

    public class FoodViewHolder extends RecyclerView.ViewHolder {
        private final TextView txtFoodTitle;
        private final TextView txtFoodPrice;
        private final EditText edtQuantity;
        private final ImageButton btnSubtractQuantity;
        private final ImageButton btnAddQuantity;
        private final ImageView imgFoodImage;

        private int currentQuantity = 0;
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
            if(food == null) return;

            currentFood = food;
            txtFoodTitle.setText(food.getTitle());
            txtFoodPrice.setText(ViLocaleUtil.formatLocalCurrency(food.getPrice()));
//            imgFoodImage.setImageResource(food.getImageResId());
            Glide.with(itemView.getContext())
                    .load(food.getImageLink())
                    .placeholder(R.drawable.fd_cb_popcorn1)
                    .error(R.drawable.fd_cb_popcorn1)
                    .into(imgFoodImage);
            updateEdtQuantity("0");
            updateButtonState(0, onQuantityChangeListener.getAvailableQuantity());
        }

        private final TextWatcher quantityWatcher = new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (isUpdating) return;

                int quantity = safeParseQuantity();
                if (onQuantityChangeListener == null) return;

                int maxAvailableQuantity = onQuantityChangeListener.getAvailableQuantity() + currentQuantity;
                if (quantity > maxAvailableQuantity) {
                    quantity = maxAvailableQuantity;
                    maxAvailableQuantity =0;
                    isUpdating = true;
                    edtQuantity.setText(String.valueOf(quantity));
                    edtQuantity.setSelection(String.valueOf(quantity).length());
                    isUpdating = false;
                }
                currentQuantity = quantity;
                onQuantityChangeListener.onQuantityChanged(currentFood, currentQuantity);
                updateButtonState(currentQuantity, maxAvailableQuantity);
            }
        };


        private void addQuantity(){
            if (onQuantityChangeListener == null) return;
            int maxAvailableQuantity =  onQuantityChangeListener.getAvailableQuantity();
            if(maxAvailableQuantity > 0){
                currentQuantity +=1;
                updateEdtQuantity(String.valueOf(currentQuantity));
                updateButtonState(currentQuantity, maxAvailableQuantity);
                onQuantityChangeListener.onQuantityChanged(currentFood, currentQuantity);
            }
        }

        private void subtractQuantity(){
            if (onQuantityChangeListener == null) return;
            if(currentQuantity > 0){
                currentQuantity -= 1;
                updateEdtQuantity(String.valueOf(currentQuantity ));
                onQuantityChangeListener.onQuantityChanged(currentFood, currentQuantity);
                int maxAvailableQuantity = onQuantityChangeListener.getAvailableQuantity();
                updateButtonState(currentQuantity , maxAvailableQuantity);

            }
        }

        private void updateButtonState(int currentQuantity, int maxAvailableQuantity) {
            btnAddQuantity.setEnabled(maxAvailableQuantity > 0);
            btnSubtractQuantity.setEnabled(currentQuantity > 0);
        }

        private void updateEdtQuantity(String quantity){
            isUpdating = true;
            edtQuantity.setText(quantity);
            isUpdating = false;
        }

        private int safeParseQuantity() {
            try {
                return Integer.parseInt(edtQuantity.getText().toString());
            } catch (NumberFormatException e) {
                return 0;
            }
        }
    }

}
