package org.grizz.keeper.service.exception.group;

import lombok.Getter;

public class GroupDoesNotExistException extends RuntimeException {
    @Getter
    private final String id;

    public GroupDoesNotExistException(String id) {
        this.id = id;
    }
}
