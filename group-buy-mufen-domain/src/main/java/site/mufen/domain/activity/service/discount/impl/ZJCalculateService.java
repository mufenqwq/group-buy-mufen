package site.mufen.domain.activity.service.discount.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import site.mufen.domain.activity.model.valobj.GroupBuyActivityDiscountVO;
import site.mufen.domain.activity.service.discount.AbstractDiscountCalculateService;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author mufen
 * @Description 直减优惠
 * @create 2025/1/8 11:52
 */
@Slf4j
@Service("ZJ")
public class ZJCalculateService extends AbstractDiscountCalculateService {
    @Override
    public BigDecimal doCalculate(BigDecimal originalPrice, GroupBuyActivityDiscountVO.GroupBuyDiscount groupBuyDiscount) {
        log.info("优惠策略折扣计算: {}", groupBuyDiscount.getDiscountType().getInfo());
        String marketExpr = groupBuyDiscount.getMarketExpr();
        BigDecimal discountPrice = originalPrice.subtract(new BigDecimal(marketExpr));
        discountPrice = discountPrice.setScale(2, RoundingMode.HALF_UP);
        if (discountPrice.compareTo(new BigDecimal("0.01")) < 0) return new BigDecimal("0.01");
        return discountPrice;
    }
}
