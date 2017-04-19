package org.grizz.keeper.controller;

import lombok.extern.slf4j.Slf4j;
import org.grizz.keeper.model.Entry;
import org.grizz.keeper.model.User;
import org.grizz.keeper.service.UserService;
import org.grizz.keeper.service.exception.MandatoryFieldsMissingException;
import org.grizz.keeper.service.exception.codes.ErrorEntry;
import org.grizz.keeper.service.exception.user.NoSuchUserException;
import org.grizz.keeper.service.exception.user.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(method = RequestMethod.GET)
    public List<User> getList() {
        List<User> allUsers = userService.getAll();
        log.info("ADMIN: {} listed all users. Amount {}", userService.getCurrentUserLogin(), allUsers.size());
        return allUsers;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/{login}", method = RequestMethod.GET)
    public User getByLogin(@PathVariable String login) {
        User user = userService.getByLogin(login);
        log.info("ADMIN: {} showed user with login {}", userService.getCurrentUserLogin(), login);
        return user;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public User createNew(@RequestBody User user) {
        User newUser = userService.add(user);
        log.info("ADMIN: {} added new user with login {}", userService.getCurrentUserLogin(), newUser.getLogin());
        return newUser;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MandatoryFieldsMissingException.class)
    public Entry mandatoryFieldsMissingExceptionHandler() {
        return ErrorEntry.userFieldsMissing();
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(UserAlreadyExistsException.class)
    public Entry keyAlreadyExistsExceptionHandler(Exception e) {
        UserAlreadyExistsException exception = (UserAlreadyExistsException) e;
        return ErrorEntry.userAlreadyExists(exception.getLogin());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchUserException.class)
    public Entry noSuchUserExceptionHandler(Exception e) {
        NoSuchUserException exception = (NoSuchUserException) e;
        return ErrorEntry.noSuchUser(exception.getLogin());
    }
}
