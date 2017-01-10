package com.obigo.obigoproject.vo;

/**
 * Created by O BI HE ROCK  on 2016-12-16
 * 김용준, 최현욱
 * 차량 정보
 */

public class VehicleVO {

    private String modelName;
    private String modelCode;
    private String modelImage;
    private String detailImage;
    private String engine;
    private int modelYear;
    private String mileage;

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public void setModelYear(int modelYear) {
        this.modelYear = modelYear;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public void setDetailImage(String detailImage) {
        this.detailImage = detailImage;
    }

    public void setModelImage(String modelImage) {
        this.modelImage = modelImage;
    }

    public void setModelCode(String modelCode) {
        this.modelCode = modelCode;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getModelName() {
        return modelName;
    }

    public String getModelCode() {
        return modelCode;
    }

    public String getModelImage() {
        return modelImage;
    }

    public String getDetailImage() {
        return detailImage;
    }

    public String getEngine() {
        return engine;
    }

    public int getModelYear() {
        return modelYear;
    }

    public String getMileage() {
        return mileage;
    }

    @Override
    public String toString() {
        return "VehicleVO{" +
                "modelName='" + modelName + '\'' +
                ", modelCode='" + modelCode + '\'' +
                ", modelImage='" + modelImage + '\'' +
                ", detailImage='" + detailImage + '\'' +
                ", engine='" + engine + '\'' +
                ", modelYear=" + modelYear +
                ", mileage='" + mileage + '\'' +
                '}';
    }
}
