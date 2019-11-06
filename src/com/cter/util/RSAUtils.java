package com.cter.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;

import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

public class RSAUtils {

    public static final String CHARSET = "UTF-8";
    public static final String RSA_ALGORITHM = "RSA";


    public static Map<String, String> createKeys(int keySize){
        //ΪRSA�㷨����һ��KeyPairGenerator����
        KeyPairGenerator kpg;
        try{
            kpg = KeyPairGenerator.getInstance(RSA_ALGORITHM);
        }catch(NoSuchAlgorithmException e){
            throw new IllegalArgumentException("No such algorithm-->[" + RSA_ALGORITHM + "]");
        }

        //��ʼ��KeyPairGenerator����,��Կ����
        kpg.initialize(keySize);
        //�����ܳ׶�
        KeyPair keyPair = kpg.generateKeyPair();
        //�õ���Կ
        Key publicKey = keyPair.getPublic();
        String publicKeyStr = Base64.encodeBase64URLSafeString(publicKey.getEncoded());
        //�õ�˽Կ
        Key privateKey = keyPair.getPrivate();
        String privateKeyStr = Base64.encodeBase64URLSafeString(privateKey.getEncoded());
        Map<String, String> keyPairMap = new HashMap<String, String>();
        keyPairMap.put("publicKey", publicKeyStr);
        keyPairMap.put("privateKey", privateKeyStr);

        return keyPairMap;
    }

