package com.obigo.obigoproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.obigo.obigoproject.R;
import com.obigo.obigoproject.presenter.MessagePresenter;
import com.obigo.obigoproject.vo.MessageVO;

import java.util.List;

import static com.obigo.obigoproject.util.ConstantsUtil.USER_ID;

/**
 * Created by O BI HE ROCK on 2016-12-01
 * 김용준, 최현욱
 * 메시지 리스트를 보여주는 페이지 - user_id, page_index
 * scroll, add_button 구현 예정
 */

public class MessageActivity extends MenuActivity implements OnItemClickListener {
    // 메지시 리스트 뷰
    ListView listView;
    // 메시지 요청
    private MessagePresenter messagePresenter;
    // 메시지 리스트
    private List<MessageVO> messageList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setTitle("MESSAGE LIST");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_list);

        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(this);

        messagePresenter = new MessagePresenter(this, USER_ID, listView);
        messagePresenter.getMessageList();
    }


    public void dispatchMessageInfo(List<MessageVO> messageList) {
        this.messageList = messageList;
    }

    // 특정 메시지 클릭
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // 특정 메시지를 꺼내옴
        MessageVO message = messageList.get(position);

        // 세부사항 데이터 전달
        Intent intent = new Intent(MessageActivity.this, MessageDetailActivity.class);

        // 메시지 객체 전달
        intent.putExtra("MessageDetailInfo", message);
        startActivity(intent);
    }
}
