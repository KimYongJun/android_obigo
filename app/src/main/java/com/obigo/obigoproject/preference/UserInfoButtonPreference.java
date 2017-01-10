package com.obigo.obigoproject.preference;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.obigo.obigoproject.R;
import com.obigo.obigoproject.presenter.UserPresenter;
import com.obigo.obigoproject.util.MaterialDialog;
import com.obigo.obigoproject.vo.UserVO;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.obigo.obigoproject.util.ConstantsUtil.USER_ID;


/**
 * Created by O BI HE ROCK on 2016-12-06
 * 김용준, 최현욱
 * UserInfo Button in Setting page
 *
 * onCreateView()는 Fragment에 실제 사용할 뷰를 만드는 작업을 하는 메소드이다.
 * LayoutInflater를 인자로 받아서 layout으로 설정한 XML을 연결하거나
 * bundle에 의한 작업을 하는 메소드이다.
 */

public class UserInfoButtonPreference extends android.preference.Preference {
    @Bind(R.id.userInfoBtn)
    Button mButtonUserInfo;
    private Context mContext;
    UserVO userVO;
    UserPresenter userPresenter;

    public UserInfoButtonPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
    }

    public UserInfoButtonPreference(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UserInfoButtonPreference(Context context) {
        this(context, null);
    }

    //뷰(버튼) 생성
    @Override
    protected View onCreateView(android.view.ViewGroup parent) {
        final LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = layoutInflater.inflate(R.layout.preference_button_userinfo, parent, false);
        ButterKnife.bind(this, layout);

        userPresenter = new UserPresenter(this, USER_ID);
        userPresenter.getUser();

        return layout;
    }

    // 사용자 정보 조회 클릭
    @OnClick(R.id.userInfoBtn)
    public void showUserInfo() {

        // 다이얼로그에 유저정보 띄우기
        final MaterialDialog materialDialog = new MaterialDialog(this.mContext);
        materialDialog.setMessage(
                        "\r\n" + "\r\n" +
                        "User Name     :    " + userVO.getName() +
                        "\r\n" + "\r\n" + "\r\n" +
                        "Email            :    " + userVO.geteMail() +
                        "\r\n" + "\r\n" + "\r\n" +
                        "Phone            :    " + userVO.getPhone() +
                        "\r\n" + "\r\n"

        )
                .setPositiveButton(android.R.string.yes,
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                materialDialog.dismiss();
                            }
                        });
        materialDialog.show();
    }

    public void dispatchUserInfo(UserVO userVO) {
        this.userVO = userVO;
    }
}
