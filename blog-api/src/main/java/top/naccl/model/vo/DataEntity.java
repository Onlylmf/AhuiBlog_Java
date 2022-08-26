package top.naccl.model.vo;

import lombok.*;

import java.io.Serializable;

/**
 * <p>ClassName:DataEntity</p>
 * <p>Description: 公众号推送数据  </p>
 *
 * @author XiangBo
 * @date 2022-05-18 14:35
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DataEntity implements Serializable {

    private String value;

    private String color;

}