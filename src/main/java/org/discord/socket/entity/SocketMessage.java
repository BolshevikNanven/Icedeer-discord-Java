package org.discord.socket.entity;

import lombok.Data;


@Data
public class SocketMessage {

    private Integer type;

    private String body;
}
