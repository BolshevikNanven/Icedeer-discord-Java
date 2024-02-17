package org.discord.common.exception;

public class AuthenticationException extends RuntimeException {
    public AuthenticationException(String msg) {
        super(msg);
    }

    public AuthenticationException() {
        super();
    }
}
