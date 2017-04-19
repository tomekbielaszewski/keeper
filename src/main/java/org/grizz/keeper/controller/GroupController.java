package org.grizz.keeper.controller;

import lombok.extern.slf4j.Slf4j;
import org.grizz.keeper.model.Entry;
import org.grizz.keeper.model.EntryGroup;
import org.grizz.keeper.model.Group;
import org.grizz.keeper.service.GroupService;
import org.grizz.keeper.service.UserService;
import org.grizz.keeper.service.exception.MandatoryFieldsMissingException;
import org.grizz.keeper.service.exception.codes.ErrorEntry;
import org.grizz.keeper.service.exception.entry.KeyDoesNotExistException;
import org.grizz.keeper.service.exception.group.*;
import org.grizz.keeper.service.exception.user.NoSuchUserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/groups")
public class GroupController {
    @Autowired
    private GroupService groupService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public Group getGroup(@PathVariable String name) {
        Group group = groupService.get(name);
        log.info("{} got group [{}]", userService.getCurrentUserLogin(), name);
        return group;
    }

    @PreAuthorize("hasAuthority('USER')")
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Group createUserGroup(@RequestBody Group group) {
        Group newGroup = groupService.add(group);
        log.info("{} added new group with name {}.", userService.getCurrentUserLogin(), newGroup.getName());
        return newGroup;
    }

    @PreAuthorize("hasAuthority('USER')")
    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Group updateUserGroup(@RequestBody Group group) {
        Group updatedGroup = groupService.update(group);
        log.info("{} updated group with name {}.", userService.getCurrentUserLogin(), updatedGroup.getName());
        return updatedGroup;
    }

    @RequestMapping(value = "/entries/{name}", method = RequestMethod.GET)
    public EntryGroup getGroupedEntries(@PathVariable String name) {
        EntryGroup entryGroup = groupService.getEntries(name);
        log.info("{} got last entries from group {}.", userService.getCurrentUserLogin(), name);
        return entryGroup;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchGroupException.class)
    public Entry noSuchGroupExceptionHandler(Exception e) {
        NoSuchGroupException exception = (NoSuchGroupException) e;
        return ErrorEntry.noSuchGroup(exception.getGroupName());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchUserException.class)
    public Entry noSuchUserExceptionHandler(Exception e) {
        NoSuchUserException exception = (NoSuchUserException) e;
        return ErrorEntry.noSuchUser(exception.getLogin());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(GroupDoesNotExistException.class)
    public Entry groupDoesNotExistException(Exception e) {
        GroupDoesNotExistException exception = (GroupDoesNotExistException) e;
        return ErrorEntry.groupDoesNotExist(exception.getId());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MandatoryFieldsMissingException.class)
    public Entry mandatoryFieldsMissingException() {
        return ErrorEntry.groupMandatoryFieldMissing();
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(GroupAlreadyExistsException.class)
    public Entry groupAlreadyExistsException(Exception e) {
        GroupAlreadyExistsException exception = (GroupAlreadyExistsException) e;
        return ErrorEntry.groupAlreadyExists(exception.getName());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(KeyDoesNotExistException.class)
    public Entry keyDoesNotExistException(Exception e) {
        KeyDoesNotExistException exception = (KeyDoesNotExistException) e;
        return ErrorEntry.keyDoesNotExist(exception.getKey());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidGroupOwnerException.class)
    public Entry invalidGroupOwnerException() {
        return ErrorEntry.invalidGroupOwner();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(GroupIsNotCreatedException.class)
    public Entry groupIsNotCreatedException() {
        return ErrorEntry.groupIsNotCreated();
    }
}
