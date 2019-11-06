package com.cter.mail;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.apache.log4j.Logger;

public class SendMail {
    private static Logger log = Logger.getLogger(SendMail.class);
    private static SendMail instance = null;

    private SendMail() {

    }

    public static SendMail getInstance() {
        if (instance == null) {
            instance = new SendMail();
        }
        return instance;
    }

    /**
     * @param to 发送人数组
     * @param cs 抄送人数组
     * @param ms  密送人数组
     * @param subject 主题
     * @param content  发送的内容
     * @param formEmail 发件人 maibaobao1995@163.com
     * @param fileList  附件List
     */
    public void send(String to[], String cs[], String ms[], String subject,
            String content, String formEmail, String fileList[])throws Exception {
       
            Properties p = new Properties();  
            p.put("mail.smtp.auth", "true");
            p.put("mail.transport.protocol", "smtp");
//            p.put("mail.smtp.host", "smtp.163.com");//邮件服务器的地址163
            p.put("mail.smtp.host", "smtp.china.com");
            p.put("mail.smtp.port", "25");
            p.put("mail.debug", "true");
            // 建立会话
            Session session = Session.getInstance(p);
            Message msg = new MimeMessage(session); // 建立信息
            BodyPart messageBodyPart = new MimeBodyPart();
            Multipart multipart = new MimeMultipart();
            msg.setFrom(new InternetAddress(formEmail)); // 发件人

            String toList = null;
            String toListcs = null;
            String toListms = null;

            // 发送,
            if (to != null) {
                toList = getMailList(to);
                new InternetAddress();
                InternetAddress[] iaToList = InternetAddress
                        .parse(toList);
                msg.setRecipients(Message.RecipientType.TO, iaToList); // 收件人
            }

            // 抄送
            if (cs != null) {
                toListcs = getMailList(cs);
                new InternetAddress();
                InternetAddress[] iaToListcs = InternetAddress
                        .parse(toListcs);
                msg.setRecipients(Message.RecipientType.CC, iaToListcs); // 抄送人
            }

            // 密送
            if (ms != null) {
                toListms = getMailList(ms);
                new InternetAddress();
                InternetAddress[] iaToListms = InternetAddress
                        .parse(toListms);
                msg.setRecipients(Message.RecipientType.BCC, iaToListms); // 密送人
            }
            msg.setSentDate(new Date()); // 发送日期
            msg.setSubject(subject); // 主题
            msg.setText(content); // 内容
            // 显示以html格式的文本内容
            messageBodyPart.setContent(content, "text/html;charset=utf-8");
            multipart.addBodyPart(messageBodyPart);

            // 2.保存多个附件
            if (fileList != null) {
                addTach(fileList, multipart);
            }

            msg.setContent(multipart);
            // 邮件服务器进行验证
            Transport tran = session.getTransport("smtp");
         tran.connect("smtp.163.com", //邮件服务器地址 
            "maibaobao1995@163.com",//邮箱地址
                    "163mail19951995");//邮箱的密码

            tran.sendMessage(msg, msg.getAllRecipients()); // 发送
            log.info("邮件发送成功");
            log.info("发送标题："+msg.getSubject());
            log.info("发送内容："+content);
         
//            log.info("邮件发送时异常",e);
         
    }

    // 添加多个附件
    public void addTach(String fileList[], Multipart multipart)
            throws MessagingException, UnsupportedEncodingException {
        for (int index = 0; index < fileList.length; index++) {
            MimeBodyPart mailArchieve = new MimeBodyPart();
            FileDataSource fds = new FileDataSource(fileList[index]);
            mailArchieve.setDataHandler(new DataHandler(fds));
            mailArchieve.setFileName(MimeUtility.encodeText(fds.getName(),
                    "utf-8", "B"));
            multipart.addBodyPart(mailArchieve);
        }
    }

    private String getMailList(String[] mailArray) {

        StringBuffer toList = new StringBuffer();
        int length = mailArray.length;
        if (mailArray != null && length < 2) {
            toList.append(mailArray[0]);
        } else {
            for (int i = 0; i < length; i++) {
                toList.append(mailArray[i]);
                if (i != (length - 1)) {
                    toList.append(",");
                }

            }
        }
        return toList.toString();

    }
   
    /**
     * 配置主题和内容发送邮件
     *  独立方法，自行测试用的
     * @param subject 
     * @param content
     */
    public static void forSend(String subject,String content)throws Exception{
        SendMail send = SendMail.getInstance();
        String to[] = { "944711140@qq.cn"};//收件人的地址
        String cs[] = null;
        String ms[] = null;
        if(content==null||content.length()==0){
            content = "这是邮件内容，仅仅是测试，不需要回复";
        }
        String fromEmail = "maibaobao1995@163.cn";//发件人的地址
        String[] arrArchievList = null;

        // 2.保存多个附件
        send.send(to, cs, ms, subject, content, fromEmail, arrArchievList);
    }



	
	/**
   * @param to 发送人数组
     * @param cs 抄送人数组
     * @param ms  密送人数组
     * @param subject 主题
     * @param content  发送的内容
     * @param formEmail 发件人 maibaobao1995@163.com
     * @param fileList  附件List
	 */
	public void send(String to[], String subject,
            String content, String formEmail)throws Exception{
		this.send(to, null, null, subject, content, formEmail, null);
	}
	
	public static void main(String[] args)throws Exception{
//		163的发送邮箱
		SendMail mail=new SendMail();
		String  formEmail="maibaobao1995@163.com";
		String[] to={"luke.zou@china-entercom.net"};
		String subject ="我测试一下";
		String content ="测试内容";
		mail.send(to, null, null, subject, content, formEmail, null);
	}
  

}
