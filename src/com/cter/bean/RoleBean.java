package com.cter.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * ��ɫ��ҳ���������ʵ����
 * @author op1768
 *
 */
public class RoleBean {
	
	private int  role_id;//��ɫid
	private String role_name;//��ɫ����
	
	private int menu_id;//�˵�����
	private int m_id;//�˵�id һ���˵�1000Ϊ��λ�������˵���1000�����ϼ�1
	private String menu_name;//�˵�����
	private int parent_m_id;//�ϼ�m_id
	private int parent_order_num;//һ���˵�����
	private int child_order_num;//�����˵�����
	
	private long cnull;//ȥ�ض�����ֶ�
	
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
 
	
	
//	private String status;//״̬0������1���ɼ�
//	private String menu_level1;//һ���˵�
//	private String menu_level2;//�����˵�
//	private int belong_menu_id;//�ϼ�menu_id
 
	
}
