package org.grizz.keeper.service.exception.entry;

import lombok.Getter;

public class RestrictedKeyException extends RuntimeException {
    @Getter
    private final String key;

    public RestrictedKeyException(String key) {
        this.key = key;
    }
}
