package com.cter.service.impl.job;

import cn.hutool.core.util.StrUtil;
import com.cter.dao.impl.ZqDataDaoImpl;
import com.cter.entity.ZqData;
import com.cter.util.LoadPropertiestUtil;
import com.cter.util.SendMailUtil;
import com.cter.util.TestConnect;
import org.apache.catalina.Host;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * ���Զ�ʱ��
 * @author op1768
 */
//ʹ��  @Componentע��ָ����bean����
@Component("startWorkJob")
public class StartWorkJob {

	@Autowired
	private ZqDataDaoImpl zqDataDao;
	private static Map<String, String> otherMap = LoadPropertiestUtil.loadProperties("config/other.properties");
	private static final String GGW_IP = otherMap.get("GGW_IP");
	private static Integer GGW_PORT = Integer.valueOf(otherMap.get("GGW_PORT"));
	private static final String formEmail = otherMap.get("formEmail");
	private static final String ggw_connect_fail_to = otherMap.get("ggw_connect_fail_to");
	private static final String host = otherMap.get("host");


	public static void main(String[] args) {
		StartWorkJob startWorkJob=new StartWorkJob();
		boolean last=false;
		boolean now=TestConnect.connectIpPort("210.5.3.30", 8081,500);
		if(last&&!now){//���һ��û�жϿ� �������ڶϿ���
			Date date=new Date();
			String dateStr=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
			startWorkJob.errorSendEmail();
		}else if(!last&&now){//���һ�ζϿ� �������ڻָ���
			startWorkJob.recoverSendEmail();
		}
	}


	//����ggwapi ��Ϣ
	public void connectGGWApi(){
		boolean res=TestConnect.connectIpPort(GGW_IP, GGW_PORT,500);
		ZqData zqData=zqDataDao.getZqDataBySysCode("GGW API Interrupt Time");
		if(zqData==null){
			zqData=new ZqData();
			String codeId=(zqDataDao.queryMaxZqDataId()+1)+"";
			zqData.setCode_id(codeId);
			zqData.setSys_code("GGW API Interrupt Time");
			zqData.setSys_name("ggw ���Ͽ�ʱ�䣬��Ϊ����");
			if(res==true){
				zqData.setParam_value1("");
			}else{
				Date date=new Date();
				String dateStr=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
				zqData.setParam_value1(dateStr);
			}
			zqDataDao.save(zqData);
		}else{
			String lastInterruptTime=zqData.getParam_value1();//���һ���ж�ʱ��
			if(StrUtil.isBlank(lastInterruptTime)&&!res){//���һ��û�жϿ� �������ڶϿ���
				Date date=new Date();
				String dateStr=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
				zqData.setParam_value1(dateStr);
				errorSendEmail();
				zqDataDao.saveOrUpdate(zqData);

			}else if(!StrUtil.isBlank(lastInterruptTime)&&res){//���һ�ζϿ� �������ڻָ���
				zqData.setParam_value1("");
				recoverSendEmail();
				zqDataDao.saveOrUpdate(zqData);
			}
		}
	}
	public   void recoverSendEmail(){
		String subject="mtp �Զ����������ӿڻָ�";
		String content="http://210.5.3.177:48888/ ���ӻָ���";
		SendMailUtil mailUtil = SendMailUtil.getInstance();
		String to[] = ggw_connect_fail_to.split(";");//�ռ��˵ĵ�ַ
		String cs[] = null;
		String ms[] = null;
		String fromEmail = formEmail;//�����˵ĵ�ַ
		String[] arrArchievList = null;
		try {
			mailUtil.send(to,cs,ms,subject,content,formEmail,arrArchievList,host,"");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public   void errorSendEmail(){
		String subject="mtp �Զ����������ӿ��쳣";
		String content="http://210.5.3.177:48888/ �����쳣���뾡��������ϵ luke.zou/dickson.yang/alex.cui<br>" +
				"�����µ�� dickson.yang���ֻ�:1368����֪ ggw-api �жϣ����������ָ���";
		SendMailUtil mailUtil = SendMailUtil.getInstance();
		String to[] = ggw_connect_fail_to.split(";");//�ռ��˵ĵ�ַ
		String cs[] = null;
		String ms[] = null;
		String fromEmail = formEmail;//�����˵ĵ�ַ
		String[] arrArchievList = null;
		try {
			mailUtil.send(to,cs,ms,subject,content,formEmail,arrArchievList,host,"");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}