package cn.cy.security.component;

import cn.cy.security.util.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * <b>备注：</b>
 * <p>{@link org.springframework.security.config.annotation.web.builders.WebSecurity#setApplicationContext(ApplicationContext)}
 * 该方法中有注入 {@link PermissionEvaluator} 是通过 {@link ApplicationContext} 获取注入的，故我们可以通过实现 {@link PermissionEvaluator} 的方式注入自己的权限校验器</p>
 *
 * @Author: 友叔(xu)
 * @Date: 2020/12/6 20:06
 * @Description: 自定义用户权限验证
 */
@Slf4j
@Component(value = "userPermissionEvaluator")
public class UserPermissionEvaluator implements PermissionEvaluator {

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        boolean flag = UserUtil.isAdmin() || UserUtil.permissions().stream().anyMatch(per -> per.equals(permission));
        log.info("{} 访问接口 {} {}", UserUtil.username(), targetDomainObject, flag ? "成功" : "失败");
        return flag;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }
}
