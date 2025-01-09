package site.mufen.domain.activity.service.trial.thread;

import site.mufen.domain.activity.adapter.repository.IActivityRepository;
import site.mufen.domain.activity.model.valobj.GroupBuyActivityDiscountVO;
import site.mufen.domain.activity.model.valobj.SCSkuActivityVO;

import javax.annotation.Resource;
import java.util.concurrent.Callable;

/**
 * @author mufen
 * @Description
 * @create 2025/1/7 22:48
 */
public class QueryGroupBuyActivityDiscountVOThreadTask implements Callable<GroupBuyActivityDiscountVO> {

    private final IActivityRepository activityRepository;
    /**
     * 来源
     */
    private final String source;
    /**
     * 渠道
     */
    private final String channel;
    /**
     * 商品Id
     */
    private final String goodsId;

    public QueryGroupBuyActivityDiscountVOThreadTask(IActivityRepository activityRepository, String source, String channel, String goodsId) {
        this.activityRepository = activityRepository;
        this.source = source;
        this.channel = channel;
        this.goodsId = goodsId;
    }


    @Override
    public GroupBuyActivityDiscountVO call() throws Exception {
        // 查询渠道商品活动配置关联配置
        SCSkuActivityVO scSkuActivityVO =  activityRepository.querySCSkuActivityBySCGoodsId(source, channel, goodsId);
        if (null == scSkuActivityVO) return null;
        // 查询活动配置
        return activityRepository.queryGroupBuyActivityDiscountVO(scSkuActivityVO.getActivityId());
    }
}
