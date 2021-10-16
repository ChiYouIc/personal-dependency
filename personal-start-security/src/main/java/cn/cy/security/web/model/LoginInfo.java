package cn.cy.security.web.model;

import cn.cy.mybatis.web.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

import java.time.LocalDate;

/**
 * @Author: 友叔(xu)
 * @Date: 2020/12/25 14:35
 * @Description: 用户登陆日志对象
 */
@Setter
@Getter
@ToString
@Alias("LoginInfo")
public class LoginInfo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long infoId;

    /**
     * 用户账号
     */
    private String account;

    /**
     * 用户账号
     */
    private String username;

    /**
     * 登录状态 1成功 0失败
     */
    private Integer status;

    /**
     * 登录IP地址
     */
    private String ipaddr;

    /**
     * 登录地点
     */
    private String loginLocation;

    /**
     * 浏览器类型
     */
    private String browser;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 提示消息
     */
    private String msg;

    /**
     * 访问时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDate loginTime;
}
