package com.xuaxi.tools.ddns;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.alidns.model.v20150109.DescribeDomainRecordsRequest;
import com.aliyuncs.alidns.model.v20150109.DescribeDomainRecordsResponse;
import com.aliyuncs.alidns.model.v20150109.DescribeDomainRecordsResponse.Record;
import com.aliyuncs.alidns.model.v20150109.UpdateDomainRecordRequest;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;

public class Main {

	private static final String accessKey = "LTAIVZMvc5FgFHyE";

	private static final String accessKeySecret = "dQqXwDCNcnsIz3mH0TSRKuogzEIVzG";

	private static final String dmianName = "xuaxi.com";

	private static final String subDomainName = "xzjdev";

	private static IAcsClient client = null;

	static {
		client = new DefaultAcsClient(DefaultProfile.getProfile("cn-hangzhou", accessKey, accessKeySecret));
	}

	private static String subDomainId;

	private static final int checkTime = 60000;

	private static String subDomainValue;
	
	private static final String getIpUrl="http://www.zhixintec.com/yip.asp";

	public static void main(String[] args) {
		Record subDomain = getSubDomainId();
		if (subDomain == null || "".equals(subDomain.getRecordId())) {
			System.out.println("异常：加载域名 " + subDomainName + "." + dmianName + " 的ID失败。系统退出");
		} else {
			subDomainId = subDomain.getRecordId();
			subDomainValue = subDomain.getValue();
			System.out.println("加载域名 " + subDomainName + "." + dmianName + " 的ID " + subDomainId + " A记录地址：" + subDomainValue);
			task();
		}
	}

	public static void task() {
		TimerTask timeTask = new TimerTask() {
			@Override
			public void run() {
				String ip=sendGet();
				if(ip!=null&&!"".equals(ip)){
					System.out.println("您的当前公网IP为 "+ip+"域名"+ subDomainName + "." + dmianName +" 的A记录为 "+subDomainValue);
					if(!subDomainValue.equals(ip)){
						System.out.println("正在修改域名"+ subDomainName + "." + dmianName +" 的A记录");
						updateARecode(ip);
						System.out.println("域名"+ subDomainName + "." + dmianName +" 的A记录修改成功");
					}else{
						System.out.println("您的IP未发生变化不需要修改A记录");
					}
				}else{
					System.out.println("获取您的IP地址失败");
				}
			}
		};
		Timer timer = new Timer();
		timer.schedule(timeTask, new Date(), checkTime);
	}
	
	public static void updateARecode(String value){
		UpdateDomainRecordRequest request=new UpdateDomainRecordRequest();
		request.setRecordId(subDomainId);
		request.setRR(subDomainName);
		request.setValue(value);
		request.setType("A");
		try {
			client.getAcsResponse(request);
			subDomainValue=value;
		} catch (ServerException e) {
			e.printStackTrace();
		} catch (ClientException e) {
			e.printStackTrace();
		}
	}

	public static Record getSubDomainId() {
		try {
			DescribeDomainRecordsRequest request = new DescribeDomainRecordsRequest();
			request.setDomainName(dmianName);
			DescribeDomainRecordsResponse response = client.getAcsResponse(request);
			List<Record> list = response.getDomainRecords();
			for (Record domain : list) {
				if (subDomainName.equals(domain.getRR())) {
					return domain;
				}
			}
		} catch (ServerException e) {
			e.printStackTrace();
		} catch (ClientException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String sendGet() {
		String result = "";
		BufferedReader in = null;
		InputStream cins = null;
		InputStreamReader irs = null;
		try {
			URL realUrl = new URL(getIpUrl);
			URLConnection connection = realUrl.openConnection();
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			connection.connect();
			cins = connection.getInputStream();
			irs = new InputStreamReader(cins);
			in = new BufferedReader(irs);
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (irs != null) {
					irs.close();
				}
				if (cins != null) {
					cins.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}
}
