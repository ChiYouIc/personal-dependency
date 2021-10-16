package cn.cy.security.web.service;

import cn.cy.security.web.model.LoginUser;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @Author: 友叔
 * @Date: 2020/12/4 15:52
 * @Description: 用户权限服务
 */
public interface ISecurityUserService extends UserDetailsService {
    /**
     * 记录用户最后登陆IP
     *
     * @param loginUser 用户对象
     * @return row 结果
     */
    public int updateUserLoginInfo(LoginUser loginUser);
}
