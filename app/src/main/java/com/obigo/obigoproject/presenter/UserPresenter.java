package com.obigo.obigoproject.presenter;

import android.util.Log;
import android.widget.Toast;

import com.obigo.obigoproject.activity.LoginActivity;
import com.obigo.obigoproject.activity.MenuActivity;
import com.obigo.obigoproject.preference.UserInfoButtonPreference;
import com.obigo.obigoproject.service.ServiceManager;
import com.obigo.obigoproject.service.UserService;
import com.obigo.obigoproject.vo.RegistrationIdVO;
import com.obigo.obigoproject.vo.UserVO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by O BI HE ROCK on 2016-12-14
 * 김용준, 최현욱
 */

public class UserPresenter {
    private UserService userService;
    private LoginActivity loginActivity;
    private MenuActivity menuActivity;
    private UserInfoButtonPreference userInfoButtonPreference;
    //유저 정보
    private String userId;
    private UserVO userVO;
    //로그인 성공 실패 결과
    private String loginResultFlag;

    // 로그인 생성자
    public UserPresenter(LoginActivity loginActivity) {
        this.loginActivity = loginActivity;
        this.userService = ServiceManager.getInstance().getUserService();
    }


    public UserPresenter(UserInfoButtonPreference userInfoButtonPreference, String userId) {
        this.userInfoButtonPreference = userInfoButtonPreference;
        this.userId = userId;
        this.userService = ServiceManager.getInstance().getUserService();
    }


    public UserPresenter(MenuActivity menuActivity, String userId){
        this.userId = userId;
        this.userService = ServiceManager.getInstance().getUserService();
        this.menuActivity = menuActivity;
    }

    //로그인 (서버에 userId,password 조회)
    public void login(String userId,String password){
        Call<String> call = userService.login(userId,password);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    loginResultFlag = response.body();
                    loginActivity.dispatchLoginResult(loginResultFlag);
                    Log.i("login success : ", loginResultFlag);
                }else {
                    Log.i("error : ", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(loginActivity.getBaseContext(), "서버와 연결이 되지 않습니다.", Toast.LENGTH_LONG).show();
            }
        });

    }


    //서버 Registration 정보 넘겨주기 (서버에 registrationId 등록 요청)
    public void insertRegistrationId(RegistrationIdVO registrationIdVO) {
        Log.i("registrationIdVO : ", registrationIdVO.toString());
        Call<String> call = userService.insertRegistrationId(registrationIdVO);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Log.i("response : ", response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
            }
        });
    }

    // 서버에 RegistrationId 넘겨주고 (서버에 registrationId 삭제 요청)
    public void deleteRegistrationId(String registrationId){
        Log.i("registrationId :  ", registrationId);
        Call<String> call =userService.deleteRegistrationId(registrationId);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Log.i("success : ", response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
            }
        });
     }

    // 유저정보 요청
    public void getUser() {
        Log.i("userId  : ", userId);
        Call<UserVO> call = userService.getUser(userId);
        call.enqueue(new Callback<UserVO>() {
            @Override
            public void onResponse(Call<UserVO> call, Response<UserVO> response) {
                if (response.isSuccessful()) {
                    userVO = response.body();
                    Log.i("userVO : ", UserPresenter.this.userVO.toString());
                    userInfoButtonPreference.dispatchUserInfo(userVO);
                } else {
                    Log.i("error : ", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<UserVO> call, Throwable t) {
                System.out.println("서버와 연결이 되지 않습니다.");
            }
        });
    }

}
