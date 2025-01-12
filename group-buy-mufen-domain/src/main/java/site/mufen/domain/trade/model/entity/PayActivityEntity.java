package site.mufen.domain.trade.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author mufen
 * @Description
 * @create 2025/1/12 20:02
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PayActivityEntity {

    /**
     * 拼单组队ID
     */
    private String teamId;
    /**
     * 活动ID
     */
    private Long activityId;
    /**
     * 活动名称
     */
    private String activityName;
    /**
     * 拼团开始时间
     */
    private LocalDateTime startTime;
    /**
     * 拼团结束时间
     */
    private LocalDateTime endTime;
    /**
     * 目标数量
     */
    private Integer targetCount;
}
