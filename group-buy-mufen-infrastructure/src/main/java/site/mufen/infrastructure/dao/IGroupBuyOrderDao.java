package site.mufen.infrastructure.dao;

import org.apache.ibatis.annotations.Mapper;
import site.mufen.infrastructure.dao.po.GroupBuyOrder;

/**
 * @author mufen
 * @Description 拼团订单Dao
 * @create 2025/1/12 18:30
 */
@Mapper
public interface IGroupBuyOrderDao {
    void insert(GroupBuyOrder groupBuyOrder);

    int updateAddLockCount(String teamId);

    int updateSubLockCount(String teamId);

    GroupBuyOrder queryGroupBuyProgress(String teamId);
}
