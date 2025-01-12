package site.mufen.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author mufen
 * @Description
 * @create 2025/1/12 21:33
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LockMarketPayOrderRequestDTO {

    // User ID
    private String userId;
    // Team ID
    private String teamId;
    // Activity ID
    private Long activityId;
    // Goods ID
    private String goodsId;
    // Source
    private String source;
    // Channel
    private String channel;
    // Out Trade Number
    private String outTradeNo;

}
