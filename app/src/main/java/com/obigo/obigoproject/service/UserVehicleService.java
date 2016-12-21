package com.obigo.obigoproject.service;

import com.obigo.obigoproject.vo.UserVehicleVO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by O BI HE ROCK on 2016-12-12
 * 김용준, 최현욱
 */

public interface UserVehicleService {
    // 해당 사용자 보유 차량 리스트 요청 - 데이터는 path로 전송 userid
    @GET("uservehicle/{userid}")
    Call<List<UserVehicleVO>> getUserVehicleList(@Path("userid") String userId);
}
