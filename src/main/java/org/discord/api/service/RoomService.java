package org.discord.api.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.discord.api.entity.Room;
import org.discord.api.mapper.RoomMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RoomService {
    @Resource
    private RoomMapper roomMapper;

    public List<Room> getList(Long userId, Long channelId) {
        List<Room> roomList = roomMapper.selectList(new LambdaQueryWrapper<Room>().eq(Room::getChannel, channelId));
        for (Room room : roomList) {
            if (room.getType() == 1 && !roomMapper.getRoomMember(room.getId()).contains(userId)){
                room.setPermission(false);
                continue;
            }
            room.setPermission(true);
        }

        return roomList;
    }
}
