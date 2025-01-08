package site.mufen.infrastructure.adapter.repository;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBitSet;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;
import site.mufen.domain.tag.adapter.repository.ITagRepository;
import site.mufen.domain.tag.model.entity.CrowdTagsJobEntity;
import site.mufen.infrastructure.dao.ICrowdTagsDao;
import site.mufen.infrastructure.dao.ICrowdTagsDetailDao;
import site.mufen.infrastructure.dao.ICrowdTagsJobDao;
import site.mufen.infrastructure.dao.po.CrowdTags;
import site.mufen.infrastructure.dao.po.CrowdTagsDetail;
import site.mufen.infrastructure.dao.po.CrowdTagsJob;
import site.mufen.infrastructure.redis.IRedisService;

import javax.annotation.Resource;

/**
 * @author mufen
 * @Description
 * @create 2025/1/8 16:28
 */
@Slf4j
@Repository
public class TagRepository implements ITagRepository {

    @Resource
    private ICrowdTagsDao crowdTagsDao;
    @Resource
    private ICrowdTagsDetailDao crowdTagsDetailDao;
    @Resource
    private ICrowdTagsJobDao crowdTagsJobDao;
    @Resource
    private IRedisService redisService;

    @Override
    public CrowdTagsJobEntity queryCrowdTagJobEntity(String tagId, String batchId) {
        log.info("根据tagId 和 batchId 查询人群标签任务, tagId:{}, batchId:{} ", tagId, batchId);
        CrowdTagsJob crowdTagsJobReq = new CrowdTagsJob();
        crowdTagsJobReq.setTagId(tagId);
        crowdTagsJobReq.setBatchId(batchId);

        CrowdTagsJob crowdTagsJobRes = crowdTagsJobDao.queryCrowdTagsJob(crowdTagsJobReq);
        return CrowdTagsJobEntity.builder()
            .tagRule(crowdTagsJobRes.getTagRule())
            .tagType(crowdTagsJobRes.getTagType())
            .statStartTime(crowdTagsJobRes.getStatStartTime())
            .statEndTime(crowdTagsJobRes.getStatEndTime()).build();

    }

    @Override
    public void addCrowdTagUserId(String tagId, String userId) {
        CrowdTagsDetail crowdTagsDetail = new CrowdTagsDetail();
        crowdTagsDetail.setTagId(tagId);
        crowdTagsDetail.setUserId(userId);

        try {
            crowdTagsDetailDao.addCrowdTagsUserId(crowdTagsDetail);

            RBitSet bitSet = redisService.getBitSet(tagId);
            bitSet.set(redisService.getIndexFromUserId(userId));
        } catch (DuplicateKeyException ignore) {

        }
    }

    @Override
    public void updateCrowdTagStatistics(String tagId, int size) {
        CrowdTags crowdTagsReq = new CrowdTags();
        crowdTagsReq.setTagId(tagId);
        crowdTagsReq.setStatistics(size);
        crowdTagsDao.updateCrowdTagsStatistics(crowdTagsReq);
    }
}
