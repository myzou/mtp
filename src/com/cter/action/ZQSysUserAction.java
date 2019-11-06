package com.cter.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.cter.entity.SysUser;
import com.cter.entity.SysUserRole;
import com.cter.service.impl.ZQSysUserService;
import com.cter.util.BaseLog;
import com.cter.util.CommonUtil;
import com.cter.util.HttpDataManageUtil;
import com.cter.util.LayuiPage;
import com.opensymphony.xwork2.ActionSupport;

@Controller
public class ZQSysUserAction extends ActionSupport {
	
	private static final long serialVersionUID = -566368986215919922L;
	
	private BaseLog log=new BaseLog(this.getClass().getName().replaceAll(".*\\.",""));

	@Autowired
	private ZQSysUserService userService;
	
	
	
	/**
	 * 加载用户list
	 * @return
	 * @
	 */
	public void  sysUserList() {
		 HttpServletRequest request=ServletActionContext.getRequest();
		 LayuiPage<SysUser>  layui=new LayuiPage<SysUser>();
		 Map<String, String> map=	 HttpDataManageUtil.request2MapAllString(request ,log);
		 userService.findSysUserPage ( map,layui );
		 HttpDataManageUtil.layuiPagination(layui.getCountSize(), layui.getDatas() ,log);
	}
	
	/** 
	 * 添加用户
	 * @return
	 * @
	 */
	public void  addSysUser() {
		 HttpServletRequest request=ServletActionContext.getRequest();
		 Map<String, String> map=	 HttpDataManageUtil.request2Map(request, "jsonStr");
		 userService.addSysUser(map);
		 HttpDataManageUtil.retJSON(true ,log);
	}
	
	/** 
	 * 修改用户
	 * @return
	 * @
	 */
	public void  updateSysUser() {
		 HttpServletRequest request=ServletActionContext.getRequest();
		 Map<String, String> map=	 HttpDataManageUtil.request2Map(request, "jsonStr");
		 userService.updateSysUser(map);
		 HttpDataManageUtil.retJSON(true ,log);
	}
	
	/**
	 * 根据用户id 
	 * 查询出所有角色用户关联表
	 * @return
	 * @
	 */
	public void  loadUserRoleByUserId() {
		 HttpServletRequest request=ServletActionContext.getRequest();
		 String user_id= request.getParameter("user_id");
		 List<SysUserRole>  users=   userService.loadUserRoleByUserId(user_id);
		 if(!CommonUtil.listIsBank(users)){
			 HttpDataManageUtil.retJson(users ,log);
		 }
	}
	
	/**
	 * 根据用户id 查询出用户
	 * @return
	 * @
	 */
	public void  getUserByUserId() {
		 HttpServletRequest request=ServletActionContext.getRequest();
		 String user_id= request.getParameter("user_id");
		 SysUser sysUser=   userService.getUserByUserId(user_id);
		 HttpDataManageUtil.retJson(sysUser ,log);
	}
	
	/**
	 * 根据user_name 查询出用户
	 * @return
	 */
	public void  getUserByUserName() {
		 HttpServletRequest request=ServletActionContext.getRequest();
		 String user_name= request.getParameter("user_name");
		 SysUser sysUser=   userService.getSysUser(user_name);
		 if(sysUser!=null){
			 HttpDataManageUtil.retJson(true ,log);
		 }else{
			 HttpDataManageUtil.retJson(false ,log);
		 }
	}
	

	
	/**
	 * 物理删除用户
	 * @return
	 * @
	 */
	public void  delSysUserByUserId() {
		 HttpServletRequest request=ServletActionContext.getRequest();
		 String user_id= request.getParameter("user_id");
		 int i=  userService.delSysUserByUserId(user_id);
		 HttpDataManageUtil.retJson(true ,log);
	}
	
	
	
}
