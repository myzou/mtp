package com.cter.bean;

/**
 * ���������������remedy�Ľ������
 * @author op1768
 * @create 2019-11-05 15:26
 * @project mtp
 */
public class ResultMessage {

    private int siteTotalSize=0;//������
    private int siteFaildSize=0;//ʧ�ܵ�����
    private int siteSuccessSize=0;//�ɹ�����
    private int siteExceptionSize=0;//��·�����쳣����
    private String siteExceptionDetailed="";//�쳣��site��ϸ
    private int mvrfSize=0;// mvrf����
    private int backboneTotalSize=0;//������
    private int backboneFaildSize=0;//ʧ�ܵ�����
    private int backboneSuccessSize=0;//�ɹ�����
    private int backboneExceptionSize=0;//�Ǹɲ����쳣����
    private String backboneExceptionDetailed="";//�쳣�ĹǸ�
    private String mvrfDetailed;// mvrf��ϸ


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
