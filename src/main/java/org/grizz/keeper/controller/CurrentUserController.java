package org.grizz.keeper.controller;

import lombok.extern.slf4j.Slf4j;
import org.grizz.keeper.controller.requests.PasswordChangeRequest;
import org.grizz.keeper.model.Entry;
import org.grizz.keeper.model.Group;
import org.grizz.keeper.model.User;
import org.grizz.keeper.service.GroupService;
import org.grizz.keeper.service.UserService;
import org.grizz.keeper.service.exception.codes.ErrorEntry;
import org.grizz.keeper.service.exception.user.NoSuchUserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user")
public class CurrentUserController {
    @Autowired
    private UserService userService;
    @Autowired
    private GroupService groupService;

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.GET)
    public User getCurrentUser() {
        User currentUser = userService.getCurrentUser();
        return currentUser;
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/keys", method = RequestMethod.GET)
    public List<String> getCurrentUserKeys() {
        List<String> currentUserKeys = userService.getCurrentUserKeys();
        log.info("{} listed all his keys. Amount {}", userService.getCurrentUserLogin(), currentUserKeys.size());
        return currentUserKeys;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/keys/{login}", method = RequestMethod.GET)
    public List<String> getUserKeys(@PathVariable String login) {
        List<String> userKeys = userService.getUserKeys(login);
        log.info("ADMIN: {} listed all keys belonged to {}. Amount {}", userService.getCurrentUserLogin(), login, userKeys.size());
        return userKeys;
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/groups", method = RequestMethod.GET)
    public List<Group> getCurrentUserGroups() {
        List<Group> groups = groupService.getCurrentUserGroups();
        log.info("{} got his own groups. Amount: {}", userService.getCurrentUserLogin(), groups.size());
        return groups;
    }

    @RequestMapping(value = "/groups/{login}", method = RequestMethod.GET)
    public List<Group> getUserGroups(@PathVariable String login) {
        List<Group> groups = groupService.getUserGroups(login);
        log.info("{} got groups of user {}. Amount: {}", userService.getCurrentUserLogin(), login, groups.size());
        return groups;
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/password", method = RequestMethod.PUT)
    public User changePassword(@RequestBody PasswordChangeRequest passwordChange) {
        userService.changePassword(passwordChange.getOldPassword(), passwordChange.getNewPassword());
        log.info("{} changed his password", userService.getCurrentUserLogin());
        return userService.getCurrentUser();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchUserException.class)
    public Entry noSuchUserExceptionHandler(Exception e) {
        NoSuchUserException exception = (NoSuchUserException) e;
        return ErrorEntry.noSuchUser(exception.getLogin());
    }
}
