package cn.cy.security.web.service.impl;

import cn.cy.security.web.mapper.UserInfoMapper;
import cn.cy.security.web.model.LoginUser;
import cn.cy.security.web.model.SecurityUserDetails;
import cn.cy.security.web.service.ISecurityUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @program: personal-website
 * @author: 开水白菜
 * @description:
 * @create: 2021-03-06 14:42
 **/
@Service
public class ISecurityUserServiceImpl implements ISecurityUserService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public int updateUserLoginInfo(LoginUser loginUser) {
        return userInfoMapper.updateUserLoginInfo(loginUser);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LoginUser loginUser = userInfoMapper.selectLoginUserInfoByUsername(username);
        return new SecurityUserDetails(loginUser);
    }
}
