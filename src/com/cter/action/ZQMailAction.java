package com.cter.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.cter.entity.AuthorizationEmail;
import com.cter.entity.CaseEmail;
import com.cter.entity.EmpowerMessage;
import com.cter.entity.SendEmail;
import com.cter.entity.ZqData;
import com.cter.service.impl.ZQMailExcelService;
import com.cter.util.BaseLog;
import com.cter.util.DateUtil;
import com.cter.util.HttpDataManageUtil;
import com.cter.util.LayuiPage;
import com.opensymphony.xwork2.ActionSupport;

@Controller
public class ZQMailAction extends ActionSupport {

	private static final long serialVersionUID = 9030160997996408286L;

	@Autowired
	private ZQMailExcelService zqMailExcelService;
	
	private String fileFileName; //struts2������װ���ļ����Ӧ���ļ����ļ���,xxxFileName,layui�ϴ����ļ���fieldĬ��ֵ��file
	private File file; //struts2������װҳ���ļ����Ӧ���ļ�����xxx
	private BaseLog log=new BaseLog("EmailLog");
	
	public String getFileFileName() {
		return fileFileName;
	}
	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	/**
	 * ���س�������
	 * @return
	 * @
	 */
	public void  loadCityNames(){
		
		 try {
			List<String> list=	zqMailExcelService.loadCityNames();
			 HttpDataManageUtil.retJson(list,log);
		} catch (Exception e) {
			 log.printStackTrace(e);
		}
	}
	
	/**
	 * ���ݳ������Ƽ���pop����
	 * @return
	 * @
	 */
	public void loadPopNames() {
		HttpServletRequest request=ServletActionContext.getRequest();
		String popName=request.getParameter("popName");
		 try {
			 List<String> list=	zqMailExcelService.loadPopNames(popName);
			 HttpDataManageUtil.retJson(list,log);
		} catch (Exception e) {
			 log.printStackTrace(e);
		}
	}
	
	
	/**
	 * ����һ��eamil������Ϣ
	 * @return
	 * @
	 */
	public void sendEmail() {
		 HttpServletRequest request=ServletActionContext.getRequest();
		 log.info("���������"+request.getParameter("JsonStr"));
		 try {
			 Map<String, String> map = HttpDataManageUtil.request2Map(request, "JsonStr");
			 zqMailExcelService.sendEmail(map);
			 HttpDataManageUtil.retJson(true,log);
		} catch (Exception e) {
			log.info("�����ʼ�ʧ�ܣ�����");
			 log.printStackTrace(e);
		}
	}
	
	/**
	 * ��ȡexcel����
	 * @
	 */
	public void loadMailExcel(){
		 try {
				zqMailExcelService.readExcel();
			} catch (Exception e) {
				log.printStackTrace(e);
			}
	}
	
	/**
	 * ��ѯ��֤����
	 * @return
	 */
	public void  loadPasswordKeys(){
		ZqData zqData=  zqMailExcelService.loadPasswordKeys();
				HttpDataManageUtil.retJson(zqData,log);
	}
	
	/**
	 * �޸���֤����password_keys
	 * @return
	 * @throws IOException 
	 */
	public void  updateKey() {
		 HttpServletRequest request=ServletActionContext.getRequest();
		 String passwordKeys=	request.getParameter("passwordKeys");
		 zqMailExcelService.updateKey(passwordKeys);
		 List<String > aa=new ArrayList<String>();
			 HttpDataManageUtil.retJson(aa,log);
	}
	/**
	 * �������г�����
	 * @return
	 * @
	 */
	public void loadPopNamesList(){
		 HttpServletRequest request=ServletActionContext.getRequest();
			 List<String> list=	zqMailExcelService.loadPopNames(null);
			 HttpDataManageUtil.retJson(list,log);
	}
	
	/**
	 * �������е�caseEmail ���������
	 * @return
	 * @
	 */
	public void loadAll(){
		 List<CaseEmail> emails=zqMailExcelService.loadAll();
		 Map<String, Object> result = new HashMap<String, Object>();
			 HttpDataManageUtil.retJson(emails,log);
	}
	
