package com.example.ticket_sale.util.debug;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LifecycleOwner;

import com.example.ticket_sale.data.ApiServiceFactory;
import com.example.ticket_sale.data.TokenManager;
import com.example.ticket_sale.data.model.Bill;
import com.example.ticket_sale.data.repository.AuthRepository;
import com.example.ticket_sale.data.repository.BillRepository;
import com.example.ticket_sale.data.repository.FoodRepository;
import com.example.ticket_sale.data.repository.MovieFormatRepository;
import com.example.ticket_sale.data.repository.MovieRepository;
import com.example.ticket_sale.data.repository.MovieShowtimeRepository;
import com.example.ticket_sale.data.repository.PaymentRepository;
import com.example.ticket_sale.data.repository.RateRepository;
import com.example.ticket_sale.data.repository.ScreenRepository;
import com.example.ticket_sale.data.repository.SeatRepository;
import com.example.ticket_sale.data.repository.SliderRepository;
import com.example.ticket_sale.data.repository.TheaterRepository;
import com.example.ticket_sale.data.repository.TicketRepository;
import com.example.ticket_sale.data.repository.UserRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

public class ApiTester {
    private static final String TAG = "ApiTester";
    private final LifecycleOwner lifecycleOwner;
    private final Context context;
    private final UserRepository userRepository = new UserRepository(ApiServiceFactory.getUserAPI());
    private final PaymentRepository paymentRepository = new PaymentRepository(ApiServiceFactory.getPaymentAPI());
    private final TicketRepository ticketRepository = new TicketRepository(ApiServiceFactory.getTicketAPI());
    private final TheaterRepository theaterRepository = new TheaterRepository(ApiServiceFactory.getTheaterAPI());
    private final SliderRepository sliderRepository = new SliderRepository(ApiServiceFactory.getSliderAPI());
    private final RateRepository rateRepository = new RateRepository(ApiServiceFactory.getRateAPI());
    private final MovieShowtimeRepository movieShowtimeRepository = new MovieShowtimeRepository(ApiServiceFactory.getMovieShowtimeAPI());
    private final MovieFormatRepository movieFormatRepository = new MovieFormatRepository(ApiServiceFactory.getMovieFormatAPI());
    private final BillRepository billRepository = new BillRepository(ApiServiceFactory.getBillAPI());
    private final AuthRepository authRepository = new AuthRepository(ApiServiceFactory.getAuthenticationAPI());
    private final MovieRepository movieRepository = new MovieRepository(ApiServiceFactory.getMovieAPI());
    private final SeatRepository seatRepository = new SeatRepository(ApiServiceFactory.getSeatAPI());
    private final ScreenRepository screenRepository = new ScreenRepository(ApiServiceFactory.getScreenAPI());
    private final FoodRepository foodRepository = new FoodRepository(ApiServiceFactory.getFoodAPI());
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private String token;

    public ApiTester(LifecycleOwner lifecycleOwner, Context context) {
        this.lifecycleOwner = lifecycleOwner;
        this.context = context;
    }

