package org.grizz.keeper.service.exception.user;

import lombok.Getter;

public class UserAlreadyExistsException extends RuntimeException {
    @Getter
    private final String login;

    public UserAlreadyExistsException(String login) {
        this.login = login;
    }
}
