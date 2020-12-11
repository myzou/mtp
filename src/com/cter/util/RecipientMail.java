package com.cter.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;

/**
 * 收取邮箱邮件 类 
 * 测试date:2019-02-20
 * @author op1768
 *
 */
public class RecipientMail {
    //收件人地址
    public static String recipientAddress = "maibaobao1995@163.com";
    //收件人账户名
    public static String recipientAccount = "maibaobao1995@163.com";
    //收件人账户密码
    public static String recipientPassword = "163mail19951995";
     
    
    
    public static void main(String[] args) throws Exception {
    	
        //1、连接邮件服务器的参数配置
        Properties props = new Properties();
        //设置传输协议
        props.setProperty("mail.store.protocol", "pop3");
        //设置收件人的POP3服务器
        props.setProperty("mail.pop3.host", "pop3.163.com");
        //2、创建定义整个应用程序所需的环境信息的 Session 对象
        Session session = Session.getInstance(props);
        //设置调试信息在控制台打印出来
//        session.setDebug(true);
         
        Store store = session.getStore("pop3");
        //连接收件人POP3服务器
        store.connect("pop3.163.com", recipientAccount, recipientPassword);
        //获得用户的邮件账户，注意通过pop3协议获取某个邮件夹的名称只能为inbox
        Folder folder = store.getFolder("inbox");
        //设置对邮件账户的访问权限
        folder.open(Folder.READ_WRITE);
         
        //得到邮件账户的所有邮件信息
        Message [] ms = folder.getMessages();
        for(int i = 0 ; i < ms.length ; i++){
        	System.out.println("--------------Message"+(i+1)+"---------------");  
            String from =InternetAddress.toString(ms[i].getFrom());  
            if(from!=null){  
                System.out.println("消息来自："+from);  
            }  
            String to=InternetAddress.toString(ms[i].getRecipients(Message.RecipientType.TO));  
            if(to!=null){  
                System.out.println("消息去往："+to);  
            }  
            String replyTo=InternetAddress.toString(ms[i].getReplyTo());  
            if(replyTo!=null){  
                System.out.println("消息回复给："+replyTo);  
            }  
            String cc=InternetAddress.toString(ms[i].getRecipients(Message.RecipientType.CC));  
            if(cc!=null){  
                System.out.println("消息抄送："+cc);  
            }  
            Date sent=ms[i].getSentDate();  
            if(sent!=null){  
                System.out.println("消息发送时间：："+sent);  
            }  
            String subject=ms[i].getSubject();  
            if(subject!=null){  
                System.out.println("消息主题："+subject);  
            }  
            Date received=ms[i].getReceivedDate();  
            if(received!=null){  
                System.out.println("消息接收时间："+received);  
            }  
            System.out.println("消息内容：");  
            String content = getMailContent((Part)ms[i]);//获取内容
            System.out.println("接收消息内容："+content);  


        }
         
        //关闭邮件夹对象
        folder.close(false);
        //关闭连接对象
        store.close();
    }
    /**
     * 获取邮件内容
     * @param part：Part
     */
	public static String getMailContent(Part part) throws Exception {    
		StringBuffer bodytext = new StringBuffer();//存放邮件内容
		//判断邮件类型,不同类型操作不同
		if (part.isMimeType("text/plain")) {    
            bodytext.append((String) part.getContent());    
        } else if (part.isMimeType("text/html")) {    
            bodytext.append((String) part.getContent());    
        } else if (part.isMimeType("multipart/*")) {    
            Multipart multipart = (Multipart) part.getContent();    
            int counts = multipart.getCount();    
            for (int i = 0; i < counts; i++) {    
                getMailContent(multipart.getBodyPart(i));    
            }    
        } else if (part.isMimeType("message/rfc822")) {    
            getMailContent((Part) part.getContent());    
        } else {}    
        return bodytext.toString();

	}
    
}