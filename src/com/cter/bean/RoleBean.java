package com.cter.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 角色表单页面接收数据实体类
 * @author op1768
 *
 */
public class RoleBean {
	
	private int  role_id;//角色id
	private String role_name;//角色名称
	
	private int menu_id;//菜单主键
	private int m_id;//菜单id 一级菜单1000为单位，二级菜单在1000基础上加1
	private String menu_name;//菜单名称
	private int parent_m_id;//上级m_id
	private int parent_order_num;//一级菜单排序
	private int child_order_num;//二级菜单排序
	
	private long cnull;//去重多余的字段
	
	public int getRole_id() {
		return role_id;
	}
	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}
	public String getRole_name() {
		return role_name;
	}
	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}
	public int getMenu_id() {
		return menu_id;
	}
	public void setMenu_id(int menu_id) {
		this.menu_id = menu_id;
	}
	public int getM_id() {
		return m_id;
	}
	public void setM_id(int m_id) {
		this.m_id = m_id;
	}
	public String getMenu_name() {
		return menu_name;
	}
	public void setMenu_name(String menu_name) {
		this.menu_name = menu_name;
	}
	public int getParent_m_id() {
		return parent_m_id;
	}
	public void setParent_m_id(int parent_m_id) {
		this.parent_m_id = parent_m_id;
	}
	public int getParent_order_num() {
		return parent_order_num;
	}
	public void setParent_order_num(int parent_order_num) {
		this.parent_order_num = parent_order_num;
	}
	public int getChild_order_num() {
		return child_order_num;
	}
	public void setChild_order_num(int child_order_num) {
		this.child_order_num = child_order_num;
	}
	public long getCnull() {
		return cnull;
	}
	public void setCnull(long cnull) {
		this.cnull = cnull;
	}
 
	
	
//	private String status;//状态0正常，1不可见
//	private String menu_level1;//一级菜单
//	private String menu_level2;//二级菜单
//	private int belong_menu_id;//上级menu_id
 
	
}
