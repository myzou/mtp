package com.cter.rsa;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author op1768
 * @create 2019-07-30 14:41
 * @project mtp
 */
public class RsaTest {

    public static void main(String[] args) throws Exception {
//        updateMethod(null,null);
//        initMethod();
//        test();

        long strat=System.currentTimeMillis();
        String encryptTotp="username=op1768&&password=Abc10151015657169&&sign=123456789&&timestamp=1572916643";
        String encrypt="username=op1768&&password=Abc10151015&&sign=123456789&&timestamp=1572916643";

        System.out.println(RSAEncrypt.privateKeyEncryptForGGWPublic(encrypt));
        System.out.println(RSAEncrypt.privateKeyEncryptForGGWPublic(encryptTotp));

        long end=System.currentTimeMillis();
        System.out.println("��ʱ��"+(end-strat)/1000.00+"s");


    }

    public static String getGGWAPI(Map<String, String> paramMap){
        String GGW_URL = "http://210.5.3.177:48888/GetLoginSession/RSA?crypto_sign=";

        String nowTime=Long.toString(new Date().getTime()/1000);
        System.out.println("nowTime:"+ cn.hutool.core.date.DateUtil.now());
        String encrypt="username="+paramMap.get("opName")
                +"&&password="+paramMap.get("opPassword")
                +"&&sign="+paramMap.get("sign")
                +"&&timestamp="+nowTime;
        String encryptAfterStr= RSAEncrypt.privateKeyEncryptForGGWPublic(encrypt);
        String tempUrl=encryptAfterStr+"&&command="+paramMap.get("command").toString()+"&&ip="+paramMap.get("ip").toString();
        String url=GGW_URL+RSAEncrypt.urlReplace(tempUrl);
        System.out.println("url:\n"+url);
        String result = HttpUtil.get(url);
        return result;
    }


