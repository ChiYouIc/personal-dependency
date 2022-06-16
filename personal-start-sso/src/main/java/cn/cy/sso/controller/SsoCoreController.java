package cn.cy.sso.controller;

import cn.cy.sso.SsoCoreService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author: 开水白菜
 * @description: 单点认证Controller
 * @create: 2021-08-25 21:13
 **/
@RestController
@ConditionalOnProperty(prefix = "sso-core", name = "type", havingValue = "client")
public class SsoCoreController {

    @Resource
    private SsoCoreService ssoService;

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        String token = request.getHeader("Authentication-Token");
        return ssoService.logout(token);
    }

    @GetMapping("/callback/{code}")
    public SsoResult callback(@PathVariable("code") String code) {
        return ssoService.callback(code);
    }
}
