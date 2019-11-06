package com.cter.service.impl;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpException;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.cter.bean.MTPA;
import com.cter.bean.PePort;
import com.cter.rsa.RSAEncrypt;
import com.cter.util.BaseLog;
import com.cter.util.LoadPropertiestUtil;
import com.cter.util.StringUtil;
import com.cter.util.totp.Totp;
import com.google.gson.internal.LinkedTreeMap;
import org.hibernate.type.StringType;

import java.nio.charset.Charset;
import java.util.*;

/**
 * @author op1768
 * @create 2019-10-28 18:24
 * @project mtp
 */
public class Backbone {
    private static Map<String, String> otherMap = LoadPropertiestUtil.loadProperties("config/other.properties");
    private static final String mtp_project = otherMap.get("mtp_project");
    private static String GGW_URL = otherMap.get("GGW_URL");
    private static String LOGIN_GGW_URL = otherMap.get("LOGIN_GGW_URL");
    private static Map<String, String> paramMap = new HashMap<String, String>();
    private static Map<String, HashMap<String, String>> ipLastLoginTimeMap = new HashMap<>();


    static {

    }

    public void add() {


    }

    public static void main(String[] args) {
        BaseLog log=new BaseLog("MTPQueryLog");

        String opName="op1768";
        String opPassword="Abc10151015";
        String command="show interfaces descriptions %7C match trunk";
        command="show route";
        //command="11";
        command="ping interface  ge-0/0/0.102 rapid source 10.122.2.237 10.122.2.238 count 111";
        String ip="152.101.179.122";//2FA
        ip="218.96.240.81";

        HashMap<String, String> paramMap=new HashMap<String, String>();
        paramMap.put("opName", opName);
        paramMap.put("opPassword", opPassword);
        paramMap.put("sign", "123456789");
        paramMap.put("command",command);
        paramMap.put("ip", ip);

        for (int i = 1; i <= 100; i++) {
            System.out.println("==========================================================="+i+"===========================================================");
            HashMap<String, String> returnMap= execute(paramMap,log);
            System.out.println(returnMap);
        }
    }


    /**
     * 验证参数是否有问题
     */
    public static Map<String, String> validateParam(String jsonStr, BaseLog log) {
        Map<String, String> returnMap = new HashMap<>();
        returnMap.put("status", "N");
        if (StringUtil.isBlank(jsonStr)) {
            returnMap.put("msg", "jsonStr不能为空");
            return returnMap;
        }
        String tempString = "";
        try {

            MTPA mtpa = JSONUtil.toBean(jsonStr, MTPA.class);
            List<PePort> pePorts = mtpa.getPePorts();
            if (StringUtil.isBlank(mtpa.getTicketName())) {
                returnMap.put("msg", "ticketName 不能为空");
                return returnMap;
            }
            if (StringUtil.isBlank(mtpa.getTense()) || (!mtpa.getTense().equals("before") && !mtpa.getTense().equals("after"))) {
                returnMap.put("msg", "tense 不能为空且只能为before或者after");
                return returnMap;

            }
            if (null == pePorts || pePorts.size() == 0) {
                returnMap.put("msg", "pePorts 必须要有参数");
                return returnMap;
            }
            for (PePort pePort : pePorts) {
                String tcpType = pePort.getTcpType();
                if (StringUtil.isBlank(tcpType)) {
                    returnMap.put("msg", "tcpType 不能为空");
                    return returnMap;
                } else if (tcpType.equals("tcp")) {
                    if (StringUtil.isBlank(pePort.getInternalSiteId())) {
                        returnMap.put("msg", "internalSiteId 不能为空");
                        return returnMap;
                    }
                    if (StringUtil.isBlank(pePort.getPeRouter())) {
                        returnMap.put("msg", "peRouter 不能为空");
                        return returnMap;
                    }
                } else if (tcpType.equals("bacbone")) {
                    if (StringUtil.isBlank(pePort.getCircuiltNumber())) {
                        returnMap.put("msg", "CircuiltNumber 不能为空");
                        return returnMap;
                    }
                    if (StringUtil.isBlank(pePort.getPeInterface())) {
                        returnMap.put("msg", "PeInterface 不能为空");
                        return returnMap;
                    }
                }
            }
        } catch (Exception e) {
            tempString = "parameter exception,Check parameter please \n" + jsonStr;
            returnMap.put("msg", tempString);
            log.info(tempString);
            log.printStackTrace(e);
            return returnMap;
        }
        returnMap.put("status", "Y");
        returnMap.put("msg", "mtp system has received parameters");
        return returnMap;
    }




