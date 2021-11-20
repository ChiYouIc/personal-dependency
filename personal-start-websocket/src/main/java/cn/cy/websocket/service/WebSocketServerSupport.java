package cn.cy.websocket.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: 开水白菜
 * @description: WebSocket 服务支撑
 * @create: 2021-11-20 01:24
 **/
public class WebSocketServerSupport implements IWebSocketServer {

    private final static Logger log = LoggerFactory.getLogger(WebSocketServerSupport.class);

    private final static ConcurrentHashMap<String, WebSocketServerSupport> WEBSOCKET_MAP = new ConcurrentHashMap<>();

    private Session session;

    private String userId;

    @OnOpen
    @Override
    public void onOpen(Session session, @PathParam(value = "sid") String userId) throws IOException {
        log.info("{} open the websocket connection", userId);

        this.session = session;
        this.userId = userId;

        // 如果已经存在先移除再添加
        if (WEBSOCKET_MAP.containsKey(userId)) {
            WEBSOCKET_MAP.get(userId).close();
        }
        WEBSOCKET_MAP.put(userId, this);
    }

    @OnClose
    @Override
    public void onClose(@PathParam(value = "sid") String userId) {
        log.info("{} close the websocket connection", userId);
        WEBSOCKET_MAP.remove(userId);
    }

    @OnMessage
    @Override
    public void onMessage(String message) {
        log.info("receive a message: {}", message);
    }

    @OnError
    @Override
    public void onError(Session session, Throwable throwable) {
        log.error(throwable.getMessage());
    }

    @Override
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    @Override
    public void sendMessage(String message, String toUserId) throws IOException {
        if (WEBSOCKET_MAP.containsKey(userId)) {
            WEBSOCKET_MAP.get(toUserId).sendMessage(message);
        } else {
            log.warn("{} be not online", toUserId);
        }
    }

    @Override
    public void close() throws IOException {
        this.session.close();
    }

    @Override
    public void close(CloseReason reason) throws IOException {
        this.session.close(reason);
    }
}
