package com.example.marlon.app_dhl.retrofit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Marlon on 25/7/2017.
 */

public class UsuarioIngreso {

    @SerializedName("Result_Cd")
    @Expose
    private String resultCd;
    @SerializedName("Result_Msg")
    @Expose
    private String resultMsg;
    @SerializedName("Session_Token")
    @Expose
    private String sessionToken;
    @SerializedName("Username")
    @Expose
    private String username;

    public UsuarioIngreso() {
    }

    public UsuarioIngreso(String resultCd, String resultMsg, String sessionToken, String username) {
        super();
        this.resultCd = resultCd;
        this.resultMsg = resultMsg;
        this.sessionToken = sessionToken;
        this.username = username;
    }

    public String getResultCd() {
        return resultCd;
    }

    public void setResultCd(String resultCd) {
        this.resultCd = resultCd;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
