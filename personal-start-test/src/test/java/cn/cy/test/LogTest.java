package cn.cy.test;

import cn.cy.test.log.Test1;
import cn.cy.test.log.Test2;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author: 友
 * @Date: 2022/5/27 16:11
 * @Description:
 */
@SpringBootTest
public class LogTest {

    @Test
    public void test() {
        Test1 test1 = new Test1();
        Test2 test2 = new Test2();
        System.out.println("傻傻");
        System.out.println("？为啥颜色都没得");
        test1.test();
        test2.test();
    }

}