    public static void test() throws Exception{
         String   privateKey="MIIEqQIBAAKCAQEAsywwyYH6DMe7cBIpVSYDNJ3P7U1kHVmfd60vZXAIgIBvENMI\n" +
                    "5y+PFXWpNf8bwIqhF7CBJ6zctNFw/ueNVOqPDBz9TYE6ootVTUCUC8F84aFdwDF2\n" +
                    "KkYSAhXtJ+tG8f10zcg2Qi23MY6CJ9FJzivBUmBOMDRGljbv35Id10D6nXcEVYCP\n" +
                    "ay0g9ROK9Z2HiiWDfJT4E8wDaejRgUO4iWxG6CvRPqh6aNW1RGxJMCLmV2eqY481\n" +
                    "bZYQCG06u//aJc10BuA5F0O22RZNm8lMpDa8E1EGaHKpp/ukeJXMREqXY3zsuy5g\n" +
                    "EfyvHXu2jiBKDru0Ijt91FjCHQPRY5QunGRbFQIDAQABAoIBAHNKvT39OCSfJVqR\n" +
                    "JS2YXzmtTs2ZHhHQyM9ejoAMXjwp6M3/rdvte3nIk5CeToJP8ibnOcSI9CAaIUa1\n" +
                    "QvlgUZnu0YPc4xkS5Vnncxw5bh1WE5iHe0zqd84Fw+wK9kTi3hVIfrmI5zepRtFO\n" +
                    "V9qlxyk1tTpJeR5RzO4QZEYXjeg3VpCM0uZiTpaVMD0q4DaqffJYRTSWxam75/7a\n" +
                    "2W0WONRJ9kjfh1wI+1zC9NBSEOBVkOytleGIdIDeeh79UC3e2O7Q0FabM00wMFB+\n" +
                    "OZZ6XmkXP1qB4ZgYtsFrcx+nXk43gR9fcZ2k1DS2X7HZIJ2eBoAbs25Zhtih3hXT\n" +
                    "mtVGnSUCgYkA2ng2KyZEx7IwCU8HMeMzux2hO5nUcD6P+/1n/e2y6+dnigZWWiPo\n" +
                    "lDE9vEoXKW42pXn7W6WbgopRTPhEjeoSWTdPo/vypxNb1U7AyvB33QcnU5vvLyPZ\n" +
                    "Nybstbg2L4APgfIQ2Blfxn7Pl5xb73HrYxat80SgYEG9uDbdHH15EdWnIVEpMUbM\n" +
                    "qwJ5ANHzyhUZ+ymuQ08NnM9o5hGsJFnMRgZmjntQ/U3R35pxLR2/TG55FJQCKbb4\n" +
                    "vxhqlOB2sd0q0OW5e77YnEn1bPxzA+kxTucQUcI07pgqBHeVEmenqZYm7Ysl8SOc\n" +
                    "k82bpbDPOZjj7Hb2h1Zk++OhkCUMglHgt333PwKBiElTB3s8AOG1YIH/UD7iLr6i\n" +
                    "lzbVzoNNvC2rQ3fqi565r/qXXewrGSiDzmCfzV5Oa3DmnAKKdsM9gUQAH6Ix6a6f\n" +
                    "7c7wBOUmji/tmq+CPfilKgHZ2FFOCt2D5vamuSkAJjRK9nRRL+ADN9F+3SxkY8iC\n" +
                    "GCgJ5HnqFq75WwTw5+2gc9Ou1dCAHrkCeQC7o7KcIVhD7Cbj5DqddBcK/FqQluyO\n" +
                    "k+ILfFqmzkeVdEjqKSpaIYSuszAtAytm2vqrkelszPNeHOPnsRgdUWdSSEjahOML\n" +
                    "x54Kf9wtyn+rkGOemE4MkEYE8qgNN8cxH0Z6OvozFqEjwTk7kY096As30XI3Uysr\n" +
                    "PWkCgYg9oBxGUqpK2VkIDYqMCpfVvwREO3alQABKyDm0yjPRWocvwvOmjGat25tt\n" +
                    "6QHRkmPhzUTAGCgd0wrhE8HLvlS4QuezqsKnYpQBprsl/45Kf1ff4WFyH6/b9GWq\n" +
                    "dQyywnQGwG6JEGCE9OMwIgRzTDj3fMmbUZ+DbkGHBBRwYFUcw7K94iZTpn/D";
            String  publicKey="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAh/hChlpkRA+zkkEB8ZoC\n" +
                    "rVcsYsbFSXYoTBKlrdCu0LiGeKs2T+U7kyZ/WZ8GP498PIucz6GYN03BWOOPe5nH\n" +
                    "WfwO05XqTid6+0ni+Bfy4Ev0FyCOQsodmQpH4ytgn0UOp8BZyJTwdN4rtMcuY/FF\n" +
                    "nyAsFpg9+F0DtlM/dVMj/UcWaMiZIBaa35XkXoPa+ng8Z7ORVOPiRHXfMGrlb9gZ\n" +
                    "WF5XHN1SHNBKha1uF3wexDHVm4+k3HvbaZFkHrgLndaYuCPPtetnSNUOw2iaiNo7\n" +
                    "Nfze1Y4ACyfvRczHEGxFEC9X7tj/Rcy2gmC9JCErxDQAHitX8DJrKtoeSJN8GvZZ\n" +
                    "JQIDAQAB";
        System.out.println("-------------privateKey-----------------");
        System.out.println(privateKey);
        System.out.println("------------publicKey------------------");
        System.out.println(publicKey);


        System.out.println("--------------��Կ����˽Կ���ܹ���-------------------");
        String plainText="username=op1768&&password=Abc2018101512345&&sign=12345&&timestamp=1564560620";
        //��Կ���ܹ���
        byte[] cipherData=RSAEncrypt.encrypt(RSAEncrypt.loadPublicKeyByStr(publicKey),plainText.getBytes());
        String cipher= Base64.encode(cipherData);
        System.out.println("ԭ�ģ�"+plainText);
        System.out.println("���ܣ�"+cipher);
        //˽Կ���ܹ���
    /*     cipher="AP8de+R0zGV/RlQRZ1nyq1oDRbFng1e12y0gr8zB84qUgNr6QncTddV/t+mjomJtQoztNEMmCt2gXe/FAusA0VzuE2b/SuJhWEfUyP8D7gKoXKKyFZZqWlf2PXHGCKvNVINV/2R+n1af5+Y9bXDDT1h2Pi0sOigrWehVVKUmRp3U4bM+gCfvGyzjycwyNlZ1lv0BM+LTnsjgjrdEanAJNq7qrzFHosnewtN2vso0ME/jvFPAOV5OOs1xClWU4BklBQzm65ls/qphW7iOmBE6OyERd3cJiUzj8Eyf6rL1TwlrS/rUOMfg2LuwEzdPwtJ+GbgDTLxDG5zJP5QyIiK02A==";
        byte[] res=RSAEncrypt.decrypt(RSAEncrypt.loadPrivateKeyByStr(privateKey), Base64.decode(cipher));
        String restr=new String(res);
        System.out.println("ԭ�ģ�"+plainText);
        System.out.println("���ܣ�"+cipher);
        System.out.println("���ܣ�"+restr);*/
    }
    /**
     * �޸Ĺ�Կ��Կд��
     * @throws Exception
     */
    public static void updateMethod(String privateKey,String publicKey) throws Exception {
        if(StrUtil.isBlank(privateKey)){
            privateKey="MIIEqQIBAAKCAQEAsywwyYH6DMe7cBIpVSYDNJ3P7U1kHVmfd60vZXAIgIBvENMI\n" +
                    "5y+PFXWpNf8bwIqhF7CBJ6zctNFw/ueNVOqPDBz9TYE6ootVTUCUC8F84aFdwDF2\n" +
                    "KkYSAhXtJ+tG8f10zcg2Qi23MY6CJ9FJzivBUmBOMDRGljbv35Id10D6nXcEVYCP\n" +
                    "ay0g9ROK9Z2HiiWDfJT4E8wDaejRgUO4iWxG6CvRPqh6aNW1RGxJMCLmV2eqY481\n" +
                    "bZYQCG06u//aJc10BuA5F0O22RZNm8lMpDa8E1EGaHKpp/ukeJXMREqXY3zsuy5g\n" +
                    "EfyvHXu2jiBKDru0Ijt91FjCHQPRY5QunGRbFQIDAQABAoIBAHNKvT39OCSfJVqR\n" +
                    "JS2YXzmtTs2ZHhHQyM9ejoAMXjwp6M3/rdvte3nIk5CeToJP8ibnOcSI9CAaIUa1\n" +
                    "QvlgUZnu0YPc4xkS5Vnncxw5bh1WE5iHe0zqd84Fw+wK9kTi3hVIfrmI5zepRtFO\n" +
                    "V9qlxyk1tTpJeR5RzO4QZEYXjeg3VpCM0uZiTpaVMD0q4DaqffJYRTSWxam75/7a\n" +
                    "2W0WONRJ9kjfh1wI+1zC9NBSEOBVkOytleGIdIDeeh79UC3e2O7Q0FabM00wMFB+\n" +
                    "OZZ6XmkXP1qB4ZgYtsFrcx+nXk43gR9fcZ2k1DS2X7HZIJ2eBoAbs25Zhtih3hXT\n" +
                    "mtVGnSUCgYkA2ng2KyZEx7IwCU8HMeMzux2hO5nUcD6P+/1n/e2y6+dnigZWWiPo\n" +
                    "lDE9vEoXKW42pXn7W6WbgopRTPhEjeoSWTdPo/vypxNb1U7AyvB33QcnU5vvLyPZ\n" +
                    "Nybstbg2L4APgfIQ2Blfxn7Pl5xb73HrYxat80SgYEG9uDbdHH15EdWnIVEpMUbM\n" +
                    "qwJ5ANHzyhUZ+ymuQ08NnM9o5hGsJFnMRgZmjntQ/U3R35pxLR2/TG55FJQCKbb4\n" +
                    "vxhqlOB2sd0q0OW5e77YnEn1bPxzA+kxTucQUcI07pgqBHeVEmenqZYm7Ysl8SOc\n" +
                    "k82bpbDPOZjj7Hb2h1Zk++OhkCUMglHgt333PwKBiElTB3s8AOG1YIH/UD7iLr6i\n" +
                    "lzbVzoNNvC2rQ3fqi565r/qXXewrGSiDzmCfzV5Oa3DmnAKKdsM9gUQAH6Ix6a6f\n" +
                    "7c7wBOUmji/tmq+CPfilKgHZ2FFOCt2D5vamuSkAJjRK9nRRL+ADN9F+3SxkY8iC\n" +
                    "GCgJ5HnqFq75WwTw5+2gc9Ou1dCAHrkCeQC7o7KcIVhD7Cbj5DqddBcK/FqQluyO\n" +
                    "k+ILfFqmzkeVdEjqKSpaIYSuszAtAytm2vqrkelszPNeHOPnsRgdUWdSSEjahOML\n" +
                    "x54Kf9wtyn+rkGOemE4MkEYE8qgNN8cxH0Z6OvozFqEjwTk7kY096As30XI3Uysr\n" +
                    "PWkCgYg9oBxGUqpK2VkIDYqMCpfVvwREO3alQABKyDm0yjPRWocvwvOmjGat25tt\n" +
                    "6QHRkmPhzUTAGCgd0wrhE8HLvlS4QuezqsKnYpQBprsl/45Kf1ff4WFyH6/b9GWq\n" +
                    "dQyywnQGwG6JEGCE9OMwIgRzTDj3fMmbUZ+DbkGHBBRwYFUcw7K94iZTpn/D";
        }
        if(StrUtil.isBlank(publicKey)){
            publicKey="MIIBCgKCAQEAsywwyYH6DMe7cBIpVSYDNJ3P7U1kHVmfd60vZXAIgIBvENMI5y+P\n" +
                    "FXWpNf8bwIqhF7CBJ6zctNFw/ueNVOqPDBz9TYE6ootVTUCUC8F84aFdwDF2KkYS\n" +
                    "AhXtJ+tG8f10zcg2Qi23MY6CJ9FJzivBUmBOMDRGljbv35Id10D6nXcEVYCPay0g\n" +
                    "9ROK9Z2HiiWDfJT4E8wDaejRgUO4iWxG6CvRPqh6aNW1RGxJMCLmV2eqY481bZYQ\n" +
                    "CG06u//aJc10BuA5F0O22RZNm8lMpDa8E1EGaHKpp/ukeJXMREqXY3zsuy5gEfyv\n" +
                    "HXu2jiBKDru0Ijt91FjCHQPRY5QunGRbFQIDAQAB";
        }
        System.out.println("-------------privateKey-----------------");
        System.out.println(privateKey);
        System.out.println("------------publicKey------------------");
        System.out.println(publicKey);


        System.out.println("--------------��Կ����˽Կ���ܹ���-------------------");
        String plainText="" +
                "lab123";
        //��Կ���ܹ���
        byte[] cipherData=RSAEncrypt.encrypt(RSAEncrypt.loadPublicKeyByStr(publicKey),plainText.getBytes());
        String cipher= Base64.encode(cipherData);
        //˽Կ���ܹ���
        byte[] res=RSAEncrypt.decrypt(RSAEncrypt.loadPrivateKeyByStr(privateKey), Base64.decode(cipher));
        String restr=new String(res);
        System.out.println("ԭ�ģ�"+plainText);
        System.out.println("���ܣ�"+cipher);
        System.out.println("���ܣ�"+restr);
        System.out.println();

        System.out.println("--------------˽Կ���ܹ�Կ���ܹ���-------------------");
        plainText="ihep_˽Կ���ܹ�Կ����";
        //˽Կ���ܹ���
        cipherData=RSAEncrypt.encrypt(RSAEncrypt.loadPrivateKeyByStr(privateKey),plainText.getBytes());
        cipher=Base64.encode(cipherData);
        //��Կ���ܹ���
        res=RSAEncrypt.decrypt(RSAEncrypt.loadPublicKeyByStr(publicKey), Base64.decode(cipher));
        restr=new String(res);
        System.out.println("ԭ�ģ�"+plainText);
        System.out.println("���ܣ�"+cipher);
        System.out.println("���ܣ�"+restr);
        System.out.println();

        System.out.println("---------------˽Կǩ������------------------");
        String content="ihep_��������ǩ����ԭʼ����";
        String signstr=RSASignature.sign(content,privateKey);
        System.out.println("ǩ��ԭ����"+content);
        System.out.println("ǩ������"+signstr);
        System.out.println();

        System.out.println("---------------��ԿУ��ǩ��------------------");
        System.out.println("ǩ��ԭ����"+content);
        System.out.println("ǩ������"+signstr);

        System.out.println("��ǩ�����"+RSASignature.doCheck(content, signstr, publicKey));
        System.out.println();

    }

