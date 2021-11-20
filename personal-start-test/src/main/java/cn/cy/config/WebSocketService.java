package cn.cy.config;

import cn.cy.websocket.service.WebSocketServerSupport;
import org.springframework.stereotype.Service;

import javax.websocket.server.ServerEndpoint;

/**
 * @author: 开水白菜
 * @description:
 * @create: 2021-11-20 01:26
 **/
@Service
@ServerEndpoint("/websocket/test/{sid}")
public class WebSocketService extends WebSocketServerSupport {
}
