package com.cter.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * 授权申请实体类
 * @author op1768
 *
 */
@Entity
@Table(name="authorization_email")
public class AuthorizationEmail {
	
	@Id
	@Column(name = "auth_id", unique = true, nullable = false, insertable = true, updatable = true)
	private int auth_id;//主键
	@Column(name="case_id")
	private String case_id;//如果已经发送就case_id 更新到此表中
	@Column(name="case_id_uuid")
	private String case_id_uuid;//对应case_id 的uuid
	@Column(name="city_name")
	private String city_name;//地区
	@Column(name="pop_name")
	private String pop_name;//申请访问机房
	@Column(name="op_type")
	private String  op_type;//详细操作
	@Column(name="supplier_name")
	private String  supplier_name;//供应商名称
	@Column(name="start_time")
	private String  start_time;//开始时间
	@Column(name="end_time")
	private String  end_time;//结束时间
	@Column(name="company")
	private String  company;//申请人部门
	@Column(name="email")
	private String  email;//申请人Email
	@Column(name="name")
	private String name;//申请人名称
	@Column(name="remarks")
	private String remarks;//备注信息
	@Column(name="cabinet")
	private String cabinet;//机柜信息
	@Column(name="create_time")
	@Temporal(TemporalType.TIMESTAMP) //2018-10-24 11:07:20 
	private Date create_time;	//创建时间
	@Column(name="update_time")
	@Temporal(TemporalType.TIMESTAMP) //2018-10-24 11:07:20 
	private Date update_time;	//更新时间
	@Column(name="au_status")
	private String au_status;	// 状态，T未发送，D删除，S已经发送
	@Column(name="into_personnel")
	private String into_personnel;//进入人员
	@Column(name="carry_facility")
	private String carry_facility;	//携带设备信息
 
	@Transient
	private List< IntoPersonnel>  intoPersonnelList;//进入人员 List
	@Transient
	private List<CarryFacility>  carryFacilityList;//携带设备细信息
	@Transient
	private String addressee_email;//发送人邮箱
	@Transient
	private String cc_addressee_email;//抄送人邮箱

	
	
	public int getAuth_id() {
		return auth_id;
	}
	public void setAuth_id(int auth_id) {
		this.auth_id = auth_id;
	}
	public String getPop_name() {
		return pop_name;
	}
	public void setPop_name(String pop_name) {
		this.pop_name = pop_name;
	}
	public String getSupplier_name() {
		return supplier_name;
	}
	public void setSupplier_name(String supplier_name) {
		this.supplier_name = supplier_name;
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
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public List<IntoPersonnel> getIntoPersonnelList() {
		return intoPersonnelList;
	}
	public void setIntoPersonnelList(List<IntoPersonnel> intoPersonnelList) {
		this.intoPersonnelList = intoPersonnelList;
	}
	public List<CarryFacility> getCarryFacilityList() {
		return carryFacilityList;
	}
	public void setCarryFacilityList(List<CarryFacility> carryFacilityList) {
		this.carryFacilityList = carryFacilityList;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public Date getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
	public String getAu_status() {
		return au_status;
	}
	public void setAu_status(String au_status) {
		this.au_status = au_status;
	}
	public String getCase_id() {
		return case_id;
	}
	public void setCase_id(String case_id) {
		this.case_id = case_id;
	}
	public String getCase_id_uuid() {
		return case_id_uuid;
	}
	public void setCase_id_uuid(String case_id_uuid) {
		this.case_id_uuid = case_id_uuid;
	}
	public String getCity_name() {
		return city_name;
	}
	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}
	public String getOp_type() {
		return op_type;
	}
	public void setOp_type(String op_type) {
		this.op_type = op_type;
	}
	public String getCabinet() {
		return cabinet;
	}
	public void setCabinet(String cabinet) {
		this.cabinet = cabinet;
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
	public String getInto_personnel() {
		return into_personnel;
	}
	public void setInto_personnel(String into_personnel) {
		this.into_personnel = into_personnel;
	}
	public String getCarry_facility() {
		return carry_facility;
	}
	public void setCarry_facility(String carry_facility) {
		this.carry_facility = carry_facility;
	}
	
	
	
	
}
