package com.example.ticket_sale.data.network.api;

import com.example.ticket_sale.data.network.ApiResponse;
import com.example.ticket_sale.data.model.Movie;
import com.example.ticket_sale.data.model.MovieType;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MovieAPI {
    String BASE_PATH = "film-service/api";

    @GET(BASE_PATH + "/film/all")
    Call<ApiResponse<List<Movie>>> getAllMovies();

    @GET(BASE_PATH + "/film/{id}")
    Call<ApiResponse<Movie>> getMovieById(@Path("id") String id);

    @GET(BASE_PATH + "/film/get/sub/{subId}")
    Call<ApiResponse<List<Movie>>> getMoviesBySubId(@Path("subId") String subId);//"1", "2",...

    @GET(BASE_PATH + "/film/get/status/{status}")
    Call<ApiResponse<List<Movie>>> getMoviesByStatus(@Path("status") String status);//"ACTIVE","COMMING_SOON", "DELETE"

    @GET(BASE_PATH + "/typefilm/get/{id}")
    Call<ApiResponse<MovieType>> getMovieTypeById(@Path("id") String id);

    @GET(BASE_PATH + "/typefilm/get/all")
    Call<ApiResponse<List<MovieType>>> getAllMovieTypes();
}
//        {
//        "id": "1",
//        "name": "QỦY NHẬP TRÀNG",
//        "age": 18,
//        "image": "https://cinestar.com.vn/_next/image/?url=https%3A%2F%2Fapi-website.cinestar.com.vn%2Fmedia%2Fwysiwyg%2FPosters%2F03_2025%2Fquy-nhap-trang-poster.jpg&w=640&q=50",
//        "sub": [
//        {
//        "id": "2",
//        "name": "[2D] Tiếng Việt"
//        }
//        ],
//        "nation": "VIỆT NAM",
//        "duration": "122'",
//        "description": "<p>Đạo diễn: Pom Nguyễn</p>",
//        "content": "<p>Đạo diễn: Pom Nguyễn</p>",
//        "trailer": "https://www.youtube.com/embed/d_C534uicPw?si=oZxZwt_VTz2s4wGk",
//        "typeFilms": [
//        {
//        "id": "KINHDI",
//        "name": "Kinh Dị",
//        "active": "ACTIVE"
//        }
//        ],
//        "status": "COMMING_SOON"
//        },
