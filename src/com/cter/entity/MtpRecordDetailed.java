package com.cter.entity;

import java.util.Date;

import javax.persistence.*;

/**
 * mtp_record_detailed	实体类
 * 这个表是用来储存 跑 mtp 的详细数据。
 * @author op1768
 * @date 2019-08-05 19:28
 */

@Entity
@Table(name = "mtp_record_detailed")
public class MtpRecordDetailed {
    @Id
    @Column(name = "mtp_record_detailed_uuid")
    private String mtpRecordDetailedUuid;//mtp线路详细uuid

    @Column(name = "case_id")
    private String caseId;//case_id

    @Column(name = "trunk_id")
    private String trunkId;

    @Column(name = "end_full_name")
    private String endFullName;//设备全名

    @Column(name = "end_interface")
    private String endInterface;//pe端口

    @Column(name = "device_ip")
    private String deviceIp;//pe ip

    @Column(name = "provider_circuit_num")
    private String providerCircuitNum;//线路编号

    @Column(name = "show_type")
    private String showType;//执行的命令类型

    @Column(name = "before_status")
    private String beforeStatus;

    @Column(name = "before_ping_status")
    private String beforePingStatus;

    @Column(name = "after_ping_status")
    private String afterPingStatus;

    @Column(name = "before_vrf_site_id")
    private String beforeVrfSiteId;//之前的vrfSiteId 如果有，就根据这个来对比，如果没 就根据

    @Column(name = "after_vrf_site_id")
    private String afterVrfSiteId;

    @Column(name = "before_result_url")
    private String beforeResultUrl;

    @Column(name = "before_delay")
    private String beforeDelay;//维护的之前延迟

    @Column(name = "before_error_cause")
    private String beforeErrorCause;

    @Column(name = "after_status")
    private String afterStatus;

    @Column(name = "after_delay")
    private String afterDelay;//维护的之后延迟

    @Column(name = "after_result_url")
    private String afterResultUrl;

    @Column(name = "after_error_cause")
    private String afterErrorCause;

    @Column(name = "case_status")
    private String caseStatus;//case状态

