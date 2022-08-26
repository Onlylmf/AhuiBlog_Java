package top.naccl.service;

import org.apache.ibatis.annotations.Param;
import top.naccl.model.dto.OtherConfig;

public interface OtherConfigService {

    /**
     * 通过code查询这条配置
     * @param configCode
     * @return
     */
    OtherConfig selectWeChatConfig(@Param("configCode")String configCode);

    /**
     *  通过code修改configJson
     * @param configCode
     * @param configJson
     * @return
     */
    int updateWeChatConfig(String configCode,String configJson);
}
