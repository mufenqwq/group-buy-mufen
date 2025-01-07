package site.mufen.test.infrastructure.dao;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import site.mufen.infrastructure.dao.IGroupBuyDiscountDao;
import site.mufen.infrastructure.dao.po.GroupBuyDiscount;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author mufen
 * @Description
 * @create 2025/1/7 18:02
 */
@SpringBootTest
@Slf4j
@RunWith(SpringRunner.class)
public class GroupBuyDiscountDaoTest {
    @Resource
    private IGroupBuyDiscountDao groupBuyDiscountDao;

    @Test
    public void test_queryGroupBuyDiscountList() {
        List<GroupBuyDiscount> groupBuyDiscounts = groupBuyDiscountDao.queryGroupBuyDiscountList();
        log.info("测试结果: {}", groupBuyDiscounts);
    }
}
