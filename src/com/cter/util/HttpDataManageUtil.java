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
 * 处理请求和发送参数工具类
 * @author op1768
 *
 */
public class HttpDataManageUtil {
	
			private static final   String  dateFormat ="yyyy-MM-dd HH:mm:ss";
			/**
			 * 根据请求(参数名称)获取参数到map
			 * @param request
			 * @param jsonStrName	参数名称
			 * @return
			 */
			public static Map<String,String >  request2Map(HttpServletRequest request,String jsonStrName ){
				String jsonStr=	request.getParameter(jsonStrName);
				JSONObject jsonObject=	JSONObject.fromObject(jsonStr);
				 Map<String, String> map = new HashMap<String, String>();
				    Iterator ite = jsonObject.keys();
				    // 遍历jsonObject数据,添加到Map对象
				    while (ite.hasNext()) {
				        String key = ite.next().toString();
				        String value = jsonObject.get(key).toString();
				        map.put(key, value);
				    }
				    //遍历
				    Iterator<Entry<String, String>>  iterator= map.entrySet().iterator();
				    while(iterator.hasNext()){
				    	 Entry<String, String> entry=iterator.next();
				    	 System.out.println("键："+entry.getKey()+"\t值："+entry.getValue());
				    }
				 return map;
			}
			
			/**
			 * 根据参数名称获取JSON对象
			 * @param request
			 * @param jsonStrName	参数名称
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
			 * 使用 Gson
			 * 根据参数名称获取JSON对象 
			 * @param request
			 * @param jsonStrName	参数名称
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
			 * String键值对获取
			 * 根据请求获取参数到map（全string）
			 * @param request
			 * @return
			 * @throws UnsupportedEncodingException 
			 */
			public static Map<String,String  >  request2MapAllString(HttpServletRequest request,BaseLog log){
				Enumeration<String>  enumeration =	request.getParameterNames();
				Map<String,String  > map =new HashMap<String,String  >();
				    //遍历
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
				    	 System.out.println("键："+name+"\t值："+value);
				    }
				 return map;
			}
			
			
			/**
			 * 返回到界面的JSON数组
			 * @param o 返回的数据对象
			 * @throws IOException
			 */
			public static void 	retJson(Object o,BaseLog log)  {
				 HttpServletResponse response=ServletActionContext.getResponse();
				 //重写方法配置，实现日期转换为字符串
				 JSONArray jsonList=JSONArray.fromObject(o,getJsonConfig());
				 String result =JSONArray.fromObject(jsonList).toString();
		        response.setContentType("text/json; charset=utf-8");
		        response.setHeader("Cache-Control", "no-cache"); //设置头信息
		        PrintWriter out=null;
				try {
					out = response.getWriter(); 
					log.info("返回界面数据："+result);
			        out.print(result);
			        out.flush();
			        out.close();
				} catch (IOException e) {
					log.printStackTrace(e);
				}
			
			}
			
			/**
			 * 返回给界面普通的JSON对象
			 * @param o 返回的数据对象
			 * @throws IOException
			 */
			public static void 	retJSON(Object o,BaseLog log ) {
				 HttpServletResponse response=ServletActionContext.getResponse();
				 //重写方法配置，实现日期转换为字符串
				 JSONObject result =JSONObject.fromObject(o,getJsonConfig());
		        response.setContentType("text/json; charset=utf-8");
		        response.setHeader("Cache-Control", "no-cache"); //设置头信息
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
			 * 返回给界面 text 文本
			 * @param str 返回的数据字符串
			 * @throws IOException
			 */
			public static void 	retString(String str,BaseLog log ) {
				 HttpServletResponse response=ServletActionContext.getResponse();
				 //重写方法配置，实现日期转换为字符串
		        response.setContentType("text/json; charset=utf-8");
		        response.setHeader("Cache-Control", "no-cache"); //设置头信息
		        PrintWriter out=null;
				try {
					out = response.getWriter();
					   log.info("返回到界面的参数："+str);
				        out.print(str);
				        out.flush();
				        out.close();
				} catch (IOException e) {
					log.printStackTrace(e);
				}
		     
			}
			
			
			
			/**
			 * 设置返回的jsonConfig 
			 * 把对象中的日期转换为字符串
			 */
			public static JsonConfig  getJsonConfig(){
			     JsonConfig jsonConfig = new JsonConfig();
			        jsonConfig.registerJsonValueProcessor(Date.class , new JsonDateValueProcessor(dateFormat));
					return jsonConfig;
			}
			
			/**
			 * 把要设置的值设置进去值栈
			 * @throws IOException
			 */
			public static void setValueStack(JSONObject jsonObject,BaseLog log) {
//				ValueStack  valueStack=ActionContext.getContext().getValueStack();//获取返回的值栈
//				valueStack.set("jsonData",jsonObject);//设置返回的值栈
				 HttpServletResponse response=ServletActionContext.getResponse();
				 String result =jsonObject.toString();
		        response.setContentType("text/json; charset=utf-8");
		        response.setHeader("Cache-Control", "no-cache"); //设置头信息
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
			 * 原值设置
			 * @throws IOException
			 */
			public static void setValueToV(Object o,BaseLog log)throws IOException{
//				ValueStack  valueStack=ActionContext.getContext().getValueStack();//获取返回的值栈
//				valueStack.set("jsonData",jsonObject);//设置返回的值栈
				 HttpServletResponse response=ServletActionContext.getResponse();
		        response.setContentType("text/json; charset=utf-8");
		        response.setHeader("Cache-Control", "no-cache"); //设置头信息
		        PrintWriter out = response.getWriter();
		        log.info(o.toString());
		        out.print(o);
		        out.flush();
		        out.close();
			}
			
			/**
			 * 分页通用处理
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
