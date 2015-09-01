package org.grizz.keeper.service.impl;

import org.grizz.keeper.model.Entry;
import org.grizz.keeper.model.Group;
import org.grizz.keeper.model.User;
import org.grizz.keeper.model.impl.GroupEntity;
import org.grizz.keeper.model.repos.GroupRepository;
import org.grizz.keeper.service.EntryService;
import org.grizz.keeper.service.GroupService;
import org.grizz.keeper.service.UserService;
import org.grizz.keeper.service.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * Created by Grizz on 2015-08-31.
 */
@Service
public class GroupServiceImpl implements GroupService {
    @Autowired
    private GroupRepository groupRepo;
    @Autowired
    private UserService userService;
    @Autowired
    private EntryService entryService;

    @Override
    public Group get(String name) {
        GroupEntity group = groupRepo.findByName(name);
        if (group == null) {
            throw new NoSuchGroupException(name);
        }
        return group;
    }

    @Override
    public List<? extends Group> getCurrentUserGroups() {
        String currentUser = userService.getCurrentUserLogin();
        return getUserGroups(currentUser);
    }

    @Override
    public List<? extends Group> getUserGroups(String login) {
        User user = userService.getByLogin(login);
        if(user == null) {
            throw new NoSuchUserException(login);
        }
        List<GroupEntity> byOwner = groupRepo.findByOwner(login);
        return byOwner;
    }

    @Override
    public Group add(GroupEntity group) {
        validate(group);
        validateGroupAlreadyExists(group);
        validateKeysExist(group);

        group.setOwner(userService.getCurrentUserLogin());
        GroupEntity insertedGroup = groupRepo.insert(group);

        return insertedGroup;
    }

    private void validate(GroupEntity group) {
        Objects.requireNonNull(group);
        if(StringUtils.isEmpty(group.getName())) throw new MandatoryFieldsMissingException();
        if(group.getKeys() == null || group.getKeys().isEmpty()) throw new MandatoryFieldsMissingException();
    }

    private void validateGroupAlreadyExists(GroupEntity group) {
        if(groupRepo.findByName(group.getName()) != null) throw new GroupAlreadyExistsException(group.getName());
    }

    private void validateKeysExist(GroupEntity group) {
        for (String key : group.getKeys()) {
            if(!entryService.keyExist(key)) throw new KeyDoesNotExistException(key);
        }
    }

    @Override
    public Group update(GroupEntity group) {
        return null;
    }

    @Override
    public List<? extends Entry> getEntries(String name) {
        return null;
    }

}
