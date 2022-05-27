package cn.cy;

import cn.cy.limit.idemp.EnableIdempotentLimit;
import cn.cy.limit.idemp.IdempType;
import cn.cy.limit.rate.EnableRateLimit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 开水白菜
 */
@EnableRateLimit
@EnableIdempotentLimit(type = IdempType.GLOBAL)
@SpringBootApplication
public class PersonalWebsiteApplicationStart {
    public static void main(String[] args) {
        SpringApplication.run(PersonalWebsiteApplicationStart.class, args);
    }
}
