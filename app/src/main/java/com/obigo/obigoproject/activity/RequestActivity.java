package com.obigo.obigoproject.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.obigo.obigoproject.R;
import com.obigo.obigoproject.presenter.UserRequestPresenter;
import com.obigo.obigoproject.vo.UserRequestVO;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.obigo.obigoproject.R.layout.request;
import static com.obigo.obigoproject.util.ConstantsUtil.USER_ID;


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
    //모델 명,색상,지역명,VIN
    @Bind(R.id.model_name)
    Spinner modelName;
    @Bind(R.id.model_color)
    Spinner modelColor;
    @Bind(R.id.location)
    Spinner location;
    @Bind(R.id.vin)
    EditText vin;
    //reset버튼, Send버튼
    @Bind(R.id.resetBtn)
    Button reset;
    @Bind(R.id.sendBtn)
    Button send;

    private UserRequestPresenter userRequestPresenter;
    private String resultFlag = "false";

    //모델 이름 리스트 번호
    int modelHoldNumber;


    private List<String> modelNameList;
    private List<String> modelCodeList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("REQUEST");
        super.onCreate(savedInstanceState);
        setContentView(request);

        ButterKnife.bind(this);

        userRequestPresenter = new UserRequestPresenter(this, modelName);
        initModelCode();
    }

    // Spiner view (모델 이름 리스트)
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

    // Reset 버튼
    @OnClick(R.id.resetBtn)
    public void resetData() {
        modelName.setSelection(0);
        modelColor.setSelection(0);
        location.setSelection(0);
        vin.setText("");
    }

    //UserRequest 요청 버튼
    @OnClick(R.id.sendBtn)
    public void requestUserCar() {
        if ((modelColor.getSelectedItem().toString().equals("Select")) || (location.getSelectedItem().toString().equals("Select"))) {
            Toast.makeText(getApplicationContext(),"정확히 입력해 주세요",Toast.LENGTH_SHORT).show();
        } if (vin.length() != 10) {
            Toast.makeText(getApplicationContext(), "VIN 번호는 10자리를 입력해 주세요", Toast.LENGTH_SHORT).show();
        } else {
            userRequestPresenter.insertUserRequest(new UserRequestVO(USER_ID, modelCodeList.get(modelHoldNumber), modelColor.getSelectedItem().toString(),
                    location.getSelectedItem().toString(), vin.getText().toString()));

            // 로딩화면 보여주기
            final ProgressDialog progressDialog = new ProgressDialog(RequestActivity.this,
                    R.style.AppTheme_Dark_Dialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Loding...");
            progressDialog.show();

            // 값을 서버에서 송수신 하는데 걸리는 시간을 지연 시키기
            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            requestResult();
                            progressDialog.dismiss();
                        }
                    }, 3000);
        }
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
