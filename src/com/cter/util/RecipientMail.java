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
 * ��ȡ�����ʼ� �� 
 * ����date:2019-02-20
 * @author op1768
 *
 */
public class RecipientMail {
    //�ռ��˵�ַ
    public static String recipientAddress = "maibaobao1995@163.com";
    //�ռ����˻���
    public static String recipientAccount = "maibaobao1995@163.com";
    //�ռ����˻�����
    public static String recipientPassword = "163mail19951995";
     
    
    
    public static void main(String[] args) throws Exception {
    	
        //1�������ʼ��������Ĳ�������
        Properties props = new Properties();
        //���ô���Э��
        props.setProperty("mail.store.protocol", "pop3");
        //�����ռ��˵�POP3������
        props.setProperty("mail.pop3.host", "pop3.163.com");
        //2��������������Ӧ�ó�������Ļ�����Ϣ�� Session ����
        Session session = Session.getInstance(props);
        //���õ�����Ϣ�ڿ���̨��ӡ����
//        session.setDebug(true);
         
        Store store = session.getStore("pop3");
        //�����ռ���POP3������
        store.connect("pop3.163.com", recipientAccount, recipientPassword);
        //����û����ʼ��˻���ע��ͨ��pop3Э���ȡĳ���ʼ��е�����ֻ��Ϊinbox
        Folder folder = store.getFolder("inbox");
        //���ö��ʼ��˻��ķ���Ȩ��
        folder.open(Folder.READ_WRITE);
         
        //�õ��ʼ��˻��������ʼ���Ϣ
        Message [] ms = folder.getMessages();
        for(int i = 0 ; i < ms.length ; i++){
        	System.out.println("--------------Message"+(i+1)+"---------------");  
            String from =InternetAddress.toString(ms[i].getFrom());  
            if(from!=null){  
                System.out.println("��Ϣ���ԣ�"+from);  
            }  
            String to=InternetAddress.toString(ms[i].getRecipients(Message.RecipientType.TO));  
            if(to!=null){  
                System.out.println("��Ϣȥ����"+to);  
            }  
            String replyTo=InternetAddress.toString(ms[i].getReplyTo());  
            if(replyTo!=null){  
                System.out.println("��Ϣ�ظ�����"+replyTo);  
            }  
            String cc=InternetAddress.toString(ms[i].getRecipients(Message.RecipientType.CC));  
            if(cc!=null){  
                System.out.println("��Ϣ���ͣ�"+cc);  
            }  
            Date sent=ms[i].getSentDate();  
            if(sent!=null){  
                System.out.println("��Ϣ����ʱ�䣺��"+sent);  
            }  
            String subject=ms[i].getSubject();  
            if(subject!=null){  
                System.out.println("��Ϣ���⣺"+subject);  
            }  
            Date received=ms[i].getReceivedDate();  
            if(received!=null){  
                System.out.println("��Ϣ����ʱ�䣺"+received);  
            }  
            System.out.println("��Ϣ���ݣ�");  
            String content = getMailContent((Part)ms[i]);//��ȡ����
            System.out.println("������Ϣ���ݣ�"+content);  


        }
         
        //�ر��ʼ��ж���
        folder.close(false);
        //�ر����Ӷ���
        store.close();
    }
    /**
     * ��ȡ�ʼ�����
     * @param part��Part
     */
	public static String getMailContent(Part part) throws Exception {    
		StringBuffer bodytext = new StringBuffer();//����ʼ�����
		//�ж��ʼ�����,��ͬ���Ͳ�����ͬ
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