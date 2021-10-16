package cn.cy.security.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 友叔
 * @Date: 2020/11/24 21:32
 * @Description: 获取不需要保护的资源路径
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "source.ignored")
public class IgnoreUrlsConfig {
    private List<String> urls = new ArrayList<>();
}
