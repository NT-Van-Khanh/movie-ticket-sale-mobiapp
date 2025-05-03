package com.example.ticket_sale.util;

import com.example.ticket_sale.model.Item;
import com.example.ticket_sale.model.Order;
import com.example.ticket_sale.model.OrderDisplayItem;
import com.example.ticket_sale.model.Seat;
import com.example.ticket_sale.model.SeatType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderUtil {

    public static List<OrderDisplayItem> mapToDisplayItems(Order order){
        List<OrderDisplayItem> orderDisplayItems = new ArrayList<>();
        List<Seat> seats = order.getSeats();
        Map<Item, Integer> foods = order.getFoods();

        addSeatItems(orderDisplayItems, order.getSeats());
        addFoodItems(orderDisplayItems, order.getFoods());

        return orderDisplayItems;
    }

    private static void addFoodItems(List<OrderDisplayItem> orderDisplayItems, Map<Item, Integer> foods) {
        if (foods == null || foods.isEmpty()) return;
        foods.forEach( (item, quantity)->{
            OrderDisplayItem orderItem = new OrderDisplayItem(item.getTitle(), item.getPrice(), quantity);
            orderDisplayItems.add(orderItem);
        });
    }

    private static void addSeatItems(List<OrderDisplayItem> orderDisplayItems, List<Seat> seats) {
        if(seats == null|| seats.isEmpty()) return;

        Map<SeatType, OrderDisplayItem> orderDisplaySeats = new HashMap<>();

        for(Seat seat: seats) {
            SeatType type = seat.getSeatType();

            OrderDisplayItem displayItem = orderDisplaySeats
                    .computeIfAbsent(type, k -> new OrderDisplayItem("", seat.getPrice(), 0));
            displayItem.setTitle((displayItem.getTitle().isEmpty() ? "" : " ") + seat.getTitle());
            displayItem.setQuantity(displayItem.getQuantity() + 1);
        }

        orderDisplayItems.addAll(orderDisplaySeats.values());
    }
}
