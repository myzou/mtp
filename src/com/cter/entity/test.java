package com.cter.entity;


import java.math.BigDecimal;
import java.util.*;

public class test   {
    public static void main(String[] args) {
        BigDecimal beforeDelay = new BigDecimal("9");
        BigDecimal afterDelay = new BigDecimal("9.6");
        System.out.println(comparisonDelay(beforeDelay,afterDelay));

         beforeDelay = new BigDecimal("9");
         afterDelay = new BigDecimal("12");
        System.out.println(comparisonDelay(beforeDelay,afterDelay));//

        beforeDelay = new BigDecimal("11");
        afterDelay = new BigDecimal("12");
        System.out.println(comparisonDelay(beforeDelay,afterDelay));

        beforeDelay = new BigDecimal("11");
        afterDelay = new BigDecimal("20");
        System.out.println(comparisonDelay(beforeDelay,afterDelay));//

        beforeDelay = new BigDecimal("51");
        afterDelay = new BigDecimal("57");
        System.out.println(comparisonDelay(beforeDelay,afterDelay));

        beforeDelay = new BigDecimal("51");
        afterDelay = new BigDecimal("60");
        System.out.println(comparisonDelay(beforeDelay,afterDelay));//

        beforeDelay = new BigDecimal("101");
        afterDelay = new BigDecimal("110");
        System.out.println(comparisonDelay(beforeDelay,afterDelay));

        beforeDelay = new BigDecimal("100");
        afterDelay = new BigDecimal("200");
        System.out.println(comparisonDelay(beforeDelay,afterDelay));

    }

    /**
     * 前后 Delay 对比
     * before 10m以下    2ms出异常
     * before 10-50ms   5ms 出异常
     * before 50-100ms     8ms出异常
     * before 100ms以上    10ms出异常
     * 异常true，正常false
     * @param beforeDelay 之前 Delay
     * @param afterDelay  之后 Delay
     * @return
     */
    public static boolean comparisonDelay(BigDecimal beforeDelay, BigDecimal afterDelay){
        boolean flag=false;//前后对比Delay 异常为true,默认 为false
        beforeDelay.doubleValue();
        if(beforeDelay.floatValue()<10&&afterDelay.subtract(beforeDelay).floatValue() >= 2){
            return true;
        }
        if(beforeDelay.floatValue()<50&&afterDelay.subtract(beforeDelay).floatValue() >= 5){
            return true;
        }
        if(beforeDelay.floatValue()<100&&afterDelay.subtract(beforeDelay).floatValue() >= 8){
            return true;
        }
        if(beforeDelay.floatValue()>=100&&afterDelay.subtract(beforeDelay).floatValue() >= 10){
            return true;
        }
        return flag;
    }

    public static void getList(List<String> list,int n){
        Queue<String> dq=new PriorityQueue();

        Map<String,Integer> map=new TreeMap<>();
        for (String s : list) {
            s=s.replace("邹进颖","黎卫雄");
            if(map.get(s)!=null&&map.get(s)!=0){
                map.put(s, map.get(s)+1);
            }else {
                map.put(s,1);
            }
        }


        ArrayList<Map.Entry<String, Integer>> list1 = new ArrayList<>(map.entrySet());
        Collections.sort(list1, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });

        int begin=1;

        for(Map.Entry<String,Integer> mapping:list1){
            if(n==0||n>list.size()){
                System.out.println(mapping.getKey()+":"+mapping.getValue());
            }else {
                if(begin<=n){
                    begin+=1;
                    System.out.println(mapping.getKey()+":"+mapping.getValue());
                }
            }

        }


    }



}