    public void testAllApis() {
        TokenManager.init(context);

        authRepository.auth("caocuong", "123123123").observe(lifecycleOwner, tokenMap -> {
            if (tokenMap == null || !tokenMap.containsKey("accessToken")) {
                Log.e("authRepository.auth", "Can't not get token!");
                return;
            }
            token = tokenMap.get("accessToken");
            Log.e("authRepository_auth","Done login!");
//            testUserApi();//--
//            testFoodApi();//DONE
//            testMovieApi();//DONE
//            testMovieShowtimeApi();//DONE
//            testMovieFormatApi();//DONE
//            testTheaterApi();//DONE
//            testTicketApi();//DONE
//            testScreenApi();//DONE
//            testSeatApi();//DONE
//            testSliderApi();//DONE

////            testBillApi();//check lại add Bill
////            testPaymentApi(); //payment chua thong nhat api
////            testRateApi();// check lại hàm xóa đánh giá

            authRepository.logout().observe(lifecycleOwner, data -> {
                if (data != null) {
                    Log.d("Logout", "Thành công: " + data.getMessage());
                } else {
                    Log.e("Logout", "Đăng xuất thất bại");
                }
            });
        });
    }
    private void testUserApi() {
        //Test getUser DONE
        userRepository.getUserById("cd664219-042d-4ffc-b851-5bbba4005d8b")
                .observe(lifecycleOwner, data -> {
                    if (data != null && data.getData() != null) {
                        Log.d("Get user data", data.getData().toString());
                    } else {
                        Log.e("Get user data", "Can not find the data of this user!");
                    }
                });
        //Test addUser DONE
        userRepository.addUser("John Doe", "0222222222", "john@example.com", "johndoe", "123123123")
                .observe(lifecycleOwner, data -> {
                    if (data != null &&data.getStatusCode() == 201) {
                        Log.d("testUserApi", "addUser success: " + data.getData());
                    } else {
                        Log.e("testUserApi", "addUser failed: " + data);
                    }
                });

        // Test updateUser DONE
        userRepository.updateUser("cd664219-042d-4ffc-b851-5bbba4005d8b", "John Smith", "0335723811", "CaoCuong1@gmail.com")
                .observe(lifecycleOwner, data -> {
                    if (data != null && data.getStatusCode()==200) {
                        Log.d("testUserApi", "updateUser success: " + data.getData());
                    } else {
                        Log.e("testUserApi", "updateUser failed: " + data);
                    }
                });

        // Test resetPassword
        userRepository.resetPassword("john@example.com", "123456", "newpassword123")
                .observe(lifecycleOwner, data -> {
                    if (data != null &&data.getStatusCode()==200) {
                        Log.d("testUserApi", "resetPassword success: " + data.getData());
                    } else {
                        Log.e("testUserApi", "resetPassword failed: " + data);
                    }
                });

        // Test sendOTPtoEmail
        userRepository.sendOTPtoEmail("john@example.com")
                .observe(lifecycleOwner, data -> {
                    if (data != null &&data.getStatusCode()==200) {
                        Log.d("testUserApi", "sendOTPtoEmail success");
                    } else {
                        Log.e("testUserApi", "sendOTPtoEmail failed: " + data);
                    }
                });

        // Test updatePassword
        userRepository.updatePassword("cd664219-042d-4ffc-b851-5bbba4005d8b", "updatedpassword123")
                .observe(lifecycleOwner, data -> {
                    if (data != null &&data.getStatusCode()==200) {
                        Log.d("testUserApi", "updatePassword success");
                    } else {
                        Log.e("testUserApi", "updatePassword failed: " + data);
                    }
                });

        Log.e("userRepository_auth","Done get user info!");
    }

    private void testMovieApi() {//Done
        movieRepository.getAllMovies().observe(lifecycleOwner, data -> {
            if (data != null && data.getStatusCode()==200) {
                Log.d("testMovieApi", "getAllMovies: " + data.getData());

//                Log.d("testMovieApi", "getAllMovies: " + gson.toJson(data.getData()));
            } else {
                Log.e("testMovieApi", "getAllMovies failed: " + data);
            }
        });

        movieRepository.getMovieById("1").observe(lifecycleOwner, data -> {
            if (data != null && data.getStatusCode()==200) {
                Log.d("testMovieApi", "getMovieById: " + data.getData());
            } else {
                Log.e("testMovieApi", "getMovieById failed: " + data);
            }
        });

        movieRepository.getMoviesBySubId("1").observe(lifecycleOwner, data -> {
            if (data != null && data.getStatusCode()==200) {
                Log.d("testMovieApi", "getMoviesBySubId: " + data.getData());
            } else {
                Log.e("testMovieApi", "getMoviesBySubId failed: " + data);
            }
        });

        movieRepository.getMoviesByStatus("ACTIVE").observe(lifecycleOwner, data -> {
            if (data != null && data.getStatusCode()==200) {
                Log.d("testMovieApi", "getMoviesByStatus: " + data.getData());
            } else {
                Log.e("testMovieApi", "getMoviesByStatus failed: " + data);
            }
        });

        movieRepository.getAllMovieTypes().observe(lifecycleOwner, data -> {
            if (data != null && data.getStatusCode()==200) {
                Log.d("testMovieApi", "getAllMovieTypes: " + data.getData());
            } else {
                Log.e("testMovieApi", "getAllMovieTypes failed: " + data);
            }
        });

        movieRepository.getMovieTypeById("TINHCAM").observe(lifecycleOwner, data -> {
            if (data != null && data.getStatusCode()==200) {
                Log.d("testMovieApi", "getMovieTypeById: " + data.getData());
            } else {
                Log.e("testMovieApi", "getMovieTypeById failed: " + data);
            }
        });

        Log.e("movieRepository_auth","Done get movie info!");
    }

