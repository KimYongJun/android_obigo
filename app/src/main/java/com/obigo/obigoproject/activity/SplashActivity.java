package com.obigo.obigoproject.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.obigo.obigoproject.R;
import com.obigo.obigoproject.presenter.BundlePresenter;
import com.obigo.obigoproject.util.AutoInstaller;
import com.obigo.obigoproject.util.DownloadFileAsync;

import static android.widget.Toast.makeText;
import static com.obigo.obigoproject.util.ConstantsUtil.APK_URL;
import static com.obigo.obigoproject.util.ConstantsUtil.bundleVersion;

/**
 * Created by O BI HE ROCK on 2016-11-28
 * 김용준, 최현욱
 * obigo logo splash
 * 3초 동안 메인 로딩
 */

public class SplashActivity extends Activity {
    private Handler handler;
    // Bundle 서버
    private BundlePresenter bundlePresenter;
    //Bundle Version Check 여부
    private String bundleVersionCheckFlag;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_splash);

        bundleVersionCheckFlag = "nonConnect";
        // 서버와 접속
        bundlePresenter = new BundlePresenter(this);

        // bundleVersionCheck 메소드 호출
        bundleVersionCheck();

        // 3초 동안 작업
        handler = new Handler();
        handler.postDelayed(new splashhandler(), 3000);
    }

    //서버에 bundleVersionCheck 요청
    public void bundleVersionCheck() {
        //서버에서 BundleVersion 체크
        bundlePresenter.bundleVersionCheck(bundleVersion);
    }

    // APK파일 서버에서 다운로드
    private void startDownload() {
        new DownloadFileAsync(this).execute(APK_URL, "1", "1");
    }

    // APK파일 설치
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
                makeText(SplashActivity.this, "접근성 수정", Toast.LENGTH_SHORT).show();
            }
        });

    }

    // Bundle Version Flag 데이터 Setting
    public void dispatchBundleVersionCheck(String bundleVersionCheckFlag) {
        this.bundleVersionCheckFlag = bundleVersionCheckFlag;
    }

    private class splashhandler implements Runnable {
        public void run() {
            //서버와 접속이 되지 않았을때
            if (bundleVersionCheckFlag == "nonConnect") {
                finish();
                Toast.makeText(getBaseContext(), "서버와 연결이 되지 않습니다. "
                        + "\r\n" + "다시 실행해 주세요", Toast.LENGTH_LONG).show();

            }//서버와 BundleVersion이 동일할때
            else if (bundleVersionCheckFlag == "true") {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);

                SplashActivity.this.finish();
            } //서버와 BundleVersion이 다를때
            else {

                // 서버에서 다운로드 메소드 호출
                startDownload();

                mProgressDialog = new ProgressDialog(SplashActivity.this);
                mProgressDialog.setMessage("Update ....");

                Toast.makeText(getBaseContext(), "업데이트 후 다시 실행해주세요.", Toast.LENGTH_LONG).show();

                // APK 다운로드 완료 후 startInstaller실행
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
