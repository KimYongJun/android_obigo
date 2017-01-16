package com.obigo.obigoproject.util;

import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.widget.Toast;

import com.obigo.obigoproject.activity.SplashActivity;
import com.obigo.obigoproject.presenter.ExceptionPresenter;
import com.obigo.obigoproject.vo.LogVO;

import java.io.PrintWriter;
import java.io.StringWriter;

import static com.obigo.obigoproject.util.ConstantsUtil.USER_ID;

/**
 * Created by O BI HE ROCK on 2017-01-11
 * 김용준, 최현욱
 * 예상하지 못한 예외 처리
 */

public class UncaughtExceptionHandlerUtil implements Thread.UncaughtExceptionHandler {

    private Context context;
    private Thread.UncaughtExceptionHandler defaultExHandler;

    // Retrofit 에러 보내기
    private ExceptionPresenter exceptionPresenter;

    public UncaughtExceptionHandlerUtil(Context context) {
        this.context = context;
        defaultExHandler = Thread.currentThread().getUncaughtExceptionHandler();

    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {

        StringWriter exception = new StringWriter();
        throwable.printStackTrace(new PrintWriter(exception));

        //예외 내용 서버에 전송
        exceptionPresenter = new ExceptionPresenter(this);
        LogVO logVO = new LogVO(USER_ID, "UncaughtException : " + exception.toString());
        exceptionPresenter.errorUserVehicle("uncaughtexception", logVO);

        new Thread() {
            @Override
            public void run() {
                // UI쓰레드에서 토스트 띄우기
                Looper.prepare();
                Toast.makeText(context, "예상치 않은 오류가 발생하였습니다." + "\r\n" +
                        "다시 시작해주세요", Toast.LENGTH_SHORT).show();

                Looper.loop();
            }
        }.start();


        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.out.println("error :" + e.toString());
        }

        //다시 SplashActivity 실행
        Intent intent = new Intent(context, SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);


        //현재 프로세스를 죽인다
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(10);


        // 기본 값인 OS를 호출하여 OS포스가 평상시처럼 앱을 닫도록 합니다.
        defaultExHandler.uncaughtException(thread, throwable);

    }


}
