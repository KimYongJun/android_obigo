package com.obigo.obigoproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.obigo.obigoproject.R;
import com.obigo.obigoproject.util.ConstantsUtil;
import com.obigo.obigoproject.vo.UserVehicleVO;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by O BI HE ROCK on 2016-12-13
 * 김용준, 최현욱
 * 차량 데이터는 CarListActivity에서 Intent로 데이터를 전송 받음
 */

public class CarDetailActivity extends AppCompatActivity {
    // 차량 세부사항 이미지
    @Bind(R.id.detail_image) ImageView carDetailImage;
    // 모델 이름
    @Bind(R.id.detail_model_name) TextView modelNameTextView;
    // 모델 코드
    @Bind(R.id.detail_model_code) TextView modelCodeTextView;
    // 모델 년도
    @Bind(R.id.detail_model_year) TextView modelYearTextView;
    // 차량 엔진
    @Bind(R.id.detail_engine) TextView engineTextView;
    // 사용자 차량 vin
    @Bind(R.id.detail_vin) TextView vinTextView;
    // 차량 Color
    @Bind(R.id.detail_model_color) TextView colorTextView;
    // 차량 location
    @Bind(R.id.detail_location) TextView locationTextView;
    // 차량 연비
    @Bind(R.id.detail_mileage) TextView mileageTextView;
    // 고장 코드
    @Bind(R.id.detail_active_dtc_count) TextView activeDtcCountTextView;

    // 차량 정보 (CarListActivity에서 데이터를 Intent로 받음)
    private UserVehicleVO userVehicleVO;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_detail);
        ButterKnife.bind(this);

        // 차량 데이터를 CarListActivity에서 전송 받음
        Intent intent = getIntent();
        userVehicleVO = (UserVehicleVO) intent.getSerializableExtra("carDetailInfo");

        // 차량 데이터 초기화
        initVariable();
    }


    // 전달 받은 차량정보를 입력
    private void initVariable() {
        // Glide로 이미지를 받음
        Glide.with(this).load(ConstantsUtil.SERVER_API_URL_REAL + ConstantsUtil.SERVER_VEHICLE_IMAGE_URL + userVehicleVO.getModelImage()).into(carDetailImage);

        modelNameTextView.setText(userVehicleVO.getModelName());
        modelCodeTextView.setText(userVehicleVO.getModelCode());
        modelYearTextView.setText(Integer.toString(userVehicleVO.getModelYear()));
        engineTextView.setText(userVehicleVO.getEngine());
        vinTextView.setText(userVehicleVO.getVin());
        colorTextView.setText(userVehicleVO.getColor());
        locationTextView.setText(userVehicleVO.getLocation());
        mileageTextView.setText(userVehicleVO.getMileage());
        activeDtcCountTextView.setText(Integer.toString(userVehicleVO.getActiveDtcCount()));
    }
}
