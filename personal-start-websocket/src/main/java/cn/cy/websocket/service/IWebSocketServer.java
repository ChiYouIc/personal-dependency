package cn.cy.websocket.service;

import javax.websocket.CloseReason;
import javax.websocket.Session;
import java.io.IOException;

/**
 * @author: 开水白菜
 * @description: WebSocket服务接口
 * @create: 2021-11-20 00:44
 **/
public interface IWebSocketServer {
    /**
     * 建立连接成功时调用
     *
     * @param session  session
     * @param username 用户名
     * @throws IOException
     */
    public void onOpen(Session session, String username) throws IOException;

    /**
     * 关闭连接时调用
     *
     * @param username 用户名
     */
    public void onClose(String username);

    /**
     * 收到客户端消息时调用
     *
     * @param message 消息
     */
    public void onMessage(String message);

    /**
     * 发生错误时调用
     *
     * @param session   session
     * @param throwable 异常
     */
    public void onError(Session session, Throwable throwable);

    /**
     * 发送消息，服务端向客户端发送消息
     *
     * @param message 消息
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException;

    /**
     * 向某个 userId 用户发送消息
     *
     * @param message 消息
     * @param userId  用户ID
     * @throws IOException
     */
    public void sendMessage(String message, String userId) throws IOException;

    /**
     * 服务端主动关闭 websocket 连接
     *
     * @throws IOException
     */
    public void close() throws IOException;

    /**
     * 服务端主动关闭 websocket 连接
     *
     * @param reason 关闭缘由
     * @throws IOException
     */
    public void close(CloseReason reason) throws IOException;
}
