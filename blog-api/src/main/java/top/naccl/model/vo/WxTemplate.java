package top.naccl.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class WxTemplate {
    private String touser;
    private String template_id;
    private String url;
    private Map<String, DataEntity> data;
}
