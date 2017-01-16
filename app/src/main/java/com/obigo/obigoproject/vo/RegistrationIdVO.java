package com.obigo.obigoproject.vo;

/**
 * Created by O BI HE ROCK on 2016-12-14
 * 김용준, 최현욱
 * FCM RegistrationId 정보
 */

public class RegistrationIdVO {
    private String userId;
    private String registrationId;

    public RegistrationIdVO() {
    }

    public RegistrationIdVO(String userId, String registrationId) {
        this.userId = userId;
        this.registrationId = registrationId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    @Override
    public String toString() {
        return "RegistrationIdVO{" +
                "userId='" + userId + '\'' +
                ", registrationId='" + registrationId + '\'' +
                '}';
    }
}
