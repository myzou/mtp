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
import org.apache.poi.ss.formula.functions.T;
import org.apache.struts2.StrutsConstants;
import org.springframework.util.StringUtils;

/**
 * 简单的工具类
 *
 * @author op1768
 */
public class CommonUtil {

    /**
     * 获取list对象第一个元素
     *
     * @param list
     * @return
     */
    public static <T> T objectListGetOne(List<T> list) {
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    public static void main(String[] args) {
        String initStr = "op1768@CNCHZTJN1001E> show interfaces ge-1/0/0.50 \n" +
                "  Logical interface ge-1/0/0.50 (Index 191) (SNMP ifIndex 977)\n" +
                "    Description: Trunk 300M to CNSUZCYY1004E (ge-0/1/3.50) via CU (SUZ-CHZ NE0023NP) (2019/07/10 by Peng Xiaochun)\n" +
                "    Flags: SNMP-Traps 0x4000 VLAN-Tag [ 0x8100.50 ]  Encapsulation: ENET2\n" +
                "    Input packets : 75684812367 \n" +
                "    Output packets: 84189694769\n" +
                "    Protocol inet, MTU: 9170\n" +
                "      Flags: Sendbcast-pkt-to-re, User-MTU\n" +
                "      Addresses, Flags: Is-Preferred Is-Primary\n" +
                "        Destination: 218.96.225.80/31, Local: 218.96.225.80\n" +
                "    Protocol iso, MTU: 1497\n" +
                "      Flags: Is-Primary, User-MTU\n" +
                "    Protocol mpls, MTU: 9150, Maximum labels: 5\n" +
                "      Flags: Is-Primary, User-MTU\n" +
                "    Protocol multiservice, MTU: Unlimited";
        String prefix = " to ";
        String suffix = " ";
        System.out.println(subByStringAndString(initStr, prefix, suffix));

    }


    /**
     * 截取一个字符串中从什么开始到什么结束的中间字符串
     * subByStringAndString("abcdefg","b","f") > cde
     *
     * @param initStr 初始字符串
     * @param prefix  前缀
     * @param suffix  后缀
     * @return
     */
    public static String subByStringAndString(String initStr, String prefix, String suffix) {
        if (StringUtil.isBlank(initStr)) {
            return "";
        }
        String returnStr = "";
        try {
            if(initStr.indexOf(prefix)==-1){
                return "error:prefix("+prefix+") String not found ";
            }
            if(initStr.indexOf(suffix)==-1){
                return "error:suffix("+suffix+") String not found ";
            }
            if (!StringUtils.isEmpty(prefix)) {
                String tempStr = initStr.substring(initStr.indexOf(prefix) + prefix.length(), initStr.length());
                if (!StringUtils.isEmpty(suffix)) {
                    returnStr = tempStr.substring(0, tempStr.indexOf(suffix));
                    return returnStr;
                } else {
                    returnStr = tempStr;
                    return returnStr;
                }

            } else {
                if (!StringUtils.isEmpty(suffix)) {
                    returnStr = initStr.substring(0, initStr.indexOf(suffix));
                    return returnStr;
                } else {
                    return initStr;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "error:String cut error";

        }

    }



    /**
     * 根据list 判断然后
     * 返回list 或者null
     *
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
     * 根据list 判断然后
     * list 不为空,大小大于0，返回true
     * 否则返回false
     *
     * @param list
     * @return
     */
    public static boolean listIsBank(List<?> list) {
        if (list == null) {
            return true;
        }
        if (list.size() == 0) {
            return true;
        }
        return false;
    }

    /**
     * 把数组对象转换String字符串
     *
     * @param stringArray
     * @return
     */
    public static String toString(Object[] stringArray) {
        if (stringArray == null || stringArray.length == 0) {
            return "";
        }
        int iMax = stringArray.length - 1;
        if (iMax == -1) {
            return "[]";
        }
        StringBuffer bf = new StringBuffer();
        bf.append('[');
        for (int i = 0; ; i++) {
            bf.append("\"");
            bf.append(String.valueOf(stringArray[i]));
            bf.append("\"");
            if (iMax == i) {
                bf.append("]");
                return bf.toString();
            }
            bf.append(",");
        }
    }

    /**
     * 把简单的json对象字符串转换为map
     *
     * @param jsonStr
     * @return 简单字符串Map<String, String>
     */
    public static Map<String, String> simpleJson2Map(String jsonStr) {
        Map<String, String> map = new HashMap<String, String>();
        JSONObject jsonObject = JSONObject.fromObject(jsonStr);
        Iterator<Object> iterator = jsonObject.keys();
        while (iterator.hasNext()) {
            String key = iterator.next().toString();
            String value = jsonObject.get(key).toString();
            map.put(key, value);
        }
        return map;
    }

    /**
     * 把复杂的json对象字符串转换为map
     *
     * @param jsonStr
     * @return 多层  Map<String ,Object >
     */
    public static Map<String, Object> complexJson2Map(String jsonStr) {
        Map<String, Object> map = new HashMap<String, Object>();
        JSONObject jsonObject = JSONObject.fromObject(jsonStr);
        Iterator<Object> iterator = jsonObject.keys();
        while (iterator.hasNext()) {
            //遍历键
            Object key = iterator.next();
            //最外层解析
            Object value = jsonObject.get(key);
            if (value instanceof JSONArray) {
                List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
                Iterator<JSONObject> vIt = ((JSONArray) value).iterator();
                while (vIt.hasNext()) {
                    JSONObject vJson = vIt.next();
                    list.add(vJson);
                }
                map.put(key.toString(), list);
            } else if (value instanceof JSONObject) {
                String aaa = ((JSONObject) value).toString();
                map.put(key.toString(), complexJson2Map(aaa));
            } else if (value instanceof String) {
                //普通String
                map.put(key.toString(), value.toString());
            } else {
                map.put(key.toString(), value);
            }

        }
        return map;
    }

    /**
     * 获取InputStream 中内容
     *
     * @param in
     * @return
     */
    public static String readStream(InputStream in) {
        try {
            //1.创建字节流数组输出流，用来输出读取到的内容
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            //2.创建缓存大小
            byte[] buffer = new byte[1024];//1kb ,1kb 有1024字节
            int len = -1;
            //3.读取输入流中的内容
            while ((len = in.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            //4 直接数组转换为字符串
            String content = baos.toString();
            //5.资源
            baos.close();
            return content;

        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    /**
     * 判断字符串是不是JSON字符串
     *
     * @param str JSON字符串
     * @return 是字符串返回true，不是返回false
     */
    public static boolean isJsonStr(String str) {
        try {
            JSONObject.fromObject(str);
        } catch (Exception e) {
            return false;
        }
        return true;

    }


}
