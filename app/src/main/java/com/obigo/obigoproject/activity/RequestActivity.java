package com.obigo.obigoproject.activity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.gregacucnik.EditTextView;
import com.obigo.obigoproject.R;
import com.obigo.obigoproject.presenter.UserRequestPresenter;
import com.obigo.obigoproject.util.ConstantsUtil;
import com.obigo.obigoproject.vo.UserRequestVO;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.obigo.obigoproject.R.layout.request;

/**
 * Created by O BI HE ROCK on 2016-12-06
 * 김용준, 최현욱
 * 차량 요청하는 페이지 - user_id, model_code, color, location, vin
 * API : /api/request
 * 차량 모델을 조회 - 송신 데이터 없음
 * API : /api/carmodellist
 * <p>
 * onClick method 호출 - 최현욱
 */

public class RequestActivity extends MenuActivity {

    @Bind(R.id.model_color)
    EditTextView color;
    @Bind(R.id.location)
    EditTextView location;
    @Bind(R.id.vin)
    EditTextView vin;

    @Bind(R.id.resetBtn)
    Button reset;
    @Bind(R.id.sendBtn)
    Button send;

    private UserRequestPresenter userRequestPresenter;
    private String resultFlag = "false";


    //스피너 (모델 이름 리스트 선택)
    private Spinner modelName;
    int modelHoldNumber;

    private List<String> modelNameList;
    private List<String> modelCodeList;

    SharedPreferences autoSetting;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("REQUEST");
        super.onCreate(savedInstanceState);
        setContentView(request);
        final LinearLayout llContainer = (LinearLayout) findViewById(R.id.car_request);

        llContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llContainer.requestFocus();
            }
        });

        ButterKnife.bind(this);

        modelName = (Spinner) findViewById(R.id.model_code);
        userRequestPresenter = new UserRequestPresenter(this, modelName);


        initModelCode();
    }

    //스피너 뷰 (모델이름 리스트)
    public void initModelCode() {
        userRequestPresenter.getVehicleList();

        modelName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                modelHoldNumber = modelName.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    //리셋 버튼
    @OnClick(R.id.resetBtn)
    public void resetData() {
        color.setText("");
        location.setText("");
        vin.setText("");

    }

    //UserRequest 요청 버튼
    @OnClick(R.id.sendBtn)
    public void requestUserCar() {
        //추후에 Toast 메시지 지우기 (test용)
        Toast.makeText(getApplicationContext(),
                "code : " + modelNameList.get(modelHoldNumber) + " , " + "color : " + color.getText().toString() +
                        " , " + "location : " + location.getText().toString() + " , " + "vin : " +
                        vin.getText().toString(), Toast.LENGTH_SHORT).show();

        userRequestPresenter.insertUserRequest(new UserRequestVO(ConstantsUtil.TEST_USER_ID, modelCodeList.get(modelHoldNumber), color.getText().toString(),
                location.getText().toString(), vin.getText().toString()));

        //로딩화면 보여주기
        final ProgressDialog progressDialog = new ProgressDialog(RequestActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loding...");
        progressDialog.show();

        //값을 서버에서 송수신 하는데 걸리는 시간을 지연 시키기
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        requestResult();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }

    public void requestResult() {

        if (resultFlag == "false") {
            resultFlag = "Try Again";
        } else {
            resultFlag = "Success Request";
        }
        Toast.makeText(getApplicationContext(), resultFlag, Toast.LENGTH_SHORT).show();
    }

    public void dispatchVehicleInfo(List<String> modelNameList, List<String> modelCodeList) {
        this.modelNameList = modelNameList;
        this.modelCodeList = modelCodeList;
    }

    public void dispatchRequestInfo(String resultFlag) {
        this.resultFlag = resultFlag;
    }


}
