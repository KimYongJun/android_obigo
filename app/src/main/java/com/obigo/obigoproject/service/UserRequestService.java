package com.obigo.obigoproject.service;

import com.obigo.obigoproject.vo.UserRequestVO;
import com.obigo.obigoproject.vo.VehicleVO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by O BI HE ROCK on 2016-12-14
 * 김용준, 최현욱
 */

public interface UserRequestService {
    // 차량 등록 요청 - 데이터는 @Body로 UserRequest를 JSON으로 전송
    @POST("userrequest/")
    Call<String> insertUserRequest(@Body UserRequestVO userRequestVO);

    //차량 정보 요청
    @GET("vehicle/")
    Call<List<VehicleVO>> getVehicleList();
}