	/**
	 * �������е�caseEmail �з�ҳ
	 * @return
	 * @
	 */
	public void caseEmailList() {
		 HttpServletRequest request=ServletActionContext.getRequest();
		 LayuiPage<CaseEmail>  layui=new LayuiPage<CaseEmail>();
		 try {
			Map<String, String> map=	 HttpDataManageUtil.request2MapAllString(request,log);
			 layui=zqMailExcelService.loadAllCaseEmail( map,layui );
			 HttpDataManageUtil.layuiPagination(layui.getCountSize(), layui.getDatas(),log);
		} catch (Exception e) {
			 log.printStackTrace(e);
		}
	}
	
	/**
	 * ��ȡҪ�༭��case_id ��uuid ��ȡ��ϸ��Ϣ��ҳ��
	 * @return
	 * @
	 */
	public void   getCaseEmail() {
		 HttpServletRequest request=ServletActionContext.getRequest();
		String case_uuid= request.getParameter("case_uuid");
//		 Map<String, String> map=	 HttpDataManageUtil.request2MapAllString(request);
		 CaseEmail caseEmail=zqMailExcelService.getCaseEmail( case_uuid );
				HttpDataManageUtil.retJson(caseEmail,log);
	}
	
	/**
	 * ��ȡ����������Ϣ
	 * @return
	 * @
	 */
	public void loadSendEmail(){
		 List<SendEmail> emails=zqMailExcelService.loadSendEmail();
			HttpDataManageUtil.retJson(emails,log);
	}
	
	/**
	 * ����PopName����EmpowerMessage ʵ����list
	 * @return
	 * @
	 */
	public void loadEmpowerMessageByDao(){
		 HttpServletRequest request=ServletActionContext.getRequest();
		 String pop_name=request.getParameter("popName");
		 List<EmpowerMessage>   empowerMessages=	 zqMailExcelService.loadEmpowerMessageByDao(pop_name);
		 HttpDataManageUtil.retJson(empowerMessages,log);
	}
	
	/**
	 * ����PopName����EmpowerMessage ʵ����
	 * @return
	 * @
	 */
	public void getEmpowerMessage(){
		 HttpServletRequest request=ServletActionContext.getRequest();
			String pop_name=request.getParameter("popName");
			EmpowerMessage  empowerMessage=	 zqMailExcelService.getEmpowerMessage(pop_name);
			 HttpDataManageUtil.retJson(empowerMessage,log);
	}
	
	/**
	 *����pop_name����������Ϣ
	 * @return
	 * @
	 */
	public void updateEmailByPopName(){
		 HttpServletRequest request=ServletActionContext.getRequest();
			Map<String, String> map=	 HttpDataManageUtil.request2MapAllString(request,log);
			try {
				if(zqMailExcelService.updateEmailByPopName(map)>0){
					 HttpDataManageUtil.retJson(true,log);
				}
			} catch (Exception e) {
				log.info( "���³���");
				log.printStackTrace(e);
			}
	}
	
	/**
	 *����email_uuid����������Ϣ
	 * @return
	 * @
	 */
	public void updateSendEmail(){
		 HttpServletRequest request=ServletActionContext.getRequest();
		 Map<String, String> map = HttpDataManageUtil.request2MapAllString(request, log);
			try {
				int i=zqMailExcelService.updateSendEmail(map);
				if(i>0){
					 HttpDataManageUtil.retJson(i,log);
				}
			} catch (Exception e) {
				log.info( "���³���");
				log.printStackTrace(e);
			}
	}
	
	
	/**
	 *���ݻ������ƺͳ�������ɾ��������Ϣ
	 * @return
	 * @
	 */
	public void deleteEmailByPopName(){
		 HttpServletRequest request=ServletActionContext.getRequest();
			Map<String, String> map=	 HttpDataManageUtil.request2MapAllString(request,log);
			try {
				if(zqMailExcelService.deleteEmailByPopName(map)>0){
					 HttpDataManageUtil.retJson(true,log);
				}
			} catch (Exception e) {
				log.info( "���³���");
				log.printStackTrace(e);
			}
	}
	
