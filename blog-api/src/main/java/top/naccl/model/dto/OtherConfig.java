package top.naccl.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class OtherConfig implements Serializable {

    public static final String VX_CONFIG_CODE = "vx_msg";


    private String id;
    /**
     * 配置名称
     */
    private String configName;

    /**
     * 具体的配置
     */
    private String configJson;

    /**
     * 配置code不要随意更改，与前端代码是搭配的
     */
    private String configCode;
}
