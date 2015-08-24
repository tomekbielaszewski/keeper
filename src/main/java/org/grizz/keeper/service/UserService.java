package org.grizz.keeper.service;

import org.grizz.keeper.model.User;

import java.util.List;

/**
 * Created by tomasz.bielaszewski on 2015-08-24.
 */
public interface UserService {
    List<? extends User> getAll();

    User getByLogin(String login);

    List<String> getCurrentUserKeys();

    User getCurrentUser();

    String getCurrentUserLogin();
}