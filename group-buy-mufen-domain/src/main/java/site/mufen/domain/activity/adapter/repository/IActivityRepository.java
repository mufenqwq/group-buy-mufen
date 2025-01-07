package site.mufen.domain.activity.adapter.repository;

import site.mufen.domain.activity.model.valobj.GroupBuyActivityDiscountVO;
import site.mufen.domain.activity.model.valobj.SkuVO;

/**
 * @author mufen
 * @Description 活动仓储服务
 * @create 2025/1/7 21:51
 */
public interface IActivityRepository {
    GroupBuyActivityDiscountVO queryGroupBuyActivityDiscountVO(String source, String channel);

    SkuVO querySkuByGoodsId(String goodsId);
}
