package com.obigo.obigoproject.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by O BI HE ROCK on 2016-12-22
 * 김용준, 최현욱
 */

public interface BundleService {
    // Bundle Version Check
    @GET("bundleversioncheck/")
    Call<String> bundleVersionCheck(@Query("bundleVersion")String bundleVersion);
}
