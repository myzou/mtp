package com.cter.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cter.dao.impl.SysUserDaoImpl;
import com.cter.entity.SysUser;
import com.cter.entity.SysUserRole;
import com.cter.util.DateUtil;
import com.cter.util.LayuiPage;
import com.cter.util.LoadPropertiestUtil;
import com.cter.util.RadiusOpLoginUtil;
import com.cter.util.StringUtil;


@Service("ZQSysUserService")
public class ZQSysUserService {

	@Autowired
	private SysUserDaoImpl sysUserDaoImpl;
	Map<String, String>  otherMap=LoadPropertiestUtil.loadProperties("config/other.properties");

	
	public  LayuiPage<SysUser>  findSysUserPage ( Map<String, String> map,LayuiPage<SysUser>  layui  ){
		sysUserDaoImpl.findSysUserPage (map, layui);
		return 	layui;
	}


	public int addSysUser(Map<String, String> map) {
		int  maxId=sysUserDaoImpl.queryMaxUserId();
		int maxUserRoleId=sysUserDaoImpl.queryMaxUserRoleId();
		String roleIds=map.get("roles");
		SysUser sysUser=new SysUser();
		sysUser.setUser_id(maxId+1);
		++maxId;
		sysUser.setCreate_time(DateUtil.getDate(new Date()));
		sysUser.setUpdate_time(sysUser.getCreate_time());
		sysUser.setPassword(map.get("password"));
		sysUser.setUser_name(map.get("user_name"));
		sysUser.setStatus("0");
		sysUserDaoImpl.save(sysUser);
		if(!StringUtil.isBlank(roleIds)){
		String[] roleStrs=roleIds.split(",");
		for(String str :roleStrs){
			SysUserRole userRole=new SysUserRole();
			userRole.setRole_id(Integer.valueOf(str));
			userRole.setUser_id(Integer.valueOf(maxId));
			userRole.setUser_role_id(maxUserRoleId+1);
			++maxUserRoleId;
			sysUserDaoImpl.saveO(userRole);
		}
		}
		return 0;
	}
	

	public int updateSysUser(Map<String, String> map) {
		int maxUserRoleId=sysUserDaoImpl.queryMaxUserRoleId();
		String user_id=map.get("user_id");
		
		
		String roleIds=map.get("roles");
		SysUser sysUser=	sysUserDaoImpl.getUserByUserId(user_id);

		sysUser.setUpdate_time(DateUtil.getDate(new Date()));
		if(!StringUtil.isBlank(map.get("password"))){
			sysUser.setPassword(map.get("password"));
		}
		sysUser.setUser_name(map.get("user_name"));
		sysUser.setStatus("0");
		sysUserDaoImpl.update(sysUser);
		sysUserDaoImpl.delSysUserRoleByUserId(user_id);
		
		if(!StringUtil.isBlank(roleIds)){
			String[] roleStrs=roleIds.split(",");
			for(String str :roleStrs){
				SysUserRole userRole=new SysUserRole();
				userRole.setRole_id(Integer.valueOf(str));
				userRole.setUser_id(Integer.valueOf(user_id));
				userRole.setUser_role_id(maxUserRoleId+1);
				++maxUserRoleId;
				sysUserDaoImpl.saveO(userRole);
			}
		}
		
		return 0;
	}
	
	public List <SysUserRole>  loadUserRoleByUserId(String user_id){
		return sysUserDaoImpl.loadUserRoleByUserId(user_id);
	}
	
	public SysUser  getUserByUserId(String user_id){
		return sysUserDaoImpl.getUserByUserId(user_id);
	}


	public int delSysUserByUserId(String user_id) {
		return sysUserDaoImpl.delSysUserByUserId(user_id);
	}

	/**
	 * op登录
	 * @param userName
	 * @param pwd
	 * @return
	 */
   public  int checkLogin(String userName,String pwd){
	   return RadiusOpLoginUtil.checkLogin(userName, pwd);
   }
	
   /**
    * 根据op帐号登录 赋予默认 plp权限
    * @param userName
    * @param password
    * @return 新添加op帐号的userId
    */
   public int addSysUserByOpLogin(String username,String password ) {
		int  maxId=sysUserDaoImpl.queryMaxUserId();
		int maxUserRoleId=sysUserDaoImpl.queryMaxUserRoleId();
		SysUser sysUser=new SysUser();
		sysUser.setUser_id(++maxId);
		sysUser.setCreate_time(DateUtil.getDate(new Date()));
		sysUser.setUpdate_time(sysUser.getCreate_time());
		sysUser.setPassword(password);
		sysUser.setUser_name(username);
		sysUser.setStatus("0");
		sysUserDaoImpl.save(sysUser);
		String roleIds=otherMap.get("defaultRoleIds");
		String[] roleStrs=roleIds.split(",");
		for(String str :roleStrs){
			SysUserRole userRole=new SysUserRole();
			userRole.setRole_id(Integer.valueOf(str));
			userRole.setUser_id(Integer.valueOf(maxId));
			userRole.setUser_role_id(++maxUserRoleId);
			sysUserDaoImpl.saveO(userRole);
		}
		return maxId;
	}
   
   public  void update(SysUser sysUser){
	     sysUserDaoImpl.update(sysUser);
   }
	
	public SysUser getSysUser(String user_name,String password){
		return sysUserDaoImpl.getSysUser(user_name, password);
	} 
	
	public SysUser getSysUser(String user_name ){
		return sysUserDaoImpl.getSysUser(user_name );
	} 
	
			 
}
