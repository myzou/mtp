package com.cter.util;

import java.util.UUID;

/**
 * uuidπ§æﬂ¿‡
 * @author op1768
 *Date:2018-11-11
 */
public class UuidUtil {

	public static String getUUID32(){
		
		String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
		 return uuid;
		}
	
		public static void main(String[] args) {
	    for (int i = 0; i < 1; i++) {
	     System.out.println(getUUID32());
		}

	}

 

}
