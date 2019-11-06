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
 * �ʼ����͹�����
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
     * @param to ����������
     * @param cs ����������
     * @param ms  ����������
     * @param subject ����
     * @param content  ���͵�����
     * @param formEmail ������ maibaobao1995@163.com
     * @param fileList  ����List
     * @param host ��������ַ
      * @param password ����������
     */
    public    void send(String to[], String cs[], String ms[], String subject, String content, String formEmail, String fileList[],String host,String password)throws Exception {
        	log.info("\nto ����������:"+Arrays.toString(to));
        	log.info("\ncs ����������:"+Arrays.toString(cs));
        	log.info("\nsubject ����:"+subject+"\t������:"+formEmail+"\t��������ַ��"+host+"\t���������룺"+password);
        	log.info("content ���͵�����:"+content);
        	log.info(" fileList  ����List:"+Arrays.toString(fileList));
            Properties p = new Properties(); // Properties p =
            // System.getProperties();
            p.put("mail.smtp.auth", "true");
            p.put("mail.transport.protocol",  PROTOCOL);
//            p.put("mail.smtp.host", "smtp.163.com");//�ʼ��������ĵ�ַ163
            p.put("mail.smtp.host", host );
            p.put("mail.smtp.port", "25");
            p.put("mail.debug", DEBUG);
            // �����Ự
            Session session = Session.getInstance(p);
            Message msg = new MimeMessage(session); // ������Ϣ
            BodyPart messageBodyPart = new MimeBodyPart();//�ı��ڵ�
            Multipart multipart = new MimeMultipart();//���ݽڵ�
            msg.setFrom(new InternetAddress(formEmail)); // ������

            String toList = null;
            String toListcs = null;
            String toListms = null;

            // ����,
            if (to != null) {
                toList = getMailList(to);
                new InternetAddress();
                InternetAddress[] iaToList = InternetAddress
                        .parse(toList);
                msg.setRecipients(Message.RecipientType.TO, iaToList); // �ռ���
            }

            // ����
            if (cs != null) {
                toListcs = getMailList(cs);
                if(!toListcs.equals("") ){
                	 new InternetAddress();
                     InternetAddress[] iaToListcs = InternetAddress
                             .parse(toListcs);
                     msg.setRecipients(Message.RecipientType.CC, iaToListcs); // ������
                }
               
            }

            // ����
            if (ms != null) {
                if(!toListcs.equals("") ){
                	toListms = getMailList(ms);
                    new InternetAddress();
                    InternetAddress[] iaToListms = InternetAddress
                            .parse(toListms);
                    msg.addRecipients(Message.RecipientType.BCC, iaToListms);
                }
            }
            msg.setSentDate(new Date()); // ��������
            msg.setSubject(subject); // ����
            msg.setText(content); // ����
            // ��ʾ��html��ʽ���ı�����
            messageBodyPart.setContent(content, "text/html;charset=utf-8");
            multipart.addBodyPart(messageBodyPart);

            // 2.����������
            if (fileList != null) {
                addTach(fileList, multipart);
            }

            msg.setContent(multipart);
            // �ʼ�������������֤
            Transport tran = session.getTransport("smtp");
            tran.connect(host, //�ʼ���������ַ 
           formEmail,//�����ַ
                   password);//���������
            
            tran.sendMessage(msg, msg.getAllRecipients()); // ����
			log.info("�ʼ����ͳɹ�");
			log.info("�ʼ���������Ϣ��"+"�˺�:"+"����"+"");
			log.info("�ʼ��ռ��ˣ�"+CommonUtil.toString(to));
			log.info("�ʼ����ͼ��ˣ�"+CommonUtil.toString(cs));
			log.info("�ʼ�����������ַ��"+CommonUtil.toString(fileList));
			log.info("�ʼ����⣺"+subject);
			log.info("�ʼ����ݣ�"+content);

    }
    
    public    void send( MimeMultipart  multipart  ,String to[], String cs[], String ms[], String subject, String content, String formEmail, String fileList[],String host,String password) {
        try {
            Properties p = new Properties(); // Properties p =
            // System.getProperties();
            p.put("mail.smtp.auth", "true");
            p.put("mail.transport.protocol",  PROTOCOL);
//            p.put("mail.smtp.host", "smtp.163.com");//�ʼ��������ĵ�ַ163
            p.put("mail.smtp.host", host );
            p.put("mail.smtp.port", "25");
            p.put("mail.debug", DEBUG);
            // �����Ự
            Session session = Session.getInstance(p);
            Message msg = new MimeMessage(session); // ������Ϣ
            BodyPart messageBodyPart = new MimeBodyPart();//�ı��ڵ�
            msg.setFrom(new InternetAddress(formEmail)); // ������

            String toList = null;
            String toListcs = null;
            String toListms = null;

            // ����,
            if (to != null) {
                toList = getMailList(to);
                if(!toList.equals("")){
                new InternetAddress();
                InternetAddress[] iaToList = InternetAddress.parse(toList);
                msg.setRecipients(Message.RecipientType.TO, iaToList); // �ռ���
                }
            }

            // ����
            if (cs != null) {
                toListcs = getMailList(cs);
                if(!toListcs.equals("")){
                new InternetAddress();
                InternetAddress[] iaToListcs = InternetAddress.parse(toListcs);
                msg.setRecipients(Message.RecipientType.CC, iaToListcs); // ������
                }
            }

            // ����
            if (ms != null) {
                toListms = getMailList(ms);
                if(!toListms.equals("")){
                	  new InternetAddress();
                      InternetAddress[] iaToListms = InternetAddress .parse(toListms);
                      msg.addRecipients(Message.RecipientType.BCC, iaToListms);
                }
            }
            msg.setSentDate(new Date()); // ��������
            msg.setSubject(subject); // ����
//            msg.setText(content); // ����
            // ��ʾ��html��ʽ���ı�����
            MimeBodyPart image = new MimeBodyPart();
            MimeBodyPart text = new MimeBodyPart();
   			text.setContent("related����һ��ͼƬ<br/><a href='#'><img src='cid:imagetupian'/></a>", "text/html;charset=utf-8");
	        DataHandler dh = new DataHandler(new FileDataSource("D://op1768//workSpace//java1234//mars45//X-admin-2.3//src//image001.jpg"));
	        image.setDataHandler(dh);
	        image.setContentID("imagetupian");    
            multipart.addBodyPart(image);
            multipart.addBodyPart(text);
            multipart.setSubType("related");
            
            
            // 2.����������
            if (fileList != null) {
                addTach(fileList, multipart);
            }

            msg.setContent(multipart);
            msg.saveChanges();
            // �ʼ�������������֤ 
            Transport tran = session.getTransport("smtp");
            tran.connect(host, //�ʼ���������ַ 
           formEmail,//�����ַ
                   password);//���������
            
            tran.sendMessage(msg, msg.getAllRecipients()); // ����
            System.out.println("�ʼ����ͳɹ�");
            to.toString();
        } catch (Exception e) {
            System.out.println("�ʼ�����ʱ�쳣");
            e.printStackTrace();
        }
    }
    
    

   

    // ��Ӷ������
    public void addTach(String fileList[], Multipart multipart)
            throws MessagingException, UnsupportedEncodingException {
        for (int index = 0; index < fileList.length; index++) {
            MimeBodyPart mailArchieve = new MimeBodyPart();
            java.io.File file=new java.io.File(fileList[index]);
            if(file.exists()){//����������ڵĻ� ��ӵ����䷢����Ϣ����
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
     * ������������ݷ����ʼ�
     *  �������������в����õ�
     * @param subject 
     * @param content
     */
    public static void forSendMy(String subject,String content )throws Exception{
   
        SendMailUtil send = SendMailUtil.getInstance();
        String to[] = {"luke.zou@china-entercom.net","944711140@qq.com"};//�ռ��˵ĵ�ַ
        String cs[] = null;
        String ms[] = null;
        if(content==null||content.length()==0){
            content = "�����ʼ����ݣ������ǲ��ԣ�����Ҫ�ظ�";
        }
        String[] arrArchievList = null;

        // 2.����������
        send.send(to, cs, ms, subject, content, myselfFormEmail, arrArchievList,myselfSendHost,myselfEmailPassword);
    }

    
	/**
	 * ʹ��op1768 ��163���� ����
	 * @param to �ռ��˼���
	 * @param cs �����˼���
	 * @param subject  ����
	 * @param content ����
	 */
    public static void SendMe(String to[],String cs[],String subject,String content)throws Exception{
        instance.send(to, cs, null, subject, content, myselfFormEmail, null,myselfSendHost,myselfEmailPassword);
    }
    

	
	
	/**
	 * ʹ��DC access ����
	 * @param to �ռ��˼���
	 * @param cs �����˼���
	 * @param subject  ����
	 * @param content ����
	 */
	public void sendZQ(String to[],String[] cs, String subject,  String content )throws Exception{
		instance.send(to, cs, null, subject, content, dcFormEmail, null,dcSendHost,dcEmailPassword);
	}
	/**
	 * ʹ��DC access ������
	 * @param to �ռ��˼���
	 * @param cs �����˼���
	  * @param ms ���ͼ���
	 * @param subject  ����
	 * @param content ����
	 */
	public void sendZQ(String to[],String[] cs,String[] ms, String subject,  String content )throws Exception{
		instance.send(to, cs, ms, subject, content, dcFormEmail, null,dcSendHost,dcEmailPassword);
	}
	
	
	
	public static void main(String[] args)throws Exception {
		SendMailUtil mail=new SendMailUtil();
		String  formEmail="dcaccess@china-entercom.net";
		String str ="D:\\op1768\\workSpace\\java1234\\mars45\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp1\\wtpwebapps\\X-admin-2.3\\upload\\20181031\\unknown###D:\\op1768\\workSpace\\java1234\\mars45\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp1\\wtpwebapps\\X-admin-2.3\\upload\\20181031\\��Ȩ��־10.29-T2.xlsx###D:\\op1768\\workSpace\\java1234\\mars45\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp1\\wtpwebapps\\X-admin-2.3\\upload\\20181031\\��Ȩ��־10.29-D3.xlsx";
		
		String[]   fileList= str.trim().split("###");
		/*String[] fileList =new String[5];
		fileList[0]="D:\\op1768\\TIM\\file\\944711140\\10-30��ɽ�����豸Ǩ��#2442979.doc";
		fileList[1]="D:\\op1768\\TIM\\file\\944711140\\image001.jpg";
		fileList[2]="D:\\op1768\\TIM\\file\\944711140\\unknown";
		fileList[3]="D:\\op1768\\TIM\\file\\944711140\\��Ȩ��־10.29-D3.xlsx";
		fileList[4]="D:\\op1768\\TIM\\file\\944711140\\��Ȩ��־10.29-T2.xlsx";*/
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
		
		 instance.send(to, cs,null , subject, content, myselfFormEmail, null,myselfSendHost,myselfEmailPassword);//�Լ������õ�����
//		 instance.send(to, null,null , subject, content, dcFormEmail, null,dcSendHost,dcEmailPassword);//�Լ������õ�����
		 

	}
    
}
