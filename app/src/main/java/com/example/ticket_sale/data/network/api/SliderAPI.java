package com.example.ticket_sale.data.network.api;


import com.example.ticket_sale.data.network.ApiResponse;
import com.example.ticket_sale.data.model.Slider;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SliderAPI {

    @GET("film-service/api/slider/get")
    Call<ApiResponse<List<Slider>>> getAllSliders();
    //{
    //  "statusCode": 200,
    //  "message": "Get slider in database",
    //  "data": [
    //    {
    //      "id": 11,
    //      "image": "http://res.cloudinary.com/de8nxh0wc/image/upload/v1742699096/65ec3e0d-5a28-4266-905e-1babe7cf8414.png",
    //      "name": "slide1.png"
    //    },
    //    {
    //      "id": 12,
    //      "image": "http://res.cloudinary.com/de8nxh0wc/image/upload/v1742699105/d3115325-cb40-49db-8ceb-09d074db46a6.png",
    //      "name": "slide2.png"
    //    },
    //    {
    //      "id": 13,
    //      "image": "http://res.cloudinary.com/de8nxh0wc/image/upload/v1742699113/2d115639-1a50-4459-8b17-c90af8a96325.png",
    //      "name": "slide3.png"
    //    },
    //    {
    //      "id": 14,
    //      "image": "http://res.cloudinary.com/de8nxh0wc/image/upload/v1742699359/9dffd704-916b-40b9-9ed0-4f76e0a8126a.jpg",
    //      "name": "satthucocungcuc.jpg"
    //    },
    //    {
    //      "id": 15,
    //      "image": "http://res.cloudinary.com/de8nxh0wc/image/upload/v1742699384/538967ce-2810-4ac7-8cf9-8b9b1d696057.jpg",
    //      "name": "trutaky.jpg"
    //    }
    //  ]
    //}
}
