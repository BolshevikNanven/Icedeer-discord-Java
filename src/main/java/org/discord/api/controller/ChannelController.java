package org.discord.api.controller;

import org.discord.api.entity.Channel;
import org.discord.api.entity.User;
import org.discord.api.service.ChannelService;
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
@RequestMapping("/channel")
public class ChannelController {
    @Resource
    private ChannelService channelService;

    @RequireAuthentication
    @PostMapping("/list")
    public JsonResponse<List<Channel>> getChannelList(HttpServletRequest req) {
        Long id = Long.parseLong(JWTUtil.decodeId(req.getHeader("Authentication")));

        return JsonResponse.success(channelService.getList(id));
    }

    @RequireAuthentication
    @PostMapping("/member")
    public JsonResponse<List<User>> getMember(HttpServletRequest req, @RequestParam("channel") Long channelId) {
        Long id = Long.parseLong(JWTUtil.decodeId(req.getHeader("Authentication")));

        try {
            List<User> memberList = channelService.getMember(id, channelId);
            return JsonResponse.success(memberList);
        } catch (Exception e) {
            return JsonResponse.error(401, e.getMessage());
        }
    }

    @RequireAuthentication
    @PostMapping("/search")
    public JsonResponse<Channel> searchChannel(@RequestParam("id") Long channelId) {

        return JsonResponse.success(channelService.searchChannel(channelId));
    }
}
