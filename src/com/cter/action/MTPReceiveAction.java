package com.cter.action;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.cter.bean.MTPA;
import com.cter.dao.impl.MTPProvisionDaoImpl;
import com.cter.entity.MTPProvision;
import com.cter.service.impl.MTPReceiveService;
import com.cter.util.BaseLog;
import com.cter.util.LoadPropertiestUtil;
import com.cter.util.SendResultToRemedy;
import com.cter.util.StringUtil;
import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


@Controller
public class MTPReceiveAction extends ActionSupport {


    private BaseLog log = new BaseLog("MTPQueryLog");


    @Autowired private MTPReceiveService mtpReceiveService;
    @Autowired private MTPProvisionDaoImpl mtpProvisionDao;

    private static Map<String, String> otherMap = LoadPropertiestUtil.loadProperties("config/other.properties");
    private static final String sendToRemedyUrl = otherMap.get("sendToRemedyUrl");
    private static String loginUrl = otherMap.get("loginUrl");
    private static String loginOutUrl = otherMap.get("loginOutUrl");


    /**
     * mtp自动化入口
     */
    public void addMtpRecordDetailed() {
        long start = System.currentTimeMillis();
        log.info("开始运行时间：" + DateUtil.now());
        String result = "";
        HttpServletRequest request = ServletActionContext.getRequest();
        request.getServletContext();
        String jsonStr = request.getParameter("jsonStr");
        log.info("页面调用了 MTPReceiveAction.addMtpRecordDetailed \n" + "调用的参数为：(" + jsonStr + ")");
        log.info("调用的ip getRemoteAddr：" + request.getRemoteAddr());
        result = mtpReceiveService.validateParam(jsonStr);
        SendResultToRemedy sendResultToRemedy = new SendResultToRemedy();
        sendResultToRemedy.setMTPQueryLog(log);
        MTPA mtpa = JSONUtil.toBean(jsonStr, MTPA.class);
        //如果有开始结束时间 就插入准备MTP表,status:insert
        if (!StringUtil.isBlank(mtpa.getStartTime()) && !StringUtil.isBlank(mtpa.getEndTime())) {
            Gson gson = new Gson();
            MTPProvision mtpProvision = new MTPProvision();
            mtpProvision.setCaseId(mtpa.getTicketName());
            mtpProvision.setJsonstr(jsonStr);
            mtpProvision.setStartTime(DateUtil.parse(mtpa.getStartTime(), "MM/dd/yy HH:mm:ss"));
            mtpProvision.setEndTime(DateUtil.parse(mtpa.getEndTime(), "MM/dd/yy HH:mm:ss"));
            mtpProvisionDao.updateData(mtpProvision);
            result = JSONUtil.toJsonStr(JSONUtil.toBean(result, JSONObject.class).put("status", "Y"));
            retString(result, log);
        } else if (JSONUtil.parseObj(result).getStr("status").equals("Y") || JSONUtil.parseObj(result).getStr("status").equals("P")) {
            retString(result, log);
            result = mtpReceiveService.addMtpRecordDetailed(jsonStr);
            String separator = File.separator;
            String uploadPath = request.getServletContext().getRealPath(separator + "mtp" + separator + DateUtil.format(new Date(), DatePattern.PURE_DATE_PATTERN) + separator);  //文件保存路径
            long endOfStart = ((System.currentTimeMillis() - start) / 1000);
            sendResultToRemedy.init(loginUrl, loginOutUrl, sendToRemedyUrl, result);
            sendResultToRemedy.run(endOfStart);
            result = "<pre>" + result + "</pre>";
        } else if (JSONUtil.parseObj(result).getStr("status").equals("N")) {
            Map<String, String> returnMap = new HashMap<>();
            returnMap.put("ticketName", mtpa.getTicketName());
            returnMap.put("status", "Y");
            returnMap.put("tense", mtpa.getTense());
            JSONUtil.toJsonStr(returnMap);
            returnMap.put("remedy_summary", "(" + JSONUtil.toBean(jsonStr, MTPA.class).getInternalSiteIdAll() + ") " + JSONUtil.parseObj(result).getStr("msg"));
            result = returnMap.get("remedy_summary");
            sendResultToRemedy.init(loginUrl, loginOutUrl, sendToRemedyUrl, JSONUtil.toJsonStr(returnMap));
            long endOfStart = ((System.currentTimeMillis() - start) / 1000);
            sendResultToRemedy.run(endOfStart);
        }
        log.info("返回字符串result：" + result);
        long end = System.currentTimeMillis();
        log.info("结束运行时间：" + DateUtil.now());
        log.info("执行线路数量：" + (StringUtil.isBlank(jsonStr) ? 0 : (JSONUtil.toBean(jsonStr, MTPA.class).getPePorts().size())) + "\t总时长:" + (end - start) / 1000.00 + "秒");
    }


    /**
     * 返回给界面 text 文本
     *
     * @param str 返回的数据字符串
     * @throws IOException
     */
    public static void retString(String str, BaseLog log) {
        HttpServletResponse response = ServletActionContext.getResponse();
        //重写方法配置，实现日期转换为字符串
        response.setContentType("text/json; charset=utf-8");
        response.setHeader("Cache-Control", "no-cache"); //设置头信息
        PrintWriter out = null;
        try {
            out = response.getWriter();
            log.info("返回到界面的参数：" + str);
            out.print(str);
            out.flush();
            out.close();
        } catch (IOException e) {
            log.printStackTrace(e);
        }

    }


    /**
     * 每10分钟执行一次
     */
    public void executeMTPProvisionBefore() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        List<MTPProvision> runBefore = mtpProvisionDao.getRunBefore();
        Gson gson = new Gson();
        for (MTPProvision mtpProvision : runBefore) {
            mtpProvisionDao.updateRuning("1", mtpProvision.getCaseId());
        }
        for (MTPProvision mtpProvision : runBefore) {
            executorService.execute(new MTPProvisionThread(mtpReceiveService, mtpProvisionDao, mtpProvision, "before"));
        }
        ThreadUtil.sleep(15 * 60 * 1000);
        for (MTPProvision mtpProvision : runBefore) {
            if (JSONUtil.toBean(mtpProvision.getJsonstr(), MTPA.class).getPePorts().size() < 100) {
                mtpProvisionDao.updateRuning("0", mtpProvision.getCaseId());
            }
        }
        executorService.awaitTermination(30,TimeUnit.MINUTES);
    }

    /**
     * 每3分钟执行一次
     */
    public void executeMTPProvisionAfter() throws Exception {

        ExecutorService executorService = Executors.newFixedThreadPool(5);
        List<MTPProvision> runAfter = mtpProvisionDao.getRunAfter();
        for (MTPProvision mtpProvision : runAfter) {
            mtpProvisionDao.updateRuning("1", mtpProvision.getCaseId());
        }
        for (MTPProvision mtpProvision : runAfter) {
            executorService.execute(new MTPProvisionThread(mtpReceiveService, mtpProvisionDao, mtpProvision, "after"));
        }
        ThreadUtil.sleep(15 * 60 * 1000);
        for (MTPProvision mtpProvision : runAfter) {
            if (JSONUtil.toBean(mtpProvision.getJsonstr(), MTPA.class).getPePorts().size() < 100) {
                mtpProvisionDao.updateRuning("0", mtpProvision.getCaseId());
            }
        }
        executorService.awaitTermination(30,TimeUnit.MINUTES);
    }


    /**
     * 根据主机名称和命令执行命令
     */
    public void executiveCommand() {


    }


}
