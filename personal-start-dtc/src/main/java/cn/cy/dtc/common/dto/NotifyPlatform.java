package cn.cy.dtc.common.dto;

import lombok.Data;

/**
 * @author: you
 * @date: 2022-04-23 17:32
 * @description: 通知平台
 */
@Data
public class NotifyPlatform {

    /**
     * 平台名称
     */
    private String platform;

    /**
     * url标识
     */
    private String urlKey;

    /**
     * 密钥
     */
    private String secret;

    /**
     * 接收者
     */
    private String receivers = "所有人";

}
