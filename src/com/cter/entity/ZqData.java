package com.cter.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * zq_data 数据实体类
 * @author op1768
 *
 */
@Entity
@Table(name="zq_data")
public class ZqData {
	@Id
	@Column(name="code_id")
	private String code_id;
	@Column(name="sys_code")
	private String sys_code;
	@Column(name="sys_name")
	private String sys_name;
	@Column(name="param_value1")
	private String param_value1;
	
	public String getParam_value1() {
		return param_value1;
	}
	public void setParam_value1(String param_value1) {
		this.param_value1 = param_value1;
	}
	public String getCode_id() {
		return code_id;
	}
	public void setCode_id(String code_id) {
		this.code_id = code_id;
	}
	public String getSys_code() {
		return sys_code;
	}
	public void setSys_code(String sys_code) {
		this.sys_code = sys_code;
	}
	public String getSys_name() {
		return sys_name;
	}
	public void setSys_name(String sys_name) {
		this.sys_name = sys_name;
	}
	@Override
	public String toString() {
		return "ZqData [code_id=" + code_id + ", sys_code=" + sys_code + ", sys_name=" + sys_name + ", param_value1="
				+ param_value1 + "]";
	}
	

	
}
