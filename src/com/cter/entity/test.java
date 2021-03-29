package com.cter.entity;


import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.cter.util.BaseLog;
import com.cter.util.DateUtil;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.math.BigDecimal;
import java.util.*;

public class test   {
    private static BaseLog log = new BaseLog("testLog");


    public static void main(String[] args) throws Exception{
    }

    /**
     * 前后 Delay 对比
     * before 10m以下    2ms出异常
     * before 10-50ms   5ms 出异常
     * before 50-100ms     8ms出异常
     * before 100ms以上    10ms出异常
     * 异常true，正常false
     * @param beforeDelay 之前 Delay
     * @param afterDelay  之后 Delay
     * @return
     */
    public static boolean comparisonDelay(BigDecimal beforeDelay, BigDecimal afterDelay){
        boolean flag=false;//前后对比Delay 异常为true,默认 为false
        beforeDelay.doubleValue();
        if(beforeDelay.floatValue()<10&&afterDelay.subtract(beforeDelay).floatValue() >= 2){
            return true;
        }
        if(beforeDelay.floatValue()<50&&afterDelay.subtract(beforeDelay).floatValue() >= 5){
            return true;
        }
        if(beforeDelay.floatValue()<100&&afterDelay.subtract(beforeDelay).floatValue() >= 8){
            return true;
        }
        if(beforeDelay.floatValue()>=100&&afterDelay.subtract(beforeDelay).floatValue() >= 10){
            return true;
        }
        return flag;
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
     20201217
     */
    public static   void sendReult( )throws Exception{
        String token="eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJcL1RnSk5WTVd3UXB2ckdLMnN1YUxDa25GWGxTdFpqOTVoQjcrQWQwTTVzNU45XC9LTGMwRE9DOHNuK0tnK3dHRk5zbTJUamR4MkJUS2V2bDdMcURRNUFQRG5sdDhVVG5uQjdrTXV3eEl3cExnUEpCRVVuUW5hQXc9PSIsIm5iZiI6MTYwODE2Njk4NywiaXNzIjoiSEtIS0dTMjczLmNwY25ldC5sb2NhbCIsImV4cCI6MTYwODE3MDcwNywiX2NhY2hlSWQiOjE4NjE0LCJpYXQiOjE2MDgxNjcxMDcsImp0aSI6IklER0FBNVYwRjBKOUZBUUw2TE85UUs4RlVVUUwyRSJ9.OnTYzfZJgRR_GAD6cuV-3vbo06_TfLBTbXi9922oACQ";
        // start HTTP POST to create an entry
        log.info("开始时间："+DateUtil.getNow());
        for (int i = 1; i < 201; i++) {
            log.info(i+"");
            CloseableHttpClient httpClient = HttpClients.createDefault();
            String AffectedEquipment="CNSHHCJX3111SW,HKGALC404051SW,HKGALC404052SW,HKGCTT440501MSW,HKGCTT440502MSW,HKHKGCTT2049901CSW,HKHKGCTT2049902CSW,KRSELSKB1001SW,KRSELSKB1002SW,CNBEJCEI1001SW,CNGUZKXC1001SW,CNGUZKXC1002SW,CNSHZBWX7001SW,CNSHZBWX7002SW,SGSINDRT1005SW,SGSINDRT1006SW,AUMELEQX1001SW,AUMELEQX1002SW,CNBEJCEI3101MSW,CNBEJCEI3101SW,CNBEJCEI3102MSW,CNBEJCEI3102SW,CNBEJCEI7001SW,CNBEJDEX1005SW,CNBEJDEX1006SW,CNBEJDEX1007SW,CNBEJDEX3101CSW,CNBEJDEX3102CSW,CNBEJDEX3103CSW,CNBEJDEX3104CSW,CNBEJDEX3115SW,CNBEJDEX3116SW,CNBEJDEX3117SW,CNBEJDEX7003SW,CNBEJDEX7004SW,CNBEJJIC1002SW,CNBEJJIC1006SW,CNBEJJIC1007SW,CNBEJJIC7001SW,CNCHQLOZ1001SW,CNCHQLOZ1002SW,CNDOGMQT1003SW,CNDOGMQT1004SW,CNFUZYAL1001SW,CNGUZKXC3101MSW,CNGUZKXC3101SW,CNGUZKXC3102MSW,CNGUZKXC3102SW,CNGUZYUJ3101CSW,CNGUZYUJ3102CSW,CNGUZYUJ3104SW,CNHAKQUH1001SW,CNHAKQUH1002SW,CNHEFYUL1001SW,CNHEFYUL1002SW,CNJIMYAQ1001SW,CNJIMYAQ1002SW,CNJIXCXY1001SW,CNJIXCXY1002SW,CNKUMDAT1001SW,CNKUMDAT1002SW,CNLAFLZL1001SW,CNLAFLZL1002SW,CNNACHUJ1001SW,CNNACHUJ1002SW,CNNATZHX1001SW,CNNATZHX1002SW,CNNIBDAZ1001SW,CNNIBDAZ1002SW,CNNIBGUH1001SW,CNNIBGUH1002SW,CNQIDZHL1001SW,CNSAYYIB1001SW,CNSAYYIB1002SW,CNSHHCJX1007SW,CNSHHCJX1008SW,CNSHHCJX1009SW,CNSHHCJX1010SW,CNSHHCJX3103CSW,CNSHHCJX3104CSW,CNSHHCJX3109SW,CNSHHCJX3110SW,CNSHHCJX3201MSW,CNSHHCJX3201SW,CNSHHCJX3202MSW,CNSHHCJX3202SW,CNSHHCJX7003SW,CNSHHCJX7004SW,CNSHHCJX8003CSW,CNSHHCJX8004CSW,CNSHHFTX1007SW,CNSHHGDS3101CSW,CNSHHGDS3103CSW,CNSHHSDS1001SW,CNSHYCXL1001SW,CNSHYCXL1002SW,CNSHYRJY1001SW,CNSHYRJY1002SW,CNSHZBWX1001SW,CNSHZBWX1002SW,CNSHZHOL1001SW,CNSHZHOL1002SW,CNSJZTGD1001SW,CNSJZTGD1002SW,CNTASCYY1001SW,CNTASCYY1002SW,CNTAYXJC1001SW,CNTAYXJC1002SW,CNTIJKEY1001SW,CNTIJKEY1002SW,CNXIAHUJ1001SW,CNXIAHUJ1002SW,CNXIMHUX1002SW,CNXIMHUX1003SW,CNYAZYZJ1001SW,CNYAZYZJ1002SW,DEFRAEQX1001SW,DEFRAEQX1002SW,DEFRAEQX3101MSW,DEFRAEQX3101SW,DEFRAEQX3102MSW,DEFRAEQX3102SW,HKHKGALC3101CSW,HKHKGALC3102CSW,HKHKGALC3111SW,HKHKGALC3112SW,HKHKGALC3113SW,HKHKGALC3114SW,HKHKGALC8001CSW,HKHKGALC8002CSW,HKHKGCTT3130SW,HKHKGCTT3131SW,HKHKGCTT3132SW,HKHKGCTT3133SW,HKHKGCTT3141CSW,HKHKGCTT3142CSW,HKHKGCTT3145SW,HKHKGCTT3146SW,HKHKGCTT3147SW,HKHKGCTT3148SW,HKHKGCTT4843101CSW,HKHKGCTT4843102CSW,HKHKGCTT8001CSW,HKHKGCTT8002CSW,JPTYOATT3101SW,JPTYOATT3102SW,RUMOWMAR3101SW,RUMOWMAR3102SW,SGSINDRT3101MSW,SGSINDRT3101SW,SGSINDRT3102MSW,SGSINDRT3102SW,SGSINDRT3103SW,SGSINDRT3104SW,SGSINDRT3105SW,SGSINDRT3106SW,SGSINDRT3107SW,SHHCJX443211CSW,SHHCJX443212CSW,TCHFET445301SW,TCHFET445302SW,TWTCHFET3301SW,TWTCHFET3302SW,UKLONCOR1002SW,UKLONCOR1004SW,UKLONCOR3101SW,UKLONCOR3102SW,USLAXCOS3101MSW,USLAXCOS3101SW,USLAXCOS3102MSW,USLAXCOS3102SW,USNYCCOS3101SW,USNYCCOS3102SW,VNNAMD445301SW,VNNAMD445302SW,VNSGNSAG1001SW,VNSGNSAG1002SW,VNSGNVNP1001SW,VNSGNVNP1002SW,ZACPTINS1001SW,ZACPTINS1002SW,ZACPTINS3101SW,ZACPTINS3102SW,HKGCTT2049901SW,HKGCTT2049902SW,HKGCTT440501SSW,HKGCTT440502SSW,SHHCJX443211SSW,SHHCJX443212SSW,TWTPEASP3121SW,TWTPEASP3122SW,CNSHHCJX3105SSW,CNSHHCJX3106SSW,CNBEJDEX3121SW,CNBEJDEX3122SW,CNGUZYUJ3101SSW,CNGUZYUJ3102SSW,CNSHGDS3121SW,CNSHGDS3122SW,CNSHHCJX3201SSW,CNSHHCJX3202SSW,CNSHHCJX8005SW,CNSHHCJX8006SW,HKHKGALC3105SW,HKHKGALC3106SW,HKHKGALC3201SSW,HKHKGALC3202SSW,HKHKGCTT3151SW,HKHKGCTT3152SW,SGSINDRT3101SSW,SGSINDRT3102SSW,SGSINDRT3103SSW,SGSINDRT3104SSW,HKHKGALC8001SSW,HKHKGALC8002SSW";
            String GUID="";

            HttpPost httpPost = new HttpPost("http://10.180.27.11:8008/api/arsys/v1/entry/API_NOC_Maintenance");

            JSONObject jsonObject=new JSONObject();
            JSONObject valuesObject=new JSONObject();
            valuesObject.put("Start Time","11/18/2020 01:00:00");
            valuesObject.put("End Time","11/18/2020 06:00:00");
            valuesObject.put("Affected Equipment",AffectedEquipment);
            valuesObject.put("Type","0");
            valuesObject.put("User","Maintenance");
            valuesObject.put("GUID","GUID0000-"+i);
            valuesObject.put("Submitter","Maintenance");
            valuesObject.put("Short Description","Maintenance");
            jsonObject.put("values",valuesObject);
            log.info("发送到remedy的内容："+ JSONUtil.toJsonStr(jsonObject));
            String sendJson=JSONUtil.toJsonStr(jsonObject);
            httpPost.setEntity(new StringEntity(sendJson, ContentType.APPLICATION_JSON));
            httpPost.addHeader("Authorization", "AR-JWT " + token);

            // make the call and print the Location
            CloseableHttpResponse response = httpClient.execute(httpPost);
            String  returnLocation = response.getFirstHeader("Location").getValue();
            log.info ("接受返回的值："+returnLocation);
            log.info ("================================================================================");

        }
        log.info("结束时间："+DateUtil.getNow());
    }


}
