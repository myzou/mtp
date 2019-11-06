package com.cter.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.cter.entity.SysUser;
import com.cter.service.impl.ZQLoginService;
import com.cter.service.impl.ZQSysUserService;
import com.cter.util.BaseLog;
import com.cter.util.HttpDataManageUtil;
import com.cter.util.LoadPropertiestUtil;
import com.cter.util.RadiusOpLoginUtil;
import com.cter.util.StringUtil;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

public class LoginAction  extends ActionSupport {
	
	private BaseLog log=new BaseLog(this.getClass().getName().replaceAll(".*\\.",""));

	@Autowired
	private ZQLoginService loginService;
	@Autowired
	private ZQSysUserService userService;

	private static final long serialVersionUID = 1L;

	Map<String, String>  map=LoadPropertiestUtil.loadProperties("config/other.properties");

	/**
	 * �������еĲ˵�
	 * @return
	 * @throws Exception
	 */
	public String  login() {
		 HttpServletRequest request=ServletActionContext.getRequest();
		 String username=request.getParameter("username");
		String password= request.getParameter("password");
		String referrer= request.getParameter("referrer");
		if(StringUtil.isBlank( username)||StringUtil.isBlank( password)){
			request.setAttribute("tip", "���������˺�����");
			 return Action.LOGIN;
			 }
		String addStr="addpassword";
		if(!password.endsWith(addStr)){
			request.setAttribute("username", username);
			request.setAttribute("tip", "�û��������������");
			 return Action.LOGIN;
		}else{
			password=password.replace(addStr, "");
		}

		String opFlag=map.get("opFlag");
		HttpSession session=	ServletActionContext.getRequest().getSession();
		session.removeAttribute("login_user_id");
		if(!StringUtil.isBlank(username)&&!StringUtil.isBlank(password)){

			if(opFlag.equals("Y")){
				int i=RadiusOpLoginUtil.checkLogin(username, password);
				if(i==2){//��¼�ɹ�
					int user_id=0;
					SysUser sysUser=userService.getSysUser(username);
					if(sysUser!=null){//���ݿ�����Ѿ����˰�����������ݿ�
						user_id=sysUser.getUser_id();
						sysUser.setPassword(password);
						userService.update(sysUser);
					}else{//���ݿ�û�в����û���Ĭ�Ͻ�ɫ
						user_id=userService.addSysUserByOpLogin(username, password);
					}
					session.setAttribute("login_user_id", String.valueOf(user_id));
					session.setAttribute("login_user", username);
					session.setMaxInactiveInterval(24*60*60);
					return Action.SUCCESS;
				}
			}
			SysUser sysUser=userService.getSysUser(username, password);
			if(sysUser!=null){
				session.setAttribute("login_user_id", String.valueOf(sysUser.getUser_id()));
				session.setAttribute("login_user", sysUser.getUser_name());
				session.setMaxInactiveInterval(24*60*60);
				return Action.SUCCESS;
			}

		}
		request.setAttribute("username", username);
		request.setAttribute("tip", "�û��������������");
		 return Action.LOGIN;
	  }


	public String loginOut()throws Exception{
		 HttpServletRequest request=ServletActionContext.getRequest();
		HttpSession session=	request.getSession();
		session.removeAttribute("login_user_id");
		session.removeAttribute("login_user");
		HttpDataManageUtil.retJson(true,log);
		return  Action.LOGIN;
	}
 
}