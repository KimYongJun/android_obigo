package com.obigo.obigoproject.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.obigo.menu.BoomMenuButton;
import com.obigo.menu.Types.BoomType;
import com.obigo.menu.Types.ButtonType;
import com.obigo.menu.Types.DimType;
import com.obigo.menu.Types.PlaceType;
import com.obigo.menu.Util;
import com.obigo.obigoproject.R;
import com.obigo.obigoproject.presenter.UserPresenter;
import com.obigo.obigoproject.util.ConstantsUtil;

import butterknife.ButterKnife;

/**
 * Created by O BI HE ROCK on 2016-11-29
 * 최현욱, 김용준
 * menu list Actionbar
 * 메뉴 Car List, Message, Request, Settings
 * 이 클래스는 각 Menu Activity에 상속
 */

public class MenuActivity extends AppCompatActivity implements
        BoomMenuButton.OnSubButtonClickListener {
    private BoomMenuButton boomMenuButton;
    private BoomMenuButton boomMenuButtonInActionBar;
    private BoomMenuButton boomInfo;

    private Context mContext;
    private View menuActionBarView;
    private View mBottomView;
    private boolean isInit = false;

    //Logout
    UserPresenter userPresenter;
    private Menu logout;
    String registrationId = FirebaseInstanceId.getInstance().getToken();
    SharedPreferences autoSetting;
    SharedPreferences.Editor editor;

    // Actionbar title
    private String title;

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;

        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);

        // ActionBar layout 지정
        menuActionBarView = mInflater.inflate(R.layout.menu_actionbar, null);
        TextView mTitleTextView = ButterKnife.findById(menuActionBarView, R.id.title_text);

        // MenuBar 제목 표시
        mTitleTextView.setText(title);

        boomMenuButtonInActionBar = ButterKnife.findById(menuActionBarView, R.id.boom);

        mActionBar.setCustomView(menuActionBarView);
        mActionBar.setDisplayShowCustomEnabled(true);

        ((Toolbar) menuActionBarView.getParent()).setContentInsetsAbsolute(0, 0);

        // 우측 하단 버튼 (2016-12-01)
        mBottomView = mInflater.inflate(R.layout.menu_button, null);

        boomMenuButton = ButterKnife.findById(mBottomView, R.id.bottom_boom);
        boomInfo = ButterKnife.findById(menuActionBarView, R.id.info);

        //로그아웃버튼
        logout = (Menu) findViewById(R.id.logoutBtn);

        userPresenter = new UserPresenter(this,ConstantsUtil.TEST_USER_ID);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (!isInit) {
            initBoom();
        }
        isInit = true;
        initBoom();
    }

    // 메뉴 초기화 화면
    private void initBoom() {
        //메뉴버튼 수
        int number = 4;

        Drawable[] drawables = new Drawable[number];

        int[] drawablesResource = new int[]{
                R.drawable.menu_car_list,
                R.drawable.menu_message,
                R.drawable.menu_request,
                R.drawable.menu_settings

        };

        for (int i = 0; i < number; i++)
            drawables[i] = ContextCompat.getDrawable(mContext, drawablesResource[i]);

        String[] STRINGS = new String[]{
                "Car List",
                "Message",
                "Request",
                "Settings"
        };

        String[] strings = new String[number];
        for (int i = 0; i < number; i++)
            strings[i] = STRINGS[i];

        // 색상 조절
        int[][] colors = settingMenuColor(number);

        ButtonType buttonType = ButtonType.CIRCLE; //버튼 모양 동그라미

        // 우측 하단 버튼 적용
        new BoomMenuButton.Builder()
                .subButtons(drawables, colors, strings)
                .button(buttonType)
                .boom(getBoomType())
                .place(getPlaceType())
                .boomButtonShadow(Util.getInstance().dp2px(2), Util.getInstance().dp2px(2))
                .subButtonsShadow(Util.getInstance().dp2px(2), Util.getInstance().dp2px(2))
                .onSubButtonClick(this)
                .dim(DimType.DIM_9)
                .init(boomMenuButton);

        // 메뉴 bar에 있는 버튼 적용
        new BoomMenuButton.Builder()
                .subButtons(drawables, colors, strings)
                .button(buttonType)
                .boom(getBoomType())
                .place(getPlaceType())
                .subButtonsShadow(Util.getInstance().dp2px(2), Util.getInstance().dp2px(2))
                .onSubButtonClick(this)
                .dim(DimType.DIM_9)
                .init(boomMenuButtonInActionBar);
    }

    // 버튼 BoomType 조정
    private BoomType getBoomType() {
        return BoomType.PARABOLA;
    }

    // 버튼 모양 조정
    private PlaceType getPlaceType() {
        return PlaceType.CIRCLE_4_1;
    }

    // 메뉴 색 지정 메소드
    private int[][] settingMenuColor(int number) {
        int[][] colors = new int[number][2];

        String[] colorName = {
                "#FFADC5",
                "#FFDDA6",
                "#C2E0BA",
                "#BBD1E8"
        };

        for (int i = 0; i < number; i++) {
            colors[i][1] = Color.parseColor(colorName[i]);
            colors[i][0] = Util.getInstance().getPressedColor(colors[i][1]);
        }

        return colors;
    }

    /**
     * 각 페이지로 이동
     * CarListActivity, MessageActivity, RequestActivity, SettingsActivity 이동
     */
    @Override
    public void onClick(int buttonIndex) {
        switch (buttonIndex) {
            case 0:
                startActivity(new Intent(this, CarListActivity.class));
                break;
            case 1:
                startActivity(new Intent(this, MessageActivity.class));
                break;
            case 2:
                startActivity(new Intent(this, RequestActivity.class));
                break;
            case 3:
                startActivity(new Intent(this, SettingsActivity.class));
                break;
        }
    }

    // 뒤로가기 버튼 클릭시 메뉴가 사라지는 기능
    @Override
    public void onBackPressed() {
        if (boomMenuButton.isClosed()
                && boomMenuButtonInActionBar.isClosed()
                && boomInfo.isClosed()) {
            super.onBackPressed();
        } else {
            boomMenuButton.dismiss();
            boomMenuButtonInActionBar.dismiss();
            boomInfo.dismiss();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.logoutBtn){
            Toast.makeText(this, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
            userPresenter.deleteRegistrationId(registrationId);

            autoSetting = getSharedPreferences("autoSetting", 0);
            editor = autoSetting.edit();

            editor.remove("autoSetting");
            editor.clear();
            editor.commit();

            startActivity(new Intent(this, LoginActivity.class));

        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


}
