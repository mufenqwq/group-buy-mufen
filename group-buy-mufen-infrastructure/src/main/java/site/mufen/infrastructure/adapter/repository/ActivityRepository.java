package site.mufen.infrastructure.adapter.repository;

import org.springframework.stereotype.Repository;
import site.mufen.domain.activity.adapter.repository.IActivityRepository;
import site.mufen.domain.activity.model.valobj.DiscountTypeEnum;
import site.mufen.domain.activity.model.valobj.GroupBuyActivityDiscountVO;
import site.mufen.domain.activity.model.valobj.SCSkuActivityVO;
import site.mufen.domain.activity.model.valobj.SkuVO;
import site.mufen.infrastructure.dao.IGroupBuyActivityDao;
import site.mufen.infrastructure.dao.IGroupBuyDiscountDao;
import site.mufen.infrastructure.dao.ISCSkuActivityDao;
import site.mufen.infrastructure.dao.ISkuDao;
import site.mufen.infrastructure.dao.po.GroupBuyActivity;
import site.mufen.infrastructure.dao.po.GroupBuyDiscount;
import site.mufen.infrastructure.dao.po.SCSkuActivity;
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
    @Resource
    private ISCSkuActivityDao iscSkuActivityDao;

    @Override
    public GroupBuyActivityDiscountVO queryGroupBuyActivityDiscountVO(Long activityId) {
        GroupBuyActivity groupBuyActivityRes = groupBuyActivityDao.queryValidGroupBuyActivity(GroupBuyActivity.builder()
            .activityId(activityId).build());
        if (null == groupBuyActivityRes) return null;
        String discountId = groupBuyActivityRes.getDiscountId();
        GroupBuyDiscount groupBuyDiscountRes = groupBuyDiscountDao.queryGroupBuyDiscountByDiscountId(discountId);
        if (null == groupBuyDiscountRes) return null;
        GroupBuyActivityDiscountVO groupBuyActivityDiscountVO = new GroupBuyActivityDiscountVO();

        GroupBuyActivityDiscountVO.GroupBuyDiscount groupBuyDiscount = GroupBuyActivityDiscountVO.GroupBuyDiscount.builder()
            .discountName(groupBuyDiscountRes.getDiscountName())
            .discountDesc(groupBuyDiscountRes.getDiscountDesc())
            .discountType(DiscountTypeEnum.get(Integer.valueOf(groupBuyDiscountRes.getDiscountType())))
            .marketPlan(groupBuyDiscountRes.getMarketPlan())
            .marketExpr(groupBuyDiscountRes.getMarketExpr())
            .tagId(groupBuyDiscountRes.getTagId()).build();

        groupBuyActivityDiscountVO.setActivityId(groupBuyActivityRes.getActivityId());
        groupBuyActivityDiscountVO.setActivityName(groupBuyActivityRes.getActivityName());
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
        if (null == sku) return null;
        return SkuVO.builder()
            .originalPrice(sku.getOriginalPrice())
            .goodsId(sku.getGoodsId())
            .goodsName(sku.getGoodsName()).build();
    }

    @Override
    public SCSkuActivityVO querySCSkuActivityBySCGoodsId(String source, String channel, String goodsId) {
        SCSkuActivity scSkuActivityReq = new SCSkuActivity();
        scSkuActivityReq.setSource(source);
        scSkuActivityReq.setChannel(channel);
        scSkuActivityReq.setGoodsId(goodsId);

        SCSkuActivity scSkuActivity = iscSkuActivityDao.querySCSkuActivityByGoodsId(scSkuActivityReq);
        if (null == scSkuActivity) return null;

        return SCSkuActivityVO.builder()
            .source(scSkuActivity.getSource())
            .channel(scSkuActivity.getChannel())
            .activityId(scSkuActivity.getActivityId())
            .goodsId(scSkuActivity.getGoodsId()).build();
    }
}
