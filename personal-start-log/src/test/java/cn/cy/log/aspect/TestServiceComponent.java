package cn.cy.log.aspect;

import cn.cy.log.Log;
import cn.cy.log.LogUtils;
import cn.cy.log.expression.LogRecordContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author: you
 * @date: 2022-04-04 11:09
 * @description:
 */
@Slf4j
@Component
public class TestServiceComponent {

//    @Resource
//    private TestServiceComponent self;

    @Log(success = "{#msg}转大写{#msg.toUpperCase}")
    public void test(String msg) {
        System.out.println("test");
    }

    @Log(success = "姓名: {#userName}， 年龄: {#age}")
    public void test1(String userName, Integer age) {
        System.out.println("test");
    }

    @Log(success = "姓名: {#userName}， 年龄: {#age}，结果：{#_ret}")
    public void test2(String userName, Integer age) {
        System.out.println("test2");
    }

    @Log(success = "姓名: {#userName}， 年龄: {#age}，结果：{#_ret}", error = "异常：{#_errorMsg}")
    public void test3(String userName, Integer age) throws Exception {
        throw new Exception("sss");
    }

    @Log(success = "新增：{#user}，结果：{#_ret}", error = "异常：{#_errorMsg}")
    public String add(String user) {
        return "添加成功";
    }

    @Log(success = "删除：{#user}，结果：{#_ret}", error = "异常：{#_errorMsg}")
    public String del(String user) {
        return "删除成功！";
    }

    @Log(success = "更新：{#user}，结果：{#_ret}", error = "异常：{#_errorMsg}")
    public String update(String user) {
//        self.del(user);
//        self.add(user);
        return "更新成功";
    }

    @Log(success = "姓名: {#name}，昵称：{#nickName}")
    public void logRecordContextTest() {
        LogRecordContext.putVariable("name", "chiyou");
        LogRecordContext.putVariable("nickName", "coder-you");
        LogUtils.info(log, "logRecordContextTest");
//        self.logRecordContextTest1();
//        self.logRecordContextTest2();
    }

    @Log(success = "姓名: {#name}")
    public void logRecordContextTest1() {
        LogRecordContext.putVariable("name", "chiyou");
        LogUtils.info(log, "logRecordContextTest1");
    }

    @Log(success = "昵称：{#nickName}")
    public void logRecordContextTest2() {
        LogRecordContext.putVariable("nickName", "coder-you");
        LogUtils.info(log, "logRecordContextTest2");
    }
}
