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
// * 邮件处理类
// * Feb 12, 2009 4:34:56 PM
// */
//public class MailUtil {
//	public String getRegisterTemplate(String realName,String userName,String password,String url){
//		StringBuffer content = new StringBuffer();
//		content.append("亲爱的").append(realName).append(":");
//		content.append("<p>").append("恭喜您已成功注册成本站会员").append("</p>");
//		content.append("<p>您的用户名为:").append(userName).append("</p>");
//		content.append("<p>您的密码为：").append(password).append("</p>");
//		content.append("<p>点击该链接登录<a href='").append(url).append("'>").append(url).append("</a></p>");
//		content.append("<br/><br/><div align=right>广州远程教育中心</div>");
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
//	//发送邮件给每个客户
//	public  static  boolean sentEmail(Map<String,String> forMap) throws AppConfigException {
//		   Properties props = new Properties();
//
//		   //初始化
//		   props.put("mail.smtp.host", AppConfig.getProperty("host")); 
//		   props.put("mail.smtp.auth", "true"); //允许smtp校验   
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
//		    //设置发信人地址   
//		    String strFrom =  AppConfig.getProperty("from");
//		    strFrom = new String(strFrom.getBytes(), "8859_1");
//		    newMessage.setFrom(new InternetAddress (javax.mail.internet.MimeUtility.encodeText("4000096930客户服务邮箱")+"<"+strFrom+">"));
//		 
//		    //收件人邮箱
//		    Address addressTo[] = { new InternetAddress(forMap.get("reviceAddress")) };
//		    newMessage.setRecipients(Message.RecipientType.TO,addressTo);
//		  
//		    //设置mail正文   
//		    newMessage.setSentDate(new java.util.Date());//设置发送日期
//		    String mail_text = forMap.get("content");
//		  
//		    
//		    MimeBodyPart part = new MimeBodyPart();//mail内容部分
//		    part.setText(mail_text == null ? "" : mail_text, "GBK");
//		    //设置邮件格式为html cqc
//		    part.setContent(mail_text.toString(),"text/html;charset=GBK");
//		    Multipart multipart = new MimeMultipart();
//		    multipart.addBodyPart(part);//在 Multipart 中增加mail内容部分
//		    newMessage.setContent(multipart);//增加 Multipart 到信息体
//
//		    newMessage.saveChanges(); //保存发送信息   
//		    transport.sendMessage(newMessage, newMessage.getRecipients(Message.RecipientType.TO)); //发送邮件   
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
//		email.put("reviceAddress", "fangli@gzedu.net");   //收件邮箱
//		email.put("content", "测试邮件用例-content");       //内容
//		email.put("subject", "测试邮件用例-subject");       //标题
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
