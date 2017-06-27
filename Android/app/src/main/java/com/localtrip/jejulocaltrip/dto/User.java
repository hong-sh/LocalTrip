package com.localtrip.jejulocaltrip.dto;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

/**
 * Created by hong on 2017. 5. 14..
 */

public class User {

    private int userIdx;
    private String userEmail;
    private String userName;
    private String userPhone;
    private int userType;
    private String userImage;
    private boolean isLogin;
    private Context context;

    public User() {

    }

    public User(Context context) {
        this.context = context;
    }

    public User(int userIdx, String userEmail, String userName, String userPhone, int userType, String userImage) {
        this.userIdx = userIdx;
        this.userEmail = userEmail;
        this.userName = userName;
        this.userPhone = userPhone;
        this.userType = userType;
        this.userImage = userImage;
    }

    public int getUserIdx() {
        return userIdx;
    }

    public void setUserIdx(int userIdx) {
        this.userIdx = userIdx;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public void saveLogin(String userEmail, String userPass) {
        SharedPreferences pref = context.getSharedPreferences("login", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("userEmail", userEmail);
        editor.putString("userPass", userPass);
        editor.commit();

    }

    public void removeLogin(){
        SharedPreferences pref = context.getSharedPreferences("login", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();

    }

    public HashMap<String, String> getPreferences(){
        SharedPreferences pref = context.getSharedPreferences("login", context.MODE_PRIVATE);

        HashMap<String, String> loginInfo = new HashMap<>();
        loginInfo.put("userEmail", pref.getString("userEmail", null));
        loginInfo.put("userPass", pref.getString("userPass", null));

        return loginInfo;
    }

}
