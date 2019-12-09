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
import com.cter.util.CommonUtil;
import com.cter.util.LoadPropertiestUtil;
import com.cter.util.StringUtil;
import com.cter.util.totp.Totp;
import com.google.gson.internal.LinkedTreeMap;
import org.hibernate.type.StringType;
import org.springframework.util.StringUtils;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.crypto.Data;
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
    private static Map<String, Map<String, String>> ipLastLoginTimeMap = new HashMap<>();

    private static BaseLog log = new BaseLog("MTPQueryLog");

    static {

    }

    public void add() {


    }

    public static void testGGwAPI() {
        String opName = "op1768";
        String opPassword = "Abc10151015";
        String command = "show route";
        command = "11";
        String ip = "152.101.179.122";//2FA
        //ip="218.96.240.81";
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("opName", opName);
        paramMap.put("opPassword", opPassword);
        paramMap.put("sign", "123456");
        paramMap.put("command", command);
        paramMap.put("ip", ip);
        System.out.println("输入的参数：" + paramMap);
        for (int i = 1; i <= 3; i++) {
            System.out.println("===========================================================" + i + "===========================================================");
            Map<String, String> returnMap = execute(paramMap, log);
            System.out.println(returnMap);
        }
    }

    public static void main(String[] args) {
   /*     HashMap<String, String> paramMap=new HashMap<String, String>();
        paramMap.put("opName", "op1768");
        paramMap.put("opPassword", "Abc10151015");
        paramMap.put("sign", "123456789");
        paramMap.put("command","11");
        paramMap.put("ip", "152.101.179.122");
        System.out.println("=========================================================");
       String encrypt="username=op1768&&password=Abc10151015461803&&sign=123456&&timestamp=1573097713";
        paramMap.put("sign","123456");
        System.out.println("encrypt:"+encrypt);
        String encryptAfterStr = RSAEncrypt.privateKeyEncryptForGGWPublic(encrypt);
        String url = GGW_URL+"?ip="+paramMap.get("ip")+"&&command="+RSAEncrypt.urlReplace(paramMap.get("command"))+"&&crypto_sign="+encryptAfterStr ;
        System.out.println("getGGWAPI url:\n" + url);*/

        //testGGwAPI();

        Backbone backbone = new Backbone();
        String backboneStr = "CNCHZTJN1001E.ge-1/0/0.50\n"+"CNSUZCYY1004E.ge-0/1/3.50";

        HashMap<String, String> devicesMap = new HashMap<String, String>();

        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("opName", "op1768");
        paramMap.put("opPassword", "Abc10151015");
        paramMap.put("sign", "123456789");
        paramMap.put("command", "11");
        paramMap.put("ip", "152.101.179.122");
        List<LinkedTreeMap<String, String>> backboneList = new ArrayList<>();
        if (!StrUtil.isBlank(backboneStr)) {
            backboneList = backbone.getBackboneList(backboneStr);
            for (int i = 0; i < backboneList.size(); i++) {
                LinkedTreeMap<String, String> tempBackboneDetailed = backboneList.get(i);
                if (!StrUtil.isBlank(tempBackboneDetailed.get("error"))) {
                    String message = tempBackboneDetailed.get("message");
                } else {
                    String peName = tempBackboneDetailed.get("peName");
                    String interfaceName = tempBackboneDetailed.get("interfaceName");
                    String ip = getDeviceByIP(devicesMap, peName);
                    String command1 = "show interfaces " + interfaceName;
                    paramMap.put("command", command1);
                    paramMap.put("ip", ip);
                    List<List<String>> backboneResult = getBackboneResult(paramMap,peName,interfaceName);
                    for (List<String> tempList : backboneResult) {
                        System.out.println(peName+">"+tempList.get(0));
                        String data=tempList.get(1);
                            if(data.indexOf("error")==-1&&data.indexOf("syntax error")==-1){
                                System.out.println(tempList.get(1));
                            }else{
                                System.err.println(tempList.get(1));
                            }

                    }


                }
            }
        }

    }

    /**
     * 根据peName 和 interfaceName 和 登录设备信息 执行骨干查询结果
     * @param paramMap
     * @param peName  设备名称
     * @param interfaceName 端口
     * @return
     */
    public static List<List<String>> getBackboneResult(Map<String, String> paramMap, String peName, String interfaceName) {
        List<List<String>> backboneResult = new ArrayList<>();
        List<String> tempList = new ArrayList<>();

        String command = "show interfaces " + interfaceName;
        paramMap.put("command", command);
        tempList.add(command);
        Map<String, String> comandRes = execute(paramMap, log);
        String tempData = comandRes.get("data");
        String tempError = comandRes.get("error");
        if (!StrUtil.isBlank(tempData) && StrUtil.isBlank(tempError)) {
            tempList.add(tempData.replace("\\n","\n"));
            Map<String, String>  tempParamMap=getParamByShow(tempData);
            backboneResult.add(tempList);
            tempList=new ArrayList<>();
            if (StringUtils.isEmpty(tempParamMap.get("error"))) {
                String descriptionPE=tempParamMap.get("descriptionPE");
                String oppositeIP=tempParamMap.get("oppositeIP");
                String localIP=tempParamMap.get("localIP");
                command = "show isis adjacency "+descriptionPE+" extensive";
                paramMap.put("command", command);
                tempList.add(command);
                comandRes = execute(paramMap, log);
                tempAddList(backboneResult,tempList,comandRes);
                command = "show ldp neighbor "+oppositeIP+" extensive";
                tempList=new ArrayList<>();

                paramMap.put("command", command);
                tempList.add(command);
                comandRes = execute(paramMap, log);
                tempAddList(backboneResult,tempList,comandRes);
                tempList=new ArrayList<>();


                command= "ping interface "+interfaceName+" rapid source "+localIP+" "+oppositeIP+" count 10 size 9000 do-not-fragment";
                paramMap.put("command", command);
                tempList.add(command);
                comandRes = execute(paramMap, log);
                tempAddList(backboneResult,tempList,comandRes);
                tempList=new ArrayList<>();

                return backboneResult;

            }else{
                tempList.add("error");
                tempList.add(tempParamMap.get("error"));
                backboneResult.add(tempList);
                return backboneResult;
            }
        } else {

            tempList.add(tempError);
            tempData = "";
            tempError = "";
            backboneResult.add(tempList);
            tempList = null;
            return backboneResult;
        }

    }

    /**
     * 临时增加list 封装重复代码
     * @param backboneResult
     * @param tempList
     * @param comandRes
     */
    public static void tempAddList(List<List<String>> backboneResult,List<String> tempList,Map<String, String> comandRes){
        if(StringUtils.isEmpty(comandRes.get("error"))){
            tempList.add(comandRes.get("data").replace("\\n","\n"));
            backboneResult.add(tempList);
        }else{
            tempList.add(comandRes.get("message"));
            backboneResult.add(tempList);
        }
    }


    /**
     * 骨干根据show结果来得到参数，
     * descriptionPE，oppositeIP，localIP
     * @param showResult
     * @return
     */
    public static Map<String, String> getParamByShow(String showResult) {
        Map<String, String> map = new HashMap<>();
        try {
            String descriptionPE = "";//对端pe
            String oppositeIP = "";//对端ip
            String localIP = "";
            String destinationIP="";//目的ip
            String destinationIPPrefix="";//目的ip前缀
            String broadcastIP="";//逻辑ip
            String postType="";//端口类型
            descriptionPE=CommonUtil.subByStringAndString(showResult," to "," ");
            localIP=CommonUtil.subByStringAndString(showResult,"Local:","\\n").trim();
            destinationIP=CommonUtil.subByStringAndString(showResult,"Destination:",",").trim();
            postType=CommonUtil.subByStringAndString(destinationIP,"/","").trim();
            broadcastIP=CommonUtil.subByStringAndString(showResult,"Broadcast:","\\n").trim();
            destinationIPPrefix=CommonUtil.subByStringAndString(destinationIP,"","/").trim();

            int destinationIPSuffix=0;//目的ip后缀
            int broadcastIPSuffix=0;//逻辑ip后缀
            int localIPSuffix=0;//本地ip后缀
            localIPSuffix=Integer.valueOf(localIP.split("\\.")[3]);
            destinationIPSuffix=Integer.valueOf(destinationIPPrefix.split("\\.")[3]);

            if(postType.equals("30")){
                if(localIPSuffix-destinationIPSuffix==1){
                    destinationIPSuffix=destinationIPSuffix+2;
                    oppositeIP=destinationIPPrefix.substring(0,destinationIPPrefix.lastIndexOf(".")+1)+destinationIPSuffix;
                }else{
                    destinationIPSuffix=destinationIPSuffix+1;
                    oppositeIP=destinationIPPrefix.substring(0,destinationIPPrefix.lastIndexOf(".")+1)+destinationIPSuffix;
                }
            }else{
                destinationIPSuffix=destinationIPSuffix+1;
                oppositeIP=destinationIPPrefix.substring(0,destinationIPPrefix.lastIndexOf(".")+1)+destinationIPSuffix;
            }


            map.put("descriptionPE", descriptionPE);
            map.put("oppositeIP", oppositeIP);
            map.put("localIP", localIP);
        } catch (Exception e) {
            map.put("error","Get parameter exception");
            e.printStackTrace();
        }
        return map;
    }



    /**
     * 根据dickson的api来查询设备的ip。
     * 他的数据源于 Combined Tool
     *
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
        verificationCode = (StringUtil.isBlank(passwordType) ? "" : new String(verificationCode));
        //System.out.println("verificationCode:" + verificationCode);
        Map<String, Object> urlMap = new HashMap<>();
        String nowTime = Long.toString(System.currentTimeMillis() / 1000);
        //System.out.println("nowTime:"+ DateUtil.now());

        String encrypt = "username=" + paramMap.get("opName")
                + "&&password=" + paramMap.get("opPassword") + verificationCode
                + "&&sign=" + paramMap.get("sign")
                + "&&timestamp=" + nowTime;
        String encryptAfterStr = RSAEncrypt.privateKeyEncryptForGGWPublic(encrypt);
        if (!StrUtil.isBlank(urlType) && urlType.equals("login")) {
            String loginGGWSuffix = encryptAfterStr;//+ "&&command=" + paramMap.get("command").toString() + "&&ip=" + paramMap.get("ip");
            return loginGGWSuffix;
        } else if (!StrUtil.isBlank(urlType) && "execute".equals(urlType)) {
            //System.out.println("加密前 encrypt:"+ encrypt);
            //System.out.println("加密后 encrypt:"+ encryptAfterStr);
            String executeSuffix = "?ip=" + paramMap.get("ip") + "&&command=" + RSAEncrypt.urlReplace(paramMap.get("command")) + "&&crypto_sign=" + encryptAfterStr;
            return executeSuffix;
        }
        return null;

    }


    /**
     * 根据参数来调用ggwAPI 执行命令
     *
     * @param paramMap paramMap参数，username:op账号，password:op密码,sign：对应的访问标记，一个op同一个时间段只能有6个，command:命令，ip:登录到ggw对应的ip
     * @return 正常对应的状态码和执行命令的状态
     */
    public static String getGGWAPITotp(Map<String, String> paramMap) {
        String result = null;

        String secretBase32 = "gmp7bb3kpghainowhr7jthvkkuy4buds";
        long refreshTime = 30l;
        long createTime = 0l;
        String crypto = "HmacSHA1";
        String codeDigits = "6";
        String verificationCode = Totp.GenerateVerificationCode(secretBase32, refreshTime, createTime, crypto, codeDigits);
        System.out.println("verificationCode:" + verificationCode);

        try {
            Map<String, Object> urlMap = new HashMap<>();
            String nowTime = Long.toString(System.currentTimeMillis() / 1000);
            //System.out.println("nowTime:"+ DateUtil.now());
            String encrypt = "username=" + paramMap.get("opName")
                    + "&&password=" + paramMap.get("opPassword") + verificationCode
                    + "&&sign=" + paramMap.get("sign")
                    + "&&timestamp=" + nowTime;

            System.out.println("encrypt:" + encrypt);
            String encryptAfterStr = RSAEncrypt.privateKeyEncryptForGGWPublic(encrypt);
            String url = GGW_URL + "?ip=" + paramMap.get("ip") + "&&command=" + RSAEncrypt.urlReplace(paramMap.get("command")) + "&&crypto_sign=" + encryptAfterStr;
            System.out.println("getGGWAPI url:\n" + url);
            try {
                //Charset charset = Charset.forName("utf8");

                result = HttpUtil.get(url);
            } catch (Exception e) {
                System.out.println(e.getCause().getMessage());//获取异常信息
                e.printStackTrace();
                result = "error；" + e.getCause().getMessage();
            }
        } catch (HttpException e) {
            e.printStackTrace();
            ;
            return "{'code':9999,'data':'Bad request'}";
        }
        return result;
    }

    public static Map<String, String> execute(Map<String, String> paramMap, BaseLog log) {
        Map<String, String> returnMap = new HashMap<>();
        String tempSgin = paramMap.get("sign");
        String nowTime = Long.toString(new Date().getTime() / 1000);

        try {
            Map<String, String> lastTimeMap = new HashMap<>();
            lastTimeMap = ipLastLoginTimeMap.get(paramMap.get("ip"));
            Long intervalTime = 601L;//默认是已经超时，并没有登录状态
            if (lastTimeMap != null || (lastTimeMap != null && lastTimeMap.get(tempSgin) == null)) {
                //ip>sign>op;lastTime
                String opName = ipLastLoginTimeMap.get(paramMap.get("ip")).get(tempSgin).split(";")[0];
                String tempLoginLastTime = ipLastLoginTimeMap.get(paramMap.get("ip")).get(tempSgin).split(";")[1];
                Long ipLastLoginTime = Long.valueOf(tempLoginLastTime) * 1000L;
                //对应的ip距离上次登陆的时间间隔 600 秒登录状态失效
                intervalTime = DateUtil.between(DateUtil.date(ipLastLoginTime), new Date(), DateUnit.SECOND);
            }

            if (intervalTime.intValue() < 600) {
                int loginNumber = 2;//参数登录次数
                Map<String, String> tempMap = new HashMap<>();
                Map<String, String> tempTotpMap = new HashMap<>();

             /*   String methodType = "execute";
                String totpUrl = GGW_URL + getGGWParamAssemble(paramMap, "totp", methodType);
                log.info("验证码执方式行命令 url：\n" + totpUrl);
                tempTotpMap = executeCommandOrLogin(totpUrl, paramMap, methodType, log);
                return tempTotpMap;*/
                for (int i = 1; i <= loginNumber; i++) {
                    String methodType = "execute";
                    String url = GGW_URL + getGGWParamAssemble(paramMap, "", methodType);
                    log.info("第" + i + "次,无验证码方式执行命令 url：\n" + url);
                    tempMap = executeCommandOrLogin(url, paramMap, methodType, log);
                    if (!StrUtil.isBlank(tempMap.get("error"))) {
                        String totpUrl = GGW_URL + getGGWParamAssemble(paramMap, "totp", methodType);
                        log.info("第" + i + "次,验证码执方式行命令 url：\n" + totpUrl);
                        tempTotpMap = executeCommandOrLogin(totpUrl, paramMap, methodType, log);
                        if ((!StrUtil.isBlank(tempTotpMap.get("error")) && i == loginNumber) || !StrUtil.isBlank(tempTotpMap.get("pass"))) {
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
                Map<String, String> tempMap = new HashMap<>();
                for (int i = 1; i <= loginNumber; i++) {
                    String url = LOGIN_GGW_URL + getGGWParamAssemble(paramMap, "", methodType);
                    log.info("第" + i + "次,登录到ggw获取session url：\n" + url);
                    tempMap = executeCommandOrLogin(url, paramMap, methodType, log);
                    if ((!StrUtil.isBlank(tempMap.get("error")) && i == loginNumber)) {
                        return tempMap;
                    } else if (!StrUtil.isBlank(tempMap.get("pass"))) {
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
    public static Map<String, String> executeCommandOrLogin(String url, Map<String, String> paramMap, String methodType, BaseLog log) {

        Map<String, String> returnMap = new HashMap<>();
        try {
            Charset charset = Charset.forName("utf8");

            String result = HttpUtil.get(url, charset);
            log.info(methodType + " result:" + result);

            if (StrUtil.isBlank(result)) {
                returnMap.put("error", "error");
                returnMap.put("message", "Login ggwapi return result is null");
                return returnMap;
            }
            JSONObject resultJsonObject = JSONUtil.parseObj(result);
            int code = resultJsonObject.getInt("code");
            String data = resultJsonObject.getStr("data");

            if (!StrUtil.isBlank(methodType) && methodType.equals("login")) {
                if (code == 0 || code == 10003) {
                    returnMap.put("pass", "pass");
                    returnMap.put("data", data);
                    Map<String, String> tempIpLastLoginTimeMap = new HashMap<>();
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
                    Map<String, String> tempIpLastLoginTimeMap = new HashMap<>();
                    tempIpLastLoginTimeMap.put(paramMap.get("sign"), paramMap.get("opName") + ";" + System.currentTimeMillis() / 1000L);
                    ipLastLoginTimeMap.put(paramMap.get("ip"), tempIpLastLoginTimeMap);
                    returnMap.put("pass", "pass");
                    returnMap.put("data", data);
                    return returnMap;
                } else {
                    returnMap.put("error", "error");
                    returnMap.put("message", "execute  for ggwapi fail (" + result + ")");
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
     *
     * @return
     */
    public static String getDeviceByIP(Map<String, String> map, String deviceName) {
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
     *
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
                    Map<String, String> checkMap = checkBackbone(tempBackbone);
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
     *
     * @param backbone
     * @return
     */
    public Map<String, String> checkBackbone(String backbone) {

        Map<String, String> map = new HashMap<>();
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

    public static BaseLog getLog() {
        return log;
    }

    public static void setLog(BaseLog log) {
        Backbone.log = log;
    }
}
