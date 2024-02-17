package org.discord.api.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hy.corecode.idgen.WFGIdGenerator;
import org.discord.api.entity.Message;
import org.discord.api.entity.User;
import org.discord.api.mapper.MessageMapper;
import org.discord.common.enums.ChannelType;
import org.discord.socket.SessionManager;
import org.discord.socket.SocketService;
import org.discord.socket.entity.SessionEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class MessageService {
    @Resource
    private MessageMapper messageMapper;
    @Resource
    private UserService userService;
    @Resource
    private WFGIdGenerator wfgIdGenerator;
    @Resource
    private SocketService socketService;

    public Long sendMessage(Long userId,Message message) throws IOException {
        SessionEntity intent= SessionManager.get(userId);

        message.setFrom(userId);
        message.setId(wfgIdGenerator.next());
        message.setUsername(intent.getUsername());
        message.setAvatar(intent.getAvatar());
        message.setChannel(intent.getChannel());
        message.setTime(new Date());

        messageMapper.insert(message);

        socketService.chat(userId, intent.getSession(), message);

        return message.getId();
    }

    public List<Message> getHistoryMessage(Long userId, Long msgId, Integer roomType, Long channelId,Long roomId) {
        LambdaQueryWrapper<Message> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(Message::getChannel,channelId);
        if (msgId != 0) {
            wrapper.lt(Message::getId, msgId);
        }

        if (roomType == 0) {
            wrapper.and(w->{
                w.or(w1->{
                    w1.eq(Message::getFrom, userId);
                    w1.eq(Message::getTo, roomId);
                });
                w.or(w2 -> {
                    w2.eq(Message::getFrom, roomId);
                    w2.eq(Message::getTo, userId);
                });
            });
        } else {
            wrapper.eq(Message::getTo, roomId);
        }

        wrapper.orderByDesc(Message::getId);
        wrapper.last("limit 15");

        List<Message> messageList = messageMapper.selectList(wrapper);

        for (Message message : messageList){
            User user=userService.getNameAndAvatar(message.getFrom());
            message.setUsername(user.getUsername());
            message.setAvatar(user.getMiniAvatar());
        }

        return messageList;

    }

}
