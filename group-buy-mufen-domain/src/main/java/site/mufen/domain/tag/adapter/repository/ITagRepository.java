package site.mufen.domain.tag.adapter.repository;

import site.mufen.domain.tag.model.entity.CrowdTagsJobEntity;

/**
 * @author mufen
 * @Description
 * @create 2025/1/8 16:26
 */
public interface ITagRepository {
    CrowdTagsJobEntity queryCrowdTagJobEntity(String tagId, String batchId);

    void addCrowdTagUserId(String tagId, String userId);

    void updateCrowdTagStatistics(String tagId, int size);
}
