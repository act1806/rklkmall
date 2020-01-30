package org.linlinjava.litemall.core;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.linlinjava.litemall.core.deppon.DepponService;
import org.linlinjava.litemall.core.express.dao.ExpressInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class DepponTest {

    private final Log logger = LogFactory.getLog(DepponTest.class);
    @Autowired
    private DepponService depponService;

    @Test
    public void test() {
        try {
            depponService.queryOrder();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Test
    public void test1() {
        try {
            depponService.newTraceQuery("123456");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
