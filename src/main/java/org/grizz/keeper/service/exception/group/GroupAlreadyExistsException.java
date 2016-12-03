package org.grizz.keeper.service.exception.group;

import lombok.Getter;

public class GroupAlreadyExistsException extends RuntimeException {
    @Getter
    private final String name;

    public GroupAlreadyExistsException(String name) {
        this.name = name;
    }
}
