package com.obigo.obigoproject.service;

import com.obigo.obigoproject.util.ConstantsUtil;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by O BI HE ROCK on 2016-12-07
 * 김용준, 최현욱
 * Retrofit2 Factory
 * 특정 서버와 연결
 * 서버 종류는 Util package에 있는 ConstantsUtil class에 있음
 */

public class RetrofitServiceGenericFactory {
    public static <T> T createService(Class<T> serviceClass) {
        return getRetofitObject().create(serviceClass);
    }

    private static Retrofit getRetofitObject() {
        return
                new Retrofit.Builder()
                        .baseUrl(ConstantsUtil.SERVER_API_URL_REAL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
    }
}
