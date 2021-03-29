package com.cter.entity;

import javax.persistence.*;
import java.util.Date;


/**
 *  准备mtp的实体类
 */
@Entity
@Table(name = "mtp_provision")
public class MTPProvision {

    @Id
    @Column(name = "case_id")
    private String caseId;
    @Column(name = "jsonstr")
    private String jsonstr;
    @Column(name = "start_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;
    @Column(name = "end_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;
    @Column(name = "run_before")
    private String runBefore;
    @Column(name = "run_after")
    private String runAfter;
    @Column(name = "runing")
    private String runing;
    @Column(name = "update_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getJsonstr() {
        return jsonstr;
    }

    public void setJsonstr(String jsonstr) {
        this.jsonstr = jsonstr;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getRunBefore() {
        return runBefore;
    }

    public void setRunBefore(String runBefore) {
        this.runBefore = runBefore;
    }

    public String getRunAfter() {
        return runAfter;
    }

    public void setRunAfter(String runAfter) {
        this.runAfter = runAfter;
    }

    public String getRuning() {
        return runing;
    }

    public void setRuning(String runing) {
        this.runing = runing;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
