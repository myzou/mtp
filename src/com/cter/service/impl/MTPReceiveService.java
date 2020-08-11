package com.cter.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.cter.bean.*;
import com.cter.dao.impl.MtpRecordDetailedDaoImpl;
import com.cter.dao.impl.TrunkInfoMtpDaoImpl;
import com.cter.entity.MtpRecordDetailed;
import com.cter.entity.ZqData;
import com.cter.rsa.RSAEncrypt;
import com.cter.util.*;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.*;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;


@Service("MTPReceiveService")
@Transactional
public class MTPReceiveService {

    @Autowired
    private MtpRecordDetailedDaoImpl mtpRecordDetailedDaoImpl;


    private static BaseLog ExecuteCommandLog = new BaseLog("ExecuteCommandLog");
    private static BaseLog MTPQueryLog = new BaseLog("MTPQueryLog");

    //���ʵ�url
    private static String tempErrorUrl = "";


    @Autowired
    private TrunkInfoMtpDaoImpl trunkInfoMtpDaoImpl;
    private static Map<String, String> otherMap = LoadPropertiestUtil.loadProperties("config/other.properties");
    private static final String mtp_project = otherMap.get("mtp_project");
    private static String GGW_URL = otherMap.get("GGW_URL");
    private static String LOGIN_GGW_URL = otherMap.get("LOGIN_GGW_URL");


    public LayuiPage<MtpRecordDetailed> findMtpRecordDetailed(Map<String, String> map, LayuiPage<MtpRecordDetailed> layui) {
        mtpRecordDetailedDaoImpl.findMtpRecordDetailedPage(map, layui);
        return layui;
    }


    public int udpateCaseStatus(Map<String, String> map) {
        String case_status = map.get("case_status");
        int i = mtpRecordDetailedDaoImpl.udpateCaseStatus(map);
        return i;
    }


