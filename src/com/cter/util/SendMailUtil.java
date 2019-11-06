package com.cter.util;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
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
 * 邮件发送工具类
 * @author Administrator
 *Date:2018-11-11
 */
public class SendMailUtil {
	
	private static Map<String, String >  map=LoadPropertiestUtil.loadProperties("config/idcEmail.properties");
	private static final String PROTOCOL=map.get("protocol");
	private static final String DEBUG=map.get("debug");
	private static final String myselfSendHost=map.get("myselfSendHost");
	private static final String myselfEmailPassword=map.get("myselfEmailPassword");
	private static final String myselfFormEmail=map.get("myselfFormEmail");
	private static final String dcSendHost=map.get("dcSendHost");
	private static final String dcFormEmail=map.get("dcFormEmail");
	private static final String dcEmailPassword=map.get("dcEmailPassword");
	private BaseLog log=new BaseLog("EmailLog");
    private static SendMailUtil instance =getInstance();

    private SendMailUtil() {

    }

    public static SendMailUtil getInstance() {
        if (instance == null) {
            instance = new SendMailUtil();
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
     * @param host 服务器地址
      * @param password 服务器密码
     */
    public    void send(String to[], String cs[], String ms[], String subject, String content, String formEmail, String fileList[],String host,String password)throws Exception {
        	log.info("\nto 发送人数组:"+Arrays.toString(to));
        	log.info("\ncs 抄送人数组:"+Arrays.toString(cs));
        	log.info("\nsubject 主题:"+subject+"\t发件人:"+formEmail+"\t服务器地址："+host+"\t服务器密码："+password);
        	log.info("content 发送的内容:"+content);
        	log.info(" fileList  附件List:"+Arrays.toString(fileList));
            Properties p = new Properties(); // Properties p =
            // System.getProperties();
            p.put("mail.smtp.auth", "true");
            p.put("mail.transport.protocol",  PROTOCOL);
//            p.put("mail.smtp.host", "smtp.163.com");//邮件服务器的地址163
            p.put("mail.smtp.host", host );
            p.put("mail.smtp.port", "25");
            p.put("mail.debug", DEBUG);
            // 建立会话
            Session session = Session.getInstance(p);
            Message msg = new MimeMessage(session); // 建立信息
            BodyPart messageBodyPart = new MimeBodyPart();//文本节点
            Multipart multipart = new MimeMultipart();//内容节点
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
                if(!toListcs.equals("") ){
                	 new InternetAddress();
                     InternetAddress[] iaToListcs = InternetAddress
                             .parse(toListcs);
                     msg.setRecipients(Message.RecipientType.CC, iaToListcs); // 抄送人
                }
               
            }

            // 密送
            if (ms != null) {
                if(!toListcs.equals("") ){
                	toListms = getMailList(ms);
                    new InternetAddress();
                    InternetAddress[] iaToListms = InternetAddress
                            .parse(toListms);
                    msg.addRecipients(Message.RecipientType.BCC, iaToListms);
                }
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
            tran.connect(host, //邮件服务器地址 
           formEmail,//邮箱地址
                   password);//邮箱的密码
            
            tran.sendMessage(msg, msg.getAllRecipients()); // 发送
			log.info("邮件发送成功");
			log.info("邮件发送人信息："+"账号:"+"密码"+"");
			log.info("邮件收件人："+CommonUtil.toString(to));
			log.info("邮件抄送件人："+CommonUtil.toString(cs));
			log.info("邮件包含附件地址："+CommonUtil.toString(fileList));
			log.info("邮件主题："+subject);
			log.info("邮件内容："+content);

    }
    
    public    void send( MimeMultipart  multipart  ,String to[], String cs[], String ms[], String subject, String content, String formEmail, String fileList[],String host,String password) {
        try {
            Properties p = new Properties(); // Properties p =
            // System.getProperties();
            p.put("mail.smtp.auth", "true");
            p.put("mail.transport.protocol",  PROTOCOL);
//            p.put("mail.smtp.host", "smtp.163.com");//邮件服务器的地址163
            p.put("mail.smtp.host", host );
            p.put("mail.smtp.port", "25");
            p.put("mail.debug", DEBUG);
            // 建立会话
            Session session = Session.getInstance(p);
            Message msg = new MimeMessage(session); // 建立信息
            BodyPart messageBodyPart = new MimeBodyPart();//文本节点
            msg.setFrom(new InternetAddress(formEmail)); // 发件人

            String toList = null;
            String toListcs = null;
            String toListms = null;

            // 发送,
            if (to != null) {
                toList = getMailList(to);
                if(!toList.equals("")){
                new InternetAddress();
                InternetAddress[] iaToList = InternetAddress.parse(toList);
                msg.setRecipients(Message.RecipientType.TO, iaToList); // 收件人
                }
            }

            // 抄送
            if (cs != null) {
                toListcs = getMailList(cs);
                if(!toListcs.equals("")){
                new InternetAddress();
                InternetAddress[] iaToListcs = InternetAddress.parse(toListcs);
                msg.setRecipients(Message.RecipientType.CC, iaToListcs); // 抄送人
                }
            }

            // 密送
            if (ms != null) {
                toListms = getMailList(ms);
                if(!toListms.equals("")){
                	  new InternetAddress();
                      InternetAddress[] iaToListms = InternetAddress .parse(toListms);
                      msg.addRecipients(Message.RecipientType.BCC, iaToListms);
                }
            }
            msg.setSentDate(new Date()); // 发送日期
            msg.setSubject(subject); // 主题
//            msg.setText(content); // 内容
            // 显示以html格式的文本内容
            MimeBodyPart image = new MimeBodyPart();
            MimeBodyPart text = new MimeBodyPart();
   			text.setContent("related这是一张图片<br/><a href='#'><img src='cid:imagetupian'/></a>", "text/html;charset=utf-8");
	        DataHandler dh = new DataHandler(new FileDataSource("D://op1768//workSpace//java1234//mars45//X-admin-2.3//src//image001.jpg"));
	        image.setDataHandler(dh);
	        image.setContentID("imagetupian");    
            multipart.addBodyPart(image);
            multipart.addBodyPart(text);
            multipart.setSubType("related");
            
            
            // 2.保存多个附件
            if (fileList != null) {
                addTach(fileList, multipart);
            }

            msg.setContent(multipart);
            msg.saveChanges();
            // 邮件服务器进行验证 
            Transport tran = session.getTransport("smtp");
            tran.connect(host, //邮件服务器地址 
           formEmail,//邮箱地址
                   password);//邮箱的密码
            
            tran.sendMessage(msg, msg.getAllRecipients()); // 发送
            System.out.println("邮件发送成功");
            to.toString();
        } catch (Exception e) {
            System.out.println("邮件发送时异常");
            e.printStackTrace();
        }
    }
    
    

   

    // 添加多个附件
    public void addTach(String fileList[], Multipart multipart)
            throws MessagingException, UnsupportedEncodingException {
        for (int index = 0; index < fileList.length; index++) {
            MimeBodyPart mailArchieve = new MimeBodyPart();
            java.io.File file=new java.io.File(fileList[index]);
            if(file.exists()){//附件如果存在的话 添加到邮箱发送信息里面
                FileDataSource fds = new FileDataSource(fileList[index]);
                mailArchieve.setDataHandler(new DataHandler(fds));
                mailArchieve.setFileName(MimeUtility.encodeText(fds.getName(),
                        "utf-8", "B"));
                multipart.addBodyPart(mailArchieve);
            }
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
    public static void forSendMy(String subject,String content )throws Exception{
   
        SendMailUtil send = SendMailUtil.getInstance();
        String to[] = {"luke.zou@china-entercom.net","944711140@qq.com"};//收件人的地址
        String cs[] = null;
        String ms[] = null;
        if(content==null||content.length()==0){
            content = "这是邮件内容，仅仅是测试，不需要回复";
        }
        String[] arrArchievList = null;

        // 2.保存多个附件
        send.send(to, cs, ms, subject, content, myselfFormEmail, arrArchievList,myselfSendHost,myselfEmailPassword);
    }

    
	/**
	 * 使用op1768 的163邮箱 发送
	 * @param to 收件人集合
	 * @param cs 抄送人集合
	 * @param subject  主题
	 * @param content 内容
	 */
    public static void SendMe(String to[],String cs[],String subject,String content)throws Exception{
        instance.send(to, cs, null, subject, content, myselfFormEmail, null,myselfSendHost,myselfEmailPassword);
    }
    

	
	
	/**
	 * 使用DC access 发送
	 * @param to 收件人集合
	 * @param cs 抄送人集合
	 * @param subject  主题
	 * @param content 内容
	 */
	public void sendZQ(String to[],String[] cs, String subject,  String content )throws Exception{
		instance.send(to, cs, null, subject, content, dcFormEmail, null,dcSendHost,dcEmailPassword);
	}
	/**
	 * 使用DC access 有密送
	 * @param to 收件人集合
	 * @param cs 抄送人集合
	  * @param ms 密送集合
	 * @param subject  主题
	 * @param content 内容
	 */
	public void sendZQ(String to[],String[] cs,String[] ms, String subject,  String content )throws Exception{
		instance.send(to, cs, ms, subject, content, dcFormEmail, null,dcSendHost,dcEmailPassword);
	}
	
	
	
	public static void main(String[] args)throws Exception {
		SendMailUtil mail=new SendMailUtil();
		String  formEmail="dcaccess@china-entercom.net";
		String str ="D:\\op1768\\workSpace\\java1234\\mars45\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp1\\wtpwebapps\\X-admin-2.3\\upload\\20181031\\unknown###D:\\op1768\\workSpace\\java1234\\mars45\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp1\\wtpwebapps\\X-admin-2.3\\upload\\20181031\\授权日志10.29-T2.xlsx###D:\\op1768\\workSpace\\java1234\\mars45\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp1\\wtpwebapps\\X-admin-2.3\\upload\\20181031\\授权日志10.29-D3.xlsx";
		
		String[]   fileList= str.trim().split("###");
		/*String[] fileList =new String[5];
		fileList[0]="D:\\op1768\\TIM\\file\\944711140\\10-30宝山机房设备迁入#2442979.doc";
		fileList[1]="D:\\op1768\\TIM\\file\\944711140\\image001.jpg";
		fileList[2]="D:\\op1768\\TIM\\file\\944711140\\unknown";
		fileList[3]="D:\\op1768\\TIM\\file\\944711140\\授权日志10.29-D3.xlsx";
		fileList[4]="D:\\op1768\\TIM\\file\\944711140\\授权日志10.29-T2.xlsx";*/
		String[] to="fanyang@china-entercom.net;ethan.li@china-entercom.net;gnoc-bj@china-entercom.net".split(";");
		String[] cs="kehufuwubu@ceicloud.com;zhaohongya@ceicloud.com;oupeng@ceicloud.com".split(";");
/*		for(String t:to){
			System.out.println(t);
		}
		for(String c:cs){
			System.out.println(c);
		}*/
//		String[] to={"dcaccess@china-entercom.net","idc@midea.com","944711140@qq.com"};
		String subject ="1";
		
		String content =" "  ;
		
		 instance.send(to, cs,null , subject, content, myselfFormEmail, null,myselfSendHost,myselfEmailPassword);//自己测试用的连接
//		 instance.send(to, null,null , subject, content, dcFormEmail, null,dcSendHost,dcEmailPassword);//自己测试用的连接
		 

	}
    
}
