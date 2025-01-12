package site.mufen.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author mufen
 * @Description
 * @create 2025/1/12 21:34
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LockMarketPayOrderResponseDTO {

    // Order ID
    private String orderId;
    // Deduction price
    private BigDecimal deductionPrice;
    // Trade order status
    private Integer tradeOrderStatus;
}
