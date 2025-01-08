package site.mufen.domain.tag.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import site.mufen.domain.tag.adapter.repository.ITagRepository;
import site.mufen.domain.tag.model.entity.CrowdTagsJobEntity;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author mufen
 * @Description
 * @create 2025/1/8 16:25
 */
@Service
@Slf4j
public class TagService implements ITagService{

    @Resource
    private ITagRepository tagRepository;

    @Override
    public void execTagBatchJob(String tagId, String batchId) {
        // 1. 查询任务
        CrowdTagsJobEntity crowdTagsJobEntity = tagRepository.queryCrowdTagJobEntity(tagId, batchId);

        // 2. 采集用户数据
        // todo
        List<String> userIdList = new ArrayList<String>(){{
            add("mufen");
            add("WangPan");
        }};

        for (String userId : userIdList) {
            tagRepository.addCrowdTagUserId(tagId, userId);
        }

        // 3. 更新人群标签统计量
        tagRepository.updateCrowdTagStatistics(tagId, userIdList.size());
    }
}
