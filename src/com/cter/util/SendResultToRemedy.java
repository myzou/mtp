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

import java.util.Date;
import java.util.Map;

/**
 * ���ͽ����remedy
 * @author op1768
 * @create 2019-10-24 11:42
 * @project mtp
 */
public class SendResultToRemedy {


    private  BaseLog MTPQueryLog = new BaseLog("MTPQueryLog");
    String loginUrl;String loginOutUrl;String sendToRemedyUrl;String result; //�̵߳ȴ�
    private static Map<String, String> otherMap = LoadPropertiestUtil.loadProperties("config/other.properties");
    private static final String loginUserName = otherMap.get("loginUserName");
    private static final String loginPassword = otherMap.get("loginPassword");
    private static final long sleep = Long.valueOf(otherMap.get("sleep"));
    private static final String host = otherMap.get("host");
    private static final String formEmail = otherMap.get("formEmail");
    private static final String emailPassword = otherMap.get("emailPassword");
    private static final String toStr = otherMap.get("to");

    public BaseLog getMTPQueryLog() {
        return MTPQueryLog;
    }

    public void setMTPQueryLog(BaseLog MTPQueryLog) {
        this.MTPQueryLog = MTPQueryLog;
    }

    public static void main(String[] args)  {
        SendMailUtil mail=SendMailUtil.getInstance();
        String[]  to=toStr.split(";");
        String [] cs =null;
        String subject="test Case "+"#114564"+" (MTP check) Send to Remedy failed";
        String content=DateUtil.getNow()+":Maintenance Case "+"#114564"+"<br>(MTP check)Send to Remedy failed<br>Please notify developer";
        String fileList[]=null;
        try {
            mail.send(to,cs, null , subject, content, formEmail, fileList, host, emailPassword);
        } catch (Exception ex) {
            ex.printStackTrace();
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
     * ���ͽ����remedy
     * @param token ��½��token������login���
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
        MTPQueryLog.info("���͵�remedy�����ݣ�"+ JSONUtil.toJsonStr(jsonObject));
        String sendJson=JSONUtil.toJsonStr(jsonObject);
        httpPost.setEntity(new StringEntity(sendJson, ContentType.APPLICATION_JSON));
        httpPost.addHeader("Authorization", "AR-JWT " + token);

        // make the call and print the Location
        CloseableHttpResponse response = httpClient.execute(httpPost);
        String  returnLocation = response.getFirstHeader("Location").getValue();
        MTPQueryLog.info("���ܷ��ص�ֵ��"+returnLocation);
        return returnLocation;
    }

    /**
     * ����ַ���Ϊ�վ�����Ϊָ���ַ����������Ϊ�վ����
     * @param ifStr   �ж��ַ���
     * @param setStr  ���õ��ַ���
     * @return
     */
    public static String ifNotNullSet(String ifStr,String setStr){
        if(StrUtil.isBlank(ifStr)){
            return setStr;
        }
        return ifStr;
    }

    /**
     * ��½��remedy ��ȡtoken
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
        MTPQueryLog.info("�ɹ���ȡ��:"+token);
        response.close();
        httpClient.close();
        MTPQueryLog.info("ģ���½�ɹ�");
        return token;
    }

    /**
     * �˳���Ӧ��token
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
        MTPQueryLog.info("�˳�ʱ��:"+loginOutDate);
        response.close();
        httpClient.close();
        MTPQueryLog.info("ģ���˳��ɹ�");
        return token;
    }


    /**
     * ����һ��url���ص�����
     * @param url
     * @throws Exception
     */
    public  void testHttpUrl(String url)throws  Exception{
        url=ifNotNullSet(url,"http://idc.china-entercom.net:8081/X-admin-2.3/login.jsp");
        CloseableHttpClient httpClient = HttpClients.createDefault();//?����httpClientʵ��
        HttpGet httpGet = new HttpGet(url);//?����httpgetʵ��
        httpGet.setHeader("User-Agent", "Mozilla/5.0?(Windows?NT?6.1;?Win64;?x64;?rv:50.0)?Gecko/20100101?Firefox/50.0");//?��������ͷ��ϢUser-Agent
        CloseableHttpResponse response = httpClient.execute(httpGet);//?ִ��http?get����
        MTPQueryLog.info("Status:" + response.getStatusLine().getStatusCode());
        HttpEntity entity = response.getEntity();//?��ȡ����ʵ��
        MTPQueryLog.info("Content-Type:" + entity.getContentType().getValue());
        //MTPQueryLog.info("��ҳ���ݣ�"+EntityUtils.toString(entity,?"utf-8"));?//?��ȡ��ҳ����
        response.close();//?response�ر�
        httpClient.close();//?httpClient�ر�

    }

    public void run(long endOfStart) {
        String token="";
        long sleepTime=(sleep-endOfStart>0L)?(sleep-endOfStart):1;
        JSONObject tempJsonObject=JSONUtil.parseObj(result);
        String ticketName=tempJsonObject.getStr("ticketName");
        String msg=tempJsonObject.getStr("remedy_summary");
        String MTP_status=tempJsonObject.getStr("status");
        String tense=tempJsonObject.getStr("tense");
        try {
            Thread.sleep(sleepTime*1000);

            token=login(loginUrl,"","");
            if(token.indexOf("ERROR")>-1){
                MTPQueryLog.info( " ��¼��Remedy��ȡtoken����");
            }else{
                String returnLocation=sendReult(sendToRemedyUrl,token,ticketName,msg ,MTP_status,tense);
            }
        } catch (Exception e) {
            e.printStackTrace();
            MTPQueryLog.info( " ���ͽ����Remedy����");
            MTPQueryLog.printStackTrace(e);
            SendMailUtil mail=SendMailUtil.getInstance();
            String[]  to=toStr.split(";");
            String [] cs =null;
            String subject="Case "+ticketName+"(MTP Check)Send to Remedy failed";
            String content=DateUtil.getDate(new Date())+"<br>Maintenance Case #"+ticketName+"<br>(MTP Check)Send to Remedy failed<br>Please notify developer";
            String fileList[]=null;
            try {
                mail.send(to,cs, null , subject, content, formEmail, fileList, host, emailPassword);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }finally {
            try {
                String loginOutDate=loginOut(loginOutUrl,token);
            } catch (Exception e) {
                e.printStackTrace();
                MTPQueryLog.info( "�˳���¼ ����");
                MTPQueryLog.printStackTrace(e);
            }
        }
    }
}
