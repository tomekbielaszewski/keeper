package org.grizz.keeper.service.exception.entry;

import lombok.Getter;

/**
 * Created by tomasz.bielaszewski on 2015-09-01.
 */
public class KeyDoesNotExistException extends RuntimeException {
    @Getter
    private final String key;

    public KeyDoesNotExistException(String key) {
        this.key = key;
    }
}
