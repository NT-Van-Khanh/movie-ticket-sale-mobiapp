package com.example.ticket_sale.mapper;

import com.example.ticket_sale.data.dto.BillRequestDTO;
import com.example.ticket_sale.data.dto.BillResponseDTO;
import com.example.ticket_sale.data.dto.ChairDTO;
import com.example.ticket_sale.data.dto.ChairRequestDTO;
import com.example.ticket_sale.data.dto.FoodOrderDTO;
import com.example.ticket_sale.data.dto.FoodResponseDTO;
import com.example.ticket_sale.model.Food;
import com.example.ticket_sale.model.Movie;
import com.example.ticket_sale.model.Order;
import com.example.ticket_sale.model.OrderDisplayItem;
import com.example.ticket_sale.model.Screen;
import com.example.ticket_sale.model.SeatPosition;
import com.example.ticket_sale.model.SeatType;
import com.example.ticket_sale.model.Showtime;
import com.example.ticket_sale.model.Theater;
import com.example.ticket_sale.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OrderMapper {
    public static BillRequestDTO toBillRequestDTO(Order order){
        BillRequestDTO billRequest = new BillRequestDTO();
        billRequest.setTotalPrice(order.getTotalCost());
        billRequest.setTransactionCode("");//transactionCode có thể null
        billRequest.setPaymentMethodId(order.getPaymentMethodId());

        List<ChairRequestDTO> chairs =order.getSeats().stream()
                .map(seatPosition -> SeatMapper.toChairRequest(seatPosition, order.getSelectedTicket()))
                .collect(Collectors.toList());
        billRequest.setChairs(chairs);

        List<FoodOrderDTO> foods = order.getFoods()
                .entrySet().stream()
                .map(entry -> FoodMapper.toFoodOrderDTO(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
        billRequest.setDishes(foods);

        Showtime showtime =  order.getShowtime();
        billRequest.setFilmShowTimeId(showtime.getId());
        billRequest.setFilmId(order.getMovie().getId());
        billRequest.setRoomId(showtime.getScreenId());

        User user = order.getUser();
        billRequest.setUserName(user.getName());
        billRequest.setEmail(user.getEmail());
        billRequest.setNumberPhone(user.getPhone());
        return billRequest;
    }

    public static Order toOrder(BillResponseDTO billDTO){
        Order order = new Order();
        order.setId(billDTO.getId());
        order.setPaymentMethodId(billDTO.getPaymentMethodId());
        List<SeatPosition> seats = billDTO.getChairs().stream()
                .map(SeatMapper::toSeatPosition)
                .collect(Collectors.toList());
//        Map<Food, Integer> foods = billDTO.getDishes().stream()
//                .collect(Collectors.toMap(
//                        dto -> FoodMapper.toFood(dto), FoodOrderDTO::getAmount));
        Showtime showtime = new Showtime();
        showtime.setId(billDTO.getFilmShowTimeId());
        showtime.setScreenId(billDTO.getRoomId());
        showtime.setTimeStart(billDTO.getTimeStart());
        showtime.setTimeEnd(billDTO.getTimeEnd());
        order.setShowtime(showtime);
        Theater theater = new Theater();
        theater.setName(billDTO.getNameBranch());
        Screen screen  = new Screen();
        screen.setId(billDTO.getRoomId());
        screen.setName(billDTO.getNameRoom());
        screen.setTheater(theater);
        order.setScreen(screen);
        Movie movie = new Movie();
        movie.setId(billDTO.getFilmId());
        movie.setTitle(billDTO.getNameFilm());
        order.setMovie(movie);

        // Gán User
        User user = new User();
        user.setName(billDTO.getUserName());
        user.setEmail(billDTO.getEmail());
        user.setPhone(billDTO.getNumberPhone());
        order.setUser(user);
        order.setSeats(seats);

        order.setImageQRCodeLink(billDTO.getQrCode());
//        order.setFoods(foods);
        return order;
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
