package com.obigo.obigoproject.service;

import com.obigo.obigoproject.vo.LogVO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by O BI HE ROCK on 2017-01-09
 * 김용준, 최현욱
 */

public interface ExceptionService {
    @POST("errorlog/{url}")
    Call<LogVO> errorUserVehicle(@Path("url") String url, @Body LogVO logVO);
}
