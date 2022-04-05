package cn.cy.log.aspect;

import cn.cy.log.Log;
import org.springframework.stereotype.Component;

/**
 * @author: you
 * @date: 2022-04-04 11:09
 * @description:
 */
@Component
public class TestServiceComponent {

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
        del(user);
        add(user);
        return "更新成功";
    }
}
