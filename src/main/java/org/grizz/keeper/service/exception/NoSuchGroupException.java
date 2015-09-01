package org.grizz.keeper.service.exception;

import lombok.Getter;

/**
 * Created by tomasz.bielaszewski on 2015-09-01.
 */
public class NoSuchGroupException extends RuntimeException {
    @Getter
    private final String groupName;

    public NoSuchGroupException(String groupName) {
        this.groupName = groupName;
    }
}
