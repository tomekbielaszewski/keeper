package org.grizz.keeper.service.exception.entry;

import lombok.Getter;

public class InvalidKeyOwnerException extends RuntimeException {
    @Getter
    private final String key;

    public InvalidKeyOwnerException(String key) {
        this.key = key;
    }
}
