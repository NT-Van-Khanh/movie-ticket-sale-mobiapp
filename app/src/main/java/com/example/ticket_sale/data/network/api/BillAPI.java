package com.example.ticket_sale.data.network.api;

import com.example.ticket_sale.data.dto.BillRequestDTO;
import com.example.ticket_sale.data.dto.BillResponseDTO;
import com.example.ticket_sale.data.dto.PaymentDTO;
import com.example.ticket_sale.data.network.ApiResponse;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BillAPI {
    String BASE_PATH = "/payment-service/api/bill";
    //099587da-c816-49f7-8378-b9e043c74225
    @GET(BASE_PATH + "/{id}")
    Call<ApiResponse<BillResponseDTO>> getBillById(@Path("id") String id);

    @GET(BASE_PATH + "/get/{customerId}")
    Call<ApiResponse<List<BillResponseDTO>>> getBillByCustomerId(@Path("customerId") String customerId);


    @POST(BASE_PATH + "/add")
    Call<ApiResponse<BillResponseDTO>> addBill(@Body BillRequestDTO bill);

    @POST(BASE_PATH + "/payment")
    Call<ApiResponse<BillResponseDTO>> payment(@Body PaymentDTO payment);


//    {
//        "additionalProp1": {},
//        "additionalProp2": {},
//        "additionalProp3": {}
//    }
}
//add bill

//        {
//        "totalPrice": 100000,
//        "transactionCode": "pi_3R9iCqBcNft3NmLQ0hh0eQkI",
//        "paymentMethodId": "1",
//        "chairs": [{
//            "chairCode": "[3,3]",
//            "price": 100000,
//            "ticket": {
//                "id": "1"
//            }
//        }],
//        "dishes": [{
//            "amount": 2,
//            "price": 50000,
//            "dishDto": {
//                "id": "3361ff55-72ea-4d7b-b66d-bcf7352bf9ee"// đang lỗi, ng dùng đã mua món này -> không thể mua nữa
//            }
//        }],
//        "filmShowTimeId": 13,
//        "roomId": "2",
//        "filmId": "9d7226db-6e50-4370-a50b-f0f2b5f28024",
//        "userName": "Cao Cường",
//        "email": "CaoCuong1@gmail.com",
//        "numberPhone": "0335723811",
//        "customerId": "cd664219-042d-4ffc-b851-5bbba4005d8b"
//        }

//   /payment-service/api/bill/payment
// {
//        "amount": 100000,
//        "id": "1",
//        "currency": "VND",
//        "billId": "75d5ccb7-f43e-4b45-b75e-0807dac3d103"
//        }