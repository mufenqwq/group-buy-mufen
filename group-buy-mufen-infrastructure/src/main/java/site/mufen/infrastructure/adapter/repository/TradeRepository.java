package site.mufen.infrastructure.adapter.repository;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import site.mufen.domain.trade.adapter.repository.ITradeRepository;
import site.mufen.domain.trade.model.aggregate.GroupBuyOrderAggregate;
import site.mufen.domain.trade.model.entity.MarketPayOrderEntity;
import site.mufen.domain.trade.model.entity.PayActivityEntity;
import site.mufen.domain.trade.model.entity.PayDiscountEntity;
import site.mufen.domain.trade.model.entity.UserEntity;
import site.mufen.domain.trade.model.valobj.GroupBuyProgressVO;
import site.mufen.domain.trade.model.valobj.TradeOrderStatusEnumVO;
import site.mufen.infrastructure.dao.IGroupBuyOrderDao;
import site.mufen.infrastructure.dao.IGroupBuyOrderListDao;
import site.mufen.infrastructure.dao.po.GroupBuyOrder;
import site.mufen.infrastructure.dao.po.GroupBuyOrderList;
import site.mufen.types.enums.ResponseCode;
import site.mufen.types.exception.AppException;

import javax.annotation.Resource;

/**
 * @author mufen
 * @Description 交易仓储
 * @create 2025/1/12 20:37
 */
@Slf4j
@Repository
public class TradeRepository implements ITradeRepository {

    @Resource
    private IGroupBuyOrderDao groupBuyOrderDao;

    @Resource
    private IGroupBuyOrderListDao groupBuyOrderListDao;

    @Override
    public MarketPayOrderEntity queryNoPayMarketOrderByOutTradeNo(String userId, String outTradeNo) {
        GroupBuyOrderList groupBuyOrderListReq = new GroupBuyOrderList();
        groupBuyOrderListReq.setUserId(userId);
        groupBuyOrderListReq.setOutTradeNo(outTradeNo);
        GroupBuyOrderList groupBuyOrderListRes = groupBuyOrderListDao.queryGroupBuyOrderListByOutTradeNo(groupBuyOrderListReq);

        if (groupBuyOrderListRes == null) return null;

        return MarketPayOrderEntity.builder()
            .orderId(groupBuyOrderListRes.getOrderId())
            .deductionPrice(groupBuyOrderListRes.getDeductionPrice())
            .tradeOrderStatusEnumVO(TradeOrderStatusEnumVO.valueOf(groupBuyOrderListRes.getStatus()))
            .build();
    }

    @Override
    public GroupBuyProgressVO queryGroupBuyProgress(String teamId) {
        GroupBuyOrder groupBuyOrder = groupBuyOrderDao.queryGroupBuyProgress(teamId);
        if (null == groupBuyOrder) return null;

        return GroupBuyProgressVO.builder()
            .targetCount(groupBuyOrder.getTargetCount())
            .completeCount(groupBuyOrder.getCompleteCount())
            .lockCount(groupBuyOrder.getLockCount())
            .build();
    }

    /**
     * 锁定支付订单
     *
     * @param groupBuyOrderAggregate 拼团订单聚合
     * @return MarketPayOrderEntity
     */
    @Transactional(timeout = 500)
    @Override
    public MarketPayOrderEntity lockMarketPayOrder(GroupBuyOrderAggregate groupBuyOrderAggregate) {
        UserEntity userEntity = groupBuyOrderAggregate.getUserEntity();
        PayActivityEntity payActivityEntity = groupBuyOrderAggregate.getPayActivityEntity();
        PayDiscountEntity payDiscountEntity = groupBuyOrderAggregate.getPayDiscountEntity();
        String teamId = payActivityEntity.getTeamId();
        if (StringUtils.isBlank(teamId)) {
            teamId = RandomStringUtils.randomNumeric(8);

            GroupBuyOrder groupBuyOrder = GroupBuyOrder.builder()
                .teamId(teamId)
                .activityId(payActivityEntity.getActivityId())
                .source(payDiscountEntity.getSource())
                .channel(payDiscountEntity.getChannel())
                .originalPrice(payDiscountEntity.getOriginalPrice())
                .deductionPrice(payDiscountEntity.getDeductionPrice())
                .payPrice(payDiscountEntity.getDeductionPrice().subtract(payDiscountEntity.getDeductionPrice()))
                .targetCount(payActivityEntity.getTargetCount())
                .completeCount(0)
                .lockCount(1)
                .build();

            groupBuyOrderDao.insert(groupBuyOrder);
        } else {
            int updateAddLockCount = groupBuyOrderDao.updateAddLockCount(teamId);
            if (1 != updateAddLockCount) {
                throw new AppException(ResponseCode.E0005.getCode(), ResponseCode.E0003.getInfo());
            }

        }
        String orderId = RandomStringUtils.randomNumeric(12);

        GroupBuyOrderList groupBuyOrderListReq = GroupBuyOrderList.builder()
            .userId(userEntity.getUserId())
            .teamId(teamId)
            .orderId(orderId)
            .activityId(payActivityEntity.getActivityId())
            .startTime(payActivityEntity.getStartTime())
            .endTime(payActivityEntity.getEndTime())
            .goodsId(payDiscountEntity.getGoodsId())
            .source(payDiscountEntity.getSource())
            .channel(payDiscountEntity.getChannel())
            .originalPrice(payDiscountEntity.getOriginalPrice())
            .deductionPrice(payDiscountEntity.getDeductionPrice())
            .status(TradeOrderStatusEnumVO.CREATE.getCode())
            .outTradeNo(payDiscountEntity.getOutTradeNo())
            .build();

        try {
            groupBuyOrderListDao.insert(groupBuyOrderListReq);
        } catch (DuplicateKeyException e) {
            throw new AppException(ResponseCode.INDEX_EXCEPTION.getCode(), ResponseCode.INDEX_EXCEPTION.getInfo());
        }
        return MarketPayOrderEntity.builder()
            .orderId(orderId)
            .deductionPrice(payDiscountEntity.getDeductionPrice())
            .tradeOrderStatusEnumVO(TradeOrderStatusEnumVO.CREATE)
            .build();

    }
}
