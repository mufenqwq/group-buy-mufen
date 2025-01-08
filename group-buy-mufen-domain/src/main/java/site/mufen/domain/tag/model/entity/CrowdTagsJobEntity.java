package site.mufen.domain.tag.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author mufen
 * @Description
 * @create 2025/1/8 16:29
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CrowdTagsJobEntity {
    /**
     * 标签类型（参与量、消费金额）
     */
    private Integer tagType;
    /**
     * 标签规则（限定类型 N次）
     */
    private String tagRule;
    /**
     * 统计数据，开始时间
     */
    private LocalDateTime statStartTime;
    /**
     * 统计数据，结束时间
     */
    private LocalDateTime statEndTime;
}
