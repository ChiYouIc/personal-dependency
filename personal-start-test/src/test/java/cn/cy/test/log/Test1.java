package cn.cy.test.log;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author: 友
 * @Date: 2022/5/27 16:12
 * @Description:
 */
@Slf4j
public class Test1 {

    public void test() {
        log.info(Test1.class.getName());
        log.error(Test1.class.getName());
        log.warn(Test1.class.getName());

    }
}
