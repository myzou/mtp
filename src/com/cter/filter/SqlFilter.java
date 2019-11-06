package com.cter.filter;


import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cter.util.CommonUtil;
import com.cter.util.HttpDataManageUtil;

import net.sf.json.JSONObject;
public class SqlFilter implements Filter{

	 FilterConfig filterConfig = null;

	    public void init (FilterConfig filterConfig) throws ServletException {
	        this. filterConfig = filterConfig;
 
	    }

	    public void destroys () {
	        this. filterConfig = null;
	    }
	    @Override
	    public void doFilter (ServletRequest request, ServletResponse response, FilterChain chain)
	             throws IOException, ServletException {
		    	HttpServletRequest req=(HttpServletRequest)request;
		    	HttpServletResponse res=(HttpServletResponse)response;
		    	 Enumeration<String> names = req.getParameterNames();  
		    	 boolean bool=false;
		         while(names.hasMoreElements()){  
		             String name = names.nextElement();  
		             String[] values = req.getParameterValues(name);  
		             for(String value: values){
		                 //sqlע��ֱ������
		            	   bool=judgeSQLInject(value.toLowerCase());
		                 	if(bool){
//			                 	 res.setContentType("text/html;charset=UTF-8");  
//			                 	res.getWriter().print(" <script type='text/javascript'>alert('�������зǷ������ַ�,�ѽ�ֹ�������ʣ�');</script>");
//			                 	res.getWriter().close();
//			               	     chain. doFilter ( request , response);
//			     				request.getRequestDispatcher("error.jsp").forward(request, response);
			                 	System.out.println("����sqlע��{},��ֹ����,"+value);
			                 	   request.getRequestDispatcher("error2.jsp").forward(request, response);
			                 	  throw new IOException("�������зǷ������ַ�,�ѽ�ֹ�������ʣ�");
 			         }
		             }  
		         }  
		         if(!bool){
		        	 chain. doFilter ( request , response);
		         } 
	    }
	    
 

	    /** 
	     * �жϲ����Ƿ��й����� 
	     * @param value 
	     * @return 
	     */  
	    public boolean judgeSQLInject(String value){  
	        if(value == null || "".equals(value) ){  
	            return false;  
	        }  
	        
	        String xssStr = "'|exec |insert |select |delete |update | count | % |chr| mid|master |truncate | char|declare| or|+|--| and";  
            String[] xssArr = xssStr.split("\\|");  
            
	        //�ж��ǲ���json�ַ���Ȼ�����ж�
	        if(CommonUtil.isJsonStr(value)){//json����value�ж�
	        	 Map <String , String >  map= CommonUtil.simpleJson2Map(value);
	        	 for(String  key :map.keySet() ){
	        		 String val=map.get(key);
	        	     for(int i=0;i<xssArr.length;i++){  
	 		            if(val.indexOf(xssArr[i])>-1){  
	 		                return true;  
	 		            }  
	 		        	}  
	        	 }
	        }else{
		        for(int i=0;i<xssArr.length;i++){  
		            if(value.indexOf(xssArr[i])>-1){  
		                return true;  
		            }  
		        }  
	        	
	        }
	        
	   
	        return false;  
	    }
	    
		@Override
		public void destroy() {
			// TODO Auto-generated method stub
			
		}
}

