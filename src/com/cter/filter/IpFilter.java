package com.cter.filter;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.cter.util.LoadPropertiestUtil;

/**
 * * ������
 * ���ܣ��Է�����IP�������Ʒ���
 * @author op1768
 */
public class IpFilter implements Filter{

	//�������������ʵ�ip
	private List<String> allowList = new ArrayList<String>();
	
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		try {
			System.out.println("������IpFilter��ʼ��ʼ�������ܣ�IP��������");
			initConfig();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		
		//��ȡ���ʵ�IP��ַ
		String remoteAddr = request.getRemoteAddr();
		//System.out.println("===============" + remoteAddr);
		//���allowListΪ��,����Ϊû������,��Ϊ�������Ƿ�����
		if(allowList.size() == 0 || allowList == null) {
			filterChain.doFilter(request, response);
		} else {
			Boolean flag = false;  //���ʱ�־��Ĭ��Ϊfalse�����Ʒ���
			//����������
			for(String regex : allowList){
				if(remoteAddr.matches(regex)){
					//ipû�����ƣ���������
					filterChain.doFilter(request, response);
					flag = true;  //��Ϊtrue����ʾ�����Ʒ���
					break;
				}
			}
			if(!flag) {
				request.setAttribute("remoteAddr", remoteAddr);
				//ip�����ƣ�����ָ��ҳ��
				request.getRequestDispatcher("error.jsp").forward(request, response);
			}
		}
		
	}
	
	@Override
	public void destroy() {
		System.out.println("������IpFilter������");
	}

	/**
	 * �������ļ����г�ʼ����У��
	 * @author ŷ��
	 * @serialData 20180728
	 * @throws IOException
	 */
	public void initConfig() throws IOException {
		Map<String, String>  map=LoadPropertiestUtil.loadProperties("config/ipConfig.properties");
		//��ȡ�������÷�ʽ��ֵ
		String allowIP = map.get("allowIP");//����IP��ַ������,���֮���ö��Ż�ֺø���
		String allowIPRange =map.get("allowIPRange");//IP��ַ���䷽ʽ������,��������ö��Ż�ֺø���
		String allowIPWildcard = map.get("allowIPWildcard");//ͨ���,����ö��Ż�ֺø���
		
		//У��,У��ʧ�ܺ��׳��쳣
		if(!validate(allowIP, allowIPRange, allowIPWildcard)) {
			throw new RuntimeException("�����ļ��д����飡");
		}
		
		/*
		 * ��ÿһ�����÷������õ�allowList��
		 */
		//����һ�����÷����ŵ�allowList��
		if(null != allowIP && !"".equals(allowIP.trim())) {
			String[] allowIPs = allowIP.split(",|;");
			for(String ip : allowIPs) {
				allowList.add(ip);
			}
		}
		
		//���ڶ������÷����ŵ�allowList��
		if(null != allowIPRange &&  !"".equals(allowIPRange.trim())) {
			//�Ƚ���ÿһ�εķָ�
			String[] allowIPRanges = allowIPRange.split(",|;");
			
			if(allowIPRanges.length > 0) {
				//��ÿһ�ν��б���
				for(String allowRanges : allowIPRanges) {
					if(allowRanges != null &&  !"".equals(allowRanges.trim())) {
						//�Ըöε�ip���н���
						String[] ips = allowRanges.split("-");
						if(ips.length > 0 && ips.length < 3) {
							String from = ips[0];//�õ��öε���ʼip
							String to = ips[1];  //�õ��öεĽ���ip
							
							//��ȡ��ip�ε�ַ��ǰ���Σ���Ϊ��ʼ�ͽ�����ip��ǰ����һ�� 
							String share = from.substring(0, from.lastIndexOf(".")+1);
							
							//��ȡ��ip�ε���ʼip�����һ��
							int start = Integer.parseInt(from.substring(from.lastIndexOf(".")+1,  from.length()));
							//��ȡ��ip�εĽ���ip�����һ��
							int end = Integer.parseInt(to.substring(to.lastIndexOf(".")+1,  to.length()));
							for(int i=start; i<=end; i++) {
								String ip = share + String.valueOf(i);
								allowList.add(ip);
							}
						} else {
							throw new RuntimeException("�����ļ��д����飡");
						}
					}
					
				}
			}
			
		}
		
		//�����������÷����ŵ�allowList��
		if(allowIPWildcard != null &&  !"".equals(allowIPWildcard)) {
			//��ȡÿ����ͨ�����ip��ַ
			String[] allowIPWildcards = allowIPWildcard.split(",|;");
			
			if(allowIPWildcards.length > 0) {
				for(String ip : allowIPWildcards) {
					if(ip.indexOf("*") != -1) {
						//��*�����滻
						ip = ip.replaceAll("\\*", "(25[0-5]|2[0-4]\\\\d|[0-1]\\\\d{2}|[1-9]?\\\\d)");
						
						allowList.add(ip);
					} else {
						throw new RuntimeException("�����ļ��д����飡");
					}
					
				}
				
			}
		}
		
		//��ӡ���allowList
		for(String str : allowList) {
			System.out.println(str);
		}
		
	}
	
	/**
	 * �������ļ�����У��
	 * @author ouyang
	 * @serialData 20180728
	 * @param allowIP
	 * @param allowIPRange
	 * @param allowIPWildcard
	 * @return
	 */
	public Boolean validate(String allowIP, String allowIPRange, String allowIPWildcard) {
		Boolean result = false;
		//IP��ַÿһ�ε�����
		String regx = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";
		//����ip������
		String ipRegx = regx + "\\." + regx + "\\."+ regx + "\\." + regx;
		
		//�Ե�һ�ַ�ʽ����У��
		Pattern pattern = Pattern.compile("("+ipRegx+")|("+ipRegx+"(,|;))*");
		if(this.isNullorMatches(allowIP, pattern)||allowIP.indexOf("10.180")>-1){
			result = true;  //ƥ��ɹ�
		} else {
			result = false;
		}
		
		//�Եڶ��ַ�ʽ����У��
		pattern = Pattern.compile("("+ipRegx+")\\-("+ipRegx+")|" + 
						"(("+ipRegx+")\\-("+ipRegx+")(,|;))*");
		if(this.isNullorMatches(allowIPRange, pattern)){
			result = true;  //ƥ��ɹ�
		} else {
			result = false;
		}
		
		//�Ե����ַ�ʽ����У��
		pattern = Pattern.compile("("+regx+"\\."+ regx+"\\."+regx+"\\."+ "\\*)|" + 
						"("+regx+"\\."+regx+"\\."+regx+"\\."+ "\\*(,|;))*");
		if(this.isNullorMatches(allowIPWildcard, pattern)){
			result = true;  //ƥ��ɹ�
		} else {
			result = false;
		}
		
		return result;
	}
	
	/**
	 * ��������ƥ��
	 * @author ŷ��
	 * @serialData 20180728
	 * @param allow
	 * @return
	 */
	public Boolean isNullorMatches(String allow, Pattern pattern) {
		//���Ϊ�գ�˵���û�û��Ӹ����������
		if(allow == null || "".equals(allow.trim())) {
			return true;
		} else {
			//�������û��,��;�ĸ�����
			if(!allow.endsWith(";") && !allow.endsWith(",")) {
				allow += ";";
			}
			//���ƥ�䣬�򷵻�true
			if(pattern.matcher(allow).matches()) {
				return true;
			}
		}
		
		return false;
	}
}

