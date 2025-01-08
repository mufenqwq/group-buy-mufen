package site.mufen.infrastructure.dao;

import org.apache.ibatis.annotations.Mapper;
import site.mufen.infrastructure.dao.po.CrowdTagsJob;

/**
 * @author mufen
 * @Description 人群标签任务Dao
 * @create 2025/1/8 15:52
 */
@Mapper
public interface ICrowdTagsJobDao {
    CrowdTagsJob queryCrowdTagsJob(CrowdTagsJob crowdTagsJobReq);
}
