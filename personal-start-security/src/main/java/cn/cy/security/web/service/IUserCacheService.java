package cn.cy.security.web.service;


import cn.cy.security.web.model.SecurityUserDetails;

import java.util.List;

/**
 * @Author: 友叔(xu)
 * @Date: 2020/12/5 11:29
 * @Description: 用户信息缓存Service
 */
public interface IUserCacheService {
    /**
     * 设置缓存后台用户信息
     *
     * @param loginUser 用户信息
     */
    public void setUserInfo(SecurityUserDetails loginUser);

    /**
     * 获取缓存后的用户信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    public SecurityUserDetails getUserInfo(String username);

    /**
     * 删除缓存的用户信息
     *
     * @param username 用户名
     * @return true 删除成功，false 删除失败
     */
    public boolean deleteUserInfo(String username);

    /**
     * 获取缓存里面所有的用户信息
     *
     * @return 用户名列表
     */
    public List<String> getUsernameList();
}
