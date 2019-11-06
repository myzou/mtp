package com.cter.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.cter.bean.MenuBean;
import com.cter.entity.AuthorizationEmail;
import com.cter.entity.SysMenu;
import com.cter.service.impl.ZQMenuService;
import com.cter.util.BaseLog;
import com.cter.util.CommonUtil;
import com.cter.util.HttpDataManageUtil;
import com.cter.util.LayuiPage;
import com.opensymphony.xwork2.ActionSupport;

@Controller
public class ZQMenuAction extends ActionSupport {

    private static final long serialVersionUID = -566368986215919922L;
    private BaseLog log = new BaseLog(this.getClass().getName().replaceAll(".*\\.", ""));

    @Autowired
    private ZQMenuService zqMenuService;

    /**
     * 加载所有的菜单
     *
     * @return
     * @
     */
    public void loadMenu() {
//		List<SysMenu> sysMenus=	zqMenuService.loadMenu();
        HttpSession session = ServletActionContext.getRequest().getSession();
        List<SysMenu> sysMenus = zqMenuService.loadMenu();
        HttpDataManageUtil.retJson(sysMenus, log);
    }


    /**
     * 根据用户名加载所有的菜单
     *
     * @return
     * @
     */
    public void loadMenuByUserName() {
//		List<SysMenu> sysMenus=	zqMenuService.loadMenu();
        HttpSession session = ServletActionContext.getRequest().getSession();
        String username = (String) session.getAttribute("login_user");
        List<MenuBean> sysMenus = zqMenuService.loadMenus(username, "admin");
        HttpDataManageUtil.retJson(sysMenus, log);
    }


    /**
     * 加载所有的菜单
     *
     * @return
     * @
     */
    public void sysMenuList() {
        HttpServletRequest request = ServletActionContext.getRequest();
        LayuiPage<SysMenu> layui = new LayuiPage<SysMenu>();
        Map<String, String> map = HttpDataManageUtil.request2MapAllString(request, log);
        zqMenuService.findSysMenuPage(map, layui);
        HttpDataManageUtil.layuiPagination(layui.getCountSize(), layui.getDatas(), log);
    }

    /**
     * 加载菜单名称
     *
     * @return
     * @
     */
    public void loadMenuNames() {
        HttpServletRequest request = ServletActionContext.getRequest();
        List<SysMenu> list = zqMenuService.loadMenuNames();
        HttpDataManageUtil.retJson(list, log);
    }


    /**
     * 添加菜单
     *
     * @return
     * @
     */
    public void addSysMenu() {
        HttpServletRequest request = ServletActionContext.getRequest();
        Map<String, String> map = HttpDataManageUtil.request2Map(request, "jsonStr");
        int i = zqMenuService.addSysMenu(map);
        HttpDataManageUtil.retJSON(true, log);
    }


    /**
     * 根据menu_id获取SysMenu
     *
     * @return
     * @
     */
    public void getSysMenu() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String menu_id = request.getParameter("menu_id");
        SysMenu menu = zqMenuService.getSysMenu(menu_id);
        HttpDataManageUtil.retJSON(menu, log);
    }


    /**
     * 根据m_id获取List<SysMenu>
     *
     * @return
     * @
     */
    public void loadSysMenuBYMId() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String m_id = request.getParameter("m_id");
        List<SysMenu> menus = zqMenuService.loadSysMenuBYMId(m_id);
        if (!CommonUtil.listIsBank(menus)) {
            HttpDataManageUtil.retJson(menus, log);
        } else {
            HttpDataManageUtil.retJson(false, log);
        }
    }

    /**
     * 更新菜单
     *
     * @return
     * @
     */
    public void updateSysMenu() {
        HttpServletRequest request = ServletActionContext.getRequest();
        Map<String, String> map = HttpDataManageUtil.request2Map(request, "jsonStr");
        int i = zqMenuService.updateSysMenu(map);
        HttpDataManageUtil.retJson(true, log);
    }

    /**
     * 物理删除菜单
     *
     * @return
     * @
     */
    public void delSysMenuByMenuId() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String menu_id = request.getParameter("menu_id");
        String m_id = request.getParameter("m_id");
        int i = zqMenuService.delSysMenuByMenuId(menu_id, m_id);
        HttpDataManageUtil.retJson(true, log);
    }
}
