package cn.cy.security.factory;

import cn.cy.common.util.LogUtils;
import cn.cy.common.util.ServletUtils;
import cn.cy.common.util.SpringUtil;
import cn.cy.common.util.ip.AddressUtils;
import cn.cy.common.util.ip.IpUtils;
import cn.cy.security.enums.LoginStatus;
import cn.cy.security.web.model.LoginInfo;
import cn.cy.security.web.model.LoginUser;
import cn.cy.security.web.service.ILoginInfoService;
import cn.cy.security.web.service.ISecurityUserService;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.TimerTask;

/**
 * @Author: 友叔
 * @Date: 2020/12/31 16:21
 * @Description: 登录日志记录异步工厂
 */
@Slf4j
public class LoginLogSyncFactory {
    /**
     * 登陆日志
     *
     * @param username 用户名
     * @param status   状态
     * @param message  消息
     * @param args     参数
     * @return 任务
     */
    public static TimerTask recordLoginInfo(final String username, final LoginStatus status, final String message, final Object... args) {
        final UserAgent userAgent = UserAgentUtil.parse(ServletUtils.getRequest().getHeader("User-Agent"));
        final String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
        return new TimerTask() {
            @Override
            public void run() {
                String address = AddressUtils.getRealAddressByIP(ip);
                StringBuilder s = new StringBuilder();
                s.append(LogUtils.getBlock(ip));
                s.append(address);
                s.append(LogUtils.getBlock(username));
                s.append(LogUtils.getBlock(status));
                s.append(LogUtils.getBlock(message));
                // 打印信息到日志
                log.info(s.toString(), args);
                // 获取客户端操作系统
                String os = userAgent.getOs().getName();
                // 获取客户端浏览器
                String browser = userAgent.getBrowser().getName();
                // 封装对象
                LoginInfo loginInfo = new LoginInfo();
                loginInfo.setUsername(username);
                loginInfo.setIpaddr(ip);
                loginInfo.setLoginLocation(address);
                loginInfo.setBrowser(browser);
                loginInfo.setOs(os);
                loginInfo.setMsg(message);
                // 日志状态
                loginInfo.setStatus(status.getStatusCode());
                // 插入数据
                SpringUtil.getBean(ILoginInfoService.class).insertLoginInfo(loginInfo);
            }
        };
    }

    /**
     * 记录用户登陆IP信息
     */
    public static TimerTask recordLoginIpAddress() {
        final String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
        return new TimerTask() {
            @Override
            public void run() {
                LoginUser loginInfo = new LoginUser();
                loginInfo.setLoginIp(ip);
                loginInfo.setLoginDate(LocalDateTime.now());
                SpringUtil.getBean(ISecurityUserService.class).updateUserLoginInfo(loginInfo);
            }
        };
    }
}
