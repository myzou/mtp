package com.cter.util;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class TestConnect {

    public static void main(String[] args) {
        connectIpPort("210.5.3.177", 48888,500);
        System.out.println(1112);


    }


    /**
     * 根据 测试ip端口是否连通正常
     * @param ip
     * @param port      端口
     * @param timeOut   超市时间
     * @return
     */
    public  static boolean connectIpPort(String ip,int port,int timeOut){
        Socket connect = new Socket();
        try {
            connect.connect(new InetSocketAddress(ip, port), timeOut);//建立连接
            boolean res = connect.isConnected();//通过现有方法查看连通状态
            System.out.println(res);//true为连通
            return res;
        } catch (IOException e) {
            System.out.println("false");//当连不通时，直接抛异常，异常捕获即可
            return false;
        } finally {
            try {
                connect.close();
            } catch (IOException e) {
                System.out.println("false");
                return false;
            }
        }
    }



}
