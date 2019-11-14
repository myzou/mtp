package com.cter.action;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.cter.bean.MTPA;
import com.cter.service.impl.MTPReceiveService;
import com.cter.service.impl.ZQSysRoleService;
import com.cter.util.BaseLog;
import com.cter.util.DateUtil;
import com.cter.util.HttpDataManageUtil;
import com.cter.util.StringUtil;
import com.cter.util.mtp.GetParam;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

@Controller
public class ParamAction extends ActionSupport {

    private static final long serialVersionUID = -566368986215919922L;

    private BaseLog log = new BaseLog("MTPQueryLog");
    private BaseLog runTimeLog = new BaseLog("RunTimeLog");

    @Autowired
    private ZQSysRoleService roleService;


    @Autowired
    private MTPReceiveService mtpReceiveService;


    /**
     * 根据excel获取参数
     */
    public void getParam() {
        long start = System.currentTimeMillis();
        runTimeLog.info("开始运行时间：" + cn.hutool.core.date.DateUtil.now());
        HttpServletRequest request = ServletActionContext.getRequest();
        String upLoadFiles = request.getParameter("upLoadFiles");
        String period = request.getParameter("period");
        String paramString = GetParam.getConnJson("", upLoadFiles, period);
        log.info("upLoadFiles:" + upLoadFiles);
        log.info("period:" + period);

        if (!StringUtil.isBlank(paramString)) {
            String returnString = "";
            if (paramString.equals("根据excel无法转换参数。请查看excel格式是否有错误。")) {
                returnString = "请检查excel文件内容是否正确，以及确定excel文件数量为一个";
                HttpDataManageUtil.retString(returnString, log);
                log.info(returnString);
            } else {
                MTPA jsonParam = JSONUtil.toBean(paramString, MTPA.class);
                Map<String, String> urlMap = mtpReceiveService.getUrl(jsonParam.getTicketName().trim(), jsonParam.getTense().trim());
                String htmlPath = urlMap.get("htmlPath");
                int peportsSize = jsonParam.getPePorts().size();
                String passDate = DateUtil.getDateStr(new Date(start + peportsSize * 4000), "yyyy-mm-dd HH:mm:ss");
                returnString = "返回的参数：<br>" + paramString + "<br>" + "稍后请访问下面链接查看结果：<br>" +
                        "<a lay-ignore href=\"" + htmlPath + "\" target=\"_blank\">" + htmlPath + "</a><br>" +
                        "共" + peportsSize + "条线路，执行一条大约所需4秒，执行完成时间预计为：" + passDate;
                HttpDataManageUtil.retString(returnString, log);
                log.info(returnString);
                log.info("getParam调用了 mtpReceiveService.addMtpRecordDetailed \n调用的参数为：(" + paramString + ")");
                //根据获取的参数执行命令
                String result = mtpReceiveService.addMtpRecordDetailed(paramString);
            }

        }

        long end = System.currentTimeMillis();
        runTimeLog.info("结束运行时间：" + cn.hutool.core.date.DateUtil.now());
        runTimeLog.info("执行线路数量：" + (JSONUtil.toBean(paramString, MTPA.class).getPePorts().size()) + "\t总时长:" + (end - start) / 1000.00);
    }


}
