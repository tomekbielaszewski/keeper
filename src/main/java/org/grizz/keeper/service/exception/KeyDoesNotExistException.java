package org.grizz.keeper.service.exception;

/**
 * Created by tomasz.bielaszewski on 2015-09-01.
 */
public class KeyDoesNotExistException extends RuntimeException {
    private final String key;

    public KeyDoesNotExistException(String key) {
        this.key = key;
    }
}
