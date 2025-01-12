package site.mufen.infrastructure.dao.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Represents a group buy order list.
 * This class contains details about the group buy order such as user ID, team ID, order ID, activity ID,
 * start and end times, goods ID, source, channel, prices, status, trade number, and timestamps.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupBuyOrderList {
    /**
     * 自增ID
     */
    private Long id;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 团队ID
     */
    private String teamId;
    /**
     * 订单ID
     */
    private String orderId;
    /**
     * 活动ID
     */
    private Long activityId;
    /**
     * 开始时间
     */
    private LocalDateTime startTime;
    /**
     * 结束时间
     */
    private LocalDateTime endTime;
    /**
     * 商品ID
     */
    private String goodsId;
    /**
     * 渠道
     */
    private String source;
    /**
     * 来源
     */
    private String channel;
    /**
     * 原价
     */
    private BigDecimal originalPrice;
    /**
     * 折扣价
     */
    private BigDecimal deductionPrice;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 交易号
     */
    private String outTradeNo;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
