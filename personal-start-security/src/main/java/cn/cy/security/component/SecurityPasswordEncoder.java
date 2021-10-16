package cn.cy.security.component;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @Author: 友叔(xu)
 * @Date: 2020/12/4 17:38
 * @Description: 密码加密器
 */
@Component(value = "passwordEncoder")
public class SecurityPasswordEncoder extends BCryptPasswordEncoder {

    /**
     * 默认密码
     */
    private static final String DEFAULT_PASSWORD = "123456";

    public String resetPassword() {
        return encode(DEFAULT_PASSWORD);
    }
}
