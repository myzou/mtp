package com.cter.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.transaction.Transactional;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cter.dao.impl.EmpowerMessageDaoImpl;
import com.cter.entity.AuthorizationEmail;
import com.cter.entity.CarryFacility;
import com.cter.entity.CaseEmail;
import com.cter.entity.EmpowerMessage;
import com.cter.entity.IntoPersonnel;
import com.cter.entity.SendEmail;
import com.cter.entity.ZqData;
import com.cter.util.BaseLog;
import com.cter.util.CommonUtil;
import com.cter.util.DateUtil;
import com.cter.util.LayuiPage;
import com.cter.util.LoadPropertiestUtil;
import com.cter.util.SendMailUtil;
import com.cter.util.StringUtil;
import com.cter.util.UuidUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


@Service("ZQMailExcelService")
@Transactional
public class ZQMailExcelService {
	
			private BaseLog log=new BaseLog("EmailLog");
//			private static  final String  fileLoaclPath="D:\\op1768\\TIM\\file\\邮箱模版.xlsx";
			private static  final String  fileLoaclPath="D:\\op1768\\TIM\\file\\中信网络施工申请单.xlsx";
			private static  final String  fileLoaclPath2="D:\\op1768\\TIM\\file\\944711140\\全国主要IDC授权手册2018-07-11.xlsx";
			private static Map<String, String >  idMap=LoadPropertiestUtil.loadProperties("config/idcEmail.properties");
			private static final String SENDTOEMAIL=idMap.get("sendToEmail");
		
			@Autowired
			private  EmpowerMessageDaoImpl 	 baseDao;
			
			public   List<String>  loadCityNames() throws Exception{
				List<String> list=baseDao.loadCityNames();
				return list;
			}
			
			public   List<String>  loadPopNames(String popName)  {
				List<String> list=baseDao.loadPopNames(popName);
				return list;
			}
			
			public    EmpowerMessage  getEmpowerMessage(String popName)  {
				List<EmpowerMessage> list=baseDao.loadEmpowerMessageByDao(popName);
				return list.get(0);
			}
			
			public    int  updateEmailByPopName(Map<String, String> map) throws Exception{
				return 	baseDao.updateEmailByPopName(map);
			}
			
			public    int  deleteEmailByPopName(Map<String, String> map) throws Exception{
				String pop_name=map.get("pop_name");
				String city_name=map.get("city_name");
				return 	baseDao.deleteEmailByPopName(pop_name,city_name);
			}
			
			public    int  updateSendEmail(Map<String, String> map) throws Exception{
				SendEmail email=new SendEmail();
				email.setEmail_uuid(map.get("email_uuid"));
				email.setEmail_code(map.get("email_code"));
				email.setEmail_host(map.get("email_host"));
				email.setProtocol(map.get("protocol"));
				email.setEmail_password(map.get("email_password"));
				return 	baseDao.updateSendEmail(email);
			}
			
			
			public void  toEmailEdit(CaseEmail caseEmail) throws Exception{
				
			}
	
	
			public boolean  sendEmail(Map<String, String> map ) throws Exception{
			String email_uuid=map.get("email_uuid");//发送的email 的email_uuid
			String yesOrNo=map.get("yesOrNo");//是否发送
	       CaseEmail caseEmail=new CaseEmail();
	       SendEmail email=baseDao.getSendEmailByKey(email_uuid);
	        caseEmail.setCase_uuid(UuidUtil.getUUID32());
	        caseEmail.setCase_id(map.get("caseID"));
	        caseEmail.setCity_name(map.get("cityNames"));
	        caseEmail.setCabinet(map.get("cabinet"));
	        caseEmail.setOp_type(map.get("opType"));
	        caseEmail.setEnd_time(map.get("end_time"));
	        caseEmail.setStart_time(map.get("start_time"));
	        caseEmail.setRemarks(map.get("remarks"));
	        caseEmail.setInto_personnel(map.get("intoPersonnel"));
	        if(!StringUtil.isBlank_new(map.get("addIntoPersonnel"))){
	        	 caseEmail.setAdd_into_personnel(map.get("addIntoPersonnel"));
	        }
	        caseEmail.setPop_name(map.get("popNames"));
//	        caseEmail.setIns_type(map.get("insType"));20181113删除 主题上内容
	        caseEmail.setCarry_facility(map.get("carryFacility"));
	        caseEmail.setCase_status("T");
	        caseEmail.setCreate_time(DateUtil.getDate(new Date()));
	        caseEmail.setSend_email(email.getEmail_code());
	        caseEmail.setSend_email_uuid(email.getEmail_uuid());
	        caseEmail.setPassword_key(  baseDao.loadPasswordKeys().getParam_value1());
	        if(yesOrNo.equals("是")){//如果页面是发送邮件的的话就组装数据进行发送邮件 为了方便自测加的
	        	this.packaging(caseEmail,map,email);
	        }else{
	             baseDao.saveO(caseEmail);
	        }
	        return true;
			}
	
