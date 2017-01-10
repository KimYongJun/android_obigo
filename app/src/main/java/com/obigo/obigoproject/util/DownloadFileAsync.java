package com.obigo.obigoproject.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by O BI HE ROCK on 2016-01-03
 * 김용준, 최현욱
 * 서버에서 APK 다운로드 해서 Storage에 저장
 */

public class DownloadFileAsync extends AsyncTask<String, String, String> {
    private ProgressDialog mDlg;
    private Context mContext;

    public DownloadFileAsync(Context context) {
        mContext = context;
    }

    @Override
    protected void onPreExecute() {
        mDlg = new ProgressDialog(mContext);
        mDlg.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mDlg.setMessage("Start");
        mDlg.show();

        super.onPreExecute();
    }

    //APK 다운로드는 Background로 다운로드 진행
    @Override
    protected String doInBackground(String... params) {
        int count = 0;

        try {
            Thread.sleep(100);
            // APK File 경로 = params[0].toString()
            URL url = new URL(params[0].toString());
            URLConnection conexion = url.openConnection();
            conexion.connect();

            int lenghtOfFile = conexion.getContentLength();
            Log.d("ANDRO_ASYNC", "Lenght of file: " + lenghtOfFile);

            //디바이스 스토리지에 저장
            InputStream input = new BufferedInputStream(url.openStream());
            OutputStream output = new FileOutputStream("/storage/emulated/0/update.apk");

            byte data[] = new byte[1024];

            long total = 0;

            while ((count = input.read(data)) != -1) {
                total += count;
                publishProgress("" + (int) ((total * 100) / lenghtOfFile));
                output.write(data, 0, count);
            }

            output.flush();
            output.close();
            input.close();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 수행이 끝나고 리턴하는 값은 다음에 수행될 onProgressUpdate 의 파라미터가 된다
        return null;
    }

    // APK 다운이 되는 동안 Update Progress 처리
    @Override
    protected void onProgressUpdate(String... progress) {
        if (progress[0].equals("progress")) {
            mDlg.setProgress(Integer.parseInt(progress[1]));
            mDlg.setMessage(progress[2]);
        } else if (progress[0].equals("max")) {
            mDlg.setMax(Integer.parseInt(progress[1]));
        }
    }

    // 예외 처리
    @SuppressWarnings("deprecation")
    @Override
    protected void onPostExecute(String unused) {
        mDlg.dismiss();
    }
}
