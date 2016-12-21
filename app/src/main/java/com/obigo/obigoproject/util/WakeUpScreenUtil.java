package com.obigo.obigoproject.util;

import android.content.Context;
import android.os.PowerManager;

/**
 * 스크린을 ON한다. 젤리빈 4.2부터는 getWindows() 권장
 * Created by O BI HE ROCK on 2016-12-20
 * 김용준, 최현욱
 */
public class WakeUpScreenUtil {
    private static PowerManager.WakeLock wakeLock;

    // timeout을 설정하면, 자동으로 릴리즈됨
    public static void acquire(Context context, long timeout) {

        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        wakeLock = pm.newWakeLock(
                PowerManager.ACQUIRE_CAUSES_WAKEUP |
                        PowerManager.FULL_WAKE_LOCK |
                        PowerManager.ON_AFTER_RELEASE
                , context.getClass().getName());

        if (timeout > 0)
            wakeLock.acquire(timeout);
        else
            wakeLock.acquire();

    }

    // 이 메소드를 사용하면 반드시 release를 해야함, 현재는 사용하지 않음
    public static void acquire(Context context) {
        acquire(context, 0);
    }

    // 현재는 사용하지 않음
    public static void release() {
        if (wakeLock.isHeld())
            wakeLock.release();
    }
}