	/**
	 * 根据参数进行组装邮件
	 * @param caseEmail
	 * @param map
	 * @throws ParseException 
	 * @throws MessagingException 
	 */
	public  void packaging( CaseEmail caseEmail,Map<String, String> map,SendEmail email) throws Exception {
		
		SendMailUtil mail=SendMailUtil.getInstance();
		EmpowerMessage empowerMessage=baseDao.getEmpowerMessageBypopName(caseEmail.getPop_name());
		String[]   to=null;
		String[]   cs=null;
		String emailMode= map.get("emailMode");//邮件模版类型 简体中文，繁体中文，English
		 String addressee_email=map.get("addressee_email").trim();
	    String cc_addressee_email=map.get("cc_addressee_email").trim();
	
	    if(StringUtil.isBlank(addressee_email)){//收件人邮箱如果空就设置944和Luke
	    	addressee_email="944711140@qq.com###luke.zou@china-entercom.net";
	    }else{
	    	addressee_email=addressee_email.replaceAll(";", "###");
	    }
	    caseEmail.setAddressee_email(addressee_email);
	    to=addressee_email.split("###");
	    
	    if(!StringUtil.isBlank(cc_addressee_email)){//如果抄送人
	    	cc_addressee_email=cc_addressee_email.replaceAll(";", "###");
	    	cs=cc_addressee_email.split("###");
	    	caseEmail.setCc_addressee_email(cc_addressee_email);
	    } else{
	    	caseEmail.setCc_addressee_email(cc_addressee_email);
	    }
	    
		String upLoadFiles=	map.get("upLoadFiles");
		String[] fileList=upLoadFiles.trim().split("###");
		String  formEmail=email.getEmail_code();
		String password=email.getEmail_password();
		String host=email.getEmail_host();
		String startTime=caseEmail.getStart_time();
		String endTime=caseEmail.getEnd_time();
		String pop_name=caseEmail.getPop_name();
		SimpleDateFormat df1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat df2=new SimpleDateFormat("MM月dd日");
		Date date =	df1.parse(startTime);
		//根据开始时间判断是否是紧急授权  授权时间为当前时间24小时内为紧急
		Long startLong_24=DateUtil.addHour(caseEmail.getCreate_time(),24).getTime();//当前时间+24
		Long long_start=	df1.parse(startTime).getTime();//授权时间
		String pressing="";
		String dateStr="";
		String subject ="";
//				pressing+dateStr+"中企通信申请授权进入"+pop_name+"#"+caseEmail.getCase_id();
		String content=""; 
		if(emailMode.equals("繁体中文")){
			try {
				dateStr=new SimpleDateFormat("MM月dd日").format(df1.parse(startTime));
			} catch (ParseException e) {
				log.printStackTrace(e);
			}
			if(long_start<startLong_24){
				pressing="緊急授權:";
			}
			
			subject =	pressing+dateStr+"中企通信申請授權進入"+pop_name;
			if(caseEmail.getOp_type().indexOf("參觀")>-1){
				subject+=" 參觀";
			}
			subject+="#"+caseEmail.getCase_id();
			if(!StringUtil.isBlank(map.get("edit_mode"))&&map.get("edit_mode").equals("追加人员")){
				subject="追加人員 "+pressing+dateStr+"中企通信申請授權進入"+pop_name+"#"+caseEmail.getCase_id();
				content="Dear&nbsp;&nbsp;&nbsp;"+(!(StringUtil.isBlank(map.get("supplier_name")))?map.get("supplier_name"):pop_name)+"，<br>" + 	"<br>" + 
						"&emsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;煩請追加以下人員進入機房"+"，謝謝。<br>"  ;
					  if(!StringUtil.isBlank_new(caseEmail.getAdd_into_personnel())){
						  content+= " 追加人員:<br>"+
						  caseEmail.getAdd_into_personnel().replace("\n", "<br>")+ 
					  " <br>" +  "<br>";
					  }
			}else{
				content="Dear&nbsp;&nbsp;&nbsp;"+(!(StringUtil.isBlank(map.get("supplier_name")))?map.get("supplier_name"):pop_name)+"，<br>" + 	"<br>" +
						"&emsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;煩請同意我司工程師進入機房"+"，謝謝。<br>" + 
						"<br>" + 
						"時間："+startTime+"-----"+endTime+"<br>" + "<br>" + 
						" 進入人員資訊：<br>" + 
						caseEmail.getInto_personnel().replace("\n", "<br>")+ 
						"<br>" +  " <br>" ;
						  content+="操作："+caseEmail.getOp_type()+"<br>" + 
						"機櫃："+caseEmail.getCabinet()+"<br>" + 
						"攜帶設備："+(caseEmail.getCarry_facility().equals("无")?"无":(caseEmail.getCarry_facility().length()<80)?caseEmail.getCarry_facility():"<br>"+caseEmail.getCarry_facility())+"<br>";	 
						  if(!StringUtil.isBlank(caseEmail.getRemarks())){
							  content+="備註:"+caseEmail.getRemarks()+"<br><br>";
						  }
			}
					content+="認證口令："+caseEmail.getPassword_key()+"<br>" + "<br>" +
					"Best regards<br>" + 
					"DC Access<br>" + 
					"Network Engineer<br>" + 
					"Network Operation Center<br>" +
					"China Enterprise ICT Solutions Limited<br>" +  "<br>" + 
					"6F, Building A, Guangzhou Information Port, No.16,Keyun Road, Tianhe District, Guangzhou, P.R.CHINA   510665<br>" + 
					"D： <callto:(86)20-8518%201311> (86)20-8518 1311&nbsp; <br>" + 
					"E：DC Access <mailto:dcaccess@china-entercom.net> dcaccess@china-entercom.net&emsp;&emsp;&emsp;&emsp;" + 
					"W：www.china-entercom.com <http://www.china-entercom.com/> <br>" + "<br>" + 	
					"Email Disclaimer<br>" + "<br>" + 
					"The information contained in this e-mail (and attachment(s)) is confidential and is intended solely for the addressee.  If you are not "
					+ "the intended recipient, please notify the sender immediately and delete this e-mail from your system.  Any unauthorized use,"
					+ " disclosure, copying, printing, forwarding or dissemination of or dealing with any part of this information is prohibited. China Enterprise"
					+ " ICT Solutions Limited does not bear any responsibility for the contents of any e-mail transmitted by its staff for any reason other than bona fide business purposes. "
					+ " Any information that is not transmitted via secure, tamper-proof technology should not be relied upon, unless advised or agreed otherwise in writing by an authorized representative of the Company.  "
					+ "As information sent under e-mail could be intercepted, corrupted, lost, destroyed, incomplete, or could arrive late or contain viruses, "
					+ "the Company does not accept liability or obligation for any errors or omissions in the contents of this e-mail (and attachment(s)), which "
					+ "arise as result of email transmission.  Where applicable, if the sender sends this e-mail as an agent for a principal (disclosed or otherwise),"
					+ " all rights of such principal regarding confidentiality, non-disclosure and privilege against the recipient are hereby reserved." ;
		}else if(emailMode.equals("English")){
			try {
				dateStr=new SimpleDateFormat("MM-dd").format(df1.parse(startTime))+"  ";
			} catch (ParseException e) {
				log.printStackTrace(e);
			}
			if(long_start<startLong_24){
				pressing="Emergency authorization:";
			}
			subject =	pressing+dateStr+"CSCC applies for authorization to enter "+pop_name;
			if(caseEmail.getOp_type().toLowerCase().indexOf("visit")>-1){
				subject+=" Visit";
			}
			subject+="#"+caseEmail.getCase_id();
			if(!StringUtil.isBlank(map.get("edit_mode"))&&map.get("edit_mode").equals("追加人员")){
				subject =	"Additional personnel "+pressing+dateStr+"CSCC applies for authorization to enter "+pop_name+"#"+caseEmail.getCase_id();
				content="Dear&nbsp;&nbsp;&nbsp;"+(!(StringUtil.isBlank(map.get("supplier_name")))?map.get("supplier_name"):pop_name)+"，<br>" + 	"<br>" + 
						"&emsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Please additional personnel  authorize access permit for our below engineer(s) to "+" in "+pop_name + "，Thank you。<br>";
						  if(!StringUtil.isBlank_new(caseEmail.getAdd_into_personnel())){
							  content+= "Additional Personnel:<br>"+
							  caseEmail.getAdd_into_personnel().replace("\n", "<br>")+  " <br>" +  "<br>";
						 }
			}else{
			content="Dear&nbsp;&nbsp;&nbsp;"+(!(StringUtil.isBlank(map.get("supplier_name")))?map.get("supplier_name"):pop_name)+"，<br>" + 	"<br>" + 
					"&emsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Please authorize access permit for our below engineer(s) to "+" in "+pop_name + "，Thank you。<br>" + 
					"<br>" + 
					"Access Hour："+startTime+"-----"+endTime+"<br>" + "<br>" + 
					"Visitor Name&Infor：<br>" + 
					caseEmail.getInto_personnel().replace("\n", "<br>")+ 
					"<br>" +  " <br>";
					  content+="Operation:"+caseEmail.getOp_type()+"<br>" + 
					" Access Area:"+caseEmail.getCabinet()+"<br>" + 
//					"Equipment information："+(caseEmail.getCarry_facility().equals("无")?"":caseEmail.getCarry_facility())+"<br>"+ " <br>" ;
						"Equipment information："+(caseEmail.getCarry_facility().equals("无")?"":(caseEmail.getCarry_facility().length()<80)?caseEmail.getCarry_facility():"<br>"+caseEmail.getCarry_facility())+"<br>" ;	 
					  if(!StringUtil.isBlank(caseEmail.getRemarks())){
						  content+="remarks:"+caseEmail.getRemarks()+"<br><br>";
					  }
					  }
					 content+="Authentication password："+caseEmail.getPassword_key()+"<br>" + "<br>" +
					"Best regards<br>" + 
					"DC Access<br>" + 
					"Network Engineer<br>" + 
					"Network Operation Center<br>" +
					"China Enterprise ICT Solutions Limited<br>" +  "<br>" + 
					"6F, Building A, Guangzhou Information Port, No.16,Keyun Road, Tianhe District, Guangzhou, P.R.CHINA   510665<br>" + 
					"D： <callto:852  21707219> 852  21707219&nbsp; <br>" + 
					"E：DC Access <mailto:gnoc@citictel-cpc.com> gnoc@citictel-cpc.com&emsp;&emsp;&emsp;&emsp;" + 
					"W：www.china-entercom.com <http://www.china-entercom.com/> <br>" + "<br>" + 	
					"Email Disclaimer<br>" + "<br>" + 
					"The information contained in this e-mail (and attachment(s)) is confidential and is intended solely for the addressee.  If you are not "
					+ "the intended recipient, please notify the sender immediately and delete this e-mail from your system.  Any unauthorized use,"
					+ " disclosure, copying, printing, forwarding or dissemination of or dealing with any part of this information is prohibited. China Enterprise"
					+ " ICT Solutions Limited does not bear any responsibility for the contents of any e-mail transmitted by its staff for any reason other than bona fide business purposes. "
					+ " Any information that is not transmitted via secure, tamper-proof technology should not be relied upon, unless advised or agreed otherwise in writing by an authorized representative of the Company.  "
					+ "As information sent under e-mail could be intercepted, corrupted, lost, destroyed, incomplete, or could arrive late or contain viruses, "
					+ "the Company does not accept liability or obligation for any errors or omissions in the contents of this e-mail (and attachment(s)), which "
					+ "arise as result of email transmission.  Where applicable, if the sender sends this e-mail as an agent for a principal (disclosed or otherwise),"
					+ " all rights of such principal regarding confidentiality, non-disclosure and privilege against the recipient are hereby reserved." ;
		}else if(emailMode.equals("简体中文")){
			try {
				dateStr=new SimpleDateFormat("MM月dd日").format(df1.parse(startTime));
			} catch (ParseException e) {
				log.printStackTrace(e);
			}
			if(long_start<startLong_24){
				pressing="紧急授权：";
			}
			subject =	pressing+dateStr+"中企通信申请授权进入"+pop_name;
			if(caseEmail.getOp_type().indexOf("参观")>-1){
				subject+=" 参观";
			}
			subject+="#"+caseEmail.getCase_id();
			if(!StringUtil.isBlank(map.get("edit_mode"))&&map.get("edit_mode").equals("追加人员")){
				subject =	"追加人员 "+pressing+dateStr+"中企通信申请授权进入"+pop_name+"#"+caseEmail.getCase_id();
				content="Dear&nbsp;&nbsp;&nbsp;"+(!(StringUtil.isBlank(map.get("supplier_name")))?map.get("supplier_name"):pop_name)+"，<br>" + 	"<br>" + 
						"&emsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;烦请追加以下人员进入机房"+"，谢谢。<br>" +  "<br>" ;
				  if(!StringUtil.isBlank_new(caseEmail.getAdd_into_personnel())){
					  content+= "追加人员:<br>"+
					  caseEmail.getAdd_into_personnel().replace("\n", "<br>")+  " <br>" +  "<br>";
				  }
			}else{
				content="Dear&nbsp;&nbsp;&nbsp;"+(!(StringUtil.isBlank(map.get("supplier_name")))?map.get("supplier_name"):pop_name)+"，<br>" + 	"<br>" + 
				"&emsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;烦请同意我司工程师进入机房"+"，谢谢。<br>" + 
				"<br>" + 
				"时间："+startTime+"-----"+endTime+"<br>" + "<br>" + 
				" 进入人员信息：<br>" + 
				caseEmail.getInto_personnel().replace("\n", "<br>")+ " <br>" +  "<br>"+
				"操作："+caseEmail.getOp_type()+"<br>" + 
				"机柜："+caseEmail.getCabinet()+"<br>" + 
				"携带设备："+(caseEmail.getCarry_facility().equals("无")?"无":(caseEmail.getCarry_facility().length()<80)?caseEmail.getCarry_facility():"<br>"+caseEmail.getCarry_facility())+"<br><br>";	 
				  if(!StringUtil.isBlank(caseEmail.getRemarks())){
					  content+="备注:"+caseEmail.getRemarks()+"<br><br>";
				  }
			} 
				 content+="认证口令："+caseEmail.getPassword_key()+"<br>" + "<br>" +
				"顺祝商祺,<br>" + 
				"DC Access<br>" + 
				"网络工程师<br>" + 
				"网络运维部<br>" +
				"中企网络通信技术有限公司<br>" +  "<br>" + 
				"广州市天河区科韵路12号之一方圆E时光西座2706－2708房&nbsp;510665<br>" + 
				"电话： <callto:(86)20-8518%201311> (86)20-8518 1311&nbsp; <br>" + 
				"邮箱：DC Access <mailto:dcaccess@china-entercom.net> dcaccess@china-entercom.net&emsp;&emsp;&emsp;&emsp;" + 
				"网址：www.china-entercom.com <http://www.china-entercom.com/> <br>" + "<br>" + 	
				"邮件免责声明<br>" + "<br>" + 
				"本邮件（及附件）含有中企网络通信技术有限公司的保密信息，仅供指定收件人阅览，" + 
				"收件人必须保密! 如阁下不是指定收件人，发件人在此深表歉意！烦请阁下立即通知发" + 
				"件人，並删除本邮件（及附件）。未经本公司书面授权或同意，严禁使用、公开、复" + 
				"制、打印、转发及分发本邮件（及附件）任何部分之内容,否则阁下需承担相应的法律" + 
				"责任。由于网络通讯的特殊性，本邮件（及附件）内的信息可能会被截载、篡改、遗" + 
				"失、损坏、不完整、延迟、以及含有病毒，对于传送该电子邮件的内容（及附件）而引" + 
				"致的错误及遗漏或其他后果，本公司概不负责。" ;
		}
					content=content.replace("\n", "<br>");
					log.info("邮件发送人信息："+email.toString());
					log.info("邮件收件人："+CommonUtil.toString(to));
					System.out.println(Arrays.toString(cs));
					log.info("邮件抄送件人："+CommonUtil.toString(cs));
					log.info("邮件包含附件地址："+CommonUtil.toString(fileList));
					log.info("邮件主题："+subject);
					log.info("邮件内容："+content);
				    baseDao.sendEmail(caseEmail);
					mail.send(to,cs, null , subject, content, formEmail, fileList, host, password);
					log.info("邮件发送成功");
					
				    log.info("保存caseEmail:"+caseEmail.toString());
				}
	
