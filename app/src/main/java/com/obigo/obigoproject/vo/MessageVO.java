package com.obigo.obigoproject.vo;

import java.io.Serializable;

/**
 * Created by O BI HE ROCK on 2016-12-16
 * 김용준, 최현욱
 * 메시지 정보
 */

public class MessageVO implements Serializable {
    private int messageNumber;
    private String title;
    private String content;
    private String sendDate;
    private String uploadFile;
    private String modelName;
    private String location;
    private int categoryNumber;

    public MessageVO(int messageNumber, String title, String content, String sendDate, String uploadFile) {
        this.messageNumber = messageNumber;
        this.title = title;
        this.content = content;
        this.sendDate = sendDate;
        this.uploadFile = uploadFile;
    }

    public void setMessageNumber(int messageNumber) {
        this.messageNumber = messageNumber;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }

    public void setUploadFile(String uploadFile) {
        this.uploadFile = uploadFile;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setCategoryNumber(int categoryNumber) {
        this.categoryNumber = categoryNumber;
    }

    public int getMessageNumber() {
        return messageNumber;
    }

    public int getCategoryNumber() {
        return categoryNumber;
    }

    public String getLocation() {
        return location;
    }

    public String getModelName() {
        return modelName;
    }

    public String getUploadFile() {
        return uploadFile;
    }

    public String getSendDate() {
        return sendDate;
    }

    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "MessageVO{" +
                "messageNumber=" + messageNumber +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", sendDate='" + sendDate + '\'' +
                ", uploadFile='" + uploadFile + '\'' +
                ", modelName='" + modelName + '\'' +
                ", location='" + location + '\'' +
                ", categoryNumber=" + categoryNumber +
                '}';
    }
}
