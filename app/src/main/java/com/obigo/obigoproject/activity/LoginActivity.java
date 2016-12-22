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
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.obigo.obigoproject.R;
import com.obigo.obigoproject.presenter.UserPresenter;
import com.obigo.obigoproject.vo.RegistrationIdVO;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.obigo.obigoproject.util.ConstantsUtil.USER_ID;

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
    private String resultFlag;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ButterKnife.bind(this);


        registrationIdResult = false;
        resultFlag = "false";

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

            final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                    R.style.AppTheme_Dark_Dialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Logging in to your account...");
            progressDialog.show();

            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {

                            onLoginSuccess();

                            progressDialog.dismiss();
                        }
                    }, 2000);

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
  /*    if (!validate()) {
          onLoginFailed();
          return;
      }*/

        loginButton.setEnabled(false);

        //userId, userPassword 서버에 조회 요청
        String userId = idText.getText().toString();
        String userPassword = passwordText.getText().toString();
        userPresenter.login(userId, userPassword);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Logging in to your account...");
        progressDialog.show();


        /**
         * 로그인 인증을 위한 로직, Server로 데이터를 보냄
         * 여기서 Bundle check, update / registration_id를 등록함
         */
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {

                        if (resultFlag == "true") {
                            onLoginSuccess();
                        } else {
                            onLoginFailed();
                        }


                        progressDialog.dismiss();
                    }
                }, 2000);
    }

    // 뒤로가기
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    // 로그인 성공
    public void onLoginSuccess() {

        //로그인 성공시 ID EditText에 입력한 값을 Static하게 쓰기
        USER_ID = idText.getText().toString();

        // 사용자 registrationId 등록
        String registrationId = FirebaseInstanceId.getInstance().getToken();
        userPresenter.insertRegistrationId(new RegistrationIdVO(USER_ID, registrationId));

        loginButton.setEnabled(true);
        finish();//뒤로가기 했을때 로그인 페이지는 보여주지않음


        Intent intent = new Intent(this, CarListActivity.class);
        startActivity(intent);
    }

    // 로그인 실패
    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        loginButton.setEnabled(true);
    }

    // 로그인 정규식 체크
    public boolean validate() {
        boolean valid = true;

        String id = idText.getText().toString();
        String password = passwordText.getText().toString();

        if (id.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(id).matches()) {
            idText.setError("enter a valid id address");
            valid = false;
        } else {
            idText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            passwordText.setError(null);
        }

        return valid;
    }

    public void dispatchLoginResult(String resultFlag) {
        this.resultFlag = resultFlag;
    }


}
