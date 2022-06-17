package cn.cy.controller;

import cn.cy.sync.async.AsyncExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.TimerTask;

/**
 * @Author: 友叔
 * @Date: 2021/12/16 16:12
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("/async")
public class AsyncTestController {

    @GetMapping("/test")
    public void test() {
        AsyncExecutor me = AsyncExecutor.me();
        me.execute(new TimerTask() {
            @Override
            public void run() {
                log.info("start do some thing...");

                log.info("end do some thing...");
            }
        });
    }

}
