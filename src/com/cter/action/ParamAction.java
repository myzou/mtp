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
     * ����excel��ȡ����
     */
    public void getParam() {
        long start = System.currentTimeMillis();
        runTimeLog.info("��ʼ����ʱ�䣺" + cn.hutool.core.date.DateUtil.now());
        HttpServletRequest request = ServletActionContext.getRequest();
        String upLoadFiles = request.getParameter("upLoadFiles");
        String period = request.getParameter("period");
        String paramString = GetParam.getConnJson("", upLoadFiles, period);
        log.info("upLoadFiles:" + upLoadFiles);
        log.info("period:" + period);

        if (!StringUtil.isBlank(paramString)) {
            String returnString = "";
            if (paramString.equals("����excel�޷�ת����������鿴excel��ʽ�Ƿ��д���")) {
                returnString = "����excel�ļ������Ƿ���ȷ���Լ�ȷ��excel�ļ�����Ϊһ��";
                HttpDataManageUtil.retString(returnString, log);
                log.info(returnString);
            } else {
                MTPA jsonParam = JSONUtil.toBean(paramString, MTPA.class);
                Map<String, String> urlMap = mtpReceiveService.getUrl(jsonParam.getTicketName().trim(), jsonParam.getTense().trim());
                String htmlPath = urlMap.get("htmlPath");
                int peportsSize = jsonParam.getPePorts().size();
                String passDate = DateUtil.getDateStr(new Date(start + peportsSize * 4000), "yyyy-mm-dd HH:mm:ss");
                returnString = "���صĲ�����<br>" + paramString + "<br>" + "�Ժ�������������Ӳ鿴�����<br>" +
                        "<a lay-ignore href=\"" + htmlPath + "\" target=\"_blank\">" + htmlPath + "</a><br>" +
                        "��" + peportsSize + "����·��ִ��һ����Լ����4�룬ִ�����ʱ��Ԥ��Ϊ��" + passDate;
                HttpDataManageUtil.retString(returnString, log);
                log.info(returnString);
                log.info("getParam������ mtpReceiveService.addMtpRecordDetailed \n���õĲ���Ϊ��(" + paramString + ")");
                //���ݻ�ȡ�Ĳ���ִ������
                String result = mtpReceiveService.addMtpRecordDetailed(paramString);
            }

        }

        long end = System.currentTimeMillis();
        runTimeLog.info("��������ʱ�䣺" + cn.hutool.core.date.DateUtil.now());
        runTimeLog.info("ִ����·������" + (JSONUtil.toBean(paramString, MTPA.class).getPePorts().size()) + "\t��ʱ��:" + (end - start) / 1000.00);
    }


}
