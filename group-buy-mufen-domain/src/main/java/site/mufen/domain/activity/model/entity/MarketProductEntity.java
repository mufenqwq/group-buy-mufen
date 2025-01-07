package site.mufen.domain.activity.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author mufen
 * @Description 市场产品Entity
 * @create 2025/1/7 18:48
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MarketProductEntity {
    /**
     * 用户Id
     */
    private String userId;
    /**
     * 商品Id
     */
    private String goodsId;
    /**
     * 来源
     */
    private String source;
    /**
     * 渠道
     */
    private String channel;
}
