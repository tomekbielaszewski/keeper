package org.grizz.keeper.model.repos;

import org.grizz.keeper.model.impl.EntryEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by Grizz on 2015-07-14.
 */
public interface EntryRepository extends MongoRepository<EntryEntity, String>, EntryRepositoryCustom {
    List<EntryEntity> findTop2000ByKeyOrderByDateDesc(String key);

    EntryEntity findTopByKeyOrderByDateDesc(String key);

    List<EntryEntity> findTop2000ByKeyAndDateGreaterThanEqualOrderByDateDesc(String key, Long date);

    Long deleteByKey(String key);

    Long deleteByKeyAndDate(String key, Long date);

    Long deleteByKeyAndDateLessThan(String key, Long date);

    EntryEntity findFirstByKey(String key);
}
