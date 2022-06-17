package cn.cy.test.log;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author: 友
 * @Date: 2022/5/27 16:13
 * @Description:
 */
@Slf4j
public class Test2 {
    public void test() {
        log.info(Test2.class.getName());
    }
}
