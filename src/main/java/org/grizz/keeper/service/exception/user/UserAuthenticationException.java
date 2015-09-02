package org.grizz.keeper.service.exception.user;

import org.springframework.security.core.AuthenticationException;

/**
 * Created by tomasz.bielaszewski on 2015-08-25.
 */
public class UserAuthenticationException extends AuthenticationException {
    public UserAuthenticationException(String msg) {
        super(msg);
    }
}
