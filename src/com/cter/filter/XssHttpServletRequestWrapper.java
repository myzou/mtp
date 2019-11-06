package com.cter.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
 
/**
 *  xss����
 * @author op1768
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {
    HttpServletRequest orgRequest = null;
 
    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
        orgRequest = request;
    }
 
    /**
    * ����getParameter���������������Ͳ���ֵ����xss���ˡ�
    * �����Ҫ���ԭʼ��ֵ����ͨ��super.getParameterValues(name)����ȡ
    * getParameterNames,getParameterValues��getParameterMapҲ������Ҫ����
    */
    @Override
    public String getParameter(String name) {
        String value = super.getParameter(xssEncode(name));
        if (value != null) {
            value = xssEncode(value);
        }
        return value;
    }
 
    /**
    * ����getHeader���������������Ͳ���ֵ����xss���ˡ�
    * �����Ҫ���ԭʼ��ֵ����ͨ��super.getHeaders(name)����ȡ
    * getHeaderNames Ҳ������Ҫ����
    */
    @Override
    public String getHeader(String name) {
 
        String value = super.getHeader(xssEncode(name));
        if (value != null) {
            value = xssEncode(value);
        }
        return value;
    }
 
    /**
    * ����������xss©���İ���ַ�ֱ���滻��ȫ���ַ�
    *
    * @param s
    * @return
    */
    private static String xssEncode(String value) {
    	if (value == null || "".equals(value)) {
            return value;
        }
        value = value.replaceAll("<", "��");
        value = value.replaceAll("\\(", "(").replace("\\)", ")");
        value = value.replaceAll("'", "'");
        value = value.replaceAll("eval\\((.*)\\)", "");
        value = value.replaceAll("%26", "&");
        value = value.replaceAll("%24", "$");
//        value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']",
//                "\"\"");
        value = value.replace("<script>", "");
        value = value.replace("<javascript>", "");
        return value;
    }
 
    /**
    * ��ȡ��ԭʼ��request
    *
    * @return
    */
    public HttpServletRequest getOrgRequest() {
        return orgRequest;
    }
 
    /**
    * ��ȡ��ԭʼ��request�ľ�̬����
    *
    * @return
    */
    public static HttpServletRequest getOrgRequest(HttpServletRequest req) {
        if (req instanceof XssHttpServletRequestWrapper) {
            return ((XssHttpServletRequestWrapper) req).getOrgRequest();
        }
 
        return req;
    }
 
    
    
    public static void main(String[] args) {
        System.out.println(XssHttpServletRequestWrapper.xssEncode("<script>alert(1)</script>"));
    }

}
