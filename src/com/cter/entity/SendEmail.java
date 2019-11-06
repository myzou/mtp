package com.cter.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ��������ı� ʵ����
 * @author op1768
 *
 */
@Entity
@Table(name="send_email")
public class SendEmail {
		@Id
		@Column(name="email_uuid")
		private String email_uuid;	//����uuid ����
		@Column(name="email_name")
		private String email_name;	//���������
		@Column(name="email_code")
		private String 	email_code;//�����ʺ�
		@Column(name="email_password")
		private String   email_password;//��������
		@Column(name="email_host")
		private String email_host;//�����ַ
		@Column(name="protocol")
		private String protocol;//����Э�鷽ʽ
		public String getEmail_name() {
			return email_name;
		}
		public void setEmail_name(String email_name) {
			this.email_name = email_name;
		}
		public String getEmail_code() {
			return email_code;
		}
		public void setEmail_code(String email_code) {
			this.email_code = email_code;
		}
		public String getEmail_password() {
			return email_password;
		}
		public void setEmail_password(String email_password) {
			this.email_password = email_password;
		}
		public String getEmail_host() {
			return email_host;
		}
		public void setEmail_host(String email_host) {
			this.email_host = email_host;
		}
		public String getProtocol() {
			return protocol;
		}
		public void setProtocol(String protocol) {
			this.protocol = protocol;
		}
		public String getEmail_uuid() {
			return email_uuid;
		}
		public void setEmail_uuid(String email_uuid) {
			this.email_uuid = email_uuid;
		}
		@Override
		public String toString() {
			return "SendEmail [email_uuid=" + email_uuid + ", email_name=" + email_name + ", email_code=" + email_code
					+ ", email_password=" + email_password + ", email_host=" + email_host + ", protocol=" + protocol
					+ "]";
		}
		
		
}
