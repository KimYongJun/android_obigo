package com.obigo.obigoproject.vo;

import java.io.Serializable;

/**
 * Created by O BI HE ROCK on 2016-12-07
 * 김용준, 최현욱
 * 사용자 차량 정보
 */

public class UserVehicleVO implements Serializable {
    private String userId;
    private String modelCode;
    private String color;
    private String location;
    private String vin;
    private int activeDtcCount;
    private String modelName;
    private String modelImage;
    private String detailImage;
    private int modelYear;
    private String engine;
    private String mileage;

    public String getModelCode() {
        return modelCode;
    }

    public void setModelCode(String modelCode) {
        this.modelCode = modelCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public int getActiveDtcCount() {
        return activeDtcCount;
    }

    public void setActiveDtcCount(int activeDtcCount) {
        this.activeDtcCount = activeDtcCount;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getModelImage() {
        return modelImage;
    }

    public void setModelImage(String modelImage) {
        this.modelImage = modelImage;
    }

    public String getDetailImage() {
        return detailImage;
    }

    public void setDetailImage(String detailImage) {
        this.detailImage = detailImage;
    }

    public int getModelYear() {
        return modelYear;
    }

    public void setModelYear(int modelYear) {
        this.modelYear = modelYear;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    @Override
    public String toString() {
        return "UserVehicleVO{" +
                "userId='" + userId + '\'' +
                ", modelCode='" + modelCode + '\'' +
                ", color='" + color + '\'' +
                ", location='" + location + '\'' +
                ", vin='" + vin + '\'' +
                ", activeDtcCount=" + activeDtcCount +
                ", modelName='" + modelName + '\'' +
                ", modelImage='" + modelImage + '\'' +
                ", detailImage='" + detailImage + '\'' +
                ", modelYear=" + modelYear +
                ", engine='" + engine + '\'' +
                ", mileage='" + mileage + '\'' +
                '}';
    }

}
