package cn.cy;

import cn.cy.limit.idemp.EnableIdempotentLimit;
import cn.cy.limit.idemp.IdempType;
import cn.cy.limit.rate.EnableRateLimit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 开水白菜
 */
@Slf4j
@EnableRateLimit
@EnableIdempotentLimit(type = IdempType.GLOBAL)
@SpringBootApplication
public class PersonalWebsiteApplicationStart {

    public static void main(String[] args) {
        SpringApplication.run(PersonalWebsiteApplicationStart.class, args);
        log.info("哈哈");
        log.info("你这项目准备干啥？");
    }
}
