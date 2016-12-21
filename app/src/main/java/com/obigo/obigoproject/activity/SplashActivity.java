package com.obigo.obigoproject.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.obigo.obigoproject.R;

/**
 * Created by O BI HE ROCK on 2016-11-28
 * 김용준, 최현욱
 * obigo logo splash
 * 1.5초 동안 메인 로딩
 */

public class SplashActivity extends Activity {
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_splash);

        // 1.5초 동안 작업
        handler = new Handler();
        handler.postDelayed(new splashhandler(), 1500);
    }

    private class splashhandler implements Runnable {
        public void run() {
            // 로딩이 끝난후 이동할 Activity
            startActivity(new Intent(getApplication(), LoginActivity.class));
            // 로딩페이지 Activity Stack에서 제거
            SplashActivity.this.finish();
        }
    }
}
