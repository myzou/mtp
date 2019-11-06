package com.cter.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

public class JsonUtil {
	
	/**
	 * 
	 * @param jsonString
	 * @param pojoClass 
	 * @return
	 */
	public static <T> T jsonToObject(String jsonString,Class<T> pojoClass	,Map<String, Object> map){
		try {
			Object pojo;
			JSONObject jsonObject=JSONObject.fromObject(jsonString);
			if(map!=null){
				pojo=JSONObject.toBean(jsonObject, pojoClass);
			}
			pojo=JSONObject.toBean(jsonObject, pojoClass);

			return (T)pojo;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
}
