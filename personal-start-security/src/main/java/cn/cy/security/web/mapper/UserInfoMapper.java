package cn.cy.security.web.mapper;

import cn.cy.security.web.model.LoginUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @program: personal-website
 * @author: 开水白菜
 * @description: 用户信息Mapper
 * @create: 2021-03-06 14:43
 **/
@Mapper
@Repository
public interface UserInfoMapper {

    /**
     * 根据用户名获取用户信息
     *
     * @param username 用户名
     * @return loginUser 用户登陆信息
     */
    public LoginUser selectLoginUserInfoByUsername(String username);

    /**
     * 更新用户信息
     *
     * @param loginUser 用户对象
     * @return row
     */
    public int updateUserLoginInfo(LoginUser loginUser);
}
