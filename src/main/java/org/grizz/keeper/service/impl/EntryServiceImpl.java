package org.grizz.keeper.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.grizz.keeper.model.impl.EntryEntity;
import org.grizz.keeper.model.repos.EntryRepository;
import org.grizz.keeper.service.EntryService;
import org.grizz.keeper.service.UserService;
import org.grizz.keeper.service.exception.MandatoryFieldsMissingException;
import org.grizz.keeper.service.exception.entry.InvalidKeyOwnerException;
import org.grizz.keeper.service.exception.entry.KeyDoesNotExistException;
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
        if (!keyExist(key)) throw new KeyDoesNotExistException(key);
        List<EntryEntity> entries = entryRepository.findTop2000ByKeyOrderByDateDesc(key);
        return entries;
    }

    @Override
    public List<EntryEntity> getHistorySince(String key, Long from) {
        if (!keyExist(key)) throw new KeyDoesNotExistException(key);
        List<EntryEntity> entries = entryRepository.findTop2000ByKeyAndDateGreaterThanEqualOrderByDateDesc(key, from);
        return entries;
    }

    @Override
    public boolean keyExist(String key) {
        return entryRepository.findFirstByKey(key) != null;
    }

    @Override
    public EntryEntity getLast(String key) {
        if (!keyExist(key)) throw new KeyDoesNotExistException(key);
        EntryEntity entry = entryRepository.findTopByKeyOrderByDateDesc(key);
        return entry;
    }

    @Override
    public EntryEntity add(EntryEntity entry) {
        validate(entry);
        validateUserOwnership(entry);
        hasRestrictedKey(entry);

        fillDateIfNeeded(entry);
        entry.setOwner(userService.getCurrentUserLogin());

        EntryEntity insertedEntry = entryRepository.insert(entry);
        return insertedEntry;
    }

    @Override
    public List<EntryEntity> addMany(List<EntryEntity> entries) {
        for (EntryEntity entry : entries) {
            validate(entry);
            validateUserOwnership(entry);
            hasRestrictedKey(entry);

            fillDateIfNeeded(entry);
            entry.setOwner(userService.getCurrentUserLogin());
        }

        List<EntryEntity> insertedEntries = entryRepository.insert(entries);
        return insertedEntries;
    }

    @Override
    public Long deleteAll(String key) {
        if (!keyExist(key)) throw new KeyDoesNotExistException(key);
        return entryRepository.deleteByKey(key);
    }

    @Override
    public Long deleteSingle(String key, Long date) {
        if (!keyExist(key)) throw new KeyDoesNotExistException(key);
        return entryRepository.deleteByKeyAndDate(key, date);
    }

    @Override
    public Long deleteOlderThan(String key, Long date) {
        if (!keyExist(key)) throw new KeyDoesNotExistException(key);
        return entryRepository.deleteByKeyAndDateLessThan(key, date);
    }

    private void validate(EntryEntity entry) {
        if (StringUtils.isEmpty(entry.getKey()) ||
                StringUtils.isEmpty(entry.getValue())) throw new MandatoryFieldsMissingException();
    }

    private void validateUserOwnership(EntryEntity entry) {
        EntryEntity entryFromDB = entryRepository.findFirstByKey(entry.getKey());
        if (entryFromDB != null) {
            String currentUserLogin = userService.getCurrentUserLogin();
            if (!currentUserLogin.equals(entryFromDB.getOwner())) throw new InvalidKeyOwnerException(entry.getKey());
        }
    }

    private void hasRestrictedKey(EntryEntity entry) {
        if ("ERROR".equals(entry.getKey()))
            throw new RestrictedKeyException(entry.getKey());
    }

    private void fillDateIfNeeded(EntryEntity entry) {
        if (entry.getDate() == null) {
            entry.setDate(System.currentTimeMillis());
        }
    }
}
