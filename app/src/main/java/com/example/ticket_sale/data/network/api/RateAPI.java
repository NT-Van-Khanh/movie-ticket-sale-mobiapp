package com.example.ticket_sale.data.network.api;

import com.example.ticket_sale.data.dto.TotalRateDTO;
import com.example.ticket_sale.data.network.ApiResponse;
import com.example.ticket_sale.data.network.PageResponse;
import com.example.ticket_sale.data.dto.RateDTO;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RateAPI {
    String BASE_PATH = "rate-service/api/rate";

    //http://localhost:8888/rate-service/api/rate/film/1?page=0&limit=2&asc=asc&orderBy=timeStamp&status=ACTIVE
    @GET(BASE_PATH + "/film/{filmId}")
    Call<ApiResponse<PageResponse<TotalRateDTO>>> getRateByMovieId(@Path("filmId") String movieId,
                                                                   @Query("page") int page,
                                                                   @Query("limit") int limit,
                                                                   @Query("asc") String asc,
                                                                   @Query("orderBy") String orderBy,
                                                                   @Query("status") String status);

    // http://localhost:8888/rate-service/api/rate/check/1/afb2f2c2-e070-42f6-9796-03fcbd085ab2
    @GET(BASE_PATH + "/check/{filmId}/{customerId}")//không có dữ liệu trả về, chỉ có 200, 404, 201....
    Call<ApiResponse<Void>> checkRated(@Path("filmId") String movieId, @Path("customerId") String UserId);

    // http://localhost:8888/rate-service/api/rate/get/rate?page=0&limit=10&asc=asc&orderBy=timeStamp&status=none
    @GET(BASE_PATH + "/get/rate")
    Call<ApiResponse<PageResponse<RateDTO>>> getCustomerRatesWithFilter(@Query("page") int page,
                                                                        @Query("limit") int limit,
                                                                        @Query("q") String q,
                                                                        @Query("asc") String asc,
                                                                        @Query("orderBy") String orderBy,
                                                                        @Query("status") String status);

    //http://localhost:8888/rate-service/api/rate/add/cd664219-042d-4ffc-b851-5bbba4005d8b/1
    //Request body
    //{
    //  "star": 5,
    //  "content": "string",
    //  "customerId": "cd664219-042d-4ffc-b851-5bbba4005d8b",
    //  "filmId": "1",
    //  "active": "ACTIVE"
    //}
    @POST(BASE_PATH + "/add/{userId}/{filmId}")
    Call<ApiResponse<RateDTO>> addRate(@Path("filmId") String movieId,
                                       @Path("userId") String userId,
                                       @Body Map<String,Object> rate);

}


//http://localhost:8888/rate-service/api/rate/film/9d7226db-6e50-4370-a50b-f0f2b5f28024?page=0&limit=10&asc=asc&orderBy=timeStamp&status=ACTIVE
//{
//        "statusCode": 200,
//        "message": "Get rate successfully",
//        "data": {
//        "pageCurrent": 1,
//        "totalPage": 1,
//        "data": {
//        "rate": 5,
//        "comments": [
//        {
//        "id": "4e8bb2c6-a72d-40f7-ad5a-229f3f203cbe",
//        "star": 5,
//        "content": "Hay",
//        "timeStamp": "2025-06-12T08:08:25.73528",
//        "customer": {
    //        "id": "cd664219-042d-4ffc-b851-5bbba4005d8b",
    //        "name": "Ngo Cao Cuong",
    //        "phoneNumber": "0335723811",
    //        "email": "CaoCuong1@gmail.com",
    //        "account": {
        //        "userName": "caocuong",
        //        "password": "$2a$10$9k.6HsbaCkE0G8Advmqtwudx5dtUXP1LyIWz/DeQmXq1CE2P1yDhy",
        //        "role": {
            //        "id": 1,
            //        "roleName": "ROLE_USER",
            //        "status": "ACTIVE"
        //        },
    //          "active": "ACTIVE"
    //        },
    //        "timestamp": "2025-04-12",
    //        "status": "ACTIVE"