					/**
					 * 读取Excel机房信息插入数据库
					 * @throws Exception
					 */
					public   void readExcel() throws Exception{
					Map<String ,String >  map =baseDao.getPopCodes();//查询所有的popName 和这次的excel对比
					List<String>  errorList =new ArrayList<String>();//与重复的popName
					InputStream is =new FileInputStream( new File(fileLoaclPath));
					XSSFWorkbook workbook = new XSSFWorkbook(is);
					StringBuffer bf=new StringBuffer();
						
					// 循环工作表Sheet
					for (int numSheet = 0; numSheet <1/* workbook.getNumberOfSheets()*/; numSheet++) {
						XSSFSheet xssfSheet = workbook.getSheetAt(numSheet);
					if (xssfSheet == null) {
					    continue;
					}
					System.out.println("sheet名称："+xssfSheet.getSheetName());
            		// 循环行Row
                	 for (int rowNum = 2; rowNum <=xssfSheet.getLastRowNum(); rowNum++) {
                	  XSSFRow row = xssfSheet.getRow(rowNum);
//                	  System.out.println(row.getRowNum());
                	  if(row.getCell(0)==null||row.getCell(0).getCellType()==3){
                		  break;
                		  }
                	  
                	  EmpowerMessage empowerMessage=new EmpowerMessage();
                	  empowerMessage.setUuid( UuidUtil.getUUID32());
                	  empowerMessage.setPop_name(nullToString(row.getCell(1)).trim());
//                	  empowerMessage.setGrMessage(nullToString(row.getCell(5)));
                	  empowerMessage.setCreate_time(DateUtil.getDate(new Date()));
                	  empowerMessage.setAddressee_email(nullToString(row.getCell(6)).trim().replace("\n", "###"));
                	  empowerMessage.setCc_addressee_email(nullToString(row.getCell(7)).trim().replace("\n", "###"));
                	  empowerMessage.setStem_from(nullToString(row.getCell(8)).trim());
                	  System.out.println(row.getRowNum()+"\t"+empowerMessage.toString());
                	  if(map.isEmpty()){
                			baseDao.save(empowerMessage); 
                	  }else{
                		 if( null==map.get(empowerMessage.getPop_name())){
                			 baseDao.save(empowerMessage);
                		 }else{ 
                			 errorList.add(empowerMessage.getPop_name());
                		 }
                	  }
                	  
                	/*	private String accessory;//附件
                		private String mode;//模式
                		private String cabinet;//机柜，逗号分开
                	 	*/                	  
			}
                
                
                System.out.println("重复存入的popName："+errorList.toString());
                System.out.println("共："+errorList.size()+"家。。");
				        }
				        if(is!=null){
				     	   is.close();
				        }
        
		
			}
			
	
			/**
			 * cell转换为字符串
			 * @param cell
			 * 数值型	0
				CELL_TYPE_STRING	字符串型	1
				CELL_TYPE_FORMULA	公式型	2
				CELL_TYPE_BLANK	空值	3
				CELL_TYPE_BOOLEAN	布尔型	4
				CELL_TYPE_ERROR	错误	5
			 * @return
			 */
			public    String nullToString ( XSSFCell  cell){
				String value="";
				if(null!=cell){
					int type=	cell.getCellType();
					switch (type) {
					case 1:
						value=cell.getStringCellValue();
						break;
					case 3:
					break;
					default:
						break;
					}
				}
				return value;
			}
		
