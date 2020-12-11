package com.cter.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * 角色表
 * @author op1768
 *
 */
@Entity
@Table(name="sys_role")
public class SysRole {
	
	@Id
//	 @GeneratedValue(strategy = GenerationType.IDENTITY)    
	@Column(name = "role_id", unique = true, nullable = false, insertable = true, updatable = true)
	private int role_id;
	@Column(name="role_name")
	private String role_name;
	@Column(name="create_time")
	@Temporal(TemporalType.TIMESTAMP) //2018-10-24 11:07:20 
	private Date create_time;	//创建时间
	@Column(name="update_time")
	@Temporal(TemporalType.TIMESTAMP) //2018-10-24 11:07:20 
	private Date update_time;	//更新时间
	@Column(name="create_from")
	private String create_from;//创建角色来源
	@Column(name="status")
	private String status;//状态 0正常，1删除
	
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public String getCreate_from() {
		return create_from;
	}
	public void setCreate_from(String create_from) {
		this.create_from = create_from;
	}
	public String getRole_name() {
		return role_name;
	}
	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}
	public Date getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
	public int getRole_id() {
		return role_id;
	}
	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	   

}