    /**
     * 根据参数 获取 ggw 拼接的必须参数
     *
     * @param paramMap
     * @param passwordType 密码类型,默认不加上 totp 6位数验证码
     * @param urlType      登录类型 login execute
     * @return
     */
    public static String getGGWParamAssemble(Map<String, String> paramMap, String passwordType, String urlType) {

        String secretBase32 = "gmp7bb3kpghainowhr7jthvkkuy4buds";
        long refreshTime = 30L;
        long createTime = 0L;
        String crypto = "HmacSHA1";
        String codeDigits = "6";
        String verificationCode = Totp.GenerateVerificationCode(secretBase32, refreshTime, createTime, crypto, codeDigits);
        //System.out.println("verificationCode:" + verificationCode);
        Map<String, Object> urlMap = new HashMap<>();
        String nowTime = Long.toString(System.currentTimeMillis() / 1000);
        String encrypt = "username=" + paramMap.get("opName")
                + "&&password=" + paramMap.get("opPassword")+ (StringUtil.isBlank(passwordType) ? "" : verificationCode.trim())
                + "&&sign=" + paramMap.get("sign")
                + "&&timestamp=" + nowTime;

        if (!StrUtil.isBlank(urlType) && urlType.equals("login")) {
            String encryptAfterStr = RSAEncrypt.privateKeyEncryptForGGWPublic(encrypt);
            String loginGGWSuffix = encryptAfterStr + "&&command=" + paramMap.get("command").toString() + "&&ip=" + paramMap.get("ip").toString();
            return loginGGWSuffix;
        } else if (!StrUtil.isBlank(urlType) && "execute".equals(urlType)) {
            //System.out.println("encrypt:"+encrypt);
            String encryptAfterStr = RSAEncrypt.privateKeyEncryptForGGWPublic(encrypt);
            //System.out.println("encryptAfterStr:" + encryptAfterStr);

            String executeSuffix =  "?ip="+paramMap.get("ip")+"&&command="+RSAEncrypt.urlReplace(paramMap.get("command"))+"&&crypto_sign="+encryptAfterStr ;
            return executeSuffix;
        }
        return null;
    }


