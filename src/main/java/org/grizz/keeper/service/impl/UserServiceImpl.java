package org.grizz.keeper.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.grizz.keeper.model.User;
import org.grizz.keeper.model.impl.UserEntity;
import org.grizz.keeper.model.repos.EntryRepository;
import org.grizz.keeper.model.repos.UserRepository;
import org.grizz.keeper.service.UserService;
import org.grizz.keeper.service.exception.MandatoryFieldsMissingException;
import org.grizz.keeper.service.exception.user.NoSuchUserException;
import org.grizz.keeper.service.exception.user.UserAlreadyExistsException;
import org.grizz.keeper.utils.HashingUtils;
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
    @Autowired
    private EntryRepository entryRepo;

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
        String currentUserLogin = this.getCurrentUserLogin();
        User currentUser = this.getByLogin(currentUserLogin);
        return currentUser;
    }

    @Override
    public String getCurrentUserLogin() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String currentUserLogin;

        if (principal instanceof UserDetails) {
            currentUserLogin = ((UserDetails) principal).getUsername();
        } else {
            currentUserLogin = principal.toString();
        }

        return currentUserLogin;
    }

    @Override
    public List<String> getUserKeys(String login) {
        if(userRepo.findByLogin(login) == null) throw new NoSuchUserException(login);
        List userOwnedKeys = entryRepo.findUserOwnedKeys(login);
        return userOwnedKeys;
    }

    @Override
    public List<String> getCurrentUserKeys() {
        String currentUserLogin = getCurrentUserLogin();
        return getUserKeys(currentUserLogin);
    }

    @Override
    public User add(UserEntity user) {
        validateUser(user);
        validateUserAlreadyExists(user);

        String password = user.getPasswordHash();
        user.setPasswordHash(HashingUtils.hash(password));
        user.setId(null);

        UserEntity newUser = userRepo.insert(user);

        return newUser;
    }

    private void validateUser(UserEntity user) {
        if (StringUtils.isEmpty(user.getLogin())) throw new MandatoryFieldsMissingException();
        if (StringUtils.isEmpty(user.getPasswordHash())) throw new MandatoryFieldsMissingException();
    }

    private void validateUserAlreadyExists(UserEntity user) {
        UserEntity existingUser = userRepo.findByLogin(user.getLogin());
        if(existingUser != null) throw new UserAlreadyExistsException(user.getLogin());
    }
}
