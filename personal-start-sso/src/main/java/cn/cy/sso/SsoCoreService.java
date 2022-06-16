package cn.cy.sso;

import cn.cy.sso.config.properties.SsoProperties;
import cn.cy.sso.controller.SsoResult;
import cn.cy.sso.model.RequestPath;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @Author: 友
 * @Date: 2022/6/16 18:44
 * @Description: Sso 服务
 */
@Service
public class SsoCoreService {

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private SsoProperties properties;

    public String logout(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authentication-Token", token);
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(new LinkedMultiValueMap<>(), headers);
        restTemplate.postForEntity(properties.logoutUrl(), httpEntity, Void.class);
        return "注销成功!";
    }

    public SsoResult callback(@PathVariable("code") String code) {
        SsoResult result = restTemplate.getForObject("/callback/" + code, SsoResult.class);
        return Objects.requireNonNull(result);
    }

    public boolean sendRequestUrl(List<RequestPath> pathList) {
        SsoResult result = restTemplate.postForObject("/client/path/" + properties.getAppCode(), pathList, SsoResult.class);
        return result != null && (boolean) result.getData();
    }
}
