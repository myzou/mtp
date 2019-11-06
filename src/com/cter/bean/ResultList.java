package com.cter.bean;

/**
 * @author op1768
 * @create 2019-09-03 18:32
 * @project mtp
 */
public class ResultList {
    private String status;
    private String msg;
    private String totalNumber;
    private String successNumber;
    private String failNumber;
    private String resultUrl;
    private String differInternalSiteId;
    private String errorInternalSiteId;
    private String tcpType;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(String totalNumber) {
        this.totalNumber = totalNumber;
    }

    public String getSuccessNumber() {
        return successNumber;
    }

    public void setSuccessNumber(String successNumber) {
        this.successNumber = successNumber;
    }

    public String getFailNumber() {
        return failNumber;
    }

    public void setFailNumber(String failNumber) {
        this.failNumber = failNumber;
    }

    public String getResultUrl() {
        return resultUrl;
    }

    public void setResultUrl(String resultUrl) {
        this.resultUrl = resultUrl;
    }

    public String getDifferInternalSiteId() {
        return differInternalSiteId;
    }

    public void setDifferInternalSiteId(String differInternalSiteId) {
        this.differInternalSiteId = differInternalSiteId;
    }

    public String getErrorInternalSiteId() {
        return errorInternalSiteId;
    }

    public void setErrorInternalSiteId(String errorInternalSiteId) {
        this.errorInternalSiteId = errorInternalSiteId;
    }

    public String getTcpType() {
        return tcpType;
    }

    public void setTcpType(String tcpType) {
        this.tcpType = tcpType;
    }

}
