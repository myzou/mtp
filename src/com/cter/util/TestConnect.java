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
     * ���� ����ip�˿��Ƿ���ͨ����
     * @param ip
     * @param port      �˿�
     * @param timeOut   ����ʱ��
     * @return
     */
    public  static boolean connectIpPort(String ip,int port,int timeOut){
        Socket connect = new Socket();
        try {
            connect.connect(new InetSocketAddress(ip, port), timeOut);//��������
            boolean res = connect.isConnected();//ͨ�����з����鿴��ͨ״̬
            System.out.println(res);//trueΪ��ͨ
            return res;
        } catch (IOException e) {
            System.out.println("false");//������ͨʱ��ֱ�����쳣���쳣���񼴿�
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
