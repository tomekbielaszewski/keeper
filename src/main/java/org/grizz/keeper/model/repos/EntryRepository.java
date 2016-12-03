package org.grizz.keeper.model.repos;

import org.grizz.keeper.model.Entry;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EntryRepository extends MongoRepository<Entry, String>, EntryRepositoryCustom {
    List<Entry> findTop2000ByKeyOrderByDateDesc(String key);

    Entry findTopByKeyOrderByDateDesc(String key);

    List<Entry> findTop2000ByKeyAndDateGreaterThanEqualOrderByDateDesc(String key, Long date);

    Long deleteByKey(String key);

    Long deleteByKeyAndDate(String key, Long date);

    Long deleteByKeyAndDateLessThan(String key, Long date);

    Entry findFirstByKey(String key);
}
