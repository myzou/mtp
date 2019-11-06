package com.cter.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * 授权信息实体类
 * @author op1768
 *
 */
@Entity
@Table(name="empower_message")
public class EmpowerMessage {
	
	@Id
	@Column(name="uuid")
	private String uuid ;//id uuid
	@Column(name="city_name")
	private String city_name;//城市名称
	@Column(name="pop_name")
	private String pop_name;//pop名称（机房名称）
//	@Transient//非数据库字段\
	@Column(name="cc_addressee_email")
	private String  cc_addressee_email;//所有抄送人邮箱，###分开
	@Column(name="addressee_email")
	private String  addressee_email;//所有收件人邮箱，###分开
	@Column(name="create_time")
	@Temporal(TemporalType.TIMESTAMP) //2018-10-24 11:07:20 
	private Date create_time;	//创建时间
	@Column(name="stem_from")
	private String stem_from ;//来源
	@Column(name="supplier_name")
	private String supplier_name ;//供应商名称
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getPop_name() {
		return pop_name;
	}
	public void setPop_name(String pop_name) {
		this.pop_name = pop_name;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public String getAddressee_email() {
		return addressee_email;
	}
	public void setAddressee_email(String addressee_email) {
		this.addressee_email = addressee_email;
	}
	public String getCc_addressee_email() {
		return cc_addressee_email;
	}
	public void setCc_addressee_email(String cc_addressee_email) {
		this.cc_addressee_email = cc_addressee_email;
	}
	public String getStem_from() {
		return stem_from;
	}
	public void setStem_from(String stem_from) {
		this.stem_from = stem_from;
	}
	public String getCity_name() {
		return city_name;
	}
	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}
	public String getSupplier_name() {
		return supplier_name;
	}
	public void setSupplier_name(String supplier_name) {
		this.supplier_name = supplier_name;
	}
 
	
	

}
