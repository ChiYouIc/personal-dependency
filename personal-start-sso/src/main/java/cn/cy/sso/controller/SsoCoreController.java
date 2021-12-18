package cn.cy.sso.controller;

import cn.cy.sso.config.properties.SsoProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @author: 开水白菜
 * @description: 单点认证Controller
 * @create: 2021-08-25 21:13
 **/
@RestController
@ConditionalOnProperty(prefix = "sso-core", name = "type", havingValue = "client")
public class SsoCoreController {

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private SsoProperties properties;

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        String token = request.getHeader("Authentication-Token");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authentication-Token", token);
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(new LinkedMultiValueMap<>(), headers);
        restTemplate.postForEntity(properties.logoutUrl(), httpEntity, Void.class);
        return "注销成功!";
    }

    @GetMapping("/callback/{code}")
    public SsoResult callback(@PathVariable("code") String code) {
        SsoResult result = restTemplate.getForObject("/callback/" + code, SsoResult.class);
        return Objects.requireNonNull(result);
    }
}
