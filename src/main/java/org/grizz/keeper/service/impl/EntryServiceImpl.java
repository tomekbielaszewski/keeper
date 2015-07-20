package org.grizz.keeper.service.impl;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.grizz.keeper.model.impl.EntryEntity;
import org.grizz.keeper.model.repos.EntryRepository;
import org.grizz.keeper.service.EntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by Grizz on 2015-07-20.
 */
@Slf4j
@Component
public class EntryServiceImpl implements EntryService {
    @Autowired
    private EntryRepository entryRepository;

    @Override
    public List<EntryEntity> getHistory(String key) {
        List<EntryEntity> entries = entryRepository.findByKeyOrderByDateDesc(key);
        return entries;
    }

    @Override
    public List<EntryEntity> getHistoryFromLast(String key, Long from) {
        List<EntryEntity> entries = entryRepository.findByKeyAndDateGreaterThanEqualOrderByDateDesc(key, from);
        return entries;
    }

    @Override
    public EntryEntity getLast(String key) {
        EntryEntity entry = entryRepository.findTopByKeyOrderByDateDesc(key);
        return entry;
    }

    @Override
    public EntryEntity add(EntryEntity entry) {
        if (validate(entry)) throw new MandatoryFieldsMissingException();
        if (hasRestrictedKey(entry)) throw new RestrictedKeyException(entry.getKey());

        fillDateIfNeeded(entry);

        EntryEntity insertedEntry = entryRepository.insert(entry);
        return insertedEntry;
    }

    @Override
    public List<EntryEntity> addMany(List<EntryEntity> entries) {
        for (EntryEntity entry : entries) {
            if (validate(entry)) throw new MandatoryFieldsMissingException();
            if (hasRestrictedKey(entry)) throw new RestrictedKeyException(entry.getKey());

            fillDateIfNeeded(entry);
        }

        List<EntryEntity> insertedEntries = entryRepository.insert(entries);
        return insertedEntries;
    }

    private void fillDateIfNeeded(EntryEntity entry) {
        if (entry.getDate() == null) {
            entry.setDate(System.currentTimeMillis());
        }
    }

    private boolean validate(EntryEntity entry) {
        return StringUtils.isEmpty(entry.getKey()) ||
                StringUtils.isEmpty(entry.getValue());
    }

    private boolean hasRestrictedKey(EntryEntity entry) {
        return "ERROR".equals(entry.getKey());
    }

    public class MandatoryFieldsMissingException extends RuntimeException {
    }

    public class RestrictedKeyException extends RuntimeException {
        @Getter
        private String key;

        public RestrictedKeyException(String key) {
            this.key = key;
        }
    }
}
