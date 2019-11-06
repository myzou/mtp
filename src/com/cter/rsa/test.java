package com.cter.rsa;

/**
 * @author op1768
 * @create 2019-08-07 17:36
 * @project mtp
 */
public class test {

    public static void main(String[] args) {
        String tempFruit = "ping: sendto: Can't assign requested address\n" +
                "ping: sendto: Can't assign requested address\n" +
                "ping: sendto: Can't assign requested address\n" +
                "ping: sendto: Can't assign requested address\n" +
                "ping: sendto: Can't assign requested address\n" +
                "PING 10.114.124.186 (10.114.124.186): 56 data bytes\n" +
                ".....\n" +
                "--- 10.114.124.186 ping statistics ---\n" +
                "5 packets transmitted, 0 packets received, 100% packet loss";
        System.out.println(tempFruit.substring(tempFruit.indexOf("received,") + 9, tempFruit.indexOf("%")).trim());
    }
}
