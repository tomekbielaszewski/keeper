package org.grizz.keeper.service;

import lombok.extern.slf4j.Slf4j;
import org.grizz.keeper.model.User;
import org.grizz.keeper.model.repos.EntryRepository;
import org.grizz.keeper.model.repos.UserRepository;
import org.grizz.keeper.service.exception.MandatoryFieldsMissingException;
import org.grizz.keeper.service.exception.user.NoSuchUserException;
import org.grizz.keeper.service.exception.user.UserAlreadyExistsException;
import org.grizz.keeper.service.exception.user.UserAuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

import static org.grizz.keeper.utils.HashingUtils.check;
import static org.grizz.keeper.utils.HashingUtils.hash;

@Slf4j
@Service
public class UserService {
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private EntryRepository entryRepo;

    public List<User> getAll() {
        return userRepo.findAll();
    }

    public User getByLogin(String login) {
        return userRepo.findByLogin(login);
    }

    public User getCurrentUser() {
        String currentUserLogin = this.getCurrentUserLogin();
        User currentUser = this.getByLogin(currentUserLogin);
        return currentUser;
    }

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

    public List<String> getUserKeys(String login) {
        validateUserExist(login);
        List userOwnedKeys = entryRepo.findUserOwnedKeys(login);
        return userOwnedKeys;
    }

    public List<String> getCurrentUserKeys() {
        String currentUserLogin = getCurrentUserLogin();
        return getUserKeys(currentUserLogin);
    }

    public User add(User user) {
        validateUser(user);
        validateUserAlreadyExist(user);

        String password = user.getPassword();
        user.setPassword(hash(password));
        user.setId(null);

        User newUser = userRepo.insert(user);
        return newUser;
    }

    public void changePassword(String oldPassword, String newPassword) {
        User currentUser = getCurrentUser();
        if (!check(oldPassword, currentUser.getPassword())) {
            throw new UserAuthenticationException("Bad password!");
        } else {
            currentUser.setPassword(hash(newPassword));
            userRepo.save(currentUser);
        }
    }

    private void validateUser(User user) {
        if (StringUtils.isEmpty(user.getLogin())) throw new MandatoryFieldsMissingException();
        if (StringUtils.isEmpty(user.getPassword())) throw new MandatoryFieldsMissingException();
    }

    private void validateUserAlreadyExist(User user) {
        User existingUser = userRepo.findByLogin(user.getLogin());
        if (existingUser != null) throw new UserAlreadyExistsException(user.getLogin());
    }

    private void validateUserExist(String login) {
        if (userRepo.findByLogin(login) == null) throw new NoSuchUserException(login);
    }
}
