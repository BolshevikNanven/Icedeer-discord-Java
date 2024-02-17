package org.discord.api.controller;

import org.discord.api.entity.Room;
import org.discord.api.service.RoomService;
import org.discord.common.annotation.RequireAuthentication;
import org.discord.common.entity.JsonResponse;
import org.discord.util.JWTUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomController {
    @Resource
    private RoomService roomService;

    @RequireAuthentication
    @PostMapping("/list")
    public JsonResponse<List<Room>> getRoomList(HttpServletRequest req, @RequestParam("channel") Long channelId) {
        Long id = Long.parseLong(JWTUtil.decodeId(req.getHeader("Authentication")));
        return JsonResponse.success(roomService.getList(id,channelId));
    }
}