    private void testSeatApi() {
        // Gọi getSeatById
        seatRepository.getSeatById("2101af5d-e8c4-4e65-8bbc-61d390be80d5").observe(lifecycleOwner, data -> {
            if (data != null && data.getStatusCode()==200) {
                Log.d("testSeatApi", "getSeatById: " + data.getData());
            } else {
                Log.e("testSeatApi", "getSeatById failed: " + data);
            }
        });

        // Gọi getAllSeats
        seatRepository.getAllSeats().observe(lifecycleOwner, data -> {
            if (data != null && data.getStatusCode()==200) {
                Log.d("testSeatApi", "getAllSeats: " +data.getData());
//                Log.d("testSeatApi", "getAllSeats: " +gson.toJson(data.getData()));
            } else {
                Log.e("testSeatApi", "getAllSeats failed: " + data);
            }
        });

        // Gọi getSeatsFilter
        seatRepository.getSeatsFilter(0, 10, "asc", "", "none", "name")
                .observe(lifecycleOwner, data -> {
            if (data != null && data.getStatusCode()==200) {
                Log.d("testSeatApi", "getSeatsFilter: " + data.getData());
            } else {
                Log.e("testSeatApi", "getSeatsFilter failed: " + data);
            }
        });
     }

    private void testBillApi() {
        // Test getBillById
        billRepository.getBillById("1").observe(lifecycleOwner, data -> {
            if (data != null && data.getStatusCode()==200) {
                Log.d("testBillApi", "getBillById: " + data.getData());
            } else {
                Log.e("testBillApi", "getBillById failed: " + data);
            }
        });

        // Test addBill
        Bill newBill = new Bill(); // khởi tạo bill mới, set dữ liệu nếu cần
        billRepository.addBill(newBill).observe(lifecycleOwner, data -> {
            if (data != null ) {
                Log.d("testBillApi", "addBill: " + data.getData());
            } else {
                Log.e("testBillApi", "addBill failed: " + data);
            }
        });

        // Test payment
        Map<String, Object> paymentData = new HashMap<>();
        paymentData.put("billId", "1");
        paymentData.put("amount", 100.0);
        billRepository.payment(paymentData).observe(lifecycleOwner, data -> {
            if (data != null && data.getStatusCode()==200) {
                Log.d("testBillApi", "payment: " + data.getData());
            } else {
                Log.e("testBillApi", "payment failed: " + data);
            }
        });
    }


    private void testFoodApi() {
        // Test lấy tất cả món ăn
        foodRepository.getAllFoods().observe(lifecycleOwner, data -> {
            if (data != null &&data.getStatusCode()==200) {
                Log.d("testFoodApi", "getAllFoods: " + data.getData());
            } else {
                Log.e("testFoodApi", "getAllFoods failed: " + data);
            }
        });

        // Test lấy món ăn theo ID
        foodRepository.getFoodById("3361ff55-72ea-4d7b-b66d-bcf7352bf9ee").observe(lifecycleOwner, data -> {
            if (data != null &&data.getStatusCode()==200) {
                Log.d("testFoodApi", "getFoodById: " + data.getData());
            } else {
                Log.e("testFoodApi", "getFoodById failed: " + data);
            }
        });

        // Test lấy tất cả loại món ăn
        foodRepository.getAllFoodTypes().observe(lifecycleOwner, data -> {
            if (data != null &&data.getStatusCode()==200) {
                Log.d("testFoodApi", "getAllFoodTypes: " + data.getData());
            } else {
                Log.e("testFoodApi", "getAllFoodTypes failed: " + data);
            }
        });

        // Test lấy loại món ăn theo ID
        foodRepository.getFoodTypeById("7b2ae9d0-3aab-4809-9060-282a257eeea9").observe(lifecycleOwner, data -> {
            if (data != null &&data.getStatusCode()==200) {
                Log.d("testFoodApi", "getFoodTypeById: " + data.getData());
            } else {
                Log.e("testFoodApi", "getFoodTypeById failed: " + data);
            }
        });
    }

    private void testTheaterApi() {
        // Test lấy theater theo ID
        theaterRepository.getTheaterById("739baa73-1d11-47c3-a049-c37da5f3da2b")
                .observe(lifecycleOwner, data -> {
                    if (data != null &&data.getStatusCode()==200) {
                        Log.d("testTheaterApi", "getTheaterById: " + data.getData());
                    } else {
                        Log.e("testTheaterApi", "getTheaterById failed: " + data);
                    }
                });

        // Test lấy tất cả theater
        theaterRepository.getAllTheaters()
                .observe(lifecycleOwner, data -> {
                    if (data != null &&data.getStatusCode()==200) {
                        Log.d("testTheaterApi", "getAllTheaters: " + data.getData());
                    } else {
                        Log.e("testTheaterApi", "getAllTheaters failed: " + data);
                    }
                });

        // Test lọc theater có phân trang
        theaterRepository.getTheatersFilter("0", "10", "asc", "none", null, "nameBranch")
                .observe(lifecycleOwner, data -> {
                    if (data != null &&data.getStatusCode()==200) {
                        Log.d("testTheaterApi", "getTheatersFilter: " + data.getData());
                    } else {
                        Log.e("testTheaterApi", "getTheatersFilter failed: " + data);
                    }
                });

    }

