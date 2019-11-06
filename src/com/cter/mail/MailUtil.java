package com.cter.mail;
//package com.test;
//
//import com.gzdec.common.config.AppConfig;
//import com.gzdec.common.config.AppConfigException;
//import com.gzdec.common.mail.MailSender;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Properties;
//
//import javax.mail.Address;
//import javax.mail.Message;
//import javax.mail.Multipart;
//import javax.mail.Session;
//import javax.mail.Transport;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeBodyPart;
//import javax.mail.internet.MimeMessage;
//import javax.mail.internet.MimeMultipart;
//
///**
// * @author lianghuahuang
// * �ʼ�������
// * Feb 12, 2009 4:34:56 PM
// */
//public class MailUtil {
//	public String getRegisterTemplate(String realName,String userName,String password,String url){
//		StringBuffer content = new StringBuffer();
//		content.append("�װ���").append(realName).append(":");
//		content.append("<p>").append("��ϲ���ѳɹ�ע��ɱ�վ��Ա").append("</p>");
//		content.append("<p>�����û���Ϊ:").append(userName).append("</p>");
//		content.append("<p>��������Ϊ��").append(password).append("</p>");
//		content.append("<p>��������ӵ�¼<a href='").append(url).append("'>").append(url).append("</a></p>");
//		content.append("<br/><br/><div align=right>����Զ�̽�������</div>");
//		return content.toString();
//	}
//	
//	public void setDefaultParam(MailSender ms){
//		try {
//			ms.setSMTPServer(AppConfig.getProperty("host"));
//			ms.setUser(AppConfig.getProperty("user"));              
//			ms.setPassword(AppConfig.getProperty("password"));                    
//			ms.setFrom(AppConfig.getProperty("from"));
//		} catch (AppConfigException e) {
//		} 
//		
//	}
//	
//	//�����ʼ���ÿ���ͻ�
//	public  static  boolean sentEmail(Map<String,String> forMap) throws AppConfigException {
//		   Properties props = new Properties();
//
//		   //��ʼ��
//		   props.put("mail.smtp.host", AppConfig.getProperty("host")); 
//		   props.put("mail.smtp.auth", "true"); //����smtpУ��   
//		   props.put("mail.transport.protocol", AppConfig.getProperty("protocal"));
//		   Session sendMailSession = Session.getInstance(props,null);
//		   sendMailSession.setDebug(true);
//
//		   
//		   try {
//		    Transport transport = sendMailSession.getTransport("smtp");
//		    transport.connect(AppConfig.getProperty("host"), AppConfig.getProperty("user"), AppConfig.getProperty("password"));
//
//		    Message newMessage = new MimeMessage(sendMailSession);		    
//		    String mail_subject=forMap.get("subject");
//		    newMessage.setSubject(mail_subject);
//
//		    
//		    //���÷����˵�ַ   
//		    String strFrom =  AppConfig.getProperty("from");
//		    strFrom = new String(strFrom.getBytes(), "8859_1");
//		    newMessage.setFrom(new InternetAddress (javax.mail.internet.MimeUtility.encodeText("4000096930�ͻ���������")+"<"+strFrom+">"));
//		 
//		    //�ռ�������
//		    Address addressTo[] = { new InternetAddress(forMap.get("reviceAddress")) };
//		    newMessage.setRecipients(Message.RecipientType.TO,addressTo);
//		  
//		    //����mail����   
//		    newMessage.setSentDate(new java.util.Date());//���÷�������
//		    String mail_text = forMap.get("content");
//		  
//		    
//		    MimeBodyPart part = new MimeBodyPart();//mail���ݲ���
//		    part.setText(mail_text == null ? "" : mail_text, "GBK");
//		    //�����ʼ���ʽΪhtml cqc
//		    part.setContent(mail_text.toString(),"text/html;charset=GBK");
//		    Multipart multipart = new MimeMultipart();
//		    multipart.addBodyPart(part);//�� Multipart ������mail���ݲ���
//		    newMessage.setContent(multipart);//���� Multipart ����Ϣ��
//
//		    newMessage.saveChanges(); //���淢����Ϣ   
//		    transport.sendMessage(newMessage, newMessage.getRecipients(Message.RecipientType.TO)); //�����ʼ�   
//		    transport.close();
//		    return true;
//		   } catch(Exception e) {
//			   return false;
//		   }
//		   
//		}
//	
//	
//	public static void main(String[] args){
//		Map<String,String>  email  = new HashMap<String,String>();
//		email.put("reviceAddress", "fangli@gzedu.net");   //�ռ�����
//		email.put("content", "�����ʼ�����-content");       //����
//		email.put("subject", "�����ʼ�����-subject");       //����
//		try {
//			sentEmail(email);
//		} catch (AppConfigException e) {
//		}
//		
//	}
//	
//	
//	
//}
