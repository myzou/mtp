package com.cter.service.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cter.dao.impl.MtpRecordDetailedDaoImpl;
import com.cter.dao.impl.TrunkInfoMtpDaoImpl;
import com.cter.entity.MtpRecordDetailed;
import com.cter.entity.TrunkInfoMtp;
import com.cter.entity.ZqData;
import com.cter.util.DateUtil;
import com.cter.util.LayuiPage;
import com.cter.util.LoadPropertiestUtil;
import com.cter.util.SSHConnectUtil;
import com.cter.util.UuidUtil;


@Service("ZQMtpRecordDetailedService")
@Transactional
public class ZQMtpRecordDetailedService {

	@Autowired
	private MtpRecordDetailedDaoImpl mtpRecordDetailedDaoImpl ;
	
	@Autowired
	private TrunkInfoMtpDaoImpl trunkInfoMtpDaoImpl;
	private static Map<String, String >  otherMap=LoadPropertiestUtil.loadProperties("config/other.properties");
	private static final String mtp_project=otherMap.get("mtp_project");

	
	public  LayuiPage<MtpRecordDetailed>  findMtpRecordDetailed ( Map<String, String> map,LayuiPage<MtpRecordDetailed>  layui  ){
		mtpRecordDetailedDaoImpl.findMtpRecordDetailedPage (map, layui);
		return 	layui;
	}
	
	
	
	public int updateKey( Map<String,String > map) {
		String op_name=map.get("op_name");
		String op_password=map.get("op_password");
		String param_value1=op_name+"###"+op_password;
		int  i=mtpRecordDetailedDaoImpl.updateKey(param_value1, null, "OP");
		return i;
	}
	
	public int udpateCaseStatus( Map<String,String > map) {
		int  i=mtpRecordDetailedDaoImpl.udpateCaseStatus(map);
		return i;
	}
	
