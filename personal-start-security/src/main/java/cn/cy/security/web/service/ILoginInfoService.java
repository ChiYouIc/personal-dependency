package cn.cy.security.web.service;


import cn.cy.security.web.model.LoginInfo;

import java.util.List;

/**
 * @Author: 友叔(xu)
 * @Date: 2020/12/24 21:10
 * @Description: 系统访问日志情况信息 Service
 */
public interface ILoginInfoService {
    /**
     * 新增系统登录日志
     *
     * @param loginInfo 访问日志对象
     */
    public void insertLoginInfo(LoginInfo loginInfo);

    /**
     * 查询系统登录日志集合
     *
     * @param loginInfo 访问日志对象
     * @return 登录记录集合
     */
    public List<LoginInfo> selectLoginInfoList(LoginInfo loginInfo);

    /**
     * 批量删除系统登录日志
     *
     * @param infoIds 需要删除的登录日志ID
     * @return
     */
    public int deleteLoginInfoByIds(Long[] infoIds);

    /**
     * 清空系统登录日志
     */
    public int cleanLoginInfo();
}
