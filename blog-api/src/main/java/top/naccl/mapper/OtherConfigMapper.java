package top.naccl.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import top.naccl.model.dto.OtherConfig;

@Mapper
@Repository
public interface OtherConfigMapper {

    /**
     * 通过code获取配置信息
     * @param configCode
     * @return
     */
    @Select("select * from other_config where config_code = #{configCode}")
    OtherConfig selectWeChatConfig(@Param("configCode")String configCode);

    /**
     *  通过config修改配置
     */
    @Select("update other_config set config_json = #{configJson} where config_code = #{configCode}")
    int updateWeChatConfig(String configCode,String configJson);
}
