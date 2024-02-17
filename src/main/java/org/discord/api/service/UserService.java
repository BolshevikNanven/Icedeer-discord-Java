package org.discord.api.service;

import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.discord.api.entity.Channel;
import org.discord.api.entity.User;
import org.discord.api.mapper.ChannelMapper;
import org.discord.api.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Resource
    private UserMapper userMapper;

    public User getInfo(Long id) {
        return userMapper.selectById(id);
    }

    public User getNameAndAvatar(Long id) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getId, id);
        wrapper.select(User::getUsername, User::getMiniAvatar);

        return userMapper.selectOne(wrapper);
    }


}
