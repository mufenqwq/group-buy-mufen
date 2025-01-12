package site.mufen.domain.trade.adapter.repository;

import site.mufen.domain.trade.model.aggregate.GroupBuyOrderAggregate;
import site.mufen.domain.trade.model.entity.MarketPayOrderEntity;
import site.mufen.domain.trade.model.valobj.GroupBuyProgressVO;

/**
 * @author mufen
 * @Description 订单仓储
 * @create 2025/1/12 20:33
 */
public interface ITradeRepository {
    MarketPayOrderEntity queryNoPayMarketOrderByOutTradeNo(String userId, String outTradeNo);

    GroupBuyProgressVO queryGroupBuyProgress(String teamId);

    MarketPayOrderEntity lockMarketPayOrder(GroupBuyOrderAggregate groupBuyOrderAggregate);
}
