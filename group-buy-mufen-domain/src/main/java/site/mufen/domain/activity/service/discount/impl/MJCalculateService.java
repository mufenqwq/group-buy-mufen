package site.mufen.domain.activity.service.discount.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import site.mufen.domain.activity.model.valobj.GroupBuyActivityDiscountVO;
import site.mufen.domain.activity.service.discount.AbstractDiscountCalculateService;
import site.mufen.types.common.Constants;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author mufen
 * @Description 满减优惠
 * @create 2025/1/8 11:52
 */
@Slf4j
@Service("MJ")
public class MJCalculateService extends AbstractDiscountCalculateService {
    @Override
    public BigDecimal doCalculate(BigDecimal originalPrice, GroupBuyActivityDiscountVO.GroupBuyDiscount groupBuyDiscount) {
        log.info("优惠策略折扣计算: {}", groupBuyDiscount.getDiscountType().getInfo());
        String marketExpr = groupBuyDiscount.getMarketExpr();
        String[] split = marketExpr.split(Constants.SPLIT);
        BigDecimal minPrice = new BigDecimal(split[0]); // 能满减的最少价格
        BigDecimal discount = new BigDecimal(split[1]); // 减少的价格
        if (originalPrice.compareTo(minPrice) < 0) return originalPrice;
        BigDecimal discountPrice = originalPrice.subtract(discount);
        if (discountPrice.compareTo(new BigDecimal("0.01")) < 0) return new BigDecimal("0.01");
        discountPrice = discountPrice.setScale(2, RoundingMode.HALF_UP);
        return discountPrice;
    }
}
