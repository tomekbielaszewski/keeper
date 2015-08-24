package org.grizz.keeper.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.grizz.keeper.model.User;
import org.grizz.keeper.model.repos.UserRepository;
import org.grizz.keeper.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by tomasz.bielaszewski on 2015-08-24.
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepo;

    @Override
    public List<? extends User> getAll() {
        return userRepo.findAll();
    }

    @Override
    public User getByLogin(String login) {
        return userRepo.findByLogin(login);
    }

    @Override
    public User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String currentUserLogin;

        if (principal instanceof UserDetails) {
            currentUserLogin = ((UserDetails) principal).getUsername();
        } else {
            currentUserLogin = principal.toString();
        }

        if (StringUtils.isEmpty(currentUserLogin)) {
            return null;
        }

        User currentUser = this.getByLogin(currentUserLogin);
        return currentUser;
    }

    @Override
    public List<String> getCurrentUserKeys() {
        User currentUser = getCurrentUser();
        //TODO: get userKeys somehow
        return null;
    }
}
