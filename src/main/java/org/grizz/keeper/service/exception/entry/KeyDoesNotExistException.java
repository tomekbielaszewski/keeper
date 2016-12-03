package org.grizz.keeper.service.exception.entry;

import lombok.Getter;

public class KeyDoesNotExistException extends RuntimeException {
    @Getter
    private final String key;

    public KeyDoesNotExistException(String key) {
        this.key = key;
    }
}
