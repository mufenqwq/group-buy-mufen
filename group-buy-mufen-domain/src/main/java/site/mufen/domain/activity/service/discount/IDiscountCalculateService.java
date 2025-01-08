package site.mufen.domain.activity.service.discount;

import site.mufen.domain.activity.model.valobj.GroupBuyActivityDiscountVO;

import java.math.BigDecimal;

/**
 * @author mufen
 * @Description 折扣计算服务接口
 * @create 2025/1/8 11:49
 */
public interface IDiscountCalculateService {
    /**
     * 折扣计算
     * @param userId 用户Id
     * @param originalPrice 原始价格
     * @param groupBuyDiscount groupBuyDiscount 对象
     * @return 折扣价格
     */
    BigDecimal discountCalculate(String userId, BigDecimal originalPrice, GroupBuyActivityDiscountVO.GroupBuyDiscount groupBuyDiscount);
}
