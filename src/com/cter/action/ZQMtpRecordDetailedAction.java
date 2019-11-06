package com.cter.action;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.cter.entity.MtpRecordDetailed;
import com.cter.service.impl.ZQMtpRecordDetailedService;
import com.cter.util.BaseLog;
import com.cter.util.DateUtil;
import com.cter.util.HttpDataManageUtil;
import com.cter.util.LayuiPage;
import com.opensymphony.xwork2.ActionSupport;

@Controller
public class ZQMtpRecordDetailedAction extends ActionSupport {
	
	private BaseLog log=new BaseLog(this.getClass().getName().replaceAll(".*\\.",""));
	
	
	@Autowired
	private ZQMtpRecordDetailedService detailedService ;
	
	/**
	 * 加载trunk_info_mtp list
	 * @return
	 * @
	 */
	public void  MtpRecordDetailedList() {
		 HttpServletRequest request=ServletActionContext.getRequest();
		 LayuiPage<MtpRecordDetailed>  layui=new LayuiPage<MtpRecordDetailed>();
		 Map<String, String> map=	 HttpDataManageUtil.request2MapAllString(request ,log);
		 detailedService.findMtpRecordDetailed ( map,layui );
		 HttpDataManageUtil.layuiPagination(layui.getCountSize(), layui.getDatas() ,log);
	}
	
	/** 
	 * 更新MtpRecordDetailed 表
	 * @return
	 * @
	 */
	public void  updateMtpRecordDetailed() {
		 HttpServletRequest request=ServletActionContext.getRequest();
		 MtpRecordDetailed trunkInfoMtp=	 (MtpRecordDetailed) HttpDataManageUtil.request2Object(request, "jsonStr", MtpRecordDetailed.class);
		 detailedService.updateMtpRecordDetailed(trunkInfoMtp);
		 HttpDataManageUtil.retJSON(true ,log);
	}
	
	/** 
	 * 增加 MtpRecordDetailed 表
	 * @return
	 * @
	 */
	public void  addMtpRecordDetailed() {
		 HttpServletRequest request=ServletActionContext.getRequest();
		 Map<String,String > map=HttpDataManageUtil.request2Map(request, "jsonStr");
		String result =detailedService.addMtpRecordDetailed(map);
		String separator=File.separator;
    	String uploadPath = request.getSession().getServletContext().getRealPath(separator+"mtp"+separator+	DateUtil.getDateStryyyyMMdd(new Date())+separator);  //文件保存路径
		result ="<pre>"+result+"</pre>";
		 HttpDataManageUtil.retString (result ,log);
	}
	
	
	/**
	 * 修改发送的帐号密码
	 * @return
	 * @throws IOException 
	 */
	public void  updateKey() {
		 HttpServletRequest request=ServletActionContext.getRequest();
		 Map<String,String > map=HttpDataManageUtil.request2Map(request, "jsonStr");
		 detailedService.updateKey(map);
		HttpDataManageUtil.retJSON(true,log);
	}
	
	/**
	 * 修改状态
	 * @return
	 * @throws IOException 
	 */
	public void  udpateCaseStatus() {
		 HttpServletRequest request=ServletActionContext.getRequest();
		 Map<String,String > map=HttpDataManageUtil.request2Map(request, "jsonStr");
	     detailedService.udpateCaseStatus(map);
		HttpDataManageUtil.retJSON(true,log);
	}
	
	
}
