package org.discord.api.controller;

import org.discord.api.service.SignService;
import org.discord.common.entity.JsonResponse;
import org.discord.util.JWTUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/sign")
public class SignController {

    @Resource
    private SignService signService;

    @PostMapping("/login")
    public JsonResponse login(@RequestParam("username") String username,
                              @RequestParam("password") String password) {
        try {
            String token = signService.makeToken(username, password);
            return JsonResponse.success(token);
        } catch (Exception e) {
            return JsonResponse.error(401, e.getMessage());
        }

    }


}
