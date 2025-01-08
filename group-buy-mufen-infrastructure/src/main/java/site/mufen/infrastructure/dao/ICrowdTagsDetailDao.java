package site.mufen.infrastructure.dao;

import org.apache.ibatis.annotations.Mapper;
import site.mufen.infrastructure.dao.po.CrowdTagsDetail;

/**
 * @author mufen
 * @Description 人群标签详情Dao
 * @create 2025/1/8 15:51
 */
@Mapper
public interface ICrowdTagsDetailDao {
    void addCrowdTagsUserId(CrowdTagsDetail crowdTagsDetailReq);

}
