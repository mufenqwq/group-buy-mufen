package site.mufen.domain.activity.service.trial.thread;

import site.mufen.domain.activity.adapter.repository.IActivityRepository;
import site.mufen.domain.activity.model.valobj.SkuVO;

import java.util.concurrent.Callable;

/**
 * @author mufen
 * @Description
 * @create 2025/1/7 23:04
 */
public class QuerySkuVOFromDBThreadTask implements Callable<SkuVO> {

    private final String goodsId;

    private final IActivityRepository activityRepository;

    public QuerySkuVOFromDBThreadTask(String goodsId, IActivityRepository activityRepository) {
        this.goodsId = goodsId;
        this.activityRepository = activityRepository;
    }

    @Override
    public SkuVO call() throws Exception {
        return activityRepository.querySkuByGoodsId(goodsId);
    }
}
