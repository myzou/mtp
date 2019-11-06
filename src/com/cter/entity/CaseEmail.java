package com.cter.entity;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * case_mail ��ϸʵ����
 * @author op1768
 *
 */
@Entity
@Table(name="case_email")
public class CaseEmail {
	
	@Id
	@Column(name="case_uuid")
	private String case_uuid ;//case���uuid
	@Column(name="city_name")
	private String city_name;
	@Column(name="pop_name")
	private String pop_name;
	@Column(name="case_id")
	private String case_id;//case����
	@Column(name="empower_id")
	private String empower_id;//��Ȩ�������uuid
	@Column(name="cabinet")
	private String cabinet;//�����,�ö��ŷֿ�
	@Column(name="password_key")
	private String password_key;//��case��֤����
	@Column(name="op_type_id")
	private String op_type_id;//������ϸid������operation_type��
	@Column(name="op_type")
	private String op_type;//������ϸ
	@Column(name="start_time")
	private String start_time;//���� ʱ��
	@Column(name="end_time")
	private String end_time;//��ȥʱ��
	@Column(name="descs")
	private String descs;//����
	@Column(name="op_staff")
	private String op_staff;
	@Column(name="into_personnel")
	private String into_personnel;//������Ա
	@Column(name="add_into_personnel")
	private String add_into_personnel;//׷�ӽ�����Ա
	@Column(name="ins_type")
	private String ins_type;//��������
	@Column(name="carry_facility")
	private String carry_facility;	//Я���豸��Ϣ
	@Column(name="create_time")
	@Temporal(TemporalType.TIMESTAMP) //2018-10-24 11:07:20 
	private Date create_time;	//����ʱ��
	@Column(name="case_status")
	private String case_status;//case״̬��T �Ѿ��ύ��Ȩ���룬Dɾ��,R�ظ��ʼ�, Sȷ����Ȩ���
	@Column(name="send_email")
	private String  send_email;//�����˵�����
	@Column(name="send_email_uuid")
	private String  send_email_uuid;//�����������uuid
	@Column(name="cc_addressee_email")
	private String  cc_addressee_email;//���г��������䣬###�ֿ�
	@Column(name="addressee_email")
	private String  addressee_email;//�����ռ������䣬###�ֿ�
	@Column(name="supplier_name")
	private String  supplier_name;//��Ӧ������
	@Column(name="remarks")
	private String  remarks;//��ע��Ϣ
	
	
	public String getCase_uuid() {
		return case_uuid;
	}
	public void setCase_uuid(String case_uuid) {
		this.case_uuid = case_uuid;
	}
	public String getCase_id() {
		return case_id;
	}
	public void setCase_id(String case_id) {
		this.case_id = case_id;
	}
	public String getEmpower_id() {
		return empower_id;
	}
	public void setEmpower_id(String empower_id) {
		this.empower_id = empower_id;
	}
	public String getCabinet() {
		return cabinet;
	}
	public void setCabinet(String cabinet) {
		this.cabinet = cabinet;
	}
	public String getPassword_key() {
		return password_key;
	}
	public void setPassword_key(String password_key) {
		this.password_key = password_key;
	}
	public String getOp_type_id() {
		return op_type_id;
	}
	public void setOp_type_id(String op_type_id) {
		this.op_type_id = op_type_id;
	}
	
	public String getOp_type() {
		return op_type;
	}
	public void setOp_type(String op_type) {
		this.op_type = op_type;
	}
	
	public String getDescs() {
		return descs;
	}
	public void setDescs(String descs) {
		this.descs = descs;
	}
	public String getOp_staff() {
		return op_staff;
	}
	public void setOp_staff(String op_staff) {
		this.op_staff = op_staff;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	public String getInto_personnel() {
		return into_personnel;
	}
	public void setInto_personnel(String into_personnel) {
		this.into_personnel = into_personnel;
	}
	public String getIns_type() {
		return ins_type;
	}
	public void setIns_type(String ins_type) {
		this.ins_type = ins_type;
	}
	public String getCarry_facility() {
		return carry_facility;
	}
	public void setCarry_facility(String carry_facility) {
		this.carry_facility = carry_facility;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public String getPop_name() {
		return pop_name;
	}
	public void setPop_name(String pop_name) {
		this.pop_name = pop_name;
	}
	public String getCase_status() {
		return case_status;
	}
	public void setCase_status(String case_status) {
		this.case_status = case_status;
	}
	public String getSend_email_uuid() {
		return send_email_uuid;
	}
	public void setSend_email_uuid(String send_email_uuid) {
		this.send_email_uuid = send_email_uuid;
	}
	public String getSend_email() {
		return send_email;
	}
	public void setSend_email(String send_email) {
		this.send_email = send_email;
	}
	public String getCc_addressee_email() {
		return cc_addressee_email;
	}
	public void setCc_addressee_email(String cc_addressee_email) {
		this.cc_addressee_email = cc_addressee_email;
	}
	public String getAddressee_email() {
		return addressee_email;
	}
	public void setAddressee_email(String addressee_email) {
		this.addressee_email = addressee_email;
	}
	public String getCity_name() {
		return city_name;
	}
	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}
	public String getAdd_into_personnel() {
		return add_into_personnel;
	}
	public void setAdd_into_personnel(String add_into_personnel) {
		this.add_into_personnel = add_into_personnel;
	}
	public String getSupplier_name() {
		return supplier_name;
	}
	public void setSupplier_name(String supplier_name) {
		this.supplier_name = supplier_name;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}


}
