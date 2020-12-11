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
 * 角色菜单关联表
 * @author op1768
 *
 */
@Entity
@Table(name="sys_role_menu")
public class SysRoleMenu {
	
	@Id
//	 @GeneratedValue(strategy = GenerationType.IDENTITY)    
	@Column(name = "role_menu_id", unique = true, nullable = false, insertable = true, updatable = true)
	private int role_menu_id;//角色菜单表主键
	@Column(name = "role_id")
	private int role_id;//角色id sys_user.role_id
	@Column(name = "menu_id")
	private int menu_id;//菜单id sys_menu.menu_id
	@Column(name = "status")
	private String status;// 状态0正常，1不可见
	public int getRole_menu_id() {
		return role_menu_id;
	}
	public void setRole_menu_id(int role_menu_id) {
		this.role_menu_id = role_menu_id;
	}
	public int getRole_id() {
		return role_id;
	}
	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}
	public int getMenu_id() {
		return menu_id;
	}
	public void setMenu_id(int menu_id) {
		this.menu_id = menu_id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
	
}
