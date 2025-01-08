package site.mufen.domain.activity.service.trial.node;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import site.mufen.domain.activity.model.entity.MarketProductEntity;
import site.mufen.domain.activity.model.entity.TrialBalanceEntity;
import site.mufen.domain.activity.model.valobj.GroupBuyActivityDiscountVO;
import site.mufen.domain.activity.model.valobj.SkuVO;
import site.mufen.domain.activity.service.discount.IDiscountCalculateService;
import site.mufen.domain.activity.service.trial.AbstractGroupBuyMarketSupport;
import site.mufen.domain.activity.service.trial.factory.DefaultActivityStrategyFactory;
import site.mufen.domain.activity.service.trial.thread.QueryGroupBuyActivityDiscountVOThreadTask;
import site.mufen.domain.activity.service.trial.thread.QuerySkuVOFromDBThreadTask;
import site.mufen.types.design.framework.tree.StrategyHandler;
import site.mufen.types.enums.ResponseCode;
import site.mufen.types.exception.AppException;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author mufen
 * @Description 查询优惠节点
 * @create 2025/1/7 19:05
 */
@Slf4j
@Service
public class MarketNode extends AbstractGroupBuyMarketSupport<MarketProductEntity, DefaultActivityStrategyFactory.DynamicContext, TrialBalanceEntity> {

    @Resource
    private ThreadPoolExecutor threadPoolExecutor;
    @Resource
    private EndNode endNode;

    private final Map<String, IDiscountCalculateService> discountCalculateServiceMap;

    public MarketNode(Map<String, IDiscountCalculateService> discountCalculateServiceMap) {
        this.discountCalculateServiceMap = discountCalculateServiceMap;
    }

    @Override
    protected void multiThread(MarketProductEntity requestParam, DefaultActivityStrategyFactory.DynamicContext dynamicCtx) throws ExecutionException, InterruptedException, TimeoutException {
        QueryGroupBuyActivityDiscountVOThreadTask queryGroupBuyActivityDiscountVOThreadTask = new QueryGroupBuyActivityDiscountVOThreadTask(activityRepository, requestParam.getSource(), requestParam.getChannel());
        FutureTask<GroupBuyActivityDiscountVO> futureTask = new FutureTask<>(queryGroupBuyActivityDiscountVOThreadTask);
        threadPoolExecutor.execute(futureTask);

        QuerySkuVOFromDBThreadTask querySkuVOFromDBThreadTask = new QuerySkuVOFromDBThreadTask(requestParam.getGoodsId(), activityRepository);
        FutureTask<SkuVO> skuVOFutureTask = new FutureTask<>(querySkuVOFromDBThreadTask);
        threadPoolExecutor.execute(skuVOFutureTask);

        // 写入上下文
        dynamicCtx.setGroupBuyActivityDiscountVO(futureTask.get(timeout, TimeUnit.MINUTES));
        dynamicCtx.setSkuVO(skuVOFutureTask.get(timeout, TimeUnit.MINUTES));

        log.info("拼团商品查询试算服务-MarketNode userId:{} 异步线程加载数据「GroupBuyActivityDiscountVO、SkuVO」完成", requestParam.getUserId());
    }

    @Override
    public TrialBalanceEntity doApply(MarketProductEntity requestParam, DefaultActivityStrategyFactory.DynamicContext dynamicCtx) throws Exception {
        log.info("拼团商品查询试算服务-Market userId: {}, requestParam: {}", requestParam.getUserId(), JSON.toJSONString(requestParam));

        // todo 拼团优惠试算
        GroupBuyActivityDiscountVO groupBuyActivityDiscountVO = dynamicCtx.getGroupBuyActivityDiscountVO();
        SkuVO skuVO = dynamicCtx.getSkuVO();
        BigDecimal originalPrice = skuVO.getOriginalPrice();
        GroupBuyActivityDiscountVO.GroupBuyDiscount groupBuyDiscount = groupBuyActivityDiscountVO.getGroupBuyDiscount();
        String marketPlan = groupBuyDiscount.getMarketPlan();
        IDiscountCalculateService discountCalculateService = discountCalculateServiceMap.get(marketPlan);
        if (discountCalculateService == null) {
            log.info("不存在{}类型的折扣计算服务, 支持类型为:{}", marketPlan, JSON.toJSONString(discountCalculateServiceMap.keySet()));
            throw new AppException(ResponseCode.E0001.getCode(), ResponseCode.E0001.getInfo());
        }
        BigDecimal discountPrice = discountCalculateService.discountCalculate(requestParam.getUserId(), originalPrice, groupBuyDiscount);
        dynamicCtx.setDeductionPrice(discountPrice);
        return router(requestParam, dynamicCtx);
    }

    @Override
    public StrategyHandler<MarketProductEntity, DefaultActivityStrategyFactory.DynamicContext, TrialBalanceEntity> get(MarketProductEntity requestParam, DefaultActivityStrategyFactory.DynamicContext dynamicCtx) {
        return endNode;
    }
}
