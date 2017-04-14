package org.grizz.keeper.service.exception.entry;

import lombok.Getter;

public class EntryDoesNotExistException extends RuntimeException {
    @Getter
    private final String id;

    public EntryDoesNotExistException(String id) {
        this.id = id;
    }
}
