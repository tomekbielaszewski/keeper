package org.grizz.keeper.service.exception;

import lombok.Getter;

/**
 * Created by tomasz.bielaszewski on 2015-09-01.
 */
public class NoSuchUserException extends RuntimeException {
    @Getter
    private final String login;

    public NoSuchUserException(String login) {
        this.login = login;
    }
}