    /**
     * ͨ����ַ���� �����ٴε���
     *
     * @param url
     * @param flag
     * @return
     */
    public static String getUrl(String url, int flag) {
        Map<String, String> paramMap = new HashMap<String, String>();
        String returnStr = null;
        try {
            returnStr = HttpUtil.get(url);
        } catch (Exception e) {
            e.printStackTrace();
            if (flag == 0) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                returnStr = getUrl(url, flag + 1);
            } else {
                e.printStackTrace();
            }
        }
        return returnStr;
    }

    /**
     * �滻Ϊ����ggw�������ַ�
     *
     * @param str
     * @return
     */
    public static String urlReplace(String str) {
        str = str.replace("\"", "%22");
        str = str.replace("\\+", "%2B");
        str = str.replace("\\/ ", "%2F");
        str = str.replace(" ", "%20");
        str = str.replace("\\?", "%3F");
        str = str.replace("\\%", "%25");
        str = str.replace("\\#", "%23");
        str = str.replace("\\&", "%26");
        return str;
    }


    /**
     * ����dickson��api����ѯ�豸��ip��
     * ��������Դ�� Combined Tool
     *
     * @return
     */
    public static String getDeviceByIP(HashMap<String, String> map, String deviceName) {
        try {
            if (map == null) {
                return "";
            } else {
                if (StringUtil.isBlank(map.get(deviceName))) {
                    String device_get_url = otherMap.get("device_get_url");
                    String ip = HttpUtil.get(device_get_url.replace("deviceName", deviceName));
                    if (!StringUtil.isBlank(ip) && !ip.equals("none")) {
                        map.put(deviceName, ip);
                        return ip;
                    } else {
                        return "";
                    }
                } else {
                    return map.get(deviceName);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "GGWAPI��ѯ�豸�����Ѿ��ر�";
        }
    }


    /**
     * ��ȡִ��������
     *
     * @return
     */
    public static String getExecteResult() {
        return null;
    }


    /**
     * ��֤�����Ƿ�������
     */
    public String validateParam(String jsonStr) {
        Map<String, String> returnMap = new HashMap<>();
        returnMap.put("status", "N");
        if (StringUtil.isBlank(jsonStr)) {
            returnMap.put("msg", "jsonStr cannot be empty");
            return JSONUtil.toJsonStr(returnMap);
        }
        String tempString = "";
        try {

            MTPA mtpa = JSONUtil.toBean(jsonStr, MTPA.class);
            List<PePort> pePorts = mtpa.getPePorts();
            if (StringUtil.isBlank(mtpa.getTicketName())) {
                returnMap.put("msg", "ticketName cannot be empty");
                return JSONUtil.toJsonStr(returnMap);
            }
            if (StringUtil.isBlank(mtpa.getInternalSiteIdAll())) {
                returnMap.put("msg", "internalSiteIdAll cannot be empty");
                return JSONUtil.toJsonStr(returnMap);
            }
            if (StringUtil.isBlank(mtpa.getTense()) || (!mtpa.getTense().equals("before") && !mtpa.getTense().equals("after"))) {
                returnMap.put("msg", "tense cannot be empty,It could is before or after");
                return JSONUtil.toJsonStr(returnMap);

            }
           /* if (null == pePorts || pePorts.size() == 0) {
                returnMap.put("msg", "pePorts cannot be empty");
                return JSONUtil.toJsonStr(returnMap);
            }
            for (PePort pePort : pePorts) {
                String tcpType = pePort.getTcpType();
                if (StringUtil.isBlank(tcpType)) {
                    returnMap.put("msg", "tcpType cannot be empty");
                    return JSONUtil.toJsonStr(returnMap);
                } else if (tcpType.equals("tcp")) {
                    if (StringUtil.isBlank(pePort.getInternalSiteId())) {
                        returnMap.put("msg", "internalSiteId cannot be empty");
                        return JSONUtil.toJsonStr(returnMap);
                    }
                    if (StringUtil.isBlank(pePort.getPeRouter())) {
                        returnMap.put("msg", "peRouter cannot be empty");
                        return JSONUtil.toJsonStr(returnMap);
                    }
                } else if (tcpType.equals("bacbone")) {
                    if (StringUtil.isBlank(pePort.getCircuiltNumber())) {
                        returnMap.put("msg", "CircuiltNumber cannot be empty");
                        return JSONUtil.toJsonStr(returnMap);
                    }
                    if (StringUtil.isBlank(pePort.getPeInterface())) {
                        returnMap.put("msg", "PeInterface cannot be empty");
                        return JSONUtil.toJsonStr(returnMap);
                    }
                }
            }*/
        } catch (Exception e) {
            tempString = "MTPQuery ������ʽ��������д���";
            returnMap.put("msg", tempString);
            MTPQueryLog.info(tempString);
            MTPQueryLog.printStackTrace(e);
            return JSONUtil.toJsonStr(returnMap);
        }
        returnMap.put("status", "Y");
        returnMap.put("msg", "Parameters have been received");
        return JSONUtil.toJsonStr(returnMap);
    }

    /**
     * ���ݹǸ��ַ�����ȡ��Ӧ�ĹǸ�List
     * error ���ڸ�ʽ����,peName interfaceName
     *
     * @param backboneStr
     * @return
     */
    public static List<LinkedTreeMap<String, String>> getBackboneList(String backboneStr) {

        List<LinkedTreeMap<String, String>> backboneList = new ArrayList<>();
        String[] backboneArr = backboneStr.split("\n");
        for (int i = 0; i < backboneArr.length; i++) {
            String tempBackbone = backboneArr[i];
            if (!StrUtil.isBlank(tempBackbone)) {
                LinkedTreeMap<String, String> tempMap = new LinkedTreeMap<String, String>();
                tempMap.put("backbone", tempBackbone);
                if (tempBackbone.length() < 10) {
                    tempMap.put("error", "error");
                    tempMap.put("message", "Error in filling in parameter format\n" + tempBackbone);
                } else {
                    //CNFUZYAL1001E.ge-0/0/0.50
                    HashMap<String, String> checkMap = checkBackbone(tempBackbone);
                    if (StrUtil.isBlank(checkMap.get("error"))) {
                        tempMap.put("peName", checkMap.get("peName"));
                        tempMap.put("interfaceName", checkMap.get("interfaceName"));
                    } else {
                        tempMap.put("error", "error");
                        tempMap.put("message", checkMap.get("message"));
                    }
                }
                backboneList.add(tempMap);
            }
        }
        return backboneList;
    }

    /**
     * ����ж�����backbone
     *
     * @param backbone
     * @return
     */
    public static HashMap<String, String> checkBackbone(String backbone) {

        HashMap<String, String> map = new HashMap<>();
        try {
            String peName = backbone.substring(0, backbone.indexOf("."));
            String interfaceName = backbone.substring(backbone.indexOf(".") + 1, backbone.length());
            map.put("peName", peName);
            map.put("interfaceName", interfaceName);
        } catch (Exception e) {
            map.put("error", "error");
            map.put("backbone", backbone);
            map.put("message", "Error in filling in parameter format\n" + backbone);
        }
        return map;
    }

    /**
     * ������·�����ַ�����ȡlist
     *
     * @return
     */
    public static List<String> getSiteListBySiteAll(String internalSiteIdAll) {
        List<String> list = null;
        try {
            list = new ArrayList<>(Arrays.asList(internalSiteIdAll.split(";")));

        } catch (Exception e) {
            return null;
        }
        return list;

    }

    /*
    ����pe�������жϲ����Ƿ�������
     */
    public String getErrorByPePort(PePort pePort){
        if(StringUtils.isEmpty(pePort.getPeRouter())){
            return pePort.getInternalSiteId()+" not found  PE Name";
        }
        return "";
    }


    /**
     * ���� �����ִ��mtpȻ���ȡ��Ӧ������
     *
     * @param jsonStr
     * @return
     */
    public String addMtpRecordDetailed(String jsonStr) {
        Map<String, String> paramMap = new HashMap<String, String>();
        Map<String, String> returnMap = new HashMap<>();
        Long strLong = System.currentTimeMillis();

        boolean consistent = true;//ά��ǰ���Ƿ�һ��
        int failNumberInt = 0;
        int successNumberInt = 0;

        StringBuffer tcpBf = new StringBuffer();//�������������Ľ��
        StringBuffer differBf=new StringBuffer();//��һ�µĽ��
        Gson gson = new Gson();
        MTPA mtpa = gson.fromJson(jsonStr, MTPA.class);

        List<PePort> pePorts = mtpa.getPePorts();
        String case_id = mtpa.getTicketName();
        String period = mtpa.getTense();
        //��ȡ��· ����������Ϊ��������׼��
        String internalSiteIdAll = mtpa.getInternalSiteIdAll();
        List<String> internalSiteIdArray = getSiteListBySiteAll(internalSiteIdAll);
        int internalSiteAllSize = (internalSiteIdArray == null) ? 0 : internalSiteIdArray.size();
        LinkedTreeMap<String, ResultMessage> siteMap = new LinkedTreeMap<>();
        LinkedTreeMap<String, ResultMessage> backboneMap = new LinkedTreeMap<>();

        String send_size_10 = "10";//����С
        String send_size_100 = "100";//����С

        HashMap<String, String> devicesMap = new HashMap<String, String>();

        Map<String, String> urlMap = getUrl(case_id, period);
        String filePath = urlMap.get("filePath");
        String htmlPath = urlMap.get("htmlPath");


        ZqData zqData = mtpRecordDetailedDaoImpl.getZqData("OP");
        String opName = zqData.getParam_value1().split("###")[0];
        String opPassword = zqData.getParam_value1().split("###")[1];
        String secretBase32 = (zqData.getParam_value1().split("###").length > 2) ? zqData.getParam_value1().split("###")[2] : "";
        paramMap.put("secretBase32", secretBase32);
        String ticketName = mtpa.getTicketName();
        List<PePort> tcpPePort = new ArrayList<>();
        List<PePort> backbonePePort = new ArrayList<>();
        System.out.println("filePath:" + filePath);

        for (PePort pePort : pePorts) {
            String tcpType = pePort.getTcpType().toLowerCase();
            if (tcpType.equals("bacbone")) {
                backbonePePort.add(pePort);
            }
            if (tcpType.equals("tcp") || tcpType.equals("cia") || tcpType.equals("isp")) {
                tcpPePort.add(pePort);
            }
        }

        String tcpInsert = "";//�������ǰ��

        /* ѭ����ʼ*/
        {
            String tcpType = "";
            String status = "";
            String tense = "";
            String differInternalSiteId = "";//��һ�µ� InternalSiteId
            String errorInternalSiteId = "";//����� InternalSiteId

            int loginInt = 1;//��¼ggw����
            Set<String> peRouterSet = new LinkedHashSet<String>();
            Map<String, List<PePort>> peRouterListMap = new HashMap<String, List<PePort>>();

            for (int p = 0; p < tcpPePort.size(); p++) {
                peRouterSet.add(tcpPePort.get(p).getPeRouter());
            }
            for (String peRouter : peRouterSet) {
                System.out.println(peRouter);
                List<PePort> tempList = new ArrayList<PePort>();
                for (int p = 0; p < tcpPePort.size(); p++) {
                    if (tcpPePort.get(p).getPeRouter().equals(peRouter)) {
                        tempList.add(tcpPePort.get(p));
                    }
                }
                peRouterListMap.put(peRouter, tempList);
            }
            if (tcpPePort.size() > 0 && period.equals("before")) {


                int a = mtpRecordDetailedDaoImpl.delMtpRecordDetailedByCaseId(mtpa.getTicketName(), null);
                for (Map.Entry<String, List<PePort>> entry : peRouterListMap.entrySet()) {

                    String peRouter = entry.getKey();
                    List<PePort> tempList = entry.getValue();
                    String errorMessage = "";
                    for (int i = 0; i < tempList.size(); i++) {
                        //��ǰ�����¼
                        StringBuffer cbBuffer = new StringBuffer();
                        PePort pePort = tempList.get(i);
                        if (internalSiteIdArray != null) {
                            internalSiteIdArray.remove(pePort.getInternalSiteId());
                        }

                        String end_full_name = pePort.getPeRouter();
                        String end_interface = pePort.getPePortInterface();
                        String trunk_name = end_full_name + "." + end_interface;
                        MtpRecordDetailed mtpRecordDetailed = new MtpRecordDetailed();
                        mtpRecordDetailed.setCaseId(case_id);
                        mtpRecordDetailed.setInternalSiteId(pePort.getInternalSiteId());
                        mtpRecordDetailed.setMtpRecordDetailedUuid(UuidUtil.getUUID32());
                        mtpRecordDetailed.setShowType(pePort.getTcpType());
                        mtpRecordDetailed.setEndInterface(end_interface);
                        mtpRecordDetailed.setBeforeResultUrl(htmlPath);
                        mtpRecordDetailed.setSendSize(send_size_10);
                        mtpRecordDetailed.setCaseStatus("now");
                        mtpRecordDetailed.setBeforeEndFullName(end_full_name);
                        mtpRecordDetailed.setBeforeEndInterface(pePort.getPePortInterface());
                        mtpRecordDetailed.setCreateTime(DateUtil.getDate(new Date()));
                        mtpRecordDetailed.setLastUpdatedTime(mtpRecordDetailed.getCreateTime());
                        String nowDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                        cbBuffer.append("----------------------------------\r\n");
                        cbBuffer.append("PE Port��" + trunk_name + "\r\n");
                        cbBuffer.append("Date��" + nowDate + "\r\n");
                        cbBuffer.append("internalSiteId��" + mtpRecordDetailed.getInternalSiteId() + "\r\n");
                        if (!StringUtil.isBlank(pePort.getVRFSiteId())) {
                            mtpRecordDetailed.setBeforeVrfSiteId(pePort.getVRFSiteId());
                            cbBuffer.append("vrfSiteId��" + pePort.getVRFSiteId() + "\r\n");
                        }
                        cbBuffer.append("----------------------------------\r\n");
                        mtpRecordDetailed.setBeforeStatus("pass");
                        mtpRecordDetailed.setBeforeErrorCause("");
                        mtpRecordDetailed.setBeforeCeWanIp(pePort.getCeWanIp());
                        mtpRecordDetailed.setBeforePeWanIp(pePort.getPeWanIp());
                        mtpRecordDetailed.setBeforeTcpType(pePort.getTcpType());
                        mtpRecordDetailed.setInternalSiteId(pePort.getInternalSiteId());

                        errorMessage=getErrorByPePort(pePort);
                        if (!StringUtils.isEmpty(errorMessage)) {//��һ���ٰ����쳣����ִ�д��
                            setError(mtpRecordDetailed, errorMessage, period, cbBuffer);
                        }else{
                            send_size_10 = String.valueOf(RandomUtil.randomInt(5, 10));
                            send_size_100 = String.valueOf(RandomUtil.randomInt(100, 120));
                            String command = "ping interface " + mtpRecordDetailed.getBeforeEndInterface() + " rapid source " + mtpRecordDetailed.getBeforePeWanIp() + " " + mtpRecordDetailed.getBeforeCeWanIp() + " count " + send_size_100;
                            String command1 = "ping interface " + mtpRecordDetailed.getBeforeEndInterface() + " rapid source " + mtpRecordDetailed.getBeforePeWanIp() + " " + mtpRecordDetailed.getBeforeCeWanIp() + " count " + send_size_10;
                            cbBuffer.append(opName + "@" + mtpRecordDetailed.getBeforeEndFullName() + ">");
                            String ip = getDeviceByIP(devicesMap, mtpRecordDetailed.getBeforeEndFullName());

                            if (StringUtil.isBlank(ip)) {
                                errorMessage = "peRouter (" + mtpRecordDetailed.getBeforeEndFullName() + ") unable Get corresponding Router ";
                            } else if (ip.equals("GGWAPI��ѯ�豸�����Ѿ��ر�")) {
                                errorMessage = ip;
                            }
                            paramMap.put("opName", opName);
                            paramMap.put("opPassword", opPassword);
                            paramMap.put("command", command1);
                            paramMap.put("ip", ip);

                            Map<String, String> temprReturnMap = GGWLoginApiUtil.execute(paramMap, GGWLoginApiUtil.getLog());
                            errorMessage = setLogByResult(paramMap, temprReturnMap, cbBuffer, mtpRecordDetailed, period);

                            if (StringUtils.isEmpty(errorMessage)) {//��һ���ٰ����쳣����ִ�д��
                                paramMap.put("command", command);
                                temprReturnMap = new HashMap<>();
                                temprReturnMap = GGWLoginApiUtil.execute(paramMap, GGWLoginApiUtil.getLog());
                                errorMessage = setLogByResult(paramMap, temprReturnMap, cbBuffer, mtpRecordDetailed, period);
                                if (StringUtils.isEmpty(errorMessage)) {//�ڶ������쳣 ��¼
                                    cbBuffer.append(paramMap.get("command") + "\t\n");
                                    cbBuffer.append(temprReturnMap.get("data"));
                                }
                            } else {
                                cbBuffer.append(paramMap.get("command") + "\t\n");
                                setError(mtpRecordDetailed, errorMessage, period, cbBuffer);
                            }
                        }

                        cbBuffer.append("\n");
                        addBf(tcpBf, cbBuffer);
                        setSiteMapByPingStatus(mtpRecordDetailed, period, siteMap, returnMap);
                        if (cbBuffer.indexOf("color:red") > -1) {
                            errorInternalSiteId += mtpRecordDetailed.getInternalSiteId() + ";";
                        }

                        mtpRecordDetailedDaoImpl.save(mtpRecordDetailed);

                    }
                }

            } else if (tcpPePort.size() > 0 && period.equals("after")) {

                for (Map.Entry<String, List<PePort>> entry : peRouterListMap.entrySet()) {
                    String peRouter = entry.getKey();
                    List<PePort> tempList = entry.getValue();
                    String errorMessage = "";

                    for (int i = 0; tempList != null && i < tempList.size(); i++) {
                        //��ǰ�����¼
                        StringBuffer cbBuffer = new StringBuffer();
                        PePort pePort = tempList.get(i);
                        if (internalSiteIdArray != null) {
                            internalSiteIdArray.remove(pePort.getInternalSiteId());
                        }
                        MtpRecordDetailed mtpRecordDetailed = mtpRecordDetailedDaoImpl.getMtpRecordDetailedByCaseIdIntId(case_id, pePort.getInternalSiteId(), pePort.getVRFSiteId());
                        if (mtpRecordDetailed != null) {
                            mtpRecordDetailed.setAfterStatus("pass");
                            mtpRecordDetailed.setAfterErrorCause("");
                            mtpRecordDetailed.setAfterResultUrl(htmlPath);
                            mtpRecordDetailed.setAfterEndInterface(pePort.getPePortInterface());
                            mtpRecordDetailed.setAfterEndFullName(pePort.getPeRouter());
                            mtpRecordDetailed.setAfterPeWanIp(pePort.getPeWanIp());
                            mtpRecordDetailed.setAfterCeWanIp(pePort.getCeWanIp());
                            mtpRecordDetailed.setLastUpdatedTime(DateUtil.getDate(new Date()));
                            mtpRecordDetailed.setAfterTcpType(pePort.getTcpType());
                            String trunk_name = pePort.getPeRouter() + "." + pePort.getPePortInterface();
                            String nowDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                            cbBuffer.append("----------------------------------\r\n");
                            cbBuffer.append("PE Port��" + trunk_name + "\r\n");
                            cbBuffer.append("internalSiteId��" + pePort.getInternalSiteId() + "\r\n");
                            cbBuffer.append("Date��" + nowDate + "\r\n");
                            if (!StringUtil.isBlank(pePort.getVRFSiteId())) {
                                mtpRecordDetailed.setAfterVrfSiteId(pePort.getVRFSiteId());
                                cbBuffer.append("vrfSiteId��" + pePort.getVRFSiteId() + "\r\n");
                            }
                            cbBuffer.append("----------------------------------\r\n");

                            String tempData="";//������ȷ���ؽ��

                            errorMessage=getErrorByPePort(pePort);
                            if (!StringUtils.isEmpty(errorMessage)) {//��һ���ٰ����쳣����ִ�д��
                                setError(mtpRecordDetailed, errorMessage, period, cbBuffer);
                            }else{
                                send_size_10 = String.valueOf(RandomUtil.randomInt(5, 10));
                                send_size_100 = String.valueOf(RandomUtil.randomInt(100, 120));
                                String command = "ping interface " + mtpRecordDetailed.getAfterEndInterface() + " rapid source " + mtpRecordDetailed.getAfterPeWanIp() + " " + mtpRecordDetailed.getAfterCeWanIp() + " count " + send_size_100;
                                String command1 = "ping interface " + mtpRecordDetailed.getAfterEndInterface() + " rapid source " + mtpRecordDetailed.getAfterPeWanIp() + " " + mtpRecordDetailed.getAfterCeWanIp() + " count " + send_size_10;
                                cbBuffer.append(opName + "@" + mtpRecordDetailed.getAfterEndFullName() + ">");
                                String ip = getDeviceByIP(devicesMap, mtpRecordDetailed.getAfterEndFullName());

                                if (StringUtil.isBlank(ip)) {
                                    errorMessage = "peRouter (" + mtpRecordDetailed.getBeforeEndFullName() + ") unable Get corresponding Router ";
                                } else if (ip.equals("GGWAPI��ѯ�豸�����Ѿ��ر�")) {
                                    errorMessage = ip;
                                }

                                paramMap.put("opName", opName);
                                paramMap.put("opPassword", opPassword);
                                paramMap.put("command", command1);
                                paramMap.put("ip", ip);

                                Map<String, String> temprReturnMap = GGWLoginApiUtil.execute(paramMap, GGWLoginApiUtil.getLog());
                                errorMessage = setLogByResult(paramMap, temprReturnMap, cbBuffer, mtpRecordDetailed, period);

                                if (StringUtils.isEmpty(errorMessage)) {//��һ���ٰ����쳣����ִ�д��
                                    paramMap.put("command", command);
                                    temprReturnMap = new HashMap<>();
                                    temprReturnMap = GGWLoginApiUtil.execute(paramMap, GGWLoginApiUtil.getLog());
                                    errorMessage = setLogByResult(paramMap, temprReturnMap, cbBuffer, mtpRecordDetailed, period);
                                    if (StringUtils.isEmpty(errorMessage)) {//�ڶ������쳣 ��¼
                                        cbBuffer.append(paramMap.get("command") + "\t\n");
                                        cbBuffer.append(temprReturnMap.get("data"));
                                        tempData=temprReturnMap.get("data");
                                    }
                                } else {
                                    cbBuffer.append(paramMap.get("command") + "\t\n");
                                    setError(mtpRecordDetailed, errorMessage, period, cbBuffer);
                                }
                            }
                            setSiteMapByPingStatus(mtpRecordDetailed, period, siteMap, returnMap);

                            if (cbBuffer.indexOf("color:red") > -1) {
                                errorInternalSiteId += mtpRecordDetailed.getInternalSiteId() + ";";
                            }
                            if (!mtpRecordDetailed.getBeforeStatus().equals(mtpRecordDetailed.getAfterStatus())) {
                                differInternalSiteId += mtpRecordDetailed.getInternalSiteId() + ";";
                                consistent = false;
                                returnMap.put("status", "N");

                                differBf.append("----------------------------------\r\n");
                                differBf.append("PE Port��" + trunk_name + "\r\n");
                                differBf.append("internalSiteId��<b style=\"color:red;\">" + pePort.getInternalSiteId() + "</b>\r\n");
                                differBf.append("Date��" + nowDate + "\r\n");
                                if (!StringUtil.isBlank(pePort.getVRFSiteId())) {
                                    mtpRecordDetailed.setAfterVrfSiteId(pePort.getVRFSiteId());
                                    differBf.append("vrfSiteId��" + pePort.getVRFSiteId() + "\r\n");
                                }
                                differBf.append("----------------------------------\r\n");
                                differBf.append(opName + "@" + mtpRecordDetailed.getAfterEndFullName() + ">");
                                differBf.append(paramMap.get("command") + "\t\n");
                                if(!StringUtil.isBlank(errorMessage)){
                                    differBf.append("<span style=\"color:red;\">"+errorMessage+"</span>");
                                }else {
                                    differBf.append("<span style=\"color:red;\">"+tempData+"</span>");
                                }
                                differBf.append("\r\n");
                            }
                            mtpRecordDetailedDaoImpl.update(mtpRecordDetailed);
                        }
                        cbBuffer.append("\r\n");
                        addBf(tcpBf, cbBuffer);
                    }
                }
                if (!StringUtil.isBlank(differBf.toString().trim())) {
                    differBf = differBf.append("==================================== separative sign  ==========================================<br><br>");
                }


                //�ж��߼�
                tcpInsert = ((consistent) ? "<b>Inspection results: consistent</b><br><br>" : "<b style=\"color:red;font-size:20px;\">Inspection results:not consistent</b><br><br><b style=\"color:red;font-size:15px;\">Not Consistent InternalSiteId:" + differInternalSiteId + "</b>\r\n\r\n" + differBf);

                if (!errorInternalSiteId.equals("")) {
                    tcpInsert += "<br><b>This check Error InternalSiteId :</b>" + ((StrUtil.isBlank(errorInternalSiteId)) ? "results  consistent" : errorInternalSiteId) + "\r\n\r\n";
                }
            }
            Long endStr = System.currentTimeMillis();
            ExecuteCommandLog.info("ִ��" + tcpPePort.size() + "��·����ʱ" + (endStr - strLong) / 1000.00 + "��");
        }
        /* pePortsѭ������ */
        tcpBf.insert(0, tcpInsert);


        //����� bacbonePePort
        /*{
            ResultList bacboneResultList = new ResultList();
            String exceptionTrunkId = "";//�쳣�� trunk

            int loginInt = 1;//��¼ggw����
            String backboneStr = "";
            //��ȡbackbone������Ȼ�����ֵ��ִ��backboneִ��
            try {
                if (backbonePePort != null && backbonePePort.size() > 0) {
                    backboneStr = backbonePePort.get(0).getPeInterface();
                }
            } catch (Exception e) {
                backboneStr = null;
            }
            List<LinkedTreeMap<String, String>> backboneList = new ArrayList<>();
            if (StrUtil.isBlank(backboneStr)) {
                backboneList = getBackboneList(backboneStr);
                for (int i = 0; i < backboneList.size(); i++) {
                    String errorMessage = "";
                    StringBuffer cbBuffer = new StringBuffer();
                    String tcpTyep = "backbone";
                    MtpRecordDetailed mtpRecordDetailed = new MtpRecordDetailed();

                    try {
                        LinkedTreeMap<String, String> tempBackboneDetailed = backboneList.get(i);
                        String peName = tempBackboneDetailed.get("peName");
                        String interfaceName = tempBackboneDetailed.get("interfaceName");
                        String ip = getDeviceByIP(devicesMap, peName);
                        String command1 = "show interfaces " + interfaceName;
                        paramMap.put("command", command1);
                        paramMap.put("ip", ip);
                        String trunk = peName + "." + interfaceName;
                        mtpRecordDetailed.setCaseId(case_id);
                        mtpRecordDetailed.setMtpRecordDetailedUuid(UuidUtil.getUUID32());
                        mtpRecordDetailed.setShowType(tcpTyep);
                        mtpRecordDetailed.setEndInterface(interfaceName);
                        mtpRecordDetailed.setBeforeResultUrl(htmlPath);
                        mtpRecordDetailed.setSendSize(send_size_10);
                        mtpRecordDetailed.setCaseStatus("now");
                        mtpRecordDetailed.setBeforeEndFullName(peName);
                        mtpRecordDetailed.setBeforeEndInterface(interfaceName);
                        mtpRecordDetailed.setCreateTime(DateUtil.getDate(new Date()));
                        mtpRecordDetailed.setLastUpdatedTime(mtpRecordDetailed.getCreateTime());
                        String nowDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                        cbBuffer.append("-------------------------------------------------------------------------------------------\r\n");
                        cbBuffer.append("Trunk ��" + trunk + "\r\n");
                        cbBuffer.append("Date��" + nowDate + "\r\n");
                        cbBuffer.append("-------------------------------------------------------------------------------------------\r\n");
                        mtpRecordDetailed.setBeforeStatus("pass");
                        mtpRecordDetailed.setBeforeErrorCause("");
                        mtpRecordDetailed.setBeforeTcpType(tcpTyep);

                        if (StrUtil.isBlank(tempBackboneDetailed.get("error"))) {
                            String message = tempBackboneDetailed.get("message");
                        } else {
                            List<List<String>> backboneResult = Backbone.getBackboneResult(paramMap, peName, interfaceName);
                            for (List<String> tempList : backboneResult) {
                                cbBuffer.append(peName + ">" + tempList.get(0));
                                String data = tempList.get(1);
                                if (data.indexOf("error") == -1 && data.indexOf("syntax error") == -1) {
                                    cbBuffer.append(tempList.get(1));
                                } else {
                                    errorMessage = tempList.get(1);
                                    setError(mtpRecordDetailed, errorMessage, period, cbBuffer);
                                }
                            }
                            cbBuffer.append("\n");
                            addBf(tcpBf, cbBuffer);
                            if (cbBuffer.indexOf("color:red") > -1) {
                                exceptionTrunkId += trunk + ";";
                            }

                            mtpRecordDetailedDaoImpl.save(mtpRecordDetailed);
                        }
                    } catch (Exception e) {
                        errorMessage = "exception:query backone error";
                        MTPQueryLog.printStackTrace(e);
                    }
                }
            } else {//û���ҵ�backbone����
                String tempStr = "not acquired peInterface";
            }
        }*/
        returnMap.put("status", (returnMap.containsKey("status")) ? returnMap.get("status") : "Y");
        returnMap.put("tense", period);
        returnMap.put("ticketName", ticketName);


        String msg = getMsgBySiteMap(siteMap, backboneMap, returnMap, internalSiteAllSize, htmlPath, internalSiteIdArray);
        if (returnMap.get("status").equals("Y")) {
            returnMap.put("returnHtmlmsg", returnMap.get("returnHtmlmsg").replace("summary", "MTP result summary"));
            msg = msg.replace("summary", "MTP result summary");
        } else {
            returnMap.put("returnHtmlmsg", returnMap.get("returnHtmlmsg").replace("summary", "MTP result summary��abnormal��,Please check the detail"));
            msg = msg.replace("summary", "MTP result summary��abnormal��,Please check the detail");
        }
        returnMap.put("msg", msg);
        MTPQueryLog.info("send msg:\n" + msg);
        tcpBf.insert(0, returnMap.get("returnHtmlmsg"));

        String resultStr = tcpBf.toString();
        addHtml(resultStr, htmlPath, filePath);
        return JSONUtil.toJsonStr(returnMap);
    }


    /**
     * ���ݽ����������־html
     */
    public static String setLogByResult(Map<String, String> paramMap, Map<String, String> temprReturnMap, StringBuffer cbBuffer, MtpRecordDetailed mtpRecordDetailed, String period) {
        String errorMessage = "";
        String tempString = "";
        if (!StringUtils.isEmpty(temprReturnMap.get("error"))) {
            cbBuffer.append(paramMap.get("command") + "\t\n");
            tempString = temprReturnMap.get("message");
            errorMessage = "error:" + tempString;
            setError(mtpRecordDetailed, errorMessage, period, cbBuffer);
        } else {
            tempString = temprReturnMap.get("data");
            if (StringUtils.isEmpty(tempString)) {
                errorMessage = "error:ggw return result is null";
                setError(mtpRecordDetailed, errorMessage, period, cbBuffer);
            } else {
                errorMessage = getFruit(tempString, period, mtpRecordDetailed);
            }
        }
        return errorMessage;
    }

    /**
     * ����siteMap����ȡmsg ���õ�returnmap
     *
     * @param siteMap
     * @param returnMap
     * @param internalSiteAllSize ������� internalSiteAll��Ϊ0 ���
     */
    public static String getMsgBySiteMap(LinkedTreeMap<String, ResultMessage> siteMap, LinkedTreeMap<String, ResultMessage> backboneMap, Map<String, String> returnMap, int internalSiteAllSize, String htmlPath, List<String> internalSiteIdArray) {
        int siteTotalSize = 0;//������
        int siteFaildSize = 0;//��������
        int siteSuccessSize = 0;//�ɹ�����
        int siteExceptionSize = 0;//��·�����쳣����
        String siteExceptionDetailed = "";//�쳣��site��ϸ
        int backboneTotalSize = 0;//������
        int backboneFaildSize = 0;//��������
        int backboneSuccessSize = 0;//�ɹ�����
        int backboneExceptionSize = 0;//�Ǹɲ����쳣����
        String backboneExceptionDetailed = "";//�쳣�ĹǸ�
        int mvrfSize = 0;// mvrf����
        String mvrfDetailed = "";// mvrf��ϸ
        int internalSiteIdArraySize = (internalSiteIdArray != null) ? internalSiteIdArray.size() : 0;//��Ӧû���ҵ���·��Ϣ������

        String nullSiteExceptionDetailed = "";//û���ҵ���site
        if (internalSiteIdArray != null) {
            for (int i = 0; i < internalSiteIdArray.size(); i++) {
                nullSiteExceptionDetailed += internalSiteIdArray.get(i) + ";";
            }
        }


        for (Map.Entry<String, ResultMessage> entry : siteMap.entrySet()) {
            String internalSiteId = entry.getKey();
            ResultMessage resultMessage = siteMap.get(internalSiteId);
            siteTotalSize += resultMessage.getSiteTotalSize();
            siteFaildSize += resultMessage.getSiteFaildSize();
            siteSuccessSize += resultMessage.getSiteSuccessSize();

            backboneTotalSize += resultMessage.getBackboneTotalSize();
            backboneFaildSize += resultMessage.getBackboneFaildSize();
            backboneSuccessSize += resultMessage.getBackboneSuccessSize();

            siteExceptionSize += resultMessage.getSiteExceptionSize();
            siteExceptionDetailed += (!StrUtil.isBlank(resultMessage.getSiteExceptionDetailed())) ? (resultMessage.getSiteExceptionDetailed() + ";") : ("");
            backboneExceptionSize += resultMessage.getBackboneExceptionSize();
            backboneExceptionDetailed += resultMessage.getBackboneExceptionDetailed();
            mvrfSize += resultMessage.getMvrfSize();
            mvrfDetailed = (!StrUtil.isBlank(mvrfDetailed) && !StrUtil.isBlank(resultMessage.getMvrfDetailed())) ? mvrfDetailed + ";" : mvrfDetailed;
            mvrfDetailed += ((StrUtil.isBlank(resultMessage.getMvrfDetailed())) ? "" : resultMessage.getMvrfDetailed());
        }

        for (Map.Entry<String, ResultMessage> entry : backboneMap.entrySet()) {
            String backboneStr = entry.getKey();
            ResultMessage resultMessage = backboneMap.get(backboneStr);
            backboneTotalSize += resultMessage.getBackboneTotalSize();
            backboneFaildSize += resultMessage.getBackboneFaildSize();
            backboneSuccessSize += resultMessage.getBackboneSuccessSize();
            backboneExceptionSize += resultMessage.getBackboneExceptionSize();
            backboneExceptionDetailed += resultMessage.getBackboneExceptionDetailed();
        }

        String msg = (
                "------------  summary ----------\n" +
                        "------------  site ----------\n" +
                        "site Total��" + ((internalSiteAllSize == 0) ? siteTotalSize : internalSiteAllSize) + "\n" +
                        "ping Success��" + siteSuccessSize + "\n" +
                        "ping faild��" + siteFaildSize + "\n" +
                        "MVRF:" + mvrfSize + "\n" +
                        "param exception��" + (siteExceptionSize + internalSiteIdArraySize) + "\n" +
                        "exception site detailed��" + siteExceptionDetailed + "\n" +
                        "null exception site detailed��" + nullSiteExceptionDetailed + "\n" +
                        "\n" +
                        "------------  backbone ----------\n" +
                        "backbone Total��" + backboneTotalSize + "\n" +
                        "backbone success��" + backboneSuccessSize + "\n" +
                        "backbone fail��" + backboneFaildSize + "\n" +
                        "param exception��" + backboneExceptionSize + "\n" +
                        "exception backbone detailed��" + backboneExceptionDetailed + "\n" +
                        "------------ Result url ----------\n" +
                        htmlPath + "\n" +
                        "------------------------separative sign --------------------------" + "\n\n\n\n");
        String returnHtmlmsg =
                "------------  summary ----------\n" +
                        "------------  site ----------\n" +
                        "site Total��" + ((internalSiteAllSize == 0) ? siteTotalSize : internalSiteAllSize) + "\n" +
                        "ping Success��" + siteSuccessSize + "\n";
        String remedy_summary= (
                "------------  summary ----------\n" +
                        "------------  site ----------\n" +
                        "site Total��" + ((internalSiteAllSize == 0) ? siteTotalSize : internalSiteAllSize) + "\n" +
                        "ping Success��" + siteSuccessSize + "\n" +
                        "ping faild��" + siteFaildSize + "\n" +
                        "MVRF:" + mvrfSize + "\n" +
                        "param exception��" + (siteExceptionSize + internalSiteIdArraySize) + "\n" +
                        "exception site detailed��" + ((siteExceptionDetailed.split(";").length>10)?"More than 10 please click on the link to see details":siteExceptionDetailed )+"\n" +
                        "null exception site detailed��" +((nullSiteExceptionDetailed.split(";").length>10)?"More than 10 please click on the link to see details":nullSiteExceptionDetailed )  + "\n" +
                        "\n" +
                        "------------ Result url ----------\n" +
                        htmlPath + "\n" +
                        "------------------------separative sign --------------------------" + "\n\n\n\n");

        if (siteFaildSize == 0) {
            returnHtmlmsg += "ping faild��" + siteFaildSize + "\n";
        } else {
            returnHtmlmsg += "<span style=\"color:red;font-weight:bold;\">" + "ping faild��" + siteFaildSize + "</span>" + "\n";
        }
        returnHtmlmsg += "MVRF:" + mvrfSize + "\n";
        if (StrUtil.isBlank(siteExceptionDetailed)) {
            returnHtmlmsg += "param exception��" + (siteExceptionSize + internalSiteIdArraySize) + "\n" +
                    "exception site detailed��" + siteExceptionDetailed + "\n";
        } else {
            returnHtmlmsg += "<span style=\"color:red;font-weight:bold;\">" + "param exception��" + (siteExceptionSize + internalSiteIdArraySize) + "</span>" + "\n" +
                    "<span style=\"color:red;font-weight:bold;\">" + "exception site detailed��" + siteExceptionDetailed + "</span>" + "\n";
        }
        if (StrUtil.isBlank(nullSiteExceptionDetailed)) {
            returnHtmlmsg += "null exception site detailed��" + nullSiteExceptionDetailed + "\n";
        } else {
            returnHtmlmsg += "<span style=\"color:red;font-weight:bold;\">" + "null exception site detailed��" + nullSiteExceptionDetailed + "</span>" + "\n";
        }
        returnHtmlmsg += "\n" +
                "------------  backbone ----------\n" +
                "backbone Total��" + backboneTotalSize + "\n" +
                "backbone success��" + backboneSuccessSize + "\n";

        if (backboneFaildSize == 0) {
            returnHtmlmsg += "backbone fail��" + backboneFaildSize + "\n";
        } else {
            returnHtmlmsg += "<span style=\"color:red;font-weight:bold;\">" + "backbone fail��" + backboneFaildSize + "</span>" + "\n";
        }
        if (StrUtil.isBlank(backboneExceptionDetailed)) {
            returnHtmlmsg += "param exception��" + backboneExceptionSize + "\n" +
                    "exception backbone detailed��" + backboneExceptionDetailed + "\n" +
                    "------------ Result url ----------\n" +
                    htmlPath + "\n" +
                    "------------------------separative sign --------------------------" + "\n\n\n\n";
        } else {
            returnHtmlmsg += "<span style=\"color:red;font-weight:bold;\">" + "param exception��" + backboneExceptionSize + "</span>" + "\n" +
                    "<span style=\"color:red;font-weight:bold;\">" + "exception backbone detailed��" + backboneExceptionDetailed + "</span>" + "\n" +
                    "------------ Result url ----------\n" +
                    htmlPath + "\n" +
                    "------------------------separative sign --------------------------" + "\n\n\n\n";
        }


        returnMap.put("remedy_summary",remedy_summary);
        returnMap.put("msg", msg);
        returnMap.put("returnHtmlmsg", returnHtmlmsg);
        return msg;
    }

    /**
     * ���� ʱ̬�� pingStatus ���ж�
     *
     * @param mtpRecordDetailed
     * @param period            ʱ̬
     * @param siteMap
     */
    public static void setSiteMapByPingStatus(MtpRecordDetailed mtpRecordDetailed, String period, LinkedTreeMap<String, ResultMessage> siteMap, Map<String, String> returnMap) {
        String internalSiteId = mtpRecordDetailed.getInternalSiteId();

        ResultMessage siteResultMessage = new ResultMessage();
        String mvrf = "";
        String pingStatus = "";

        if (period.equals("before")) {
            pingStatus = mtpRecordDetailed.getBeforePingStatus();
            mvrf = mtpRecordDetailed.getBeforeVrfSiteId();
        } else {
            pingStatus = mtpRecordDetailed.getAfterPingStatus();
            mvrf = mtpRecordDetailed.getAfterVrfSiteId();
        }
        if (siteMap.containsKey(internalSiteId)) {
            siteResultMessage = siteMap.get(internalSiteId);
            siteResultMessage.setMvrfSize((siteResultMessage.getMvrfSize() == 0 ? 1 : siteResultMessage.getMvrfSize()) + 1);
            siteResultMessage.setSiteTotalSize(siteResultMessage.getSiteTotalSize() + 1);
            siteResultMessage.setMvrfDetailed(siteResultMessage.getMvrfDetailed() + ";" + mvrf);
            if (StrUtil.isBlank(pingStatus) || (!StrUtil.isBlank(pingStatus) && (pingStatus.equals("ping�����쳣") || pingStatus.equals("ǰ���ӳٴ���2")))) {
                returnMap.put("status", "N");
                siteResultMessage.setSiteExceptionSize(siteResultMessage.getSiteExceptionSize() + 1);
            } else if (pingStatus.equals("ping����")) {
                siteResultMessage.setSiteSuccessSize(siteResultMessage.getSiteSuccessSize() + 1);
            } else if (pingStatus.equals("ping����") || pingStatus.equals("ping��ͨ")) {
                siteResultMessage.setSiteFaildSize(siteResultMessage.getSiteFaildSize() + 1);
            }
            siteMap.put(internalSiteId, siteResultMessage);
        } else {
            siteResultMessage.setSiteTotalSize(1);
            siteResultMessage.setMvrfDetailed(mvrf);
            if (StrUtil.isBlank(pingStatus) || (!StrUtil.isBlank(pingStatus) && (pingStatus.equals("ping�����쳣") || pingStatus.equals("ǰ���ӳٴ���2")))) {
                returnMap.put("status", "N");
                siteResultMessage.setSiteExceptionDetailed(mtpRecordDetailed.getInternalSiteId());
                siteResultMessage.setSiteExceptionSize(1);
            } else if (pingStatus.equals("ping����")) {
                siteResultMessage.setSiteSuccessSize(1);
            } else if (pingStatus.equals("ping����") || pingStatus.equals("ping��ͨ")) {
                siteResultMessage.setSiteFaildSize(1);
            }
            siteMap.put(internalSiteId, siteResultMessage);
        }
    }

    /**
     * ����mtp��ѯ֮�����Ϣ�� remedy
     *
     * @param mtpResult
     * @param remedyUrl
     */
    public void sendToRemedy(MtpResult mtpResult, String remedyUrl) {
        Map<String, Object> hashMap = new HashMap<>();
        String mtpResultStr = JSONUtil.toJsonStr(mtpResult);
        hashMap.put("jsonStr", mtpResultStr);
        MTPQueryLog.info("���͵Ĳ���Ϊ mtpResultStr:" + mtpResultStr);
        MTPQueryLog.info("remedy��ַ remedyUrl:" + remedyUrl);
        String remedyReturnStr = HttpUtil.post(remedyUrl, hashMap);
        MTPQueryLog.info("���ؽ�� remedyReturnStr:" + remedyReturnStr);
        cn.hutool.json.JSONObject returnJsonObject = JSONUtil.parseObj(remedyReturnStr);
        if (!StringUtil.isBlank(returnJsonObject.getStr("status")) && returnJsonObject.getStr("status").equals("Y")) {
            MTPQueryLog.info("���� �����˴���������鿴");
        } else {
            MTPQueryLog.info("��Ϣ�Ѿ����͵�remedy");
        }

    }

    /**
     * ���ô�����Ϣ
     *
     * @param mtpRecordDetailed
     * @param errorMessage
     * @param period
     */
    public static void setError(MtpRecordDetailed mtpRecordDetailed, String errorMessage, String period, StringBuffer cbBuffer) {
        if (period.equals("before")) {
            mtpRecordDetailed.setBeforeStatus("fail");
            mtpRecordDetailed.setBeforeErrorCause(errorMessage);
            cbBuffer.append("<span style=\"color:red;font-weight:bold;\">" + errorMessage + "</span>\r\n");
        } else if (period.equals("after")) {
            mtpRecordDetailed.setAfterStatus("fail");
            mtpRecordDetailed.setAfterErrorCause(errorMessage);
            cbBuffer.append("<span style=\"color:red;font-weight:bold;\">" + errorMessage + "</span>\r\n");
        }


    }


    /**
     * ��ȡ�ļ�·��
     *
     * @param caseId
     * @param period ʱ̬����ά��֮ǰ����֮��
     * @return
     */
    public Map<String, String> getUrl(String caseId, String period) {
        HttpServletRequest request = ServletActionContext.getRequest();
        String separator = File.separator;
        ;
        String requestUrl = request.getServletContext().getRealPath("");
        //request.getSession().getServletContext().getRealPath("");
        //D:\op1768\tool\idea\MyOldObject\mtp\WebContent
        //    	String mtpPath =  separator+"mtp"+separator+	DateUtil.getDateStryyyyMMdd(new Date())+separator;  //�ļ�����·��
        String mtpPath = "/mtp/" + DateUtil.getDateStryyyyMMdd(new Date()) + "/";  //�ļ�����·��
        String requestHtmlUrl = request.getRequestURL().toString();//����url
        String urlPrefix = requestHtmlUrl.replace(request.getRequestURI(), "") + request.getContextPath();
        String urlPostfix = caseId + "_" + period + ".html";
        String htmlPath = urlPrefix + mtpPath + urlPostfix;
        String filePath = requestUrl + mtpPath + urlPostfix;
        Map<String, String> urlMap = new HashMap<String, String>();
        urlMap.put("htmlPath", htmlPath);
        urlMap.put("filePath", filePath);
        return urlMap;
    }

    /**
     * ���ö��������������ж��� �������ǲ����������쳣��
     *
     * @param period     ʱ̬
     * @param detailed   ʵ����
     * @param pingStatus
     */
    public static void setPingStatus(String period, MtpRecordDetailed detailed, String pingStatus) {
        if (period.equals("before")) {
            detailed.setBeforePingStatus(pingStatus);
        } else {
            detailed.setAfterPingStatus(pingStatus);
        }

    }

    /**
     * ���ݷ��ؽ�����úͻ�ȡֵ
     *
     * @param tempFruit ����ж��ַ���
     * @param period    ʱ��
     * @param detailed
     * @return
     */
    public static String getFruit(String tempFruit, String period, MtpRecordDetailed detailed) {
        String errorMessage = "";
        String received = "";//������
        System.out.println(tempFruit);
        String pingStatus = "";
        try {
            received = tempFruit.substring(tempFruit.indexOf("received,") + 9, tempFruit.indexOf("%")).trim();
        } catch (Exception e) {
            pingStatus = "ping�����쳣";
            setPingStatus(period, detailed, pingStatus);
            e.printStackTrace();
            errorMessage = tempFruit;
            return errorMessage;
        }

        if (received.equals("0")) {

            System.out.println("�����ʣ���" + received + "��");
            if (period.equals("before")) {
                String temporary = tempFruit.substring(tempFruit.indexOf("stddev =") + 8).trim();
                BigDecimal beforeDelay = BigDecimal.valueOf(Double.valueOf(temporary.substring(0, temporary.indexOf("/"))));
                detailed.setBeforeDelay(beforeDelay.toString());
                pingStatus = "ping����";
                setPingStatus(period, detailed, pingStatus);
            } else if (tempFruit.indexOf("stddev =") > -1 && !StringUtil.isBlank(detailed.getBeforeDelay())) {
                String temporary = tempFruit.substring(tempFruit.indexOf("stddev =") + 8).trim();
                BigDecimal afterDelay = BigDecimal.valueOf(Double.valueOf(temporary.substring(0, temporary.indexOf("/"))));
                BigDecimal beforeDelay = BigDecimal.valueOf(Double.valueOf(detailed.getBeforeDelay()));
                detailed.setAfterDelay(afterDelay.toString());

                if (afterDelay.subtract(beforeDelay).floatValue() >= 2) {
                    pingStatus = "ǰ���ӳٴ���2";
                    setPingStatus(period, detailed, pingStatus);
                    //return   errorMessage = tempFruit + "\n��ά�����ӳٳ����Ա�֮ǰ���������� 1,֮ǰ�ӳ٣�" + beforeDelay + "\t ֮����ӳ٣�" + afterDelay;
                    return errorMessage = tempFruit + "\nThe delay after maintenance exceeded that before comparison 2,before delay��" + beforeDelay + "\t after delay��" + afterDelay;
                }
                pingStatus = "ping����";
                setPingStatus(period, detailed, pingStatus);
            }
        } else {
            try {
                if (Integer.valueOf(received) > 0 && Integer.valueOf(received) < 100) {
                    errorMessage = tempFruit;
                    pingStatus = "ping����";
                    setPingStatus(period, detailed, pingStatus);
                }
                if (Integer.valueOf(received) == 100|| tempFruit.indexOf("100% packet loss")>-1 ) {
                    errorMessage = tempFruit;
                    pingStatus = "ping��ͨ";
                    setPingStatus(period, detailed, pingStatus);
                }
            } catch (Exception e) {
                errorMessage = tempFruit;
                pingStatus = "ping�����쳣";
                setPingStatus(period, detailed, pingStatus);
            }
        }
        return errorMessage;
    }

    /**
     * ���ݲ���������ggwAPI ִ������
     *
     * @param paramMap
     * @return
     */
    public static String getGGWAPI(Map<String, String> paramMap) {
        String nowTime = Long.toString(new Date().getTime() / 1000);
        System.out.println("nowTime:" + cn.hutool.core.date.DateUtil.now());
        String encrypt = "username=" + paramMap.get("opName")
                + "&&password=" + paramMap.get("opPassword")
                + "&&sign=" + paramMap.get("sign")
                + "&&timestamp=" + nowTime;
        ExecuteCommandLog.info("ִ��������\ncommand=" + paramMap.get("command").toString() + "\nip=" + paramMap.get("ip"));
        String encryptAfterStr = RSAEncrypt.privateKeyEncryptForGGWPublic(encrypt);
        String url = GGW_URL + "?ip=" + paramMap.get("ip") + "&&command=" + RSAEncrypt.urlReplace(paramMap.get("command")) + "&&crypto_sign=" + encryptAfterStr;

        System.out.println("url:\n" + url);
        tempErrorUrl = url;
        String result = null;
        try {
            result = HttpUtil.get(url);
            ExecuteCommandLog.info("����API ���ؽ����\n" + result);
        } catch (Exception e) {
            System.out.println(e.getCause().getMessage());//��ȡ�쳣��Ϣ
            e.printStackTrace();
            result = "error��" + e.getCause().getMessage();
        }
        return result;
    }


    /**
     * ���ݲ�������¼ggw
     *
     * @param paramMap
     * @return
     */
    public static String loginGGWAPI(Map<String, String> paramMap) {
        String nowTime = Long.toString(new Date().getTime() / 1000);
        System.out.println("nowTime:" + cn.hutool.core.date.DateUtil.now());
        String encrypt = "username=" + paramMap.get("opName")
                + "&&password=" + paramMap.get("opPassword")
                + "&&sign=" + paramMap.get("sign")
                + "&&timestamp=" + nowTime;
        String encryptAfterStr = RSAEncrypt.privateKeyEncryptForGGWPublic(encrypt);
        String tempUrl = encryptAfterStr + "&&command=" + paramMap.get("command").toString() + "&&ip=" + paramMap.get("ip").toString();
        String url = LOGIN_GGW_URL + RSAEncrypt.urlReplace(encryptAfterStr);
        System.out.println("url:\n" + url);
        tempErrorUrl = url;
        String result = "";
        try {
            result = HttpUtil.get(url);
            if (StrUtil.isBlank(result)) {
                result = "error:login ���ز���Ϊ��";
            }
        } catch (Exception e) {
            System.out.println(e.getCause().getMessage());//��ȡ�쳣��Ϣ
            e.printStackTrace();
            result = "error��" + e.getCause().getMessage();
        }

        return result;
    }


    /**
     * ����װ������ �ŵ�html��
     *
     * @param htmlStr  ��װ������
     * @param htmlPath
     * @param filePath
     * @return
     */
    public static void addHtml(String htmlStr, String htmlPath, String filePath) {
        htmlStr = "<pre>" + htmlStr + "</pre>";
        InputStream in = null;
        File file = new File(filePath);
        try {
            if (file.exists()) {
                file.delete();
            }
            in = new ByteArrayInputStream(htmlStr.getBytes("UTF-8"));
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
                System.out.println(file.getPath() + ">�����ɹ�!");
            } else {
                file.createNewFile();
                System.out.println(file.getPath() + ">�����ɹ�!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            FileOutputStream fs = new FileOutputStream(filePath);
            int byteread = 0;
            byte[] buffer = new byte[1024];
            while ((byteread = in.read(buffer)) != -1) {
                fs.write(buffer, 0, byteread);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * ���ݵ�ǰ������жϲ����ܼ�¼�е�λ��
     *
     * @param bf  �ܽ��
     * @param cbf ��ǰ���
     */
    public static void addBf(StringBuffer bf, StringBuffer cbf) {
        if (cbf.indexOf("color:red") > -1) {
            bf.insert(0, cbf.toString());
            System.out.println("-----------------------------------------------------------------");
            System.out.println(bf);
        } else {
            bf.append(cbf.toString());
        }

    }


    public static void main(String[] args) {
        StringBuffer bf = new StringBuffer("wlf");
        StringBuffer cbf = new StringBuffer("color:red and");
        addBf(bf, cbf);
        System.out.println(bf); //����insert����������wanglf
    }


}
