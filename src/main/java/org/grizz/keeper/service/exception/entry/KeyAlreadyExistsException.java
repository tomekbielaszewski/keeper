package org.grizz.keeper.service.exception.entry;

import lombok.Getter;

public class KeyAlreadyExistsException extends RuntimeException {
    @Getter
    private final String key;

    public KeyAlreadyExistsException(String key) {
        this.key = key;
    }
}
