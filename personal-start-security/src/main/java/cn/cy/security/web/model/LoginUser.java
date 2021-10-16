package cn.cy.security.web.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author: 友叔(xu)
 * @Date: 2020/12/4 16:16
 * @Description: 登陆用户
 */
@Getter
@Setter
@ToString
@Alias("LoginUser")
public class LoginUser implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 性别
     */
    private String sex;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 是否Admin
     */
    private Integer admin;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 密码
     */
    private String password;

    /**
     * 登陆IP
     */
    @JsonIgnore
    private String loginIp;

    /**
     * 登录方式
     */
    @JsonIgnore
    private String type;

    /**
     * 自动登录
     */
    @JsonIgnore
    private boolean autoLogin;

    /**
     * 登陆时间
     */
    @JsonIgnore
    private LocalDateTime loginDate;

    /**
     * uuid
     */
    private String captchaCodeToken;

    /**
     * 验证码
     */
    private String captchaCode;

    /**
     * 备注
     */
    private String remark;

    private List<Role> roles;

    private List<Permission> permissions;

}
