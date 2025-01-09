package site.mufen.domain.activity.service.trial.node;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import site.mufen.domain.activity.adapter.repository.IActivityRepository;
import site.mufen.domain.activity.model.entity.MarketProductEntity;
import site.mufen.domain.activity.model.entity.TrialBalanceEntity;
import site.mufen.domain.activity.model.valobj.GroupBuyActivityDiscountVO;
import site.mufen.domain.activity.service.trial.AbstractGroupBuyMarketSupport;
import site.mufen.domain.activity.service.trial.factory.DefaultActivityStrategyFactory;
import site.mufen.types.design.framework.tree.StrategyHandler;

import javax.annotation.Resource;

/**
 * @author mufen
 * @Description
 * @create 2025/1/9 21:37
 */
@Slf4j
@Service
public class TagNode extends AbstractGroupBuyMarketSupport<MarketProductEntity, DefaultActivityStrategyFactory.DynamicContext, TrialBalanceEntity> {

    @Resource
    private EndNode endNode;
    @Resource
    private IActivityRepository repository;

    @Override
    protected TrialBalanceEntity doApply(MarketProductEntity requestParam, DefaultActivityStrategyFactory.DynamicContext dynamicCtx) throws Exception {
        GroupBuyActivityDiscountVO groupBuyActivityDiscountVO = dynamicCtx.getGroupBuyActivityDiscountVO();

        String tagId = groupBuyActivityDiscountVO.getTagId();

        boolean visible = groupBuyActivityDiscountVO.isVisible();
        boolean enable = groupBuyActivityDiscountVO.isEnable();
        if (StringUtils.isBlank(tagId)) {
            dynamicCtx.setVisible(true);
            dynamicCtx.setEnable(true);
            return router(requestParam, dynamicCtx);
        }
        boolean isWithin = repository.isTagCrowdRange(tagId, requestParam.getUserId());
        dynamicCtx.setVisible(isWithin || visible);
        dynamicCtx.setEnable(isWithin || enable);

        return router(requestParam, dynamicCtx);
    }

    @Override
    public StrategyHandler<MarketProductEntity, DefaultActivityStrategyFactory.DynamicContext, TrialBalanceEntity> get(MarketProductEntity requestParam, DefaultActivityStrategyFactory.DynamicContext dynamicCtx) {
        return endNode;
    }
}
