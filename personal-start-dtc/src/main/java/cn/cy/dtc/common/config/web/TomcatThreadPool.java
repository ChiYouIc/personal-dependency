package cn.cy.dtc.common.config.web;

import lombok.Data;

/**
 * @author: you
 * @date: 2022-04-23 17:40
 * @description: Tomcat 线程池
 */
@Data
public class TomcatThreadPool {
    /**
     * Maximum amount of worker threads.
     */
    private int max = 200;

    /**
     * Minimum amount of worker threads.
     */
    private int minSpare = 10;
}
