package cn.cy.log.aspect;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author: you
 * @date: 2022-04-04 10:53
 * @description:
 */
@SpringBootTest
public class LogAspectTest {

    @Autowired
    private TestServiceComponent component;

    @Test
    public void logTest() {
        component.test("abc");
    }

    @Test
    public void logTestManyParams() {
        component.test1("chiyou", 9);
    }

    @Test
    public void logTestSuccessRes() {
        component.test2("chiyou", 9);
    }

    @Test
    public void logTestError() throws Exception {
        component.test3("chiyou", 9);
    }

    @Test
    public void LogTestAdd() {
        component.add("chiyou");
    }

    @Test
    public void logTestUpdate() {
        component.update("chiyou");
    }
}
