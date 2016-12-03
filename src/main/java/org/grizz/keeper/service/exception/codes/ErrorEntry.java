package org.grizz.keeper.service.exception.codes;

import org.grizz.keeper.model.Entry;

public class ErrorEntry {
    public static Entry restrictedKey(String key) {
        return Entry.builder()
                .key("ERROR")
                .value("Provided key [" + key + "] is restricted!")
                .date(System.currentTimeMillis())
                .build();
    }

    public static Entry keyAndValueAreMandatory() {
        return Entry.builder()
                .key("ERROR")
                .value("KEY and VALUE are mandatory!")
                .date(System.currentTimeMillis())
                .build();
    }

    public static Entry unauthorized() {
        return Entry.builder()
                .key("ERROR")
                .value("Unauthorized access! Login first!")
                .date(System.currentTimeMillis())
                .build();
    }

    public static Entry badLoginPassword() {
        return Entry.builder()
                .key("ERROR")
                .value("Bad login or password!")
                .date(System.currentTimeMillis())
                .build();
    }

    public static Entry insufficientPermissions() {
        return Entry.builder()
                .key("ERROR")
                .value("Insufficient permissions!")
                .date(System.currentTimeMillis())
                .build();
    }

    public static Entry invalidKeyOwner(String key) {
        return Entry.builder()
                .key("ERROR")
                .value("Provided key [" + key + "] belongs to somebody else!")
                .date(System.currentTimeMillis())
                .build();
    }

    public static Entry userFieldsMissing() {
        return Entry.builder()
                .key("ERROR")
                .value("LOGIN and PASSWORD are mandatory!")
                .date(System.currentTimeMillis())
                .build();
    }

    public static Entry userAlreadyExists(String login) {
        return Entry.builder()
                .key("ERROR")
                .value("User [" + login + "] already exist!")
                .date(System.currentTimeMillis())
                .build();
    }

    public static Entry noSuchGroup(String groupName) {
        return Entry.builder()
                .key("ERROR")
                .value("There is no group with name " + groupName)
                .date(System.currentTimeMillis())
                .build();
    }

    public static Entry noSuchUser(String login) {
        return Entry.builder()
                .key("ERROR")
                .value("User [" + login + "] does not exist!")
                .date(System.currentTimeMillis())
                .build();
    }

    public static Entry groupDoesNotExist(String id) {
        return Entry.builder()
                .key("ERROR")
                .value("Group with id [" + id + "] does not exist!")
                .date(System.currentTimeMillis())
                .build();
    }

    public static Entry groupMandatoryFieldMissing() {
        return Entry.builder()
                .key("ERROR")
                .value("Group NAME and KEYS are mandatory")
                .date(System.currentTimeMillis())
                .build();
    }

    public static Entry groupAlreadyExists(String name) {
        return Entry.builder()
                .key("ERROR")
                .value("Group with name [" + name + "] already exist!")
                .date(System.currentTimeMillis())
                .build();
    }

    public static Entry keyDoesNotExist(String key) {
        return Entry.builder()
                .key("ERROR")
                .value("Key [" + key + "] does not exist!")
                .date(System.currentTimeMillis())
                .build();
    }

    public static Entry invalidGroupOwner() {
        return Entry.builder()
                .key("ERROR")
                .value("You are not an owner of this group!")
                .date(System.currentTimeMillis())
                .build();
    }

    public static Entry groupIsNotCreated() {
        return Entry.builder()
                .key("ERROR")
                .value("Cannot update group - create it first!")
                .date(System.currentTimeMillis())
                .build();
    }
}
