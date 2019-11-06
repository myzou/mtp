package com.cter.util;


import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.sound.midi.Soundbank;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**AES ��һ�ֿ�������㷨�����û���������Ϣ���ܴ���
 * ��ԭʼ���ݽ���AES���ܺ��ڽ���Base64����ת����
 */
public class AesCBC {
    /*
     * �����õ�Key ������26����ĸ���������
     * �˴�ʹ��AES-128-CBC����ģʽ��key��ҪΪ16λ��
     */
    private static String sKey="sklhdflsjfsdgdeg";
    private static String ivParameter="cfbsdfgsdfxccvd1";
    private static AesCBC instance=null;
    private AesCBC(){

    }
    public static AesCBC getInstance(){
        if (instance==null)
            instance= new AesCBC();
        return instance;
    }
    // ����
    public String encrypt(String sSrc, String encodingFormat, String sKey, String ivParameter) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] raw = sKey.getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());//ʹ��CBCģʽ����Ҫһ������iv�������Ӽ����㷨��ǿ��
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes(encodingFormat));
        return new BASE64Encoder().encode(encrypted);//�˴�ʹ��BASE64��ת�롣
    }

    // ����
    public String decrypt(String sSrc, String encodingFormat, String sKey, String ivParameter) throws Exception {
        try {
            byte[] raw = sKey.getBytes("ASCII");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] encrypted1 = new BASE64Decoder().decodeBuffer(sSrc);//����base64����
            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original,encodingFormat);
            return originalString;
        } catch (Exception ex) {
            return null;
        }
    }

    public static void main(String[] args) throws Exception {
        // ��Ҫ���ܵ��ִ�
        String cSrc = "NkxhMHVURitIWjQ2VzE2SWNUN1RZa1FycDRaSFJISDJzMkQvbmNwajNHWnZIK0pPNEQrWk12YlFvaUJpOXd0b09EcTF4TEFRaFpKbSUwQWdNMi9EOHNYRUNpalhuOXhyb2toajZWUlJnMnBNMmIzVkxOSEVWOU5ld3FEc293ejZ2MUhzQ3BLZi85cnI2Y3VIRHN0VnNQaEhGYjAlMEFScEJrV1RhNWdvVDVCVEFMVVVqNjhnb2FiN1VZT0c4UDRRUytQZDEzRlFyK2RGejlqTnBubzdHQ1JyUHdic0RFWDlNbTJ0QnFHOVhSJTBBaVU5SEwxdUdCRmhka0hNcXROWmc2S0VYK0xEQXNlV3BkcStwUGJUbXpBQWRySXY4cC9JRGh0akErcWpxRW0zbWVKaFFGeGRsc0tTTiUwQVdZZUU5MEVKc3F6eC9PYXJuRHJ3T04vN1hPRlhNNjRRdEh3eWFJdzd2WEJxWGxRTWs3eE0vSXVXZmgwSDFsOERjdmUxTXBGcXFLa3clMEFBYjBPN2ZIRm5oaDJRdXArQ1RSNnhleW9rMFdUWURnMUJpS1BHMnFpOXV6TzRvK1owcG84ejk2MmphQkxBVkVoUzgwbTVmTzNKY3hIJTBBRVFJWjZXM2ptcFl2ekNmbzFvd2piQlRneU9BWEpIYUJRV1Fiait5dkl4dDdZbDU4TWpuRkJab3d6RXlsTDJLbXkrbUhyREpudEtCRSUwQUE4OUd3ZnZrdisyWEF6Tm9qRTZPTGlzMHJLNUhyZTZBaXNDYm9EdjBjaUFYVU5ZRGgzMzJMa1lLUm9lS2dIT3JVNmlObzkvMTNCbXklMEE3cHhXR2d2UHhWMVpxbk5neG54U3FKS2Z4U2tlSlFGemxLS3UyOE9aekY1TG9NZS80SkJHdE8xeDJUa0s4ZTBqZy9tN1NSSEZuUFdSJTBBb1hiSS91SjZjOURqTFJkYnN0aVdpTXFXSUxYVTg3Q25DV2xHTzhlSDRJS1FlNTgzWUNCSGpBdGJJY0NUMnIxOHBURVB1eUxSbTNPOSUwQWNXa2NpcXhOWjJKaERHQVd3Y3l0cEU0RktldzdJLzRwVG1IaHlTczVQUnVQN2JCVVdNTDFNK2oxRUdRbzlOTXM1bmtRWUpobWplWS8lMEFNMndiZmtPcDhEdlgvQmhqTk1EMFgyQzY5TlBTWFhPR3c0RU1tTkxRcnhFV3R3RFRnRmo1azVMYWxUWW1jZUZTbk1EYzRNVGhxUGc5JTBBRTZFZ3B3JTNEJTNE";
        System.out.println(cSrc);
        // ����
        long lStart = System.currentTimeMillis();
        String enString = AesCBC.getInstance().encrypt(cSrc,"utf-8",sKey,ivParameter);
        System.out.println("���ܺ���ִ��ǣ�"+ enString);

        long lUseTime = System.currentTimeMillis() - lStart;
        System.out.println("���ܺ�ʱ��" + lUseTime + "����");
        // ����
        lStart = System.currentTimeMillis();
        String DeString = AesCBC.getInstance().decrypt(enString,"utf-8",sKey,ivParameter);
        System.out.println("���ܺ���ִ��ǣ�" + DeString);
        lUseTime = System.currentTimeMillis() - lStart;
        System.out.println("���ܺ�ʱ��" + lUseTime + "����");
        System.out.println("==================================================================");
        BASE64Encoder encode=new BASE64Encoder();
        BASE64Decoder decoder=new BASE64Decoder();
        String data=encode.encode(cSrc.getBytes());
        System.out.println("base64���ܺ� "+ data);
        System.out.println("base64���ܺ� "+ new String(decoder.decodeBuffer(data) ) );

    }


}
