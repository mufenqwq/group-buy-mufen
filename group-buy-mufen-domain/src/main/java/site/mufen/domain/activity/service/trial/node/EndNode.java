package site.mufen.domain.activity.service.trial.node;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import site.mufen.domain.activity.model.entity.MarketProductEntity;
import site.mufen.domain.activity.model.entity.TrialBalanceEntity;
import site.mufen.domain.activity.model.valobj.GroupBuyActivityDiscountVO;
import site.mufen.domain.activity.model.valobj.SkuVO;
import site.mufen.domain.activity.service.trial.AbstractGroupBuyMarketSupport;
import site.mufen.domain.activity.service.trial.factory.DefaultActivityStrategyFactory;
import site.mufen.types.design.framework.tree.StrategyHandler;

import java.math.BigDecimal;

/**
 * @author mufen
 * @Description
 * @create 2025/1/7 19:06
 */
@Slf4j
@Service
public class EndNode extends AbstractGroupBuyMarketSupport<MarketProductEntity, DefaultActivityStrategyFactory.DynamicContext, TrialBalanceEntity> {
    @Override
    public TrialBalanceEntity doApply(MarketProductEntity requestParam, DefaultActivityStrategyFactory.DynamicContext dynamicCtx) throws Exception {
        log.info("拼团商品查询试算服务-EndNode userId: {}, requestParam: {}", requestParam.getUserId(), JSON.toJSONString(requestParam));
        // 拼团活动配置
        GroupBuyActivityDiscountVO groupBuyActivityDiscountVO = dynamicCtx.getGroupBuyActivityDiscountVO();
        // 商品信息
        SkuVO skuVO = dynamicCtx.getSkuVO();

        BigDecimal deductionPrice = dynamicCtx.getDeductionPrice();

        return TrialBalanceEntity.builder()
            .goodsId(skuVO.getGoodsId())
            .goodsName(skuVO.getGoodsName())
            .originalPrice(skuVO.getOriginalPrice())
            .deductionPrice(deductionPrice)
            .targetCount(groupBuyActivityDiscountVO.getTarget())
            .startTime(groupBuyActivityDiscountVO.getStartTime())
            .endTime(groupBuyActivityDiscountVO.getEndTime())
            .isEnable(false)
            .isVisible(false).build();
    }

    @Override
    public StrategyHandler<MarketProductEntity, DefaultActivityStrategyFactory.DynamicContext, TrialBalanceEntity> get(MarketProductEntity requestParam, DefaultActivityStrategyFactory.DynamicContext dynamicCtx) {
        return defaultStrategyHandler;
    }
}
