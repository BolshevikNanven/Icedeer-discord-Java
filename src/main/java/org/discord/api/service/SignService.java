package org.discord.api.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.discord.api.entity.User;
import org.discord.api.mapper.UserMapper;
import org.discord.util.JWTUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class SignService extends ServiceImpl<UserMapper, User> {

    @Resource
    private UserMapper userMapper;

    public String makeToken(String username, String password) throws Exception {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, username);

        User user = userMapper.selectOne(wrapper);

        if (user == null) {
            throw new Exception("用户名不存在");
        }
        if (!password.equals(user.getPassword())) {
            throw new Exception("密码错误");
        }

        return JWTUtil.sign(user.getId().toString());
    }
}
