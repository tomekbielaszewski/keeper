package org.grizz.keeper.service.exception.user;

import lombok.Getter;

/**
 * Created by Grizz on 2015-08-25.
 */
public class UserAlreadyExistsException extends RuntimeException {
    @Getter
    private final String login;

    public UserAlreadyExistsException(String login) {
        this.login = login;
    }
}
