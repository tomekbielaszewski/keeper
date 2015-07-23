package org.grizz.keeper.service;

import org.grizz.keeper.model.impl.EntryEntity;

import java.util.List;

/**
 * Created by Grizz on 2015-07-20.
 */
public interface EntryService {
    List<EntryEntity> getHistory(String key);

    List<EntryEntity> getHistoryFromLast(String key, Long from);

    EntryEntity getLast(String key);

    EntryEntity add(EntryEntity entry);

    List<EntryEntity> addMany(List<EntryEntity> entries);

    Long deleteAll(String key);

    Long deleteSingle(String key, Long date);

    Long deleteOlderThan(String key, Long date);
}
