package com.cter.entity;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

   /**
    * trunk_info_mtp	实体类
    * @author	op1768
    * @date	2019-02-20 06:37 
    */ 

@Entity
@Table(name="trunk_info_mtp")
public class TrunkInfoMtp{
	@Id
	 @Column(name="trunk_id")
	private Long trunkId;//骨干id

	 @Column(name="provider_circuit_num")
	private String providerCircuitNum;

	 @Column(name="internal_circuit_num")
	private String internalCircuitNum;//内城线路

	 @Column(name="provider")
	private String provider;//线路

	 @Column(name="trunk_name")
	private String trunkName;//连接的名称

	 @Column(name="a_end_full_name")
	private String aEndFullName;//a设备名

	 @Column(name="z_end_full_name")
	private String zEndFullName;//z设备名

	 @Column(name="a_end_interface")
	private String aEndInterface;//a接口名

	 @Column(name="z_end_interface")
	private String zEndInterface;//z接口名

	 @Column(name="a_end_interface_ip")
	private String aEndInterfaceIp;//a接口ip

	 @Column(name="z_end_interface_ip")
	private String zEndInterfaceIp;//z接口ip

	 @Column(name="link_type")
	private String linkType;//线路类型

	 @Column(name="create_time")
	@Temporal(TemporalType.TIMESTAMP)  
	private Date createTime;//创建时间

	 @Column(name="last_updated_time")
	@Temporal(TemporalType.TIMESTAMP)  
	private Date lastUpdatedTime;//更新时间


	public void setTrunkId(Long trunkId){
		this.trunkId=trunkId;
	}

	public Long getTrunkId(){
		return trunkId;
	}

	public void setProviderCircuitNum(String providerCircuitNum){
		this.providerCircuitNum=providerCircuitNum;
	}

	public String getProviderCircuitNum(){
		return providerCircuitNum;
	}

	public void setInternalCircuitNum(String internalCircuitNum){
		this.internalCircuitNum=internalCircuitNum;
	}

	public String getInternalCircuitNum(){
		return internalCircuitNum;
	}

	public void setProvider(String provider){
		this.provider=provider;
	}

	public String getProvider(){
		return provider;
	}

	public void setTrunkName(String trunkName){
		this.trunkName=trunkName;
	}

	public String getTrunkName(){
		return trunkName;
	}

	public void setAEndFullName(String aEndFullName){
		this.aEndFullName=aEndFullName;
	}

	public String getAEndFullName(){
		return aEndFullName;
	}

	public void setZEndFullName(String zEndFullName){
		this.zEndFullName=zEndFullName;
	}

	public String getZEndFullName(){
		return zEndFullName;
	}

	public void setAEndInterface(String aEndInterface){
		this.aEndInterface=aEndInterface;
	}

	public String getAEndInterface(){
		return aEndInterface;
	}

	public void setZEndInterface(String zEndInterface){
		this.zEndInterface=zEndInterface;
	}

	public String getZEndInterface(){
		return zEndInterface;
	}

	public void setAEndInterfaceIp(String aEndInterfaceIp){
		this.aEndInterfaceIp=aEndInterfaceIp;
	}

	public String getAEndInterfaceIp(){
		return aEndInterfaceIp;
	}

	public void setZEndInterfaceIp(String zEndInterfaceIp){
		this.zEndInterfaceIp=zEndInterfaceIp;
	}

	public String getZEndInterfaceIp(){
		return zEndInterfaceIp;
	}

	public void setLinkType(String linkType){
		this.linkType=linkType;
	}

	public String getLinkType(){
		return linkType;
	}

	public void setCreateTime(Date createTime){
		this.createTime=createTime;
	}

	public Date getCreateTime(){
		return createTime;
	}

	public void setLastUpdatedTime(Date lastUpdatedTime){
		this.lastUpdatedTime=lastUpdatedTime;
	}

	public Date getLastUpdatedTime(){
		return lastUpdatedTime;
	}
}

