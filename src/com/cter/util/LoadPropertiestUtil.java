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
 * ����properties������
 * @author op1768
 *
 */
public class LoadPropertiestUtil {

	/**
	 * �����ļ�·�����ز�����map
	 * @param filePath  �����ļ���·��
	 * @return
	 */
		public static   Map<String,String>   loadProperties(String filePath){
			Map <String ,String> map =new HashMap<String, String>();
			Properties pro =new Properties();
			try {
				String charsetName= "UTF-8";
				InputStream ins=LoadPropertiestUtil.class.getClassLoader().getResourceAsStream(filePath);//��ȡ������
				InputStreamReader in=	new InputStreamReader(ins, charsetName);//ʹ��InputStreamReader �ı��ַ�����
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
					 System.out.println("����"+ entry.getKey() +"\t\tֵ:"+entry.getValue());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return map;
		}
		
		/**
		 * ��ȡ�����ļ�������
		 * @param filePath   ���磺 config/db.properties
		 * @param charsetName  �ַ�����:UTF-8  GBK
		 * @return
		 */
		public static   Map<String,String>   loadProperties(String filePath,String charsetName){
			Map <String ,String> map =new HashMap<String, String>();
			Properties pro =new Properties();
			try {
				InputStream ins=CopyUpdateOfSvn.class  .getClassLoader().getResourceAsStream(filePath);//��ȡ������
				InputStreamReader in=	new InputStreamReader(ins, charsetName);//ʹ��InputStreamReader �ı��ַ�����
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
					 System.out.println("����"+ entry.getKey() +"\t\tֵ:"+entry.getValue());
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
