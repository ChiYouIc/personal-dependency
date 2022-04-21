package cn.cy.log;

import org.junit.jupiter.api.Test;

/**
 * @Author: 友
 * @Date: 2022/4/21 14:21
 * @Description: LogMsgFormatter 测试
 */
public class LogMsgFormatterTest {

    @Test
    public void test1() {
        System.out.println(LogMsgFormatter.format("name : {}, age : {}", "池", 12));
        System.out.println(LogMsgFormatter.format("name : {}, age : {}, address : {}", "池", 12));
        System.out.println(LogMsgFormatter.format("name : \\\\{}, age : {\\}, address : {}", "池", 12));
    }

}
