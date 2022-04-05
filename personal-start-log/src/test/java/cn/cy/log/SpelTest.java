package cn.cy.log;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;

/**
 * @author: you
 * @date: 2022-04-03 12:00
 * @description: 日志
 */
@SpringBootTest
public class SpelTest {

    @Test
    public void test1() {
        SpelExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression("#root.purchaseName");
        Order order = new Order();
        order.setPurchaseName("张三");
        System.out.println(expression.getValue(order));
    }

    @Log(success = "sssss")
    public void test2() {

    }

    class Order {

        private String purchaseName;

        public String getPurchaseName() {
            return purchaseName;
        }

        public void setPurchaseName(String purchaseName) {
            this.purchaseName = purchaseName;
        }
    }

}
