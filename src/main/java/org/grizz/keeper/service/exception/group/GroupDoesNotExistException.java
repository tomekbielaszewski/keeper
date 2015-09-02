package org.grizz.keeper.service.exception.group;

import lombok.Getter;

/**
 * Created by tomasz.bielaszewski on 2015-09-02.
 */
public class GroupDoesNotExistException extends RuntimeException {
    @Getter
    private final String id;

    public GroupDoesNotExistException(String id) {
        this.id = id;
    }
}
