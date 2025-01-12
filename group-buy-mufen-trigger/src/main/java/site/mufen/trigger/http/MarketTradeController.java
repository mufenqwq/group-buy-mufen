package site.mufen.trigger.http;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.mufen.api.IMarketTradeService;
import site.mufen.api.dto.LockMarketPayOrderRequestDTO;
import site.mufen.api.dto.LockMarketPayOrderResponseDTO;
import site.mufen.api.response.Response;
import site.mufen.domain.activity.model.entity.MarketProductEntity;
import site.mufen.domain.activity.model.entity.TrialBalanceEntity;
import site.mufen.domain.activity.model.valobj.GroupBuyActivityDiscountVO;
import site.mufen.domain.activity.service.IIndexGroupBuyMarketService;
import site.mufen.domain.trade.model.entity.MarketPayOrderEntity;
import site.mufen.domain.trade.model.entity.PayActivityEntity;
import site.mufen.domain.trade.model.entity.PayDiscountEntity;
import site.mufen.domain.trade.model.entity.UserEntity;
import site.mufen.domain.trade.model.valobj.GroupBuyProgressVO;
import site.mufen.domain.trade.service.ITradeOrderService;
import site.mufen.types.enums.ResponseCode;
import site.mufen.types.exception.AppException;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author mufen
 * @Description
 * @create 2025/1/12 21:41
 */
@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/gbm/trade/")
public class MarketTradeController implements IMarketTradeService {

    @Resource
    private IIndexGroupBuyMarketService indexGroupBuyMarketService;

    @Resource
    private ITradeOrderService tradeOrderService;


    @Override
    public Response<LockMarketPayOrderResponseDTO> lockMarketPayOrder(LockMarketPayOrderRequestDTO requestDTO) {
        try {
            // 参数校验
            String userId = requestDTO.getUserId();
            String teamId = requestDTO.getTeamId();
            Long activityId = requestDTO.getActivityId();
            String goodsId = requestDTO.getGoodsId();
            String source = requestDTO.getSource();
            String channel = requestDTO.getChannel();
            String outTradeNo = requestDTO.getOutTradeNo();

            log.info("营销订单锁单: {} LockMarketPayOrderRequestDTO: {}", userId, JSON.toJSONString(requestDTO));

            if (StringUtils.isBlank(userId) || null == activityId || StringUtils.isBlank(goodsId) || StringUtils.isBlank(source) || StringUtils.isBlank(channel)) {
                return Response.<LockMarketPayOrderResponseDTO>builder()
                    .code(ResponseCode.ILLEGAL_PARAMETER.getCode())
                    .info(ResponseCode.ILLEGAL_PARAMETER.getInfo())
                    .build();
            }

            MarketPayOrderEntity marketPayOrderEntity = tradeOrderService.queryNoPayMarketOrderByOutTradeNo(userId, outTradeNo);
            if (null != marketPayOrderEntity) {
                LockMarketPayOrderResponseDTO lockMarketPayOrderResponseDTO = new LockMarketPayOrderResponseDTO();
                lockMarketPayOrderResponseDTO.setOrderId(marketPayOrderEntity.getOrderId());
                lockMarketPayOrderResponseDTO.setDeductionPrice(marketPayOrderEntity.getDeductionPrice());
                lockMarketPayOrderResponseDTO.setTradeOrderStatus(marketPayOrderEntity.getTradeOrderStatusEnumVO().getCode());

                log.info("交易锁单记录存在:{} marketPayOrderEntity:{}", userId, JSON.toJSONString(marketPayOrderEntity));

                return Response.<LockMarketPayOrderResponseDTO>builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .info(ResponseCode.SUCCESS.getInfo())
                    .data(lockMarketPayOrderResponseDTO).build();
            }

            if (null != teamId) {
                GroupBuyProgressVO groupBuyProgressVO = tradeOrderService.queryGroupBuyProgress(teamId);

                if (null != groupBuyProgressVO && Objects.equals(groupBuyProgressVO.getTargetCount(), groupBuyProgressVO.getLockCount())) {
                    log.info("交易锁单拦截-拼单目标已达成:{} {}", userId, teamId);
                    return Response.<LockMarketPayOrderResponseDTO>builder()
                        .code(ResponseCode.E0006.getCode())
                        .info(ResponseCode.E0006.getInfo())
                        .build();
                }
            }

            // 营销优惠试算
            TrialBalanceEntity trialBalanceEntity = indexGroupBuyMarketService.indexMarketTrial(MarketProductEntity.builder()
                .userId(userId)
                .channel(channel)
                .source(source)
                .goodsId(goodsId)
                .build());

            GroupBuyActivityDiscountVO groupBuyActivityDiscountVO = trialBalanceEntity.getGroupBuyActivityDiscountVO();

            marketPayOrderEntity = tradeOrderService.lockMarketPayOrder(UserEntity.builder().userId(userId).build(),
                PayActivityEntity.builder()
                    .teamId(teamId)
                    .activityId(activityId)
                    .activityName(groupBuyActivityDiscountVO.getActivityName())
                    .startTime(groupBuyActivityDiscountVO.getStartTime())
                    .endTime(groupBuyActivityDiscountVO.getEndTime())
                    .targetCount(groupBuyActivityDiscountVO.getTarget())
                    .build(),
                PayDiscountEntity.builder()
                    .source(source)
                    .channel(channel)
                    .goodsId(goodsId)
                    .goodsName(trialBalanceEntity.getGoodsName())
                    .originalPrice(trialBalanceEntity.getOriginalPrice())
                    .deductionPrice(trialBalanceEntity.getDeductionPrice())
                    .outTradeNo(outTradeNo)
                    .build());

            log.info("交易锁单记录(新):{} marketPayOrderEntity:{}", userId, JSON.toJSONString(marketPayOrderEntity));
            return Response.<LockMarketPayOrderResponseDTO>builder()
                .code(ResponseCode.SUCCESS.getCode())
                .info(ResponseCode.SUCCESS.getInfo())
                .data(LockMarketPayOrderResponseDTO.builder()
                    .orderId(marketPayOrderEntity.getOrderId())
                    .deductionPrice(marketPayOrderEntity.getDeductionPrice())
                    .tradeOrderStatus(marketPayOrderEntity.getTradeOrderStatusEnumVO().getCode())
                    .build())
                .build();

        } catch (AppException e) {
            log.error("营销交易锁单业务异常:{} LockMarketPayOrderRequestDTO:{}", requestDTO.getUserId(), JSON.toJSONString(requestDTO), e);
            return Response.<LockMarketPayOrderResponseDTO>builder()
                .code(e.getCode())
                .info(e.getInfo())
                .build();
        } catch (Exception e) {
            log.error("营销交易锁单系统异常:{} LockMarketPayOrderRequestDTO:{}", requestDTO.getUserId(), JSON.toJSONString(requestDTO), e);
            return Response.<LockMarketPayOrderResponseDTO>builder()
                .code(ResponseCode.UN_ERROR.getCode())
                .info(ResponseCode.UN_ERROR.getInfo())
                .build();
        }

    }
}
