package cn.cy.sso.utils;

import cn.cy.sso.model.SsoUser;

/**
 * @author: 开水白菜
 * @description: 用户工具
 * @create: 2021-11-17 19:52
 **/
public final class UserUtil {

    public static SsoUser getUserInfo() {
        return SsoUtil.getInfo();
    }

    public static String id() {
        return getUserInfo().getId();
    }

    public static String userId() {
        return getUserInfo().getUserId();
    }

    public static String username() {
        return getUserInfo().getUsername();
    }

    public static String token() {
        return getUserInfo().getToken();
    }

    public static String authKey() {
        return getUserInfo().getAuthKey();
    }

}
