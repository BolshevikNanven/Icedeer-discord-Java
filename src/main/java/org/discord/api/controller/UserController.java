package org.discord.api.controller;

import org.discord.api.entity.Channel;
import org.discord.api.entity.User;
import org.discord.api.service.UserService;
import org.discord.common.annotation.RequireAuthentication;
import org.discord.common.entity.JsonResponse;
import org.discord.util.JWTUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @RequireAuthentication
    @PostMapping("/info")
    public JsonResponse<User> getInfo(HttpServletRequest req) {
        Long id = Long.parseLong(JWTUtil.decodeId(req.getHeader("Authentication")));

        return JsonResponse.success(userService.getInfo(id));
    }


}
