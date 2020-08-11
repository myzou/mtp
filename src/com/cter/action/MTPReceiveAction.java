package com.cter.action;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.cter.bean.MTPA;
import com.cter.bean.ResultList;
import com.cter.service.impl.MTPReceiveService;
import com.cter.util.SendResultToRemedy;
import com.cter.util.BaseLog;
import com.cter.util.DateUtil;
import com.cter.util.LoadPropertiestUtil;
import com.cter.util.StringUtil;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;


@Controller
public class MTPReceiveAction extends ActionSupport {


    private BaseLog log=new BaseLog("MTPQueryLog");


    @Autowired
    private MTPReceiveService mtpReceiveService;


    private static Map<String, String> otherMap = LoadPropertiestUtil.loadProperties("config/other.properties");
    private static final String sendToRemedyUrl = otherMap.get("sendToRemedyUrl");
    private static String loginUrl =  otherMap.get("loginUrl");
    private static String loginOutUrl =  otherMap.get("loginOutUrl");
    /**
     * mtp�Զ������
     *
     */
    public void addMtpRecordDetailed() {
        long start= System.currentTimeMillis();
        log.info("��ʼ����ʱ�䣺"+ cn.hutool.core.date.DateUtil.now());
        String result = "";
        HttpServletRequest request = ServletActionContext.getRequest();
        String jsonStr=request.getParameter("jsonStr");
        log.info("ҳ������� MTPReceiveAction.addMtpRecordDetailed \n" +
                "���õĲ���Ϊ��(" + jsonStr + ")");
        log.info("���õ�ip getRemoteAddr��" + request.getRemoteAddr());
        result = mtpReceiveService.validateParam(jsonStr);
        retString(result, log);

        if(JSONUtil.parseObj(result).getStr("status").equals("Y")){
            result = mtpReceiveService.addMtpRecordDetailed(jsonStr);
            String separator = File.separator;
            String uploadPath = request.getServletContext().getRealPath(separator + "mtp" + separator + DateUtil.getDateStryyyyMMdd(new Date()) + separator);  //�ļ�����·��
            SendResultToRemedy sendResultToRemedy=new SendResultToRemedy();
//            sendResultToRemedy.setMTPQueryLog(log);
            long endOfStart= ((System.currentTimeMillis()-start)/1000);
            sendResultToRemedy.init(loginUrl,loginOutUrl,sendToRemedyUrl,result);
            sendResultToRemedy.run(endOfStart);

            result = "<pre>"   + result + "</pre>";
        }

        log.info("�����ַ���result��" + result);
        long end= System.currentTimeMillis();
        log.info("��������ʱ�䣺"+ cn.hutool.core.date.DateUtil.now());
        log.info("ִ����·������"+(StringUtil.isBlank(jsonStr)?0:(JSONUtil.toBean(jsonStr, MTPA.class).getPePorts().size()))+"\t��ʱ��:"+(end-start)/1000.00+"��");
    }



    /**
     * ���ظ����� text �ı�
     * @param str ���ص������ַ���
     * @throws IOException
     */
    public static void 	retString(String str,BaseLog log ) {
        HttpServletResponse response=ServletActionContext.getResponse();
        //��д�������ã�ʵ������ת��Ϊ�ַ���
        response.setContentType("text/json; charset=utf-8");
        response.setHeader("Cache-Control", "no-cache"); //����ͷ��Ϣ
        PrintWriter out=null;
        try {
            out = response.getWriter();
            log.info("���ص�����Ĳ�����"+str);
            out.print(str);
            out.flush();
            out.close();
        } catch (IOException e) {
            log.printStackTrace(e);
        }

    }
    /**
     *
     * �����������ƺ�����ִ������
     *
     */
    public void executiveCommand() {


    }


}
