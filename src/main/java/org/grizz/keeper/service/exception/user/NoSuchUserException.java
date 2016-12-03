package org.grizz.keeper.service.exception.user;

import lombok.Getter;

public class NoSuchUserException extends RuntimeException {
    @Getter
    private final String login;

    public NoSuchUserException(String login) {
        this.login = login;
    }
}
