package com.obigo.obigoproject.presenter;

import android.util.Log;

import com.obigo.obigoproject.activity.SplashActivity;
import com.obigo.obigoproject.service.BundleService;
import com.obigo.obigoproject.service.ServiceManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by O BI HE ROCK on 2016-12-22
 * 김용준, 최현욱
 */

public class BundlePresenter {

    private BundleService bundleService;
    private SplashActivity splashActivity;

    //BundleVersion 체크 동일 여부
    private String bundleVersionCheckFlag;

    public BundlePresenter(SplashActivity splashActivity){
        this.splashActivity =splashActivity;
        this.bundleService = ServiceManager.getInstance().getBundleService();
    }

    //Bundle Version 체크
    public void bundleVersionCheck(String bundleVersion){
        Call<String> call = bundleService.bundleVersionCheck(bundleVersion);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    bundleVersionCheckFlag = response.body();
                    splashActivity.dispatchBundleVersionCheck(bundleVersionCheckFlag);
                    Log.i("BundleVersion : ", bundleVersionCheckFlag);
                }else {
                    Log.i("error : ", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.i("error : ", t.getMessage());
            }
        });
    }

}