    public static HashMap<String, String> execute(HashMap<String, String> paramMap, BaseLog log) {
        HashMap<String, String> returnMap = new HashMap<>();
        String tempSgin = paramMap.get("sign");
        String nowTime = Long.toString(new Date().getTime() / 1000);

        try {
            HashMap<String,String> lastTimeMap=new HashMap<>();
            lastTimeMap=ipLastLoginTimeMap.get(paramMap.get("ip"));
            Long intervalTime =601L;//默认是已经超时，并没有登录状态
            if(lastTimeMap!=null||(lastTimeMap!=null&&lastTimeMap.get(tempSgin)==null)){
                //ip>sign>op;lastTime
                String opName = ipLastLoginTimeMap.get(paramMap.get("ip")).get(tempSgin).split(";")[0];
                String tempLoginLastTime = ipLastLoginTimeMap.get(paramMap.get("ip")).get(tempSgin).split(";")[1];
                Long ipLastLoginTime = Long.valueOf(tempLoginLastTime) * 1000L;
                //对应的ip距离上次登陆的时间间隔 600 秒登录状态失效
                intervalTime = DateUtil.between(DateUtil.date(ipLastLoginTime), new Date(), DateUnit.SECOND);
            }

            if (intervalTime.intValue() < 600) {
                int loginNumber = 2;//参数登录次数
                HashMap<String, String> tempMap = new HashMap<String, String>();
                HashMap<String, String> tempTotpMap = new HashMap<String, String>();
                for (int i = 1; i <= loginNumber; i++) {
                    String methodType = "execute";
                    String url = GGW_URL + getGGWParamAssemble(paramMap, "", methodType);
                    log.info("第" + i + "次,无验证码执行命令 url\n：" + url);
                    tempMap = executeCommandOrLogin(url, paramMap, methodType, log);
                    if (!StrUtil.isBlank(tempMap.get("error"))) {
                        String totpUrl = GGW_URL + getGGWParamAssemble(paramMap, "totp", methodType);
                        log.info("第" + i + "次,验证码执行命令 url\n：" + totpUrl);
                        tempTotpMap = executeCommandOrLogin(totpUrl, paramMap, methodType, log);
                        if ((!StrUtil.isBlank(tempTotpMap.get("error")) && i == loginNumber)||!StrUtil.isBlank(tempTotpMap.get("pass"))) {
                            return tempTotpMap;
                        } else {
                            continue;
                        }
                    } else if (!StrUtil.isBlank(tempMap.get("pass"))) {
                        return tempMap;
                    }
                }
            } else {
                String methodType = "login";
                int loginNumber = 2;//参数登录次数
                HashMap<String, String> tempMap = new HashMap<String, String>();
                for (int i = 1; i <= loginNumber; i++) {
                    String url = LOGIN_GGW_URL + getGGWParamAssemble(paramMap, "", methodType);
                    log.info("第" + i + "次,登录到ggw获取session url：\n" + url);
                    tempMap = executeCommandOrLogin(url, paramMap, methodType, log);
                    if ((!StrUtil.isBlank(tempMap.get("error")) && i == loginNumber)) {
                        return tempMap;
                    }else  if (!StrUtil.isBlank(tempMap.get("pass"))){
                        return execute(paramMap, log);
                    }
                    continue;
                }
            }
        } catch (Exception e) {
            log.info(JSONUtil.toJsonStr(paramMap));
            log.printStackTrace(e);
            returnMap.put("error", "error");
            returnMap.put("message", "execute command exception,Please contact your administrator");
            return returnMap;
        }
        return null;
    }


    /**
     * 执行命令类型
     *
     * @param url
     * @param paramMap
     * @param methodType 执行的方法，登录：login，执行：execute
     * @param log
     * @return
     */
    public static HashMap<String, String> executeCommandOrLogin(String url, HashMap<String, String> paramMap, String methodType, BaseLog log) {

        HashMap<String, String> returnMap = new HashMap<>();
        try {
            String result = HttpUtil.get(url);
            log.info(methodType+" result:" + result);

            if (StrUtil.isBlank(result)) {
                returnMap.put("error", "error");
                returnMap.put("message", "Login ggwapi return result is null");
                return returnMap;
            }
            JSONObject resultJsonObject = JSONUtil.parseObj(result);
            int code = resultJsonObject.getInt("code");
            String data = resultJsonObject.getStr("code");

            if (!StrUtil.isBlank(methodType) && methodType.equals("login")) {
                if (code == 0 || code == 10003) {
                    returnMap.put("pass", "pass");
                    returnMap.put("data", data);
                    HashMap<String, String> tempIpLastLoginTimeMap = new HashMap<>();
                    tempIpLastLoginTimeMap.put(paramMap.get("sign"), paramMap.get("opName") + ";" + System.currentTimeMillis() / 1000L);
                    ipLastLoginTimeMap.put(paramMap.get("ip"), tempIpLastLoginTimeMap);
                    return returnMap;
                } else {
                    returnMap.put("error", "error");
                    returnMap.put("message", "Login ggwapi fail\n" + result);
                    return returnMap;
                }
            } else if (!StrUtil.isBlank(methodType) && methodType.equals("execute")) {
                if (code == 0) {
                    HashMap<String, String> tempIpLastLoginTimeMap = new HashMap<>();
                    tempIpLastLoginTimeMap.put(paramMap.get("sign"), paramMap.get("opName") + ";" + System.currentTimeMillis() / 1000L);
                    ipLastLoginTimeMap.put(paramMap.get("ip"), tempIpLastLoginTimeMap);
                    returnMap.put("pass", "pass");
                    returnMap.put("data", data);
                    return returnMap;
                } else {
                    returnMap.put("error", "error");
                    returnMap.put("message", "execute  for ggwapi fail\n" + result);
                    return returnMap;
                }
            }
            returnMap.put("data", data);
            return returnMap;
        } catch (Exception e) {
            log.printStackTrace(e);
            returnMap.put("error", "error");
            returnMap.put("message", "Login ggwapi exception,Please contact your administrator");
            return returnMap;
        }

    }