	public void updateMtpRecordDetailed(MtpRecordDetailed oldMtp) {
	    StringBuilder bf=new StringBuilder();
	    String period="after";
	    ZqData zqData= mtpRecordDetailedDaoImpl.getZqData("OP");
	    String opName=zqData.getParam_value1().split("###")[0];
	    String opPassword=zqData.getParam_value1().split("###")[1];
		String caseId=oldMtp.getCaseId();
		 List<MtpRecordDetailed> detaileds=mtpRecordDetailedDaoImpl.queryMtpRecordDetailed(caseId,"trunk_");
		Map<String, String> urlMap=   getUrl (oldMtp.getCaseId(),period);
	   	String filePath= urlMap.get("filePath");
	    String htmlPath= urlMap.get("htmlPath");
		    System.out.println("filePath:"+filePath);
		for(int i=0;detaileds!=null&&i<detaileds.size();i++){
					MtpRecordDetailed detailed=detaileds.get(i);
					if(detailed.getShowType().equals("trunk_provider_circuit_num")){
		    		TrunkInfoMtp infoMtp=null;
		    		infoMtp=	trunkInfoMtpDaoImpl.getTrunkInfoMtpByProviderCircuitNum(detailed.getProviderCircuitNum());
			    	String  nowDate= new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒").format(new Date());

	    	    	detailed.setAfterResultUrl(htmlPath);

    				if(infoMtp!=null){
		    	    detailed.setTrunkId(infoMtp.getTrunkId().toString());
		    	    detailed.setEndFullName(infoMtp.getAEndFullName());
		    	    detailed.setEndInterface(infoMtp.getAEndInterface());
		    	    detailed.setDeviceIp(infoMtp.getAEndInterfaceIp());
		    	    detailed.setShowType("trunk_provider_circuit_num");
	    	    	detailed.setAfterStatus("pass");
	    	    	detailed.setAfterErrorCause("");
    				bf.append("---------------------------------provider_circuit_num："+detailed.getProviderCircuitNum()+"-----------------"+nowDate+"------------------------\r\n");

		    	    detailed.setLastUpdatedTime( DateUtil.getDate(new Date()));
	    			SSHConnectUtil.OutputResult=new StringBuilder();
	    			SSHConnectUtil.connectNum=0;

	    			String showResult=SSHConnectUtil.trunkMtp(detailed,period,detailed.getEndFullName(),detailed.getDeviceIp(),detailed.getEndInterface(),opName,opPassword);
	    			bf.append(showResult);
	    		}else{
		    	    detailed.setAfterStatus("fail");
		    	    detailed.setAfterErrorCause("根据线路没有找到对应的IP");
		    		bf.append("\r\n<span style=\"color:red;font-weight:bold;\">error:根据线路 ("+detailed.getProviderCircuitNum()+" )没有找到对应的IP</span>\r\n");
	    		}
    			mtpRecordDetailedDaoImpl.update(detailed);
			}else if(detailed.getShowType().equals("trunk_trunk_name")){
	    		String  end_full_name=detailed.getEndFullName();
	    		String  end_interface=detailed.getEndInterface();
	    		TrunkInfoMtp infoMtp= 	trunkInfoMtpDaoImpl.getTrunkInfoMtpByPE(end_full_name, end_interface);
	    	    detailed.setEndFullName(end_full_name);
	    	    detailed.setEndInterface(end_interface);
	    	    detailed.setAfterResultUrl(htmlPath);
	    	   String  nowDate= new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒").format(new Date());

	    		if(infoMtp!=null){
	    			
		    	    detailed.setTrunkId(infoMtp.getTrunkId().toString());
		    	    if(infoMtp.getAEndFullName().equals(end_full_name)&&infoMtp.getAEndInterface().equals(end_interface)){
		    	    	detailed.setDeviceIp(infoMtp.getAEndInterfaceIp());
		    	    }else if(infoMtp.getZEndFullName().equals(end_full_name)&&infoMtp.getZEndInterface().equals(end_interface)){
		    	    	detailed.setDeviceIp(infoMtp.getZEndInterfaceIp());
		    	    } 
		    	    
		    	    detailed.setProviderCircuitNum(infoMtp.getProviderCircuitNum());

		    	    detailed.setAfterStatus("pass");
		    	    detailed.setAfterErrorCause("");
		    	    detailed.setLastUpdatedTime( DateUtil.getDate(new Date()));
	    			bf.append("--------------"+detailed.getDeviceIp()+"-------------------PE端口："+end_full_name+"."+end_interface+"--------------------"+nowDate+"---------------------\r\n");

	    			SSHConnectUtil.OutputResult=new StringBuilder();
	    			String showResult=SSHConnectUtil.trunkMtp(detailed,period,detailed.getEndFullName(),detailed.getDeviceIp(),detailed.getEndInterface(),opName,opPassword);
	    			bf.append(showResult);
	    		}else{
	    			detailed.setAfterStatus("fail");
	    			detailed.setAfterErrorCause ("根据PE主机和端口没有找到对应的IP");
		    	    bf.append("\r\n<span style=\"color:red;font-weight:bold;\">根据PE主机和端口没有找到对应的IP\r\n").append("PE主机："+detailed.getEndFullName())
		    	    .append("\r\n端口："+detailed.getEndInterface()+"</span>\r\n");
	    		}
			}
			mtpRecordDetailedDaoImpl.updateO(detailed);
		}
	    addHtml(bf.toString(), htmlPath,filePath);


	}
	
