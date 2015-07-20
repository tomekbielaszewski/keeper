package org.grizz.keeper.service.exception;

import lombok.Getter;

/**
 * Created by Grizz on 2015-07-20.
 */
public class RestrictedKeyException extends RuntimeException {
    @Getter
    private String key;

    public RestrictedKeyException(String key) {
        this.key = key;
    }
}
