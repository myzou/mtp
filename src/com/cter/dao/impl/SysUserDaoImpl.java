package com.cter.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cter.entity.SysMenu;
import com.cter.entity.SysUser;
import com.cter.entity.SysUserRole;
import com.cter.util.CommonUtil;
import com.cter.util.DBUtils;
import com.cter.util.LayuiPage;


@Repository("SysUserDaoImpl")
@SuppressWarnings("all")
public class SysUserDaoImpl extends BaseDaOImpl<SysUser> {
	
	
	public LayuiPage<SysUser>  findSysUserPage  ( Map<String, String>   map ,LayuiPage<SysUser>  layui ){
		  DBUtils db =  DBUtils.getDBUtils();
		String 	format="yyyy-MM-dd HH:mm:ss";
		 String sql="select user_id ,user_name,password,status    from sys_user a   where 1=1 ";
		 int page=  Integer.valueOf(map.get("page"));
		 int limit=  Integer.valueOf(map.get("limit"));
		 String user_name=  map.get("user_name");
	      List<Object> params=new ArrayList<Object>();
		 if(null!=user_name&&!user_name.equals("")){
			 params.add(user_name);
			 sql+=" and user_name  like     concat('%',?,'%')    ";
		 }
		int count=0;
		 List<SysUser> List=null;
		try {
			count=db.findCount(sql, params);
			List = db.loadPage(sql, params, SysUser.class, page, limit);
			 layui.setCountSize(count);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
        db.closeDB();
    }
		 if(List!=null ){
			 layui.setDatas(List);
		 }
		return layui;
	}
	

	public List<SysMenu> loadMenu() {
	    DBUtils db =  DBUtils.getDBUtils();
	    List<SysMenu>  menuList=new ArrayList<SysMenu>();
        String sql = "select menu_id,menu_name,menu_path,parent_menu_id,order_num,status,m_id"
        		+ " from sys_menu where status='0' order by m_id  asc ,order_num asc ";
        List<Object> params=new ArrayList<Object>();
        try {
        	menuList=  (List<SysMenu>) db.executeQueryByRef(sql, params, SysMenu.class);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            db.closeDB();
        }
        if(menuList!=null && menuList.size()>0){
       return  	menuList;
        }
		return null;
	}

	public int queryMaxUserId() {
		 DBUtils db =  DBUtils.getDBUtils();
		String sql="select max(user_id) as max from sys_user";
		int i=0;
		  try {
			  Object o= db.executeQuery(sql, null).get(0).get("max");
			  i=  Integer.valueOf(( o!=null&&!o.equals("") )?o.toString():"0");
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
	        db.closeDB();	
		}
		return i ;
	}
	
	/**
	 * 查找sys_user_role 表的主键最大值
	 * @return
	 */
	public int queryMaxUserRoleId() {
		 DBUtils db =  DBUtils.getDBUtils();
		String sql="select max(user_role_id) as max from sys_user_role ";
		int i=0;
		  try {
			  Object o= db.executeQuery(sql, null).get(0).get("max");
			  i=  Integer.valueOf(( o!=null&&!o.equals("") )?o.toString():"0");
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
	        db.closeDB();	
		}
		return i ;
	}


	public List<SysUserRole> loadUserRoleByUserId(String user_id) {
		String hql ="from SysUserRole where user_id = ?";
		Integer[] param=new Integer[]{Integer.valueOf(user_id)};
		 List<SysUserRole> list= this.findOByClass(hql, param, SysUserRole.class);
		 if(list!=null&&list.size()>0){
				return list;
		 }
			return null;
	}


	public int delSysUserByUserId(String user_id) {
		 DBUtils db =  DBUtils.getDBUtils();
		String delUser="delete from sys_user  where  user_id= ? ";
		String delUserRole="delete from  sys_user_role where  user_id= ? ";
		List<Object>  params=new ArrayList<Object>();
		params.add(Integer.valueOf(user_id));
		int i=0;
		  try {
			 i= db.executeUpdate(delUser, params);
			 db.executeUpdate(delUserRole, params);
			 if( i>0){
				 return i;
			 }
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
	        db.closeDB();	
		}
		return i ;
	}
	
	/**
	 * 根据user_id删除对应sys_user_role
	 * @param user_id
	 * @return
	 */
	public int delSysUserRoleByUserId(String user_id) {
		 DBUtils db =  DBUtils.getDBUtils();
		String delUserRole="delete from  sys_user_role where  user_id= ? ";
		List<Object>  params=new ArrayList<Object>();
		params.add(Integer.valueOf(user_id));
		int i=0;
		  try {
			 i=	 db.executeUpdate(delUserRole, params);
			 if( i>0){
				 return i;
			 }
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
	        db.closeDB();	
		}
		return i ;
	}


	public SysUser  getUserByUserId(String user_id) {
		String hql ="from SysUser where user_id = ?";
		Integer[] param=new Integer[]{Integer.valueOf(user_id)};
		 List<SysUser> list= this.findOByClass(hql, param, SysUser.class);
		 if(list!=null&&list.size()>0){
				return list.get(0);
		 }
			return null;
	}
	
	
	/**
	 * 根据帐号密码获取帐号信息
	 * @param user_name
	 * @param password
	 * @return
	 */
	public SysUser getSysUser(String user_name,String password){
		String hql="from SysUser where user_name= ?  and password= ? ";
		 List<Object> param =new ArrayList<Object>();
		 param.add(user_name);
		 param.add(password);
		 List<SysUser> list=		this.find(hql, param);

		 if(list!=null&&list.size()>0){
			 return list.get(0);
		 }
		return null;
	}
	
	/**
	 * 根据帐号获取帐号信息
	 * @param user_name
	 * @return
	 */
	public SysUser getSysUser(String user_name ){
		String hql="from SysUser where user_name= ?    ";
		 List<Object> param =new ArrayList<Object>();
		 param.add(user_name);
		 List<SysUser> list=		this.find(hql, param);
		 return  CommonUtil.objectListGetOne(list);
	}
	
}
