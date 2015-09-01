package org.grizz.keeper.service.exception;

import lombok.Getter;

/**
 * Created by tomasz.bielaszewski on 2015-09-01.
 */
public class GroupAlreadyExistsException extends RuntimeException {
    @Getter
    private final String name;

    public GroupAlreadyExistsException(String name) {
        this.name = name;
    }
}
