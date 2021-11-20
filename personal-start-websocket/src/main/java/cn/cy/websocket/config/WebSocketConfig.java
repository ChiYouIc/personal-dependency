package cn.cy.websocket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author: 开水白菜
 * @description: WebSocket 配置
 * @create: 2021-11-20 00:34
 **/
@Configuration
public class WebSocketConfig {

    /**
     * ServerEndpointExporter 作用：
     * 这个 Bean 会自动注册使用 @ServerEndpoint 注解声明的 websocket endpoint
     *
     * @return ServerEndpointExporter
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
