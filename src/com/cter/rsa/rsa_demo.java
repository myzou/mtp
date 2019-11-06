package com.cter.rsa;

import cn.hutool.core.codec.Base64;

import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

public class rsa_demo {

    public String encryptoMode = "RSA/ECB/PKCS1Padding";
    //public String encryptoMode ="RSA/ECB/NoPadding";

    private String priKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIQDV6O1chTlFYVGVrl6hBp65gVT+SwTAumCfA9rUuybVhVEMNMJ5PL3vxVtgw8XSySHSxct05D7bx0e/SlFQrt/+8wtksXuCszLoXJ+iL2ib435H66goLFERts05jNZWk7Qw3Jfoo+ZVbJbAD/1PJ0f1i0We0nOfOv4+Ry7EkwBAgMBAAECgYAbvkwTxpUkGQTNznt5XWsX2Y4vvncWMSSiiF6kKuzxdq+/pmYQY/ruqOxKh4pMiJutIJXCWEonJQA0AYg4vSqJxx9TtxX25dWeOSyHXVwg/C932eclehnEzSUAwhemILknvMzhwBgmToheavA47ac7pvXWuM7nF7+GLzaT35Qw8QJBAO9uuujqEMUv7ZB9LARL2Odc801QqMwaqxF2bCeseCugNcZwv3CxHFEDXs63lQFBFK/oIsD3TAz4/eyhabr2NJUCQQCNJczz9bba0RgXwakojpiOFV97oYh5Z7EggnG1NUQj1VaCN4OveFkUow0PXeDNWwcayrZTs3bpvrQZOlR7bxK9AkEAuibECNwaZ8TIleLLuxdhLxg0TXMdmXpOcEg69GmCEKEBziPQo4P/uPi+2EchARWhSNZZVt+t8BQSD8y7EFHlNQJAdQWVAeKDArH+tpuCIrTuuEXyDu8i1fzpNpnPqjwKTWcvvPBHxnAR8vEn/8iGe9tvbYAJYCXXff31FUFuD5RMRQJAd/WioaZXlmOrIeAL31h6H9mrAAVPY2C1Yv3dZWjvDOB12RkO5ZoU1yIXigQGOJbIaGtHgJOmqC/rGUl2HKGuSg==";

    private String pubKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCEA1ejtXIU5RWFRla5eoQaeuYFU/ksEwLpgnwPa1Lsm1YVRDDTCeTy978VbYMPF0skh0sXLdOQ+28dHv0pRUK7f/vMLZLF7grMy6Fyfoi9om+N+R+uoKCxREbbNOYzWVpO0MNyX6KPmVWyWwA/9TydH9YtFntJznzr+PkcuxJMAQIDAQAB";

    public String sign_str = "123456";
    /***
     * 秘钥的来源方式 openssl 生成
     */

    /**
     * 获得公钥
     *
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    private PublicKey getPublicKey(String pubKeyString) throws NoSuchAlgorithmException, InvalidKeySpecException {
//        byte[] pubKeyByte = Base64.getDecoder().decode(pubKey);
        byte[] pubKeyByte = Base64.decode(pubKey);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(pubKeyByte);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey pubkey = keyFactory.generatePublic(spec);
        return pubkey;
    }

    /**
     * 获得私钥
     *
     * @return
     */
    private PrivateKey getPrivateKey(String priKeyString) throws NoSuchAlgorithmException, InvalidKeySpecException {
//        byte[] priKeyByte = Base64.getDecoder().decode(priKey);
        byte[] priKeyByte = Base64.decode(priKey);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(priKeyByte);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey priKey = keyFactory.generatePrivate(spec);
        return priKey;
    }

    /**
     * 公钥加密 （私钥加密）
     */
    public String encrypto(String text, Key key) {
        try {
            Cipher cipher = Cipher.getInstance(encryptoMode);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte tempBytes[] = cipher.doFinal(text.getBytes());
//            String secretText=Base64.getEncoder().encodeToString(tempBytes);
            String secretText = Base64.encode(tempBytes);
            return secretText;
        } catch (Exception e) {
            throw new RuntimeException("加密字符串[" + text + "]时遇到异常", e);
        }
    }

    /**
     * 私钥解密（公钥解密）
     *
     * @param secretText
     */
    public String decrypto(String secretText, Key key) {
        try {
            //生成公钥
            Cipher cipher = Cipher.getInstance(encryptoMode);
            cipher.init(Cipher.DECRYPT_MODE, key);
            // 密文解码
//            byte[] secretText_decode = Base64.getDecoder().decode(secretText.getBytes());
            byte[] secretText_decode = Base64.decode(secretText.getBytes());
            byte tempBytes[] = cipher.doFinal(secretText_decode);
            String text = new String(tempBytes);
            return text;
        } catch (Exception e) {
            throw new RuntimeException("解密字符串[" + secretText + "]时遇到异常", e);
        }
    }

    /**
     * 由于每次公钥 加密出来的结果都不一样，所有python java 每次加密出来的结果都不一样，也就没有可比性。我们只考虑能解密就行
     *
     * @param args
     */
    public static void main(String[] args) throws Exception {

        rsa_demo rsa = new rsa_demo();
        System.err.println("明文:" + rsa.sign_str);
        PublicKey pubkey = rsa.getPublicKey(rsa.pubKey);
        PrivateKey prikey = rsa.getPrivateKey(rsa.priKey);
        String secretText = rsa.encrypto(rsa.sign_str, pubkey);//公钥加密，私钥解密

        secretText = "Lm9PN4oM1dl17d2XFYRIs+hDV6RkGPVYBjgYAglaj020v5RnYzClHUN6lOVBzpeYKyH1MY5JzyOfxuYZHKCupVqhcvY4+zx+jowBH2nbVp1+/OrzuiPkNivfvmEad6ImAZp5/3Y/dVafABm5xZE78j7Ytlv0ak4seXMGTisU39o=";
        System.out.println("密文:" + secretText);
        String text = rsa.decrypto(secretText, prikey);

        System.out.println("明文:" + text);

    }


}