//        },
//        "film": {
    //        "id": "9d7226db-6e50-4370-a50b-f0f2b5f28024",
    //        "name": "Nhà bà nữ 2",
    //        "age": 18,
    //        "image": "http://res.cloudinary.com/de8nxh0wc/image/upload/v1742056023/1b6ca6c9-f807-4122-aee1-4f05cfb7d32f.jpg",
    //        "nation": "Việt Nam",
    //        "duration": "120",
    //        "sub": [
        //        {
        //        "id": "1",
        //        "name": "[2D] Lồng Tiếng"
        //        }
    //        ],
    //        "description": "<p>M&ocirc; tả...;l</p>",
    //        "content": "<p>Nội dung...</p>",
    //        "trailer": "link",
    //        "typeFilms": [
        //        {
        //        "id": "GIADINH",
        //        "name": "Gia Đình",
        //        "active": "ACTIVE"
        //        }
    //        ],
    //        "status": "ACTIVE"
//        },
//        "active": "ACTIVE"
//        }
//        ]
//        }
//        }
//        }














//http://localhost:8888/rate-service/api/rate/get/rate?page=0&limit=10&asc=asc&orderBy=timeStamp&status=none
//{
//        "statusCode": 200,
//        "message": "Get rate successfully",
//        "data": {
//        "pageCurrent": 1,
//        "totalPage": 1,
//        "data": [
//        {
//        "id": "372eeb94-0650-4d28-8484-272145b853b9",
//        "star": 5,
//        "content": "Phim Khá Hay",
//        "timeStamp": "2025-03-27T11:21:09.783258",
//        "customer": {
//        "id": "cd664219-042d-4ffc-b851-5bbba4005d8b",
//        "name": "Ngo Cao Cuong",
//        "phoneNumber": "0335723811",
//        "email": "CaoCuong1@gmail.com",
//        "account": {
//        "userName": "caocuong",
//        "password": "$2a$10$9k.6HsbaCkE0G8Advmqtwudx5dtUXP1LyIWz/DeQmXq1CE2P1yDhy",
//        "role": {
//        "id": 1,
//        "roleName": "ROLE_USER",
//        "status": "ACTIVE"
//        },
//        "active": "ACTIVE"
//        },
//        "timestamp": "2025-04-12",
//        "status": "ACTIVE"
//        },
//        "film": {
//        "id": "5",
//        "name": "NỤ HÔN BẠC TỶ (T13)",
//        "age": 13,
//        "image": "https://cinestar.com.vn/_next/image/?url=https%3A%2F%2Fapi-website.cinestar.com.vn%2Fmedia%2Fwysiwyg%2FPosters%2F01-2025%2Fnu-hon-bac-ty-poster.jpg&w=1920&q=75",
//        "nation": "VIỆT NAM",
//        "duration": "100'",
//        "sub": [],
//        "description": "Câu chuyện xoay quanh Vân - cô gái bán bánh mì vô tình gặp phải hai chàng trai trong một tai nạn nhỏ. Làm thế nào khi tiếng sét ái tình đánh một lúc cả ba người? Liệu giữa một chàng trai chững chạc, nam tính và một chàng trai đôi chút ngông nghênh, cool ngầu - đâu sẽ là “Nụ Hôn Bạc Tỷ” của cô gái xinh đẹp?",
//        "content": "Đạo diễn: Thu Trang",
//        "trailer": "https://www.youtube.com/embed/mip53H5Cuuo?si=bu8ljNNYQCiZWgYc",
//        "typeFilms": [
//        {
//        "id": "TINHCAM",
//        "name": "Tình Cảm",
//        "active": "ACTIVE"
//        }
//        ],
//        "status": "ACTIVE"
//        },
//        "active": "ACTIVE"
//        },
//        {
//        "id": "f69759bd-6539-42d7-81ae-d643130431f8",
//        "star": 5,
//        "content": "Phim Hay quas",
//        "timeStamp": "2025-03-30T11:29:23.119213",
//        "customer": {
//        "id": "cd664219-042d-4ffc-b851-5bbba4005d8b",
//        "name": "Ngo Cao Cuong",
//        "phoneNumber": "0335723811",
//        "email": "CaoCuong1@gmail.com",
//        "account": {
//        "userName": "caocuong",
//        "password": "$2a$10$9k.6HsbaCkE0G8Advmqtwudx5dtUXP1LyIWz/DeQmXq1CE2P1yDhy",
//        "role": {
//        "id": 1,
//        "roleName": "ROLE_USER",
//        "status": "ACTIVE"
//        },
//        "active": "ACTIVE"
//        },
//        "timestamp": "2025-04-12",
//        "status": "ACTIVE"
//        },
//        "film": {
//        "id": "4",
//        "name": "NHÀ GIA TIÊN (T`8)",
//        "age": 18,
//        "image": "https://cinestar.com.vn/_next/image/?url=https%3A%2F%2Fapi-website.cinestar.com.vn%2Fmedia%2Fwysiwyg%2FPosters%2F02_2025%2Fnha-gia-tien-sneak.jpg&w=640&q=50",
//        "nation": "VIỆT NAM",
//        "duration": "117'",
//        "sub": [],
//        "description": "Nhà Gia Tiên xoay quanh câu chuyện đa góc nhìn về các thế hệ khác nhau trong một gia đình, có hai nhân vật chính là Gia Minh (Huỳnh Lập) và Mỹ Tiên (Phương Mỹ Chi). Trở về căn nhà gia tiên để quay các video “triệu view” trên mạng xã hội, Mỹ Tiên - một nhà sáng tạo nội dung thuộc thế hệ Z vốn không tin vào chuyện tâm linh, hoàn toàn mất kết nối với gia đình, bất ngờ nhìn thấy Gia Minh - người anh trai đã mất từ lâu. Để hồn ma của Gia Minh có thể siêu thoát và không tiếp tục làm phiền mình, Mỹ Tiên bắt tay cùng Gia Minh lên kế hoạch giữ lấy căn nhà gia tiên đang bị họ hàng tranh chấp, đòi ông nội chia tài sản. Đứng trước hàng loạt bí mật động trời trong căn nhà gia tiên, liệu Mỹ Tiên có vượt qua được tất cả để hoàn thành di nguyện của Gia Minh?",
//        "content": "Đạo diễn: Huỳnh Lập",
//        "trailer": "https://www.youtube.com/embed/xdRQvnZyQes?si=umA5gcqK2oq7ABIO",
//        "typeFilms": [
//        {
//        "id": "HAI",
//        "name": "Hài",
//        "active": "ACTIVE"
//        },
//        {
//        "id": "GIADINH",
//        "name": "Gia Đình",
//        "active": "ACTIVE"
//        }
//        ],
//        "status": "ACTIVE"
//        },
//        "active": "ACTIVE"
//        },
//        {
//        "id": "1886cc81-7bbd-46c0-b019-e66ce85f6594",
//        "star": 5,
//        "content": "Tam tam",
//        "timeStamp": "2025-03-30T11:32:15.918672",
//        "customer": {
//        "id": "cd664219-042d-4ffc-b851-5bbba4005d8b",
//        "name": "Ngo Cao Cuong",
//        "phoneNumber": "0335723811",
//        "email": "CaoCuong1@gmail.com",
//        "account": {
//        "userName": "caocuong",
//        "password": "$2a$10$9k.6HsbaCkE0G8Advmqtwudx5dtUXP1LyIWz/DeQmXq1CE2P1yDhy",
//        "role": {
//        "id": 1,
//        "roleName": "ROLE_USER",
//        "status": "ACTIVE"
//        },
//        "active": "ACTIVE"
//        },
//        "timestamp": "2025-04-12",
//        "status": "ACTIVE"
//        },
//        "film": {
//        "id": "2",
//        "name": "LẠC TRÔI (P)",
//        "age": 99,
//        "image": "https://cinestar.com.vn/_next/image/?url=https%3A%2F%2Fapi-website.cinestar.com.vn%2Fmedia%2Fwysiwyg%2FPosters%2F03_2025%2Flac-troi.jpg&w=1920&q=75",
//        "nation": "KHÁC",
//        "duration": "84'",
//        "sub": [],
//        "description": "<p>Trước bối cảnh hậu tận thế, ch&uacute; m&egrave;o x&aacute;m nh&uacute;t nh&aacute;t, vốn lu&ocirc;n sợ nước phải rời bỏ v&ugrave;ng an to&agrave;n khi căn nh&agrave; th&acirc;n y&ecirc;u bị cuốn tr&ocirc;i bởi cơn lũ dữ. Tr&ecirc;n h&agrave;nh tr&igrave;nh vượt đại dương m&ecirc;nh m&ocirc;ng, ch&uacute; m&egrave;o c&ugrave;ng những người bạn đồng h&agrave;nh (Capybara, ch&oacute; Labrador Retriever, Vượn C&aacute;o, chim Thư k&yacute;) phải học c&aacute;ch vượt qua nỗi sợ v&agrave; chấp nhận những điểm kh&aacute;c biệt để c&ugrave;ng nhau tồn tại.</p>",
//        "content": "<p>Khởi chiếu: Thứ S&aacute;u, 07/03/2025</p>",
//        "trailer": "https://www.youtube.com/embed/cVYywSYuU5A?si=XuAOC1fM3w7ZDbRy",
//        "typeFilms": [
//        {
//        "id": "HOATHINH",
//        "name": "Hoạt Hình",
//        "active": "ACTIVE"
//        },
//        {
//        "id": "PHIEULUU",
//        "name": "Phiêu Lưu",
//        "active": "ACTIVE"
//        }
//        ],
//        "status": "ACTIVE"
//        },
//        "active": "ACTIVE"
//        },
//        {
//        "id": "6abd2754-4fd3-4f8b-896c-1b6cda0061f8",
//        "star": 5,
//        "content": "Hay as",
//        "timeStamp": "2025-04-13T00:19:37.597859",
//        "customer": {
//        "id": "cd664219-042d-4ffc-b851-5bbba4005d8b",
//        "name": "Ngo Cao Cuong",
//        "phoneNumber": "0335723811",
//        "email": "CaoCuong1@gmail.com",
//        "account": {
//        "userName": "caocuong",
//        "password": "$2a$10$9k.6HsbaCkE0G8Advmqtwudx5dtUXP1LyIWz/DeQmXq1CE2P1yDhy",
//        "role": {
//        "id": 1,
//        "roleName": "ROLE_USER",
//        "status": "ACTIVE"
//        },
//        "active": "ACTIVE"
//        },
//        "timestamp": "2025-04-12",
//        "status": "ACTIVE"
//        },
//        "film": {
//        "id": "8",
//        "name": "TIẾNG VỌNG KINH HOÀNG",
//        "age": 18,
//        "image": "https://cinestar.com.vn/_next/image/?url=https%3A%2F%2Fapi-website.cinestar.com.vn%2Fmedia%2Fwysiwyg%2FPosters%2F03_2025%2Fthe-monkey.jpg&w=1920&q=75",
//        "nation": "KHÁC",
//        "duration": "99'",
//        "sub": [
//        {
//        "id": "1",
//        "name": "[2D] Lồng Tiếng"
//        }
//        ],
//        "content": "Khởi chiếu: Thứ Sáu, 14/03/2025",
//        "typeFilms": [],
//        "status": "COMMING_SOON"
//        },
//        "active": "ACTIVE"
//        },
//        {
//        "id": "4e8bb2c6-a72d-40f7-ad5a-229f3f203cbe",
//        "star": 5,
//        "content": "Hay",
//        "timeStamp": "2025-06-12T08:08:25.73528",
//        "customer": {
//        "id": "cd664219-042d-4ffc-b851-5bbba4005d8b",
//        "name": "Ngo Cao Cuong",
//        "phoneNumber": "0335723811",
//        "email": "CaoCuong1@gmail.com",
//        "account": {
//        "userName": "caocuong",
//        "password": "$2a$10$9k.6HsbaCkE0G8Advmqtwudx5dtUXP1LyIWz/DeQmXq1CE2P1yDhy",
//        "role": {
//        "id": 1,
//        "roleName": "ROLE_USER",
//        "status": "ACTIVE"
//        },
//        "active": "ACTIVE"
//        },
//        "timestamp": "2025-04-12",
//        "status": "ACTIVE"
//        },
//        "film": {
//        "id": "9d7226db-6e50-4370-a50b-f0f2b5f28024",
//        "name": "Nhà bà nữ 2",
//        "age": 18,
//        "image": "http://res.cloudinary.com/de8nxh0wc/image/upload/v1742056023/1b6ca6c9-f807-4122-aee1-4f05cfb7d32f.jpg",
//        "nation": "Việt Nam",
//        "duration": "120",
//        "sub": [
//        {
//        "id": "1",
//        "name": "[2D] Lồng Tiếng"
//        }
//        ],
//        "description": "<p>M&ocirc; tả...;l</p>",
//        "content": "<p>Nội dung...</p>",
//        "trailer": "link",
//        "typeFilms": [
//        {
//        "id": "GIADINH",
//        "name": "Gia Đình",
//        "active": "ACTIVE"
//        }
//        ],
//        "status": "ACTIVE"
//        },
//        "active": "ACTIVE"
//        }
//        ]
//        }
//        }




//http://localhost:8888/rate-service/api/rate/check/9d7226db-6e50-4370-a50b-f0f2b5f28024/cd664219-042d-4ffc-b851-5bbba4005d8b