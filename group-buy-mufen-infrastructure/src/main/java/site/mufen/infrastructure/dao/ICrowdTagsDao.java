package site.mufen.infrastructure.dao;

import org.apache.ibatis.annotations.Mapper;
import site.mufen.infrastructure.dao.po.CrowdTags;

/**
 * @author mufen
 * @Description 人群标签Doo
 * @create 2025/1/8 15:51
 */
@Mapper
public interface ICrowdTagsDao {
    void updateCrowdTagsStatistics(CrowdTags crowdTagsReq);
}
