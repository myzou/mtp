package com.cter.bean;


/**
 * �˵���ҳ���������ʵ����
 * @author Administrator
 *
 */
public class MenuBean {

	private int menu_id;//�˵�����
	private int m_id;//�˵�id һ���˵�1000Ϊ��λ�������˵���1000�����ϼ�1
	private String menu_name;//�˵�����
	private String menu_path;//�˵�·��
	private int parent_m_id;//�ϼ��˵�id
	private int parent_order_num;//һ���˵�����
	private int child_order_num;//�����˵�����
	private String status;//״̬0������1���ɼ�
	
	private String user_name;//�û�����
	
	private String role_name;//��ɫ����
	private int role_id;//��ɫid
	private String r_status;//��ɫ״̬  0���� 
	private String m_status;//�˵�״̬ 0����
	private String rm_status;//��ɫ�˵�״̬  0����
	private String u_status;//��ɫ״̬ 0����
	
	private long cnull;//ȥ�ض�����ֶ�
	
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
