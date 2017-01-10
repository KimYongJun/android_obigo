package com.obigo.obigoproject.presenter;

import android.util.Log;

import com.obigo.obigoproject.activity.CarListActivity;
import com.obigo.obigoproject.service.ExceptionService;
import com.obigo.obigoproject.service.ServiceManager;
import com.obigo.obigoproject.vo.LogVO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by O BI HE ROCK on 2017-01-09
 * 김용준, 최현욱
 */

public class ExceptionPresenter {
    private ExceptionService exceptionService;
    private CarListActivity carListActivity;

    public ExceptionPresenter(CarListActivity carListActivity){
        this.carListActivity =carListActivity;
        this.exceptionService = ServiceManager.getInstance().getExceptionService();
    }

    public void errorUserVehicle(LogVO logVO){
        Call<LogVO> call = exceptionService.errorUserVehicle("uservehicle", logVO);
        call.enqueue(new Callback<LogVO>() {
            @Override
            public void onResponse(Call<LogVO> call, Response<LogVO> response) {
                if (response.isSuccessful()) {

                }else {
                    Log.i("error : ", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<LogVO> call, Throwable t) {
                Log.i("error : ", t.getMessage());
            }
        });
    }

}
