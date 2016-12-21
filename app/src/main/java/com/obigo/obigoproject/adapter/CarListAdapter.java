package com.obigo.obigoproject.adapter;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.obigo.obigoproject.R;
import com.obigo.obigoproject.activity.CarDetailActivity;
import com.obigo.obigoproject.activity.CarListActivity;
import com.obigo.obigoproject.util.FlipperUtil;
import com.obigo.obigoproject.vo.UserVehicleVO;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by O BI HE ROCK on 2016-12-20
 * 김용준, 최현욱
 * 차량 이름 및 CarDetail 정보 확인
 */

public class CarListAdapter extends PagerAdapter {
    // Activity setting
    private CarListActivity carListActivity;
    // 차량 이름 정보
    private String[] carNameList;
    // 차량 정보
    private List<UserVehicleVO> userVehicleList;

    public CarListAdapter(CarListActivity carListActivity, String[] carNameList, List<UserVehicleVO> userVehicleList) {
        this.carListActivity = carListActivity;
        this.carNameList = carNameList;
        this.userVehicleList = userVehicleList;
    }

    // 보유한 차량 갯수
    @Override
    public int getCount() {
        return userVehicleList.size();
    }

    // 페이지 보여주기
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    // 차량 이미지 마다 이름 넣기
    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = View.inflate(container.getContext(), R.layout.car_list_name, null);
        TextView carNameTextView = ButterKnife.findById(view, R.id.message_text);

        // 차량 이름 view
        carNameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 클릭 했을 때 CarDetailActivity로 차량 데이터를 전달
                Intent intent = new Intent(carListActivity, CarDetailActivity.class);
                intent.putExtra("carDetailInfo", userVehicleList.get(position));
                carListActivity.startActivity(intent);
            }
        });

        container.addView(view, 0);
        carNameTextView.setText(FlipperUtil.replaceTags(carNameList[position], carListActivity.getApplicationContext()));
        return view;
    }

    // view 삭제
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