	public String  addMtpRecordDetailed( Map<String,String > map) {
	    StringBuffer bf=new StringBuffer();

	    String  trunk_names=map.get("trunk_names");
	    String  provider_circuit_nums =map.get("provider_circuit_nums");
	    String  case_id =map.get("case_id");
	    String  send_size =map.get("send_size");//包大小

	    String  period ="before";
	    Map<String, String> urlMap=   getUrl (case_id,period);
	    String filePath= urlMap.get("filePath");
	    String htmlPath= urlMap.get("htmlPath");

	    ZqData zqData= mtpRecordDetailedDaoImpl.getZqData("OP");
	    String opName=zqData.getParam_value1().split("###")[0];
	    String opPassword=zqData.getParam_value1().split("###")[1];

	    
	    String [] provider_circuit_nums_arr=provider_circuit_nums.split("\n");
	    String [] trunk_names_arr=trunk_names.split("\n");

	    if(provider_circuit_nums.length()>=1&&provider_circuit_nums_arr!=null&&provider_circuit_nums_arr.length>=1){
	    	for(int i=0;i<provider_circuit_nums_arr.length;i++){
	    		String provider_circuit_num= provider_circuit_nums_arr[i].trim();
	    		TrunkInfoMtp infoMtp=null;
	    		infoMtp=	trunkInfoMtpDaoImpl.getTrunkInfoMtpByProviderCircuitNum(provider_circuit_num);
	    		
	    	    MtpRecordDetailed mtpRecordDetailed=new MtpRecordDetailed();
		    	String  nowDate= new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒").format(new Date());
	    	    bf.append("---------------------------------provider_circuit_num："+provider_circuit_num+"--------------"+nowDate+"---------------------------\r\n");
    			mtpRecordDetailed.setMtpRecordDetailedUuid(UuidUtil.getUUID32());
	    	    mtpRecordDetailed.setCaseId(case_id);
	    	    mtpRecordDetailed.setProviderCircuitNum(provider_circuit_num);
	    	    mtpRecordDetailed.setShowType("trunk_provider_circuit_num");
	    	    mtpRecordDetailed.setBeforeResultUrl(htmlPath);
	    	    mtpRecordDetailed.setSendSize(send_size);
	    	    mtpRecordDetailed.setCaseStatus("now");
	    	    mtpRecordDetailed.setCreateTime( DateUtil.getDate(new Date()));
	    	    mtpRecordDetailed.setLastUpdatedTime(mtpRecordDetailed.getCreateTime());

	    		if(infoMtp!=null){
		    	    mtpRecordDetailed.setTrunkId(infoMtp.getTrunkId().toString());
		    	    mtpRecordDetailed.setEndFullName(infoMtp.getAEndFullName());
		    	    mtpRecordDetailed.setEndInterface(infoMtp.getAEndInterface());
		    	    mtpRecordDetailed.setDeviceIp(infoMtp.getAEndInterfaceIp());

	    	    	mtpRecordDetailed.setBeforeStatus("pass");
	    	    	mtpRecordDetailed.setBeforeErrorCause("");

/*		    	    	mtpRecordDetailed.setAfterStatus("");
		    	    	mtpRecordDetailed.setAfterResultUrl("");
		    	    	mtpRecordDetailed.setAfterErrorCause("");*/
	    			SSHConnectUtil.OutputResult=new StringBuilder();
	    			SSHConnectUtil.connectNum=0;
	    			String showResult=SSHConnectUtil.trunkMtp(mtpRecordDetailed,period,mtpRecordDetailed.getEndFullName(),mtpRecordDetailed.getDeviceIp(),mtpRecordDetailed.getEndInterface(),opName,opPassword);
	    			bf.append(showResult);
	    		}else{
		    	    mtpRecordDetailed.setBeforeStatus("fail");
		    	    mtpRecordDetailed.setBeforeErrorCause("根据线路没有找到对应的IP");
		    		bf.append("\r\n<span style=\"color:red;font-weight:bold;\">error:根据线路 ("+provider_circuit_num+" )没有找到对应的IP</span>\r\n");
	    		}
	    		mtpRecordDetailedDaoImpl.save(mtpRecordDetailed);
	    		
	    	}
	    }
	    
	    if(trunk_names.length()>10&&  trunk_names_arr!=null&&trunk_names_arr.length>=1){
	    	for(int i=0;i<trunk_names_arr.length;i++){
	    		String trunk_name=trunk_names_arr[i].trim();
	    		String  end_full_name=trunk_name.substring(0, trunk_name.indexOf(".")) ;
	    		String  end_interface=trunk_name.substring(  trunk_name.indexOf(".")+1,trunk_name.length()) ;
	    		TrunkInfoMtp infoMtp= 	trunkInfoMtpDaoImpl.getTrunkInfoMtpByPE(end_full_name, end_interface);
    		    MtpRecordDetailed mtpRecordDetailed=new MtpRecordDetailed();
	    	    mtpRecordDetailed.setCaseId(case_id);
	    	    mtpRecordDetailed.setMtpRecordDetailedUuid(UuidUtil.getUUID32());
	    	    mtpRecordDetailed.setShowType("trunk_trunk_name");
	    	    mtpRecordDetailed.setEndFullName(end_full_name);
	    	    mtpRecordDetailed.setEndInterface(end_interface);
	    	    mtpRecordDetailed.setBeforeResultUrl(htmlPath);
	    	    mtpRecordDetailed.setSendSize(send_size);
	    	    mtpRecordDetailed.setCaseStatus("now");
	    	    mtpRecordDetailed.setCreateTime( DateUtil.getDate(new Date()));
	    	    mtpRecordDetailed.setLastUpdatedTime(mtpRecordDetailed.getCreateTime());
		    	String  nowDate= new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒").format(new Date());

	    		if(infoMtp!=null){
		    	    mtpRecordDetailed.setTrunkId(infoMtp.getTrunkId().toString());
		    	    if(infoMtp.getAEndFullName().equals(end_full_name)&&infoMtp.getAEndInterface().equals(end_interface)){
		    	    	mtpRecordDetailed.setDeviceIp(infoMtp.getAEndInterfaceIp());
		    	    }else if(infoMtp.getZEndFullName().equals(end_full_name)&&infoMtp.getZEndInterface().equals(end_interface)){
		    	    	mtpRecordDetailed.setDeviceIp(infoMtp.getZEndInterfaceIp());
		    	    } 
		    	    mtpRecordDetailed.setProviderCircuitNum(infoMtp.getProviderCircuitNum());
		    	    mtpRecordDetailed.setBeforeStatus("pass");
		    	    mtpRecordDetailed.setBeforeErrorCause("");

	    			bf.append("-----------------"+mtpRecordDetailed.getDeviceIp()+"-------------------PE端口："+trunk_name+"-----------------"+nowDate+"------------------------\r\n");

	    			SSHConnectUtil.OutputResult=new  StringBuilder();
	    			String showResult=SSHConnectUtil.trunkMtp(mtpRecordDetailed,period,mtpRecordDetailed.getEndFullName(),mtpRecordDetailed.getDeviceIp(),mtpRecordDetailed.getEndInterface(),opName,opPassword);
	    			bf.append(showResult);
	    		}else{
		    	    mtpRecordDetailed.setBeforeStatus("fail");
		    	    mtpRecordDetailed.setBeforeErrorCause("\r\n根据PE主机和端口没有找到对应的IP\r\n");
		    	    bf.append("\r\n<span style=\"color:red;font-weight:bold;\">根据PE主机和端口没有找到对应的IP\r\n").append("PE主机："+mtpRecordDetailed.getEndFullName())
		    	    .append("\r\n端口："+mtpRecordDetailed.getEndInterface()+"</span>\r\n");
	    		}
	    		mtpRecordDetailedDaoImpl.save(mtpRecordDetailed);
	    	}
	    }

	    addHtml(bf.toString(), htmlPath,filePath);
	    System.out.println(bf);
	    return bf.toString();
	    
	}
	
