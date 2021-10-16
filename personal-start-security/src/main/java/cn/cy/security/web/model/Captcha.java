package cn.cy.security.web.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author: 友叔(xu)
 * @Date: 2020/12/30 15:29
 * @Description: 验证码实体类
 */
@Getter
@Setter
@ToString
public class Captcha {
    private String uuid;
    private String img;
    private String code;

    public Captcha() {
    }

    public Captcha(String uuid, String img) {
        this.uuid = uuid;
        this.img = img;
    }
}
