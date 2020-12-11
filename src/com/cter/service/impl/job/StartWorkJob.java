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
 * 测试定时器
 * @author op1768
 */
//使用  @Component注入指定的bean名称
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
		if(last&&!now){//最后一次没有断开 并且现在断开了
			Date date=new Date();
			String dateStr=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
			startWorkJob.errorSendEmail();
		}else if(!last&&now){//最后一次断开 并且现在恢复了
			startWorkJob.recoverSendEmail();
		}
	}


	//测试ggwapi 信息
	public void connectGGWApi(){
		boolean res=TestConnect.connectIpPort(GGW_IP, GGW_PORT,500);
		ZqData zqData=zqDataDao.getZqDataBySysCode("GGW API Interrupt Time");
		if(zqData==null){
			zqData=new ZqData();
			String codeId=(zqDataDao.queryMaxZqDataId()+1)+"";
			zqData.setCode_id(codeId);
			zqData.setSys_code("GGW API Interrupt Time");
			zqData.setSys_name("ggw 最后断开时间，空为正常");
			if(res==true){
				zqData.setParam_value1("");
			}else{
				Date date=new Date();
				String dateStr=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
				zqData.setParam_value1(dateStr);
			}
			zqDataDao.save(zqData);
		}else{
			String lastInterruptTime=zqData.getParam_value1();//最近一次中断时间
			if(StrUtil.isBlank(lastInterruptTime)&&!res){//最后一次没有断开 并且现在断开了
				Date date=new Date();
				String dateStr=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
				zqData.setParam_value1(dateStr);
				errorSendEmail();
				zqDataDao.saveOrUpdate(zqData);

			}else if(!StrUtil.isBlank(lastInterruptTime)&&res){//最后一次断开 并且现在恢复了
				zqData.setParam_value1("");
				recoverSendEmail();
				zqDataDao.saveOrUpdate(zqData);
			}
		}
	}
	public   void recoverSendEmail(){
		String subject="mtp 自动化服务器接口恢复";
		String content="http://210.5.3.177:48888/ 连接恢复。";
		SendMailUtil mailUtil = SendMailUtil.getInstance();
		String to[] = ggw_connect_fail_to.split(";");//收件人的地址
		String cs[] = null;
		String ms[] = null;
		String fromEmail = formEmail;//发件人的地址
		String[] arrArchievList = null;
		try {
			mailUtil.send(to,cs,ms,subject,content,formEmail,arrArchievList,host,"");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public   void errorSendEmail(){
		String subject="mtp 自动化服务器接口异常";
		String content="http://210.5.3.177:48888/ 连接异常。请尽快升级联系 luke.zou/dickson.yang/alex.cui<br>" +
				"并且致电给 dickson.yang（分机:1368）告知 ggw-api 中断，请求重启恢复。";
		SendMailUtil mailUtil = SendMailUtil.getInstance();
		String to[] = ggw_connect_fail_to.split(";");//收件人的地址
		String cs[] = null;
		String ms[] = null;
		String fromEmail = formEmail;//发件人的地址
		String[] arrArchievList = null;
		try {
			mailUtil.send(to,cs,ms,subject,content,formEmail,arrArchievList,host,"");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}