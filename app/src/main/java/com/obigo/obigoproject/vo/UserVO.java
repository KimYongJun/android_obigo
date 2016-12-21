package com.obigo.obigoproject.vo;

/**
 * Created by O BI HE ROCK on 2016-12-19
 * 김용준, 최현욱
 * 회원 정보
 */

public class UserVO {
    private String userId;
    private String password;
    private String name;
    private String eMail;
    private String phone;
    private String roleName;
    private String date;

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String geteMail() {
        return eMail;
    }

    public String getRoleName() {
        return roleName;
    }

    public String getPhone() {
        return phone;
    }

    public String getDate() {
        return date;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "UserVO{" +
                "userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", eMail='" + eMail + '\'' +
                ", phone='" + phone + '\'' +
                ", roleName='" + roleName + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
