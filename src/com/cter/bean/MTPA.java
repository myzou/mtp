package com.cter.bean;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import net.sf.json.JSONObject;


/**
 * 用来接收 执行mtp的实体类
 */
public class MTPA {

//    {"ticketName":"HD0000003111868","pePorts":[{"internalSiteId":"49906-01","peRouter":"USLAXCOS1003E","pePortInterface":"ge-0/1/2.2011","peWanIp":"10.117.136.85","ceWanIp":"10.200.30.14","circuitNumber":"","peInterface":"","tcpType":"tcp","VRFSiteId":""}],"tense":"after","internalSiteIdAll":"49906-01;"}

    private String ticketName;
    private List<PePort> pePorts = new ArrayList<PePort>();
    private String tense;//做维护前  还是之后
    private String internalSiteIdAll;//用于接收所有的 siteid
    private  String startTime;//维护开始时间
    private  String endTime;//维护结束时间


    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getTense() {
        return tense;
    }


    public void setTense(String tense) {
        this.tense = tense;
    }


    public String getTicketName() {
        return ticketName;
    }


    public void setTicketName(String ticketName) {
        this.ticketName = ticketName;
    }


    public List<PePort> getPePorts() {
        return pePorts;
    }


    public void setPePorts(List<PePort> pePorts) {
        this.pePorts = pePorts;
    }

    public String getInternalSiteIdAll() {
        return internalSiteIdAll;
    }

    public void setInternalSiteIdAll(String internalSiteIdAll) {
        this.internalSiteIdAll = internalSiteIdAll;
    }

    public static void main(String[] args) {
        MTPA aMtpa = new MTPA();
        aMtpa.setTicketName("123456");
        aMtpa.setTense("before");
        List<PePort> pePorts = new ArrayList<PePort>();
        PePort pePort = new PePort();
        pePort.setInternalSiteId("25253-01");
        pePort.setPeRouter("CNGUZYUJ1002C");
        pePort.setPePortInterface("xe-1/2/0.50");
        pePort.setCeWanIp("218.96.232.9");
        pePort.setPeWanIp("218.96.232.10");
        pePort.setTcpType("trunk");
        pePorts.add(pePort);

        PePort pePort1 = new PePort();
        pePort1.setInternalSiteId("25253-02");
        pePort1.setPeRouter("CNGUZYUJ1002C");
        pePort1.setPePortInterface("xe-1/2/0.50");
        pePort1.setCeWanIp("218.96.232.10");
        pePort1.setPeWanIp("218.96.232.9");
        pePort1.setTcpType("trunk");
        pePort1.setProviderCircuitNum("");
        pePort1.setCircuiltNumber("");
        pePort1.setProviderCircuitNum("");
        pePorts.add(pePort1);
        aMtpa.setPePorts(pePorts);
        Gson gson = new Gson();
        String jsonStr = "\t" + gson.toJson(aMtpa);
        System.out.println(jsonStr);
        MTPA mtpa = gson.fromJson(jsonStr, MTPA.class);
        String asa = mtpa.getPePorts().get(0).getPeWanIp();
        System.out.println(mtpa.getPePorts().get(0).getPeWanIp());
        System.out.println(mtpa);
    }


}
