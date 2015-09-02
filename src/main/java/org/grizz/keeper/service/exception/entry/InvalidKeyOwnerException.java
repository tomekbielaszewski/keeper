package org.grizz.keeper.service.exception.entry;

import lombok.Getter;

/**
 * Created by tomasz.bielaszewski on 2015-09-02.
 */
public class InvalidKeyOwnerException extends RuntimeException {
    @Getter
    private final String key;

    public InvalidKeyOwnerException(String key) {
        this.key = key;
    }
}
