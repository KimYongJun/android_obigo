package com.obigo.obigoproject.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.obigo.obigoproject.R;
import com.obigo.obigoproject.presenter.BundlePresenter;
import com.obigo.obigoproject.util.ResourceVO;

import java.util.List;

/**
 * Created by O BI HE ROCK on 2016-11-28
 * 김용준, 최현욱
 * obigo logo splash
 * 1.5초 동안 메인 로딩
 */

public class SplashActivity extends Activity {
    private Handler handler;
    private BundlePresenter bundlePresenter;

    //bundleVersion 저장
    SharedPreferences autoSetting;
    SharedPreferences.Editor editor;

    //디바이스 내부 앱 bundleVersion
    private String bundleVersion;

    //BundleVersion 체크 동일 여부
    private String bundleVersionCheckFlag;

    //Bundle 업데이트 받을 파일
    private List<ResourceVO> resourceList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_splash);

        bundleVersionCheckFlag = "false";
        bundlePresenter = new BundlePresenter(this);

        bundleVersionCheck();

        // 1.5초 동안 작업
        handler = new Handler();
        handler.postDelayed(new splashhandler(), 2000);
    }

    //서버에 bundleVersionCheck 요청
    public void bundleVersionCheck() {

        autoSetting = getSharedPreferences("bundleVersion", 0);
        editor = autoSetting.edit();

        //디바이스 내에 BundleVersion 불러오기
        if (autoSetting.getString("BUNDLE_VERSION", "") == "") {
            editor.putString("BUNDLE_VERSION", "1.0");
            editor.commit();
            bundleVersion = autoSetting.getString("BUNDLE_VERSION", "");

        } else {
            bundleVersion = autoSetting.getString("BUNDLE_VERSION", "");
        }

        //서버에서 BundleVersion 체크
        bundlePresenter.bundleVersionCheck(bundleVersion);

    }

    //BundleVersion 체크 후 다르면 업데이트 시도
    public void bundleVersionCheckResult() {

        if (bundleVersionCheckFlag == "true") {
            Toast.makeText(getBaseContext(), "번들버전 같아요", Toast.LENGTH_SHORT).show();
        } else {
            //번들 버전 다르면 여기서 버전 업데이트 작업 해줘야함
            //번들 없데이트후에  editor.putString("BUNDLE_VERSION", "1.0") 이용해서 버전 갱신시켜주기
            Toast.makeText(getBaseContext(), "번들버전 달라요", Toast.LENGTH_SHORT).show();
            bundlePresenter.bundleUpdate();
        }
    }

    public void dispatchBundleVersionCheck(String bundleVersionCheckFlag) {
        this.bundleVersionCheckFlag = bundleVersionCheckFlag;
    }

    public void dispatchBundleUpdate(List<ResourceVO> resourceList) {
        this.resourceList = resourceList;
    }


    private class splashhandler implements Runnable {
        public void run() {

            bundleVersionCheckResult();

            // 로딩이 끝난후 이동할 Activity
            startActivity(new Intent(getApplication(), LoginActivity.class));
            // 로딩페이지 Activity Stack에서 제거
            SplashActivity.this.finish();
        }
    }
}
