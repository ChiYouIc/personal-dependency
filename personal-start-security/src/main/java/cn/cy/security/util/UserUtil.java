package cn.cy.security.util;

import cn.cy.security.web.model.LoginUser;
import cn.cy.security.web.model.SecurityUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

/**
 * @Author: 友叔(xu)
 * @Date: 2020/12/12 13:12
 * @Description: 用户信息工具
 */
public class UserUtil {

    private static final Integer IS_ADMIN = 1;

    public static SecurityUserDetails getSecurityUserDetails() {
        return (SecurityUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static LoginUser getLoginUser() {
        return getSecurityUserDetails().getLoginUser();
    }

    /**
     * 获取用户ID
     */
    public static Long userId() {
        return getLoginUser().getId();
    }

    /**
     * 获取用户名
     */
    public static String username() {
        return getLoginUser().getUsername();
    }

    /**
     * 是否管理员
     */
    public static boolean isAdmin() {
        return getLoginUser().getAdmin().equals(IS_ADMIN);
    }

    /**
     * 获取角色
     */
    public static List<String> roles() {
        return getSecurityUserDetails().getRoles();
    }

    /**
     * 获取权限
     */
    public static List<String> permissions() {
        return getSecurityUserDetails().getPermissions();
    }
}
