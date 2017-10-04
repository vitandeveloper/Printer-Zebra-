package com.example.marlon.app_dhl.retrofit.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Marlon on 25/7/2017.
 */

public class Usuario {

    @SerializedName("UserName")
    private String userName;
    @SerializedName("Password")
    private String password;
    @SerializedName("DevieLatLong")
    private String devieLatLong;

    public Usuario() {
    }

    public Usuario(String userName, String password, String devieLatLong) {
        super();
        this.userName = userName;
        this.password = password;
        this.devieLatLong = devieLatLong;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDevieLatLong() {
        return devieLatLong;
    }

    public void setDevieLatLong(String devieLatLong) {
        this.devieLatLong = devieLatLong;
    }
}
