package org.grizz.keeper.model.repos;

import org.grizz.keeper.model.impl.EntryEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by Grizz on 2015-07-14.
 */
public interface UserRepository extends MongoRepository<EntryEntity, String> {
    List<EntryEntity> findByKeyOrderByDateDesc(String key);
    EntryEntity findTopByKeyOrderByDateDesc(String key);
    List<EntryEntity> findByKeyAndDateGreaterThanEqualOrderByDateDesc(String key, Long date);

    Long deleteByKey(String key);

    Long deleteByKeyAndDate(String key, Long date);

    Long deleteByKeyAndDateLessThan(String key, Long date);
}
