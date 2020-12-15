package com.cter.util;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpException;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.cter.rsa.RSAEncrypt;
import com.cter.util.TempDBUtils;
import com.cter.util.totp.Totp;
import org.springframework.util.StringUtils;


import java.nio.charset.Charset;
import java.util.*;

/**
 * @author op1768
 * @create 2019-09-18 18:33
 * @project light-attenuation
 */
public class GGWLoginApiUtil {

    private static Map<String, String> otherMap = LoadPropertiestUtil.loadProperties("config/other.properties");
    private static String GGW_URL = otherMap.get("GGW_URL");
    private static String LOGIN_GGW_URL = otherMap.get("LOGIN_GGW_URL");
    private static Map<String, String> paramMap = new HashMap<String, String>();
    private static Map<String, Map<String, String>> ipLastLoginTimeMap = new HashMap<>();
    private static int loginNum=0;//登录次数

    private static BaseLog log = new BaseLog("MTPQueryLog");

    public static BaseLog getLog() {
        return log;
    }

    public static void setLog(BaseLog log) {
        GGWLoginApiUtil.log = log;
    }

    /**
     * 根据参数来登录ggw
     * @param paramMap
     * paramMap参数，username:op账号，password:op密码,sign：对应的访问标记，一个op同一个时间段只能有6个，command:命令，ip:登录到ggw对应的ip
     * @return 正常返回登录的信息，错误返回error
     */
    public static String loginGGWAPI(Map<String, String> paramMap) {
        String nowTime = Long.toString(System.currentTimeMillis() / 1000);
        //System.out.println("nowTime:"+ DateUtil.now());
        String encrypt = "username=" + paramMap.get("opName")
                + "&&password=" + paramMap.get("opPassword")
                + "&&sign=" + paramMap.get("sign")
                + "&&timestamp=" + nowTime;
        String encryptAfterStr = RSAEncrypt.privateKeyEncryptForGGWPublic(encrypt);
        String tempUrl = encryptAfterStr + "&&command=" + paramMap.get("command").toString() + "&&ip=" + paramMap.get("ip").toString();
        String url = LOGIN_GGW_URL + RSAEncrypt.urlReplace(encryptAfterStr);
        System.out.println("loginGGWAPI url:\n" + url);
        String result = "";
        try {
            result = HttpUtil.get(url);
            if (StrUtil.isBlank(result)) {
                result = "error:login 返回参数为空";
            }
        } catch (Exception e) {
            //System.out.println(e.getCause().getMessage());//获取异常信息
            e.printStackTrace();
            result = "error；" + e.getCause().getMessage();
        }

        return result;
    }


    /**
     * 根据参数来调用ggwAPI 执行命令
     * @param paramMap
     * paramMap参数，username:op账号，password:op密码,sign：对应的访问标记，一个op同一个时间段只能有6个，command:命令，ip:登录到ggw对应的ip
     * @return 正常对应的状态码和执行命令的状态
     */
    public static String getGGWAPI(Map<String, String> paramMap) {
        String result = null;

        String secretBase32="gmp7bb3kpghainowhr7jthvkkuy4buds";
        long refreshTime=30l;
        long createTime=0l;
        String crypto="HmacSHA1";
        String codeDigits="6";
        String verificationCode=Totp.GenerateVerificationCode(secretBase32,refreshTime,createTime,crypto,codeDigits);
        System.out.println("verificationCode:"+verificationCode);

        try {
            Map<String, Object> urlMap=new HashMap<>();
            String nowTime = Long.toString(System.currentTimeMillis() / 1000);
            //System.out.println("nowTime:"+ DateUtil.now());
            String encrypt = "username=" + paramMap.get("opName")
                    + "&&password=" + paramMap.get("opPassword")
                    + "&&sign=" + paramMap.get("sign")
                    + "&&timestamp=" + nowTime;

            System.out.println("encrypt:"+encrypt);
            String encryptAfterStr = RSAEncrypt.privateKeyEncryptForGGWPublic(encrypt);
            String url = GGW_URL+"?ip="+paramMap.get("ip")+"&&command="+RSAEncrypt.urlReplace(paramMap.get("command"))+"&&crypto_sign="+encryptAfterStr ;
            System.out.println("getGGWAPI url:\n" + url);
            try {
                Charset charset = Charset.forName("utf8");

                result = HttpUtil.get(url,charset);
            } catch (Exception e) {
                System.out.println(e.getCause().getMessage());//获取异常信息
                e.printStackTrace();
                result = "error；" + e.getCause().getMessage();
            }
        } catch (HttpException e) {
            e.printStackTrace();;
            return "{'code':9999,'data':'Bad request'}";
        }
        return result;
    }

