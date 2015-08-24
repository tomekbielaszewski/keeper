package org.grizz.keeper.controller;

import org.grizz.keeper.model.Entry;
import org.grizz.keeper.service.exception.codes.ErrorEntry;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by tomasz.bielaszewski on 2015-08-24.
 */
@RestController
public class SessionController {

    @RequestMapping(value = "/logout/success", method = RequestMethod.GET)
    public void logoutSuccess() {
    }

    @RequestMapping(value = "/insufficientPermissions", method = RequestMethod.GET)
    public Entry denied() {
        return ErrorEntry.insufficientPermissions();
    }
}