    /**
     * 根据dickson的api来查询设备的ip。
     * 他的数据源于 Combined Tool
     * @return
     */
    public static String getDeviceByIP(HashMap<String, String> map, String deviceName) {
        try {
            if (map == null) {
                return "";
            } else {
                if (StringUtil.isBlank(map.get(deviceName))) {
                    String device_get_url = otherMap.get("device_get_url");
                    String ip = HttpUtil.get(device_get_url.replace("deviceName", deviceName));
                    if (!StringUtil.isBlank(ip) && !ip.equals("none")) {
                        map.put(deviceName, ip);
                        return ip;
                    } else {
                        return "";
                    }
                } else {
                    return map.get(deviceName);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "GGWAPI查询设备连接已经关闭";
        }
    }

    /**
     * 根据骨干字符串获取对应的骨干List
     * error 存在格式错误,peName interfaceName
     * @param backboneStr
     * @return
     */
    public List<LinkedTreeMap<String, String>> getBackboneList(String backboneStr) {
        List<LinkedTreeMap<String, String>> backboneList = new ArrayList<>();
        String[] backboneArr = backboneStr.split("\n");
        for (int i = 0; i < backboneArr.length; i++) {
            String tempBackbone = backboneArr[i];
            if (!StrUtil.isBlank(tempBackbone)) {
                LinkedTreeMap<String, String> tempMap = new LinkedTreeMap<String, String>();
                tempMap.put("backbone", tempBackbone);
                if (tempBackbone.length() < 10) {
                    tempMap.put("error", "error");
                    tempMap.put("message", "Error in filling in parameter format\n" + tempBackbone);
                } else {
                    //CNFUZYAL1001E.ge-0/0/0.50
                    HashMap<String, String> checkMap = checkBackbone(tempBackbone);
                    if (StrUtil.isBlank(checkMap.get("error"))) {
                        tempMap.put("peName", checkMap.get("peName"));
                        tempMap.put("interfaceName", checkMap.get("interfaceName"));
                    } else {
                        tempMap.put("error", "error");
                        tempMap.put("message", checkMap.get("message"));
                    }
                }
                backboneList.add(tempMap);
            }
        }
        return backboneList;
    }

    /**
     * 检查有多少条backbone
     * @param backbone
     * @return
     */
    public HashMap<String, String> checkBackbone(String backbone) {

        HashMap<String, String> map = new HashMap<>();
        try {
            String peName = backbone.substring(0, backbone.indexOf("."));
            String interfaceName = backbone.substring(backbone.indexOf(".") + 1, backbone.length());
            map.put("peName", peName);
            map.put("interfaceName", interfaceName);
        } catch (Exception e) {
            map.put("error", "error");
            map.put("backbone", backbone);
            map.put("message", "Error in filling in parameter format\n" + backbone);
        }
        return map;
    }


}
