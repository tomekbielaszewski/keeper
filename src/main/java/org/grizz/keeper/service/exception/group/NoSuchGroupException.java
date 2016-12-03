package org.grizz.keeper.service.exception.group;

import lombok.Getter;

public class NoSuchGroupException extends RuntimeException {
    @Getter
    private final String groupName;

    public NoSuchGroupException(String groupName) {
        this.groupName = groupName;
    }
}
