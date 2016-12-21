package com.obigo.obigoproject.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.obigo.obigoproject.R;
import com.obigo.obigoproject.adapter.CarListAdapter;
import com.obigo.obigoproject.presenter.UserVehiclePresenter;
import com.obigo.obigoproject.util.ConstantsUtil;
import com.obigo.obigoproject.vo.UserVehicleVO;
import com.viewpagerindicator.PageIndicator;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by O BI HE ROCK on 2016-11-28
 * 김용준, 최현욱
 * 차량 리스트를 보여주는 페이지 - user_id
 * API : /api/carlist
 * model_name, model_image, vin
 */

public class CarListActivity extends MenuActivity {
    // view pager
    @Bind(R.id.car_list_view)
    ViewPager viewPager;
    @Bind(R.id.indicator)
    PageIndicator indicator;
    // 차량 현재 이미지
    @Bind(R.id.current_icon_image)
    ImageView currentCarListImage;
    // 차량 다음 이미지
    @Bind(R.id.next_icon_image)
    ImageView nextCarListImage;
    // 페이지 위치 지정
    private int lastPage = 0;
    // 이미지 배열
    private String[] icons;
    // 차량 이름 리스트
    private String[] carNameList;

    // Retrofit 차량 요청
    private UserVehiclePresenter userVehiclePresenter;
    // 차량 리스트
    private List<UserVehicleVO> userVehicleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_list);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setTitle("CAR LIST");
        ButterKnife.bind(this);

        // 차량 요청 객체 생성
        userVehiclePresenter = new UserVehiclePresenter(this, ConstantsUtil.TEST_USER_ID);
        userVehiclePresenter.getUserVehicleList();

        initAdapter();
    }

    protected void initAdapter() {
        // image change
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                if (lastPage != viewPager.getCurrentItem()) {
                    lastPage = viewPager.getCurrentItem();

                    final ImageView fadeoutImage;
                    final ImageView fadeinImage;
                    if (currentCarListImage.getVisibility() == View.VISIBLE) {
                        fadeoutImage = currentCarListImage;
                        fadeinImage = nextCarListImage;

                    } else {
                        fadeoutImage = nextCarListImage;
                        fadeinImage = currentCarListImage;
                    }

                    fadeinImage.bringToFront();
                    // 이미지 넣기
                    Glide.with(getApplicationContext()).load(ConstantsUtil.SERVER_API_URL_REAL + ConstantsUtil.SERVER_VEHICLE_IMAGE_URL +
                            userVehicleList.get(lastPage).getModelImage()).into(fadeinImage);
                    fadeinImage.clearAnimation();
                    fadeoutImage.clearAnimation();

                    Animation outAnimation = AnimationUtils.loadAnimation(CarListActivity.this, R.anim.icon_anim_fade_out);
                    outAnimation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            fadeoutImage.setVisibility(View.GONE);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });

                    Animation inAnimation = AnimationUtils.loadAnimation(CarListActivity.this, R.anim.icon_anim_fade_in);
                    inAnimation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                            fadeinImage.setVisibility(View.VISIBLE);
                        }

                        // 사용하지 않는 메소드
                        @Override
                        public void onAnimationEnd(Animation animation) {
                        }

                        // 사용하지 않는 메소드
                        @Override
                        public void onAnimationRepeat(Animation animation) {
                        }
                    });

                    fadeoutImage.startAnimation(outAnimation);
                    fadeinImage.startAnimation(inAnimation);
                }
            }


            // 사용하지 않는 메소드
            @Override
            public void onPageSelected(int position) {

            }

            // 사용하지 않는 메소드
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    public void dispatchUserVehicleInfo(List<UserVehicleVO> userVehicleList) {
        this.userVehicleList = userVehicleList;
        initVariable();
    }

    private void initVariable() {
        icons = new String[userVehicleList.size()];

        // 이미지 파일 리스트 넣기
        for (int i = 0; i < userVehicleList.size(); i++) {
            icons[i] = ConstantsUtil.SERVER_API_URL_REAL + ConstantsUtil.SERVER_VEHICLE_IMAGE_URL +
                    userVehicleList.get(i).getModelImage();
        }

        carNameList = new String[userVehicleList.size()];

        // 차량 이름 리스트 넣기
        for (int i = 0; i < userVehicleList.size(); i++) {
            carNameList[i] = userVehicleList.get(i).getModelName();
        }

        // userVehicleList data가 있을 경우 처음 이미지 고정 데이터 넣기
        if(userVehicleList.size() > 0) {
            Glide.with(this).load(ConstantsUtil.SERVER_API_URL_REAL + ConstantsUtil.SERVER_VEHICLE_IMAGE_URL
                    + userVehicleList.get(0).getModelImage()).into(currentCarListImage);
        }

        nextCarListImage.setVisibility(View.GONE);
        viewPager.setAdapter(new CarListAdapter(this, carNameList, userVehicleList));
        viewPager.setPageMargin(0);
        viewPager.setOffscreenPageLimit(1);
        indicator.setViewPager(viewPager);
    }
}
