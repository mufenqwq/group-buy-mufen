package site.mufen.infrastructure.dao;

import org.apache.ibatis.annotations.Mapper;
import site.mufen.infrastructure.dao.po.GroupBuyActivity;

import java.util.List;

/**
 * @author mufen
 * @Description 拼团活动Dao
 * @create  2025/1/7 17:20
 */
@Mapper
public interface IGroupBuyActivityDao {

    List<GroupBuyActivity> queryGroupBuyActivityList();
}
