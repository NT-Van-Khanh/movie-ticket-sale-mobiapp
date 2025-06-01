package com.example.ticket_sale.mapper;

import com.example.ticket_sale.data.dto.BillRequestDTO;
import com.example.ticket_sale.model.Food;
import com.example.ticket_sale.model.Order;
import com.example.ticket_sale.model.OrderDisplayItem;
import com.example.ticket_sale.model.SeatPosition;
import com.example.ticket_sale.model.SeatType;
import com.example.ticket_sale.model.Showtime;
import com.example.ticket_sale.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderMapper {
    public static BillRequestDTO toBillRequestDTO(Order order){
        BillRequestDTO billRequest = new BillRequestDTO();
        billRequest.setTotalPrice(order.getTotalCost());
        billRequest.setTransactionCode("transactionCode");
        billRequest.setPaymentMethodId("id");

//        billRequest.setChairs(order.getSe);
//        billRequest.setDishes(order.getFoods());

        Showtime showtime =  order.getShowtime();
        billRequest.setFilmShowTimeId(showtime.getId());
        billRequest.setFilmId(showtime.getMovieId());
        billRequest.setRoomId(showtime.getScreenId());

        User user = order.getUser();
        billRequest.setUserName(user.getName());
        billRequest.setEmail(user.getEmail());
        billRequest.setNumberPhone(user.getPhone());
        return billRequest;
    }

    public static List<OrderDisplayItem> mapToDisplayItems(Order order){
        List<OrderDisplayItem> orderDisplayItems = new ArrayList<>();
        addSeatItems(orderDisplayItems, order);
        addFoodItems(orderDisplayItems, order);
        return orderDisplayItems;
    }

    private static void addFoodItems(List<OrderDisplayItem> orderDisplayItems, Order order) {
        Map<Food, Integer> foods = order.getFoods();
        if (foods == null || foods.isEmpty()) return;
        foods.forEach( (item, quantity)->{
            OrderDisplayItem orderItem = new OrderDisplayItem(item.getTitle(), item.getPrice(), quantity);
            orderDisplayItems.add(orderItem);
        });
    }

    private static void addSeatItems(List<OrderDisplayItem> orderDisplayItems,Order order) {
        List<SeatPosition> seats = order.getSeats();
        if(seats == null|| seats.isEmpty()) return;

        Map<SeatType, OrderDisplayItem> orderDisplaySeats = new HashMap<>();

        for(SeatPosition seat: seats) {
            SeatType type = seat.getSeatType();
            OrderDisplayItem displayItem = orderDisplaySeats
                    .computeIfAbsent(type, k -> new OrderDisplayItem("", order.getSelectedTicket().getPrice(), 0));
            displayItem.setTitle((displayItem.getTitle().isEmpty() ? "" : " ") + seat.getTitle());
            displayItem.setQuantity(displayItem.getQuantity() + 1);
        }

        orderDisplayItems.addAll(orderDisplaySeats.values());
    }
}
