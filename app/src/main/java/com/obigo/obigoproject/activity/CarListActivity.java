package com.obigo.obigoproject.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.obigo.obigoproject.R;
import com.obigo.obigoproject.adapter.CarListAdapter;
import com.obigo.obigoproject.presenter.ExceptionPresenter;
import com.obigo.obigoproject.presenter.UserVehiclePresenter;
import com.obigo.obigoproject.vo.LogVO;
import com.obigo.obigoproject.vo.UserVehicleVO;
import com.viewpagerindicator.PageIndicator;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.obigo.obigoproject.util.ConstantsUtil.AUTO_USER_ID;
import static com.obigo.obigoproject.util.ConstantsUtil.SERVER_API_URL;
import static com.obigo.obigoproject.util.ConstantsUtil.SERVER_VEHICLE_IMAGE_URL;
import static com.obigo.obigoproject.util.ConstantsUtil.USER_ID;


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
    // Retrofit 에러 보내기
    private ExceptionPresenter exceptionPresenter;
    //뒤로가기
    private boolean isSecond = false;  // 두번째 클릭인지 체크
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("CAR LIST");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_list);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ButterKnife.bind(this);

        // 차량 요청 객체 생성
        userVehiclePresenter = new UserVehiclePresenter(this, USER_ID);
        userVehiclePresenter.getUserVehicleList();

        //에러 서버에 보내기 객체 생성
        exceptionPresenter = new ExceptionPresenter(this);

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
                    Glide.with(getApplicationContext()).load(SERVER_API_URL + SERVER_VEHICLE_IMAGE_URL +
                            userVehicleList.get(lastPage).getModelImage())
                            .listener(new RequestListener<String, GlideDrawable>() {
                                @Override
                                public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                    LogVO logVO = new LogVO(USER_ID, "잘못된 파일 : " +  model);

                                    exceptionPresenter.errorUserVehicle(logVO);
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                    return false;
                                }
                            })
                            .into(fadeinImage);

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

                        @Override
                        public void onAnimationEnd(Animation animation) {
                        }


                        @Override
                        public void onAnimationRepeat(Animation animation) {
                        }
                    });

                    fadeoutImage.startAnimation(outAnimation);
                    fadeinImage.startAnimation(inAnimation);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

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
            icons[i] = SERVER_API_URL + SERVER_VEHICLE_IMAGE_URL +
                    userVehicleList.get(i).getModelImage();
        }

        carNameList = new String[userVehicleList.size()];

        // 차량 이름 리스트 넣기
        for (int i = 0; i < userVehicleList.size(); i++) {
            carNameList[i] = userVehicleList.get(i).getModelName();
        }

        // userVehicleList data가 있을 경우 처음 이미지 고정 데이터 넣기
        if (userVehicleList.size() > 0) {
            Glide.with(this).load(SERVER_API_URL + SERVER_VEHICLE_IMAGE_URL
                    + userVehicleList.get(0).getModelImage())
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            LogVO logVO = new LogVO(USER_ID, "잘못된 파일 : " +  model);

                            exceptionPresenter.errorUserVehicle(logVO);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            return false;
                        }
                    }).into(currentCarListImage);
        }

        nextCarListImage.setVisibility(View.GONE);
        viewPager.setAdapter(new CarListAdapter(this, carNameList, userVehicleList));
        viewPager.setPageMargin(0);
        viewPager.setOffscreenPageLimit(1);
        indicator.setViewPager(viewPager);
    }

    // back 키 이벤트
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (isSecond == false) { // 첫번째인 경우
                Toast.makeText(this, "뒤로 버튼을 한번 더 누르면 종료합니다.", Toast.LENGTH_LONG).show();
                isSecond = true;
                //Back키가 2초내에 두번 눌렸는지 감지
                TimerTask second = new TimerTask() {
                    @Override
                    public void run() {

                        //자동 로그인 아닌 경우 종료시 registrationId 삭제
                        if (AUTO_USER_ID == "") {
                            userPresenter.deleteRegistrationId(registrationId);
                        }
                        timer.cancel();
                        timer = null;
                        isSecond = false;
                    }
                };
                if (timer != null) {
                    timer.cancel();
                    timer = null;
                }
                timer = new Timer();
                timer.schedule(second, 2000);
            } else {
                super.onBackPressed();
            }
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        //자동 로그인 아닌 경우 종료시 registrationId 삭제
        if (AUTO_USER_ID == "") {
            userPresenter.deleteRegistrationId(registrationId);
        }
        super.onDestroy();
    }
}
