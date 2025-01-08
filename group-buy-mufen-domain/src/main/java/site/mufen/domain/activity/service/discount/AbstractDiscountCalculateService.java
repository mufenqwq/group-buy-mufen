package site.mufen.domain.activity.service.discount;


import lombok.extern.slf4j.Slf4j;
import site.mufen.domain.activity.model.valobj.DiscountTypeEnum;
import site.mufen.domain.activity.model.valobj.GroupBuyActivityDiscountVO;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author mufen
 * @Description 折扣计算服务抽奖类
 * @create 2025/1/8 11:49
 */
@Slf4j
public abstract class AbstractDiscountCalculateService implements IDiscountCalculateService {
    @Override
    public BigDecimal discountCalculate(String userId, BigDecimal originalPrice, GroupBuyActivityDiscountVO.GroupBuyDiscount groupBuyDiscount) {
        // 1.人群标签过滤
        if (DiscountTypeEnum.TAG.equals(groupBuyDiscount.getDiscountType())) {
            boolean isCrowdRange = filterTagId(userId, groupBuyDiscount.getTagId());
            if (!isCrowdRange) return originalPrice;
        }
        // 2. 折扣价格计算
        return doCalculate(originalPrice, groupBuyDiscount);
    }

    // 人群过滤 限定人群优惠
    private boolean filterTagId(String userId, String tagId) {
        // todo 后续开发这部分
        return true;
    }

    /**
     * 抽象出一个最后的检查折扣价格方法，防止出现问题
     * @param discountPrice 折扣价格
     * @return 验证过后的折扣价格
     */
    protected BigDecimal validDiscountPrice(BigDecimal discountPrice) {
        discountPrice = discountPrice.setScale(2, RoundingMode.HALF_UP);
        if (discountPrice.compareTo(new BigDecimal("0.01")) < 0) return new BigDecimal("0.01");
        return discountPrice;
    }

    protected abstract BigDecimal doCalculate(BigDecimal originalPrice, GroupBuyActivityDiscountVO.GroupBuyDiscount groupBuyDiscount);
}
