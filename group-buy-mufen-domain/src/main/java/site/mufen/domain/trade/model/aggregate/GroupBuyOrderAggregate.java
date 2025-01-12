package site.mufen.domain.trade.model.aggregate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.mufen.domain.trade.model.entity.PayActivityEntity;
import site.mufen.domain.trade.model.entity.PayDiscountEntity;
import site.mufen.domain.trade.model.entity.UserEntity;

/**
 * @author mufen
 * @Description 拼团订单聚合
 * @create 2025/1/12 20:27
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupBuyOrderAggregate {

    private UserEntity userEntity;

    private PayActivityEntity payActivityEntity;

    private PayDiscountEntity payDiscountEntity;
}
