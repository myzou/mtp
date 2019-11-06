package com.cter.filter;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * ���ڼ���û��Ƿ��½�Ĺ����������δ��¼�����ض���ָ�ĵ�¼ҳ�� ���ò��� checkSessionKey ������� Session �б���Ĺؼ���
 * redirectURL ����û�δ��¼�����ض���ָ����ҳ�棬URL������ ContextPath notCheckURLList
 * ��������URL�б��Էֺŷֿ������� URL �в����� ContextPath
 */
public class SessionCheckFilter implements Filter {
	protected FilterConfig filterConfig = null;
	private String redirectURL = null;
	private Set<String> notCheckURLList = new HashSet<String>();
	private String sessionKey = null;
	private String login_user=null;

	@Override
	public void destroy() {
		notCheckURLList.clear();
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		HttpSession session = request.getSession();
		System.out.println(request.getRequestURL());
		if (login_user != null) {
			filterChain.doFilter(request, response);
			return;
		}
		//���ٲ�����jsp��Ҳû�е�¼���û�
		if (session.getAttribute("login_user") == null&&(!checkRequestURIIntNotFilterList(request))  ) {
			response.sendRedirect(request.getContextPath() + redirectURL+"?referrer="+request.getRequestURL());
			return;
		}
		filterChain.doFilter(servletRequest, servletResponse);
	}

	private boolean checkRequestURIIntNotFilterList(HttpServletRequest request) {
		String uri = request.getServletPath() + (request.getPathInfo() == null ? "" : request.getPathInfo());
//		System.out.println( request.getServletPath() + (request.getPathInfo() == null ? "" : request.getPathInfo()));
		String temp = request.getRequestURI();
		temp = temp.substring(request.getContextPath().length() + 1);
//		 System.out.println("�Ƿ������"+uri+";"+notCheckURLList+"=="+notCheckURLList.contains(uri));
		//20190402 ���Ӱ��� /mtp/�������  htmlҳ��Ϊ������
		if(uri.startsWith("/mtp/")||notCheckURLList.contains(uri)){
			return true;
		}else {
			return false;
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		login_user= filterConfig.getInitParameter("login_user");
		redirectURL = filterConfig.getInitParameter("redirectURL");
		sessionKey = filterConfig.getInitParameter("checkSessionKey");
		String notCheckURLListStr = filterConfig.getInitParameter("notCheckURLList");
		if (notCheckURLListStr != null) {
			System.out.println(notCheckURLListStr);
			String[]  params= notCheckURLListStr.split(",");
			for (int i = 0; i < params.length; i++) {
				notCheckURLList.add(params[i].trim());
			}
		}
	}

}