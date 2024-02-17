package org.discord.socket.entity;

import lombok.Data;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;

@Data
public class SessionEntity {

    private String username;

    private String avatar;

    private Long channel;

    private List<Long> room;

    private WebSocketSession session;

}
