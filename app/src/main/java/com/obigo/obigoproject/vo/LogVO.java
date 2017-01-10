package com.obigo.obigoproject.vo;

/**
 * Created by O BI HE ROCK on 2017-01-09
 * 김용준, 최현욱
 * 로그 정보
 */

public class LogVO {
    private String body;
    private String returned;

    public LogVO(String body, String returned) {
        this.body = body;
        this.returned = returned;
    }

    public String getBody() {
        return body;
    }

    public String getReturned() {
        return returned;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setReturned(String returned) {
        this.returned = returned;
    }

    @Override
    public String toString() {
        return "LogVO{" +
                "body='" + body + '\'' +
                ", returned='" + returned + '\'' +
                '}';
    }
}
