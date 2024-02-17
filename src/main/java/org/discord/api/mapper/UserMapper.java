package org.discord.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.discord.api.entity.User;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {

}
