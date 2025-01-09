package site.mufen.domain.activity.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author mufen
 * @Description SC_sku_activity 值对象
 * @create 2025/1/9 17:43
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SCSkuActivityVO {
    private String source;
    private String channel;
    private Long activityId;
    private String goodsId;
}