	/**
	 * 获取文件路径
	 * @param caseId 
	 * @param period  时态（做维护之前或者之后）
	 * @return
	 */
	public static Map<String, String> getUrl(String caseId,String period ){
		HttpServletRequest request=ServletActionContext.getRequest();
		String separator=File.separator;
		String requestUrl=request.getSession().getServletContext().getRealPath("");
//    	String mtpPath =  separator+"mtp"+separator+	DateUtil.getDateStryyyyMMdd(new Date())+separator;  //文件保存路径
    	String mtpPath =  "/mtp/"+	DateUtil.getDateStryyyyMMdd(new Date())+"/";  //文件保存路径

    	String urlPrefix=mtp_project;
    	String urlPostfix =caseId+"_"+period+".html";
    	String htmlPath=urlPrefix+mtpPath+urlPostfix;
    	String filePath=requestUrl+mtpPath+urlPostfix;
    	Map<String, String >   urlMap=new HashMap<String, String>();
    	urlMap.put("htmlPath", htmlPath);
    	urlMap.put("filePath", filePath);
    	return urlMap;
	}
	
	
	/**
	 * 把组装的内容 放到html中
	 * @param htmlStr  组装的内容
	 * @param caseId  case名称
	 * @return
	 */
	public static void  addHtml(String htmlStr,String htmlPath,String filePath){
    	htmlStr="<pre>"+htmlStr+"</pre>";
    	InputStream  in=null;
		File file = new File(filePath);
		try {
			in=new ByteArrayInputStream(htmlStr.getBytes("UTF-8"));
		if(!file.getParentFile().exists()){
				file.getParentFile().mkdirs();
				file.createNewFile();
				System.out.println(file.getPath()+">创建成功!");
		}else{
			file.createNewFile();
			System.out.println(file.getPath()+">创建成功!");
		}
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
		try {
			FileOutputStream fs= new FileOutputStream(filePath);
	    	int byteread = 0; 
	    	byte[] buffer = new byte[1024];
	    	while ( (byteread = in.read(buffer)) != -1) {
	    		fs.write(buffer, 0, byteread);
	    	}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
	
}
