package com.cter.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * ��������ͷ��Ͳ���������
 * @author op1768
 *
 */
public class HttpDataManageUtil {
	
			private static final   String  dateFormat ="yyyy-MM-dd HH:mm:ss";
			/**
			 * ��������(��������)��ȡ������map
			 * @param request
			 * @param jsonStrName	��������
			 * @return
			 */
			public static Map<String,String >  request2Map(HttpServletRequest request,String jsonStrName ){
				String jsonStr=	request.getParameter(jsonStrName);
				JSONObject jsonObject=	JSONObject.fromObject(jsonStr);
				 Map<String, String> map = new HashMap<String, String>();
				    Iterator ite = jsonObject.keys();
				    // ����jsonObject����,��ӵ�Map����
				    while (ite.hasNext()) {
				        String key = ite.next().toString();
				        String value = jsonObject.get(key).toString();
				        map.put(key, value);
				    }
				    //����
				    Iterator<Entry<String, String>>  iterator= map.entrySet().iterator();
				    while(iterator.hasNext()){
				    	 Entry<String, String> entry=iterator.next();
				    	 System.out.println("����"+entry.getKey()+"\tֵ��"+entry.getValue());
				    }
				 return map;
			}
			
			/**
			 * ���ݲ������ƻ�ȡJSON����
			 * @param request
			 * @param jsonStrName	��������
			 * @return
			 */
			public static Object request2Object(HttpServletRequest request,String jsonStrName,Class classType  ){
				String jsonStr=	request.getParameter(jsonStrName);
				System.out.println("jsonStr\n"+jsonStr);
				JSONObject jsonObject=	JSONObject.fromObject(jsonStr);
				Object object=	JSONObject.toBean(jsonObject, classType);
				 return object;
			}
			
			/**
			 * ʹ�� Gson
			 * ���ݲ������ƻ�ȡJSON���� 
			 * @param request
			 * @param jsonStrName	��������
			 * @return
			 */
			public static  <T> T   requestGson2Object(HttpServletRequest request,String jsonStrName,Class<T>  clazz){
				String jsonStr=	request.getParameter(jsonStrName);
				System.out.println("jsonStr\n"+jsonStr);
				Gson gs = new Gson();  	
				T  t= gs.fromJson(jsonStr, clazz);
				 return t;
			}
			
			/**
			 * String��ֵ�Ի�ȡ
			 * ���������ȡ������map��ȫstring��
			 * @param request
			 * @return
			 * @throws UnsupportedEncodingException 
			 */
			public static Map<String,String  >  request2MapAllString(HttpServletRequest request,BaseLog log){
				Enumeration<String>  enumeration =	request.getParameterNames();
				Map<String,String  > map =new HashMap<String,String  >();
				    //����
//				String str=new String(request.getParameter("menu_name").getBytes("ISO8859-1"),"UTF-8");
//				request.setCharacterEncoding("utf-8");
				String methodType=request.getMethod();
				    while(enumeration.hasMoreElements()){
				    	String  name=null;
				    	String value=null;
				    	name= enumeration.nextElement();
							try {
								if(methodType.equals("GET")){
									value= new String(request.getParameter(name).getBytes("ISO8859-1"),"UTF-8") ;
								}else  {
									value=  java.net.URLDecoder.decode(request.getParameter(name),"utf-8") ;
								}
							} catch (UnsupportedEncodingException e) {
								log.printStackTrace(e);
							}
				    	map.put(name, value);
				    	 System.out.println("����"+name+"\tֵ��"+value);
				    }
				 return map;
			}
			
			
			/**
			 * ���ص������JSON����
			 * @param o ���ص����ݶ���
			 * @throws IOException
			 */
			public static void 	retJson(Object o,BaseLog log)  {
				 HttpServletResponse response=ServletActionContext.getResponse();
				 //��д�������ã�ʵ������ת��Ϊ�ַ���
				 JSONArray jsonList=JSONArray.fromObject(o,getJsonConfig());
				 String result =JSONArray.fromObject(jsonList).toString();
		        response.setContentType("text/json; charset=utf-8");
		        response.setHeader("Cache-Control", "no-cache"); //����ͷ��Ϣ
		        PrintWriter out=null;
				try {
					out = response.getWriter(); 
					log.info("���ؽ������ݣ�"+result);
			        out.print(result);
			        out.flush();
			        out.close();
				} catch (IOException e) {
					log.printStackTrace(e);
				}
			
			}
			
