package org.discord.api.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.discord.api.entity.Channel;
import org.discord.api.entity.User;
import org.discord.api.mapper.ChannelMapper;
import org.discord.api.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChannelService {

    @Resource
    private ChannelMapper channelMapper;

    public List<Channel> getList(Long userid) {
        List<Channel> channelList = new ArrayList<>();

        List<Long> channelIds = channelMapper.selectChannelIds(userid);

        for (Long channelId : channelIds) {
            channelList.add(channelMapper.selectById(channelId));
        }
        return channelList;
    }

    public List<User> getMember(Long userId,Long channelId) throws Exception {
        List<Long> channelIds=channelMapper.selectChannelIds(userId);
        if(!channelIds.contains(channelId)){
            throw new Exception("无权限");
        }
        return channelMapper.getMember(channelId);
    }

    public Channel searchChannel(Long channelId){
        return channelMapper.selectById(channelId);
    }
}