    /**
     * 获取设备信息
     * @return
     */
    public static Map<String, Map<String, Object>> getDevices() {
        String username = "nss_script";
        String password = "ZX4XdUpH";
        String url = "jdbc:log4jdbc:mysql://218.97.9.147:3306/nm_shared_info?characterEncoding=utf-8";
        String driver = "net.sf.log4jdbc.DriverSpy";
        TempDBUtils tempDBUtils = new TempDBUtils(username, password, driver, url);

        String sql = "select ip,full_name from devices  ";
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Map<String, Object>> map = new LinkedHashMap<String, Map<String, Object>>();
        List<Object> params = new ArrayList<Object>();
        list = tempDBUtils.executeQueryCE(sql, params);
        for (Map<String, Object> o : list) {
            map.put(o.get("full_name").toString(), o);
        }

        String[] testIpArr = new String[]{"202.76.80.210", "10.180.5.206"};
        //用来存放testlabIp
        Map<String, Object> tempMap = new HashMap<>();
        for (int i = 0; i < testIpArr.length; i++) {
            String teampIp = testIpArr[i];
            tempMap.put("full_name", teampIp);
            tempMap.put("ip", teampIp);
            map.put(teampIp, tempMap);
        }
        return map;
    }

    public static void test(){
        String opName="op1768";
        String opPassword="Abc1015";
        String command1="show interfaces diagnostics optics ge-0/0/8";
        String ip="218.96.240.81";
        Map<String, String> paramMap=new HashMap<String, String>( );
        paramMap.put("opName", opName);
        paramMap.put("opPassword", opPassword);
        paramMap.put("sign", "123456");
        paramMap.put("command", command1);
        paramMap.put("ip", ip);
        System.out.println("入参："+paramMap);
        String loginReturnString=loginGGWAPI(paramMap);
        System.out.println("登录返回参数："+loginReturnString);
        String getGGWAPIString=getGGWAPI(paramMap);
        System.out.println("返回的结果集："+getGGWAPIString);
    }

    public static void test1(){
        Map<String, Map<String, Object>> devicesMap = getDevices();
        String opName="op1768";
        String opPassword="Abc10151015";
        //String command="show interfaces descriptions %7C match trunk";
        String command="show route";
        command="11";
        String ip="218.96.240.96";//2FA
        //ip="218.96.240.81";



        //System.out.println(devicesMap.get("CNSHHCJX1001E"));
        Map<String, String> paramMap=new HashMap<String, String>();
        paramMap.put("opName", opName);
        paramMap.put("opPassword", opPassword);
        paramMap.put("sign", "123456");
        paramMap.put("command",command);
        paramMap.put("ip", ip);
        System.out.println("输入的参数："+paramMap);
        String loginReturnString=loginGGWAPI(paramMap);
        System.out.println("登录返回参数："+loginReturnString);
        //String getGGWAPIString=getGGWAPI(paramMap);
        String getGGWAPIString=getGGWAPITotp(paramMap);

        System.out.println("返回的结果集："+ JSONUtil.parseObj(getGGWAPIString));
    }


