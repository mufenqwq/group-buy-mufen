package site.mufen.test.infrastructure.dao;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import site.mufen.infrastructure.dao.IGroupBuyActivityDao;
import site.mufen.infrastructure.dao.po.GroupBuyActivity;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author mufen
 * @Description 单测
 * @create 2025/1/7 17:44
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class GroupBuyActivityDaoTest {
    @Resource
    private IGroupBuyActivityDao groupBuyActivityDao;

    @Test
    public void test_queryGroupActivityList() {
        List<GroupBuyActivity> groupBuyActivities = groupBuyActivityDao.queryGroupBuyActivityList();
        log.info("测试结果: {}", groupBuyActivities);
    }
}
