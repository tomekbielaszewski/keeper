package org.grizz.keeper.service;

import org.grizz.keeper.model.Entry;
import org.grizz.keeper.model.EntryGroup;
import org.grizz.keeper.model.Group;
import org.grizz.keeper.model.User;
import org.grizz.keeper.model.repos.GroupRepository;
import org.grizz.keeper.service.exception.MandatoryFieldsMissingException;
import org.grizz.keeper.service.exception.entry.KeyDoesNotExistException;
import org.grizz.keeper.service.exception.group.*;
import org.grizz.keeper.service.exception.user.NoSuchUserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GroupService {
    @Autowired
    private GroupRepository groupRepo;
    @Autowired
    private UserService userService;
    @Autowired
    private EntryService entryService;

    public Group get(String name) {
        return Optional.ofNullable(groupRepo.findByName(name))
                .orElseThrow(() -> new NoSuchGroupException(name));
    }

    public List<Group> getCurrentUserGroups() {
        String currentUser = userService.getCurrentUserLogin();
        return getUserGroups(currentUser);
    }

    public List<Group> getUserGroups(String login) {
        User user = Optional.ofNullable(userService.getByLogin(login))
                .orElseThrow(() -> new NoSuchUserException(login));
        List<Group> byOwner = groupRepo.findByOwner(user.getLogin());
        return byOwner;
    }

    public Group add(Group group) {
        validateMandatoryFields(group);
        validateGroupAlreadyExists(group);
        validateKeysExist(group);

        group.setOwner(userService.getCurrentUserLogin());
        Group insertedGroup = groupRepo.insert(group);

        return insertedGroup;
    }

    public Group update(Group group) {
        validateMandatoryFields(group);
        validateGroupExist(group);
        validateKeysExist(group);
        validateOwner(group);

        Group groupToUpdate = groupRepo.findOne(group.getId());
        groupToUpdate.setName(group.getName());
        groupToUpdate.setKeys(group.getKeys());
        groupToUpdate.setOwner(group.getOwner());

        Group updatedGroup = groupRepo.save(groupToUpdate);

        return updatedGroup;
    }

    public EntryGroup getEntries(String groupName) {
        Group byName = Optional.ofNullable(groupRepo.findByName(groupName))
                .orElseThrow(() -> new NoSuchGroupException(groupName));
        List<String> keys = byName.getKeys();
        List<Entry> entries = keys.stream()
                .map(key -> entryService.getLast(key))
                .collect(Collectors.toList());

        return EntryGroup.builder()
                .name(groupName)
                .entries(entries)
                .build();
    }

    private void validateMandatoryFields(Group group) {
        Objects.requireNonNull(group);
        if (StringUtils.isEmpty(group.getName())) throw new MandatoryFieldsMissingException();
        if (group.getKeys() == null || group.getKeys().isEmpty()) throw new MandatoryFieldsMissingException();
    }

    private void validateGroupAlreadyExists(Group group) {
        if (groupRepo.findByName(group.getName()) != null) throw new GroupAlreadyExistsException(group.getName());
    }

    private void validateKeysExist(Group group) {
        for (String key : group.getKeys()) {
            if (!entryService.keyExist(key)) throw new KeyDoesNotExistException(key);
        }
    }

    private void validateOwner(Group group) {
        if (StringUtils.isEmpty(group.getOwner())) throw new MandatoryFieldsMissingException();
        if (!groupRepo.findOne(group.getId()).getOwner().equals(userService.getCurrentUserLogin()))
            throw new InvalidGroupOwnerException();
    }

    private void validateGroupExist(Group group) {
        if (group.getId() == null) throw new GroupIsNotCreatedException();
        if (groupRepo.findOne(group.getId()) == null) throw new GroupDoesNotExistException(group.getId());
    }
}
