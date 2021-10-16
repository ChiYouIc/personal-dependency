package cn.cy.security.enums;


import cn.cy.web.enums.IEnum;

/**
 * @Author: 友叔(xu)
 * @Date: 2020/12/4 16:24
 * @Description: 用户状态码
 */
public enum UserStatusCode implements IEnum<Integer> {
    /**
     * 0	->	关闭
     * 1	->	开启
     */
    DISABLE(0, "关闭"),
    ENABLE(1, "开启"),
    ;

    private final Integer value;
    private final String description;

    UserStatusCode(final Integer value, final String description) {
        this.value = value;
        this.description = description;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
