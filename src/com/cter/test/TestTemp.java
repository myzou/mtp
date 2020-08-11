package com.cter.test;

import cn.hutool.core.date.DateUtil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author op1768
 * @create 2019-07-26 12:53
 * @project mtp
 */
public class TestTemp  extends  Thread{


    private String name;

    public TestTemp(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        try {
            System.out.println(DateUtil.now()+"\t"+"name:"+name);
            Thread.sleep(5000);
            System.out.println(DateUtil.now()+"\t"+"Name:"+name);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        for (int i = 0; i <10 ; i++) {
            TestTemp testTemp=new TestTemp("name"+i);
            testTemp.start();
        }
    }
}
