package com.example.ticket_sale.data.network.api;

import com.example.ticket_sale.data.network.ApiResponse;
import com.example.ticket_sale.data.dto.MovieFormatDTO;
import com.example.ticket_sale.data.dto.MovieWrapper;
import com.example.ticket_sale.data.network.PageResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieFormatAPI {
    String BASE_PATH = "film-service/api";
    @GET(BASE_PATH + "/sub/all")
    Call<ApiResponse<List<MovieFormatDTO>>> getAllMovieFormats();

    @GET(BASE_PATH + "/subfilm/get/all")
    Call<ApiResponse<List<MovieWrapper>>> getAllMoviesWithFormats();

    //http://localhost:8888/film-service/api/subfilm/get/sub/1?page=0&limit=10&asc=asc
    @GET(BASE_PATH + "/subfilm/get/sub/{subId}")
    Call<ApiResponse<PageResponse<MovieWrapper>>> getMoviesByFormatId(@Path("subId") String id,
                                                                    @Query("page") String page,
                                                                    @Query("limit") String limit,
                                                                    @Query("asc") String asc);

}

//    {
//        "statusCode": 200,
//            "message": "Get subfilm successfully",
//            "data": [
//        {
//            "id": "05382054-4cf2-4a72-bf9f-2aa03eb0fd2c",
//                "filmDto": {
//            "id": "9d7226db-6e50-4370-a50b-f0f2b5f28024",
//                    "name": "Nhà bà nữ 2",
//                    "age": 18,
//                    "image": null,
//                    "sub": null,
//                    "nation": null,
//                    "duration": null,
//                    "description": "<p>M&ocirc; tả...;l</p>",
//                    "content": "<p>Nội dung...</p>",
//                    "trailer": "link",
//                    "typeFilms": null,
//                    "status": null
//        },
//            "subDto": {
//            "id": "1",
//                    "name": "[2D] Lồng Tiếng"
//        }
//        },
//        {
//            "id": "2",
//                "filmDto": {
//            "id": "1",
//                    "name": "QỦY NHẬP TRÀNG",
//                    "age": 18,
//                    "image": null,
//                    "sub": null,
//                    "nation": null,
//                    "duration": null,
//                    "description": "<p>Đạo diễn: Pom Nguyễn</p>",
//                    "content": "<p>Đạo diễn: Pom Nguyễn</p>",
//                    "trailer": "https://www.youtube.com/embed/d_C534uicPw?si=oZxZwt_VTz2s4wGk",
//                    "typeFilms": null,
//                    "status": null
//        },
//            "subDto": {
//            "id": "2",
//                    "name": "[2D] Tiếng Việt"
//        }
//        },
//        {
//            "id": "3",
//                "filmDto": {
//            "id": "3",
//                    "name": "SÁT THỦ VÔ CÙNG CỰC HÀI (T16) LT",
//                    "age": 16,
//                    "image": null,
//                    "sub": null,
//                    "nation": null,
//                    "duration": null,
//                    "description": "<p>C&acirc;u chuyện tiếp nối về cuộc đời l&agrave;m hoạ sĩ webtoon Jun, người nổi tiếng trong thời gian ngắn với tư c&aacute;ch l&agrave; t&aacute;c giả của webtoon Đặc vụ &aacute;m s&aacute;t Jun, nhanh ch&oacute;ng mang danh l&agrave; \"nh&agrave; văn thiếu n&atilde;o\" sau khi Phần 2 bị chỉ tr&iacute;ch thảm hại, nhưng mọi thứ thay đổi khi một cuộc tấn c&ocirc;ng khủng bố ngo&agrave;i đời thực giống hệt với phần 2 anh vừa xuất bản, khiến Jun bị NIS buộc tội sai l&agrave; kẻ chủ mưu đằng sau tội &aacute;c.</p>",
//                    "content": "<p>Đạo diễn: Choi Won-sub</p>",
//                    "trailer": "https://www.youtube.com/embed/DXNno1pNlyM?si=TbiSuHH7BGhbQDNG",
//                    "typeFilms": null,
//                    "status": null
//        },
//            "subDto": {
//            "id": "1",
//                    "name": "[2D] Lồng Tiếng"
//        }
//        },
//        {
//            "id": "cbad9b6b-a714-4989-8f37-63cbd5e46bb9",
//                "filmDto": {
//            "id": "8",
//                    "name": "TIẾNG VỌNG KINH HOÀNG",
//                    "age": 18,
//                    "image": null,
//                    "sub": null,
//                    "nation": null,
//                    "duration": null,
//                    "description": null,
//                    "content": "Khởi chiếu: Thứ Sáu, 14/03/2025",
//                    "trailer": null,
//                    "typeFilms": null,
//                    "status": null
//        },
//            "subDto": {
//            "id": "1",
//                    "name": "[2D] Lồng Tiếng"
//        }
//        }
//  ]
//    }
