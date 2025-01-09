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
import site.mufen.types.enums.ResponseCode;
import site.mufen.types.exception.AppException;

import java.math.BigDecimal;

/**
 * @author mufen
 * @Description 异常兜底节点
 * @create 2025/1/7 19:06
 */
@Slf4j
@Service
public class ErrorNode extends AbstractGroupBuyMarketSupport<MarketProductEntity, DefaultActivityStrategyFactory.DynamicContext, TrialBalanceEntity> {
    @Override
    public TrialBalanceEntity doApply(MarketProductEntity requestParam, DefaultActivityStrategyFactory.DynamicContext dynamicCtx) throws Exception {
        log.info("拼团商品查询试算服务-ErrorNode userId: {}, requestParam: {}", requestParam.getUserId(), JSON.toJSONString(requestParam));

        // 无营销配置
        if (null == dynamicCtx.getGroupBuyActivityDiscountVO() || null == dynamicCtx.getSkuVO()) {
            log.info("商品无拼团营销配置 {}", requestParam.getGoodsId());
            throw new AppException(ResponseCode.E0002.getCode(), ResponseCode.E0002.getInfo());
        }
        return TrialBalanceEntity.builder().build();
    }

    @Override
    public StrategyHandler<MarketProductEntity, DefaultActivityStrategyFactory.DynamicContext, TrialBalanceEntity> get(MarketProductEntity requestParam, DefaultActivityStrategyFactory.DynamicContext dynamicCtx) {
        return defaultStrategyHandler;
    }
}