			/**
			 * ���ظ�������ͨ��JSON����
			 * @param o ���ص����ݶ���
			 * @throws IOException
			 */
			public static void 	retJSON(Object o,BaseLog log ) {
				 HttpServletResponse response=ServletActionContext.getResponse();
				 //��д�������ã�ʵ������ת��Ϊ�ַ���
				 JSONObject result =JSONObject.fromObject(o,getJsonConfig());
		        response.setContentType("text/json; charset=utf-8");
		        response.setHeader("Cache-Control", "no-cache"); //����ͷ��Ϣ
		        PrintWriter out=null;
				try {
					out = response.getWriter();
					   log.info(result.toString());
				        out.print(result);
				        out.flush();
				        out.close();
				} catch (IOException e) {
					log.printStackTrace(e);
				}
		     
			}
			
			/**
			 * ���ظ����� text �ı�
			 * @param str ���ص������ַ���
			 * @throws IOException
			 */
			public static void 	retString(String str,BaseLog log ) {
				 HttpServletResponse response=ServletActionContext.getResponse();
				 //��д�������ã�ʵ������ת��Ϊ�ַ���
		        response.setContentType("text/json; charset=utf-8");
		        response.setHeader("Cache-Control", "no-cache"); //����ͷ��Ϣ
		        PrintWriter out=null;
				try {
					out = response.getWriter();
					   log.info("���ص�����Ĳ�����"+str);
				        out.print(str);
				        out.flush();
				        out.close();
				} catch (IOException e) {
					log.printStackTrace(e);
				}
		     
			}
			
			
			
			/**
			 * ���÷��ص�jsonConfig 
			 * �Ѷ����е�����ת��Ϊ�ַ���
			 */
			public static JsonConfig  getJsonConfig(){
			     JsonConfig jsonConfig = new JsonConfig();
			        jsonConfig.registerJsonValueProcessor(Date.class , new JsonDateValueProcessor(dateFormat));
					return jsonConfig;
			}
			
			/**
			 * ��Ҫ���õ�ֵ���ý�ȥֵջ
			 * @throws IOException
			 */
			public static void setValueStack(JSONObject jsonObject,BaseLog log) {
//				ValueStack  valueStack=ActionContext.getContext().getValueStack();//��ȡ���ص�ֵջ
//				valueStack.set("jsonData",jsonObject);//���÷��ص�ֵջ
				 HttpServletResponse response=ServletActionContext.getResponse();
				 String result =jsonObject.toString();
		        response.setContentType("text/json; charset=utf-8");
		        response.setHeader("Cache-Control", "no-cache"); //����ͷ��Ϣ
		        PrintWriter out=null;
		    	try {
					out = response.getWriter();
					log.info(result.toString());
			        out.print(result);
			        out.flush();
			        out.close();
				} catch (IOException e) {
					log.printStackTrace(e);
				}
			}
			
			
			/**
			 * ԭֵ����
			 * @throws IOException
			 */
			public static void setValueToV(Object o,BaseLog log)throws IOException{
//				ValueStack  valueStack=ActionContext.getContext().getValueStack();//��ȡ���ص�ֵջ
//				valueStack.set("jsonData",jsonObject);//���÷��ص�ֵջ
				 HttpServletResponse response=ServletActionContext.getResponse();
		        response.setContentType("text/json; charset=utf-8");
		        response.setHeader("Cache-Control", "no-cache"); //����ͷ��Ϣ
		        PrintWriter out = response.getWriter();
		        log.info(o.toString());
		        out.print(o);
		        out.flush();
		        out.close();
			}
			
			/**
			 * ��ҳͨ�ô���
			 * @throws Exception
			 */
			public static void layuiPagination( int count,List list,BaseLog log){
				 Map<String, Object> result = new HashMap<String, Object>();
				    result.put("code", 0);
				    result.put("msg", "");
				    result.put("count",count);
				    JSONArray array = JSONArray.fromObject(list,getJsonConfig());
				    result.put("data", array);
				    JSONObject jsonObject=  JSONObject.fromObject(result,getJsonConfig());
					setValueStack(jsonObject,log);
			}
			
}
