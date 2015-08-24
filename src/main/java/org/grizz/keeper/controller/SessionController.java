package org.grizz.keeper.controller;

import lombok.extern.slf4j.Slf4j;
import org.grizz.keeper.model.Entry;
import org.grizz.keeper.service.UserService;
import org.grizz.keeper.service.exception.codes.ErrorEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by tomasz.bielaszewski on 2015-08-24.
 */
@Slf4j
@RestController
public class SessionController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/logout/success", method = RequestMethod.GET)
    public void logoutSuccess() {
    }

    @RequestMapping(value = "/insufficientPermissions", method = RequestMethod.GET)
    public Entry denied() {
        String currentUser = userService.getCurrentUsersLogin();
        if (currentUser != null) {
            log.warn("User [{}] tried to access prohibited area", currentUser);
        }
        return ErrorEntry.insufficientPermissions();
    }
}