    /**
     * ����ժ���ķ���
     */
    public static void initMethod() throws Exception{
        RSAEncrypt rsaEncrypt=new RSAEncrypt();

        String filepath="D:/";

        //���ɹ�Կ��˽Կ�ļ�
        RSAEncrypt.genKeyPair(filepath);

        System.out.println("--------------��Կ����˽Կ���ܹ���-------------------");
        String plainText="ihep_��Կ����˽Կ����";
        //��Կ���ܹ���
        byte[] cipherData=RSAEncrypt.encrypt(RSAEncrypt.loadPublicKeyByStr(RSAEncrypt.loadPublicKeyByFile(filepath)),plainText.getBytes());
        String cipher= Base64.encode(cipherData);
        //˽Կ���ܹ���
        byte[] res=RSAEncrypt.decrypt(RSAEncrypt.loadPrivateKeyByStr(RSAEncrypt.loadPrivateKeyByFile(filepath)), Base64.decode(cipher));
        String restr=new String(res);
        System.out.println("ԭ�ģ�"+plainText);
        System.out.println("���ܣ�"+cipher);
        System.out.println("���ܣ�"+restr);
        System.out.println();

        System.out.println("--------------˽Կ���ܹ�Կ���ܹ���-------------------");
        plainText="ihep_˽Կ���ܹ�Կ����";
        //˽Կ���ܹ���
        cipherData=RSAEncrypt.encrypt(RSAEncrypt.loadPrivateKeyByStr(RSAEncrypt.loadPrivateKeyByFile(filepath)),plainText.getBytes());
        cipher=Base64.encode(cipherData);
        //��Կ���ܹ���
        res=RSAEncrypt.decrypt(RSAEncrypt.loadPublicKeyByStr(RSAEncrypt.loadPublicKeyByFile(filepath)), Base64.decode(cipher));
        restr=new String(res);
        System.out.println("ԭ�ģ�"+plainText);
        System.out.println("���ܣ�"+cipher);
        System.out.println("���ܣ�"+restr);
        System.out.println();

        System.out.println("---------------˽Կǩ������------------------");
        String content="ihep_��������ǩ����ԭʼ����";
        String signstr=RSASignature.sign(content,RSAEncrypt.loadPrivateKeyByFile(filepath));
        System.out.println("ǩ��ԭ����"+content);
        System.out.println("ǩ������"+signstr);
        System.out.println();

        System.out.println("---------------��ԿУ��ǩ��------------------");
        System.out.println("ǩ��ԭ����"+content);
        System.out.println("ǩ������"+signstr);

        System.out.println("��ǩ�����"+RSASignature.doCheck(content, signstr, RSAEncrypt.loadPublicKeyByFile(filepath)));
        System.out.println();
    }
}