package site.mufen.infrastructure.dao.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author mufen
 * @Description 人群标签
 * @create 2025/1/8 15:34
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CrowdTags {

    /**
     * 主键Id
     */
    private Long id;
    /**
     * tagId
     */
    private String tagId;
    /**
     * 标签名称
     */
    private String tagName;
    /**
     * 标签描述
     */
    private String tagDesc;
    /**
     *人群标签统计量
     */
    private Integer statistics;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 修改时间
     */
    private LocalDateTime updateTime;
}
