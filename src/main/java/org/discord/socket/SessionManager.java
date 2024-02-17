package org.discord.socket;

import org.discord.socket.entity.SessionEntity;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {
    private static final ConcurrentHashMap<Long, SessionEntity> SESSION_MAP = new ConcurrentHashMap<>();

    public static void add(Long id, SessionEntity sessionEntity) {
        SESSION_MAP.put(id, sessionEntity);
    }

    public static SessionEntity get(Long id) {
        return SESSION_MAP.get(id);
    }

    public static ConcurrentHashMap<Long, SessionEntity> getAll(){
        return SESSION_MAP;
    }

    public static void remove(Long id) {
        WebSocketSession session = SESSION_MAP.remove(id).getSession();
        if (session != null) {
            try {
                session.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

}
