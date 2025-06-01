package com.example.ticket_sale.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ticket_sale.data.ApiServiceFactory;
import com.example.ticket_sale.data.dto.MovieShowtimeDTO;
import com.example.ticket_sale.data.dto.TheaterDTO;
import com.example.ticket_sale.data.dto.TicketDTO;
import com.example.ticket_sale.data.network.ApiResponse;
import com.example.ticket_sale.data.repository.MovieShowtimeRepository;
import com.example.ticket_sale.data.repository.TheaterRepository;
import com.example.ticket_sale.data.repository.TicketRepository;
import com.example.ticket_sale.model.ShowtimeKey;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MovieShowtimeViewModel extends ViewModel {
    private final TheaterRepository theaterRepository;
    private final MovieShowtimeRepository showtimeRepository;
    private final TicketRepository ticketRepository;

    private Map<ShowtimeKey, LiveData<ApiResponse<List<MovieShowtimeDTO>>>> showtimesMap;
    private LiveData<ApiResponse<List<TheaterDTO>>> theaters;
    private LiveData<ApiResponse<List<MovieShowtimeDTO>>> showtimes;

    private LiveData<ApiResponse<List<TicketDTO>>> tickets;


    public MovieShowtimeViewModel() {
        this.theaterRepository = new TheaterRepository(ApiServiceFactory.getTheaterAPI());
        this.showtimeRepository = new MovieShowtimeRepository(ApiServiceFactory.getMovieShowtimeAPI());
        this.ticketRepository = new TicketRepository(ApiServiceFactory.getTicketAPI());
        showtimesMap = new HashMap<>();
    }

    private void fetchTheaters(){
        this.theaters = theaterRepository.getAllTheaters();
    }

    private void fetchShowtimes(String brandId, String date, String movieId, String movieFormatId){
        this.showtimes = showtimeRepository.getShowtimesByMovieAndTheater(brandId, date, movieId, movieFormatId);
    }

    public LiveData<ApiResponse<List<TheaterDTO>>> getTheaters(){
        if(theaters == null) fetchTheaters();
        return theaters;
    }

    public LiveData<ApiResponse<List<MovieShowtimeDTO>>> getShowtimes(String brandId, String date,
                                                                      String movieId, String movieFormatId){
        fetchShowtimes(brandId, date, movieId, movieFormatId);
        return showtimes;
    }

    private void fetchTickets(){
        this.tickets = ticketRepository.getAllActiveTickets();
    }

    public  LiveData<ApiResponse<List<TicketDTO>>> getTickets(){
        fetchTickets();
        return this.tickets;
    }


    private void fetchShowtimesGroupedByTheater(ShowtimeKey showtimeKey){
        if(showtimeKey == null) return;
        if (!showtimesMap.containsKey(showtimeKey)) {
            LiveData<ApiResponse<List<MovieShowtimeDTO>>> showtimes = showtimeRepository
                    .getShowtimesByMovieAndTheater(showtimeKey.getTheaterId(),
                            showtimeKey.getDate(),
                            showtimeKey.getMovieId(),
                            showtimeKey.getFormatId());
            showtimesMap.put(showtimeKey, showtimes);
        }
    }

    public LiveData<ApiResponse<List<MovieShowtimeDTO>>> getShowtimes(ShowtimeKey showtimeKey) {
        if (showtimeKey == null || !showtimeKey.isValid()) return new MutableLiveData<>();
        if(!showtimesMap.containsKey(showtimeKey)) fetchShowtimesGroupedByTheater(showtimeKey);
        return showtimesMap.getOrDefault(showtimeKey, new MutableLiveData<>());
    }
}
