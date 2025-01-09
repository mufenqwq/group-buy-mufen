package site.mufen.test.domain.activity;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import site.mufen.domain.activity.model.entity.MarketProductEntity;
import site.mufen.domain.activity.model.entity.TrialBalanceEntity;
import site.mufen.domain.activity.service.IIndexGroupBuyMarketService;

import javax.annotation.Resource;

/**
 * @author mufen
 * @Description
 * @create 2025/1/8 0:46
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class IIndexGroupBuyMarketServiceTest {

    @Resource
    private IIndexGroupBuyMarketService indexGroupBuyMarketService;

    @Test
    public void test() throws Exception {
        MarketProductEntity marketProductEntity = new MarketProductEntity();
        marketProductEntity.setUserId("mufenqwq");
        marketProductEntity.setSource("s01");
        marketProductEntity.setChannel("c01");
        marketProductEntity.setGoodsId("9890001");

        TrialBalanceEntity trialBalanceEntity = indexGroupBuyMarketService.indexMarketTrial(marketProductEntity);
        log.info("请求参数: {}", JSON.toJSONString(marketProductEntity));
        log.info("响应参数: {}", JSON.toJSONString(trialBalanceEntity));
    }

    @Test
    public void test_indexMarketTrial_error() throws Exception {
        MarketProductEntity marketProductEntity = new MarketProductEntity();
        marketProductEntity.setUserId("mufenqwq");
        marketProductEntity.setSource("s01");
        marketProductEntity.setChannel("c01");
        marketProductEntity.setGoodsId("9890002");

        TrialBalanceEntity trialBalanceEntity = indexGroupBuyMarketService.indexMarketTrial(marketProductEntity);

        log.info("请求参数: {}", JSON.toJSONString(marketProductEntity));
        log.info("响应参数: {}", JSON.toJSONString(trialBalanceEntity));
    }

}
