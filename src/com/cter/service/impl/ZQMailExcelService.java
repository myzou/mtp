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
//			private static  final String  fileLoaclPath="D:\\op1768\\TIM\\file\\����ģ��.xlsx";
			private static  final String  fileLoaclPath="D:\\op1768\\TIM\\file\\��������ʩ�����뵥.xlsx";
			private static  final String  fileLoaclPath2="D:\\op1768\\TIM\\file\\944711140\\ȫ����ҪIDC��Ȩ�ֲ�2018-07-11.xlsx";
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
			String email_uuid=map.get("email_uuid");//���͵�email ��email_uuid
			String yesOrNo=map.get("yesOrNo");//�Ƿ���
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
//	        caseEmail.setIns_type(map.get("insType"));20181113ɾ�� ����������
	        caseEmail.setCarry_facility(map.get("carryFacility"));
	        caseEmail.setCase_status("T");
	        caseEmail.setCreate_time(DateUtil.getDate(new Date()));
	        caseEmail.setSend_email(email.getEmail_code());
	        caseEmail.setSend_email_uuid(email.getEmail_uuid());
	        caseEmail.setPassword_key(  baseDao.loadPasswordKeys().getParam_value1());
	        if(yesOrNo.equals("��")){//���ҳ���Ƿ����ʼ��ĵĻ�����װ���ݽ��з����ʼ� Ϊ�˷����Բ�ӵ�
	        	this.packaging(caseEmail,map,email);
	        }else{
	             baseDao.saveO(caseEmail);
	        }
	        return true;
			}
	
	/**
	 * ���ݲ���������װ�ʼ�
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
		String emailMode= map.get("emailMode");//�ʼ�ģ������ �������ģ��������ģ�English
		 String addressee_email=map.get("addressee_email").trim();
	    String cc_addressee_email=map.get("cc_addressee_email").trim();
	
	    if(StringUtil.isBlank(addressee_email)){//�ռ�����������վ�����944��Luke
	    	addressee_email="944711140@qq.com###luke.zou@china-entercom.net";
	    }else{
	    	addressee_email=addressee_email.replaceAll(";", "###");
	    }
	    caseEmail.setAddressee_email(addressee_email);
	    to=addressee_email.split("###");
	    
	    if(!StringUtil.isBlank(cc_addressee_email)){//���������
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
		SimpleDateFormat df2=new SimpleDateFormat("MM��dd��");
		Date date =	df1.parse(startTime);
		//���ݿ�ʼʱ���ж��Ƿ��ǽ�����Ȩ  ��Ȩʱ��Ϊ��ǰʱ��24Сʱ��Ϊ����
		Long startLong_24=DateUtil.addHour(caseEmail.getCreate_time(),24).getTime();//��ǰʱ��+24
		Long long_start=	df1.parse(startTime).getTime();//��Ȩʱ��
		String pressing="";
		String dateStr="";
		String subject ="";
//				pressing+dateStr+"����ͨ��������Ȩ����"+pop_name+"#"+caseEmail.getCase_id();
		String content=""; 
		if(emailMode.equals("��������")){
			try {
				dateStr=new SimpleDateFormat("MM��dd��").format(df1.parse(startTime));
			} catch (ParseException e) {
				log.printStackTrace(e);
			}
			if(long_start<startLong_24){
				pressing="�o���ڙ�:";
			}
			
			subject =	pressing+dateStr+"����ͨ����Ո�ڙ��M��"+pop_name;
			if(caseEmail.getOp_type().indexOf("���^")>-1){
				subject+=" ���^";
			}
			subject+="#"+caseEmail.getCase_id();
			if(!StringUtil.isBlank(map.get("edit_mode"))&&map.get("edit_mode").equals("׷����Ա")){
				subject="׷���ˆT "+pressing+dateStr+"����ͨ����Ո�ڙ��M��"+pop_name+"#"+caseEmail.getCase_id();
				content="Dear&nbsp;&nbsp;&nbsp;"+(!(StringUtil.isBlank(map.get("supplier_name")))?map.get("supplier_name"):pop_name)+"��<br>" + 	"<br>" + 
						"&emsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��Ո׷�������ˆT�M��C��"+"���x�x��<br>"  ;
					  if(!StringUtil.isBlank_new(caseEmail.getAdd_into_personnel())){
						  content+= " ׷���ˆT:<br>"+
						  caseEmail.getAdd_into_personnel().replace("\n", "<br>")+ 
					  " <br>" +  "<br>";
					  }
			}else{
				content="Dear&nbsp;&nbsp;&nbsp;"+(!(StringUtil.isBlank(map.get("supplier_name")))?map.get("supplier_name"):pop_name)+"��<br>" + 	"<br>" +
						"&emsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��Ոͬ����˾���̎��M��C��"+"���x�x��<br>" + 
						"<br>" + 
						"�r�g��"+startTime+"-----"+endTime+"<br>" + "<br>" + 
						" �M���ˆT�YӍ��<br>" + 
						caseEmail.getInto_personnel().replace("\n", "<br>")+ 
						"<br>" +  " <br>" ;
						  content+="������"+caseEmail.getOp_type()+"<br>" + 
						"�C����"+caseEmail.getCabinet()+"<br>" + 
						"�y���O�䣺"+(caseEmail.getCarry_facility().equals("��")?"��":(caseEmail.getCarry_facility().length()<80)?caseEmail.getCarry_facility():"<br>"+caseEmail.getCarry_facility())+"<br>";	 
						  if(!StringUtil.isBlank(caseEmail.getRemarks())){
							  content+="���]:"+caseEmail.getRemarks()+"<br><br>";
						  }
			}
					content+="�J�C���"+caseEmail.getPassword_key()+"<br>" + "<br>" +
					"Best regards<br>" + 
					"DC Access<br>" + 
					"Network Engineer<br>" + 
					"Network Operation Center<br>" +
					"China Enterprise ICT Solutions Limited<br>" +  "<br>" + 
					"6F, Building A, Guangzhou Information Port, No.16,Keyun Road, Tianhe District, Guangzhou, P.R.CHINA   510665<br>" + 
					"D�� <callto:(86)20-8518%201311> (86)20-8518 1311&nbsp; <br>" + 
					"E��DC Access <mailto:dcaccess@china-entercom.net> dcaccess@china-entercom.net&emsp;&emsp;&emsp;&emsp;" + 
					"W��www.china-entercom.com <http://www.china-entercom.com/> <br>" + "<br>" + 	
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
			if(!StringUtil.isBlank(map.get("edit_mode"))&&map.get("edit_mode").equals("׷����Ա")){
				subject =	"Additional personnel "+pressing+dateStr+"CSCC applies for authorization to enter "+pop_name+"#"+caseEmail.getCase_id();
				content="Dear&nbsp;&nbsp;&nbsp;"+(!(StringUtil.isBlank(map.get("supplier_name")))?map.get("supplier_name"):pop_name)+"��<br>" + 	"<br>" + 
						"&emsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Please additional personnel  authorize access permit for our below engineer(s) to "+" in "+pop_name + "��Thank you��<br>";
						  if(!StringUtil.isBlank_new(caseEmail.getAdd_into_personnel())){
							  content+= "Additional Personnel:<br>"+
							  caseEmail.getAdd_into_personnel().replace("\n", "<br>")+  " <br>" +  "<br>";
						 }
			}else{
			content="Dear&nbsp;&nbsp;&nbsp;"+(!(StringUtil.isBlank(map.get("supplier_name")))?map.get("supplier_name"):pop_name)+"��<br>" + 	"<br>" + 
					"&emsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Please authorize access permit for our below engineer(s) to "+" in "+pop_name + "��Thank you��<br>" + 
					"<br>" + 
					"Access Hour��"+startTime+"-----"+endTime+"<br>" + "<br>" + 
					"Visitor Name&Infor��<br>" + 
					caseEmail.getInto_personnel().replace("\n", "<br>")+ 
					"<br>" +  " <br>";
					  content+="Operation:"+caseEmail.getOp_type()+"<br>" + 
					" Access Area:"+caseEmail.getCabinet()+"<br>" + 
//					"Equipment information��"+(caseEmail.getCarry_facility().equals("��")?"":caseEmail.getCarry_facility())+"<br>"+ " <br>" ;
						"Equipment information��"+(caseEmail.getCarry_facility().equals("��")?"":(caseEmail.getCarry_facility().length()<80)?caseEmail.getCarry_facility():"<br>"+caseEmail.getCarry_facility())+"<br>" ;	 
					  if(!StringUtil.isBlank(caseEmail.getRemarks())){
						  content+="remarks:"+caseEmail.getRemarks()+"<br><br>";
					  }
					  }
					 content+="Authentication password��"+caseEmail.getPassword_key()+"<br>" + "<br>" +
					"Best regards<br>" + 
					"DC Access<br>" + 
					"Network Engineer<br>" + 
					"Network Operation Center<br>" +
					"China Enterprise ICT Solutions Limited<br>" +  "<br>" + 
					"6F, Building A, Guangzhou Information Port, No.16,Keyun Road, Tianhe District, Guangzhou, P.R.CHINA   510665<br>" + 
					"D�� <callto:852  21707219> 852  21707219&nbsp; <br>" + 
					"E��DC Access <mailto:gnoc@citictel-cpc.com> gnoc@citictel-cpc.com&emsp;&emsp;&emsp;&emsp;" + 
					"W��www.china-entercom.com <http://www.china-entercom.com/> <br>" + "<br>" + 	
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
		}else if(emailMode.equals("��������")){
			try {
				dateStr=new SimpleDateFormat("MM��dd��").format(df1.parse(startTime));
			} catch (ParseException e) {
				log.printStackTrace(e);
			}
			if(long_start<startLong_24){
				pressing="������Ȩ��";
			}
			subject =	pressing+dateStr+"����ͨ��������Ȩ����"+pop_name;
			if(caseEmail.getOp_type().indexOf("�ι�")>-1){
				subject+=" �ι�";
			}
			subject+="#"+caseEmail.getCase_id();
			if(!StringUtil.isBlank(map.get("edit_mode"))&&map.get("edit_mode").equals("׷����Ա")){
				subject =	"׷����Ա "+pressing+dateStr+"����ͨ��������Ȩ����"+pop_name+"#"+caseEmail.getCase_id();
				content="Dear&nbsp;&nbsp;&nbsp;"+(!(StringUtil.isBlank(map.get("supplier_name")))?map.get("supplier_name"):pop_name)+"��<br>" + 	"<br>" + 
						"&emsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;����׷��������Ա�������"+"��лл��<br>" +  "<br>" ;
				  if(!StringUtil.isBlank_new(caseEmail.getAdd_into_personnel())){
					  content+= "׷����Ա:<br>"+
					  caseEmail.getAdd_into_personnel().replace("\n", "<br>")+  " <br>" +  "<br>";
				  }
			}else{
				content="Dear&nbsp;&nbsp;&nbsp;"+(!(StringUtil.isBlank(map.get("supplier_name")))?map.get("supplier_name"):pop_name)+"��<br>" + 	"<br>" + 
				"&emsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;����ͬ����˾����ʦ�������"+"��лл��<br>" + 
				"<br>" + 
				"ʱ�䣺"+startTime+"-----"+endTime+"<br>" + "<br>" + 
				" ������Ա��Ϣ��<br>" + 
				caseEmail.getInto_personnel().replace("\n", "<br>")+ " <br>" +  "<br>"+
				"������"+caseEmail.getOp_type()+"<br>" + 
				"����"+caseEmail.getCabinet()+"<br>" + 
				"Я���豸��"+(caseEmail.getCarry_facility().equals("��")?"��":(caseEmail.getCarry_facility().length()<80)?caseEmail.getCarry_facility():"<br>"+caseEmail.getCarry_facility())+"<br><br>";	 
				  if(!StringUtil.isBlank(caseEmail.getRemarks())){
					  content+="��ע:"+caseEmail.getRemarks()+"<br><br>";
				  }
			} 
				 content+="��֤���"+caseEmail.getPassword_key()+"<br>" + "<br>" +
				"˳ף����,<br>" + 
				"DC Access<br>" + 
				"���繤��ʦ<br>" + 
				"������ά��<br>" +
				"��������ͨ�ż������޹�˾<br>" +  "<br>" + 
				"���������������·12��֮һ��ԲEʱ������2706��2708��&nbsp;510665<br>" + 
				"�绰�� <callto:(86)20-8518%201311> (86)20-8518 1311&nbsp; <br>" + 
				"���䣺DC Access <mailto:dcaccess@china-entercom.net> dcaccess@china-entercom.net&emsp;&emsp;&emsp;&emsp;" + 
				"��ַ��www.china-entercom.com <http://www.china-entercom.com/> <br>" + "<br>" + 	
				"�ʼ���������<br>" + "<br>" + 
				"���ʼ�����������������������ͨ�ż������޹�˾�ı�����Ϣ������ָ���ռ���������" + 
				"�ռ��˱��뱣��! ����²���ָ���ռ��ˣ��������ڴ����Ǹ�⣡�����������֪ͨ��" + 
				"���ˣ��Kɾ�����ʼ�������������δ������˾������Ȩ��ͬ�⣬�Ͻ�ʹ�á���������" + 
				"�ơ���ӡ��ת�����ַ����ʼ������������κβ���֮����,���������е���Ӧ�ķ���" + 
				"���Ρ���������ͨѶ�������ԣ����ʼ������������ڵ���Ϣ���ܻᱻ���ء��۸ġ���" + 
				"ʧ���𻵡����������ӳ١��Լ����в��������ڴ��͸õ����ʼ������ݣ�������������" + 
				"�µĴ�����©���������������˾�Ų�����" ;
		}
					content=content.replace("\n", "<br>");
					log.info("�ʼ���������Ϣ��"+email.toString());
					log.info("�ʼ��ռ��ˣ�"+CommonUtil.toString(to));
					System.out.println(Arrays.toString(cs));
					log.info("�ʼ����ͼ��ˣ�"+CommonUtil.toString(cs));
					log.info("�ʼ�����������ַ��"+CommonUtil.toString(fileList));
					log.info("�ʼ����⣺"+subject);
					log.info("�ʼ����ݣ�"+content);
				    baseDao.sendEmail(caseEmail);
					mail.send(to,cs, null , subject, content, formEmail, fileList, host, password);
					log.info("�ʼ����ͳɹ�");
					
				    log.info("����caseEmail:"+caseEmail.toString());
				}
	
					/**
					 * ��ȡExcel������Ϣ�������ݿ�
					 * @throws Exception
					 */
					public   void readExcel() throws Exception{
					Map<String ,String >  map =baseDao.getPopCodes();//��ѯ���е�popName ����ε�excel�Ա�
					List<String>  errorList =new ArrayList<String>();//���ظ���popName
					InputStream is =new FileInputStream( new File(fileLoaclPath));
					XSSFWorkbook workbook = new XSSFWorkbook(is);
					StringBuffer bf=new StringBuffer();
						
					// ѭ��������Sheet
					for (int numSheet = 0; numSheet <1/* workbook.getNumberOfSheets()*/; numSheet++) {
						XSSFSheet xssfSheet = workbook.getSheetAt(numSheet);
					if (xssfSheet == null) {
					    continue;
					}
					System.out.println("sheet���ƣ�"+xssfSheet.getSheetName());
            		// ѭ����Row
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
                	  
                	/*	private String accessory;//����
                		private String mode;//ģʽ
                		private String cabinet;//���񣬶��ŷֿ�
                	 	*/                	  
			}
                
                
                System.out.println("�ظ������popName��"+errorList.toString());
                System.out.println("����"+errorList.size()+"�ҡ���");
				        }
				        if(is!=null){
				     	   is.close();
				        }
        
		
			}
			
	
			/**
			 * cellת��Ϊ�ַ���
			 * @param cell
			 * ��ֵ��	0
				CELL_TYPE_STRING	�ַ�����	1
				CELL_TYPE_FORMULA	��ʽ��	2
				CELL_TYPE_BLANK	��ֵ	3
				CELL_TYPE_BOOLEAN	������	4
				CELL_TYPE_ERROR	����	5
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
			 * ��Ȩ�������
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
				String testTool="    ���Թ��ߣ�";//���Թ���
				 String car="    ������";//����
				 String grounding="    �ϼܣ�";//�ϼ�
				 String undercarriage="    �¼ܣ�";//�¼�
				 String others="    ������";//����
				
				for(Object o:cfArr){
					 CarryFacility facility=(CarryFacility) JSONObject.toBean(JSONObject.fromObject(o),CarryFacility.class);
					 facility.setAuth_id(email.getAuth_id());
					 switch (facility.getC_operate()) {
					case "���Թ���":
						testTool+=facility.getC_manufacturer()+"  "+facility.getC_model()+"  "+facility.getC_serial_no()+",";
						break;
					case "����":
						car+=facility.getC_manufacturer()+"  "+facility.getC_model()+"  "+facility.getC_serial_no()+",";
						break;
					case "�ϼ�":
						grounding+=facility.getC_manufacturer()+"  "+facility.getC_model()+"  "+facility.getC_serial_no()+",";
						break;
					case "�¼�":
						undercarriage+=facility.getC_manufacturer()+"  "+facility.getC_model()+"  "+facility.getC_serial_no()+",";
						break;
					case "����":
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
				 email.setCarry_facility((carry_facilityStr.length()!=0)?carry_facilityStr:"��");
				 baseDao.saveO(email);
			}
			
			/**
			 * ��Ȩ�������
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
				
				String email_uuid=map.get("email_uuid");//���͵�email ��email_uuid
				String yesOrNo=map.get("yesOrNo");//�Ƿ���
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
					map.put("carryFacility",  "��");
				}else{
					facilityStr=facilityStr.substring(0, facilityStr.length()-1);
//					map.put("carryFacility",  facilityStr);
				}
//		        caseEmail.setCarry_facility(map.get("carryFacility"));

				if(yesOrNo.equals("��")){//���ҳ���Ƿ����ʼ��ĵĻ�����װ���ݽ��з����ʼ� Ϊ�˷����Բ�ӵ�
		        	this.packaging(caseEmail,map,sEmail);
		        }else if(yesOrNo.equals("��")){
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
			 * ����ר��
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
					
					
	/*			for(int i=0;i<100;i++){ //����caseEmail
				    CaseEmail caseEmail=new CaseEmail();
			        caseEmail.setCase_uuid(UuidUtil.getUUID32());
			        caseEmail.setSend_email("2656566@qq.com");
			        caseEmail.setCase_status("T");
			        caseEmail.setAddressee_email("123456@qq.com###2656566@qq.com");
			        caseEmail.setCc_addressee_email("123456@qq.com###2656566@qq.com");
			        caseEmail.setCase_id(String.valueOf((2439558+i)));
			        caseEmail.setCabinet("A13����");
			        caseEmail.setPassword_key("KpCh4864");
			        caseEmail.setOp_type("��·����"+i);
			        caseEmail.setEnd_time("2018-10-25  24:00");
			        caseEmail.setStart_time("2018-10-25  11:00");
			        caseEmail.setInto_personnel("������ ID����44098219780210669X��\r\n" + 
			        		"��С�� ID����441523198811026791��");
			        caseEmail.setPop_name("���ݸ�Ѹ����");
			        caseEmail.setIns_type("����");
			        caseEmail.setCarry_facility("������Ҫ�Ĺ���");
			        String datefor="yyyy-MM-dd HH:mm:ss";
			        Date createDate= DateUtil.getDateByString(StringUtil.formatDate(new Date(),datefor), datefor);
			        caseEmail.setCreate_time(createDate);
			        baseDao.saveO(caseEmail);
				}*/
				

			}
	
			/**
			 * ����case_uuid ɾ��CaseEmail
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
			 * ʱ��Ա�
			 * @param startDate ��ʼʱ��
			 * @param endDate   ����ʱ��
			 * @param type 			ʱ�����ͣ�yMdHms����������ʱ����
			 * @param size			  �����Ǽӣ������Ǽ� ��Ȼ��Խ���ʱ����ȣ�
			 * @return  ����ʱ����ڿ�ʼʱ�䷵��true�����򷵻�false
			 */
			public static boolean dateComparison(Date startDate,Date endDate ,String type,int size){
				
				return true;
			}
	
			/**
			 * ʱ����ӻ������
			 * @param startDate ��ʼʱ��
			 * @param type 			ʱ�����ͣ�yMdHms����������ʱ����
			 * @param size			  �����Ǽӣ������Ǽ�  
			 * @return   ���ض�Ӧ��ʱ��
			 */
			public static Date addOrSubDate(Date startDate ,String type,int size){
				
				return new Date();
			}

			

			public SendEmail getSendEmailByKey(String email_uuid){
					return  baseDao.getSendEmailByKey(email_uuid)	;
			}
	
	
	
}
