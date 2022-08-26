package top.naccl.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import top.naccl.entity.SiteSetting;
import top.naccl.model.dto.OtherConfig;
import top.naccl.model.vo.DataEntity;
import top.naccl.model.vo.Weather;
import top.naccl.model.vo.WxTemplate;
import top.naccl.service.OtherConfigService;
import top.naccl.service.SiteSettingService;
import top.naccl.service.VxMsg;
import top.naccl.util.CalcBirthdayUtils;
import top.naccl.util.HttpUtil;
import top.naccl.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 用于推送微信测试号消息
 */

@Service
public class VxMsgImpl implements VxMsg {

    @Autowired
    private OtherConfigService otherConfigService;

    @Autowired
    private SiteSettingService siteSettingService;


    //保存所有查询到信息
    static HashMap<String,String> maps;

    @Override
    public void sendVxMsg() throws ParseException {

        maps = new HashMap<String,String>();

        //判断是否填写了多个UserID
        List<SiteSetting> type5 = siteSettingService.getList().get("type5");
        for (SiteSetting s: type5) {
            maps.put(s.getNameEn(),s.getValue());
        }

        for (SiteSetting config: type5) {
            if(config.getNameEn().equals("user_id")){
                List<String> strings = Arrays.asList(config.getValue().split(","));
                for (String userId: strings) {
                    vxMsg(maps,userId);
                }
            }
        }

    }

    public void vxMsg(HashMap<String,String> maps,String userId){

        String ACCESS_TOKEN_URL = maps.get("access_token_url") + maps.get("app_id") + "&secret=" + maps.get("app_secret");
        String weatherUrl = maps.get("guide_uri") + "city=" + maps.get("city") + "&key=" + maps.get("guide_key");
        // 获取天气情况
        String wearther = HttpUtil.sendGet(weatherUrl);
        JSONObject jsonWearther = JSON.parseObject(wearther);
        String list = jsonWearther.getJSONArray("lives").get(0).toString();
        Weather weather = JSON.parseObject(list, Weather.class);

        //获取随机文字
        String a = maps.get("hitokoto");
        String random = HttpUtil.sendGet(a);
        JSONObject hitokoto = JSON.parseObject(random);

        // 获取access_token
        String s = HttpUtil.sendGet(ACCESS_TOKEN_URL);
        JSONObject jsonObject = JSON.parseObject(s);
        String accessToken = jsonObject.getString("access_token");

        // 构建消息模板
        Map<String, DataEntity> templateData = new HashMap<>();
        templateData.put("title",DataEntity.builder().value("臭弟弟，早安 又是美好的一天").color("#FFB3B3").build());
        templateData.put("titleWeather",DataEntity.builder().value("来报告今日天气情况喽").color("#FFDBA4").build());
        templateData.put("address",DataEntity.builder().value(weather.getCity()).color("#FFE9AE").build());
        templateData.put("weather",DataEntity.builder().value(weather.getWeather()).color("#7C83FD").build());
        templateData.put("humidity",DataEntity.builder().value(weather.getHumidity()).color("#96BAFF").build());
        templateData.put("temperature",DataEntity.builder().value(weather.getTemperature()).color("#7DEDFF").build());
        templateData.put("reporttime",DataEntity.builder().value(weather.getReporttime()).color("#88FFF7").build());
        templateData.put("birthday",DataEntity.builder().value(CalcBirthdayUtils.getBirthday()+"").color("#7ac23c").build());
        templateData.put("hitokoto",DataEntity.builder().value(hitokoto.getString("hitokoto")).color("#98BAE7").build());
        templateData.put("from",DataEntity.builder().value(hitokoto.getString("from")).color("#7267CB").build());
        templateData.put("end",DataEntity.builder().value("愿度过美好的一天~").color("#C1EFFF").build());

        WxTemplate builder = WxTemplate.builder()
                .template_id(maps.get("msg_template_id"))
                .data(templateData)
                .url(maps.get("to_url"))
                .touser(userId)
                .build();

        executePush(builder,accessToken);
    }



    public void executePush(WxTemplate wxTemplate, String accessToken) {
        Object o = JSON.toJSON(wxTemplate);
        String s = HttpUtil.sendPost(String.format(maps.get("send_template_url"),accessToken, o.toString().replace("[", "{").replace("]", "}")),o.toString());
        System.out.println(s);
    }
}
