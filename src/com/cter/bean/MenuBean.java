package com.cter.bean;


/**
 * 菜单表单页面接收数据实体类
 * @author Administrator
 *
 */
public class MenuBean {

	private int menu_id;//菜单主键
	private int m_id;//菜单id 一级菜单1000为单位，二级菜单在1000基础上加1
	private String menu_name;//菜单名称
	private String menu_path;//菜单路径
	private int parent_m_id;//上级菜单id
	private int parent_order_num;//一级菜单排序
	private int child_order_num;//二级菜单排序
	private String status;//状态0正常，1不可见
	
	private String user_name;//用户名称
	
	private String role_name;//角色名称
	private int role_id;//角色id
	private String r_status;//角色状态  0正常 
	private String m_status;//菜单状态 0正常
	private String rm_status;//角色菜单状态  0正常
	private String u_status;//角色状态 0正常
	
	private long cnull;//去重多余的字段
	
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
 
	public int getChild_order_num() {
		return child_order_num;
	}
	public void setChild_order_num(int child_order_num) {
		this.child_order_num = child_order_num;
	}
	public int getParent_order_num() {
		return parent_order_num;
	}
	public void setParent_order_num(int parent_order_num) {
		this.parent_order_num = parent_order_num;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRole_name() {
		return role_name;
	}
	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}
	public int getRole_id() {
		return role_id;
	}
	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}
	public String getR_status() {
		return r_status;
	}
	public void setR_status(String r_status) {
		this.r_status = r_status;
	}
	public String getM_status() {
		return m_status;
	}
	public void setM_status(String m_status) {
		this.m_status = m_status;
	}
	public String getRm_status() {
		return rm_status;
	}
	public void setRm_status(String rm_status) {
		this.rm_status = rm_status;
	}
	public String getU_status() {
		return u_status;
	}
	public void setU_status(String u_status) {
		this.u_status = u_status;
	}
	public long getCnull() {
		return cnull;
	}
	public void setCnull(long cnull) {
		this.cnull = cnull;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	
	
	

}
