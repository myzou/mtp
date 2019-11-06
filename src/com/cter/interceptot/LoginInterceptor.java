package com.cter.interceptot;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.cter.util.StringUtil;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class LoginInterceptor extends AbstractInterceptor {

    /**
     *
     */
    private static final long serialVersionUID = -8806273228376999256L;

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        ActionContext ctx = invocation.getInvocationContext();
        HttpServletRequest request = ServletActionContext.getRequest();
        String uri = request.getServletPath() + (request.getPathInfo() == null ? "" : request.getPathInfo());

        Map session = ctx.getSession();
        String login_user = (String) session.get("login_user");
        String login_user_id = (String) session.get("login_user_id");
        //���û�л�ȡ���û�����id,��Ҫ��¼
        Boolean  ignoreArrIsContain =false;
        String []   ignoreArr=new String[]{"/excelConversion","/static_html","addMtpRecordDetailed"};
        ignoreArrIsContain=StringUtil.stringIndexOfArray(ignoreArr, uri);

        //���û�л�ȡ���û�����id,��Ҫ��¼
        if(ignoreArrIsContain||!StringUtil.isBlank(login_user)&&!StringUtil.isBlank(login_user_id)){
//			System.out.println("login_user:"+login_user+",login_user_id:"+login_user_id);
            return invocation.invoke();
        }
        System.out.println(request.getRemoteAddr() + ",ip,û�е�¼����");
        ctx.put("tip", request.getRemoteAddr() + ",ip,û�е�¼����");
        return "login";
    }

}
