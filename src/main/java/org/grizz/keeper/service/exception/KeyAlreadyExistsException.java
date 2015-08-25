package org.grizz.keeper.service.exception;

import lombok.Getter;

/**
 * Created by Grizz on 2015-08-24.
 */

public class KeyAlreadyExistsException extends RuntimeException {
    @Getter
    private String key;

    public KeyAlreadyExistsException(String key) {
        this.key = key;
    }
}
