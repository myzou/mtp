package com.cter.util;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.cpcnet.frameset.ssi.naming.SimpleRadiusAuthenticationHandler;

import java.util.Map;

/**
 * ������¼��op�ʺ���
 *
 * @author op1768
 */
public class RadiusOpLoginUtil {

    private static Map<String, String> map = LoadPropertiestUtil.loadProperties("config/other.properties");
    private static final String radiusHost = map.get("radiusHost");
    private static final String sharedSecret = map.get("sharedSecret");
    private static final int authPort = Integer.valueOf(map.get("authPort"));
    private static final String authProtocol = map.get("authProtocol");


    // for UAT radius
/*NOC ���� 
 * 	Radius server��202.76.23.51
	Port�� 1645
	sharesecret�� NocSystem
	authProtocol :
	*/
    // /����PC������ֻ������UAT RADIUS, test ACC: op494 / abc123
    //���������Ļ���Ҫ�Ѵ��뷢������˾������
//		 private static final String radiusHost = "202.76.80.77";
//		 private static final String sharedSecret = "aaron";
//		 private static final int authPort = 1812;
//		 private static final String authProtocol = "CHAP";

    private static final int socketTimeout = 5000;
    private static final int retry = 0;

    /**
     * op�ʺ������¼����
     * �����ʺţ�op494  ���룺abc123
     *
     * @param userName op�ʺ���
     * @param pwd      op����
     * @return 2    ��¼�ɹ�
     * ������û���õ���
     * -6 	�������;//CODE_ERROR
     * -5 	�����׳��쳣 //CODE_RADIUS_EXCEPTION
     * -4	�������Դ���//CODE_UNKNOWN_ATTRIBUTE
     * -3	����IO�쳣CODE_IO_EXCEPTION
     * -2 	��������ʱ//CODE_REQUEST_TIME_OUT
     * -1	������������//CODE_UNKNOWN_HOST
     * 1	�����������//CODE_ACCESS_REQUEST
     * 2	�����������//CODE_ACCESS_ACCEPT
     * 3	��������ܾ�//CODE_ACCESS_REJECT
     * 11	�������󷵻�//CODE_ACCESS_CHALLENGE
     */
	/*	public static  int checkLogin(String userName,String pwd){
			int a= SimpleRadiusAuthenticationHandler.authenticate(
					radiusHost, sharedSecret, authProtocol,  userName,
					 pwd, authPort, socketTimeout, retry);
			System.out.println("��֤��¼���أ�"+a);
			System.out.println(userName+"/"+pwd);
			return a;
		}*/
    public static int checkLogin(String userName, String pwd) {
        String checkOPUrl = "http://10.181.160.4:8081/X-admin-2.3/login_checkLogin.action?username=opusername&password=oppassword";
        checkOPUrl = checkOPUrl.replace("opusername", userName).replace("oppassword", pwd);
        System.out.println("checkOPUrl:" + checkOPUrl);
        String checkResult = HttpUtil.get(checkOPUrl);
        System.out.println("��֤��¼���أ�" + checkResult);
        System.out.println(userName + "/" + pwd);
        if (!StrUtil.isEmpty(checkResult) && checkResult.equals("Y")) {
            return 2;
        }
        return 3;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(checkLogin("op494", "abc123"));
    }

}
