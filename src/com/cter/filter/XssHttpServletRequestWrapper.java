package com.cter.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
 
/**
 *  xss过滤
 * @author op1768
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {
    HttpServletRequest orgRequest = null;
 
    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
        orgRequest = request;
    }
 
    /**
    * 覆盖getParameter方法，将参数名和参数值都做xss过滤。
    * 如果需要获得原始的值，则通过super.getParameterValues(name)来获取
    * getParameterNames,getParameterValues和getParameterMap也可能需要覆盖
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
    * 覆盖getHeader方法，将参数名和参数值都做xss过滤。
    * 如果需要获得原始的值，则通过super.getHeaders(name)来获取
    * getHeaderNames 也可能需要覆盖
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
    * 将容易引起xss漏洞的半角字符直接替换成全角字符
    *
    * @param s
    * @return
    */
    private static String xssEncode(String value) {
    	if (value == null || "".equals(value)) {
            return value;
        }
        value = value.replaceAll("<", "＜");
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
    * 获取最原始的request
    *
    * @return
    */
    public HttpServletRequest getOrgRequest() {
        return orgRequest;
    }
 
    /**
    * 获取最原始的request的静态方法
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
