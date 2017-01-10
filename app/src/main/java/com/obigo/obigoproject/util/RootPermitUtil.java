package com.obigo.obigoproject.util;

import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * Created by O BI HE ROCK on 2016-01-03
 * 김용준, 최현욱
 * adb shell 루트 권한 부여
 */

public class RootPermitUtil {
    public static final String TAG = "Utils";

    //안드로이드 adb shell 루트 권한을 부여받고 Install 진행
    @Deprecated
    public static boolean isRooted() {
        Process process = null;
        try {
            process = Runtime.getRuntime().exec("su");
            OutputStream outputStream = process.getOutputStream();
            InputStream inputStream = process.getInputStream();
            outputStream.write("id\n".getBytes());
            outputStream.flush();
            outputStream.write("exit\n".getBytes());
            outputStream.flush();
            process.waitFor();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String s = bufferedReader.readLine();
            if (s.contains("uid=0")) return true;
        } catch (IOException e) {
            Log.e(TAG, "IOException");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (process != null)
                process.destroy();
        }
        return false;
    }

    // 파일 경로 존재 유무 확인
    public static boolean checkRooted() {
        boolean result = false;
        try {
            result = new File("/system/bin/su").exists() || new File("/system/xbin/su").exists();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
