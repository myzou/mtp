package com.cter.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cter.bean.RoleBean;
import com.cter.entity.SysRole;
import com.cter.entity.SysRoleMenu;
import com.cter.entity.SysUserRole;
import com.cter.util.CommonUtil;
import com.cter.util.DBUtils;
import com.cter.util.LayuiPage;
import com.cter.util.StringUtil;


@Repository("SysRoleDaoImpl")
@SuppressWarnings("all")
public class SysRoleDaoImpl extends BaseDaOImpl<SysRole > {
	

	
	public LayuiPage<SysRole>  findSysRolePage  ( Map<String, String>   map ,LayuiPage<SysRole>  layui ){
		  DBUtils db =  DBUtils.getDBUtils();
		String 	format="yyyy-MM-dd HH:mm:ss";
		 String sql=" select  b.role_id ,b.role_name,ifnull((select a.role_name from  sys_role a where a.role_id=b.create_from), '') as 'create_from',b.status "
		 		+ "  from sys_role b where 1=1    ";
		 int page=  Integer.valueOf(map.get("page"));
		 int limit=  Integer.valueOf(map.get("limit"));
		 String role_name=  map.get("role_name");
	      List<Object> params=new ArrayList<Object>();
		 if(null!=role_name&&!role_name.equals("")){
			 params.add(role_name);
			 sql+=" and role_name  like  concat('%',?,'%')   ";
		 }
		int count=0;
		 List<SysRole> List=null;
		try {
			count=db.findCount(sql, params);
			List = db.loadPage(sql, params, SysRole.class, page, limit);
			 layui.setCountSize(count);
//			count=db.loadPage(sql, params,  MenuBean.class, page, limit);
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
	

	/**
	 * 获取所有角色
	 * @return
	 */
	public List<SysRole> loadRoles(){
		String hql="from SysRole   ";
		 List<SysRole> lists=this.find(hql);
		 if(lists!=null&&lists.size()>0){
			 return lists;
		 }
		return null;
		}
		
		/**
		 * 获取sys_role最大的role_id
		 * @return
		 */
		public int queryMaxRoleId( ) {
			 DBUtils db =  DBUtils.getDBUtils();
			String sql="select max(role_id) as max from sys_role";
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
		 * 根据roleId获取SysRole
		 * @return
		 */
		public SysRole getRoleByRoleId(String role_id){
			String hql="from SysRole  where role_id=?";
			List<Object> param=new ArrayList<Object>();
			param.add(Integer.valueOf(role_id));
			List<SysRole> list=this.find(hql, param);
			return CommonUtil.objectListGetOne(list);
			}
		
		public List<RoleBean>  loadRoleMenuByRoleId(String role_id) {
		    DBUtils db =  DBUtils.getDBUtils();
		    //查询出所有的菜单 字段去重,
	        String sqlAll = "select  a.m_id,a.menu_id,a.menu_name,a.parent_m_id,a.parent_order_num,a.child_order_num"
	        		+ " from sys_menu a group by a.menu_name order by a.parent_order_num asc, a.child_order_num asc" ;
	        //不用去重
	        String sqlById = "select r.`role_id`,r.`role_name`,m.`menu_id`,m.`m_id`,m.`menu_name`,m.`parent_m_id` ,m.`child_order_num`,m.`parent_order_num`  " + 
	        		"from sys_role_menu rm inner join sys_role r on rm.`role_id`=r.`role_id`  " + 
	        		"inner join sys_menu m on m.`menu_id`=rm.`menu_id` " + 
	        		"where r.`role_id`= ? order by m.`parent_order_num` asc ,m.`child_order_num` asc " ;
	        List<Object> params=new ArrayList<Object>();	
	        if(StringUtil.isBlank(role_id)){
	        	return 	   db. executeQueryByRefTExc(sqlAll, null, RoleBean.class);
	        	}else{
	        		params.add(Integer.valueOf(role_id));
		        	return 	   db. executeQueryByRefTExc(sqlById, params, RoleBean.class);
	        	}
		}
		
		
		/**
		 * 获取sys_role_menu最大的role_id
		 * @return
		 */
		public int queryMaxRoleMenuId( ) {
			 DBUtils db =  DBUtils.getDBUtils();
			String sql="select max(role_menu_id) as max from sys_role_menu";
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
		 * 根据roleId获取所有的sys_role_menu
		 * @return
		 */
		public List<SysRoleMenu> loadSysRoleMenuByRoleId(String role_id){
			String hql="from SysRoleMenu  where role_id=?";
			List<Object> param=new ArrayList<Object>();
			param.add(Integer.valueOf(role_id));
			List<SysRoleMenu> list=findOByClass(hql, param, SysRoleMenu.class);
			return CommonUtil.objectListGetList(list);
			}
		 
		public List<SysUserRole> loadUserRoleByRoleId(String role_id) {
			String hql ="from SysUserRole where role_id = ?";
			Integer[] param=new Integer[]{Integer.valueOf(role_id)};
			 List<SysUserRole> list= this.findOByClass(hql, param, SysUserRole.class);
			 if(list!=null&&list.size()>0){
					return list;
			 }
				return null;
		}


		public int delSysRoleMenu(String role_id) {
			 DBUtils db =  DBUtils.getDBUtils();
				String delRoleMenu="delete from  sys_role_menu where  role_id= ? ";
				List<Object>  params=new ArrayList<Object>();
				params.add(Integer.valueOf(role_id));
				int i=0;
				  try {
					 i=db.executeUpdate(delRoleMenu, params);
					 if( i>0){
						 return i;
					 }
				} catch (SQLException e) {
					e.printStackTrace();
				}finally{
			        db.closeDB();	
				}
			return  i;
		}
		
		public int delSysRole(String role_id) {
			 DBUtils db =  DBUtils.getDBUtils();
				String delRole="delete from sys_role where  role_id= ? ";
				String delUserRole="delete from  sys_user_role where  role_id= ? ";
				String delRoleMenu="delete from  sys_role_menu where  role_id= ? ";
				List<Object>  params=new ArrayList<Object>();
				params.add(Integer.valueOf(role_id));
				int i=0;
				  try {
					 i= db.executeUpdate(delRole, params);
					 db.executeUpdate(delUserRole, params);
					 db.executeUpdate(delRoleMenu, params);
					 if( i>0){
						 return i;
					 }
				} catch (SQLException e) {
					e.printStackTrace();
				}finally{
			        db.closeDB();	
				}
			return  i;
		}
		
	
}
