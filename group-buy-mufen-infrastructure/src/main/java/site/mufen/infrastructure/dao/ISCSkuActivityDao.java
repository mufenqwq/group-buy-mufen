package site.mufen.infrastructure.dao;

import org.apache.ibatis.annotations.Mapper;
import site.mufen.infrastructure.dao.po.SCSkuActivity;

/**
 * @author mufen
 * @Description 商品信息Dao
 * @create 2025/1/9 17:24
 */
@Mapper
public interface ISCSkuActivityDao {

    SCSkuActivity querySCSkuActivityByGoodsId(SCSkuActivity scSkuActivity);
}
