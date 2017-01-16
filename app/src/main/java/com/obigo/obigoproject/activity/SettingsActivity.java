package com.obigo.obigoproject.activity;


import android.content.Context;
import android.content.res.Configuration;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.MenuItem;

import com.obigo.obigoproject.R;
import com.obigo.obigoproject.preference.AppCompatPreferenceActivity;
import com.obigo.obigoproject.util.ConstantsUtil;

/**
 * Created by O BI HE ROCK on 2016-12-06
 * 김용준, 최현욱
 * Bundle 정보, push message 정보, auto_login, logout, user_info
 * logout 요청 - user_id (registration_id를 삭제)
 * API : /api/logout
 */

public class SettingsActivity extends AppCompatPreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("SETTINGS");
        setupActionBar();

        // Settings page location
        addPreferencesFromResource(R.xml.settings);
        setOnPreferenceChange(findPreference("notifications_new_message_ringtone"));

    }

    private void setOnPreferenceChange(Preference mPreference) {
        mPreference.setOnPreferenceChangeListener(onPreferenceChangeListener);

        onPreferenceChangeListener.onPreferenceChange(
                mPreference,
                PreferenceManager.getDefaultSharedPreferences(
                        mPreference.getContext()).getString(
                        mPreference.getKey(), ""));
    }

    private Preference.OnPreferenceChangeListener onPreferenceChangeListener = new Preference.OnPreferenceChangeListener() {
        /**
         * RingtonePreference의 경우 stringValue가
         * content://media/internal/audio/media의 형식이기 때문에
         * RingtoneManager을 사용하여 Summary를 적용한다
         *
         * 무음일경우 ""이다
         */
        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            String stringValue = newValue.toString();

            ConstantsUtil.ringtone = stringValue;

            if (TextUtils.isEmpty(stringValue)) {
                preference.setSummary("무음으로 설정됨");
            } else {
                Ringtone ringtone = RingtoneManager.getRingtone(
                        preference.getContext(), Uri.parse(stringValue));

                if (ringtone == null) {
                    preference.setSummary(null);

                } else {
                    String name = ringtone
                            .getTitle(preference.getContext());
                    preference.setSummary(name);
                }
            }


            return true;
        }

    };

    private static boolean isXLargeTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_XLARGE;
    }

    //Settings Page에 Bar등록
    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    // 뒤로가기 버튼 눌렀을때 이전 페이지로 돌아가는데 필요한 메서드
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onIsMultiPane() {
        return isXLargeTablet(this);
    }

}
