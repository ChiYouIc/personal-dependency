package cn.cy.websocket.service;

import org.springframework.stereotype.Service;

import javax.websocket.server.ServerEndpoint;

/**
 * @author: 开水白菜
 * @description: WebSocket 服务
 * @create: 2021-11-20 01:04
 **/
@Service
@ServerEndpoint("/webSocket/{sid}")
public class WebSocketServer extends WebSocketServerSupport {

}
