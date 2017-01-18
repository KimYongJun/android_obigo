package com.obigo.obigoproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.obigo.obigoproject.activity.SettingsActivity;
import com.obigo.obigoproject.presenter.UserPresenter;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.obigo.obigoproject.util.ConstantsUtil.USER_ID;
import static com.obigo.obigoproject.util.ConstantsUtil.USER_PASSWORD;

/**
 * Created by O BI HE ROCK on 2017-01-18
 * 김용준, 최현욱
 * 비밀번호 변경
 */

public class PasswordModifyActivity extends AppCompatActivity {
    //현재 비밀번호
    @Bind(R.id.user_modify_password)
    EditText modifyPassword;
    //변경할 비밀번호
    @Bind(R.id.user_modify_newpassword)
    EditText modifyNewPassword;
    //보내기 버튼
    @Bind(R.id.btn_ok)
    Button btnOK;
    //비밀번호 변경 결과
    String passwordModifyResultFlag;

    UserPresenter userPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preference_passwordmodify);
        ButterKnife.bind(this);

        setTitle("PASSWORD MODIFY");
        setupActionBar();

        userPresenter = new UserPresenter(this);
    }

    @OnClick(R.id.btn_ok)
    public void modifyPassword(){

        String passowrd = modifyPassword.getText().toString();
        String newPassword= modifyNewPassword.getText().toString();
        String pattern = "^.*(?=.{6,20})(?=.*[0-9])(?=.*[a-zA-Z]).*$";

        if (passowrd != USER_PASSWORD){
            Toast.makeText(getBaseContext(),"현재 비밀번호와 일치하지 않습니다.", Toast.LENGTH_LONG).show();
        }else if(newPassword.matches(pattern) == false){
            Toast.makeText(getBaseContext(),"비밀번호는 영문,숫자를 혼합하여"+"\r\n"
                    +"6~20자 이내이어야 합니다.", Toast.LENGTH_LONG).show();
        }else{
            //비밀번호 변경 요청
            userPresenter.passwordModify(USER_ID,passowrd,newPassword);
            onResult();
        }


    }

    public void onResult(){
        final ProgressDialog progressDialog = new ProgressDialog(PasswordModifyActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Connect...");
        progressDialog.show();

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        if (passwordModifyResultFlag == "true") {
                            Toast.makeText(getBaseContext(), "비밀번호가 변경되었습니다", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getBaseContext(), "변경 실패, 다시 시도해주세요.", Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                }, 3000);
    }




    // Page에 Bar등록
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
        Intent intent = new Intent(this, SettingsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);

        return super.onOptionsItemSelected(item);
    }

    public void dispatchPassword(String passwordModifyResultFlag){
        this.passwordModifyResultFlag =passwordModifyResultFlag;
    }
}
