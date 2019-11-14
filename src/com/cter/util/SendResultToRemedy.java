package com.cter.util;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.cter.bean.ResultList;
import com.cter.service.impl.MTPReceiveService;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.http.client.methods.HttpUriRequest;

import java.util.Map;

/**
 * 推送结果到remedy
 * @author op1768
 * @create 2019-10-24 11:42
 * @project mtp
 */
public class SendResultToRemedy implements  Runnable {


    private BaseLog runTimeLog =new BaseLog("RunTimeLog");
    String loginUrl;String loginOutUrl;String sendToRemedyUrl;String result;long sleep=60;//线程等待
    private static Map<String, String> otherMap = LoadPropertiestUtil.loadProperties("config/other.properties");
    private static final String loginUserName = otherMap.get("loginUserName");
    private static final String loginPassword = otherMap.get("loginPassword");


    public BaseLog getRunTimeLog() {
        return runTimeLog;
    }

    public void setRunTimeLog(BaseLog runTimeLog) {
        this.runTimeLog = runTimeLog;
    }

    public static void main(String[] args)  {
        SendResultToRemedy sendResultToRemedy=new SendResultToRemedy();
        try {
            String token=sendResultToRemedy.login("","","");
            String returnLocation=sendResultToRemedy.sendReult("",token,"","2019 10 24 test","","");
            String loginOutDate=sendResultToRemedy.loginOut("",token);
            //runTimeLog.info(HttpUtil.get("https://t66y.com"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void init(String loginUrl,String loginOutUrl,String sendToRemedyUrl,String result){
        this.loginOutUrl=loginOutUrl;
        this.loginUrl=loginUrl;
        this.sendToRemedyUrl=sendToRemedyUrl;
        this.result=result;
    }

    /**
     *
     * @param loginUrl
     * @param loginOutUrl
     * @param sendToRemedyUrl
     * @param result
     * @return
     */
    public void sendReultInlet(String loginUrl,String loginOutUrl,String sendToRemedyUrl,String result){

        init(loginUrl,loginOutUrl,sendToRemedyUrl,result);



    }

    /**
     * 发送结果到remedy
     * @param token 登陆的token，方法login获得
     * @param ticketName
     * @param msg
     * @param MTP_status
     * @param tense
     * @return
     * @throws Exception
     */
    public  String sendReult(String sendToRemedyUrl,String token,String ticketName,String msg,String MTP_status,String tense)throws Exception{
        // start HTTP POST to create an entry
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // build the JSON entry
        ticketName=ifNotNullSet(ticketName,"HD0000002584545");
        msg =ifNotNullSet(msg,"this is  test");
        MTP_status=ifNotNullSet(MTP_status,"Y");
        tense=ifNotNullSet(tense,"before");

        sendToRemedyUrl=ifNotNullSet(sendToRemedyUrl,"http://10.180.27.11:8008/api/arsys/v1/entry/API_NOC_MTP_Result");
        HttpPost httpPost = new HttpPost(sendToRemedyUrl);

        JSONObject jsonObject=new JSONObject();
        JSONObject valuesObject=new JSONObject();
        valuesObject.put("ticketName",ticketName);
        valuesObject.put("MTP_status",MTP_status);
        valuesObject.put("msg",msg);
        valuesObject.put("tense",tense);
        jsonObject.put("values",valuesObject);
        runTimeLog.info("发送到remedy的内容："+ JSONUtil.toJsonStr(jsonObject));
        String sendJson=JSONUtil.toJsonStr(jsonObject);
        httpPost.setEntity(new StringEntity(sendJson, ContentType.APPLICATION_JSON));
        httpPost.addHeader("Authorization", "AR-JWT " + token);

        // make the call and print the Location
        CloseableHttpResponse response = httpClient.execute(httpPost);
        String  returnLocation = response.getFirstHeader("Location").getValue();
        runTimeLog.info("接受返回的值："+returnLocation);
        return returnLocation;
    }

    /**
     * 如果字符串为空就设置为指定字符串，如果不为空就输出
     * @param ifStr   判断字符串
     * @param setStr  设置的字符串
     * @return
     */
    public static String ifNotNullSet(String ifStr,String setStr){
        if(StrUtil.isBlank(ifStr)){
            return setStr;
        }
        return ifStr;
    }

    /**
     * 登陆到remedy 获取token
     * @param loginUrl
     * @param username
     * @param password
     * @return
     * @throws Exception
     */
    public  String login(String loginUrl,String username,String password)throws Exception{
        loginUrl=ifNotNullSet(loginUrl,"http://10.180.27.11:8008/api/jwt/login");
        username=ifNotNullSet(username,loginUserName);
        password=ifNotNullSet(password,loginPassword);
        // start HTTP POST to create an entry
        CloseableHttpClient httpClient = HttpClients.createDefault();
        loginUrl=loginUrl+"?username="+username+"&password="+password;
        HttpPost httpPost = new HttpPost(loginUrl);

        // build the JSON entry
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");

        // make the call and print the Location
        CloseableHttpResponse response = httpClient.execute(httpPost);
        HttpEntity entity=response.getEntity();
        String token= EntityUtils.toString(entity,"utf-8");
        runTimeLog.info("成功获取到:"+token);
        response.close();
        httpClient.close();
        runTimeLog.info("模拟登陆成功");
        return token;
    }

    /**
     * 退出对应的token
     * @param loginOut
     * @param token
     * @return
     * @throws Exception
     */
    public  String loginOut(String loginOut,String token)throws Exception{
        loginOut=ifNotNullSet(loginOut,"http://10.180.27.11:8008/api/jwt/logout");
        // start HTTP POST to create an entry
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(loginOut);

        // build the JSON entry
        httpPost.addHeader("Authorization", "AR-JWT "+token);

        // make the call and print the Location
        CloseableHttpResponse response = httpClient.execute(httpPost);
        String  loginOutDate = response.getFirstHeader("Date").getValue();
        runTimeLog.info("退出时间:"+loginOutDate);
        response.close();
        httpClient.close();
        runTimeLog.info("模拟退出成功");
        return token;
    }


    /**
     * 测试一个url返回的内容
     * @param url
     * @throws Exception
     */
    public  void testHttpUrl(String url)throws  Exception{
        url=ifNotNullSet(url,"http://idc.china-entercom.net:8081/X-admin-2.3/login.jsp");
        CloseableHttpClient httpClient = HttpClients.createDefault();//?创建httpClient实例
        HttpGet httpGet = new HttpGet(url);//?创建httpget实例
        httpGet.setHeader("User-Agent", "Mozilla/5.0?(Windows?NT?6.1;?Win64;?x64;?rv:50.0)?Gecko/20100101?Firefox/50.0");//?设置请求头消息User-Agent
        CloseableHttpResponse response = httpClient.execute(httpGet);//?执行http?get请求
        runTimeLog.info("Status:" + response.getStatusLine().getStatusCode());
        HttpEntity entity = response.getEntity();//?获取返回实体
        runTimeLog.info("Content-Type:" + entity.getContentType().getValue());
        //runTimeLog.info("网页内容："+EntityUtils.toString(entity,?"utf-8"));?//?获取网页内容
        response.close();//?response关闭
        httpClient.close();//?httpClient关闭

    }

    @Override
    public void run() {
        String token="";
        try {
            Thread.sleep(sleep*1000);
            JSONObject tempJsonObject=JSONUtil.parseObj(result);
            String ticketName=tempJsonObject.getStr("ticketName");
            String msg=tempJsonObject.getStr("msg");
            String MTP_status=tempJsonObject.getStr("status");
            String tense=tempJsonObject.getStr("tense");
            token=login(loginUrl,"","");
            if(token.indexOf("ERROR")>-1){
                runTimeLog.info( " 登录到Remedy获取token错误：");
            }else{
                String returnLocation=sendReult(sendToRemedyUrl,token,ticketName,msg ,MTP_status,tense);
            }
        } catch (Exception e) {
            e.printStackTrace();
            runTimeLog.info( " 发送结果到Remedy错误：");
            runTimeLog.printStackTrace(e);
        }finally {
            try {
                String loginOutDate=loginOut(loginOutUrl,token);
            } catch (Exception e) {
                e.printStackTrace();
                runTimeLog.info( "退出登录 错误：");
                runTimeLog.printStackTrace(e);
            }
        }
    }
}
