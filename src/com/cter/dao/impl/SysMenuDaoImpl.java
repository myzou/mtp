package com.cter.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cter.bean.MenuBean;
import com.cter.entity.SysMenu;
import com.cter.util.CommonUtil;
import com.cter.util.DBUtils;
import com.cter.util.LayuiPage;


@Repository("SysMenuDaoImpl")
@SuppressWarnings("all")
public class SysMenuDaoImpl extends BaseDaOImpl<SysMenu > {
	

	public List<SysMenu> loadMenu() {
	    DBUtils db =  DBUtils.getDBUtils();
	    List<SysMenu>  menuList=new ArrayList<SysMenu>();
        String sql = "select menu_id,menu_name,menu_path,parent_m_id,status,m_id,parent_order_num,child_order_num"
        		+ " from sys_menu  order by  parent_order_num  asc,  child_order_num  asc ";
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
	
	
	
	public  List<MenuBean>   loadMenus(String username,String password) {
		DBUtils db = DBUtils.getDBUtils();
	    List<MenuBean> list=new ArrayList<MenuBean>();
	    String sql="select a.*, count(distinct a.menu_id) as cnull from "
	    		+ "( select m.`child_order_num`, m.`parent_order_num`, m.`menu_id`, r.`role_name`, rm.`role_id` , m.`m_id`, u.`user_name`, m.`menu_name`, m.`menu_path`, m.`parent_m_id` , r.`status` as 'r_status', m.`status` as 'm_status', rm.`status` as 'rm_status', u.`status` as 'u_status' "
	    		+ "from sys_menu m inner join sys_role_menu rm inner join sys_role r inner join sys_user u inner join sys_user_role ur on (r.`role_id` = rm.`role_id` and r.`status` = '0' and rm.`status` = '0' and u.`status` = '0' and m.`status` = '0' and rm.`menu_id` = m.`menu_id` and r.`role_id` = ur.`role_id` and u.`user_id` = ur.`user_id`) ) a "
	    		+ "where a.`user_name` =  ?  group by a.menu_id order by a.`parent_order_num` asc, a.`child_order_num` asc"; 
        List<Object> params=new ArrayList<Object>();
        params.add(username);
        try {
        	list= db.executeQueryByRef(sql, params, MenuBean.class);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            db.closeDB();
        }
        if(list!=null && list.size()>0){
       return  	list;
        }
		return null;
	}
	
	public LayuiPage<SysMenu>  findSysMenuPage  ( Map<String, String>   map ,LayuiPage<SysMenu>  layui ){
		  DBUtils db =  DBUtils.getDBUtils();
		String 	format="yyyy-MM-dd HH:mm:ss";
		 String sql="select case m.parent_m_id when 0 then m.menu_name else '' end as 'menu_level1', case m.parent_m_id when 0 then '' else m.menu_name end as 'menu_level2', "
		 		+ "case m.parent_m_id when 0 then m.menu_id else(select menu_id from sys_menu where m_id=m.parent_m_id) end as 'belong_menu_id',  " + 
		 		"m.menu_id, m.m_id, m.menu_name, m.menu_path, m.parent_m_id, m.`child_order_num`,m.`parent_order_num`, m.status " + 
		 		" from sys_menu m where 1 = 1  ";
		 	 
		 int page=  Integer.valueOf(map.get("page"));
		 int limit=  Integer.valueOf(map.get("limit"));
		 String menu_name=  map.get("menu_name");
	      List<Object> params=new ArrayList<Object>();
		 if(null!=menu_name&&!menu_name.equals("")){
			 params.add(menu_name);
			 sql+=" and menu_name like concat('%',?,'%')  ";
		 }
		int count=0;
		sql+= "order by m.parent_order_num asc, m.`child_order_num` asc";
		 List<SysMenu> List=null;
		try {
			count=db.findCount(sql, params);
			List = db.loadPage(sql, params, SysMenu.class, page, limit);
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
	 * 根据城市名称加载城市popName  List 列表
	 * @param cityName
	 * @return
	 */
	public  	List<String>   loadPopNames(String cityName){
			List<String>  retList=new ArrayList<String>();
		    DBUtils db =  DBUtils.getDBUtils();
	        String sql = "select pop_name from empower_message where 1=1 ";
	        List<Object> params=new ArrayList<Object>();
	        if(null!=cityName){
	        	sql+=" and city_name =  ?";
	        	params.add(cityName);
	        }
	        try {
	        	List<Map<String, Object>>   list= (List<Map<String, Object>>)db.executeQuery(sql, params);
	        	for(Map<String, Object> o :list) {
	        		String pop_name=	o.get("pop_name").toString();
	        		retList.add(pop_name);
				}
	        	
	        } catch (Exception e) {
	            e.printStackTrace();
	        }finally{
	            db.closeDB();
	        }
        return retList;

		}
		public List <SysMenu> loadMenuNames(){
			List<String>  retList=new ArrayList<String>();
		    DBUtils db =  DBUtils.getDBUtils();
	        String sql = "select  distinct(menu_name),menu_id,m_id from sys_menu  where parent_m_id = '0' ";
	        List<SysMenu> list=null;
	        try {
	        	 list=db.executeQueryByRef(sql, null, SysMenu.class);
	        	    return list;
	        } catch (Exception e) {
	            e.printStackTrace();
	        }finally{
	            db.closeDB();
	        }
	    return null;
		}
		
		public int queryMaxMenuId( ) {
			 DBUtils db =  DBUtils.getDBUtils();
			String sql="select max(menu_id) as max from sys_menu";
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
		
		public int queryMaxMId( ) {
			 DBUtils db =  DBUtils.getDBUtils();
			String sql="select max(m_id) as max from sys_menu";
			int i=0;
			  try {
				  Object o= db.executeQuery(sql, null).get(0).get("max");
				  if( o!=null )
				i=  Integer.valueOf(o.toString());
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
		        db.closeDB();	
			}
			return i ;
		}
		
		/**
		 * 根据menu_id 获取最对应的上级目录id和最大m_id
		 * @return
		 */
		public Map<String, String> getMaxIdPIdByMenuId(int  menu_id ) {
			 DBUtils db =  DBUtils.getDBUtils();
			 Map<String, String> map =new HashMap<String, String>();
			String sql="select ifnull(max(a.m_id),b.m_id) as 'm_id' ,b.m_id as 'parent_m_id' from  (select m_id,parent_m_id  from sys_menu where menu_id = ? ) b ,  sys_menu a " + 
					"where  a.parent_m_id=b.m_id ";
			int i=0;
			List <Object>  params=new ArrayList<Object>();
			params.add(menu_id);
			  try {
				  List<Map<String, Object>> list= db.executeQuery(sql, params);
				  map.put("m_id",list.get(0).get("m_id").toString()  )   ;
				  map.put("parent_m_id", list.get(0).get("parent_m_id").toString()  )   ;
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
		        db.closeDB();	
			}
			return map ;
		}
		
		public SysMenu getSysMenu(String menu_id){
				String hql="from SysMenu where menu_id=?";
				List<Object> param=new ArrayList<Object>() ;
				param.add(Integer.valueOf(menu_id));
				 List<SysMenu> menus=this.find(hql, param);
				return CommonUtil.objectListGetOne(menus);
		}
		
		public List<SysMenu> loadSysMenuBYMId(String m_id){
			String hql="from SysMenu where parent_m_id=?";
			List<Object> param=new ArrayList<Object>() ;
			param.add(Integer.valueOf(m_id));
			 List<SysMenu> menus=this.find(hql, param);
			return CommonUtil.objectListGetList(menus);
	}
		


		public int delSysMenuByMenuId(String menu_id) {
			 DBUtils db =  DBUtils.getDBUtils();
				String delMenu="delete from sys_menu  where  menu_id= ? ";
				List<Object>  params=new ArrayList<Object>();
				params.add(Integer.valueOf(menu_id));
				int i=0;
				  try {
					 i= db.executeUpdate(delMenu, params);
					 if( i>0){
						 return i;
					 }
				} catch (SQLException e) {
					e.printStackTrace();
				}finally{
			        db.closeDB();	
				}
			return 0;
		}

		
		public int delSysMenuByMId(String m_id) {
			 DBUtils db =  DBUtils.getDBUtils();
				String delMenu="delete from sys_menu  where  m_id= ?  or parent_m_id =  ?";
				String delRoleMenu="delete from sys_role_menu where menu_id  in (select menu_id from    sys_menu  where  m_id= ?  or parent_m_id=?) "  ;
				List<Object>  params=new ArrayList<Object>();
				params.add(Integer.valueOf(m_id));
				params.add(Integer.valueOf(m_id));
				int i=0;
				  try {
					 i= db.executeUpdate(delMenu, params);
					 db.executeUpdate(delRoleMenu, params);
					 if( i>0){
						 return i;
					 }
				} catch (SQLException e) {
					e.printStackTrace();
				}finally{
			        db.closeDB();	
				}
			return 0;
		}
		
		public int updateParentOrderNum(int old_parent_order_num ,int  new_parent_order_num) {
			 DBUtils db =  DBUtils.getDBUtils();
				String delMenu="update sys_menu a set a.`parent_order_num` = case a.`parent_order_num` "
						+ "when ? then ?  when ?  then  ?  else a.`parent_order_num` end where 1 = 1";
				List<Object>  params=new ArrayList<Object>();
				params.add(old_parent_order_num);
				params.add(new_parent_order_num);
				params.add(new_parent_order_num);
				params.add(old_parent_order_num);
				int i=0;
				  try {
					 i= db.executeUpdate(delMenu, params);
					 if( i>0){
						 return i;
					 }
				} catch (SQLException e) {
					e.printStackTrace();
				}finally{
			        db.closeDB();	
				}
			return 0;
		}
		
		
		
		
		
		
		
}
