package com.cter.util;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class TestConnect {

    public static void main(String[] args) {
        connectIpPort("210.5.3.177", 48888, 500);
        System.out.println(1112);


    }

    private static int retestsNumbers = 1;//重试次数


    /**
     * 根据 测试ip端口是否连通正常
     *
     * @param ip
     * @param port    端口
     * @param timeOut 超市时间
     * @return
     */
    public static boolean connectIpPort(String ip, int port, int timeOut) {
        Socket connect = new Socket();
        try {
            connect.connect(new InetSocketAddress(ip, port), timeOut);//建立连接
            boolean res = connect.isConnected();//通过现有方法查看连通状态
            if (res = true) {
                System.out.println(retestsNumbers + ":true");
                return true;
            } else {
                if (retestsNumbers == 1) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    System.out.println(retestsNumbers + ":false");
                    retestsNumbers = 2;
                    return connectIpPort(ip, port, timeOut);
                } else {
                    System.out.println(retestsNumbers + ":false");
                    retestsNumbers = 1;
                    return false;
                }
            }
        } catch (IOException e) {
            if (retestsNumbers == 1) {
                System.out.println(retestsNumbers + ":false");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                retestsNumbers = 2;
                return connectIpPort(ip, port, timeOut);
            } else {
                System.out.println(retestsNumbers + ":false");
                retestsNumbers = 1;
                return false;
            }
        } finally {
            try {
                connect.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