    @Column(name = "send_size")
    private String sendSize;//包大小

    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @Column(name = "last_updated_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdatedTime;

    @Column(name = "before_ce_wan_ip")
    private String beforeCeWanIp;

    @Column(name = "internal_site_id")
    private String internalSiteId;//线路编号

    @Column(name = "before_pe_wan_ip")
    private String beforePeWanIp;

    @Column(name = "before_tcp_type")
    private String beforeTcpType;

    @Column(name = "before_end_full_name")
    private String beforeEndFullName;

    @Column(name = "before_end_interface")
    private String beforeEndInterface;

    @Column(name = "after_ce_wan_ip")
    private String afterCeWanIp;

    @Column(name = "after_pe_wan_ip")
    private String afterPeWanIp;

    @Column(name = "after_tcp_type")
    private String afterTcpType;

    @Column(name = "after_end_full_name")
    private String afterEndFullName;

    @Column(name = "after_end_interface")
    private String afterEndInterface;


    public void setMtpRecordDetailedUuid(String mtpRecordDetailedUuid) {
        this.mtpRecordDetailedUuid = mtpRecordDetailedUuid;
    }

    public String getMtpRecordDetailedUuid() {
        return mtpRecordDetailedUuid;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setTrunkId(String trunkId) {
        this.trunkId = trunkId;
    }

    public String getTrunkId() {
        return trunkId;
    }

    public void setEndFullName(String endFullName) {
        this.endFullName = endFullName;
    }

    public String getEndFullName() {
        return endFullName;
    }

    public void setEndInterface(String endInterface) {
        this.endInterface = endInterface;
    }

    public String getEndInterface() {
        return endInterface;
    }

    public void setDeviceIp(String deviceIp) {
        this.deviceIp = deviceIp;
    }

    public String getDeviceIp() {
        return deviceIp;
    }

    public void setProviderCircuitNum(String providerCircuitNum) {
        this.providerCircuitNum = providerCircuitNum;
    }

    public String getProviderCircuitNum() {
        return providerCircuitNum;
    }

    public void setShowType(String showType) {
        this.showType = showType;
    }

    public String getShowType() {
        return showType;
    }

    public void setBeforeStatus(String beforeStatus) {
        this.beforeStatus = beforeStatus;
    }

    public String getBeforeStatus() {
        return beforeStatus;
    }

    public void setBeforeResultUrl(String beforeResultUrl) {
        this.beforeResultUrl = beforeResultUrl;
    }

    public String getBeforeResultUrl() {
        return beforeResultUrl;
    }

    public void setBeforeDelay(String beforeDelay) {
        this.beforeDelay = beforeDelay;
    }

    public String getBeforeDelay() {
        return beforeDelay;
    }

    public void setBeforeErrorCause(String beforeErrorCause) {
        this.beforeErrorCause = beforeErrorCause;
    }

    public String getBeforeErrorCause() {
        return beforeErrorCause;
    }

    public void setAfterStatus(String afterStatus) {
        this.afterStatus = afterStatus;
    }

    public String getAfterStatus() {
        return afterStatus;
    }

    public void setAfterDelay(String afterDelay) {
        this.afterDelay = afterDelay;
    }

    public String getAfterDelay() {
        return afterDelay;
    }

    public void setAfterResultUrl(String afterResultUrl) {
        this.afterResultUrl = afterResultUrl;
    }

    public String getAfterResultUrl() {
        return afterResultUrl;
    }

    public void setAfterErrorCause(String afterErrorCause) {
        this.afterErrorCause = afterErrorCause;
    }

    public String getAfterErrorCause() {
        return afterErrorCause;
    }

    public void setCaseStatus(String caseStatus) {
        this.caseStatus = caseStatus;
    }

    public String getCaseStatus() {
        return caseStatus;
    }

    public void setSendSize(String sendSize) {
        this.sendSize = sendSize;
    }

    public String getSendSize() {
        return sendSize;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setLastUpdatedTime(Date lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }

    public Date getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public void setBeforeCeWanIp(String beforeCeWanIp) {
        this.beforeCeWanIp = beforeCeWanIp;
    }

    public String getBeforeCeWanIp() {
        return beforeCeWanIp;
    }

    public void setInternalSiteId(String internalSiteId) {
        this.internalSiteId = internalSiteId;
    }

    public String getInternalSiteId() {
        return internalSiteId;
    }

    public void setBeforePeWanIp(String beforePeWanIp) {
        this.beforePeWanIp = beforePeWanIp;
    }

    public String getBeforePeWanIp() {
        return beforePeWanIp;
    }

    public void setBeforeTcpType(String beforeTcpType) {
        this.beforeTcpType = beforeTcpType;
    }

    public String getBeforeTcpType() {
        return beforeTcpType;
    }

    public void setBeforeEndFullName(String beforeEndFullName) {
        this.beforeEndFullName = beforeEndFullName;
    }

    public String getBeforeEndFullName() {
        return beforeEndFullName;
    }

    public void setBeforeEndInterface(String beforeEndInterface) {
        this.beforeEndInterface = beforeEndInterface;
    }

    public String getBeforeEndInterface() {
        return beforeEndInterface;
    }

    public void setAfterCeWanIp(String afterCeWanIp) {
        this.afterCeWanIp = afterCeWanIp;
    }

    public String getAfterCeWanIp() {
        return afterCeWanIp;
    }

    public void setAfterPeWanIp(String afterPeWanIp) {
        this.afterPeWanIp = afterPeWanIp;
    }

    public String getAfterPeWanIp() {
        return afterPeWanIp;
    }

    public void setAfterTcpType(String afterTcpType) {
        this.afterTcpType = afterTcpType;
    }

    public String getAfterTcpType() {
        return afterTcpType;
    }

    public void setAfterEndFullName(String afterEndFullName) {
        this.afterEndFullName = afterEndFullName;
    }

    public String getAfterEndFullName() {
        return afterEndFullName;
    }

    public void setAfterEndInterface(String afterEndInterface) {
        this.afterEndInterface = afterEndInterface;
    }

    public String getAfterEndInterface() {
        return afterEndInterface;
    }

    public String getBeforeVrfSiteId() {
        return beforeVrfSiteId;
    }

    public void setBeforeVrfSiteId(String beforeVrfSiteId) {
        this.beforeVrfSiteId = beforeVrfSiteId;
    }

    public String getAfterVrfSiteId() {
        return afterVrfSiteId;
    }

    public void setAfterVrfSiteId(String afterVrfSiteId) {
        this.afterVrfSiteId = afterVrfSiteId;
    }

    public String getBeforePingStatus() {
        return beforePingStatus;
    }

    public void setBeforePingStatus(String beforePingStatus) {
        this.beforePingStatus = beforePingStatus;
    }

    public String getAfterPingStatus() {
        return afterPingStatus;
    }

    public void setAfterPingStatus(String afterPingStatus) {
        this.afterPingStatus = afterPingStatus;
    }
}

