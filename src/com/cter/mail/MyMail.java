package com.cter.mail;

import java.util.Properties;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MyMail {

	public static void main(String[] args) throws Exception {
		Properties props = new Properties();
		// ����debug����
		props.setProperty("mail.debug", "true");
		// ���ͷ�������Ҫ�����֤
		props.setProperty("mail.smtp.auth", "true");
		// �����ʼ�������������
		props.setProperty("mail.host", "smtp.163.com");
		// �����ʼ�Э������
		props.setProperty("mail.transport.protocol", "smtp");

		// ���û�����Ϣ
		Session session = Session.getInstance(props);

		// �����ʼ�����
		Message msg = new MimeMessage(session);
		msg.setSubject("��ӱ���㻹�ǰ��ҵ���");
		// �����ʼ�����
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<5;i++){
			sb.append("����һ�� ���е����ݣ�\t\n���Ѿ�������\n");
		}
		msg.setText(sb.toString());
		// ���÷�����
		msg.setFrom(new InternetAddress("maibaobao1995@163.com"));

		Transport transport = session.getTransport();
		// �����ʼ�������
		transport.connect("maibaobao1995", "163mail19951995");
		// �����ʼ�
		transport.sendMessage(msg, new Address[] { new InternetAddress("944711140@qq.com") });
		// �ر�����
		transport.close();

	}

}
