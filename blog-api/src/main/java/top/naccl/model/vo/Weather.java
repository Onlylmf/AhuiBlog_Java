package top.naccl.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Weather {

//    private String city;
//    private String date;
//    private String weather;
//    private String humidity;
//    private String low;
//    private String wind;
//    private String high;
//    private String airQuality;
    private String city;
    private String weather; //天气现象
    private String winddirection;//风向
    private String humidity; //空气湿度
    private String reporttime; //查询时间
    private String temperature; //实时气温
}
