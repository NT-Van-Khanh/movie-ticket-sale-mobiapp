package com.example.ticket_sale.mapper;

import android.util.Log;

import com.example.ticket_sale.R;
import com.example.ticket_sale.data.dto.FoodResponseDTO;
import com.example.ticket_sale.data.dto.FoodTypeDTO;
import com.example.ticket_sale.model.Food;
import com.example.ticket_sale.model.FoodType;
import com.example.ticket_sale.model.Item;

import java.util.List;
import java.util.stream.Collectors;

public class FoodMapper {

    public static Food toFood(FoodResponseDTO foodDTO ){
        Food f = new Food();
        f.setId(foodDTO.getId());
        f.setTitle(foodDTO.getName());
        f.setPrice(foodDTO.getPrice());
        f.setImageLink(foodDTO.getImageLink());
        return f;
    }

//    public static FoodCombo toFoodCombo(FoodResponseDTO foodDTO){
//        FoodCombo fc = new FoodCombo();
//        fc.setId(foodDTO.getId());
//        fc.setTitle(foodDTO.getName());
//        fc.setPrice(foodDTO.getPrice());
//        fc.setImageLink(foodDTO.getImageLink());
//        fc.setDescription(String.format("Tiết kiệm hơn, nhiều hơn với %s",foodDTO.getName()));
//        return fc;
//    }

    public static FoodType toFoodType(FoodTypeDTO foodTypeDTO){
//       List<Item> foods;

//       if("COMBO".equals(foodTypeDTO.getName())){
//            foods = foodTypeDTO.getDishes()
//                    .stream()
//                    .map(FoodMapper::toFood)
//                    .collect(Collectors.toList());
//        }else{
//            foods = foodTypeDTO.getDishes()
//                    .stream()
//                    .map(FoodMapper::toFood)
//                    .collect(Collectors.toList());
//        }
        Log.e("foods", String.valueOf( foodTypeDTO.getDishes().size()));
        List<Food> foods = foodTypeDTO.getDishes()
                    .stream()
                    .map(FoodMapper::toFood)
                    .collect(Collectors.toList());
        return new FoodType( foodTypeDTO.getId(), foodTypeDTO.getName(), foods);
    }
}
