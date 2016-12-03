package org.grizz.keeper.service;

import lombok.extern.slf4j.Slf4j;
import org.grizz.keeper.model.Entry;
import org.grizz.keeper.model.repos.EntryRepository;
import org.grizz.keeper.service.exception.MandatoryFieldsMissingException;
import org.grizz.keeper.service.exception.entry.InvalidKeyOwnerException;
import org.grizz.keeper.service.exception.entry.KeyDoesNotExistException;
import org.grizz.keeper.service.exception.entry.RestrictedKeyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
public class EntryService {
    @Autowired
    private EntryRepository entryRepository;
    @Autowired
    private UserService userService;

    public List<Entry> getHistory(String key) {
        if (!keyExist(key)) throw new KeyDoesNotExistException(key);
        List<Entry> entries = entryRepository.findTop2000ByKeyOrderByDateDesc(key);
        return entries;
    }

    public List<Entry> getHistorySince(String key, Long from) {
        if (!keyExist(key)) throw new KeyDoesNotExistException(key);
        List<Entry> entries = entryRepository.findTop2000ByKeyAndDateGreaterThanEqualOrderByDateDesc(key, from);
        return entries;
    }

    public boolean keyExist(String key) {
        return entryRepository.findFirstByKey(key) != null;
    }

    public Entry getLast(String key) {
        if (!keyExist(key)) throw new KeyDoesNotExistException(key);
        Entry entry = entryRepository.findTopByKeyOrderByDateDesc(key);
        return entry;
    }

    public Entry add(Entry entry) {
        validate(entry);
        validateUserOwnership(entry);
        hasRestrictedKey(entry);

        fillDateIfNeeded(entry);
        entry.setOwner(userService.getCurrentUserLogin());

        Entry insertedEntry = entryRepository.insert(entry);
        return insertedEntry;
    }

    public List<Entry> addMany(List<Entry> entries) {
        for (Entry entry : entries) {
            validate(entry);
            validateUserOwnership(entry);
            hasRestrictedKey(entry);

            fillDateIfNeeded(entry);
            entry.setOwner(userService.getCurrentUserLogin());
        }

        List<Entry> insertedEntries = entryRepository.insert(entries);
        return insertedEntries;
    }

    public Long deleteAll(String key) {
        if (!keyExist(key)) throw new KeyDoesNotExistException(key);
        return entryRepository.deleteByKey(key);
    }

    public Long deleteSingle(String key, Long date) {
        if (!keyExist(key)) throw new KeyDoesNotExistException(key);
        return entryRepository.deleteByKeyAndDate(key, date);
    }

    public Long deleteOlderThan(String key, Long date) {
        if (!keyExist(key)) throw new KeyDoesNotExistException(key);
        return entryRepository.deleteByKeyAndDateLessThan(key, date);
    }

    private void validate(Entry entry) {
        if (StringUtils.isEmpty(entry.getKey()) ||
                StringUtils.isEmpty(entry.getValue())) throw new MandatoryFieldsMissingException();
    }

    private void validateUserOwnership(Entry entry) {
        Entry entryFromDB = entryRepository.findFirstByKey(entry.getKey());
        if (entryFromDB != null) {
            String currentUserLogin = userService.getCurrentUserLogin();
            if (!currentUserLogin.equals(entryFromDB.getOwner())) throw new InvalidKeyOwnerException(entry.getKey());
        }
    }

    private void hasRestrictedKey(Entry entry) {
        if ("ERROR".equals(entry.getKey()))
            throw new RestrictedKeyException(entry.getKey());
    }

    private void fillDateIfNeeded(Entry entry) {
        if (entry.getDate() == null) {
            entry.setDate(System.currentTimeMillis());
        }
    }
}
