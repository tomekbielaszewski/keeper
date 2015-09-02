package org.grizz.keeper.service.exception.codes;

import org.grizz.keeper.model.Entry;
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

    public static EntryEntity badLoginPassword() {
        return EntryEntity.builder()
                .key("ERROR")
                .value("Bad login or password!")
                .date(System.currentTimeMillis())
                .build();
    }

    public static EntryEntity insufficientPermissions() {
        return EntryEntity.builder()
                .key("ERROR")
                .value("Insufficient permissions!")
                .date(System.currentTimeMillis())
                .build();
    }

    public static Entry invalidKeyOwner(String key) {
        return EntryEntity.builder()
                .key("ERROR")
                .value("Provided key [" + key + "] belongs to somebody else!")
                .date(System.currentTimeMillis())
                .build();
    }

    public static Entry userFieldsMissing() {
        return EntryEntity.builder()
                .key("ERROR")
                .value("LOGIN and PASSWORD are mandatory!")
                .date(System.currentTimeMillis())
                .build();
    }

    public static Entry userAlreadyExists(String login) {
        return EntryEntity.builder()
                .key("ERROR")
                .value("User [" + login + "] already exist!")
                .date(System.currentTimeMillis())
                .build();
    }

    public static Entry noSuchGroup(String groupName) {
        return EntryEntity.builder()
                .key("ERROR")
                .value("There is no group with name " + groupName)
                .date(System.currentTimeMillis())
                .build();
    }

    public static Entry noSuchUser(String login) {
        return EntryEntity.builder()
                .key("ERROR")
                .value("User [" + login + "] does not exist!")
                .date(System.currentTimeMillis())
                .build();
    }

    public static Entry groupDoesNotExist(String id) {
        return EntryEntity.builder()
                .key("ERROR")
                .value("Group with id [" + id + "] does not exist!")
                .date(System.currentTimeMillis())
                .build();
    }

    public static Entry groupMandatoryFieldMissing() {
        return EntryEntity.builder()
                .key("ERROR")
                .value("Group NAME and KEYS are mandatory")
                .date(System.currentTimeMillis())
                .build();
    }

    public static Entry groupAlreadyExists(String name) {
        return EntryEntity.builder()
                .key("ERROR")
                .value("Group with name [" + name + "] already exist!")
                .date(System.currentTimeMillis())
                .build();
    }

    public static Entry keyDoesNotExist(String key) {
        return EntryEntity.builder()
                .key("ERROR")
                .value("Key [" + key + "] does not exist!")
                .date(System.currentTimeMillis())
                .build();
    }

    public static Entry invalidGroupOwner() {
        return EntryEntity.builder()
                .key("ERROR")
                .value("You are not an owner of this group!")
                .date(System.currentTimeMillis())
                .build();
    }

    public static Entry groupIsNotCreated() {
        return EntryEntity.builder()
                .key("ERROR")
                .value("Cannot update group - create it first!")
                .date(System.currentTimeMillis())
                .build();
    }
}
