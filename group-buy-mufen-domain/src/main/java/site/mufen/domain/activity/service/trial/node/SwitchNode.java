package site.mufen.domain.activity.service.trial.node;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import site.mufen.domain.activity.adapter.repository.IActivityRepository;
import site.mufen.domain.activity.model.entity.MarketProductEntity;
import site.mufen.domain.activity.model.entity.TrialBalanceEntity;
import site.mufen.domain.activity.service.trial.AbstractGroupBuyMarketSupport;
import site.mufen.domain.activity.service.trial.factory.DefaultActivityStrategyFactory;
import site.mufen.types.design.framework.tree.StrategyHandler;
import site.mufen.types.enums.ResponseCode;
import site.mufen.types.exception.AppException;

import javax.annotation.Resource;

/**
 * @author mufen
 * @Description
 * @create 2025/1/7 19:03
 */
@Slf4j
@Service
public class SwitchNode extends AbstractGroupBuyMarketSupport<MarketProductEntity, DefaultActivityStrategyFactory.DynamicContext, TrialBalanceEntity> {

    @Resource
    private MarketNode marketNode;

    @Resource
    private IActivityRepository repository;

    @Override
    public TrialBalanceEntity doApply(MarketProductEntity requestParam, DefaultActivityStrategyFactory.DynamicContext dynamicCtx) throws Exception {
        log.info("拼团商品查询试算服务-SwitchNode userId:{} requestParameter:{}", requestParam.getUserId(), JSON.toJSONString(requestParam));

        String userId = requestParam.getUserId();
        if (repository.downgradeSwitch()) {
            log.info("活动降级拦截 userId:{}", requestParam.getUserId());
            throw new AppException(ResponseCode.E0003.getCode(), ResponseCode.E0003.getInfo());
        }

        if (repository.cutRange(userId)) {
            log.info("拼团活动切量拦截 userId:{}", requestParam.getUserId());
            throw new AppException(ResponseCode.E0004.getCode(), ResponseCode.E0004.getInfo());
        }

        return router(requestParam, dynamicCtx);
    }

    @Override
    public StrategyHandler<MarketProductEntity, DefaultActivityStrategyFactory.DynamicContext, TrialBalanceEntity> get(MarketProductEntity requestParam, DefaultActivityStrategyFactory.DynamicContext dynamicCtx) {
        return marketNode;
    }
}
