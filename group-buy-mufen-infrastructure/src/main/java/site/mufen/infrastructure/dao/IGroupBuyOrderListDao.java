package site.mufen.infrastructure.dao;

import org.apache.ibatis.annotations.Mapper;
import site.mufen.infrastructure.dao.po.GroupBuyOrderList;

/**
 * @author mufen
 * @Description 订单列表Dao
 * @create 2025/1/12 18:30
 */
@Mapper
public interface IGroupBuyOrderListDao {

    void insert(GroupBuyOrderList groupBuyOrderList);

    GroupBuyOrderList queryGroupBuyOrderListByOutTradeNo(GroupBuyOrderList groupBuyOrderListReq);

}
