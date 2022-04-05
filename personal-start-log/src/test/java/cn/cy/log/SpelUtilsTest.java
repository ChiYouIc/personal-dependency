package cn.cy.log;

import org.junit.jupiter.api.Test;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: you
 * @date: 2022-04-03 21:02
 * @description: Spel 工具类测试
 */
public class SpelUtilsTest {

    @Test
    public void parseExpressionTest() {

        List<String> values = new ArrayList<>();

        String msg = "我是{#root.userName}，我今年{#root.age}岁.abc转大写{'abc'.toUpperCase}";
        List<String> strings = SpelUtils.parseExpression(msg);
        SpelExpressionParser parser = new SpelExpressionParser();
        for (String s : strings) {
            Expression expression = parser.parseExpression(s);
            String value = expression.getValue(getUser(), String.class);
            msg = msg.replaceFirst(SpelUtils.REG, value);
        }

        System.out.println(msg);

    }


    public static User getUser() {
        return new User("池友", 25);
    }

}
