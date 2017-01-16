package com.obigo.obigoproject.vo;

/**
 * Created by O BI HE ROCK on 2016-12-14
 * 김용준, 최현욱
 * 사용자 요청 정보
 */

public class UserRequestVO {
    private String userId;
    private String modelCode;
    private String color;
    private String location;
    private String vin;

    public UserRequestVO() {
    }

    public UserRequestVO(String userId, String modelCode, String color, String location, String vin) {
        this.userId = userId;
        this.modelCode = modelCode;
        this.color = color;
        this.location = location;
        this.vin = vin;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getModelCode() {
        return modelCode;
    }

    public void setModelCode(String modelCode) {
        this.modelCode = modelCode;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    @Override
    public String toString() {
        return "UserRequestVO{" +
                ", userId='" + userId + '\'' +
                ", modelCode='" + modelCode + '\'' +
                ", color='" + color + '\'' +
                ", location='" + location + '\'' +
                ", vin='" + vin + '\'' +
                '}';
    }
}
