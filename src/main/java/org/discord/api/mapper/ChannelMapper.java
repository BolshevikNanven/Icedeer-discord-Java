package org.discord.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.discord.api.entity.Channel;
import org.discord.api.entity.User;

import java.util.List;

public interface ChannelMapper extends BaseMapper<Channel> {
    @Select("select channel.* from user_to_channel,channel where user_to_channel.user_id = #{userid} and channel.id = user_to_channel.channel_id")
    List<Channel> getChannelsByUserid(String userid);

    @Select("SELECT user.* FROM user_to_channel,user WHERE user_to_channel.channel_id = #{channelId} and user.id = user_to_channel.user_id")
    List<User> getMember(Long channelId);

    @Select("SELECT channel_id FROM user_to_channel WHERE user_id = #{userId}")
    List<Long> selectChannelIds(Long userId);
}
