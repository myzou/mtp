package com.cter.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 进入机房携带设备的信息
 * @author op1768
 *
 */
@Entity
@Table(name="carry_facility")
public class CarryFacility {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)    
	@Column(name="cf_id")
	private int cf_id;// 主键
	@Column(name="auth_id")
	private int auth_id;//对应的授权AuthorizationEmail的主键
	@Column(name="c_manufacturer")
	private String c_manufacturer;//生产厂家
	@Column(name="c_model")
	private String c_model;//携带设备设备型号的名称
	@Column(name="c_serial_no")
	private String c_serial_no;//序列号/车牌号
	@Column(name="c_operate")
	private String c_operate;//用途/操作
	public int getCf_id() {
		return cf_id;
	}
	public void setCf_id(int cf_id) {
		this.cf_id = cf_id;
	}
	public int getAuth_id() {
		return auth_id;
	}
	public void setAuth_id(int auth_id) {
		this.auth_id = auth_id;
	}
	public String getC_model() {
		return c_model;
	}
	public void setC_model(String c_model) {
		this.c_model = c_model;
	}
	public String getC_serial_no() {
		return c_serial_no;
	}
	public void setC_serial_no(String c_serial_no) {
		this.c_serial_no = c_serial_no;
	}
	public String getC_operate() {
		return c_operate;
	}
	public void setC_operate(String c_operate) {
		this.c_operate = c_operate;
	}
	public String getC_manufacturer() {
		return c_manufacturer;
	}
	public void setC_manufacturer(String c_manufacturer) {
		this.c_manufacturer = c_manufacturer;
	}
	
	
}
