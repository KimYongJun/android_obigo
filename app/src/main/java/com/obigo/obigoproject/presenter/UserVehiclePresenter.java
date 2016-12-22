package com.obigo.obigoproject.presenter;

import android.util.Log;

import com.obigo.obigoproject.activity.CarListActivity;
import com.obigo.obigoproject.service.ServiceManager;
import com.obigo.obigoproject.service.UserVehicleService;
import com.obigo.obigoproject.vo.UserVehicleVO;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by O BI HE ROCK on 2016-12-12
 * 김용준, 최현욱
 */

public class UserVehiclePresenter {
    private UserVehicleService userVehicleService;
    private CarListActivity carListActivity;

    private String userId;
    private List<UserVehicleVO> userVehicleList;

    public UserVehiclePresenter(CarListActivity carListActivity, String userId) {
        this.carListActivity = carListActivity;
        this.userVehicleService = ServiceManager.getInstance().getUserVehicleService();
        this.userId = userId;
    }

    //유저 소유 차량 리스트 요청
    public void getUserVehicleList() {
        Log.i("userId : ", userId);
        Call<List<UserVehicleVO>> call = userVehicleService.getUserVehicleList(userId);
        call.enqueue(new Callback<List<UserVehicleVO>>() {
            @Override
            public void onResponse(Call<List<UserVehicleVO>> call, Response<List<UserVehicleVO>> response) {
                if (response.isSuccessful()) {
                    userVehicleList = response.body();
                    Log.i("user : ", UserVehiclePresenter.this.userVehicleList.toString());
                    carListActivity.dispatchUserVehicleInfo(userVehicleList);
                } else {
                    Log.i("error : ", response.errorBody().toString());
                }

            }

            // 서버와 접속 실패
            @Override
            public void onFailure(Call<List<UserVehicleVO>> call, Throwable t) {
                Log.i("에러 : ", t.getMessage());
            }
        });
    }
}
