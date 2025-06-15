package com.example.ticket_sale.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.ticket_sale.data.ApiServiceFactory;
import com.example.ticket_sale.data.dto.MovieDTO;
import com.example.ticket_sale.data.dto.PageRequest;
import com.example.ticket_sale.data.dto.Slider;
import com.example.ticket_sale.data.network.ApiResponse;
import com.example.ticket_sale.data.network.PageResponse;
import com.example.ticket_sale.data.repository.MovieRepository;
import com.example.ticket_sale.data.repository.SliderRepository;

import java.util.List;

public class HomeViewModel extends ViewModel {
    private final MovieRepository movieRepository;
    private final SliderRepository sliderRepository;
    private LiveData<ApiResponse<List<Slider>>> currentPosters;
    private LiveData<ApiResponse<List<MovieDTO>>> currentMovies;
    private LiveData<ApiResponse<List<MovieDTO>>> upcomingMovies;
    private LiveData<ApiResponse<PageResponse<MovieDTO>>> top6CurrentMovies;
    private LiveData<ApiResponse<PageResponse<MovieDTO>>> top6UpcomingMovies;

    public HomeViewModel() {
        this.movieRepository = new MovieRepository(ApiServiceFactory.getMovieAPI());
        this.sliderRepository = new SliderRepository(ApiServiceFactory.getSliderAPI());
    }

    private void fetchCurrentPosters(){
        currentPosters = sliderRepository.getAllSliders();
    }

    private void fetchCurrentMovies(){
        this.currentMovies = movieRepository.getMoviesByStatus("ACTIVE");
    }

    private void fetchTop6CurrentMovies(){
        PageRequest pageRequest = new PageRequest("0", "6", "", "asc","ACTIVE","name");
        this.top6CurrentMovies = movieRepository.getMoviesCustom(pageRequest);
    }
    
    private void fetchTop6UpcomingMovies(){
        PageRequest pageRequest = new PageRequest("0", "6", "", "asc","COMMING_SOON","name");
        this.top6UpcomingMovies = movieRepository.getMoviesCustom(pageRequest);
    }
    
    private void fetchUpcomingMovies(){
        upcomingMovies = movieRepository.getMoviesByStatus("COMMING_SOON");
    }

    public LiveData<ApiResponse<List<Slider>>> getCurrentPosters(){
        if(currentPosters == null) fetchCurrentPosters();
        return currentPosters;
    }

    public LiveData<ApiResponse<List<MovieDTO>>> getCurrentMovies() {
        if(currentMovies == null) fetchCurrentMovies();
        return currentMovies;
    }

    public LiveData<ApiResponse<List<MovieDTO>>> getUpcomingMovies() {
        if(upcomingMovies == null) fetchUpcomingMovies();
        return upcomingMovies;
    }

    public LiveData<ApiResponse<PageResponse<MovieDTO>>> getTop6CurrentMovies() {
        if(top6CurrentMovies == null) fetchTop6CurrentMovies();
        return top6CurrentMovies;
    }

    public LiveData<ApiResponse<PageResponse<MovieDTO>>> getTop6UpcomingMovies() {
        if(top6UpcomingMovies == null) fetchTop6UpcomingMovies();
        return top6UpcomingMovies;
    }

    //    public LiveData<ApiResponse<List<MovieDTO>>> getAllMovies(){
//        return movieRepository.getAllMovies();
//    }
//    public void fetchCurrentMovies() {
//        movieRepository.getMoviesByStatus("NOW").observeForever(apiResponse -> {
//            if (apiResponse != null && apiResponse.getStatusCode() == 200) {
//                currentMovies.setValue(apiResponse.getData());
//            } else {
//                errorMessage.setValue("Lấy dữ liệu phim thất bại");
//            }
//        });
//    }
//    public void fetchUpcommingMovies() {
//        movieRepository.getMoviesByStatus("NOW").observeForever(apiResponse -> {
//            if (apiResponse != null && apiResponse.getStatusCode() == 200) {
//                currentMovies.setValue(apiResponse.getData());
//            } else {
//                errorMessage.setValue("Lấy dữ liệu phim thất bại");
//            }
//        });
//    }
//    public LiveData<List<MovieDTO>> getCurrentMovies() {
//        return currentMovies;
//    }
//    public   LiveData<ApiResponse<List<MovieDTO>>> getMoviesByStatus(String status){
//        return movieRepository.getMoviesByStatus(status);
//    }
//
//    public LiveData<ApiResponse<List<Slider>>>  getAllSliders(){
//        return sliderRepository.getAllSliders();
//    }
}
