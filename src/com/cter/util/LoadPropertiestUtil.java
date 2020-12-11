package com.cter.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

/**
 * 加载properties工具类
 * @author op1768
 *
 */
public class LoadPropertiestUtil {

	/**
	 * 根据文件路径加载参数到map
	 * @param filePath  配置文件的路径
	 * @return
	 */
		public static   Map<String,String>   loadProperties(String filePath){
			Map <String ,String> map =new HashMap<String, String>();
			Properties pro =new Properties();
			try {
				String charsetName= "UTF-8";
				InputStream ins=LoadPropertiestUtil.class.getClassLoader().getResourceAsStream(filePath);//获取输入流
				InputStreamReader in=	new InputStreamReader(ins, charsetName);//使用InputStreamReader 改变字符编码
				pro.load(in);  
				Iterator<String> iterator=pro.stringPropertyNames().iterator();
				while (iterator.hasNext()){
					String key= iterator.next();	
					String value=(String)pro.get(key);
					map.put(key,value);
				}
				Iterator<Entry<String, String>> iterator1=map.entrySet().iterator();
				while(iterator1.hasNext()){
					 Entry<String, String> entry=iterator1.next();
					 System.out.println("键："+ entry.getKey() +"\t\t值:"+entry.getValue());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return map;
		}
		
		/**
		 * 获取配置文件的内容
		 * @param filePath   例如： config/db.properties
		 * @param charsetName  字符编码:UTF-8  GBK
		 * @return
		 */
		public static   Map<String,String>   loadProperties(String filePath,String charsetName){
			Map <String ,String> map =new HashMap<String, String>();
			Properties pro =new Properties();
			try {
				InputStream ins=CopyUpdateOfSvn.class  .getClassLoader().getResourceAsStream(filePath);//获取输入流
				InputStreamReader in=	new InputStreamReader(ins, charsetName);//使用InputStreamReader 改变字符编码
				pro.load(in);  
				Iterator<String> iterator=pro.stringPropertyNames().iterator();
				while (iterator.hasNext()){
					String key= iterator.next();	
					String value=(String)pro.get(key);
					map.put(key,value);
				}
				Iterator<Entry<String, String>> iterator1=map.entrySet().iterator();
				while(iterator1.hasNext()){
					 Entry<String, String> entry=iterator1.next();
					 System.out.println("键："+ entry.getKey() +"\t\t值:"+entry.getValue());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			return map;
		}
		
		/*public static void main(String[] args) {
			loadProperties("log4j.properties");
		}*/
}
