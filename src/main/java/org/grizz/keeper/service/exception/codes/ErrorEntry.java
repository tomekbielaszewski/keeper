package org.grizz.keeper.service.exception.codes;

import org.grizz.keeper.model.impl.EntryEntity;

/**
 * Created by Grizz on 2015-07-26.
 */
public class ErrorEntry {
    public static EntryEntity restrictedKey(String key) {
        return EntryEntity.builder()
                .key("ERROR")
                .value("Provided key [" + key + "] is restricted!")
                .date(System.currentTimeMillis())
                .build();
    }

    public static EntryEntity keyAndValueAreMandatory() {
        return EntryEntity.builder()
                .key("ERROR")
                .value("KEY and VALUE are mandatory!")
                .date(System.currentTimeMillis())
                .build();
    }

    public static EntryEntity unauthorized() {
        return EntryEntity.builder()
                .key("ERROR")
                .value("Unauthorized access! Login first!")
                .date(System.currentTimeMillis())
                .build();
    }
}
