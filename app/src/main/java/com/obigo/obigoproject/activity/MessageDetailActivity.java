package com.obigo.obigoproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.obigo.obigoproject.R;
import com.obigo.obigoproject.util.ConstantsUtil;
import com.obigo.obigoproject.vo.MessageVO;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by O BI HE ROCK on 2016-12-13.
 * 김용준, 최현욱
 * 메시지 세부사항 페이지 - 특정 메시지 정보
 * MessageActivity에서 Intent로 데이터를 보내줌
 */

public class MessageDetailActivity extends AppCompatActivity {
    // 메시지 제목
    @Bind(R.id.message_detail_title)
    TextView _message_detail_title;
    // 메시지 내용
    @Bind(R.id.message_detail_content)
    TextView _message_detail_content;
    // 메시지 날짜
    @Bind(R.id.message_detail_date)
    TextView _message_detail_date;
    // 메시지 이미지
    @Bind(R.id.message_detail_image)
    ImageView _message_detail_image;

    // Intent로 메시지 정보를 전달받음
    private MessageVO messageVO;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_detail);
        ButterKnife.bind(this);

        // 메시지 정보를 전달받음
        Intent intent = getIntent();
        messageVO = (MessageVO) intent.getSerializableExtra("MessageDetailInfo");

        // 메시지 정보 Setting
        initVariable(messageVO);
    }


    // 전달받은 메시지 정보를 입력
    private void initVariable(MessageVO messageVO) {
        _message_detail_title.setText(messageVO.getTitle());
        _message_detail_content.setText(messageVO.getContent());
        _message_detail_date.setText(messageVO.getSendDate());
        Glide.with(this).load(ConstantsUtil.SERVER_API_URL_REAL + ConstantsUtil.SERVER_MESSAGE_IMAGE_URL +
                messageVO.getUploadFile()).into(_message_detail_image);
    }

}