package org.discord.api.controller;

import org.discord.api.entity.Message;
import org.discord.api.service.MessageService;
import org.discord.common.annotation.RequireAuthentication;
import org.discord.common.entity.JsonResponse;
import org.discord.util.JWTUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {
    @Resource
    private MessageService messageService;

    @RequireAuthentication
    @PostMapping("/history")
    public JsonResponse<List<Message>> getHistory(HttpServletRequest req,
                                                  @RequestParam("lastMsg") Long lastMsg,
                                                  @RequestParam("roomType") Integer roomType,
                                                  @RequestParam("channel") Long channelId,
                                                  @RequestParam("room") Long roomId) {
        Long userId = Long.parseLong(JWTUtil.decodeId(req.getHeader("Authentication")));
        return JsonResponse.success(messageService.getHistoryMessage(userId, lastMsg, roomType, channelId, roomId));

    }

    @RequireAuthentication
    @PostMapping("/send")
    public JsonResponse<String> send(HttpServletRequest req, @RequestBody Message message) {
        Long userId = Long.parseLong(JWTUtil.decodeId(req.getHeader("Authentication")));

        try {
            String msgId = messageService.sendMessage(userId, message).toString();
            return JsonResponse.success(msgId);
        } catch (Exception exception) {
            return JsonResponse.error(100, exception.getMessage());
        }

    }
}
