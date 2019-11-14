package com.cter.util.totp;

import cn.hutool.core.util.StrUtil;
import com.cter.util.StringUtil;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.lang.reflect.UndeclaredThrowableException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * @author op1768
 * @create 2019-10-31 9:57
 * @project light-attenuation
 */
public class Totp {


    public static void main(String[] args) throws Exception {
        while(true) {
            String secretBase32="gmp7bb3kpghainowhr7jthvkkuy4buds";
            long refreshTime=30l;
            long createTime=0l;
            String crypto="HmacSHA1";
            String codeDigits="6";
            System.out.println(GenerateVerificationCode(secretBase32,refreshTime,createTime,crypto,codeDigits));
            Thread.sleep(5000);
        }
    }

    private static final int[] DIGITS_POWER
            // 0 1  2   3    4     5      6       7        8
            = {1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000};


    /**
     * ����ַ���Ϊ�վ�����Ϊָ���ַ����������Ϊ�վ����
     * @param ifStr   �ж��ַ���
     * @param setStr  ���õ��ַ���
     * @return
     */
    public static String ifNotNullSet(String ifStr,String setStr){
        if(StrUtil.isBlank(ifStr)){
            return setStr;
        }
        return ifStr;
    }

    /**
     * ������֤��
     * secretBase32:Ϊ otpauth��secret 32 λ�ܳף�ÿ���˲�һ����
     * refreshTime: ��֤��ˢ��ʱ�䣨�룩
     * time: ָ��ʱ��� �����룩
     * codeDigits: ��֤��λ��������6λ������֤�� д 6
     * otpauth://totp/Engineering & Services:Luke Zou?secret=****&issuer=Engineering & Services
     * @return
     */
    public static String GenerateVerificationCode(String secretBase32,Long refreshTime,Long createTime, String crypto,String codeDigits ) {
        secretBase32=ifNotNullSet(secretBase32,"gmp7bb3kpghainowhr7jthvkkuy4buds");
        refreshTime =(refreshTime==0)?30:refreshTime;
        long currentTime=((createTime==0)?System.currentTimeMillis():createTime)/1000L;
        crypto=(!StrUtil.isBlank(crypto))?crypto:"HmacSHA1";
        String secretHex = "";
        try {
            secretHex = HexEncoding.encode(Base32String.decode(secretBase32));
        } catch (Base32String.DecodingException e) {
            System.out.println ("����" + secretBase32 + "����");
            return "error:����" + secretBase32 + "�����������" ;
        }
        //��ʼʱ�� Ĭ��Ϊ0
        String steps = "0";
        try {
            long t = currentTime / refreshTime;
            steps = Long.toHexString(t).toUpperCase();
            while (steps.length() < 16) {
                steps = "0" + steps;
            }
            return generateTOTP(secretHex, steps, codeDigits,crypto );
        } catch (final Exception e) {
            e.printStackTrace();
            return "error:��Կ("+secretBase32+")���ɶ�̬��������������" ;
        }

    }

    /**
     * ������֤��
     * secretBase32:Ϊ otpauth��secret 32 λ�ܳף�ÿ���˲�һ����
     * refreshTime: ��֤��ˢ��ʱ�䣨�룩
     * time: ָ��ʱ��� �����룩
     * codeDigits: ��֤��λ��������6λ������֤�� д 6
     * otpauth://totp/Engineering & Services:Luke Zou?secret=****&issuer=Engineering & Services
     * @return
     */
    public static String GenerateVerificationCode() {
        String secretBase32="gmp7bb3kpghainowhr7jthvkkuy4buds";
        Long refreshTime=30L;
        Long createTime=0L;
        String crypto="HmacSHA1";
        String codeDigits="6";

        refreshTime =(refreshTime==0)?30:refreshTime;
        long currentTime=((createTime==0)?System.currentTimeMillis():createTime)/1000L;
        crypto=(!StrUtil.isBlank(crypto))?crypto:"HmacSHA1";
        String secretHex = "";
        try {
            secretHex = HexEncoding.encode(Base32String.decode(secretBase32));
        } catch (Base32String.DecodingException e) {
            System.out.println ("����" + secretBase32 + "����");
            return "error:����" + secretBase32 + "�����������" ;
        }
        //��ʼʱ�� Ĭ��Ϊ0
        String steps = "0";
        try {
            long t = currentTime / refreshTime;
            steps = Long.toHexString(t).toUpperCase();
            while (steps.length() < 16) {
                steps = "0" + steps;
            }
            return generateTOTP(secretHex, steps, codeDigits,crypto );
        } catch (final Exception e) {
            e.printStackTrace();
            return "error:��Կ("+secretBase32+")���ɶ�̬��������������" ;
        }

    }


    private static byte[] hexStr2Bytes(String hex) {
        // Adding one byte to get the right conversion
        // Values starting with "0" can be converted
        byte[] bArray = new BigInteger("10" + hex, 16).toByteArray();

        // Copy all the REAL bytes, not the "first"
        byte[] ret = new byte[bArray.length - 1];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = bArray[i + 1];
        }
        return ret;
    }


    private static byte[] hmac_sha(String crypto, byte[] keyBytes,
                                   byte[] text){
        try {
            Mac hmac;
            hmac = Mac.getInstance(crypto);
            SecretKeySpec macKey =
                    new SecretKeySpec(keyBytes, "RAW");
            hmac.init(macKey);
            return hmac.doFinal(text);
        } catch (GeneralSecurityException gse) {
            throw new UndeclaredThrowableException(gse);
        }
    }


    public static String generateTOTP(String key, String time, String returnDigits, String crypto) {
        int codeDigits = Integer.decode(returnDigits).intValue();
        String result = null;

        // Using the counter
        // First 8 bytes are for the movingFactor
        // Compliant with base RFC 4226 (HOTP)
        while (time.length() < 16) {
            time = "0" + time;
        }

        // Get the HEX in a Byte[]
        byte[] msg = hexStr2Bytes(time);
        byte[] k = hexStr2Bytes(key);

        byte[] hash = hmac_sha(crypto, k, msg);

        // put selected bytes into result int
        int offset = hash[hash.length - 1] & 0xf;

        int binary =
                ((hash[offset] & 0x7f) << 24) |
                        ((hash[offset + 1] & 0xff) << 16) |
                        ((hash[offset + 2] & 0xff) << 8) |
                        (hash[offset + 3] & 0xff);

        int otp = binary % DIGITS_POWER[codeDigits];

        result = Integer.toString(otp);
        while (result.length() < codeDigits) {
            result = "0" + result;
        }
        return result;
    }
}
