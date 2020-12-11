package com.cter.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.cter.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cter.bean.MenuBean;
import com.cter.dao.impl.SysMenuDaoImpl;
import com.cter.entity.AuthorizationEmail;
import com.cter.entity.SysMenu;
import com.cter.util.LayuiPage;
import com.cter.util.StringUtil;


@Service("ZQMenuService")
public class ZQMenuService {

	@Autowired
	private SysMenuDaoImpl menuDaoImpl;
	
	public List<SysMenu> loadMenu() {
		return menuDaoImpl.loadMenu();
	}
	
	
	public List<MenuBean> loadMenus(String username,String password) {
		List<User> list=new LinkedList<>();
		return menuDaoImpl.loadMenus(username,password);
	}
	
	
	public  LayuiPage<SysMenu>  findSysMenuPage ( Map<String, String> map,LayuiPage<SysMenu>  layui  ){
		menuDaoImpl.findSysMenuPage (map, layui);
		return 	layui;
	}


	public List<SysMenu> loadMenuNames() {
		return  menuDaoImpl.loadMenuNames();
	}


	public int addSysMenu(Map<String, String> map) {
		String menu_name1=map.get("menu_name1");
		String menu_name2=map.get("menu_name2");
		String menu_path=map.get("menu_path");
		String status=map.get("status");
		String child_order_num=map.get("child_order_num");
		String parent_order_num=map.get("parent_order_num");
		int  maxMenuId=menuDaoImpl.queryMaxMenuId();
		int maxMId=menuDaoImpl.queryMaxMId();

		if(!StringUtil.isBlank(menu_name2)){//二级菜单不为空，添加二级菜单
			SysMenu sysMenu=new SysMenu();
			Map<String, String>  vMap=menuDaoImpl.getMaxIdPIdByMenuId(Integer.valueOf(menu_name1));
			SysMenu pSysMenu=menuDaoImpl.getSysMenu(menu_name1);
			sysMenu.setMenu_id(maxMenuId+1);
			sysMenu.setM_id(Integer.valueOf(vMap.get("m_id").toString())+1);
			sysMenu.setMenu_name(menu_name2);
			sysMenu.setMenu_path(menu_path);
			sysMenu.setParent_m_id(Integer.valueOf(vMap.get("parent_m_id")));
			sysMenu.setParent_order_num(pSysMenu.getParent_order_num());
			sysMenu.setChild_order_num(Integer.valueOf(child_order_num));
			sysMenu.setStatus(status);
			menuDaoImpl.save(sysMenu);
		}else{// 添加一级菜单
			SysMenu sysMenu=new SysMenu();
			sysMenu.setMenu_id(maxMenuId+1);
			sysMenu.setM_id((maxMId/1000+1)*1000);
			sysMenu.setMenu_name(menu_name1);
			sysMenu.setMenu_path(menu_path);
			sysMenu.setParent_m_id(0);
			sysMenu.setParent_order_num(Integer.valueOf(parent_order_num));
			sysMenu.setChild_order_num(0);
			sysMenu.setStatus(status);
			menuDaoImpl.save(sysMenu);
		}
		return 0;
	}
	
	


	public SysMenu getSysMenu(String menu_id) {
		return menuDaoImpl.getSysMenu(menu_id);
	}

	public List<SysMenu> loadSysMenuBYMId(String m_id) {
		return menuDaoImpl.loadSysMenuBYMId(m_id);
	}

	public int updateSysMenu(Map<String, String> map) {
		String menu_name1=map.get("menu_name1");
		String menu_name2=map.get("menu_name2");
		String menu_path=map.get("menu_path");
		String status=map.get("status");
		String menu_id=map.get("menu_id");
		String bolong_menu_id=map.get("bolong_menu_id");
		String child_order_num=map.get("child_order_num");
		String parent_order_num=map.get("parent_order_num");


		if(!StringUtil.isBlank(menu_name2)){//二级菜单不为空，修改二级菜单
			Map<String, String>  vMap=menuDaoImpl.getMaxIdPIdByMenuId(Integer.valueOf(menu_name1));
			SysMenu sysMenu=menuDaoImpl.getSysMenu(menu_id);
			SysMenu pPysMenu=menuDaoImpl.getSysMenu(bolong_menu_id);
			sysMenu.setMenu_name(menu_name2);
			sysMenu.setMenu_path(menu_path);
			sysMenu.setChild_order_num(Integer.valueOf(child_order_num));
			sysMenu.setStatus(status);
			
			if( pPysMenu.getM_id() !=sysMenu.getParent_m_id()){//一级菜单不相等就更改
				sysMenu.setM_id(Integer.valueOf(vMap.get("m_id"))+1);
				sysMenu.setParent_m_id(pPysMenu.getM_id() );
				sysMenu.setParent_order_num(pPysMenu.getParent_order_num());
			}
			
			menuDaoImpl.update(sysMenu);
		}else{// 修改一级菜单
			
			SysMenu pPysMenu=menuDaoImpl.getSysMenu(menu_id);
			int old_parent_order_num=pPysMenu.getParent_order_num();
			int new_parent_order_num=Integer.valueOf(parent_order_num);
			pPysMenu.setMenu_name(menu_name1);
			pPysMenu.setMenu_path(menu_path);
			pPysMenu.setParent_order_num(new_parent_order_num);
			pPysMenu.setStatus(status);
			menuDaoImpl.update(pPysMenu);
			menuDaoImpl.updateParentOrderNum(old_parent_order_num,new_parent_order_num);
			
		}
		return 0;
	}

	/**
	 * @param menu_id 菜单主键id
	 * @param m_id         菜单m_id
	 * @return
	 */
	public int delSysMenuByMenuId(String menu_id,String m_id) {
		if(!StringUtil.isBlank(m_id)){//删除一级菜单
			return 	 menuDaoImpl.delSysMenuByMId(m_id);
		} 
		//删除二级菜单
		return 	 menuDaoImpl.delSysMenuByMenuId(menu_id);
		 
	}
 
			 
			 
}
