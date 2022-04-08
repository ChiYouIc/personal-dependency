package cn.cy.log.bo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @Author: 友
 * @Date: 2022/4/8 19:49
 * @Description: 访问日志
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class AccessLog {
    /**
     * 名称
     */
    private String name;

    /**
     * 是否执行成功（Y-是，N-否）
     */
    private String success;

    /**
     * 具体消息
     */
    private String message;

    /**
     * ip
     */
    private String ip;

    /**
     * 地址
     */
    private String location;

    /**
     * 浏览器
     */
    private String browser;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 访问类型（字典 1登入 2登出）
     */
    private Integer visType;

    /**
     * 访问时间
     */
    private LocalDateTime visTime;

    /**
     * 访问人
     */
    private String account;
}
