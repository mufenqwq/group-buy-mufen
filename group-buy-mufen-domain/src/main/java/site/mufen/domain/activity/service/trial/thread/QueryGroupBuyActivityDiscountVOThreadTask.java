package site.mufen.domain.activity.service.trial.thread;

import site.mufen.domain.activity.adapter.repository.IActivityRepository;
import site.mufen.domain.activity.model.valobj.GroupBuyActivityDiscountVO;

import javax.annotation.Resource;
import java.util.concurrent.Callable;

/**
 * @author mufen
 * @Description
 * @create 2025/1/7 22:48
 */
public class QueryGroupBuyActivityDiscountVOThreadTask implements Callable<GroupBuyActivityDiscountVO> {

    private final IActivityRepository activityRepository;
    private final String source;
    private final String channel;

    public QueryGroupBuyActivityDiscountVOThreadTask(IActivityRepository activityRepository, String source, String channel) {
        this.activityRepository = activityRepository;
        this.source = source;
        this.channel = channel;
    }


    @Override
    public GroupBuyActivityDiscountVO call() throws Exception {
        return activityRepository.queryGroupBuyActivityDiscountVO(source, channel);
    }
}
