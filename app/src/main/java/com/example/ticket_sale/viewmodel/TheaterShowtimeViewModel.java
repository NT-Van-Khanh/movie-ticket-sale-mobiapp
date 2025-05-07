package com.example.ticket_sale.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ticket_sale.data.ApiServiceFactory;
import com.example.ticket_sale.data.dto.MovieDTO;
import com.example.ticket_sale.data.dto.MovieShowtimeDTO;
import com.example.ticket_sale.data.network.ApiResponse;
import com.example.ticket_sale.data.repository.MovieRepository;
import com.example.ticket_sale.data.repository.MovieShowtimeRepository;
import com.example.ticket_sale.data.repository.ScreenRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TheaterShowtimeViewModel extends ViewModel {
    private final MovieShowtimeRepository showtimeRepository;
    private final MovieRepository movieRepository;
//    private LiveData<ApiResponse<List<MovieShowtimeDTO>>> showtimes;
    private LiveData<ApiResponse<List<MovieDTO>>> movies;
    private final Map<String, LiveData<ApiResponse<List<MovieShowtimeDTO>>>> showtimesMap = new HashMap<>();

    public TheaterShowtimeViewModel() {
        this.movieRepository = new MovieRepository(ApiServiceFactory.getMovieAPI());
        this.showtimeRepository = new MovieShowtimeRepository(ApiServiceFactory.getMovieShowtimeAPI());
    }

    public void fetchShowtimesGroupedByMovie(String theaterId, String date, String movieId, String formatId){
        String key = movieId + "_" + formatId;
        if (!showtimesMap.containsKey(key)) {
            LiveData<ApiResponse<List<MovieShowtimeDTO>>> showtimes = showtimeRepository.getShowtimesByMovieAndTheater(theaterId, date, movieId, formatId);
            showtimesMap.put(key, showtimes);
        }
    }

    public void fetchCurrentMovies(){
        movies =movieRepository.getMoviesByStatus("CURRENT");
    }

    public LiveData<ApiResponse<List<MovieShowtimeDTO>>> getShowtimes(String movieId, String formatId) {
        String key = movieId + "_" + formatId;
        return showtimesMap.getOrDefault(key, new MutableLiveData<>());
    }

    public LiveData<ApiResponse<List<MovieDTO>>> getMovies() {
        return movies;
    }

    public void clearShowtimes() {
        showtimesMap.clear();
    }

//    public LiveData<ApiResponse<List<MovieShowtimeDTO>>> getShowtimes() {
//        return showtimes;
//    }



//    private final MediatorLiveData<Map<ApiResponse<MovieDTO>, ApiResponse<List<MovieShowtimeDTO>>>> showtimesLiveData = new MediatorLiveData<>();

//    getShowtimesGroupedByMovie(theaterId, date);
    //        showtimes = showtimeRepository.getShowtimesByRoomId(screenId,"2025-03-17");
//    private void getShowtimesGroupedByMovie(String theaterId, String date) {
//        movies =movieRepository.getMoviesByStatus("CURRENT");
//
//        if(movies!= null){
//            showtimes = showtimeRepository.getShowtimesByMovieAndTheater(theaterId, date,);
//        }
//        movieRepository.getMoviesByStatus("CURRENT").observeForever(moviesResponse -> {
//            if (moviesResponse == null || moviesResponse.getData() == null) {
//                showtimesLiveData.postValue(new HashMap<>());
//                return;
//            }
//
//            List<MovieDTO> movies = moviesResponse.getData();
//            Map<ApiResponse<MovieDTO>, ApiResponse<List<MovieShowtimeDTO>> resultMap = new HashMap<>();
//            AtomicInteger counter = new AtomicInteger();
//
//            for (MovieDTO movie : movies) {
//                for (MovieFormat format : movie.getFormat()) {
//                    counter.incrementAndGet();
//                    showtimeRepository.getShowtimesByMovieAndTheater(theaterId, date, movie.getId(), format.getId())
//                            .observeForever(showtimes -> {
//                                synchronized (resultMap) {
//                                    resultMap.computeIfAbsent(movie, k -> new ArrayList<>()).addAll(showtimes);
//                                }
//                                if (counter.decrementAndGet() == 0) {
//                                    showtimesLiveData.postValue(resultMap);
//                                }
//                            });
//                }
//            }
//
//            if (counter.get() == 0) {
//                showtimesLiveData.postValue(resultMap);
//            }
//        });
//    }
//
//    public LiveData<Map<MovieDTO, List<MovieShowtimeDTO>>> getShowtimesByMovieId() {
//        return showtimesByMovieId;
//    }

//    public LiveData<ApiResponse<List<ScreenDTO>>> getShowtimes(String theaterId){
//        screens = screenRepository.getScreensByBranchId(theaterId);
//        Map<MovieDTO, List<MovieShowtimeDTO>> showtimesByMovie = new HashMap<>();
//        Map<ScreenDTO, List<MovieShowtimeDTO>> showtimeByScreen =  new HashMap<>();
//
//        return screens;
//    }



//    public LiveData<ApiResponse<List<MovieShowtimeDTO>>> getShowtimes(String screenId, String date) {
//        showtimes = showtimeRepository.getShowtimesByRoomId(screenId,date);
//        return showtimes;
//    }

}
