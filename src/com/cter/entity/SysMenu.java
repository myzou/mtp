package com.cter.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 菜单表
 * @author op1768
 *
 */
@Entity
@Table(name="sys_menu")
public class SysMenu {
	
	@Id
//	 @GeneratedValue(strategy = GenerationType.IDENTITY)    
	@Column(name = "menu_id", unique = true, nullable = false, insertable = true, updatable = true)
	private int menu_id;//菜单主键
	@Column(name="m_id")
	private int m_id;//菜单id 一级菜单1000为单位，二级菜单在1000基础上加1
	@Column(name="menu_name")
	private String menu_name;//菜单名称
	@Column(name="menu_path")
	private String menu_path;//菜单路径
	@Column(name="parent_m_id")
	private int parent_m_id;//上级m_id
	
	@Column(name="parent_order_num")
	private int parent_order_num;//一级菜单排序
	@Column(name="child_order_num")
	private int child_order_num;//二级菜单排序  
	
	@Column(name="status")
	private String status;//状态0正常，1不可见
	@Transient
	private String menu_level1;//一级菜单
	@Transient
	private String menu_level2;//二级菜单
	@Transient
	private int belong_menu_id;//上级menu_id
	
	
	public int getMenu_id() {
		return menu_id;
	}
	public void setMenu_id(int menu_id) {
		this.menu_id = menu_id;
	}
	public String getMenu_name() {
		return menu_name;
	}
	public void setMenu_name(String menu_name) {
		this.menu_name = menu_name;
	}
	public String getMenu_path() {
		return menu_path;
	}
	public void setMenu_path(String menu_path) {
		this.menu_path = menu_path;
	}
	 
	public int getParent_m_id() {
		return parent_m_id;
	}
	public void setParent_m_id(int parent_m_id) {
		this.parent_m_id = parent_m_id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public int getM_id() {
		return m_id;
	}
	public void setM_id(int m_id) {
		this.m_id = m_id;
	}
	public String getMenu_level1() {
		return menu_level1;
	}
	public void setMenu_level1(String menu_level1) {
		this.menu_level1 = menu_level1;
	}
	public String getMenu_level2() {
		return menu_level2;
	}
	public void setMenu_level2(String menu_level2) {
		this.menu_level2 = menu_level2;
	}
	public int getBelong_menu_id() {
		return belong_menu_id;
	}
	public void setBelong_menu_id(int belong_menu_id) {
		this.belong_menu_id = belong_menu_id;
	}
 
	
	
	
	
}
