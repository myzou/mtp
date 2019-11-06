package com.cter.test;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.cter.util.StringUtil;

import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @author op1768
 * @create 2019-07-26 12:53
 * @project mtp
 */
public class TestTemp {

    public static void main(String[] args) {

//        privateKeyDecrypt(null,null);
//         虎头闯杭州,多抬头看天,切勿只管种地

        publicKeyEncrypt(null,null);

    }

    /**
     * 通过公钥加密字符串
     */
    public static void publicKeyEncrypt(String PUBLIC_KEY,String str){

        PUBLIC_KEY = "MOS7pzANBgkq5ZK55Ze6DQEBAQUAA+S6tgAw5LqvAuS6lgDvv70pLO+/vemEvzrlibjvv714RFzlu6vmmo/nmYblrb7vv73vv70C54GbDFUYKFELFhTpp7Z35oyrBOiwgiE6au6TuOW5gCHmj7bvv73lk6Hls7dj6ICX6aqS5aqp54iM5qGB77+96LOLembou6TmkZDojZPno7rno4vvv73prpYHBeigmuWGpFLvv73lu69x7piY5oOD77+9Uwbojbnvv73umZooYy7vv73mv7d9AgMBAAE=";
        RSA rsa = new RSA();
        str = "虎头闯杭州,多抬头看天,切勿只管种地";
        byte[] encrypt = rsa.encrypt(StrUtil.bytes(str, CharsetUtil.CHARSET_UTF_8), KeyType.PublicKey);

        System.out.println(new String(encrypt,StandardCharsets.UTF_8));
//        byte[] decrypt = rsa.decrypt(aByte, KeyType.PrivateKey);

    }


    /**
     * 通过私钥 解密已经加密的字符串
     * @param PRIVATE_KEY  私钥字符串
     * @param encryptStr    加密的字符串
     */
    public static void  privateKeyDecrypt(String PRIVATE_KEY,String encryptStr){

         PRIVATE_KEY = "MO+/vXYCAQAwDQYJKuWSueWXug0BAQEFAATvv71gMO+/vVwCAQAC5LqWAO+/vSks77+96YS/OuWJuO+/vXhEXOW7q+aaj+eZhuWtvu+/ve+/vQLngZsMVRgoUQsWFOmntnfmjKsE6LCCITpq7pO45bmAIeaPtu+/veWToeWzt2PogJfpqpLlqqnniIzmoYHvv73os4t6Zui7pOaRkOiNk+ejuueji++/vemulgcF6KCa5YakUu+/veW7r3HumJjmg4Pvv71TBuiNue+/ve6ZmihjLu+/vea/t30CAwEAAQLkupAoSFDmjJJMIOWsqEgsIuaYu+eWse6bm+iDseaVmR/nqrvmk5flnafnrL/pmZQEd++/veKUkUTvv70V5LiW77+9EuW9sTFfdTd177+9YlYO6aStfOaegEcxBTvmmIPunJY35pWmGOiAiWTpvKXnqpV85Z6P77+9D+eIugforKUw7o+g77+9H2/pt6/ooL1o5ZeF4pSBWiZkD2zjgZEjOXpz56GdZWfnqaoCQQDvv73ngpnvv71W556o77+9b+6ShOWqv0o1VWzuh5vvv73lm4XnqYLvv71dReioqOiNjOivqQdQ6ai6HuW5qhREeVfpi7UUdWwu6L2d5oqvLem6t+eqmwJBAOaFh0FlLe6NsRnkup5e77+9XzjngpU077+96Iy2PVDonpnvv73lvI7mo64J7omIGGXnmaDnv6gmVeWsoSg7MxPpl4nuj6PohIXuh58kKj3vv73uk6cCQD49Gjrvv707GzsY7oOwBCNteBDoqot7566KJgZa77+9KX7unp9B5bmJO0junLzoiYTvv71KP+i1tQ7vv73ojLnpnLMx6bma7omt77+95L+rAO+/vUA1Ee+/vemmhy8yBu+/vempteiBoeihueezlu+/vQTmvZHltZfoppzpvKkd5bmA77+9N+S7rOedo++/veaPpgN7a+W1rgPnmoQ6SuiUhDflv7YjCe+/vWECQQDvv73njowqRRDvv714J+aBtjLnpKXokbM86J+hcei1m+iml+ins2TmmqhUUl5T6K2z57CwUO6JkO+/vea4pum8iuWPl2EGWeWluOmcpCIv77ixdOWrnQ==";

        RSA rsa = new RSA(PRIVATE_KEY, null);

        encryptStr = "YG2w5niLfoTZMJVCaDQQf/EgRzNWUsglFkviSTPVlMYbHxumMtMPEdiFcWWQEV/guWqU2Nyew/cJjXcDcj303CJDHMl2fSvIu38jXUdNr24Qn6hnbPPwsnzVAWZ/+YWPvBVDlB43s6wqkY8MvqxzBk7+ROEpmZ4QEe8177kfnoI=";

        byte[] aByte = HexUtil.decodeHex(encryptStr);
        byte[] decrypt = rsa.decrypt(aByte, KeyType.PrivateKey);
        System.out.println(new String(decrypt,StandardCharsets.UTF_8));

    }

    /**
     * 打印 rsa 公钥私钥
     * 依赖于 hutool
     */
    public static void print(){
        KeyPair pair = SecureUtil.generateKeyPair("RSA");
        PrivateKey privateKey= pair.getPrivate();
        PublicKey publicKey=pair.getPublic();


        String PRIVATE_KEY =new String(privateKey.getEncoded());
        String PUBLIC_KEY =new String(publicKey.getEncoded());

        System.out.println(Base64.encode(PRIVATE_KEY));
        System.out.println("=======================");
        System.out.println(Base64.encode(PUBLIC_KEY));

    }


}
