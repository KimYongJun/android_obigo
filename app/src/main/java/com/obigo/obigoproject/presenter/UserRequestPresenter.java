package com.obigo.obigoproject.presenter;

import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.obigo.obigoproject.R;
import com.obigo.obigoproject.activity.RequestActivity;
import com.obigo.obigoproject.service.ServiceManager;
import com.obigo.obigoproject.service.UserRequestService;
import com.obigo.obigoproject.vo.UserRequestVO;
import com.obigo.obigoproject.vo.VehicleVO;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by O BI HE ROCK on 2016-12-14
 * 김용준, 최현욱
 */

public class UserRequestPresenter {
    private UserRequestService userRequestService;
    private RequestActivity requestActivity;
    private String resultFlag;
    private List<VehicleVO> vehicleList;
    private List<String> modelNameList = new ArrayList<>();
    private List<String> modelCodeList = new ArrayList<>();
    private ArrayAdapter<String> spinnerArrayAdapter;
    private Spinner code;


    public UserRequestPresenter(RequestActivity requestActivity, Spinner code) {
        this.requestActivity = requestActivity;
        this.userRequestService = ServiceManager.getInstance().getUserRequestService();
        this.code = code;
    }

    //차량리스 정보 요청
    public void getVehicleList() {
        Call<List<VehicleVO>> call = userRequestService.getVehicleList();
        call.enqueue(new Callback<List<VehicleVO>>() {
            @Override
            public void onResponse(Call<List<VehicleVO>> call, Response<List<VehicleVO>> response) {
                if (response.isSuccessful()) {
                    vehicleList = response.body();
                    Log.i("vehicleList : ", UserRequestPresenter.this.vehicleList.toString());
                    for (int i = 0; i < vehicleList.size(); i++) {
                        modelNameList.add(i, vehicleList.get(i).getModelName());
                        modelCodeList.add(i, vehicleList.get(i).getModelCode());
                    }
                    requestActivity.dispatchVehicleInfo(modelNameList, modelCodeList);

                    spinnerArrayAdapter = new ArrayAdapter<String>(requestActivity, R.layout.spinner_item_position, modelNameList);
                    spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item_position);
                    code.setAdapter(spinnerArrayAdapter);

                } else {
                    Log.i("error : ", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<List<VehicleVO>> call, Throwable t) {
                Log.i("에러 : ", t.getMessage());
            }
        });
    }


    //Request 정보 넘겨주기(서버에 Request 등록 요청)
    public void insertUserRequest(final UserRequestVO userRequestVO) {
        Log.i("userRequest : ", userRequestVO.toString());
        Call<String> call = userRequestService.insertUserRequest(userRequestVO);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    resultFlag = response.body();
                    requestActivity.dispatchRequestInfo(resultFlag);
                    Log.i("succes : ", resultFlag);
                } else {
                    Log.i("error : ", response.errorBody().toString());
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.i("에러 : ", t.getMessage());
            }
        });
    }

}
