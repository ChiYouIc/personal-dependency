package cn.cy.sso.utils;


import cn.cy.sso.model.SsoUser;

/**
 * @author: 开水白菜
 * @description: 单点工具；使用 ThreadLocal 将当前线程用户信息保存
 * @create: 2021-09-14 22:00
 **/
public final class SsoUtil {

    private static final ThreadLocal<SsoUser> CONTEXT_HOLDER = new ThreadLocal<>();

    public static SsoUser getInfo() {
        return CONTEXT_HOLDER.get();
    }

    public static void setInfo(SsoUser ssoUser) {
        CONTEXT_HOLDER.set(ssoUser);
    }

    public static void remove() {
        CONTEXT_HOLDER.remove();
    }

}
