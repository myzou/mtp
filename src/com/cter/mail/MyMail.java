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
		// 开启debug调试
		props.setProperty("mail.debug", "true");
		// 发送服务器需要身份验证
		props.setProperty("mail.smtp.auth", "true");
		// 设置邮件服务器主机名
		props.setProperty("mail.host", "smtp.163.com");
		// 发送邮件协议名称
		props.setProperty("mail.transport.protocol", "smtp");

		// 设置环境信息
		Session session = Session.getInstance(props);

		// 创建邮件对象
		Message msg = new MimeMessage(session);
		msg.setSubject("进颖哥你还是爱我的吗");
		// 设置邮件内容
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<5;i++){
			sb.append("测试一下 换行的内容，\t\n我已经换行了\n");
		}
		msg.setText(sb.toString());
		// 设置发件人
		msg.setFrom(new InternetAddress("maibaobao1995@163.com"));

		Transport transport = session.getTransport();
		// 连接邮件服务器
		transport.connect("maibaobao1995", "163mail19951995");
		// 发送邮件
		transport.sendMessage(msg, new Address[] { new InternetAddress("944711140@qq.com") });
		// 关闭连接
		transport.close();

	}

}
