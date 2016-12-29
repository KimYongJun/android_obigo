package com.obigo.obigoproject.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.obigo.obigoproject.R;
import com.obigo.obigoproject.presenter.BundlePresenter;
import com.obigo.obigoproject.util.AutoInstaller;
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

    public static final String APK_URL = "http://192.168.1.14/obigoProject/api/bundledown";
    private ProgressDialog mProgressDialog;

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
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        bundleVersion = packageInfo.versionName;

        //서버에서 BundleVersion 체크
        bundlePresenter.bundleVersionCheck(bundleVersion);

    }


    private void startDownload() {
        String url = "http://192.168.1.14/obigoProject/api/bundledown";
        new DownloadFileAsync(this).execute(url, "1", "1");
    }

    private void startInstaller() {
        AutoInstaller installer = AutoInstaller.getDefault(SplashActivity.this);
        installer.installFromUrl(APK_URL);
        installer.setOnStateChangedListener(new AutoInstaller.OnStateChangedListener() {
            @Override
            public void onStart() {
                mProgressDialog.show();
            }

            @Override
            public void onComplete() {
                mProgressDialog.dismiss();
            }

            @Override
            public void onNeed2OpenService() {
                Toast.makeText(SplashActivity.this, "请打开辅助功能服务", Toast.LENGTH_SHORT).show();
            }
        });

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

                startDownload();

                mProgressDialog = new ProgressDialog(SplashActivity.this);
                mProgressDialog.setMessage("Update ....");

                Toast.makeText(getBaseContext(), "업데이트 후 다시 실행해주세요", Toast.LENGTH_LONG).show();
                //   bundlePresenter.bundleUpdate();


                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {

                                startInstaller();
                            }
                        }, 2000);

            }


        }
    }
}
