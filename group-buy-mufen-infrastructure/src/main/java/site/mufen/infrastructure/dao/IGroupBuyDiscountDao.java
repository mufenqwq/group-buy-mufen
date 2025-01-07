package site.mufen.infrastructure.dao;

import org.apache.ibatis.annotations.Mapper;
import site.mufen.infrastructure.dao.po.GroupBuyDiscount;

import java.util.List;

/**
 * @author mufen
 * @Description 拼团配置Dao
 * @create 2025/1/7 17:20
 */
@Mapper
public interface IGroupBuyDiscountDao {
    List<GroupBuyDiscount> queryGroupBuyDiscountList();

    GroupBuyDiscount queryGroupBuyDiscountByDiscountId(String discountId);
}
