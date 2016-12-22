package com.obigo.obigoproject.util;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by O BE HI ROCK on 2016-12-08.
 * 김용준, 최현욱
 * 서버에 접속하는 공통 URL
 */

public class ConstantsUtil extends AppCompatActivity {

    public static String SERVER_API_URL_REAL =  "http://192.168.1.2/obigoProject/api/"; //성현
                                              //"http://192.168.1.7/obigoProject/api/";  //경우형

    public static String SERVER_VEHICLE_IMAGE_URL = "image/vehicle/";
    public static String SERVER_MESSAGE_IMAGE_URL = "image/pushmessage/";

    //로그인 성공시 등록될 ID
    public static String USER_ID;


}

