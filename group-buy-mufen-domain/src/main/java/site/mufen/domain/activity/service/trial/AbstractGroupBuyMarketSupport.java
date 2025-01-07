package site.mufen.domain.activity.service.trial;

import site.mufen.domain.activity.adapter.repository.IActivityRepository;
import site.mufen.types.design.framework.tree.AbstractMultiThreadStrategyRouter;
import site.mufen.types.design.framework.tree.AbstractStrategyRouter;

import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * @author mufen
 * @Description
 * @create 2025/1/7 18:56
 */
public abstract class AbstractGroupBuyMarketSupport<MarketProductEntity, DynamicContext, TrialBalanceEntity> extends AbstractMultiThreadStrategyRouter<MarketProductEntity, DynamicContext, TrialBalanceEntity> {

    protected long timeout = 500;

    @Resource
    protected IActivityRepository activityRepository;

    @Override
    protected void multiThread(MarketProductEntity requestParam, DynamicContext dynamicCtx) throws ExecutionException, InterruptedException, TimeoutException {

    }
}
