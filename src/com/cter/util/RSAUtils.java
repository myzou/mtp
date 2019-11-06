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
        //为RSA算法创建一个KeyPairGenerator对象
        KeyPairGenerator kpg;
        try{
            kpg = KeyPairGenerator.getInstance(RSA_ALGORITHM);
        }catch(NoSuchAlgorithmException e){
            throw new IllegalArgumentException("No such algorithm-->[" + RSA_ALGORITHM + "]");
        }

        //初始化KeyPairGenerator对象,密钥长度
        kpg.initialize(keySize);
        //生成密匙对
        KeyPair keyPair = kpg.generateKeyPair();
        //得到公钥
        Key publicKey = keyPair.getPublic();
        String publicKeyStr = Base64.encodeBase64URLSafeString(publicKey.getEncoded());
        //得到私钥
        Key privateKey = keyPair.getPrivate();
        String privateKeyStr = Base64.encodeBase64URLSafeString(privateKey.getEncoded());
        Map<String, String> keyPairMap = new HashMap<String, String>();
        keyPairMap.put("publicKey", publicKeyStr);
        keyPairMap.put("privateKey", privateKeyStr);

        return keyPairMap;
    }

    /**
     * 得到公钥
     * @param publicKey 密钥字符串（经过base64编码）
     * @throws Exception
     */
    public static RSAPublicKey getPublicKey(String publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        //通过X509编码的Key指令获得公钥对象
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKey));
        RSAPublicKey key = (RSAPublicKey) keyFactory.generatePublic(x509KeySpec);
        return key;
    }



    /**
     * 得到私钥
     * @param privateKey 密钥字符串（经过base64编码）
     * @throws Exception
     */
    public static RSAPrivateKey getPrivateKey(String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        //通过PKCS#8编码的Key指令获得私钥对象
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
        RSAPrivateKey key = (RSAPrivateKey) keyFactory.generatePrivate(pkcs8KeySpec);
        return key;
    }

    /**
     * 公钥加密
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
            throw new RuntimeException("加密字符串[" + data + "]时遇到异常", e);
        }
    }

    /**
     * 私钥解密
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
            throw new RuntimeException("解密字符串[" + data + "]时遇到异常", e);
        }
    }

    /**
     * 私钥加密
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
            throw new RuntimeException("加密字符串[" + data + "]时遇到异常", e);
        }
    }

    /**
     * 公钥解密
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
            throw new RuntimeException("解密字符串[" + data + "]时遇到异常", e);
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
            throw new RuntimeException("加解密阀值为["+maxBlock+"]的数据时发生异常", e);
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

        System.out.println("公钥: \n\r" + publicKey);
        System.out.println("私钥： \n\r" + privateKey);

        System.out.println("公钥加密——私钥解密");
        String str = "站在大明门前守卫的禁卫军，事先没有接到\n" +
                "有关的命令，但看到大批盛装的官员来临，也就\n" +
                "以为确系举行大典，因而未加询问。进大明门即\n" +
                "为皇城。文武百官看到端门午门之前气氛平静，\n" +
                "城楼上下也无朝会的迹象，既无几案，站队点名\n" +
                "的御史和御前侍卫“大汉将军”也不见踪影，不免\n" +
                "心中揣测，互相询问：所谓午朝是否讹传？";
        System.out.println("\r明文：\r\n" + str);
        System.out.println("\r明文大小：\r\n" + str.getBytes().length);
        String encodedData = RSAUtils.publicEncrypt(str, RSAUtils.getPublicKey(publicKey));
        System.out.println("密文：\r\n" + encodedData);
        String decodedData = RSAUtils.privateDecrypt(encodedData, RSAUtils.getPrivateKey(privateKey));
        System.out.println("解密后文字: \r\n" + decodedData);


    }
}