			public void updateKey(String passwordKeys) {
				baseDao.updateKey(passwordKeys);
			}
			
			public ZqData loadPasswordKeys() {
				ZqData zqData=	baseDao.loadPasswordKeys();
				return zqData;
			}
			
			public  List<CaseEmail> loadAll(){
					return 	baseDao.loadAllCaseEmail();
			}
			
			public  LayuiPage<CaseEmail>  loadAllCaseEmail( Map<String, String> map,LayuiPage<CaseEmail>  layui  ){
				baseDao.findCaseEmailPage(map, layui);
				return 	layui;
			}
			
			public  LayuiPage<AuthorizationEmail>  loadAllAuthorizationEmail( Map<String, String> map,LayuiPage<AuthorizationEmail>  layui  ){
				baseDao.findAuthorizationEmailPage(map, layui);
				return 	layui;
			}
			
			public List<SendEmail> loadSendEmail(){
				return 	baseDao.loadSendEmail();
			}
			
			public int getCount(String tableName){
				return baseDao.countO(tableName);
			}
			
			public  List<EmpowerMessage>  loadEmpowerMessageByDao(String pop_name) {
				return baseDao.loadEmpowerMessageByDao(pop_name);
			}
			
			/**
			 * 授权申请添加
			 * @param JsonStr
			 * @throws Exception
			 */
			public  void  authorizationAdd( String JsonStr  ) {
				AuthorizationEmail email=(AuthorizationEmail) JSONObject.toBean(JSONObject.fromObject(JsonStr),AuthorizationEmail.class);
				JSONObject jsonObject1=JSONObject.fromObject(JsonStr);
				JSONArray  ipArr= jsonObject1.getJSONArray("intoPersonnelList");
				JSONArray cfArr= jsonObject1.getJSONArray("carryFacilityList");
				int maxAuthId=baseDao.queryMaxAuthId();
				email.setAuth_id(maxAuthId+1);
				email.setCreate_time(DateUtil.getDate(new Date()));
				email.setUpdate_time(email.getCreate_time());
				email.setAu_status("T");
				String carry_facilityStr="";
				String IntoPersonnelStr="";
				
				for(Object o:ipArr){
					 IntoPersonnel intoPersonnel=(IntoPersonnel) JSONObject.toBean(JSONObject.fromObject(o),IntoPersonnel.class);
					intoPersonnel.setAuth_id(email.getAuth_id());
					String name=(StringUtil.isBlank(intoPersonnel.getP_name()))?"":intoPersonnel.getP_name();
					String id=(StringUtil.isBlank(intoPersonnel.getP_id()))?"":intoPersonnel.getP_id();
					IntoPersonnelStr+=(name+"\t");
					IntoPersonnelStr+=(id+"\t\n");
					 baseDao.saveO(intoPersonnel);
				}
				email.setInto_personnel(IntoPersonnelStr);
				String testTool="    测试工具：";//测试工具
				 String car="    车辆：";//车辆
				 String grounding="    上架：";//上架
				 String undercarriage="    下架：";//下架
				 String others="    其他：";//其他
				
				for(Object o:cfArr){
					 CarryFacility facility=(CarryFacility) JSONObject.toBean(JSONObject.fromObject(o),CarryFacility.class);
					 facility.setAuth_id(email.getAuth_id());
					 switch (facility.getC_operate()) {
					case "测试工具":
						testTool+=facility.getC_manufacturer()+"  "+facility.getC_model()+"  "+facility.getC_serial_no()+",";
						break;
					case "车辆":
						car+=facility.getC_manufacturer()+"  "+facility.getC_model()+"  "+facility.getC_serial_no()+",";
						break;
					case "上架":
						grounding+=facility.getC_manufacturer()+"  "+facility.getC_model()+"  "+facility.getC_serial_no()+",";
						break;
					case "下架":
						undercarriage+=facility.getC_manufacturer()+"  "+facility.getC_model()+"  "+facility.getC_serial_no()+",";
						break;
					case "其他":
						others+=facility.getC_manufacturer()+"  "+facility.getC_model()+"  "+facility.getC_serial_no()+",";
						break;
					default:
						break;
					}
					 baseDao.saveO(facility);
					 System.out.println(facility.toString());
				}
				 testTool=(testTool.substring(testTool.length()-1).equals(","))?testTool.substring(0, testTool.length()-1):"";
				 car=(car.substring(car.length()-1).equals(","))?car.substring(0, car.length()-1):"";
				 grounding=(grounding.substring(grounding.length()-1).equals(","))?grounding.substring(0, grounding.length()-1):"";
				 undercarriage=(undercarriage.substring(undercarriage.length()-1).equals(","))?undercarriage.substring(0, undercarriage.length()-1):"";
				 others=(others.substring(others.length()-1).equals(","))?others.substring(0, others.length()-1):"";
				 carry_facilityStr=testTool+"\n"+car+"\n"+grounding+"\n"+undercarriage+"\n"+others;
				 email.setCarry_facility((carry_facilityStr.length()!=0)?carry_facilityStr:"无");
				 baseDao.saveO(email);
			}
			