    public static void main(String[] args) {
        /*for (int i = 1; i <=3 ; i++) {
            System.out.println("====================="+i+"======================");
            test1();
        }*/


        String opName="op1768";
        String opPassword="Abc10151015";
        //String command="show interfaces descriptions %7C match trunk";
        String command="show route";
        command="11";
        String ip="218.96.240.96";//2FA
        //ip="218.96.240.81";
        Map<String, String> paramMap=new HashMap<String, String>();
        paramMap.put("opName", opName);
        paramMap.put("opPassword", opPassword);
        paramMap.put("sign", "123456");
        paramMap.put("command",command);
        paramMap.put("ip", ip);
        Map<String, String> temprReturnMap= execute(paramMap,GGWLoginApiUtil.log);
        String tempString="";
        if(StringUtils.isEmpty(temprReturnMap.get("error"))){
            tempString=temprReturnMap.get("data");
        }else{
            tempString=temprReturnMap.get("message");
        }
        System.out.println("===============================================================================");
        System.out.println("ip:"+ip);
        System.out.println("command:"+command);
        System.out.println("tempString:"+tempString);

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

        String secretBase32 =paramMap.get("secretBase32");// "gmp7bb3kpghainowhr7jthvkkuy4buds";
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
            String loginGGWSuffix = encryptAfterStr + "&&command=" + paramMap.get("command").toString() + "&&ip=" + paramMap.get("ip");
            return loginGGWSuffix;
        } else if (!StrUtil.isBlank(urlType) && "execute".equals(urlType)) {
            System.out.println("加密前 encrypt:"+ encrypt);
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

    /**
     * 根据 paramMap 参数来执行command来活去执行的结果
     * get("error") 有内容为错误，否则 get("data")  为执行结果
     * op1768 验证码密匙 secretBase32：gmp7bb3kpghainowhr7jthvkkuy4buds
     * @param paramMap
     * @param log
     * @return
     */
    public static Map<String, String> execute(Map<String, String> paramMap, BaseLog log) {
        Map<String, String> returnMap = new HashMap<>();
        String tempSgin = (StringUtils.isEmpty(paramMap.get("sign"))?"123456":paramMap.get("sign"));
        String nowTime = Long.toString(new Date().getTime() / 1000);

        try {
            Map<String, String> lastTimeMap = new HashMap<>();
            lastTimeMap = ipLastLoginTimeMap.get(paramMap.get("ip"));
            Long intervalTime = 601L;//默认是已经超时，并没有登录状态
            if (lastTimeMap != null &&  lastTimeMap.get(tempSgin) != null) {
                //ip>sign>op;lastTime
                String opName = ipLastLoginTimeMap.get(paramMap.get("ip")).get(tempSgin).split(";")[0];
                String tempLoginLastTime = ipLastLoginTimeMap.get(paramMap.get("ip")).get(tempSgin).split(";")[1];
                Long ipLastLoginTime = Long.valueOf(tempLoginLastTime) * 1000L;
                //对应的ip距离上次登陆的时间间隔 600 秒登录状态失效
                intervalTime = cn.hutool.core.date.DateUtil.between(DateUtil.date(ipLastLoginTime), new Date(), DateUnit.SECOND);
            }
            if (intervalTime.intValue() < 600) {
                int loginNumber = 2;//参数登录次数
                Map<String, String> tempMap = new HashMap<>();
                Map<String, String> tempTotpMap = new HashMap<>();


                for (int i = 1; i <= loginNumber; i++) {
                    String methodType = "execute";
                    String  totpUrl= GGW_URL + getGGWParamAssemble(paramMap, "totp", methodType);
                    log.info("第" + i + "次,有验证码方式执行命令 url：\n" + totpUrl);
                    tempMap = executeCommandOrLogin(totpUrl, paramMap, methodType, log);
                    if (!StrUtil.isBlank(tempMap.get("error"))&&loginNum<10) {
                        paramMap.put("sign","123457");
                        String tempLoginUrl = LOGIN_GGW_URL + getGGWParamAssemble(paramMap, "", "login");
                        log.info("验证码 登录到ggw获取session url：\n" + totpUrl);
                        executeCommandOrLogin(tempLoginUrl, paramMap, "login", log);

                        String notTotpUrl = GGW_URL + getGGWParamAssemble(paramMap, "", methodType);
                        log.info("第" + i + "次,无验证码执方式行命令 url：\n" + notTotpUrl);
                        tempTotpMap = executeCommandOrLogin(notTotpUrl, paramMap, methodType, log);
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
                paramMap.put("sign","123456");
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
                loginNum+=1;
                return returnMap;
            }
            JSONObject resultJsonObject = JSONUtil.parseObj(result);
            int code = resultJsonObject.getInt("code");
            String data = resultJsonObject.getStr("data");
            data=(StringUtils.isEmpty(data))?data:data.replace("\\n","\n");

            if (!StrUtil.isBlank(methodType) && methodType.equals("login")) {
                if (code == 0 || code == 10003) {
                    returnMap.put("pass", "pass");
                    returnMap.put("data", data);
                    Map<String, String> tempIpLastLoginTimeMap = new HashMap<>();
                    tempIpLastLoginTimeMap.put(paramMap.get("sign"), paramMap.get("opName") + ";" + System.currentTimeMillis() / 1000L);
                    ipLastLoginTimeMap.put(paramMap.get("ip"), tempIpLastLoginTimeMap);
                    loginNum=0;
                    return returnMap;
                } else {
                    returnMap.put("error", "error");
                    returnMap.put("message", "Login ggwapi fail\n" + result);
                    loginNum+=1;
                    return returnMap;
                }
            } else if (!StrUtil.isBlank(methodType) && methodType.equals("execute")) {

                if (code == 0) {
                    Map<String, String> tempIpLastLoginTimeMap = new HashMap<>();
                    tempIpLastLoginTimeMap.put(paramMap.get("sign"), paramMap.get("opName") + ";" + System.currentTimeMillis() / 1000L);
                    ipLastLoginTimeMap.put(paramMap.get("ip"), tempIpLastLoginTimeMap);
                    returnMap.put("pass", "pass");
                    returnMap.put("data", data);
                    loginNum=0;
                    return returnMap;
                } else {
                    returnMap.put("error", "error");
                    returnMap.put("message", "execute  for ggwapi fail (" + result + ")");
                    loginNum+=1;
                    return returnMap;
                }
            }
            returnMap.put("data", data);
            loginNum=0;
            return returnMap;
        } catch (Exception e) {
            log.printStackTrace(e);
            returnMap.put("error", "error");
            returnMap.put("message", "Login ggwapi exception,Please contact your administrator");
            loginNum+=1;
            return returnMap;
        }

    }


}
