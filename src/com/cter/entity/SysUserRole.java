package com.cter.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户角色关联表
 * @author op1768
 *
 */
@Entity
@Table(name="sys_user_role")
public class SysUserRole {
	@Id
	@Column(name="user_role_id")
	private int user_role_id;//主键
	@Column(name="role_id")
	private int role_id;//角色id
	@Column(name="user_id")
	private int user_id;//用户id
	public int getUser_role_id() {
		return user_role_id;
	}
	public void setUser_role_id(int user_role_id) {
		this.user_role_id = user_role_id;
	}
	public int getRole_id() {
		return role_id;
	}
	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

}