    private void testPaymentApi() {
//        paymentRepository.getAllPayments(token).observe(this, data -> Log.d("testPaymentApi", "Payments: " + data));
    }

    private void testTicketApi() {
        // Test lấy ticket theo ID
        ticketRepository.getTicketById("2")
                .observe(lifecycleOwner, data -> {
                    if (data != null &&data.getStatusCode()==200) {
                        Log.d("testTicketApi", "getTicketById: " + data.getData());
                    } else {
                        Log.e("testTicketApi", "getTicketById failed: " + data);
                    }
                });

        // Test lấy tất cả ticket đang active
        ticketRepository.getAllActiveTickets()
                .observe(lifecycleOwner, data -> {
                    if (data != null &&data.getStatusCode()==200) {
                        Log.d("testTicketApi", "getAllActiveTickets: " + data.getData());
                    } else {
                        Log.e("testTicketApi", "getAllActiveTickets failed: " + data);
                    }
                });

        // Test lọc ticket có phân trang
        ticketRepository.getTicketsFilter("0", "10", "asc", "none", "price", null)
                .observe(lifecycleOwner, data -> {
                    if (data != null &&data.getStatusCode()==200) {
                        Log.d("testTicketApi", "getTicketsFilter: " + data.getData());
                    } else {
                        Log.e("testTicketApi", "getTicketsFilter failed: " + data);
                    }
                });
    }

    private void testSliderApi() {
        sliderRepository.getAllSliders()
                .observe(lifecycleOwner, data ->{
                    if(data != null &&data.getStatusCode()==200){
                        Log.d("testSliderApi","getAllSliders: "+ data.getData());
                    }else{
                        Log.e("testSliderApi","getAllSliders: "+ data);
                    }
                });
    }

    private void testRateApi() {
        // Lấy đánh giá theo phim
        rateRepository.getRateByMovieId("1", 0, 10,
                        "asc", "timeStamp", "ACTIVE")
                .observe(lifecycleOwner, data -> {
                    if (data != null &&data.getStatusCode()==200) {
                        Log.d("testRateApi", "getRateByMovieId: " + data.getData());
                    } else {
                        Log.e("testRateApi", "getRateByMovieId failed: " + data);
                    }
                });

        // Kiểm tra user đã đánh giá chưa
        rateRepository.checkRated("1", "afb2f2c2-e070-42f6-9796-03fcbd085ab2")
                .observe(lifecycleOwner, data -> {
                    if (data != null &&data.getStatusCode()==200) {
                        Log.d("testRateApi", "checkRated: already rated");
                    } else {
                        Log.d("testRateApi", "checkRated: not rated or failed");
                    }
                });

        // Lọc đánh giá
        rateRepository.getRatesWithFilter(0, 10, "", "asc", "timeStamp", "none")
                .observe(lifecycleOwner, data -> {
                    if (data != null &&data.getStatusCode()==200) {
                        Log.d("testRateApi", "getRatesWithFilter: " + data.getData());
                    } else {
                        Log.e("testRateApi", "getRatesWithFilter failed: " + data);
                    }
                });

        // Thêm đánh giá
        Integer star = 5;
        String content = "Phim hay, đáng xem!";
        rateRepository.addRate("afb2f2c2-e070-42f6-9796-03fcbd085ab2",
                        "1",  star, content)
                .observe(lifecycleOwner, data -> {
                    if (data != null &&data.getStatusCode()==200) {
                        Log.d("testRateApi", "addRate success: " + data.getData());
                    } else {
                        Log.e("testRateApi", "addRate failed: " + data);
                    }
                });

        // Xoá đánh giá
        rateRepository.deleteRate("rate-id")
                .observe(lifecycleOwner, data -> {
                    if (data != null &&data.getStatusCode()==200) {
                        Log.d("testRateApi", "deleteRate success");
                    } else {
                        Log.e("testRateApi", "deleteRate failed: " + data);
                    }
                });
    }

