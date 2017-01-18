package com.obigo.obigoproject.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.obigo.obigoproject.R;
import com.obigo.obigoproject.presenter.UserPresenter;
import com.obigo.obigoproject.vo.RegistrationIdVO;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

import static com.obigo.obigoproject.util.ConstantsUtil.AUTO_USER_ID;
import static com.obigo.obigoproject.util.ConstantsUtil.USER_ID;
import static com.obigo.obigoproject.util.ConstantsUtil.USER_PASSWORD;

/**
 * Created by O BI HE ROCK on 2016-11-28
 * 김용준, 최현욱
 * login_page
 * login 작업 수행 - registration_id, user_id , password를 보냄
 * API : /api/login
 * Bundle check 작업 수행 - android_bundle_version
 * API : /api/bundlecheck
 * Bundle update 작업 수행 - 송신 데이터 없음
 * API : /api/bundleupdate
 */

public class LoginActivity extends AppCompatActivity {
    // 아이디
    @Bind(R.id.user_id)
    EditText idText;
    // 비밀번호
    @Bind(R.id.user_password)
    EditText passwordText;
    // 로그인 버튼
    @Bind(R.id.btn_login)
    Button loginButton;
    // 아이디,페스워드 찾기
    @Bind(R.id.find_text)
    TextView findText;
    //체크박스
    @Bind(R.id.auto_login_check)
    CheckBox _auto_login_check;
    //id,password 저장
    SharedPreferences autoSetting;
    SharedPreferences.Editor editor;
    // 유저 요청
    private UserPresenter userPresenter;
    // registrationId 등록 결과
    private boolean registrationIdResult;
    //로그인 성공 실패 결과
    private String loginResultFlag;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ButterKnife.bind(this);

        registrationIdResult = false;
        loginResultFlag = "false";
        userPresenter = new UserPresenter(this);

        autoLoginSettings();
    }


    //자동 로그인(id,password 저장 )
    public void autoLoginSettings() {
        autoSetting = getSharedPreferences("autoSetting", 0);
        editor = autoSetting.edit();

        if (autoSetting.getBoolean("Auto_Login_enabled", false)) {
            idText.setText(autoSetting.getString("ID", ""));
            passwordText.setText(autoSetting.getString("PW", ""));
            _auto_login_check.setChecked(true);

            //userId, userPassword 서버에 조회 요청
            String userId = idText.getText().toString();
            String userPassword = passwordText.getText().toString();
            userPresenter.login(userId, userPassword);

            onResult();
        }
        _auto_login_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    //Check 되면 ID,PW Shared Preference에 저장
                    String ID = idText.getText().toString();
                    String PW = passwordText.getText().toString();

                    editor.putString("ID", ID);
                    editor.putString("PW", PW);
                    editor.putBoolean("Auto_Login_enabled", true);
                    editor.commit();

                    autoSetting = getSharedPreferences("autoSetting", 0);
                    String test = autoSetting.getString("ID", "");
                    System.out.println("onCheckedChanged 자동 : " + test);
                } else {
                    editor.clear();
                    editor.commit();
                }
            }
        });
    }

    // 로그인 버튼 클릭
    @OnClick(R.id.btn_login)
    public void login() {
        // 로그인 정규식 체크
        if (!validate()) {
            return;
        }

        loginButton.setEnabled(false);

        //userId, userPassword 서버에 조회 요청
        String userId = idText.getText().toString();
        String userPassword = passwordText.getText().toString();
        userPresenter.login(userId, userPassword);

        onResult();
    }

    //ID,PASSWORD 찾기 클릭
    @OnTouch(R.id.find_text)
    public boolean find() {
        Intent intent = new Intent(this, FindActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);

        return true;
    }


    //서버에 ID,PASSWORD 조회후 onLoginSuccess or onLoginFailed를 호출
    public void onResult() {
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Logging in to your account...");
        progressDialog.show();


        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        if (loginResultFlag == "true") {
                            onLoginSuccess();
                        } else {
                            onLoginFailed();
                        }
                        progressDialog.dismiss();
                    }
                }, 2000);
    }


    // 로그인 성공
    public void onLoginSuccess() {
        //로그인 성공시 ID EditText에 입력한 값을 Static하게 쓰기
        USER_ID = idText.getText().toString();
        USER_PASSWORD = passwordText.getText().toString();
        autoSetting = getSharedPreferences("autoSetting", 0);

        //자동 로그인일 경우 ID Static하게 쓰기
        if (autoSetting.getString("ID", "") != "") {
            AUTO_USER_ID = autoSetting.getString("ID", "");
        }
        String registrationId = FirebaseInstanceId.getInstance().getToken();
        userPresenter.insertRegistrationId(new RegistrationIdVO(USER_ID, registrationId));

        loginButton.setEnabled(true);
        finish();//뒤로가기 했을때 로그인 페이지는 보여주지않음

        Intent intent = new Intent(this, CarListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    // 로그인 실패
    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login Failed", Toast.LENGTH_LONG).show();
        loginButton.setEnabled(true);
    }


    // 로그인 정규식 체크
    public boolean validate() {
        String id = idText.getText().toString();
        String password = passwordText.getText().toString();

        if (id.isEmpty()) {
            idText.setError("Enter a vaild ID");
            Toast.makeText(getBaseContext(), "Enter a vaild ID", Toast.LENGTH_LONG).show();
            return false;
        }

        if (password.isEmpty()) {
            passwordText.setError("Enter a vaild Password");
            Toast.makeText(getBaseContext(), "Enter a vaild Password", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    // 로그인 성공 유무
    public void dispatchLoginResult(String loginResultFlag) {
        this.loginResultFlag = loginResultFlag;
    }

}
