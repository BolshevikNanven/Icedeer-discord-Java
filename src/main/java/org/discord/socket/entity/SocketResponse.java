package org.discord.socket.entity;

import lombok.Data;

@Data
public class SocketResponse <T> {
    private Integer type;

    private T body;

    public SocketResponse(Integer type,T body){
        this.type=type;
        this.body=body;
    }

}
