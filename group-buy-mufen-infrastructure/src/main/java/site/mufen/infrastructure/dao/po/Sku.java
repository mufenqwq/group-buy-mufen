package site.mufen.infrastructure.dao.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author mufen
 * @Description Sku po 对象
 * @create 2025/1/7 22:00
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Sku {
    /**
     * 主键Id
     */
    private Long id;
    /**
     * 来源
     */
    private String source;
    /**
     * 渠道
     */
    private String channel;
    /**
     * 商品Id
     */
    private String goodsId;
    /**
     * 商品名称
     */
    private String goodsName;
    /**
     * 原始价格
     */
    private BigDecimal originalPrice;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
