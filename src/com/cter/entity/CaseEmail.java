package com.cter.entity;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * case_mail 明细实体类
 * @author op1768
 *
 */
@Entity
@Table(name="case_email")
public class CaseEmail {
	
	@Id
	@Column(name="case_uuid")
	private String case_uuid ;//case表的uuid
	@Column(name="city_name")
	private String city_name;
	@Column(name="pop_name")
	private String pop_name;
	@Column(name="case_id")
	private String case_id;//case单号
	@Column(name="empower_id")
	private String empower_id;//授权表的主键uuid
	@Column(name="cabinet")
	private String cabinet;//机柜号,用逗号分开
	@Column(name="password_key")
	private String password_key;//此case认证口令
	@Column(name="op_type_id")
	private String op_type_id;//操作详细id（关联operation_type）
	@Column(name="op_type")
	private String op_type;//操作详细
	@Column(name="start_time")
	private String start_time;//进入 时间
	@Column(name="end_time")
	private String end_time;//出去时间
	@Column(name="descs")
	private String descs;//描述
	@Column(name="op_staff")
	private String op_staff;
	@Column(name="into_personnel")
	private String into_personnel;//进入人员
	@Column(name="add_into_personnel")
	private String add_into_personnel;//追加进入人员
	@Column(name="ins_type")
	private String ins_type;//操作类型
	@Column(name="carry_facility")
	private String carry_facility;	//携带设备信息
	@Column(name="create_time")
	@Temporal(TemporalType.TIMESTAMP) //2018-10-24 11:07:20 
	private Date create_time;	//创建时间
	@Column(name="case_status")
	private String case_status;//case状态，T 已经提交授权申请，D删除,R回复邮件, S确认授权完成
	@Column(name="send_email")
	private String  send_email;//发送人的邮箱
	@Column(name="send_email_uuid")
	private String  send_email_uuid;//发送人邮箱的uuid
	@Column(name="cc_addressee_email")
	private String  cc_addressee_email;//所有抄送人邮箱，###分开
	@Column(name="addressee_email")
	private String  addressee_email;//所有收件人邮箱，###分开
	@Column(name="supplier_name")
	private String  supplier_name;//供应商名称
	@Column(name="remarks")
	private String  remarks;//备注信息
	
	
	public String getCase_uuid() {
		return case_uuid;
	}
	public void setCase_uuid(String case_uuid) {
		this.case_uuid = case_uuid;
	}
	public String getCase_id() {
		return case_id;
	}
	public void setCase_id(String case_id) {
		this.case_id = case_id;
	}
	public String getEmpower_id() {
		return empower_id;
	}
	public void setEmpower_id(String empower_id) {
		this.empower_id = empower_id;
	}
	public String getCabinet() {
		return cabinet;
	}
	public void setCabinet(String cabinet) {
		this.cabinet = cabinet;
	}
	public String getPassword_key() {
		return password_key;
	}
	public void setPassword_key(String password_key) {
		this.password_key = password_key;
	}
	public String getOp_type_id() {
		return op_type_id;
	}
	public void setOp_type_id(String op_type_id) {
		this.op_type_id = op_type_id;
	}
	
	public String getOp_type() {
		return op_type;
	}
	public void setOp_type(String op_type) {
		this.op_type = op_type;
	}
	
	public String getDescs() {
		return descs;
	}
	public void setDescs(String descs) {
		this.descs = descs;
	}
	public String getOp_staff() {
		return op_staff;
	}
	public void setOp_staff(String op_staff) {
		this.op_staff = op_staff;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	public String getInto_personnel() {
		return into_personnel;
	}
	public void setInto_personnel(String into_personnel) {
		this.into_personnel = into_personnel;
	}
	public String getIns_type() {
		return ins_type;
	}
	public void setIns_type(String ins_type) {
		this.ins_type = ins_type;
	}
	public String getCarry_facility() {
		return carry_facility;
	}
	public void setCarry_facility(String carry_facility) {
		this.carry_facility = carry_facility;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public String getPop_name() {
		return pop_name;
	}
	public void setPop_name(String pop_name) {
		this.pop_name = pop_name;
	}
	public String getCase_status() {
		return case_status;
	}
	public void setCase_status(String case_status) {
		this.case_status = case_status;
	}
	public String getSend_email_uuid() {
		return send_email_uuid;
	}
	public void setSend_email_uuid(String send_email_uuid) {
		this.send_email_uuid = send_email_uuid;
	}
	public String getSend_email() {
		return send_email;
	}
	public void setSend_email(String send_email) {
		this.send_email = send_email;
	}
	public String getCc_addressee_email() {
		return cc_addressee_email;
	}
	public void setCc_addressee_email(String cc_addressee_email) {
		this.cc_addressee_email = cc_addressee_email;
	}
	public String getAddressee_email() {
		return addressee_email;
	}
	public void setAddressee_email(String addressee_email) {
		this.addressee_email = addressee_email;
	}
	public String getCity_name() {
		return city_name;
	}
	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}
	public String getAdd_into_personnel() {
		return add_into_personnel;
	}
	public void setAdd_into_personnel(String add_into_personnel) {
		this.add_into_personnel = add_into_personnel;
	}
	public String getSupplier_name() {
		return supplier_name;
	}
	public void setSupplier_name(String supplier_name) {
		this.supplier_name = supplier_name;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}


}
