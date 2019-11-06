package com.cter.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cter.bean.RoleBean;
import com.cter.dao.impl.SysRoleDaoImpl;
import com.cter.entity.SysRole;
import com.cter.entity.SysRoleMenu;
import com.cter.entity.SysUserRole;
import com.cter.util.CommonUtil;
import com.cter.util.DateUtil;
import com.cter.util.LayuiPage;
import com.cter.util.StringUtil;


@Service("ZQSysRoleService")
public class ZQSysRoleService {

	@Autowired
	private SysRoleDaoImpl roleDaoImpl;
	
	public  LayuiPage<SysRole>  findSysRolePage ( Map<String, String> map,LayuiPage<SysRole>  layui  ){
		roleDaoImpl.findSysRolePage (map, layui);
		return 	layui;
	}
			 
	public  List<SysRole>  loadRoles (){
		return 	roleDaoImpl.loadRoles ();
	}

	public SysRole getRoleByRoleId(String role_id){
		return roleDaoImpl.getRoleByRoleId(role_id);
	}
	
	public List<RoleBean> loadRoleMenuByRoleId(String role_id){
		return roleDaoImpl.loadRoleMenuByRoleId(role_id);
	}
	
	public boolean addSysRole(Map<String, String> map) {
		SysRole role=new SysRole();
		String role_name=map.get("role_name");
		String modifyMenuId=map.get("modifyMenuId");
		String status=map.get("status");
		int maxRoleMenuId=roleDaoImpl.queryMaxRoleMenuId();
		int maxRoleId=roleDaoImpl.queryMaxRoleId();
		role.setRole_id(++maxRoleId);
		role.setCreate_from("1");
		role.setCreate_time(DateUtil.getDate(new Date()));
		role.setUpdate_time(role.getCreate_time());
		role.setRole_name(role_name);
		role.setStatus(status);
		roleDaoImpl.save(role);
		
		 if(!StringUtil.isBlank(modifyMenuId)){
		     String[] modifyMenuIds=modifyMenuId.split(",");
			 for (String menuId:modifyMenuIds){
					SysRoleMenu roleMenu=new SysRoleMenu();
					roleMenu.setMenu_id(Integer.valueOf(menuId));
					roleMenu.setRole_menu_id(++maxRoleMenuId);
					roleMenu.setRole_id(Integer.valueOf(role.getRole_id()));
					roleMenu.setStatus("0");
					roleDaoImpl.saveO(roleMenu);
			 }
	 }
	
		return true;
	}
	
	public void updateSysRole(Map<String, String> map) {
		
		String role_id=map.get("role_id");
		String role_name=map.get("role_name");
		String modifyMenuId=map.get("modifyMenuId");
		String status=map.get("status");
		int maxRoleMenuId=roleDaoImpl.queryMaxRoleMenuId();
		SysRole role=roleDaoImpl.getRoleByRoleId(role_id);
		role.setUpdate_time(DateUtil.getDate(new Date()));
		role.setRole_name(role_name);
		role.setStatus(status);
		 roleDaoImpl.update(role);
		 
		 int i =roleDaoImpl.delSysRoleMenu(role_id);

		 if(!StringUtil.isBlank(modifyMenuId)){
			     String[] modifyMenuIds=modifyMenuId.split(",");
				 for (String menuId:modifyMenuIds){
						SysRoleMenu roleMenu=new SysRoleMenu();
						roleMenu.setMenu_id(Integer.valueOf(menuId));
						roleMenu.setRole_menu_id(++maxRoleMenuId);
						roleMenu.setRole_id(Integer.valueOf(role.getRole_id()));
						roleMenu.setStatus("0");
						roleDaoImpl.saveO(roleMenu);
				 }
		 }
		
	}
	
	public int delSysRole(String role_id) {
		return roleDaoImpl.delSysRole(role_id);
	}
	
	public List<SysRoleMenu>  loadSysRoleMenuByRoleId(String role_id){
		return roleDaoImpl.loadSysRoleMenuByRoleId(role_id);
	}
	
	public List<SysRoleMenu> checkRoleMenuByRoleId (String role_id ){
		 return roleDaoImpl.loadSysRoleMenuByRoleId(role_id);
	}
	
		public List<SysUserRole> checkUserRoleByRoleId(String role_id ){
			 return roleDaoImpl.loadUserRoleByRoleId(role_id);
		}
	
}