			/**
			 * 授权申请添加
			 * @param JsonStr
			 * @throws Exception
			 */
			public  void  authEmailSave( String JsonStr ,Map<String, String> map ) throws Exception{
				CaseEmail caseEmail=new CaseEmail();
				AuthorizationEmail email=(AuthorizationEmail) JSONObject.toBean(JSONObject.fromObject(JsonStr),AuthorizationEmail.class);
				JSONObject jsonObject1=JSONObject.fromObject(JsonStr);
				JSONArray  ipArr= jsonObject1.getJSONArray("intoPersonnelList");
				JSONArray cfArr= jsonObject1.getJSONArray("carryFacilityList");
				email.setUpdate_time(DateUtil.getDate(new Date()));
				email.setAu_status("S");
				email.setCreate_time(DateUtil.getDateByString(map.get("create_time"), "yyyy-MM-dd HH:mm:ss"));
				baseDao.updateO(email);
				baseDao.delIPCFByAuth_id(String.valueOf(email.getAuth_id()));
				
				String email_uuid=map.get("email_uuid");//发送的email 的email_uuid
				String yesOrNo=map.get("yesOrNo");//是否发送
		       SendEmail sEmail=baseDao.getSendEmailByKey(email_uuid);
		        caseEmail.setCase_uuid(UuidUtil.getUUID32());
		        caseEmail.setCase_id(map.get("case_id"));
		        caseEmail.setCity_name(map.get("city_name"));
		        caseEmail.setCabinet(map.get("cabinet"));
		        caseEmail.setOp_type(map.get("op_type"));
		        caseEmail.setEnd_time(map.get("end_time"));
		        caseEmail.setStart_time(map.get("start_time"));
		        caseEmail.setPop_name(map.get("pop_name"));
		        caseEmail.setSupplier_name(map.get("supplier_name"));
		        caseEmail.setInto_personnel( map.get("into_personnel"));
		        caseEmail.setCarry_facility( map.get("carry_facility")); 
		        caseEmail.setRemarks( map.get("remarks"));
		        caseEmail.setCase_status("T");
		        caseEmail.setCreate_time(email.getUpdate_time());
		        caseEmail.setSend_email(sEmail.getEmail_code());
		        caseEmail.setSend_email_uuid(sEmail.getEmail_uuid());
		        caseEmail.setPassword_key(  baseDao.loadPasswordKeys().getParam_value1());
		        
		        
		        String IntoPersonnelStr="";
				for(int i=0;i<ipArr.size();i++){
					Object o =ipArr.get(i);
					 IntoPersonnel intoPersonnel=(IntoPersonnel) JSONObject.toBean(JSONObject.fromObject(o),IntoPersonnel.class);
					intoPersonnel.setAuth_id(email.getAuth_id());
					String name=(StringUtil.isBlank(intoPersonnel.getP_name()))?"":intoPersonnel.getP_name();
					String id=(StringUtil.isBlank(intoPersonnel.getP_id()))?"":intoPersonnel.getP_id();
					IntoPersonnelStr+=(name+"\t");
					IntoPersonnelStr+=(id+"\t\n");
					 baseDao.saveO(intoPersonnel);
				}
				 System.out.println(IntoPersonnelStr.toString());
//				 map.put("intoPersonnel",  IntoPersonnelStr);
//				 caseEmail.setInto_personnel(map.get("intoPersonnel"));
				 String facilityStr="";
				for(Object o:cfArr){
					 CarryFacility facility=(CarryFacility) JSONObject.toBean(JSONObject.fromObject(o),CarryFacility.class);
					 facility.setAuth_id(email.getAuth_id());
					 String name=(StringUtil.isBlank(facility.getC_model()))?"":facility.getC_model()+",";
					 facilityStr+=name;
					 baseDao.saveO(facility);
				}
				System.out.println(facilityStr);
				if(StringUtil.isBlank(facilityStr)){
					map.put("carryFacility",  "无");
				}else{
					facilityStr=facilityStr.substring(0, facilityStr.length()-1);
//					map.put("carryFacility",  facilityStr);
				}
//		        caseEmail.setCarry_facility(map.get("carryFacility"));

				if(yesOrNo.equals("是")){//如果页面是发送邮件的的话就组装数据进行发送邮件 为了方便自测加的
		        	this.packaging(caseEmail,map,sEmail);
		        }else if(yesOrNo.equals("否")){
		        	baseDao.saveO(caseEmail);
		        }
				
			}
			public int addEmpowerMessage( Map<String, String> map ){
				 EmpowerMessage emp=new EmpowerMessage();
				 emp.setUuid(UuidUtil.getUUID32());
				 emp.setAddressee_email(map.get("addressee_email").replaceAll(";", "###"));
				 emp.setCc_addressee_email(map.get("cc_addressee_email").replaceAll(";", "###"));
				 if(!StringUtil.isBlank(map.get("oldCity"))){
					 emp.setCity_name(map.get("oldCity"));
				 }else  if(!StringUtil.isBlank(map.get("newCity"))){
					 emp.setCity_name(map.get("newCity"));
				 }
				 emp.setCreate_time(DateUtil.getDate(new Date()));
				 emp.setPop_name(map.get("pop_name"));
				 emp.setStem_from(map.get("stem_from"));
				 Serializable ser=	 baseDao.save(emp);
				 return (ser!=null)?1:0;
			}
			