    /**
     * �õ���Կ
     * @param publicKey ��Կ�ַ���������base64���룩
     * @throws Exception
     */
    public static RSAPublicKey getPublicKey(String publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        //ͨ��X509�����Keyָ���ù�Կ����
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKey));
        RSAPublicKey key = (RSAPublicKey) keyFactory.generatePublic(x509KeySpec);
        return key;
    }



    /**
     * �õ�˽Կ
     * @param privateKey ��Կ�ַ���������base64���룩
     * @throws Exception
     */
    public static RSAPrivateKey getPrivateKey(String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        //ͨ��PKCS#8�����Keyָ����˽Կ����
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
        RSAPrivateKey key = (RSAPrivateKey) keyFactory.generatePrivate(pkcs8KeySpec);
        return key;
    }

    /**
     * ��Կ����
     * @param data
     * @param publicKey
     * @return
     */
    public static String publicEncrypt(String data, RSAPublicKey publicKey){
        try{
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return Base64.encodeBase64URLSafeString(rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes(CHARSET), publicKey.getModulus().bitLength()));
        }catch(Exception e){
            throw new RuntimeException("�����ַ���[" + data + "]ʱ�����쳣", e);
        }
    }

    /**
     * ˽Կ����
     * @param data
     * @param privateKey
     * @return
     */

    public static String privateDecrypt(String data, RSAPrivateKey privateKey){
        try{
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.decodeBase64(data), privateKey.getModulus().bitLength()), CHARSET);
        }catch(Exception e){
            throw new RuntimeException("�����ַ���[" + data + "]ʱ�����쳣", e);
        }
    }

    /**
     * ˽Կ����
     * @param data
     * @param privateKey
     * @return
     */

    public static String privateEncrypt(String data, RSAPrivateKey privateKey){
        try{
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            return Base64.encodeBase64URLSafeString(rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes(CHARSET), privateKey.getModulus().bitLength()));
        }catch(Exception e){
            throw new RuntimeException("�����ַ���[" + data + "]ʱ�����쳣", e);
        }
    }

    /**
     * ��Կ����
     * @param data
     * @param publicKey
     * @return
     */

    public static String publicDecrypt(String data, RSAPublicKey publicKey){
        try{
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.decodeBase64(data), publicKey.getModulus().bitLength()), CHARSET);
        }catch(Exception e){
            throw new RuntimeException("�����ַ���[" + data + "]ʱ�����쳣", e);
        }
    }

    private static byte[] rsaSplitCodec(Cipher cipher, int opmode, byte[] datas, int keySize){
        int maxBlock = 0;
        if(opmode == Cipher.DECRYPT_MODE){
            maxBlock = keySize / 8;
        }else{
            maxBlock = keySize / 8 - 11;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] buff;
        int i = 0;
        try{
            while(datas.length > offSet){
                if(datas.length-offSet > maxBlock){
                    buff = cipher.doFinal(datas, offSet, maxBlock);
                }else{
                    buff = cipher.doFinal(datas, offSet, datas.length-offSet);
                }
                out.write(buff, 0, buff.length);
                i++;
                offSet = i * maxBlock;
            }
        }catch(Exception e){
            throw new RuntimeException("�ӽ��ܷ�ֵΪ["+maxBlock+"]������ʱ�����쳣", e);
        }
        byte[] resultDatas = out.toByteArray();
        IOUtils.closeQuietly(out);
        return resultDatas;
    }

    public static void main (String[] args) throws Exception {
        Map<String, String> keyMap = RSAUtils.createKeys(1024);
//        String  publicKey = keyMap.get("publicKey");
//        String  privateKey = keyMap.get("privateKey");
        String  publicKey = "ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQDeZpgbOey3R3KXJB4YvfdiP3eVhtvPPkRzpAOZxMzOFHJsE6utuISaBcCmXmGkA3QFoqKB+twF/ifakbIsq3GQoe4CsHySTuquG+0ccrsafeMKp92KqV0mQFG1ls6eSqEeL3oalWFJjFteRCg6d0CMJA2ALsIX2nOc8ogHYlEx1+KUuOttqEKlvUFecWgltbq0qqmWMlhFhfnZo3lCgYkKadOCnYYrFMGZY1qj2zLJ5KPDI0pIt2bP5SylMUFE+uq37i1NV4+fDl/t9mkRzCYhio/KqgZU76rEDCMuN8HjaQASjIJuwAjNPWsdqEyexU4tBMSwjDzlVaWt+SHzADif op1826@CNGUMW431.cpcnet.local";
        String  privateKey ="MIIEowIBAAKCAQEA3maYGznst0dylyQeGL33Yj93lYbbzz5Ec6QDmcTMzhRybBOr\n" +
                "rbiEmgXApl5hpAN0BaKigfrcBf4n2pGyLKtxkKHuArB8kk7qrhvtHHK7Gn3jCqfd\n" +
                "iqldJkBRtZbOnkqhHi96GpVhSYxbXkQoOndAjCQNgC7CF9pznPKIB2JRMdfilLjr\n" +
                "bahCpb1BXnFoJbW6tKqpljJYRYX52aN5QoGJCmnTgp2GKxTBmWNao9syyeSjwyNK\n" +
                "SLdmz+UspTFBRPrqt+4tTVePnw5f7fZpEcwmIYqPyqoGVO+qxAwjLjfB42kAEoyC\n" +
                "bsAIzT1rHahMnsVOLQTEsIw85VWlrfkh8wA4nwIDAQABAoIBAFoBlU93etiV3sdS\n" +
                "Q6fpwaOOYfwuzuipn5RZbWee4PAKEGCy+UjeIzQeXeA21DdHen4JWtFV0BPQzxpC\n" +
                "2qP/Yn+ROTz3ZgLkU0/RV32q5RQMnIUkeKF0d5FSeH8QIGCpfVk1B6qYh/33Asc8\n" +
                "uDcpKgNNV/Vndv+mViHPecPbQ1by8cgcH5zJkaJzpGpMSXcSlg6bHc0BQj+nHrqs\n" +
                "dyi0q0ewiPRdU4ebQBd9pnQkIjK7OgAiAHpBHfg7zbs8bUfZbwvlJjt70saNrzj1\n" +
                "rzc2xGcn/qik8ixuf/qEAk5lcdKYn6yJ3bRLmnqmEf1TjCPrR4wax+hp8fD64g21\n" +
                "FJctvZECgYEA7EGYMhXoBVT3ZzpEof2ZjUkOyQ0y2JU6fQGBIdVziIc0Bxf2BIKM\n" +
                "O/SFu8NYvYzcs5TohHpfW3C070u0+cXfOxLKH3y5aVd+k/f6Op4RZvV8QgVwjlPE\n" +
                "8SaK0Mu7OjQyKMPspVFg5xlzni8XZSbVXb33Jk5fMS0Az52iGpk/CskCgYEA8PyU\n" +
                "SkI+mXQMWgL3GgAI0uvc7Xow+PUNWqO7R3DNJATPMQewjSoG0kYTbllHh3tjc49l\n" +
                "cDMxIpJk/PhndHZnoQnWmG5K1LnlFX5FsHwbimy19fg6vp1tIHn57N7nM6nS+Bza\n" +
                "gca7dwEV+kN1uDokH4C9/VQOdv47nXzdofQu9CcCgYAibkDO89BXjpVrrts3vGy3\n" +
                "YXFNLaY+WCko9KIsGx52RDX53q0U5S2owy8GOSbioPe9GDN2sxrYialkwmTCjerF\n" +
                "giyAwD2JM54X1GjcSBMDLT2JpZ9Mrrqh5lsOqNJFXjv9IuiHA0AbDeOCpxxCupEX\n" +
                "00qEg6Ft2kAnAbMRK0o7uQKBgQC/uXORENsU4wrgofNNsDFU/WmrVceYif2x2wmo\n" +
                "cMoEmf7tFx0TchgzOqVC6azK0RPyYGdnuJKi2q2VxVspgPo5WMZuR3EphSwIyYlQ\n" +
                "O6z9mDe8FV1HQaAMcn6wQTbYF4hM9UeycBW9PBPsI7eIdlk+5wJAhZtseB+d0DXU\n" +
                "H50riQKBgF2+F8c2HmN99vf84XD7kO1yjcgC4oWqdlgpPPaH4njcN/it4XrLB47U\n" +
                "PLIXieBM2kO4DamSjn3DM1mMNY0NpqIpLcjLOFPr06gUmSDE1Zgx1rfrauEjRVWc\n" +
                "nE2oyIaI29U3KjZOJH6AfAaLqXahgwe+hE6393fl3qy4/eDhRIQU";

        System.out.println("��Կ: \n\r" + publicKey);
        System.out.println("˽Կ�� \n\r" + privateKey);

        System.out.println("��Կ���ܡ���˽Կ����");
        String str = "վ�ڴ�����ǰ�����Ľ�����������û�нӵ�\n" +
                "�йص��������������ʢװ�Ĺ�Ա���٣�Ҳ��\n" +
                "��Ϊȷϵ���д�䣬���δ��ѯ�ʡ��������ż�\n" +
                "Ϊ�ʳǡ�����ٹٿ�����������֮ǰ����ƽ����\n" +
                "��¥����Ҳ�޳���ļ��󣬼��޼�����վ�ӵ���\n" +
                "����ʷ����ǰ�������󺺽�����Ҳ������Ӱ������\n" +
                "���д��⣬����ѯ�ʣ���ν�糯�Ƿ�ﴫ��";
        System.out.println("\r���ģ�\r\n" + str);
        System.out.println("\r���Ĵ�С��\r\n" + str.getBytes().length);
        String encodedData = RSAUtils.publicEncrypt(str, RSAUtils.getPublicKey(publicKey));
        System.out.println("���ģ�\r\n" + encodedData);
        String decodedData = RSAUtils.privateDecrypt(encodedData, RSAUtils.getPrivateKey(privateKey));
        System.out.println("���ܺ�����: \r\n" + decodedData);


    }
}