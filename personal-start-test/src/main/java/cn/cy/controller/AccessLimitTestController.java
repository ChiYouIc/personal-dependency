package cn.cy.controller;

import cn.cy.limit.idemp.IdempotentLimiter;
import cn.cy.limit.rate.LimitType;
import cn.cy.limit.rate.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: 友
 * @Date: 2022/5/24 18:19
 * @Description: 访问控制
 */
@Slf4j
@RestController
@RequestMapping("limit")
public class AccessLimitTestController {

    @IdempotentLimiter(time = 10)
    @GetMapping("/idem")
    public void idempotentLimitTest() throws InterruptedException {
        Thread.sleep(12000);
        log.info("ssssssssssss");
    }

    @IdempotentLimiter
    @GetMapping("/idem1")
    public void idempotentLimitTest_1() throws Exception {
        throw new Exception("ssss");
    }

    @GetMapping("/idem2")
    public void idempotentLimitTest_2() throws Exception {
        throw new Exception("ssss");
    }

    @IdempotentLimiter(enable = false)
    @GetMapping("/idem3")
    public void idempotentLimitTest_3() throws InterruptedException {
        Thread.sleep(12000);
        log.info("ssssssssssss");
    }

    @RateLimiter(time = 5, count = 1, limitType = LimitType.IP)
    @GetMapping("/rate")
    public void rateLimitTest() {
        log.info("ssssss");
    }
}
