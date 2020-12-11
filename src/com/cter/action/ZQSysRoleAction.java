package com.cter.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.cter.bean.RoleBean;
import com.cter.entity.SysRole;
import com.cter.entity.SysRoleMenu;
import com.cter.entity.SysUserRole;
import com.cter.service.impl.ZQSysRoleService;
import com.cter.util.BaseLog;
import com.cter.util.CommonUtil;
import com.cter.util.HttpDataManageUtil;
import com.cter.util.LayuiPage;
import com.opensymphony.xwork2.ActionSupport;

@Controller
public class ZQSysRoleAction extends ActionSupport {
	
	private static final long serialVersionUID = -566368986215919922L;
	
	private BaseLog log=new BaseLog(this.getClass().getName().replaceAll(".*\\.",""));

	@Autowired
	private ZQSysRoleService roleService;
	
	
	/**
	 * 加载所有的角色
	 * @return
	 * @throws Exception
	 */
	public void   sysRoleList(){
		 HttpServletRequest request=ServletActionContext.getRequest();
		 LayuiPage<SysRole>  layui=new LayuiPage<SysRole>();
		 Map<String, String> map=	 HttpDataManageUtil.request2MapAllString(request ,log);
		 roleService.findSysRolePage ( map,layui );
		 HttpDataManageUtil.layuiPagination(layui.getCountSize(), layui.getDatas() ,log);
	}
	
	/**
	 * 加载所有的角色
	 * @return
	 * @throws Exception
	 */
	public void   loadRoles(){
		 HttpServletRequest request=ServletActionContext.getRequest();
		 List<SysRole>  list=	 roleService.loadRoles ( );
		 HttpDataManageUtil.retJson(list ,log);
	}
	
	
	/**
	 *添加角色
	 * @return
	 * @throws Exception
	 */
	public void   addSysRole(){
		 HttpServletRequest request=ServletActionContext.getRequest();
		 Map<String, String> map= HttpDataManageUtil.request2Map(request, "jsonStr");
		boolean b= roleService.addSysRole (map );
		HttpDataManageUtil.retJSON (true ,log);
	}
	
	/**
	 * 修改角色
	 * @return
	 * @throws Exception
	 */
	public void   updateSysRole(){
		 HttpServletRequest request=ServletActionContext.getRequest(); 
		 Map<String, String> map= HttpDataManageUtil.request2Map(request, "jsonStr");
		 roleService.updateSysRole ( map);
		 HttpDataManageUtil.retJson(true ,log);
	}
	
	/**
	 * 删除角色
	 * @return
	 * @throws Exception
	 */
	public void   delSysRole(){
		 HttpServletRequest request=ServletActionContext.getRequest();
		 String role_id=request.getParameter("role_id");
		int i= roleService.delSysRole ( role_id);
		if(i>0){
			 HttpDataManageUtil.retJson(i ,log);
		}
	}
	
	/**
	 *根据roleId 查看角色信息
	 * @return
	 * @throws Exception
	 */
	public void  getRoleByRoleId(){
		 HttpServletRequest request=ServletActionContext.getRequest();
		 String role_id=request.getParameter("role_id");
		  SysRole   sysRole=	 roleService.getRoleByRoleId (role_id );
		 if(sysRole!=null){
			 HttpDataManageUtil.retJSON(sysRole ,log);
		 }
	}
	
	/**
	 *根据roleId 查看角色信息
	 * @return
	 * @throws Exception
	 */
	public void  loadRoleMenuByRoleId(){
		 HttpServletRequest request=ServletActionContext.getRequest();
		 String role_id=request.getParameter("role_id");
		 List<RoleBean>   sysRole=	 roleService.loadRoleMenuByRoleId (role_id );
		 if(sysRole!=null){
			 HttpDataManageUtil.retJson(sysRole ,log);
		 }
	}
	
	
	/**
	 * 删除角色之前根据roleId检查菜单
	 * sys_role_menu
	 * @return
	 * @throws Exception
	 */
	public void  checkRoleMenuByRoleId(){
		 HttpServletRequest request=ServletActionContext.getRequest();
		 String role_id=request.getParameter("role_id");
		 List<SysRoleMenu> roleMenus= roleService.checkRoleMenuByRoleId (role_id );
		if(!CommonUtil.listIsBank(roleMenus)){
			 HttpDataManageUtil.retJson(roleMenus ,log);
		}
	}
	
	/**
	 * 删除角色之前根据roleId检查用户角色
	 * sys_user_role
	 * @return
	 * @throws Exception
	 */
	public void  checkUserRoleByRoleId(){
		 HttpServletRequest request=ServletActionContext.getRequest();
		 String role_id=request.getParameter("role_id");
		 List<SysUserRole>  sysUserRoles=roleService.checkUserRoleByRoleId (role_id );
		 if(!CommonUtil.listIsBank(sysUserRoles)){
			 HttpDataManageUtil.retJson(sysUserRoles ,log);
		}
	}
	
}
