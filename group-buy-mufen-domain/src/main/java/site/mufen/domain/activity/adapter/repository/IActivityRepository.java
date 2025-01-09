package site.mufen.domain.activity.adapter.repository;

import site.mufen.domain.activity.model.valobj.GroupBuyActivityDiscountVO;
import site.mufen.domain.activity.model.valobj.SCSkuActivityVO;
import site.mufen.domain.activity.model.valobj.SkuVO;

/**
 * @author mufen
 * @Description 活动仓储服务
 * @create 2025/1/7 21:51
 */
public interface IActivityRepository {
    GroupBuyActivityDiscountVO queryGroupBuyActivityDiscountVO(Long activityId);

    SkuVO querySkuByGoodsId(String goodsId);

    SCSkuActivityVO querySCSkuActivityBySCGoodsId(String source, String channel, String goodsId);

    boolean isTagCrowdRange(String tagId, String userId);
}
