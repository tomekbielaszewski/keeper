package org.grizz.keeper.controller;

import lombok.extern.slf4j.Slf4j;
import org.grizz.keeper.model.Entry;
import org.grizz.keeper.model.User;
import org.grizz.keeper.model.impl.UserEntity;
import org.grizz.keeper.service.UserService;
import org.grizz.keeper.service.exception.MandatoryFieldsMissingException;
import org.grizz.keeper.service.exception.UserAlreadyExistsException;
import org.grizz.keeper.service.exception.codes.ErrorEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
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

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public User getCurrentUser() {
        User currentUser = userService.getCurrentUser();
        ((UserEntity)currentUser).setPasswordHash(null);
        return currentUser;
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/keys", method = RequestMethod.GET)
    public List<String> getCurrentUserKeys() {
        List<String> currentUserKeys = userService.getCurrentUserKeys();
        log.info("{} listed all his keys. Amount {}", userService.getCurrentUserLogin(), currentUserKeys.size());
        return currentUserKeys;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/keys/{login}", method = RequestMethod.GET)
    public List<String> getUserKeys(@PathVariable String login) {
        List<String> userKeys = userService.getUserKeys(login);
        log.info("ADMIN: {} listed all keys belonged to {}. Amount {}", userService.getCurrentUserLogin(), login, userKeys.size());
        return userKeys;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<? extends User> getList() {
        List<? extends User> allUsers = userService.getAll();
        log.info("ADMIN: {} listed all users. Amount {}", userService.getCurrentUserLogin(), allUsers.size());
        return allUsers;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/{login}", method = RequestMethod.GET)
    public User getByLogin(@PathVariable String login) {
        User user = userService.getByLogin(login);
        log.info("ADMIN: {} showed user with login {}", userService.getCurrentUserLogin(), login);
        return user;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public User createNew(@RequestBody UserEntity user) {
        User newUser = userService.add(user);
        log.info("ADMIN: {} added new user with login {}", userService.getCurrentUserLogin(), newUser.getLogin());
        return newUser;
    }

    @ExceptionHandler(MandatoryFieldsMissingException.class)
    public Entry mandatoryFieldsMissingExceptionHandler(Exception e, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return ErrorEntry.userFieldsMissing();
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public Entry keyAlreadyExistsExceptionHandler(Exception e, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        UserAlreadyExistsException exception = (UserAlreadyExistsException) e;
        return ErrorEntry.userAlreadyExists(exception.getLogin());
    }
}
