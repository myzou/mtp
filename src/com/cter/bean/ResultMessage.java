package com.cter.bean;

/**
 * 这个类是用来返回remedy的结果的类
 * @author op1768
 * @create 2019-11-05 15:26
 * @project mtp
 */
public class ResultMessage {

    private int siteTotalSize=0;//总数量
    private int siteFaildSize=0;//失败的数量
    private int siteSuccessSize=0;//成功数量
    private int siteExceptionSize=0;//线路参数异常数量
    private String siteExceptionDetailed="";//异常的site详细
    private int mvrfSize=0;// mvrf数量
    private int backboneTotalSize=0;//总数量
    private int backboneFaildSize=0;//失败的数量
    private int backboneSuccessSize=0;//成功数量
    private int backboneExceptionSize=0;//骨干参数异常数量
    private String backboneExceptionDetailed="";//异常的骨干
    private String mvrfDetailed;// mvrf详细


    public int getSiteTotalSize() {
        return siteTotalSize;
    }

    public void setSiteTotalSize(int siteTotalSize) {
        this.siteTotalSize = siteTotalSize;
    }

    public int getBackboneTotalSize() {
        return backboneTotalSize;
    }

    public void setBackboneTotalSize(int backboneTotalSize) {
        this.backboneTotalSize = backboneTotalSize;
    }

    public int getSiteFaildSize() {
        return siteFaildSize;
    }

    public void setSiteFaildSize(int siteFaildSize) {
        this.siteFaildSize = siteFaildSize;
    }

    public int getSiteSuccessSize() {
        return siteSuccessSize;
    }

    public void setSiteSuccessSize(int siteSuccessSize) {
        this.siteSuccessSize = siteSuccessSize;
    }

    public int getSiteExceptionSize() {
        return siteExceptionSize;
    }

    public void setSiteExceptionSize(int siteExceptionSize) {
        this.siteExceptionSize = siteExceptionSize;
    }

    public String getSiteExceptionDetailed() {
        return siteExceptionDetailed;
    }

    public void setSiteExceptionDetailed(String siteExceptionDetailed) {
        this.siteExceptionDetailed = siteExceptionDetailed;
    }

    public int getMvrfSize() {
        return mvrfSize;
    }

    public void setMvrfSize(int mvrfSize) {
        this.mvrfSize = mvrfSize;
    }

    public int getBackboneFaildSize() {
        return backboneFaildSize;
    }

    public void setBackboneFaildSize(int backboneFaildSize) {
        this.backboneFaildSize = backboneFaildSize;
    }

    public int getBackboneSuccessSize() {
        return backboneSuccessSize;
    }

    public void setBackboneSuccessSize(int backboneSuccessSize) {
        this.backboneSuccessSize = backboneSuccessSize;
    }

    public int getBackboneExceptionSize() {
        return backboneExceptionSize;
    }

    public void setBackboneExceptionSize(int backboneExceptionSize) {
        this.backboneExceptionSize = backboneExceptionSize;
    }

    public String getBackboneExceptionDetailed() {
        return backboneExceptionDetailed;
    }

    public void setBackboneExceptionDetailed(String backboneExceptionDetailed) {
        this.backboneExceptionDetailed = backboneExceptionDetailed;
    }

    public String getMvrfDetailed() {
        return mvrfDetailed;
    }

    public void setMvrfDetailed(String mvrfDetailed) {
        this.mvrfDetailed = mvrfDetailed;
    }
}
