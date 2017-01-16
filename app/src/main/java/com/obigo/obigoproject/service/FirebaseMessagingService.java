package com.obigo.obigoproject.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.RemoteMessage;
import com.obigo.obigoproject.R;
import com.obigo.obigoproject.activity.MessageActivity;
import com.obigo.obigoproject.activity.SplashActivity;
import com.obigo.obigoproject.util.ConstantsUtil;
import com.obigo.obigoproject.util.WakeUpScreenUtil;

import java.net.URL;

import static com.obigo.obigoproject.util.ConstantsUtil.AUTO_USER_ID;
import static com.obigo.obigoproject.util.ConstantsUtil.USER_ID;

/**
 * Created by O BI HE ROCK on 2016-12-14
 * 김용준, 최현욱
 * FirebaseMessagingService 서버에서 발송한 메시지를 받음
 * 전달받은 메시지를 클릭하면 MessageActivity로 이동
 */

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    private static final String TAG = "FirebaseMsgService";
    SharedPreferences autoSetting;

    // 서버에서 Push Message 전달시 받는 작업
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        String title = remoteMessage.getData().get("title");
        String content = remoteMessage.getData().get("content");
        String upload = remoteMessage.getData().get("upload");

        sendNotification(title, content, upload);


    }

    // 메시지를 받음
    private void sendNotification(String title, String content, String upload) {
        // 화면이 켜지는 작업 - 10초 유지
        WakeUpScreenUtil.acquire(getApplicationContext(), 10000);

        // 메시지를 클릭하면 메시지 리스트로 이동
        Intent intent;
        autoSetting = getSharedPreferences("autoSetting", 0);

        //Static한 USER_ID가 남아있는경우
        if (USER_ID != null) {
            intent = new Intent(this, MessageActivity.class);
        }
        //정상종료나, 프로세스 모두종료하기 한 경우
        else {
            //자동로그인일 경우
            if (autoSetting.getString("ID", "") != "") {
                AUTO_USER_ID = autoSetting.getString("ID", "");
                USER_ID = AUTO_USER_ID;
                intent = new Intent(this, MessageActivity.class);
            }
            //자동로그인 아닌경우
            else {
                intent = new Intent(this, SplashActivity.class);
            }

        }


        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Bitmap bigPicture = null;
        try {
            URL url = new URL(ConstantsUtil.SERVER_API_URL + ConstantsUtil.SERVER_MESSAGE_IMAGE_URL + upload);
            bigPicture = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }



        Uri soundUri =  Uri.parse(ConstantsUtil.ringtone);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(content)
                .setAutoCancel(true)
                .setSound(soundUri)
                .setContentIntent(pendingIntent);

        if (upload != null) {
            notificationBuilder.setStyle(new NotificationCompat.BigPictureStyle()
                    .bigPicture(bigPicture));
        }

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // ID of notification
        notificationManager.notify(0, notificationBuilder.build());
    }


}