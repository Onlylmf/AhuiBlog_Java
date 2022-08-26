package top.naccl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.jupiter.api.Test;
import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbSearcher;
import org.lionsoul.ip2region.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;
import top.naccl.entity.SiteSetting;
import top.naccl.model.vo.Weather;
import top.naccl.service.SiteSettingService;
import top.naccl.util.HttpUtil;
import top.naccl.util.UserAgentUtils;
import top.naccl.util.common.SpringContextUtils;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

@SpringBootTest
class BlogApiApplicationTests {

	@Autowired
	UserAgentUtils userAgentUtils;

	private static DbSearcher searcher;
	private static Method method;

	@Test
	void contextLoads() {
	}

	//测试查询ip地区
	@Test
	void test() {
		try {
			InputStream inputStream = new ClassPathResource("/ipdb/ip2region.db").getInputStream();
			//将 ip2region.db 转为 ByteArray
			byte[] dbBinStr = FileCopyUtils.copyToByteArray(inputStream);
			DbConfig dbConfig = new DbConfig();
			searcher = new DbSearcher(dbConfig, dbBinStr);
			//二进制方式初始化 DBSearcher，需要使用基于内存的查找算法 memorySearch
			method = searcher.getClass().getMethod("memorySearch", String.class);
		}catch (Exception e){
			e.printStackTrace();
		}

		String ip = "102.54.94.97";
		if (ip == null || !Util.isIpAddress(ip)) {
			System.err.println("null");
		}
		try {
			DataBlock dataBlock = (DataBlock) method.invoke(searcher, ip);
			String ipInfo = dataBlock.getRegion();
			if (!StringUtils.isEmpty(ipInfo)) {
				ipInfo = ipInfo.replace("|0", "");
				ipInfo = ipInfo.replace("0|", "");
			}
			System.err.println(ipInfo);
		} catch (Exception e) {
			System.err.println("getCityInfo exception");
		}

	}

	@Test
	//使用yauaa解析user-agent
	public void test1(){
		String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.77 Safari/537.36";
		Map<String, String> userAgentMap = userAgentUtils.parseOsAndBrowser(userAgent);
		System.err.println(userAgentMap);
		String os = userAgentMap.get("os");
		String browser = userAgentMap.get("browser");
		System.err.println(os);
		System.err.println(browser);

	}

	@Test
	public void test2() throws ParseException {
//		// 获取天气情况
//		String wearther = HttpUtil.sendGet("http://autodev.openspeech.cn/csp/api/v2.1/weather?openId=aiuicus&clientType=android&sign=android&city=杭州");
//		JSONObject jsonWearther = JSON.parseObject(wearther);
//		String data = jsonWearther.getString("data");
//		JSONObject jsonObject = JSON.parseObject(data);
//		String list = jsonObject.getJSONArray("list").get(0).toString();
//
//		System.out.println(JSON.parseObject(list, Weather.class));

		String wearther = HttpUtil.sendGet("https://restapi.amap.com/v3/weather/weatherInfo?city=330106&key=f5de45abd3dbd85e65ef5884e0e9d42e");
		JSONObject jsonWearther = JSON.parseObject(wearther);
		String list = jsonWearther.getJSONArray("lives").get(0).toString();
		System.out.println(JSON.parseObject(list,Weather.class));

		String a = "2022-08-25 16:07:05";
		System.out.println(a.substring(0, a.indexOf(" ")));
		System.out.println(top.naccl.util.StringUtils.substring(a, 0, 11));

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date temperature = simpleDateFormat.parse(a);
		String format = DateFormatUtils.format(temperature, "yyyy-MM-dd");
		System.out.println(format);
	}


	@Test
	public void aallssl(){
		try{
			InputStream inputStream = new ClassPathResource("/ipdb/ip2region.db").getInputStream();
			//将 ip2region.db 转为 ByteArray
			byte[] dbBinStr = FileCopyUtils.copyToByteArray(inputStream);
			DbSearcher dbSearcher = new DbSearcher(new DbConfig(),dbBinStr);

			String ip = "125.120.155.227";
			DataBlock dataBlock = dbSearcher.memorySearch(ip);
			String ipInfo = dataBlock.getRegion();
			if (!StringUtils.isEmpty(ipInfo)) {
				ipInfo = ipInfo.replace("|0", "");
				ipInfo = ipInfo.replace("0|", "");
			}
			System.err.println(ipInfo);
		}catch (Exception e){
			e.printStackTrace();
		}

	}


}
