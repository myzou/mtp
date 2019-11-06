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
 * ��������ͨ������
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
     * @param to ����������
     * @param cs ����������
     * @param ms  ����������
     * @param subject ����
     * @param content  ���͵�����
     * @param formEmail ������ maibaobao1995@163.com
     * @param fileList  ����List
     */
    public void send(String to[], String cs[], String ms[], String subject,
            String content, String formEmail, String fileList[]) {
        try {
            Properties p = new Properties(); // Properties p =
            // System.getProperties();
            p.put("mail.smtp.auth", "true");
            p.put("mail.transport.protocol", "smtp");
//            p.put("mail.smtp.host", "smtp.163.com");//�ʼ��������ĵ�ַ163
//            http://www.zimbra.com/
            p.put("mail.smtp.host", "smtp.zimbra.com");
            p.put("mail.smtp.port", "25");
            p.put("mail.debug", "true");
            // �����Ự
            Session session = Session.getInstance(p);
            Message msg = new MimeMessage(session); // ������Ϣ
            BodyPart messageBodyPart = new MimeBodyPart();
            Multipart multipart = new MimeMultipart();
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
                new InternetAddress();
                InternetAddress[] iaToListcs = InternetAddress
                        .parse(toListcs);
                msg.setRecipients(Message.RecipientType.CC, iaToListcs); // ������
            }

            // ����
            if (ms != null) {
                toListms = getMailList(ms);
                new InternetAddress();
                InternetAddress[] iaToListms = InternetAddress
                        .parse(toListms);
                msg.setRecipients(Message.RecipientType.BCC, iaToListms); // ������
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
            tran.connect("smtp.163.com", //�ʼ���������ַ 
            "maibaobao1995@163.com",//�����ַ
                    "163mail19951995");//���������
            
                    
            tran.sendMessage(msg, msg.getAllRecipients()); // ����
            System.out.println("�ʼ����ͳɹ�");

        } catch (Exception e) {
            logger.info("�ʼ�����ʱ�쳣",e);
        }
    }

    // ��Ӷ������
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
     * ������������ݷ����ʼ�
     *  �������������в����õ�
     * @param subject 
     * @param content
     */
    public static void forSend(String subject,String content){
        SendMail3 send = SendMail3.getInstance();
        String to[] = { "944711140@qq.com"};//�ռ��˵ĵ�ַ
        String cs[] = null;
        String ms[] = null;
        if(content==null||content.length()==0){
            content = "�����ʼ����ݣ������ǲ��ԣ�����Ҫ�ظ�";
        }
        String fromEmail = "maibaobao1995@163.cn";//�����˵ĵ�ַ
        String[] arrArchievList = null;

        // 2.����������
        send.send(to, cs, ms, subject, content, fromEmail, arrArchievList);
    }



	
	/**
   * @param to ����������
     * @param cs ����������
     * @param ms  ����������
     * @param subject ����
     * @param content  ���͵�����
     * @param formEmail ������ maibaobao1995@163.com
     * @param fileList  ����List
	 */
	public void send(String to[], String subject,
            String content, String formEmail){
		this.send(to, null, null, subject, content, formEmail, null);
	}
	
	/*public static void main(String[] args) {
//		163�ķ�������
		SendMail2 mail=new SendMail2();
		String  formEmail="maibaobao1995@163.com";
		String[] to={"luke.zou@china-entercom.net"};
		String subject ="�Ҳ���һ��";
		String content ="��������";
		mail.send(to, null, null, subject, content, formEmail, null);
	}*/
	
	public static void main(String[] args) { 
		SendMail3 mail=new SendMail3();
		String  formEmail="maibaobao1995@163.com";
		String[] to={"luke.zou@china-entercom.net","944711140@qq.com"};
		String subject ="10/18��������������Ȩ�����Ϻ�̩����������·ά��#2432952";
		String content ="Dear ���ţ�<br>" + 
				"<br>" + 
				"  <br>" + 
				"<br>" + 
				" &emsp;����ͬ����˾����ʦ�����������·ά�������ԣ�лл��<br>" + 
				"<br>" + 
				"ʱ�䣺2018-10-17 13:00-2018-10-17&emsp;20:00<br>" + 
				"<br>" + 
				" <br>" + 
				"<br>" + 
				"��Ա��<br>" + 
				"<br>" + 
				"������&nbsp;���֤320684198906154673<br>" + 
				"<br>" + 
				" <br>" + 
				"<br>" + 
				"�������ڽ�������jd���Բ�����·ά����������·<br>" + 
				"<br>" + 
				"�����������л���<br>" + 
				"<br>" + 
				" <br>" + 
				"<br>" + 
				" <br>" + 
				"<br>" + 
				"��֤���KpCh4864<br>" + 
				"<br>" + 
				" <br>" + 
				"<br>" + 
				"--<br>" + 
				"<br>" + 
				"˳ף����,<br>" + 
				"<br>" + 
				"������(Jicen Ge)<br>" + 
				"<br>" + 
				"���繤��ʦ<br>" + 
				"<br>" + 
				"������ά��<br>" + 
				"<br>" + 
				"��������ͨ�ż������޹�˾<br>" + 
				"<br>" + 
				"���������������·12��֮һ��ԲEʱ������2706��2708��&nbsp;510665<br>" + 
				"<br>" + 
				"�绰�� <callto:(86)20-8518%201311> (86)20-8518 1311&nbsp;�ֻ���(86)18825106779<br>" + 
				"<br>" + 
				"���䣺jicen <mailto:cijor.chen@china-entercom.net> .ge@china-entercom.net<br>" + 
				"��ַ��www.china-entercom.com <http://www.china-entercom.com/> <br>" + 
				"<br>" + 
				"<br>" + 
				"<br>" + 
				"�ʼ���������<br>" + 
				"<br>" + 
				"���ʼ�����������������������ͨ�ż������޹�˾�ı�����Ϣ������ָ���ռ���������<br>" + 
				"�ռ��˱��뱣��! ����²���ָ���ռ��ˣ��������ڴ����Ǹ�⣡�����������֪ͨ��<br>" + 
				"���ˣ��Kɾ�����ʼ�������������δ������˾������Ȩ��ͬ�⣬�Ͻ�ʹ�á���������<br>" + 
				"�ơ���ӡ��ת�����ַ����ʼ������������κβ���֮����,���������е���Ӧ�ķ���<br>" + 
				"���Ρ���������ͨѶ�������ԣ����ʼ������������ڵ���Ϣ���ܻᱻ���ء��۸ġ���<br>" + 
				"ʧ���𻵡����������ӳ١��Լ����в��������ڴ��͸õ����ʼ������ݣ�������������<br>" + 
				"�µĴ�����©���������������˾�Ų�����<br>" + 
				"";
		
		
		mail.send(to, null, null, subject, content, formEmail, null);
	}
    
}
