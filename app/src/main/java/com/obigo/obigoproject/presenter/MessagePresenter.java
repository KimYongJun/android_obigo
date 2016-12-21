package com.obigo.obigoproject.presenter;

import android.util.Log;
import android.widget.ListView;

import com.obigo.obigoproject.R;
import com.obigo.obigoproject.activity.MessageActivity;
import com.obigo.obigoproject.adapter.MessageListAdapter;
import com.obigo.obigoproject.service.MessageService;
import com.obigo.obigoproject.service.ServiceManager;
import com.obigo.obigoproject.vo.MessageVO;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by O BI HE ROCK on 2016-12-14
 * 김용준, 최현욱
 * 모든 메시지 정보를 받음
 */

public class MessagePresenter {
    private ListView listView;
    private MessageListAdapter messageListAdapter;
    // 메시지 리스트 요청
    private MessageService messageService;
    private MessageActivity messageActivity;
    // 메시지 정보
    private List<MessageVO> messageList;
    // 사용자 정보
    private String userId;

    public MessagePresenter(MessageActivity messageActivity, String userId, ListView listView) {
        this.listView = listView;
        this.messageActivity = messageActivity;
        this.messageService = ServiceManager.getInstance().getMessageService();
        this.userId = userId;
    }

    // 사용자 메시지 리스트 요청
    public void getMessageList() {
        Log.i("userId  : ", userId);
        Call<List<MessageVO>> call = messageService.getMessageList(userId);
        call.enqueue(new Callback<List<MessageVO>>() {
            @Override
            public void onResponse(Call<List<MessageVO>> call, Response<List<MessageVO>> response) {
                if (response.isSuccessful()) {
                    // 메시지 정보를 전달받음
                    messageList = response.body();
                    Log.i("user : ", MessagePresenter.this.messageList.toString());
                    messageListAdapter = new MessageListAdapter(messageActivity,
                            R.layout.message_item, messageList);
                    messageActivity.dispatchMessageInfo(messageList);
                    listView.setAdapter(messageListAdapter);
                } else {
                    // 에러 정보 출력
                    Log.i("error : ", response.errorBody().toString());
                }

            }

            // 서버와 접속 실패
            @Override
            public void onFailure(Call<List<MessageVO>> call, Throwable t) {
                Log.i("에러 : ", t.getMessage());
            }
        });
    }
}
