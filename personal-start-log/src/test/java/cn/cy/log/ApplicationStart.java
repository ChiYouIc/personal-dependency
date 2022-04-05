package cn.cy.log;

import cn.cy.log.bo.Operator;
import cn.cy.log.service.IOperatorGetService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author: you
 * @date: 2022-04-04 10:52
 * @description:
 */
@SpringBootApplication
public class ApplicationStart {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationStart.class, args);
    }

    @Bean
    public IOperatorGetService operatorGetService() {
        return () -> new Operator("1", "222");
    }
}
