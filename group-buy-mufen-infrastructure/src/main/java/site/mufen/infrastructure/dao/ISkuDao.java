package site.mufen.infrastructure.dao;

import org.apache.ibatis.annotations.Mapper;
import site.mufen.infrastructure.dao.po.Sku;

/**
 * @author mufen
 * @Description Sku Dao 对象
 * @create 2025/1/7 22:06
 */
@Mapper
public interface ISkuDao {

    Sku querySkuByGoodsId(String goodsId);
}
