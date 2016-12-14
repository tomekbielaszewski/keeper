package org.grizz.keeper.service.exception.codes;

import org.grizz.keeper.model.Entry;

public class ErrorEntry {
    public static Entry restrictedKey(String key) {
        return error("Provided key [" + key + "] is restricted!");
    }

    public static Entry keyAndValueAreMandatory() {
        return error("KEY and VALUE are mandatory!");
    }

    public static Entry unauthorized() {
        return error("Unauthorized access! Login first!");
    }

    public static Entry badLoginPassword() {
        return error("Bad login or password!");
    }

    public static Entry insufficientPermissions() {
        return error("Insufficient permissions!");
    }

    public static Entry invalidKeyOwner(String key) {
        return error("Provided key [" + key + "] belongs to somebody else!");
    }

    public static Entry userFieldsMissing() {
        return error("LOGIN and PASSWORD are mandatory!");
    }

    public static Entry userAlreadyExists(String login) {
        return error("User [" + login + "] already exist!");
    }

    public static Entry noSuchGroup(String groupName) {
        return error("There is no group with name " + groupName);
    }

    public static Entry noSuchUser(String login) {
        return error("User [" + login + "] does not exist!");
    }

    public static Entry groupDoesNotExist(String id) {
        return error("Group with id [" + id + "] does not exist!");
    }

    public static Entry groupMandatoryFieldMissing() {
        return error("Group NAME and KEYS are mandatory");
    }

    public static Entry groupAlreadyExists(String name) {
        return error("Group with name [" + name + "] already exist!");
    }

    public static Entry keyDoesNotExist(String key) {
        return error("Key [" + key + "] does not exist!");
    }

    public static Entry invalidGroupOwner() {
        return error("You are not an owner of this group!");
    }

    public static Entry groupIsNotCreated() {
        return error("Cannot update group - create it first!");
    }

    private static Entry error(String error) {
        return Entry.builder()
                .key("ERROR")
                .value(error)
                .date(System.currentTimeMillis())
                .build();
    }
}
