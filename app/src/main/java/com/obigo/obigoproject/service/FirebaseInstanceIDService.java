package com.obigo.obigoproject.service;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by O BI HE ROCK on 2016-12-14
 * 김용준, 최현욱
 * FirebaseInstanceIDService onTokenRefresh를 통해 registrationId(token)를 발급받음
 */

public class FirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = "FirebaseIIDService";

    // 메시지 발급 작업
    @Override
    public void onTokenRefresh() {
        String registrationId = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + registrationId);
    }
}