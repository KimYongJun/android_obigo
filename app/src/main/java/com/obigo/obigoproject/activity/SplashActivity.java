package com.obigo.obigoproject.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.obigo.obigoproject.R;
import com.obigo.obigoproject.presenter.BundlePresenter;
import com.obigo.obigoproject.util.DownloadFileAsync;
import com.obigo.obigoproject.vo.ResourceVO;

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

    //디바이스 내부 앱 bundleVersion
    private String bundleVersion;
    private PackageInfo packageInfo;

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
        handler.postDelayed(new splashhandler(), 3000);
    }

    //서버에 bundleVersionCheck 요청
    public void bundleVersionCheck() {

        //어플리케이션 버전 불러오기
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(),0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        bundleVersion = packageInfo.versionName;

        //서버에서 BundleVersion 체크
        bundlePresenter.bundleVersionCheck(bundleVersion);

    }


    private void startDownload() {
        //String url = "http://cfs11.blog.daum.net/image/5/blog/2008/08/22/18/15/48ae83c8edc9d&filename=DSC04470.JPG";
        String url = "http://192.168.1.14/obigoProject/api/bundledown";
        //  String url = "http://192.168.1.14/obigoProject/api/bundledownn";
        new DownloadFileAsync(this).execute(url, "1", "1");
    }

    public void dispatchBundleVersionCheck(String bundleVersionCheckFlag) {
        this.bundleVersionCheckFlag = bundleVersionCheckFlag;
    }

    public void dispatchBundleUpdate(List<ResourceVO> resourceList) {
        this.resourceList = resourceList;
    }


    private class splashhandler implements Runnable {
        public void run() {

            if (bundleVersionCheckFlag == "true") {
                // 로딩이 끝난후 이동할 Activity
                startActivity(new Intent(getApplication(), LoginActivity.class));
                // 로딩페이지 Activity Stack에서 제거
                SplashActivity.this.finish();

            } else {
                Toast.makeText(getBaseContext(), "업데이트가 필요합니다.", Toast.LENGTH_SHORT).show();
                //   bundlePresenter.bundleUpdate();
               startDownload();

            }

        }
    }
}
