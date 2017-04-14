package org.grizz.keeper.service.exception.codes;

import com.google.common.collect.Maps;
import org.grizz.keeper.model.Entry;

import java.util.Map;

public class ErrorEntry {
    public static Entry restrictedKey(String key) {
        return getErrorEntry("Provided key [" + key + "] is restricted!");
    }

    public static Entry keyAndValueAreMandatory() {
        return getErrorEntry("KEY and VALUE are mandatory!");
    }

    public static Entry unauthorized() {
        return getErrorEntry("Unauthorized access! Login first!");
    }

    public static Entry badLoginPassword() {
        return getErrorEntry("Bad login or password!");
    }

    public static Entry insufficientPermissions() {
        return getErrorEntry("Insufficient permissions!");
    }

    public static Entry invalidKeyOwner(String key) {
        return getErrorEntry("Provided key [" + key + "] belongs to somebody else!");
    }

    public static Entry userFieldsMissing() {
        return getErrorEntry("LOGIN and PASSWORD are mandatory!");
    }

    public static Entry userAlreadyExists(String login) {
        return getErrorEntry("User [" + login + "] already exist!");
    }

    public static Entry noSuchGroup(String groupName) {
        return getErrorEntry("There is no group with name " + groupName);
    }

    public static Entry noSuchUser(String login) {
        return getErrorEntry("User [" + login + "] does not exist!");
    }

    public static Entry groupDoesNotExist(String id) {
        return getErrorEntry("Group with id [" + id + "] does not exist!");
    }

    public static Entry groupMandatoryFieldMissing() {
        return getErrorEntry("Group NAME and KEYS are mandatory");
    }

    public static Entry groupAlreadyExists(String name) {
        return getErrorEntry("Group with name [" + name + "] already exist!");
    }

    public static Entry keyDoesNotExist(String key) {
        return getErrorEntry("Key [" + key + "] does not exist!");
    }

    public static Entry entryDoesNotExist(String id) {
        return getErrorEntry("Entry with id [" + id + "] does not exist!");
    }

    public static Entry invalidGroupOwner() {
        return getErrorEntry("You are not an owner of this group!");
    }

    public static Entry groupIsNotCreated() {
        return getErrorEntry("Cannot update group - create it first!");
    }

    private static Entry getErrorEntry(String message) {
        Map<String, Object> value = Maps.newHashMap();
        value.put("message", message);
        return Entry.builder()
                .key("ERROR")
                .value(value)
                .date(System.currentTimeMillis())
                .build();
    }
}
