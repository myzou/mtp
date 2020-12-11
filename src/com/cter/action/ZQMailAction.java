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
	
	private String fileFileName; //struts2用来封装该文件域对应的文件的文件名,xxxFileName,layui上传的文件域field默认值是file
	private File file; //struts2用来封装页面文件域对应的文件内容xxx
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
	 * 加载城市名称
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
	 * 根据城市名称加载pop名称
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
	 * 增加一条eamil发送信息
	 * @return
	 * @
	 */
	public void sendEmail() {
		 HttpServletRequest request=ServletActionContext.getRequest();
		 log.info("进入参数："+request.getParameter("JsonStr"));
		 try {
			 Map<String, String> map = HttpDataManageUtil.request2Map(request, "JsonStr");
			 zqMailExcelService.sendEmail(map);
			 HttpDataManageUtil.retJson(true,log);
		} catch (Exception e) {
			log.info("发送邮件失败！！！");
			 log.printStackTrace(e);
		}
	}
	
	/**
	 * 读取excel数据
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
	 * 查询认证口令
	 * @return
	 */
	public void  loadPasswordKeys(){
		ZqData zqData=  zqMailExcelService.loadPasswordKeys();
				HttpDataManageUtil.retJson(zqData,log);
	}
	
	/**
	 * 修改认证口令password_keys
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
	 * 加载所有城市名
	 * @return
	 * @
	 */
	public void loadPopNamesList(){
		 HttpServletRequest request=ServletActionContext.getRequest();
			 List<String> list=	zqMailExcelService.loadPopNames(null);
			 HttpDataManageUtil.retJson(list,log);
	}
	
	/**
	 * 加载所有的caseEmail 测试着玩的
	 * @return
	 * @
	 */
	public void loadAll(){
		 List<CaseEmail> emails=zqMailExcelService.loadAll();
		 Map<String, Object> result = new HashMap<String, Object>();
			 HttpDataManageUtil.retJson(emails,log);
	}
	
	/**
	 * 加载所有的caseEmail 有分页
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
	 * 获取要编辑的case_id 的uuid 获取详细信息到页面
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
	 * 获取所有邮箱信息
	 * @return
	 * @
	 */
	public void loadSendEmail(){
		 List<SendEmail> emails=zqMailExcelService.loadSendEmail();
			HttpDataManageUtil.retJson(emails,log);
	}
	
	/**
	 * 根据PopName加载EmpowerMessage 实体类list
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
	 * 根据PopName加载EmpowerMessage 实体类
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
	 *根据pop_name更新邮箱信息
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
				log.info( "更新出错");
				log.printStackTrace(e);
			}
	}
	
	/**
	 *根据email_uuid更新邮箱信息
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
				log.info( "更新出错");
				log.printStackTrace(e);
			}
	}
	
	
	/**
	 *根据机房名称和城市名称删除机房信息
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
				log.info( "更新出错");
				log.printStackTrace(e);
			}
	}
	
	/**
	 * 获取文件上传的信息
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
	 * 上传附件方法 
	 * 附件上传到 根目录 updat/当天日期 文件夹
	 * @
	 */
	public void uploadImg(){
			HttpServletRequest request=ServletActionContext.getRequest();
			String type = request.getParameter("type");
			String id = request.getParameter("id");
			String separator=File.separator;
	    	String uploadPath = request.getSession().getServletContext().getRealPath(separator+"upload"+separator+	DateUtil.getDateStryyyyMMdd(new Date())+separator);  //文件保存路径
	    	File files = new File(uploadPath);
			if(!files.isDirectory() && !files.exists()) {//文件夹不存在就创建
				files.mkdirs();
			}
	
			 try {
					/**页面控件的文件流**/   
		        	InputStream inStream=null;
					inStream=new FileInputStream(file);
					String fileName=uploadPath+separator+fileFileName; //File.separator代替分隔符, 防止linux和windows环境不同
					FileOutputStream fs = new FileOutputStream(fileName);
			    	int byteread = 0; 
			    	byte[] buffer = new byte[1024];
			    	while ( (byteread = inStream.read(buffer)) != -1) {
			    		fs.write(buffer, 0, byteread);
			    	}
			    	fs.close();  //注意关闭流
			    	inStream.close();
			 
//		        System.out.println(DateUtil.getDateStryyyyMMdd(new Date()));
					Map<String,Object> resultMap=new HashMap<String,Object>();
					resultMap.put("fileName", fileName);  //上传一个文件后返回的数据
					resultMap.put("status", "success");
					resultMap.put("fullPath", fileName);
					resultMap.put("url",uploadPath+separator+fileName);
					System.out.println(fileName);
					HttpDataManageUtil.retJSON (resultMap,log);
				} catch (Exception e) {
					log.info("上传文件出错");
					log.printStackTrace(e);
				}
	}
	
	/**
	 * 跳转到caseEmail 编辑页
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
	 * 根据uuid逻辑删除caseEmail表数据
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
	 * 根据email_uuiid 删除send_email
	 */
	public void delSendEmail(){
		 HttpServletRequest request=ServletActionContext.getRequest();
			String email_uuid=request.getParameter("email_uuid");
		   int i=	  zqMailExcelService.delSendEmail(email_uuid);
			 HttpDataManageUtil.retJson(true,log);
	}
	
	/**
	 * 根据状态和id 修改表case状态
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
	 * 授权申请列表
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
	 * 根据au_id删除AuthorizationEmail
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
	 * 根据auth_id 获取AuthorizationEmail
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
	 * 发送邮件caseEmail和保存authorizationEmail
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
	 * 增加EmpowerMessage
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
	 * 增加SendEmail
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
	 * 根据email_uuid 获取发送邮箱信息
	 */
	public void getSendEmailByKey(){
		 HttpServletRequest request=ServletActionContext.getRequest();
		 String email_uuid=request.getParameter("email_uuid");
		 SendEmail email=  zqMailExcelService.getSendEmailByKey(email_uuid);
		 HttpDataManageUtil.retJSON(email,log);
	}
	
	/**
	 * 测试专用类
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