	/**
	 * ��ȡ�ļ��ϴ�����Ϣ
	 * @return
	 * @
	 */
	public void upLoad( ){  
		HttpServletRequest request= ServletActionContext.getRequest();
		 Map<String,String> map=new HashMap<String,String>();
		 String type=request.getParameter("type");
		 String path =request.getSession().getServletContext().getRealPath("\\upload\\");
	}
	
	
	
	/**
	 * �ϴ��������� 
	 * �����ϴ��� ��Ŀ¼ updat/�������� �ļ���
	 * @
	 */
	public void uploadImg(){
			HttpServletRequest request=ServletActionContext.getRequest();
			String type = request.getParameter("type");
			String id = request.getParameter("id");
			String separator=File.separator;
	    	String uploadPath = request.getSession().getServletContext().getRealPath(separator+"upload"+separator+	DateUtil.getDateStryyyyMMdd(new Date())+separator);  //�ļ�����·��
	    	File files = new File(uploadPath);
			if(!files.isDirectory() && !files.exists()) {//�ļ��в����ھʹ���
				files.mkdirs();
			}
	
			 try {
					/**ҳ��ؼ����ļ���**/   
		        	InputStream inStream=null;
					inStream=new FileInputStream(file);
					String fileName=uploadPath+separator+fileFileName; //File.separator����ָ���, ��ֹlinux��windows������ͬ
					FileOutputStream fs = new FileOutputStream(fileName);
			    	int byteread = 0; 
			    	byte[] buffer = new byte[1024];
			    	while ( (byteread = inStream.read(buffer)) != -1) {
			    		fs.write(buffer, 0, byteread);
			    	}
			    	fs.close();  //ע��ر���
			    	inStream.close();
			 
//		        System.out.println(DateUtil.getDateStryyyyMMdd(new Date()));
					Map<String,Object> resultMap=new HashMap<String,Object>();
					resultMap.put("fileName", fileName);  //�ϴ�һ���ļ��󷵻ص�����
					resultMap.put("status", "success");
					resultMap.put("fullPath", fileName);
					resultMap.put("url",uploadPath+separator+fileName);
					System.out.println(fileName);
					HttpDataManageUtil.retJSON (resultMap,log);
				} catch (Exception e) {
					log.info("�ϴ��ļ�����");
					log.printStackTrace(e);
				}
	}
	
	/**
	 * ��ת��caseEmail �༭ҳ
	 * @return
	 * @
	 */
	public void 	toEmailEdit(){
		
		 HttpServletRequest request=ServletActionContext.getRequest();
		 HttpServletResponse response=ServletActionContext.getResponse();
		 Object object=	 HttpDataManageUtil.request2Object(request, "data",CaseEmail.class);
		HttpDataManageUtil.retJson(1,log);
	}

	/**
	 * ����uuid�߼�ɾ��caseEmail������
	 * @return
	 * @
	 */
	public void delCaseEmail(){
		 HttpServletRequest request=ServletActionContext.getRequest();
			String case_uuid=request.getParameter("case_uuid");
		   int i=	  zqMailExcelService.delCaseEmail(case_uuid);
			 HttpDataManageUtil.retJson(true,log);
	}
	/**
	 * ����email_uuiid ɾ��send_email
	 */
	public void delSendEmail(){
		 HttpServletRequest request=ServletActionContext.getRequest();
			String email_uuid=request.getParameter("email_uuid");
		   int i=	  zqMailExcelService.delSendEmail(email_uuid);
			 HttpDataManageUtil.retJson(true,log);
	}
	
