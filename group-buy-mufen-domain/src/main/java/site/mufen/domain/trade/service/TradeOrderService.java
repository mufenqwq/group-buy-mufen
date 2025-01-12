package site.mufen.domain.trade.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import site.mufen.domain.trade.adapter.repository.ITradeRepository;
import site.mufen.domain.trade.model.aggregate.GroupBuyOrderAggregate;
import site.mufen.domain.trade.model.entity.MarketPayOrderEntity;
import site.mufen.domain.trade.model.entity.PayActivityEntity;
import site.mufen.domain.trade.model.entity.PayDiscountEntity;
import site.mufen.domain.trade.model.entity.UserEntity;
import site.mufen.domain.trade.model.valobj.GroupBuyProgressVO;

import javax.annotation.Resource;

/**
 * @author mufen
 * @Description 订单服务
 * @create 2025/1/12 20:32
 */
@Slf4j
@Service
public class TradeOrderService implements ITradeOrderService {

    @Resource
    private ITradeRepository repository;

    /**
     * 根据用户id和外部订单号查询未支付的订单
     *
     * @param userId     用户id
     * @param outTradeNo 外部订单号
     * @return 订单
     */
    @Override
    public MarketPayOrderEntity queryNoPayMarketOrderByOutTradeNo(String userId, String outTradeNo) {
        return repository.queryNoPayMarketOrderByOutTradeNo(userId, outTradeNo);
    }

    /**
     * 查询拼团进度
     * @param teamId 拼团id
     * @return 拼团进度
     */
    @Override
    public GroupBuyProgressVO queryGroupBuyProgress(String teamId) {
        return repository.queryGroupBuyProgress(teamId);
    }

    /**
     * 锁定订单
     * @param userEntity 用户
     * @param payActivityEntity 活动
     * @param payDiscountEntity 优惠
     * @return 订单
     */
    @Override
    public MarketPayOrderEntity lockMarketPayOrder(UserEntity userEntity, PayActivityEntity payActivityEntity, PayDiscountEntity payDiscountEntity) {

        GroupBuyOrderAggregate groupBuyOrderAggregate = GroupBuyOrderAggregate.builder()
            .userEntity(userEntity)
            .payActivityEntity(payActivityEntity)
            .payDiscountEntity(payDiscountEntity).build();

        return repository.lockMarketPayOrder(groupBuyOrderAggregate);
    }
}
