package com.example.ticket_sale.data.network.api;

import com.example.ticket_sale.data.network.ApiResponse;
import com.example.ticket_sale.data.model.Food;
import com.example.ticket_sale.data.model.FoodType;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface FoodAPI {
    String BASE_PATH = "dish-service/api";

    //http://localhost:8888/dish-service/api/dish/3361ff55-72ea-4d7b-b66d-bcf7352bf9ee
    @GET(BASE_PATH + "/dish/{id}")
    Call<ApiResponse<Food>> getFoodById(@Path("id") String id);

    //http://localhost:8888/dish-service/api/dish/all
    @GET(BASE_PATH + "/dish/all")
    Call<ApiResponse<List<Food>>> getAllFoods();

    //http://localhost:8888/dish-service/api/typedish/7b2ae9d0-3aab-4809-9060-282a257eeea9
    @GET(BASE_PATH + "/typedish/{id}")
    Call<ApiResponse<FoodType>> getFoodTypeById(@Path("id") String id);

    //http://localhost:8888/dish-service/api/typedish/all
    @GET(BASE_PATH + "/typedish/all")
    Call<ApiResponse<List<FoodType>>> getAllFoodTypes();
}





    //{
//  "statusCode": 200,
//  "message": "Get all dish successfully",
//  "data": [
//    {
//      "id": "3361ff55-72ea-4d7b-b66d-bcf7352bf9ee",
//      "price": 10000100,
//      "active": "ACTIVE",
//      "name": "BẮP RANG BƠ",
//      "image": "https://api-website.cinestar.com.vn/media/.thumbswysiwyg/pictures/PICCONNEW/CNS035_COMBO_GAU.png?rand=1723084117",
//      "typeDish": null
//    },
//    {
//      "id": "b2bb4e3b-fcc4-40d6-afae-a2b7119cae5b",
//      "price": 10000,
//      "active": "ACTIVE",
//      "name": "Bắp xào me",
//      "image": "http://res.cloudinary.com/de8nxh0wc/image/upload/v1742286322/1884f2b7-dd77-4cff-b94d-1a4d3da3703f.jpg",
//      "typeDish": null
//    }
//  ]
//}



//{
//  "statusCode": 200,
//  "message": "Get all type dish in database",
//  "data": [
//    {
//      "id": "7b2ae9d0-3aab-4809-9060-282a257eeea9",
//      "active": "ACTIVE",
//      "name": "Bắp Rang",
//      "dishes": [
//        {
//          "id": "3361ff55-72ea-4d7b-b66d-bcf7352bf9ee",
//          "price": 10000100,
//          "active": "ACTIVE",
//          "name": "BẮP RANG BƠ",
//          "image": "https://api-website.cinestar.com.vn/media/.thumbswysiwyg/pictures/PICCONNEW/CNS035_COMBO_GAU.png?rand=1723084117",
//          "typeDish": null
//        },
//        {
//          "id": "b2bb4e3b-fcc4-40d6-afae-a2b7119cae5b",
//          "price": 10000,
//          "active": "ACTIVE",
//          "name": "Bắp xào me",
//          "image": "http://res.cloudinary.com/de8nxh0wc/image/upload/v1742286322/1884f2b7-dd77-4cff-b94d-1a4d3da3703f.jpg",
//          "typeDish": null
//        }
//      ]
//    }
//  ]
//}


