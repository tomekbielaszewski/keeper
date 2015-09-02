package org.grizz.keeper.controller;

import lombok.extern.slf4j.Slf4j;
import org.grizz.keeper.model.Entry;
import org.grizz.keeper.model.EntryGroup;
import org.grizz.keeper.model.Group;
import org.grizz.keeper.model.impl.GroupEntity;
import org.grizz.keeper.service.GroupService;
import org.grizz.keeper.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Grizz on 2015-08-30.
 */
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

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public List<? extends Group> getCurrentUserGroups() {
        List<? extends Group> groups = groupService.getCurrentUserGroups();
        log.info("{} got his own groups. Amount: {}", userService.getCurrentUserLogin(), groups.size());
        return groups;
    }

    @RequestMapping(value = "/user/{login}", method = RequestMethod.GET)
    public List<? extends Group> getUserGroups(@PathVariable String login) {
        List<? extends Group> groups = groupService.getUserGroups(login);
        log.info("{} got groups of user {}. Amount: {}", userService.getCurrentUserLogin(), login, groups.size());
        return groups;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Group createUserGroup(@RequestBody GroupEntity group) {
        Group newGroup = groupService.add(group);
        log.info("{} added new group with name {}.", userService.getCurrentUserLogin(), newGroup.getName());
        return newGroup;
    }

    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Group updateUserGroup(@RequestBody GroupEntity group) {
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

    //TODO secure endpoints
    //TODO exception handling
}
