package com.cter.util;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpException;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.cter.rsa.RSAEncrypt;
import com.cter.util.TempDBUtils;
import com.cter.util.totp.Totp;


import java.nio.charset.Charset;
import java.util.*;

/**
 * @author op1768
 * @create 2019-09-18 18:33
 * @project light-attenuation
 */
public class GGWLoginApiUtil {


    private static String GGW_URL = "http://210.5.3.177:48888/ExecuteCommand/RSA";
    private static String LOGIN_GGW_URL = "http://210.5.3.177:48888/GetLoginSession/RSA?crypto_sign=";

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
    public static String getGGWAPITotp(Map<String, String> paramMap) {
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
                    + "&&password=" + paramMap.get("opPassword")+verificationCode
                    + "&&sign=" + paramMap.get("sign")
                    + "&&timestamp=" + nowTime;

            System.out.println("encrypt:"+encrypt);
            String encryptAfterStr = RSAEncrypt.privateKeyEncryptForGGWPublic(encrypt);
            String url = GGW_URL+"?ip="+paramMap.get("ip")+"&&command="+RSAEncrypt.urlReplace(paramMap.get("command"))+"&&crypto_sign="+encryptAfterStr ;
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
            e.printStackTrace();;
            return "{'code':9999,'data':'Bad request'}";
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
        String ip="152.101.179.122";//2FA
        ip="218.96.240.81";



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
        for (int i = 1; i <=10 ; i++) {
            System.out.println("====================="+i+"======================");
            test1();
        }
        //test();

    }

}
