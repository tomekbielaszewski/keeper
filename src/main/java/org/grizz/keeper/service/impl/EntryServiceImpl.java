package org.grizz.keeper.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.grizz.keeper.model.impl.EntryEntity;
import org.grizz.keeper.model.repos.EntryRepository;
import org.grizz.keeper.service.EntryService;
import org.grizz.keeper.service.UserService;
import org.grizz.keeper.service.exception.entry.KeyAlreadyExistsException;
import org.grizz.keeper.service.exception.MandatoryFieldsMissingException;
import org.grizz.keeper.service.exception.entry.RestrictedKeyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by Grizz on 2015-07-20.
 */
@Slf4j
@Service
public class EntryServiceImpl implements EntryService {
    @Autowired
    private EntryRepository entryRepository;
    @Autowired
    private UserService userService;

    @Override
    public List<EntryEntity> getHistory(String key) {
        List<EntryEntity> entries = entryRepository.findByKeyOrderByDateDesc(key);
        return entries;
    }

    @Override
    public List<EntryEntity> getHistorySince(String key, Long from) {
        List<EntryEntity> entries = entryRepository.findByKeyAndDateGreaterThanEqualOrderByDateDesc(key, from);
        return entries;
    }

    @Override
    public boolean keyExist(String key) {
        return entryRepository.findFirstByKey(key) != null;
    }

    @Override
    public EntryEntity getLast(String key) {
        EntryEntity entry = entryRepository.findTopByKeyOrderByDateDesc(key);
        return entry;
    }

    @Override
    public EntryEntity add(EntryEntity entry) {
        if (validate(entry)) throw new MandatoryFieldsMissingException();
        if (validateUserOwnership(entry)) throw new KeyAlreadyExistsException(entry.getKey());
        if (hasRestrictedKey(entry)) throw new RestrictedKeyException(entry.getKey());

        fillDateIfNeeded(entry);
        entry.setOwner(userService.getCurrentUserLogin());

        EntryEntity insertedEntry = entryRepository.insert(entry);
        return insertedEntry;
    }

    @Override
    public List<EntryEntity> addMany(List<EntryEntity> entries) {
        for (EntryEntity entry : entries) {
            if (validate(entry)) throw new MandatoryFieldsMissingException();
            if (validateUserOwnership(entry)) throw new KeyAlreadyExistsException(entry.getKey());
            if (hasRestrictedKey(entry)) throw new RestrictedKeyException(entry.getKey());

            fillDateIfNeeded(entry);
            entry.setOwner(userService.getCurrentUserLogin());
        }

        List<EntryEntity> insertedEntries = entryRepository.insert(entries);
        return insertedEntries;
    }

    @Override
    public Long deleteAll(String key) {
        return entryRepository.deleteByKey(key);
    }

    @Override
    public Long deleteSingle(String key, Long date) {
        return entryRepository.deleteByKeyAndDate(key, date);
    }

    @Override
    public Long deleteOlderThan(String key, Long date) {
        return entryRepository.deleteByKeyAndDateLessThan(key, date);
    }

    private boolean validate(EntryEntity entry) {
        return StringUtils.isEmpty(entry.getKey()) ||
                StringUtils.isEmpty(entry.getValue());
    }

    private boolean validateUserOwnership(EntryEntity entry) {
        EntryEntity entryFromDB = entryRepository.findFirstByKey(entry.getKey());
        if (entryFromDB == null) {
            return false;
        }
        String currentUserLogin = userService.getCurrentUserLogin();
        return !currentUserLogin.equals(entryFromDB.getOwner());
    }

    private boolean hasRestrictedKey(EntryEntity entry) {
        return "ERROR".equals(entry.getKey());
    }

    private void fillDateIfNeeded(EntryEntity entry) {
        if (entry.getDate() == null) {
            entry.setDate(System.currentTimeMillis());
        }
    }
}
