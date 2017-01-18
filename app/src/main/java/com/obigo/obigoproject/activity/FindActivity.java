package com.obigo.obigoproject.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.obigo.obigoproject.R;
import com.obigo.obigoproject.presenter.UserPresenter;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by O BI HE ROCK on 2017-01-13
 * 김용준, 최현욱
 * 계정 찾기
 */

public class FindActivity extends AppCompatActivity {
    //이름
    @Bind(R.id.user_name)
    EditText userName;
    //이메일
    @Bind(R.id.user_email)
    EditText userEmail;
    //보내기 버튼
    @Bind(R.id.btn_send)
    Button btnSend;
    //에메일 전송 결과
    String findEmailResultFlag = "false";

    UserPresenter userPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find);
        ButterKnife.bind(this);

        setTitle("FIND");
        setupActionBar();

        userPresenter = new UserPresenter(this);
    }


    @OnClick(R.id.btn_send)
    public void sendEmailAddress() {
        String name = userName.getText().toString();
        String email = userEmail.getText().toString();
        //이름,이메일 조회 요청
        userPresenter.find(name, email);
        onResult();
    }

    public void onResult() {
        final ProgressDialog progressDialog = new ProgressDialog(FindActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Connect...");
        progressDialog.show();

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        if (findEmailResultFlag == "true") {
                            Toast.makeText(getBaseContext(), "Email에 ID,Password가 전송 되었습니다.", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getBaseContext(), "등록되어 있지 않는 이름 또는 Email입니다.", Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                }, 6000);
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
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);

        return super.onOptionsItemSelected(item);
    }

    public void dispatchFindEmailResult(String findEmailResultFlag) {
        this.findEmailResultFlag = findEmailResultFlag;
    }


}
