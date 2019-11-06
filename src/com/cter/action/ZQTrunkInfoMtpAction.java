package com.cter.action;

import java.io.File;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.hibernate.property.MapAccessor.MapGetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.cter.entity.TrunkInfoMtp;
import com.cter.service.impl.ZQTrunkInfoMtpService;
import com.cter.util.BaseLog;
import com.cter.util.DateUtil;
import com.cter.util.HttpDataManageUtil;
import com.cter.util.LayuiPage;
import com.opensymphony.xwork2.ActionSupport;

@Controller
public class ZQTrunkInfoMtpAction extends ActionSupport {
	
	private BaseLog log=new BaseLog(this.getClass().getName().replaceAll(".*\\.",""));
	
	
	@Autowired
	private ZQTrunkInfoMtpService mtpService;
	
	/**
	 * 加载trunk_info_mtp list
	 * @return
	 * @
	 */
	public void  trunkInfoMtpList() {
		 HttpServletRequest request=ServletActionContext.getRequest();
		 LayuiPage<TrunkInfoMtp>  layui=new LayuiPage<TrunkInfoMtp>();
		 Map<String, String> map=	 HttpDataManageUtil.request2MapAllString(request ,log);
		 mtpService.findSysUserPage ( map,layui );
		 HttpDataManageUtil.layuiPagination(layui.getCountSize(), layui.getDatas() ,log);
	}
	
	/** 
	 * 更新TrunkInfoMtp 表
	 * @return
	 * @
	 */
	public void  updateTrunkInfoMtp() {
		 HttpServletRequest request=ServletActionContext.getRequest();
		 TrunkInfoMtp trunkInfoMtp=	  HttpDataManageUtil.requestGson2Object(request, "jsonStr", TrunkInfoMtp.class);
		 mtpService.updateTrunkInfoMtp(trunkInfoMtp);
		 HttpDataManageUtil.retJSON(true ,log);
	}
	
	/** 
	 * 增加 TrunkInfoMtp 表
	 * @return
	 * @
	 */
	public void  addTrunkInfoMtp() {
		 HttpServletRequest request=ServletActionContext.getRequest();
		 TrunkInfoMtp trunkInfoMtp=	  HttpDataManageUtil.requestGson2Object(request, "jsonStr", TrunkInfoMtp.class);
		 mtpService.addTrunkInfoMtp(trunkInfoMtp);
		 HttpDataManageUtil.retJSON(true ,log);
	}
	
	/** 
	 * 增加 TrunkInfoMtp 表
	 * @return
	 * @
	 */
	public void  getTrunkInfoMtp() {
		 HttpServletRequest request=ServletActionContext.getRequest();
		 String trunkId= request.getParameter("trunkId");
		 TrunkInfoMtp infoMtp= mtpService.getTrunkInfoMtp(trunkId);
		 HttpDataManageUtil.retJSON(infoMtp ,log);
	}
	
	/**
	 * 物理线路
	 * @return
	 * @
	 */
	public void  delTrunkInfoMtp() {
		 HttpServletRequest request=ServletActionContext.getRequest();
		 String trunkId= request.getParameter("trunkId");
		 int i=  mtpService.delTrunkInfoMtp(trunkId);
		 HttpDataManageUtil.retJson(i ,log);
	}
	
}
