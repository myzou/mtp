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

/**
 * 测试中企通信邮箱
 * @author Administrator
 *
 */
public class SendMail3 {
    private static Logger logger = Logger.getLogger(SendMail3.class);
    private static SendMail3 instance = null;

    private SendMail3() {

    }

    public static SendMail3 getInstance() {
        if (instance == null) {
            instance = new SendMail3();
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
            String content, String formEmail, String fileList[]) {
        try {
            Properties p = new Properties(); // Properties p =
            // System.getProperties();
            p.put("mail.smtp.auth", "true");
            p.put("mail.transport.protocol", "smtp");
//            p.put("mail.smtp.host", "smtp.163.com");//邮件服务器的地址163
//            http://www.zimbra.com/
            p.put("mail.smtp.host", "smtp.zimbra.com");
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
            System.out.println("邮件发送成功");

        } catch (Exception e) {
            logger.info("邮件发送时异常",e);
        }
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
    public static void forSend(String subject,String content){
        SendMail3 send = SendMail3.getInstance();
        String to[] = { "944711140@qq.com"};//收件人的地址
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
            String content, String formEmail){
		this.send(to, null, null, subject, content, formEmail, null);
	}
	
	/*public static void main(String[] args) {
//		163的发送邮箱
		SendMail2 mail=new SendMail2();
		String  formEmail="maibaobao1995@163.com";
		String[] to={"luke.zou@china-entercom.net"};
		String subject ="我测试一下";
		String content ="测试内容";
		mail.send(to, null, null, subject, content, formEmail, null);
	}*/
	
	public static void main(String[] args) { 
		SendMail3 mail=new SendMail3();
		String  formEmail="maibaobao1995@163.com";
		String[] to={"luke.zou@china-entercom.net","944711140@qq.com"};
		String subject ="10/18中企网络申请授权进入上海泰富机房做线路维护#2432952";
		String content ="Dear 中信，<br>" + 
				"<br>" + 
				"  <br>" + 
				"<br>" + 
				" &emsp;烦请同意我司工程师进入机房做线路维护并测试，谢谢。<br>" + 
				"<br>" + 
				"时间：2018-10-17 13:00-2018-10-17&emsp;20:00<br>" + 
				"<br>" + 
				" <br>" + 
				"<br>" + 
				"人员：<br>" + 
				"<br>" + 
				"费天涯&nbsp;身份证320684198906154673<br>" + 
				"<br>" + 
				" <br>" + 
				"<br>" + 
				"操作：在交换机挂jd测试并做线路维护，测试线路<br>" + 
				"<br>" + 
				"机柜：中企所有机柜<br>" + 
				"<br>" + 
				" <br>" + 
				"<br>" + 
				" <br>" + 
				"<br>" + 
				"认证口令：KpCh4864<br>" + 
				"<br>" + 
				" <br>" + 
				"<br>" + 
				"--<br>" + 
				"<br>" + 
				"顺祝商祺,<br>" + 
				"<br>" + 
				"葛立波(Jicen Ge)<br>" + 
				"<br>" + 
				"网络工程师<br>" + 
				"<br>" + 
				"网络运维部<br>" + 
				"<br>" + 
				"中企网络通信技术有限公司<br>" + 
				"<br>" + 
				"广州市天河区科韵路12号之一方圆E时光西座2706－2708房&nbsp;510665<br>" + 
				"<br>" + 
				"电话： <callto:(86)20-8518%201311> (86)20-8518 1311&nbsp;手机：(86)18825106779<br>" + 
				"<br>" + 
				"邮箱：jicen <mailto:cijor.chen@china-entercom.net> .ge@china-entercom.net<br>" + 
				"网址：www.china-entercom.com <http://www.china-entercom.com/> <br>" + 
				"<br>" + 
				"<br>" + 
				"<br>" + 
				"邮件免责声明<br>" + 
				"<br>" + 
				"本邮件（及附件）含有中企网络通信技术有限公司的保密信息，仅供指定收件人阅览，<br>" + 
				"收件人必须保密! 如阁下不是指定收件人，发件人在此深表歉意！烦请阁下立即通知发<br>" + 
				"件人，並删除本邮件（及附件）。未经本公司书面授权或同意，严禁使用、公开、复<br>" + 
				"制、打印、转发及分发本邮件（及附件）任何部分之内容,否则阁下需承担相应的法律<br>" + 
				"责任。由于网络通讯的特殊性，本邮件（及附件）内的信息可能会被截载、篡改、遗<br>" + 
				"失、损坏、不完整、延迟、以及含有病毒，对于传送该电子邮件的内容（及附件）而引<br>" + 
				"致的错误及遗漏或其他后果，本公司概不负责。<br>" + 
				"";
		
		
		mail.send(to, null, null, subject, content, formEmail, null);
	}
    
}