    private void testMovieShowtimeApi() {
        movieShowtimeRepository.getShowtimesByRoomId("1", "2025-03-17")
                .observe(lifecycleOwner, data -> {
                    if (data != null &&data.getStatusCode()==200) {
                        Log.d("testShowtimeApi", "getShowtimesByRoomId: " + data.getData());
                    } else {
                        Log.e("testShowtimeApi", "getShowtimesByRoomId failed: " + data);
                    }
                });

        // Lấy lịch chiếu theo rạp và phim
        //http://localhost:8888/filmshowtime-service/api/filmshowtime/get/739baa73-1d11-47c3-a049-c37da5f3da2b/2025-03-21/9d7226db-6e50-4370-a50b-f0f2b5f28024/1
        movieShowtimeRepository.getShowtimesByMovieAndTheater("739baa73-1d11-47c3-a049-c37da5f3da2b",
                        "2025-03-21", "9d7226db-6e50-4370-a50b-f0f2b5f28024", "1")
                .observe(lifecycleOwner, data -> {
                    if (data != null &&data.getStatusCode()==200) {
                        Log.d("testShowtimeApi", "getShowtimesByMovieAndTheater: " + data.getData());
                    } else {
                        Log.e("testShowtimeApi", "getShowtimesByMovieAndTheater failed: " + data);
                    }
                });
    }

    private void testMovieFormatApi() {
        // Lấy tất cả định dạng phim
        movieFormatRepository.getAllMovieFormats().observe(lifecycleOwner, data -> {
            if (data != null &&data.getStatusCode()==200) {
                Log.d("testMovieFormatApi", "getAllMovieFormats: " + data.getData());
            } else {
                Log.e("testMovieFormatApi", "getAllMovieFormats failed: " + data);
            }
        });

        // Lấy tất cả phim theo định dạng (gộp thông tin phim và định dạng)
        movieFormatRepository.getAllMoviesWithFormats().observe(lifecycleOwner, data -> {
            if (data != null &&data.getStatusCode()==200) {
                Log.d("testMovieFormatApi", "getAllMoviesWithFormats: " + data.getData());
            } else {
                Log.e("testMovieFormatApi", "getAllMoviesWithFormats failed: " + data);
            }
        });

        // Lấy danh sách phim theo ID định dạng
        //http://localhost:8888/film-service/api/subfilm/get/sub/1?page=0&limit=10&asc=asc
        movieFormatRepository.getMoviesByFormatId("1", "0", "10", "asc")
                .observe(lifecycleOwner, data -> {
                    if (data != null &&data.getStatusCode()==200) {
                        Log.d("testMovieFormatApi", "getMoviesByFormatId: " + data.getData());
                    } else {
                        Log.e("testMovieFormatApi", "getMoviesByFormatId failed: " + data);
                    }
                });
    }


    private void testScreenApi() { // Lấy lịch chiếu theo phòng và ngày
        // Lấy screen theo ID
        screenRepository.getScreenById("1")
                .observe(lifecycleOwner, data -> {
                    if (data != null &&data.getStatusCode()==200) {
                        Log.d("testScreenApi", "getScreenById: " + data.getData());
                    } else {
                        Log.e("testScreenApi", "getScreenById failed: " + data);
                    }
                });

        // Lấy tất cả screen
        screenRepository.getAllScreens()
                .observe(lifecycleOwner, data -> {
                    if (data != null &&data.getStatusCode()==200) {
                        Log.d("testScreenApi", "getAllScreens: " + data.getData());
                    } else {
                        Log.e("testScreenApi", "getAllScreens failed: " + data);
                    }
                });

        // Lấy screen theo chi nhánh
        screenRepository.getScreensByBranchId("739baa73-1d11-47c3-a049-c37da5f3da2b")
                .observe(lifecycleOwner, data -> {
                    if (data != null &&data.getStatusCode()==200) {
                        Log.d("testScreenApi", "getScreensByBranchId: " + data.getData());
                    } else {
                        Log.e("testScreenApi", "getScreensByBranchId failed: " + data);
                    }
                });

        // Lọc screen có phân trang
        screenRepository.getScreensFilter(0, 10, "asc", "", "none", "name")
                .observe(lifecycleOwner, data -> {
                    if (data != null &&data.getStatusCode()==200) {
                        Log.d("testScreenApi", "getScreensFilter: " + data.getData());
                    } else {
                        Log.e("testScreenApi", "getScreensFilter failed: " + data);
                    }
                });
    }

}
