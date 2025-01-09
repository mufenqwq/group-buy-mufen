package site.mufen.infrastructure.dao.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author mufen
 * @Description sku商品与活动关联表
 * @create 2025/1/9 17:20
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SCSkuActivity {
    /**
     *自增ID
     */
    private Long id;
    /**
     *渠道
     */
    private String source;
    /**
     *来源
     */
    private String channel;
    /**
     * 活动Id
     */
    private Long activityId;
    /**
     *商品ID
     */
    private String goodsId;
    /**
     *创建时间
     */
    private LocalDateTime createTime;
    /**
     *修改时间
     */
    private LocalDateTime updateTime;
}
