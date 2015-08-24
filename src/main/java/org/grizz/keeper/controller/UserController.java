package org.grizz.keeper.controller;

import lombok.extern.slf4j.Slf4j;
import org.grizz.keeper.model.User;
import org.grizz.keeper.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by tomasz.bielaszewski on 2015-08-24.
 */
@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public User getCurrentUser() {
        return userService.getCurrentUser();
    }

    @RequestMapping(value = "/keys", method = RequestMethod.GET)
    public List<String> getCurrentUserKeys() {
        List<String> currentUserKeys = userService.getCurrentUserKeys();
        log.info("{} listed all his keys. Amount {}", userService.getCurrentUsersLogin(), currentUserKeys.size());
        return currentUserKeys;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<? extends User> getList() {
        List<? extends User> allUsers = userService.getAll();
        log.info("ADMIN: {} listed all users. Amount {}", userService.getCurrentUsersLogin(), allUsers.size());
        return allUsers;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/{login}", method = RequestMethod.GET)
    public User getByLogin(@PathVariable String login) {
        User user = userService.getByLogin(login);
        log.info("ADMIN: {} showed user with login {}", userService.getCurrentUsersLogin(), user.getLogin());
        return user;
    }
}
