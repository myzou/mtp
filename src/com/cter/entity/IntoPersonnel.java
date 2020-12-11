package com.cter.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 授权申请实体类-
 * 进入机房人员列表实体类
 * 
 * @author op1768
 *
 */
@Entity
@Table(name="into_personnel")
public class IntoPersonnel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)    
	@Column(name="ip_id")
	private int ip_id;// 主键
	@Column(name="auth_id")
	private int auth_id;//对应的授权AuthorizationEmail的主键
	@Column(name="p_name")
	private String p_name;//进入机房人员名称
	@Column(name="p_id")
	private String p_id;//进入机房人员id
	@Column(name="p_company")
	private String p_company;//进入机房人员所属部门 默认的
	@Column(name="p_other_company")
	private String p_other_company;//进入机房人员部门 在默认中没有的
	public int getIp_id() {
		return ip_id;
	}
	public void setIp_id(int ip_id) {
		this.ip_id = ip_id;
	}
	public int getAuth_id() {
		return auth_id;
	}
	public void setAuth_id(int auth_id) {
		this.auth_id = auth_id;
	}
	public String getP_name() {
		return p_name;
	}
	public void setP_name(String p_name) {
		this.p_name = p_name;
	}
	public String getP_id() {
		return p_id;
	}
	public void setP_id(String p_id) {
		this.p_id = p_id;
	}
	public String getP_company() {
		return p_company;
	}
	public void setP_company(String p_company) {
		this.p_company = p_company;
	}
	public String getP_other_company() {
		return p_other_company;
	}
	public void setP_other_company(String p_other_company) {
		this.p_other_company = p_other_company;
	}
	@Override
	public String toString() {
		return "IntoPersonnel [ip_id=" + ip_id + ", auth_id=" + auth_id + ", p_name=" + p_name + ", p_id=" + p_id
				+ ", p_company=" + p_company + ", p_other_company=" + p_other_company + "]";
	}
	 
}
