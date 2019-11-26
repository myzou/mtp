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
    private static final String mtp_project = otherMap.get("mtp_project");
    private static String GGW_URL = otherMap.get("GGW_URL");
    private static String LOGIN_GGW_URL = otherMap.get("LOGIN_GGW_URL");
    private static Map<String, String> paramMap = new HashMap<String, String>();
    private static Map<String, Map<String, String>> ipLastLoginTimeMap = new HashMap<>();

    private static BaseLog log = new BaseLog("MTPQueryLog");

    public static BaseLog getLog() {
        return log;
    }

    public static void setLog(BaseLog log) {
        GGWLoginApiUtil.log = log;
    }

    /**
     * ���ݲ�������¼ggw
     * @param paramMap
     * paramMap������username:op�˺ţ�password:op����,sign����Ӧ�ķ��ʱ�ǣ�һ��opͬһ��ʱ���ֻ����6����command:���ip:��¼��ggw��Ӧ��ip
     * @return �������ص�¼����Ϣ�����󷵻�error
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
                result = "error:login ���ز���Ϊ��";
            }
        } catch (Exception e) {
            //System.out.println(e.getCause().getMessage());//��ȡ�쳣��Ϣ
            e.printStackTrace();
            result = "error��" + e.getCause().getMessage();
        }

        return result;
    }


    /**
     * ���ݲ���������ggwAPI ִ������
     * @param paramMap
     * paramMap������username:op�˺ţ�password:op����,sign����Ӧ�ķ��ʱ�ǣ�һ��opͬһ��ʱ���ֻ����6����command:���ip:��¼��ggw��Ӧ��ip
     * @return ������Ӧ��״̬���ִ�������״̬
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
                System.out.println(e.getCause().getMessage());//��ȡ�쳣��Ϣ
                e.printStackTrace();
                result = "error��" + e.getCause().getMessage();
            }
        } catch (HttpException e) {
            e.printStackTrace();;
            return "{'code':9999,'data':'Bad request'}";
        }
        return result;
    }

    /**
     * ��ȡ�豸��Ϣ
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
        //�������testlabIp
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
        System.out.println("��Σ�"+paramMap);
        String loginReturnString=loginGGWAPI(paramMap);
        System.out.println("��¼���ز�����"+loginReturnString);
        String getGGWAPIString=getGGWAPI(paramMap);
        System.out.println("���صĽ������"+getGGWAPIString);
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
        System.out.println("����Ĳ�����"+paramMap);
        String loginReturnString=loginGGWAPI(paramMap);
        System.out.println("��¼���ز�����"+loginReturnString);
        //String getGGWAPIString=getGGWAPI(paramMap);
        String getGGWAPIString=getGGWAPITotp(paramMap);

        System.out.println("���صĽ������"+ JSONUtil.parseObj(getGGWAPIString));
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
     * ���ݲ��� ��ȡ ggw ƴ�ӵı������
     *
     * @param paramMap
     * @param passwordType ��������,Ĭ�ϲ����� totp 6λ����֤��
     * @param urlType      ��¼���� login execute
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
            System.out.println("����ǰ encrypt:"+ encrypt);
            //System.out.println("���ܺ� encrypt:"+ encryptAfterStr);
            String executeSuffix = "?ip=" + paramMap.get("ip") + "&&command=" + RSAEncrypt.urlReplace(paramMap.get("command")) + "&&crypto_sign=" + encryptAfterStr;
            return executeSuffix;
        }
        return null;

    }


    /**
     * ���ݲ���������ggwAPI ִ������
     *
     * @param paramMap paramMap������username:op�˺ţ�password:op����,sign����Ӧ�ķ��ʱ�ǣ�һ��opͬһ��ʱ���ֻ����6����command:���ip:��¼��ggw��Ӧ��ip
     * @return ������Ӧ��״̬���ִ�������״̬
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
                System.out.println(e.getCause().getMessage());//��ȡ�쳣��Ϣ
                e.printStackTrace();
                result = "error��" + e.getCause().getMessage();
            }
        } catch (HttpException e) {
            e.printStackTrace();
            ;
            return "{'code':9999,'data':'Bad request'}";
        }
        return result;
    }

    /**
     * ���� paramMap ������ִ��command����ȥִ�еĽ��
     * get("error") ������Ϊ���󣬷��� get("data")  Ϊִ�н��
     * op1768 ��֤���ܳ� secretBase32��gmp7bb3kpghainowhr7jthvkkuy4buds
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
            Long intervalTime = 601L;//Ĭ�����Ѿ���ʱ����û�е�¼״̬
            if (lastTimeMap != null &&  lastTimeMap.get(tempSgin) != null) {
                //ip>sign>op;lastTime
                String opName = ipLastLoginTimeMap.get(paramMap.get("ip")).get(tempSgin).split(";")[0];
                String tempLoginLastTime = ipLastLoginTimeMap.get(paramMap.get("ip")).get(tempSgin).split(";")[1];
                Long ipLastLoginTime = Long.valueOf(tempLoginLastTime) * 1000L;
                //��Ӧ��ip�����ϴε�½��ʱ���� 600 ���¼״̬ʧЧ
                intervalTime = cn.hutool.core.date.DateUtil.between(DateUtil.date(ipLastLoginTime), new Date(), DateUnit.SECOND);
            }
            if (intervalTime.intValue() < 600) {
                int loginNumber = 2;//������¼����
                Map<String, String> tempMap = new HashMap<>();
                Map<String, String> tempTotpMap = new HashMap<>();


                for (int i = 1; i <= loginNumber; i++) {
                    String methodType = "execute";
                    String url = GGW_URL + getGGWParamAssemble(paramMap, "", methodType);
                    log.info("��" + i + "��,����֤�뷽ʽִ������ url��\n" + url);
                    tempMap = executeCommandOrLogin(url, paramMap, methodType, log);
                    if (!StrUtil.isBlank(tempMap.get("error"))) {
                        paramMap.put("sign","123457");
                        String tempLoginUrl = LOGIN_GGW_URL + getGGWParamAssemble(paramMap, "", "login");
                        log.info("��֤�� ��¼��ggw��ȡsession url��\n" + url);
                        executeCommandOrLogin(tempLoginUrl, paramMap, "login", log);

                        String totpUrl = GGW_URL + getGGWParamAssemble(paramMap, "totp", methodType);
                        log.info("��" + i + "��,��֤��ִ��ʽ������ url��\n" + totpUrl);
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
                paramMap.put("sign","123456");
                String methodType = "login";
                int loginNumber = 2;//������¼����
                Map<String, String> tempMap = new HashMap<>();
                for (int i = 1; i <= loginNumber; i++) {
                    String url = LOGIN_GGW_URL + getGGWParamAssemble(paramMap, "", methodType);
                    log.info("��" + i + "��,��¼��ggw��ȡsession url��\n" + url);
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
     * ִ����������
     *
     * @param url
     * @param paramMap
     * @param methodType ִ�еķ�������¼��login��ִ�У�execute
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
            data=(StringUtils.isEmpty(data))?data:data.replace("\\n","\n");

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


}
