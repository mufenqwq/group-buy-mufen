package site.mufen.domain.trade.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.mufen.domain.trade.model.valobj.TradeOrderStatusEnumVO;

import java.math.BigDecimal;

/**
 * @author mufen
 * @Description
 * @create 2025/1/12 20:11
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MarketPayOrderEntity {

    private String orderId;
    /**
     * 折扣金额
     */
    private BigDecimal deductionPrice;
    /**
     * 交易订单状态
     */
    private TradeOrderStatusEnumVO tradeOrderStatusEnumVO;
}
