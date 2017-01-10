package com.obigo.obigoproject.util;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by O BE HI ROCK on 2016-12-08.
 * 김용준, 최현욱
 * 서버에 접속하는 공통 URL
 */

public class ConstantsUtil extends AppCompatActivity {
    // 서버 경로
    public static String SERVER_API_URL = //"http://192.168.1.2/obigoProject/api/"; //성현
                                             "http://192.168.1.14/obigoProject/api/";//유현
                                            //http://52.78.201.143:8080/obigoProject/api/

    // APK File 경로
    public static final String APK_URL = SERVER_API_URL + "bundledown";

    // Vehicle Image File 경로
    public static String SERVER_VEHICLE_IMAGE_URL = "image/vehicle/";

    // Push Message Image File 경로
    public static String SERVER_MESSAGE_IMAGE_URL = "image/pushmessage/";

    //로그인 성공시 등록될 ID
    public static String USER_ID;

    //자동 로그인이 아닐 경우 유지할 ID
    public static String UNCHECK_USER_ID;

    //Application BundleVersion
    public static String bundleVersion ="1.0";
}

