package com.example.ticket_sale.data;

import com.example.ticket_sale.data.network.api.*;

import retrofit2.Retrofit;

public class ApiServiceFactory {
    private static final Retrofit retrofit = RetrofitInstance.getInstance();

    private static UserAPI userAPI;
    private static PaymentAPI paymentAPI;
    private static TicketAPI ticketAPI;
    private static TheaterAPI theaterAPI;
    private static SliderAPI sliderAPI;
    private static RateAPI rateAPI;
//    private static MovieTypeAPI movieTypeAPI;
    private static MovieShowtimeAPI movieShowtimeAPI;
    private static MovieFormatAPI movieFormatAPI;
//    private static FoodTypeAPI foodTypeAPI;
//    private static ContactAPI contactAPI;
    private static BillAPI billAPI;
    private  static  AuthenticationAPI authenticationAPI;
    private static MovieAPI movieAPI;
    private static SeatAPI seatAPI;
    private static ScreenAPI screenAPI;
    private static FoodAPI foodAPI;
    public static <T> T createService(Class<T> serviceClass) {
        return RetrofitInstance.getInstance().create(serviceClass);
    }// nên dùng cách này vì token có thể thay đổi

    public static UserAPI getUserAPI() {
        if(userAPI == null)  userAPI = retrofit.create(UserAPI.class);
        return userAPI;
    }

    public static PaymentAPI getPaymentAPI(){
        if(paymentAPI == null)   paymentAPI = retrofit.create(PaymentAPI.class);
        return paymentAPI;
    }

    public static TicketAPI getTicketAPI(){
        if(ticketAPI == null) ticketAPI = retrofit.create(TicketAPI.class);
        return ticketAPI;
    }

    public static MovieAPI getMovieAPI(){
        if(movieAPI == null) movieAPI = retrofit.create(MovieAPI.class);
        return  movieAPI;
    }

    public static SeatAPI getSeatAPI(){
        if(seatAPI == null) seatAPI = retrofit.create(SeatAPI.class);
        return seatAPI;
    }

    public static ScreenAPI getScreenAPI(){
        if(screenAPI == null) screenAPI = retrofit.create(ScreenAPI.class);
        return screenAPI;
    }

    public static FoodAPI getFoodAPI(){
        if(foodAPI == null) foodAPI = retrofit.create(FoodAPI.class);
        return foodAPI;
    }

    public static AuthenticationAPI getAuthenticationAPI() {
        if(authenticationAPI ==null)
            authenticationAPI = retrofit.create(AuthenticationAPI.class);
        return authenticationAPI;
    }

    public static BillAPI getBillAPI() {
        if(billAPI == null) billAPI = retrofit.create(BillAPI.class);
        return billAPI;
    }

    public static MovieFormatAPI getMovieFormatAPI() {
        if(movieFormatAPI == null ) movieFormatAPI = retrofit.create(MovieFormatAPI.class);
        return movieFormatAPI;
    }

    public static MovieShowtimeAPI getMovieShowtimeAPI() {
        if (movieShowtimeAPI == null) movieShowtimeAPI = retrofit.create(MovieShowtimeAPI.class);
        return movieShowtimeAPI;
    }

    public static RateAPI getRateAPI() {
        if(rateAPI == null) rateAPI = retrofit.create(RateAPI.class);
        return rateAPI;
    }

    public static SliderAPI getSliderAPI() {
        if(sliderAPI == null) sliderAPI = retrofit.create(SliderAPI.class);
        return sliderAPI;
    }

    public static TheaterAPI getTheaterAPI() {
        if(theaterAPI == null) theaterAPI = retrofit.create(TheaterAPI.class);
        return theaterAPI;
    }


//    public static MovieTypeAPI getMovieTypeAPI() {
//        if (movieTypeAPI == null) movieTypeAPI = retrofit.create(MovieTypeAPI.class);
//        return movieTypeAPI;
//    }

//    public static FoodTypeAPI getFoodTypeAPI() {
//        if(foodTypeAPI == null) foodTypeAPI = retrofit.create(FoodTypeAPI.class);
//        return foodTypeAPI;
//    }

}
