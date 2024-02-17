package org.discord.socket;

import com.alibaba.fastjson.JSON;
import com.hy.corecode.idgen.WFGIdGenerator;
import org.discord.api.entity.User;
import org.discord.api.service.MessageService;
import org.discord.api.service.UserService;
import org.discord.socket.entity.SessionEntity;
import org.discord.socket.entity.SocketMessage;
import org.discord.util.JWTUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.annotation.Resource;

@Component
public class SocketHandler extends TextWebSocketHandler {

    @Resource
    private UserService userService;
    @Resource
    private SocketService socketService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String url = session.getUri().getPath();
        String token = url.substring(url.lastIndexOf('/') + 1);
        Long id = Long.parseLong(JWTUtil.decodeId(token));

        SessionEntity sessionEntity = new SessionEntity();
        User user = userService.getInfo(id);

        sessionEntity.setSession(session);
        sessionEntity.setUsername(user.getUsername());
        sessionEntity.setAvatar(user.getMiniAvatar());

        SessionManager.add(id, sessionEntity);
        System.out.println(id + "连接成功");

    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String url = session.getUri().getPath();
        String token = url.substring(url.lastIndexOf('/') + 1);
        Long id = Long.parseLong(JWTUtil.decodeId(token));

        SocketMessage socketMessage = JSON.parseObject(message.getPayload(), SocketMessage.class);

        switch (socketMessage.getType()) {
            //切换频道
            case 1:{
                socketService.switchChannel(id,session,socketMessage.getBody());
                break;
            }
            default: session.close();
        }


    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String url = session.getUri().getPath();
        String token = url.substring(url.lastIndexOf('/') + 1);
        Long id = Long.parseLong(JWTUtil.decodeId(token));

        SessionManager.remove(id);
        System.out.println(id + "断开连接");
    }
}
