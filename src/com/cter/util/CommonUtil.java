package com.cter.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * �򵥵Ĺ�����
 * @author op1768
 *
 */
public class CommonUtil {
	
	/**
	 * ��ȡlist�����һ��Ԫ��
	 * @param list
	 * @return
	 */
	public static <T> T objectListGetOne(List<T> list) {
			if (list != null && list.size() > 0) {
				return list.get(0);
				}
				return null;
				}
	
	/**
	 * ����list �ж�Ȼ��
	 * ����list ����null
	 * @param list
	 * @return
	 */
	public static <T> List<T> objectListGetList(List<T> list) {
		if (list != null && list.size() > 0) {
			return list;
			}
			return null;
			}
	
	/**
	 * ����list �ж�Ȼ��
	 * list ��Ϊ��,��С����0������true
	 * ���򷵻�false
	 * @param list
	 * @return
	 */
	public static boolean listIsBank(List<?> list) {
			if (list == null ) {
			return true;
			}
			if (list.size() ==0 ) {
				return true;
			}
			return false ;
			}
	
	/**
	 * ���������ת��String�ַ���
	 * @param stringArray
	 * @return
	 */
	public static String  toString(Object [] stringArray){
		if (stringArray==null||stringArray.length==0){
			return "";
		}
		int iMax =stringArray.length-1;
		if(iMax==-1){
			return "[]";
		}
		StringBuffer bf=new StringBuffer();
		bf.append('[');
		for (int i=0;;i++){
			bf.append("\"");
			bf.append(String.valueOf(stringArray[i]));
			bf.append("\"");
			if(iMax==i){
				bf.append("]");
				return bf.toString();
			}
			bf.append(",");
		}
	}
	
	/**
	 * �Ѽ򵥵�json�����ַ���ת��Ϊmap
	 * @param jsonStr
	 * @return ���ַ���Map<String ,String >
	 */
	public static Map<String ,String > simpleJson2Map(String  jsonStr){
		Map<String, String >   map=new HashMap<String,String>();
		JSONObject jsonObject=JSONObject.fromObject(jsonStr);
		Iterator <Object>   iterator=jsonObject.keys();
		while(iterator.hasNext()){
			String key=iterator.next().toString();
			String value=jsonObject.get(key).toString();
			map.put(key, value);
		}
		return map;
	}
	
	/**
	 * �Ѹ��ӵ�json�����ַ���ת��Ϊmap
	 * @param jsonStr
	 * @return  ���  Map<String ,Object >
	 */
	public static Map<String ,Object > complexJson2Map(String  jsonStr){
		Map<String, Object >   map=new HashMap<String,Object>();
		JSONObject jsonObject=JSONObject.fromObject(jsonStr);
		Iterator <Object>   iterator=jsonObject.keys();
	    while(iterator.hasNext() ){
	    	//������
	    	Object key =iterator.next();
	    	//��������
	    	Object value=jsonObject.get(key);
	    	if(value instanceof  JSONArray){
	    	List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
	    	Iterator<JSONObject>  vIt=((JSONArray)value).iterator();
	    	while(vIt.hasNext()){
	    		JSONObject vJson=vIt.next();
	    		list.add(vJson);
	    	}
	    	 map.put(key.toString(), list);
	    	}else if(value instanceof  JSONObject){
	    		String aaa=((JSONObject)value).toString();
	    		map.put(key.toString(), complexJson2Map( aaa ));
	    	}else if(value instanceof String){
	    		//��ͨString
	    		map.put(key.toString(),value.toString());
	    	}else{
	    		map.put(key.toString(),value);
	    	}
			 
		}
		return map;
	}
	
	/**
	 * ��ȡInputStream ������
	 * @param in
	 * @return
	 */
	public static String readStream(InputStream in){
		try {
			//1.�����ֽ�����������������������ȡ��������
			ByteArrayOutputStream baos=new ByteArrayOutputStream();
			//2.���������С
			byte[] buffer=new byte[1024];//1kb ,1kb ��1024�ֽ�
			int len =-1;
			//3.��ȡ�������е�����
			while((len=in.read(buffer))!=-1){
				baos.write(buffer, 0, len);
			}
			//4 ֱ������ת��Ϊ�ַ���
			String content=baos.toString();
			//5.��Դ
			baos.close();
			return content;

		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
	
	/**
	 * �ж��ַ����ǲ���JSON�ַ���
	 * @param str JSON�ַ���
	 * @return  ���ַ�������true�����Ƿ���false
	 */
	public static boolean isJsonStr(String str ){
		 try {
			JSONObject.fromObject(str);
		} catch (Exception e) {
			return false;
		}
		 return true;
		
	}
	
	

}
