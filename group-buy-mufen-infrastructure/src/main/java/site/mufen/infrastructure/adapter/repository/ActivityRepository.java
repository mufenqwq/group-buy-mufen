package site.mufen.infrastructure.adapter.repository;

import org.springframework.stereotype.Repository;
import site.mufen.domain.activity.adapter.repository.IActivityRepository;
import site.mufen.domain.activity.model.valobj.GroupBuyActivityDiscountVO;
import site.mufen.domain.activity.model.valobj.SkuVO;
import site.mufen.infrastructure.dao.IGroupBuyActivityDao;
import site.mufen.infrastructure.dao.IGroupBuyDiscountDao;
import site.mufen.infrastructure.dao.ISkuDao;
import site.mufen.infrastructure.dao.po.GroupBuyActivity;
import site.mufen.infrastructure.dao.po.GroupBuyDiscount;
import site.mufen.infrastructure.dao.po.Sku;

import javax.annotation.Resource;

/**
 * @author mufen
 * @Description 活动仓储实现类
 * @create 2025/1/7 22:51
 */
@Repository
public class ActivityRepository implements IActivityRepository {

    @Resource
    private IGroupBuyActivityDao groupBuyActivityDao;
    @Resource
    private IGroupBuyDiscountDao groupBuyDiscountDao;
    @Resource
    private ISkuDao skuDao;

    @Override
    public GroupBuyActivityDiscountVO queryGroupBuyActivityDiscountVO(String source, String channel) {
        GroupBuyActivity groupBuyActivityRes = groupBuyActivityDao.queryValidGroupBuyActivity(GroupBuyActivity.builder()
            .source(source).channel(channel).build());
        String discountId = groupBuyActivityRes.getDiscountId();
        GroupBuyDiscount groupBuyDiscountRes = groupBuyDiscountDao.queryGroupBuyDiscountByDiscountId(discountId);
        GroupBuyActivityDiscountVO groupBuyActivityDiscountVO = new GroupBuyActivityDiscountVO();

        GroupBuyActivityDiscountVO.GroupBuyDiscount groupBuyDiscount = GroupBuyActivityDiscountVO.GroupBuyDiscount.builder()
            .discountName(groupBuyDiscountRes.getDiscountName())
            .discountDesc(groupBuyDiscountRes.getDiscountDesc())
            .discountType(groupBuyDiscountRes.getDiscountType())
            .marketPlan(groupBuyDiscountRes.getMarketPlan())
            .marketExpr(groupBuyDiscountRes.getMarketExpr())
            .tagId(groupBuyDiscountRes.getTagId()).build();

        groupBuyActivityDiscountVO.setActivityId(groupBuyActivityRes.getActivityId());
        groupBuyActivityDiscountVO.setActivityName(groupBuyActivityRes.getActivityName());
        groupBuyActivityDiscountVO.setSource(groupBuyActivityRes.getSource());
        groupBuyActivityDiscountVO.setChannel(groupBuyActivityRes.getChannel());
        groupBuyActivityDiscountVO.setGoodsId(groupBuyActivityRes.getGoodsId());
        groupBuyActivityDiscountVO.setGroupBuyDiscount(groupBuyDiscount);
        groupBuyActivityDiscountVO.setGroupType(groupBuyActivityRes.getGroupType());
        groupBuyActivityDiscountVO.setTakeLimitCount(groupBuyActivityRes.getTakeLimitCount());
        groupBuyActivityDiscountVO.setTarget(groupBuyActivityRes.getTarget());
        groupBuyActivityDiscountVO.setValidTime(groupBuyActivityRes.getValidTime());
        groupBuyActivityDiscountVO.setStatus(groupBuyActivityRes.getStatus());
        groupBuyActivityDiscountVO.setStartTime(groupBuyActivityRes.getStartTime());
        groupBuyActivityDiscountVO.setEndTime(groupBuyActivityRes.getEndTime());
        groupBuyActivityDiscountVO.setTagId(groupBuyActivityRes.getTagId());
        groupBuyActivityDiscountVO.setTagScope(groupBuyActivityRes.getTagScope());


        return groupBuyActivityDiscountVO;
    }

    @Override
    public SkuVO querySkuByGoodsId(String goodsId) {
        Sku sku = skuDao.querySkuByGoodsId(goodsId);
        return SkuVO.builder()
            .goodsId(sku.getGoodsId())
            .goodsName(sku.getGoodsName())
            .originalPrice(sku.getOriginalPrice())
            .build();
    }
}
