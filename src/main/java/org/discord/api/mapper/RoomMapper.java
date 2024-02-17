package org.discord.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.discord.api.entity.Room;

import java.util.List;

public interface RoomMapper extends BaseMapper<Room> {
    @Select("SELECT user_id FROM user_to_room WHERE room_id = #{roomId}")
    List<Long> getRoomMember(Long roomId);

    @Select("SELECT room_id FROM user_to_room WHERE user_id = #{userId} AND channel_id = #{channelId}")
    List<Long> selectRoomIds(Long userId,Long channelId);
}
