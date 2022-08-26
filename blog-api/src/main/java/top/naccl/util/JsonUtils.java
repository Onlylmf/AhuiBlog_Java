package top.naccl.util;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

/**
 * JsonUtils
 */
public class JsonUtils {


    //private static final ObjectMapper MAPPER = new I18NObjectMapper();
    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        MAPPER.setSerializationInclusion(Include.NON_NULL);
        MAPPER.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * 将对象转换成json字符串。
     * <p>Title: pojoToJson</p>
     * <p>Description: </p>
     *
     * @param data
     * @return
     */
    public static String objectToJsonWhitI18N(Object data) {
        try {
            String string = MAPPER.writeValueAsString(data);
            return string;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String objectToJson(Object data) {
        try {
            if (data == null) return null;
            if (data instanceof String) {
                return (String) data;
            }
            return MAPPER.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将json结果集转化为对象
     *
     * @param jsonData json数据
     * @param beanType 对象中的object类型
     * @return
     */
    public static <T> T jsonToPojo(String jsonData, Class<T> beanType) {
        try {
            if (StringUtils.isBlank(jsonData)) return null;
            return MAPPER.readValue(jsonData, beanType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将json结果集转化为对象
     *
     * @param jsonData json数据
     * @param beanType 对象中的object类型
     * @return
     */
    public static <T> T jsonToPojo(Object jsonData, Class<T> beanType) {
        if (jsonData == null) return null;
        String json = objectToJson(jsonData);
        if (StringUtils.isBlank(json)) return null;
        try {
            T t = MAPPER.readValue(json, beanType);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将json数据转换成pojo对象list
     */
    public static <T> List<T> jsonToList(String jsonData, Class<T> beanType) {
        if (StringUtils.isBlank(jsonData)) return null;
        try {
            JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, beanType);
            return MAPPER.readValue(jsonData, javaType);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> List<T> jsonToList(Object jsonData, Class<T> beanType) {
        return jsonToList(objectToJson(jsonData), beanType);
    }

}
