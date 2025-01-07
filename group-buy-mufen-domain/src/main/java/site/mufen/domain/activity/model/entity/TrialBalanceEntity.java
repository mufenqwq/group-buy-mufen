package site.mufen.domain.activity.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author mufen
 * @Description 试算平衡Entity
 * @create 2025/1/7 18:50
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrialBalanceEntity {

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
    /**
     * 优惠价格
     */
    private BigDecimal deductionPrice;
    /**
     * 拼团目标人数
     */
    private Integer targetCount;
    /**
     * 拼团开始时间
     */
    private LocalDateTime startTime;
    /**
     * 拼团结束时间
     */
    private LocalDateTime endTime;
    /**
     * 是否可见
     */
    private Boolean isVisible;
    /**
     * 是否可以参与
     */
    private Boolean isEnable;
}
