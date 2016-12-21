package com.obigo.obigoproject.service;

import com.obigo.obigoproject.vo.MessageVO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


/**
 * Created by O BI HE ROCK on 2016-12-14
 * 김용준, 최현욱
 */

public interface MessageService {
    // 사용자 메시지 리스트 요청 - 데이터는 path로 전송 userid
    @GET("message/{userid}")
    Call<List<MessageVO>> getMessageList(@Path("userid") String userId);
}
