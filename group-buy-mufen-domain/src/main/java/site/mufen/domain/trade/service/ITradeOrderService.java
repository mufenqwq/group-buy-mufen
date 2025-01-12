package site.mufen.domain.trade.service;

import site.mufen.domain.trade.model.entity.MarketPayOrderEntity;
import site.mufen.domain.trade.model.entity.PayActivityEntity;
import site.mufen.domain.trade.model.entity.PayDiscountEntity;
import site.mufen.domain.trade.model.entity.UserEntity;
import site.mufen.domain.trade.model.valobj.GroupBuyProgressVO;

/**
 * @author mufen
 * @Description 交易订单服务
 * @create 2025/1/12 20:01
 */
public interface ITradeOrderService {

    MarketPayOrderEntity queryNoPayMarketOrderByOutTradeNo(String userId, String outTradeNo);

    GroupBuyProgressVO queryGroupBuyProgress(String teamId);

    MarketPayOrderEntity lockMarketPayOrder(UserEntity userEntity, PayActivityEntity payActivityEntity, PayDiscountEntity payDiscountEntity);
}
