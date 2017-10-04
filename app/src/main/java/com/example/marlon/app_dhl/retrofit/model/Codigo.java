package com.example.marlon.app_dhl.retrofit.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Marlon on 26/7/2017.
 */

public class Codigo {

    @SerializedName("Session_Token")
    private String sessionToken;
    @SerializedName("Awb_No")
    private String awbNo;

    public Codigo() {
    }


    public Codigo(String sessionToken, String awbNo) {
        super();
        this.sessionToken = sessionToken;
        this.awbNo = awbNo;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public String getAwbNo() {
        return awbNo;
    }

    public void setAwbNo(String awbNo) {
        this.awbNo = awbNo;
    }
}
