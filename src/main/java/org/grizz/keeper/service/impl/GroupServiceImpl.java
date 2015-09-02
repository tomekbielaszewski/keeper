package org.grizz.keeper.service.impl;

import com.google.common.collect.Lists;
import org.grizz.keeper.model.Entry;
import org.grizz.keeper.model.EntryGroup;
import org.grizz.keeper.model.Group;
import org.grizz.keeper.model.User;
import org.grizz.keeper.model.impl.EntryEntity;
import org.grizz.keeper.model.impl.EntryGroupEntity;
import org.grizz.keeper.model.impl.GroupEntity;
import org.grizz.keeper.model.repos.GroupRepository;
import org.grizz.keeper.service.EntryService;
import org.grizz.keeper.service.GroupService;
import org.grizz.keeper.service.UserService;
import org.grizz.keeper.service.exception.MandatoryFieldsMissingException;
import org.grizz.keeper.service.exception.entry.KeyDoesNotExistException;
import org.grizz.keeper.service.exception.group.*;
import org.grizz.keeper.service.exception.user.NoSuchUserException;
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
        if (user == null) {
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

    @Override
    public Group update(GroupEntity group) {
        validate(group);
        validateGroupExists(group);
        validateOwner(group);
        validateKeysExist(group);

        GroupEntity groupToUpdate = groupRepo.findOne(group.getId());
        groupToUpdate.setName(group.getName());
        groupToUpdate.setKeys(group.getKeys());
        groupToUpdate.setOwner(group.getOwner());

        GroupEntity updatedGroup = groupRepo.save(groupToUpdate);

        return updatedGroup;
    }

    @Override
    public EntryGroup getEntries(String groupName) {
        GroupEntity byName = groupRepo.findByName(groupName);
        if (byName == null) throw new NoSuchGroupException(groupName);
        List<String> keys = byName.getKeys();
        List<Entry> entries = Lists.newArrayList();

        for (String key : keys) {
            EntryEntity last = entryService.getLast(key);
            entries.add(last);
        }

        return EntryGroupEntity.builder()
                .name(groupName)
                .entries(entries)
                .build();
    }

    private void validate(GroupEntity group) {
        Objects.requireNonNull(group);
        if (StringUtils.isEmpty(group.getName())) throw new MandatoryFieldsMissingException();
        if (group.getKeys() == null || group.getKeys().isEmpty()) throw new MandatoryFieldsMissingException();
    }

    private void validateGroupAlreadyExists(GroupEntity group) {
        if (groupRepo.findByName(group.getName()) != null) throw new GroupAlreadyExistsException(group.getName());
    }

    private void validateKeysExist(GroupEntity group) {
        for (String key : group.getKeys()) {
            if (!entryService.keyExist(key)) throw new KeyDoesNotExistException(key);
        }
    }

    private void validateOwner(GroupEntity group) {
        if (StringUtils.isEmpty(group.getOwner())) throw new MandatoryFieldsMissingException();
        if (!groupRepo.findOne(group.getId()).getOwner().equals(userService.getCurrentUserLogin()))
            throw new InvalidGroupOwnerException();
    }

    private void validateGroupExists(GroupEntity group) {
        if (group.getId() == null) throw new GroupIsNotCreatedException();
        if (groupRepo.findOne(group.getId()) == null) throw new GroupDoesNotExistException(group.getId());
    }
}
