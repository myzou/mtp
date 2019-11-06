package com.cter.rsa;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.asymmetric.RSA;
import sun.security.pkcs.PKCS10;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RSAEncrypt {
    /**
     * �ֽ�����ת�ַ���ר�ü���
     */
    private static final char[] HEX_CHAR = {'0', '1', '2', '3', '4', '5', '6',
            '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * ���������Կ��
     */
    public static void genKeyPair(String filePath) {
        // KeyPairGenerator���������ɹ�Կ��˽Կ�ԣ�����RSA�㷨���ɶ���
        KeyPairGenerator keyPairGen = null;
        try {
            keyPairGen = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // ��ʼ����Կ������������Կ��СΪ96-1024λ
        keyPairGen.initialize(2048, new SecureRandom());
        // ����һ����Կ�ԣ�������keyPair��
        KeyPair keyPair = keyPairGen.generateKeyPair();
        // �õ�˽Կ
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        // �õ���Կ
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        try {

            // �õ���Կ�ַ���
            String publicKeyString = Base64.encode(publicKey.getEncoded());
            // �õ�˽Կ�ַ���
            String privateKeyString = Base64.encode(privateKey.getEncoded());
            // ����Կ��д�뵽�ļ�
            FileWriter pubfw = new FileWriter(filePath + "/publicKey.keystore");
            FileWriter prifw = new FileWriter(filePath + "/privateKey.keystore");
            BufferedWriter pubbw = new BufferedWriter(pubfw);
            BufferedWriter pribw = new BufferedWriter(prifw);
            pubbw.write(publicKeyString);
            pribw.write(privateKeyString);
            pubbw.flush();
            pubbw.close();
            pubfw.close();
            pribw.flush();
            pribw.close();
            prifw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * ���ļ����������м��ع�Կ
     *
     * @param path ��Կ������
     * @throws Exception ���ع�Կʱ�������쳣
     */
    public static String loadPublicKeyByFile(String path) throws Exception {
        try {
            BufferedReader br = new BufferedReader(new FileReader(path
                    + "/publicKey.keystore"));
            String readLine = null;
            StringBuilder sb = new StringBuilder();
            while ((readLine = br.readLine()) != null) {
                sb.append(readLine);
            }
            br.close();
            return sb.toString();
        } catch (IOException e) {
            throw new Exception("��Կ��������ȡ����");
        } catch (NullPointerException e) {
            throw new Exception("��Կ������Ϊ��");
        }
    }

    /**
     * ���ַ����м��ع�Կ
     *
     * @param publicKeyStr ��Կ�����ַ���
     * @throws Exception ���ع�Կʱ�������쳣
     */
    public static RSAPublicKey loadPublicKeyByStr(String publicKeyStr)
            throws Exception {
        RSAPublicKey publicKey = null;
        try {
            byte[] buffer = Base64.decode(publicKeyStr);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            try {
                publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
                buffer = Base64.decode(Base64.encode(publicKeyStr.getBytes()));
                keyFactory = KeyFactory.getInstance("RSA");
                keySpec = new X509EncodedKeySpec(buffer);
                publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
            }
            return publicKey;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new Exception("�޴��㷨");
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
            throw new Exception("��Կ�Ƿ�");
        } catch (NullPointerException e) {
            e.printStackTrace();
            throw new Exception("��Կ����Ϊ��");
        }
    }

    /**
     * ���ļ��м���˽Կ
     * s     * @param path ˽Կ�ļ���
     *
     * @return �Ƿ�ɹ�
     * @throws Exception
     */
    public static String loadPrivateKeyByFile(String path) throws Exception {
        try {
            BufferedReader br = new BufferedReader(new FileReader(path
                    + "/privateKey.keystore"));
            String readLine = null;
            StringBuilder sb = new StringBuilder();
            while ((readLine = br.readLine()) != null) {
                sb.append(readLine);
            }
            br.close();
            return sb.toString();
        } catch (IOException e) {
            throw new Exception("˽Կ���ݶ�ȡ����");
        } catch (NullPointerException e) {
            throw new Exception("˽Կ������Ϊ��");
        }
    }

    public static RSAPrivateKey loadPrivateKeyByStr(String privateKeyStr)
            throws Exception {
        RSAPrivateKey rsaPrivateKey = null;
        try {
            byte[] buffer = Base64.decode(privateKeyStr);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            try {
                rsaPrivateKey = (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
                buffer = Base64.decode(Base64.encode(privateKeyStr.getBytes()));
                keySpec = new PKCS8EncodedKeySpec(buffer);
                keyFactory = KeyFactory.getInstance("RSA");
                rsaPrivateKey = (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
            }
            return rsaPrivateKey;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new Exception("�޴��㷨");
        } catch (NullPointerException e) {
            e.printStackTrace();
            throw new Exception("˽Կ����Ϊ��");
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("˽Կ����Ϊ��");
        }
    }

    /**
     * ��Կ���ܹ���
     *
     * @param publicKey     ��Կ
     * @param plainTextData ��������
     * @return
     * @throws Exception ���ܹ����е��쳣��Ϣ
     */
    public static byte[] encrypt(RSAPublicKey publicKey, byte[] plainTextData)
            throws Exception {
        if (publicKey == null) {
            throw new Exception("���ܹ�ԿΪ��, ������");
        }
        Cipher cipher = null;
        try {
            // ʹ��Ĭ��RSA
            cipher = Cipher.getInstance("RSA");
            // cipher= Cipher.getInstance("RSA", new BouncyCastleProvider());
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] output = cipher.doFinal(plainTextData);
            return output;
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("�޴˼����㷨");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeyException e) {
            throw new Exception("���ܹ�Կ�Ƿ�,����");
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
            throw new Exception("���ĳ��ȷǷ�");
        } catch (BadPaddingException e) {
            e.printStackTrace();
            throw new Exception("������������");
        }
    }

    /**
     * ˽Կ���ܹ���
     *
     * @param privateKey    ˽Կ
     * @param plainTextData ��������
     * @return
     * @throws Exception ���ܹ����е��쳣��Ϣ
     */
    public static byte[] encrypt(RSAPrivateKey privateKey, byte[] plainTextData)
            throws Exception {
        if (privateKey == null) {
            throw new Exception("����˽ԿΪ��, ������");
        }
        Cipher cipher = null;
        try {
            // ʹ��Ĭ��RSA
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            byte[] output = cipher.doFinal(plainTextData);
            return output;
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("�޴˼����㷨");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeyException e) {
            throw new Exception("����˽Կ�Ƿ�,����");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("���ĳ��ȷǷ�");
        } catch (BadPaddingException e) {
            throw new Exception("������������");
        }
    }

    /**
     * ˽Կ���ܹ���
     *
     * @param privateKey ˽Կ
     * @param cipherData ��������
     * @return ����
     * @throws Exception ���ܹ����е��쳣��Ϣ
     */
    public static byte[] decrypt(RSAPrivateKey privateKey, byte[] cipherData)
            throws Exception {
        if (privateKey == null) {
            throw new Exception("����˽ԿΪ��, ������");
        }
        Cipher cipher = null;
        try {
            // ʹ��Ĭ��RSA
            cipher = Cipher.getInstance("RSA");
            // cipher= Cipher.getInstance("RSA", new BouncyCastleProvider());
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] output = cipher.doFinal(cipherData);
            return output;
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("�޴˽����㷨");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeyException e) {
            throw new Exception("����˽Կ�Ƿ�,����");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("���ĳ��ȷǷ�");
        } catch (BadPaddingException e) {
            throw new Exception("������������");
        }
    }

    /**
     * ��Կ���ܹ���
     *
     * @param publicKey  ��Կ
     * @param cipherData ��������
     * @return ����
     * @throws Exception ���ܹ����е��쳣��Ϣ
     */
    public static byte[] decrypt(RSAPublicKey publicKey, byte[] cipherData)
            throws Exception {
        if (publicKey == null) {
            throw new Exception("���ܹ�ԿΪ��, ������");
        }
        Cipher cipher = null;
        try {
            // ʹ��Ĭ��RSA
            cipher = Cipher.getInstance("RSA");
            // cipher= Cipher.getInstance("RSA", new BouncyCastleProvider());
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            byte[] output = cipher.doFinal(cipherData);
            return output;
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("�޴˽����㷨");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeyException e) {
            throw new Exception("���ܹ�Կ�Ƿ�,����");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("���ĳ��ȷǷ�");
        } catch (BadPaddingException e) {
            throw new Exception("������������");
        }
    }

    /**
     * �ֽ�����תʮ�������ַ���
     *
     * @param data ��������
     * @return ʮ����������
     */
    public static String byteArrayToString(byte[] data) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            // ȡ���ֽڵĸ���λ ��Ϊ�����õ���Ӧ��ʮ�����Ʊ�ʶ�� ע���޷�������
            stringBuilder.append(HEX_CHAR[(data[i] & 0xf0) >>> 4]);
            // ȡ���ֽڵĵ���λ ��Ϊ�����õ���Ӧ��ʮ�����Ʊ�ʶ��
            stringBuilder.append(HEX_CHAR[(data[i] & 0x0f)]);
            if (i < data.length - 1) {
                stringBuilder.append(' ');
            }
        }
        return stringBuilder.toString();
    }

    /**
     * ˽Կ����
     *
     * @param publicKey ˽Կ�ַ���
     * @param plainText ��Ҫ���ܵ�˽Կ
     * @return
     */
    public static String privateKeyEncrypt(String publicKey, String plainText) {
        String encryptAfterStr = "";
        try {
            byte[] cipherData = RSAEncrypt.encrypt(RSAEncrypt.loadPublicKeyByStr(publicKey), plainText.getBytes());
            encryptAfterStr = Base64.encode(cipherData);
        } catch (Exception e) {
            e.printStackTrace();
            encryptAfterStr = "error��Encrypt fail";
        }
        return encryptAfterStr;
    }


    /**
     * ת���ַ�Ϊ�ļ�����
     *
     * @param str �ַ�
     * @return
     */
    public static String urlReplace(String str) {
//        str= str.replace("\"","%22");
//        str= str.replace("\\+","%2B");
//        str= str.replace("\\/ ","%2F");
        str = str.replace(" ", "%20");
//        str= str.replace("\\?","%3F");
//        str= str.replace("\\%","%25");
//        str= str.replace("\\#","%23");
//        str= str.replace("\\&","%26");
        return str;
    }

    /**
     * ʹ��ggwapi�Ĺ�Կ�������ַ���
     *
     * @param plainText
     * @return
     */
    public static String privateKeyEncryptForGGWPublic(String plainText) {
        String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAh/hChlpkRA+zkkEB8ZoC\n" +
                "rVcsYsbFSXYoTBKlrdCu0LiGeKs2T+U7kyZ/WZ8GP498PIucz6GYN03BWOOPe5nH\n" +
                "WfwO05XqTid6+0ni+Bfy4Ev0FyCOQsodmQpH4ytgn0UOp8BZyJTwdN4rtMcuY/FF\n" +
                "nyAsFpg9+F0DtlM/dVMj/UcWaMiZIBaa35XkXoPa+ng8Z7ORVOPiRHXfMGrlb9gZ\n" +
                "WF5XHN1SHNBKha1uF3wexDHVm4+k3HvbaZFkHrgLndaYuCPPtetnSNUOw2iaiNo7\n" +
                "Nfze1Y4ACyfvRczHEGxFEC9X7tj/Rcy2gmC9JCErxDQAHitX8DJrKtoeSJN8GvZZ\n" +
                "JQIDAQAB";

        //���Լ��Ľӿ�
     /*   publicKey="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmfu1E99t4bllV+DddFUZ\n" +
                "xOSSvGMfoGbg/EEEL/tX5oW609k4kjwFQPgqh11q/KjkRBYoTJ/sF3r4wbNMxqBi\n" +
                "AtIL//gVLNmhWeXQ1VuDV8RFhOhWtjFR7eS5GIq+5fzYQX5BKI3/I/DFGh09mNI8\n" +
                "OljaDLCuqD93q3cnMVXQFQP2IopIhpMp67o8aPFiacUGErZUJiN3pQ6vQB0Y7SLS\n" +
                "jqKddsrj0VsDY2jOocpYgDHuu2cXXsMXuMESGOHlXl0Eu9tdqYewicJcbQzywKKf\n" +
                "+hfWUwuHoiI8ekJlj9zdc8tCe+wCpHhCftHfo8+uKsqcIb0aiEKjuWG6bOmh9epH\n" +
                "0QIDAQAB";*/


        return privateKeyEncrypt(publicKey, plainText);
    }


}