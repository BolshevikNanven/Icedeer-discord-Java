package org.discord.socket;

import com.alibaba.fastjson.JSON;
import com.hy.corecode.idgen.WFGIdGenerator;
import org.discord.api.entity.Message;
import org.discord.api.mapper.ChannelMapper;
import org.discord.api.mapper.MessageMapper;
import org.discord.api.mapper.RoomMapper;
import org.discord.socket.entity.SessionEntity;
import org.discord.socket.entity.SocketResponse;
import org.discord.socket.entity.SwitchChannel;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class SocketService {
    @Resource
    private ChannelMapper channelMapper;
    @Resource
    private RoomMapper roomMapper;

    public void chat(Long id, WebSocketSession session, Message message) throws IOException {

        SocketResponse<Message> socketResponse = new SocketResponse<>(0,message);

        TextMessage textResponse = new TextMessage(JSON.toJSONString(socketResponse));

        if (message.getType() == 0) {
            SessionEntity target = SessionManager.get(message.getTo());
            //目标在线 && 同一频道 && 目标不为自身
            if (target != null && target.getChannel().equals(message.getChannel()) && !message.getFrom().equals(id)) {
                target.getSession().sendMessage(textResponse);
            }
            session.sendMessage(textResponse);
        } else if (message.getType() == 2) {
            for (Map.Entry<Long, SessionEntity> entry : SessionManager.getAll().entrySet()) {
                SessionEntity target = entry.getValue();
                if (target.getChannel().equals(message.getChannel()) && !entry.getKey().equals(id)) {
                    target.getSession().sendMessage(textResponse);
                }
            }
        } else if (message.getType() == 1) {
            for (Map.Entry<Long, SessionEntity> entry : SessionManager.getAll().entrySet()) {
                SessionEntity target = entry.getValue();
                if (target.getChannel().equals(message.getChannel()) && target.getRoom().contains(message.getTo()) && !entry.getKey().equals(id)) {
                    target.getSession().sendMessage(textResponse);
                }
            }
        }

    }

    public void switchChannel(Long id, WebSocketSession session, String body) throws IOException {
        SessionEntity intent = SessionManager.get(id);

        SwitchChannel switchChannel = JSON.parseObject(body, SwitchChannel.class);
        List<Long> chanelList = channelMapper.selectChannelIds(id);

        if (chanelList.contains(switchChannel.getChannel())) {
            intent.setChannel(switchChannel.getChannel());
            intent.setRoom(roomMapper.selectRoomIds(id, switchChannel.getChannel()));
        }

    }

}