			public int addSendEmail( Map<String, String> map ){
				SendEmail email=new SendEmail();
				email.setEmail_uuid(UuidUtil.getUUID32());
				email.setEmail_code(map.get("email_code"));
				email.setEmail_host(map.get("email_host"));
				email.setEmail_name(map.get("email_name"));
				email.setEmail_password(map.get("email_password"));
				email.setProtocol(map.get("protocol"));
				 Serializable ser=	 baseDao.saveO(email);
				 return (ser!=null)?1:0;
			}
			
			
			/**
			 * 测试专用
			 * @throws Exception 
			 */
			public  void  test( String JsonStr  )  {
//					this.readExcel();
					AuthorizationEmail email=(AuthorizationEmail) JSONObject.toBean(JSONObject.fromObject(JsonStr),AuthorizationEmail.class);
					JSONObject jsonObject1=JSONObject.fromObject(JsonStr);
					JSONArray  ipArr= jsonObject1.getJSONArray("intoPersonnelList");
					JSONArray cfArr= jsonObject1.getJSONArray("carryFacilityList");
					baseDao.saveO(email);
					for(Object o:ipArr){
						 IntoPersonnel intoPersonnel=(IntoPersonnel) JSONObject.toBean(JSONObject.fromObject(o),IntoPersonnel.class);
						intoPersonnel.setAuth_id(email.getAuth_id());
						 baseDao.saveO(intoPersonnel);
						 System.out.println(intoPersonnel.toString());
					}
					for(Object o:cfArr){
						 CarryFacility facility=(CarryFacility) JSONObject.toBean(JSONObject.fromObject(o),CarryFacility.class);
						 facility.setAuth_id(email.getAuth_id());
						 baseDao.saveO(facility);
						 System.out.println(facility.toString());
					}
					
					
	/*			for(int i=0;i<100;i++){ //插入caseEmail
				    CaseEmail caseEmail=new CaseEmail();
			        caseEmail.setCase_uuid(UuidUtil.getUUID32());
			        caseEmail.setSend_email("2656566@qq.com");
			        caseEmail.setCase_status("T");
			        caseEmail.setAddressee_email("123456@qq.com###2656566@qq.com");
			        caseEmail.setCc_addressee_email("123456@qq.com###2656566@qq.com");
			        caseEmail.setCase_id(String.valueOf((2439558+i)));
			        caseEmail.setCabinet("A13机柜");
			        caseEmail.setPassword_key("KpCh4864");
			        caseEmail.setOp_type("线路抢修"+i);
			        caseEmail.setEnd_time("2018-10-25  24:00");
			        caseEmail.setStart_time("2018-10-25  11:00");
			        caseEmail.setInto_personnel("彭晓春 ID：（44098219780210669X）\r\n" + 
			        		"彭小将 ID：（441523198811026791）");
			        caseEmail.setPop_name("广州高迅机房");
			        caseEmail.setIns_type("抢修");
			        caseEmail.setCarry_facility("抢修需要的工具");
			        String datefor="yyyy-MM-dd HH:mm:ss";
			        Date createDate= DateUtil.getDateByString(StringUtil.formatDate(new Date(),datefor), datefor);
			        caseEmail.setCreate_time(createDate);
			        baseDao.saveO(caseEmail);
				}*/
				

			}
	
