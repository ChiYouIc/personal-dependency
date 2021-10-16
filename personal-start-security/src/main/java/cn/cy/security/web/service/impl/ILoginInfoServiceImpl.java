package cn.cy.security.web.service.impl;

import cn.cy.security.web.mapper.LoginInfoMapper;
import cn.cy.security.web.model.LoginInfo;
import cn.cy.security.web.service.ILoginInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: 友叔(xu)
 * @Date: 2020/12/24 21:13
 * @Description: 系统登陆日志记录Service实现
 */
@Slf4j
@Service
public class ILoginInfoServiceImpl implements ILoginInfoService {

    @Autowired
    private LoginInfoMapper loginInfoMapper;

    /**
     * 新增系统登录日志
     *
     * @param loginInfo 访问日志对象
     */
    @Override
    public void insertLoginInfo(LoginInfo loginInfo) {
        log.info("{}", loginInfo);
        loginInfoMapper.insertLoginInfo(loginInfo);
    }

    /**
     * 查询系统登录日志集合
     *
     * @param loginInfo 访问日志对象
     * @return 登录记录集合
     */
    @Override
    public List<LoginInfo> selectLoginInfoList(LoginInfo loginInfo) {
        return loginInfoMapper.selectLoginInfoList(loginInfo);
    }

    /**
     * 批量删除系统登录日志
     *
     * @param infoIds 需要删除的登录日志ID
     * @return
     */
    @Override
    public int deleteLoginInfoByIds(Long[] infoIds) {
        return loginInfoMapper.deleteLoginInfoByIds(infoIds);
    }

    /**
     * 清空系统登录日志
     */
    @Override
    public int cleanLoginInfo() {
        return loginInfoMapper.cleanLoginInfo();
    }
}
