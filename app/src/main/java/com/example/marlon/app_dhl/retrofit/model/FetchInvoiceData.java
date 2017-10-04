package com.example.marlon.app_dhl.retrofit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FetchInvoiceData {
    @SerializedName("Result_Cd")
    @Expose
    private String resultCd;
    @SerializedName("Result_Msg")
    @Expose
    private String resultMsg;
    @SerializedName("vw_Charge_Ty")
    @Expose
    private String vwChargeTy;
    @SerializedName("vw_Total_Charge_Amount")
    @Expose
    private String vwTotalChargeAmount;
    @SerializedName("vw_DCG_Charge_Cd")
    @Expose
    private String vwDCGChargeCd;
    @SerializedName("vw_Charge_Global_Nm")
    @Expose
    private String vwChargeGlobalNm;
    @SerializedName("vw_Charge_Nm")
    @Expose
    private String vwChargeNm;

    public FetchInvoiceData() {
    }


    public FetchInvoiceData(String resultCd, String resultMsg, String vwChargeTy, String vwTotalChargeAmount, String vwDCGChargeCd, String vwChargeGlobalNm, String vwChargeNm) {
        super();
        this.resultCd = resultCd;
        this.resultMsg = resultMsg;
        this.vwChargeTy = vwChargeTy;
        this.vwTotalChargeAmount = vwTotalChargeAmount;
        this.vwDCGChargeCd = vwDCGChargeCd;
        this.vwChargeGlobalNm = vwChargeGlobalNm;
        this.vwChargeNm = vwChargeNm;
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

    public String getVwChargeTy() {
        return vwChargeTy;
    }

    public void setVwChargeTy(String vwChargeTy) {
        this.vwChargeTy = vwChargeTy;
    }

    public String getVwTotalChargeAmount() {
        return vwTotalChargeAmount;
    }

    public void setVwTotalChargeAmount(String vwTotalChargeAmount) {
        this.vwTotalChargeAmount = vwTotalChargeAmount;
    }

    public String getVwDCGChargeCd() {
        return vwDCGChargeCd;
    }

    public void setVwDCGChargeCd(String vwDCGChargeCd) {
        this.vwDCGChargeCd = vwDCGChargeCd;
    }

    public String getVwChargeGlobalNm() {
        return vwChargeGlobalNm;
    }

    public void setVwChargeGlobalNm(String vwChargeGlobalNm) {
        this.vwChargeGlobalNm = vwChargeGlobalNm;
    }

    public String getVwChargeNm() {
        return vwChargeNm;
    }

    public void setVwChargeNm(String vwChargeNm) {
        this.vwChargeNm = vwChargeNm;
    }
}
