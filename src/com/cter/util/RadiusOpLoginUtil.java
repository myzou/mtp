package com.cter.util;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.cpcnet.frameset.ssi.naming.SimpleRadiusAuthenticationHandler;

import java.util.Map;

/**
 * 用来登录的op帐号类
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
/*NOC 配置 
 * 	Radius server：202.76.23.51
	Port： 1645
	sharesecret： NocSystem
	authProtocol :
	*/
    // /本地PC机器上只可以用UAT RADIUS, test ACC: op494 / abc123
    //访问其他的话需要把代码发布到公司的内网
//		 private static final String radiusHost = "202.76.80.77";
//		 private static final String sharedSecret = "aaron";
//		 private static final int authPort = 1812;
//		 private static final String authProtocol = "CHAP";

    private static final int socketTimeout = 5000;
    private static final int retry = 0;

    /**
     * op帐号密码登录方法
     * 测试帐号：op494  密码：abc123
     *
     * @param userName op帐号名
     * @param pwd      op密码
     * @return 2    登录成功
     * 下面是没有用到的
     * -6 	代码错误;//CODE_ERROR
     * -5 	代码抛出异常 //CODE_RADIUS_EXCEPTION
     * -4	代码属性错误//CODE_UNKNOWN_ATTRIBUTE
     * -3	代码IO异常CODE_IO_EXCEPTION
     * -2 	代码请求超时//CODE_REQUEST_TIME_OUT
     * -1	代码主机错误//CODE_UNKNOWN_HOST
     * 1	代码请求访问//CODE_ACCESS_REQUEST
     * 2	代码请求接受//CODE_ACCESS_ACCEPT
     * 3	代码请求拒绝//CODE_ACCESS_REJECT
     * 11	代码请求返回//CODE_ACCESS_CHALLENGE
     */
	/*	public static  int checkLogin(String userName,String pwd){
			int a= SimpleRadiusAuthenticationHandler.authenticate(
					radiusHost, sharedSecret, authProtocol,  userName,
					 pwd, authPort, socketTimeout, retry);
			System.out.println("验证登录返回："+a);
			System.out.println(userName+"/"+pwd);
			return a;
		}*/
    public static int checkLogin(String userName, String pwd) {
        String checkOPUrl = "http://10.181.160.4:8081/X-admin-2.3/login_checkLogin.action?username=opusername&password=oppassword";
        checkOPUrl = checkOPUrl.replace("opusername", userName).replace("oppassword", pwd);
        System.out.println("checkOPUrl:" + checkOPUrl);
        String checkResult = HttpUtil.get(checkOPUrl);
        System.out.println("验证登录返回：" + checkResult);
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
