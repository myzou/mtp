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
 * 用户表
 * @author op1768
 *
 */
@Entity
@Table(name="sys_user")
public class SysUser {
	
	@Id
//	 @GeneratedValue(strategy = GenerationType.IDENTITY)    
	@Column(name = "user_id", unique = true, nullable = false, insertable = true, updatable = true)
	private int user_id;
	@Column(name="user_name")
	private String user_name;
	@Column(name="password")
	private String password;
	@Column(name="create_time")
	@Temporal(TemporalType.TIMESTAMP) //2018-10-24 11:07:20 
	private Date create_time;	//创建时间
	@Column(name="update_time")
	@Temporal(TemporalType.TIMESTAMP) //2018-10-24 11:07:20 
	private Date update_time;	//更新时间
	@Column(name="status")
	private String status;//状态 0正常，1删除
	
	
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