			/**
			 * 根据case_uuid 删除CaseEmail
			 * @param case_uuid 
			 * @return
			 */
			public  int delCaseEmail(String case_uuid) {
				return baseDao.delCaseEmail(case_uuid);
			 }
			
			public  int delSendEmail(String email_uuid) {
				return baseDao.delSendEmail(email_uuid);
			 }
			
			public  int caseEmailUpdate(String case_uuid,String case_status) {
				return baseDao.caseEmailUpdate(case_uuid,case_status);
			 }
			
			public  int authorizationDelete(String auth_id)  {
				return baseDao.authorizationUpdate(auth_id,"D");
			 }
			
			public  AuthorizationEmail getAuthorizationEmail(String auth_id) {
				return baseDao.getAuthorizationEmail(auth_id );
			 }
			
			
			public CaseEmail getCaseEmail(String case_uuid) {
			return baseDao.getCaseEmail(case_uuid);
			}
	
	
	
			/**
			 * 时间对比
			 * @param startDate 开始时间
			 * @param endDate   结束时间
			 * @param type 			时间类型，yMdHms代表年月日时分秒
			 * @param size			  正数是加，负数是减 （然后对结束时间相比）
			 * @return  结束时间大于开始时间返回true，否则返回false
			 */
			public static boolean dateComparison(Date startDate,Date endDate ,String type,int size){
				
				return true;
			}
	
			/**
			 * 时间相加或者相减
			 * @param startDate 开始时间
			 * @param type 			时间类型，yMdHms代表年月日时分秒
			 * @param size			  正数是加，负数是减  
			 * @return   返回对应的时间
			 */
			public static Date addOrSubDate(Date startDate ,String type,int size){
				
				return new Date();
			}

			

			public SendEmail getSendEmailByKey(String email_uuid){
					return  baseDao.getSendEmailByKey(email_uuid)	;
			}
	
	
	
}
