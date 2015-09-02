package org.grizz.keeper.service.exception.group;

/**
 * Created by tomasz.bielaszewski on 2015-09-02.
 */
public class GroupDoesNotExistException extends RuntimeException {
    private final String id;

    public GroupDoesNotExistException(String id) {
        this.id = id;
    }
}
