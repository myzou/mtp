package com.cter.action;

import cn.hutool.json.JSONUtil;
import com.cter.bean.MTPA;
import com.cter.dao.impl.MTPProvisionDaoImpl;
import com.cter.entity.MTPProvision;
import com.cter.service.impl.MTPReceiveService;
import com.cter.util.BaseLog;
import com.cter.util.LoadPropertiestUtil;
import com.cter.util.SendResultToRemedy;
import com.google.gson.Gson;

import java.io.File;
import java.util.HashMap;
import java.util.Map;


public class MTPProvisionThread implements Runnable {


    private MTPReceiveService mtpReceiveService;
    private MTPProvisionDaoImpl mtpProvisionDao;
    String tense;
    MTPProvision mtpProvision;
    private BaseLog log = new BaseLog("MTPQueryLog");


    private static Map<String, String> otherMap = LoadPropertiestUtil.loadProperties("config/other.properties");
    private static final String sendToRemedyUrl = otherMap.get("sendToRemedyUrl");
    private static String loginUrl = otherMap.get("loginUrl");
    private static String loginOutUrl = otherMap.get("loginOutUrl");
    Gson gson = new Gson();


    public MTPProvisionThread(MTPReceiveService mtpReceiveService, MTPProvisionDaoImpl mtpProvisionDao, MTPProvision mtpProvision, String tense) {
        this.mtpReceiveService = mtpReceiveService;
        this.mtpProvisionDao = mtpProvisionDao;
        this.mtpProvision = mtpProvision;
        this.tense = tense;
    }

    @Override
    public void run() {
        long endOfStart = 120L;
        SendResultToRemedy sendResultToRemedy = new SendResultToRemedy();
        sendResultToRemedy.setMTPQueryLog(log);
        String result = "";
        Map<String, String> returnMap = new HashMap<>();
        returnMap.put("ticketName", mtpProvision.getCaseId());
        returnMap.put("status", "Y");
        JSONUtil.toJsonStr(returnMap);
        String jsonStr = mtpProvision.getJsonstr();
        MTPA mtpa = (MTPA) gson.fromJson(jsonStr, MTPA.class);


        if (tense.equals("after")) {
            returnMap.put("tense", "after");
            long start = System.currentTimeMillis();
            mtpa.setTense("after");
            jsonStr = JSONUtil.toJsonStr(mtpa);
            try {
                String validateResult = mtpReceiveService.validateParam(jsonStr);
                if (JSONUtil.parseObj(validateResult).getStr("status").equals("N")) {
                    returnMap.put("remedy_summary", JSONUtil.parseObj(validateResult).getStr("msg"));
                    result = returnMap.get("remedy_summary");
                    sendResultToRemedy.init(loginUrl, loginOutUrl, sendToRemedyUrl, JSONUtil.toJsonStr(returnMap));
                    sendResultToRemedy.run(endOfStart);
                } else {
                    result = mtpReceiveService.addMtpRecordDetailed(jsonStr);
                    sendResultToRemedy.init(loginUrl, loginOutUrl, sendToRemedyUrl, result);
                    sendResultToRemedy.run(endOfStart);
                    result = "<pre>" + result + "</pre>";
                }
                log.info("返回字符串result：" + result);
                long end = System.currentTimeMillis();
                log.info("\t总时长:" + (end - start) / 1000.00 + "秒");
                mtpProvisionDao.updateRunAfter("1", mtpProvision.getCaseId());
                mtpProvisionDao.updateRuning("0", mtpProvision.getCaseId());
            } catch (Exception e) {
                e.printStackTrace();
                mtpProvisionDao.updateRunAfter("3", mtpProvision.getCaseId());
                mtpProvisionDao.updateRuning("0", mtpProvision.getCaseId());
            }
        } else {
            returnMap.put("tense", "before");
            long start = System.currentTimeMillis();
            mtpa.setTense("before");
            jsonStr = JSONUtil.toJsonStr(mtpa);
            try {
                String validateResult = mtpReceiveService.validateParam(jsonStr);
                if (JSONUtil.parseObj(validateResult).getStr("status").equals("N")) {
                    returnMap.put("remedy_summary",  JSONUtil.parseObj(validateResult).getStr("msg"));
                    result = returnMap.get("remedy_summary");
                    sendResultToRemedy.init(loginUrl, loginOutUrl, sendToRemedyUrl, JSONUtil.toJsonStr(returnMap));
                    sendResultToRemedy.run(endOfStart);
                } else {
                    result = mtpReceiveService.addMtpRecordDetailed(jsonStr);
                    sendResultToRemedy.init(loginUrl, loginOutUrl, sendToRemedyUrl, result);
                    sendResultToRemedy.run(endOfStart);
                    result = "<pre>" + result + "</pre>";
                }
                log.info("返回字符串result：" + result);
                long end = System.currentTimeMillis();
                log.info("\t总时长:" + (end - start) / 1000.00 + "秒");
                mtpProvisionDao.updateRunBefore("1", mtpProvision.getCaseId());
                mtpProvisionDao.updateRuning("0", mtpProvision.getCaseId());
            } catch (Exception e) {
                e.printStackTrace();
                mtpProvisionDao.updateRunBefore("3", mtpProvision.getCaseId());
                mtpProvisionDao.updateRuning("0", mtpProvision.getCaseId());
            }
        }


    }
}