	/**
	 * ����״̬��id �޸ı�case״̬
	 * @return
	 * @
	 */
	public void caseEmailUpdate(){
		 HttpServletRequest request=ServletActionContext.getRequest();
		String case_uuid=request.getParameter("case_uuid");
		String case_status=request.getParameter("case_status");
		   int i=	  zqMailExcelService.caseEmailUpdate(case_uuid,case_status);
			 HttpDataManageUtil.retJson(i,log);
	}
	/**
	 * ��Ȩ��������
	 * @return
	 * @
	 */
	public void authorizationAdd(){
		 HttpServletRequest request=ServletActionContext.getRequest();
			String JsonStr=request.getParameter("JsonStr");
			zqMailExcelService.authorizationAdd(JsonStr );
			HttpDataManageUtil.retJson(true,log);
	}
	
	
	/**
	 * ��Ȩ�����б�
	 * @return
	 * @
	 */
	public void authorizationList() {
		 HttpServletRequest request=ServletActionContext.getRequest();
		 LayuiPage<AuthorizationEmail>  layui=new LayuiPage<AuthorizationEmail>();
		 Map<String, String> map=	 HttpDataManageUtil.request2MapAllString(request,log);
		 layui=zqMailExcelService.loadAllAuthorizationEmail( map,layui );
		 HttpDataManageUtil.layuiPagination(layui.getCountSize(), layui.getDatas(),log);
		 try {
				
			} catch (Exception e) {
				log.printStackTrace(e);
			}
	}
	/**
	 * ����au_idɾ��AuthorizationEmail
	 * @return
	 * @
	 */
	public void authorizationDelete(){
		 HttpServletRequest request=ServletActionContext.getRequest();
		 String auth_id=request.getParameter("auth_id");
		 int i=	  zqMailExcelService.authorizationDelete(auth_id);
		 HttpDataManageUtil.retJson(i,log);
	}
	
	/**
	 * ����auth_id ��ȡAuthorizationEmail
	 * @return
	 * @
	 */
	public void getAuthorizationEmail() {
		HttpServletRequest request=ServletActionContext.getRequest();
		String auth_id=request.getParameter("auth_id");
		AuthorizationEmail email=  zqMailExcelService.getAuthorizationEmail(auth_id);
		 HttpDataManageUtil.retJson(email,log);
	}
	
	/**
	 * �����ʼ�caseEmail�ͱ���authorizationEmail
	 * @return
	 * @
	 */
	public void authEmailSave(){
		 HttpServletRequest request=ServletActionContext.getRequest();
		 String JsonStr=request.getParameter("JsonStr");
		 Map<String, String> map = HttpDataManageUtil.request2Map(request, "JsonStr");
		 try {
			 zqMailExcelService.authEmailSave(JsonStr,map);
			 HttpDataManageUtil.retJson(true,log);
			} catch (Exception e) {
				log.printStackTrace(e);
			}
	}
	
	/**
	 * ����EmpowerMessage
	 * @return
	 * @
	 */
	public void addEmpowerMessage(){
		 HttpServletRequest request=ServletActionContext.getRequest();
		 Map<String, String> map = HttpDataManageUtil.request2Map(request, "JsonStr");
		 int i=zqMailExcelService.addEmpowerMessage(map);
		 HttpDataManageUtil.retJSON(i,log);
	}
	
	/**
	 * ����SendEmail
	 * @return
	 * @
	 */
	public void addSendEmail(){
		 HttpServletRequest request=ServletActionContext.getRequest();
		 Map<String, String> map = HttpDataManageUtil.request2Map(request, "JsonStr");
		 int i=zqMailExcelService.addSendEmail(map);
		 HttpDataManageUtil.retJSON(i,log);
	}
	
	
	/**
	 * ����email_uuid ��ȡ����������Ϣ
	 */
	public void getSendEmailByKey(){
		 HttpServletRequest request=ServletActionContext.getRequest();
		 String email_uuid=request.getParameter("email_uuid");
		 SendEmail email=  zqMailExcelService.getSendEmailByKey(email_uuid);
		 HttpDataManageUtil.retJSON(email,log);
	}
	
	/**
	 * ����ר����
	 * @return
	 * @
	 */
	public void test() {
		 HttpServletRequest request=ServletActionContext.getRequest();
			String JsonStr=request.getParameter("JsonStr");
			try {
				zqMailExcelService.test(JsonStr );
			} catch (Exception e) {
				log.printStackTrace(e);
			}
			
			
	}
	
}
