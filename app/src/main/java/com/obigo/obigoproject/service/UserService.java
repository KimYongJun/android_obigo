package com.obigo.obigoproject.service;

import com.obigo.obigoproject.vo.RegistrationIdVO;
import com.obigo.obigoproject.vo.UserVO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by O BI HE ROCK on 2016-12-14
 * 김용준, 최현욱
 */

public interface UserService {



    // 특정 사용자 registrationId 저장 (서버에 저장 요청)
    @POST("registrationid/")
    Call<String> insertRegistrationId(@Body RegistrationIdVO registrationIdVO);

    //유저정보 호출
    @GET("user/{userid}")
    Call<UserVO> getUser(@Path("userid") String userId);

    //로그아웃 버튼 누르면 registrationId (서버에 삭제 요청)
    @DELETE("deleteregistrationid/")
    Call<String> deleteRegistrationId(@Query("registrationId") String registrationId );
}
